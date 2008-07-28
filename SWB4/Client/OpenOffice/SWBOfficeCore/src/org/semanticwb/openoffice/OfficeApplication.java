/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import javax.swing.UIManager;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.openoffice.interfaces.IOpenOfficeApplication;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.dialogs.DialogAbout;
import org.semanticwb.openoffice.ui.dialogs.DialogChangePassword;
import org.semanticwb.openoffice.ui.dialogs.DialogLogin;
import org.semanticwb.openoffice.ui.wizard.SelectDirectory;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectVersionToOpen;
import org.semanticwb.xmlrpc.XmlRpcProxyFactory;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeApplication
{

    private static MenuListener menuListener;
    private static UserInfo userInfo = null;
    private static URI webAddress = null;

    static
    {
        Locale.setDefault(new Locale("es"));
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch ( Exception ue )
        {
            // No debe hacer nada
            System.out.println(ue.getMessage());
        }
    }

    public void setMenuListener(MenuListener menuListener)
    {
        OfficeApplication.menuListener = menuListener;
    }

    protected OfficeApplication()
    {
        if ( OfficeApplication.tryLogin() )
        {
            IOpenOfficeApplication officeApplication = XmlRpcProxyFactory.newInstance(IOpenOfficeApplication.class, webAddress);
            if ( officeApplication.isValidVersion("1.0") && menuListener != null )
            {
                menuListener.onLogin();
            }
        }
    }

    /**
     * Opens a document in a file path
     * @param file Path for the file
     * @return OfficeDocument opened
     * @throws org.semanticwb.WBException If the document can not be opened
     */
    public abstract OfficeDocument open(File file) throws WBException;

    /**
     * Returns all documents opened
     * @return List of documents
     * @throws org.semanticwb.WBException In case that there was an error
     */
    public abstract List<OfficeDocument> getDocuments() throws WBException;

    public final void changePassword()
    {
        DialogChangePassword dialog = new DialogChangePassword(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    /*if (!dialog.isCanceled())
    {
    }*/
    }

    public final void open()
    {
        OpenResultProducer resultProducer = new OpenResultProducer(this);
        Class[] clazz = new Class[]{SelectPage.class, SelectVersionToOpen.class, SelectDirectory.class};
        Wizard wiz = WizardPage.createWizard("Asistente de apertura de contenido", clazz, resultProducer);
        wiz.show();
    }

    public final void showAbout()
    {
        DialogAbout dialog = new DialogAbout(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static URI getWebAddress() throws WBException
    {

        if ( webAddress == null )
        {
            if ( !tryLogin() )
            {
                throw new WBException("The user can be logged");
            }
        }
        return webAddress;
    }

    public static int setupDocument(String contentId)
    {
        int contentIdToReturn = Integer.MIN_VALUE;
        if ( contentId != null && OfficeApplication.tryLogin() )
        {
            try
            {
                IOpenOfficeDocument document = XmlRpcProxyFactory.newInstance(IOpenOfficeDocument.class, OfficeApplication.getWebAddress());
                document.setWebAddress(OfficeApplication.getWebAddress());
                try
                {                    
                    if ( document.exists(Integer.parseInt(contentId)) )
                    {
                        contentIdToReturn = Integer.parseInt(contentId);
                    }
                }
                catch ( NumberFormatException nfe )
                {
                    ErrorLog.log(nfe);
                }
            }
            catch ( WBException e )
            {
                ErrorLog.log(e);
            }
        }
        return contentIdToReturn;
    }
    /*public static MenuListener getMenuListener()
    {
    return menuListener;
    }*/

    private final static boolean logOn()
    {
        boolean logOn=false;
        DialogLogin frmlogin = new DialogLogin(new javax.swing.JFrame(), true);
        frmlogin.setLocationRelativeTo(null);
        frmlogin.setVisible(true);
        if ( !frmlogin.isCanceled() )
        {            
            String login = frmlogin.getLogin();
            String password = frmlogin.getPassword();
            userInfo = new UserInfo(password, login);
            webAddress =  frmlogin.getWebAddress();
            logOn = true;
        }
        else
        {
            logOff();
        }
        return logOn;
    }

    public static boolean tryLogin()
    {
        boolean tryLogin=false;
        if ( userInfo == null || webAddress == null )
        {
            logOn();
            if ( userInfo == null || webAddress == null )
            {
                logOff();
                tryLogin=false;
            }
            else
            {
                tryLogin=true;
            }
        }
        else
        {
            tryLogin=true;
        }
        return tryLogin;
    }

    public final static void logOff()
    {
        userInfo = null;
        webAddress = null;
        if ( menuListener != null )
        {
            menuListener.onLogout();
        }
    }
}
