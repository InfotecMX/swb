/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.security.auth;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import org.semanticwb.Logger;
import org.semanticwb.SWBInstance;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticModel;

/**
 *
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class TripeStoreLoginModule implements LoginModule {

    static Logger log = SWBUtils.getLogger(TripeStoreLoginModule.class);
    protected Subject subject;
    protected CallbackHandler callbackHandler;
    protected Map sharedState;
    protected Map options;
    protected boolean loginflag = false;
    protected User principal = null;
    protected Object credential = null;

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
        log.debug("Initialized...");
    }

    public boolean login() throws LoginException {
        if (callbackHandler == null) {
            throw new LoginException("No callbackHandler supplied");
        }

        String login;
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", false);
        try {

            callbackHandler.handle(callbacks);
            login = ((NameCallback) callbacks[0]).getName();
            credential = ((PasswordCallback) callbacks[1]).getPassword();
            ((PasswordCallback) callbacks[1]).clearPassword();
        } catch (IOException ex) {
            log.error("IO Error Login a user", ex);
            throw new LoginException("IO Error: " + ex.getMessage());
        } catch (UnsupportedCallbackException ex) {
            log.error("UnsupportedCallbackException Error Login a user", ex);
            throw new LoginException("UnsupportedCallbackException Error: " + ex.getMessage());
        }

        principal = SWBContext.getUserRepository("swb_users").getUser(login); //TODO Checar lo del repositorio de usuarios
        if (1!=principal.getStatus()) throw new LoginException("User innactive");
        if (null==principal.getUsrPassword()) {
            if (null!=credential) throw new LoginException("Password Mistmatch");
        } else {
            try {
                if (!principal.getUsrPassword().equals(SWBUtils.CryptoWrapper.comparablePassword(new String((char[]) credential)))) {
                    throw new LoginException("Password Mistmatch");
                }
            } catch (NoSuchAlgorithmException ex) {
                    log.error("User: Can't set a crypted Password", ex);
                    throw new LoginException("Digest Failed");
            }
        }
            
        //FAKE CODE
        //SWBUtils.CryptoWrapper.comparablePassword(new String(credential));
        //log.debug("Login: "+login);
        //log.debug("passw: "+String.valueOf((char[])credential));
        //log.debug("Comp: "+(String.valueOf((char[])credential).equals(login + "08")));
        //if (!String.valueOf((char[])credential).equals(login + "08")) {
        //    throw new LoginException("Password Mistmatch");
        //}
        //SemanticModel model = SWBInstance.getSemanticMgr().createModel("UsrRepDemo","www.semanticwb.org");

        
        /*Resource res = (Resource) model.getRDFModel().createResource(login, 
        model.getRDFModel().createResource(SWBContext.getVocabulary().User.getURI()));*/

        //principal = (User) model.createSemanticObject(login, SWBContext.getVocabulary().User);

        //UserRepository rep = SWBContext.createUserRepository("swb_users", "http://rep.infotec.com.mx");
        //principal = rep.createUser(login);
        
        //End Fake Code

        loginflag = true;
        principal.setUsrLastLogin(new Date());
        return loginflag;
    }

    public boolean commit() throws LoginException {
        if (!loginflag) {
            return false;
        }
        subject.getPrincipals().add(principal);
        subject.getPrivateCredentials().add(credential);
        return loginflag;
    }

    public boolean abort() throws LoginException {
        if (subject != null) {
            subject.getPrincipals().clear();
            subject.getPrivateCredentials().clear();
            subject.getPublicCredentials().clear();
        }
        return true;
    }

    public boolean logout() throws LoginException {
        if (subject != null) {
            subject.getPrincipals().clear();
            subject.getPrivateCredentials().clear();
            subject.getPublicCredentials().clear();
        }
        return true;
    }
}
