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
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.servlet.internal.DistributorParams;

/**
 *
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class TripleStoreLoginModule implements LoginModule {

    static Logger log = SWBUtils.getLogger(TripleStoreLoginModule.class);
    protected Subject subject;
    protected CallbackHandler callbackHandler;
    protected Map sharedState;
    protected Map options;
    protected boolean loginflag = false;
    protected User principal = null;
    protected Object credential = null;
    protected String website = null;

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
        log.debug("Initialized...");
    }

    public boolean login() throws LoginException {
        if (callbackHandler == null && !(callbackHandler instanceof SWB4CallbackHandler)) {
            throw new LoginException("No callbackHandler or not adecuate callbackHandler supplied");
        }

        String login;
        Callback[] callbacks = new Callback[3];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", false);
        callbacks[2] = new TextInputCallback("Site");
        try {

            callbackHandler.handle(callbacks);
            login = ((NameCallback) callbacks[0]).getName();
            credential = ((PasswordCallback) callbacks[1]).getPassword();
            ((PasswordCallback) callbacks[1]).clearPassword();
            website = ((TextInputCallback)callbacks[2]).getText();
            System.out.println("-- "+login+" -- "+website+" -- "+ new String((char[])credential));
        } catch (IOException ex) {
            log.error("IO Error Login a user", ex);
            throw new LoginException("IO Error: " + ex.getMessage());
        } catch (UnsupportedCallbackException ex) {
            log.error("UnsupportedCallbackException Error Login a user", ex);
            throw new LoginException("UnsupportedCallbackException Error: " + ex.getMessage());
        }
        WebSite ws = SWBContext.getWebSite(website);
        System.out.println(ws);
        UserRepository ur = ws.getUserRepository();
        ur = SWBContext.getUserRepository(ur.getId());
        System.out.println(ur);
        principal = ur.getUserByLogin(login); 
        System.out.println("--"+principal);
        //TODO Checar lo del repositorio de usuarios
        if (null==principal) throw new LoginException("User inexistent");
        
        System.out.println(principal.getClass().getName());
        if (!principal.isActive()) throw new LoginException("User innactive");
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
