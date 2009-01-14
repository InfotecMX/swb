/*
 * DialogLogin.java
 *
 * Created on 3 de junio de 2008, 10:28 AM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.image.ImageObserver;
import java.net.URI;
import org.semanticwb.openoffice.*;

/**
 *
 * @author  victor.lorenzana
 */
public class DialogLogin extends javax.swing.JDialog implements ImageObserver
{    
    private int numTry = 0;
    private boolean canceled = true;
    private URI webAddress;
    private String login,  password;
    ConfigurationListURI configurationListURI = new ConfigurationListURI();

    /** Creates new form DialogLogin */
    public DialogLogin(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
        this.add(new BackGroundImagePanel(this));
        this.setSize(510, 310);

    }

    public boolean isCanceled()
    {
        return canceled;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public URI getWebAddress()
    {
        return webAddress;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Acceso a servicio de publicación");
        setLocationByPlatform(true);
        setModal(true);
        setResizable(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
