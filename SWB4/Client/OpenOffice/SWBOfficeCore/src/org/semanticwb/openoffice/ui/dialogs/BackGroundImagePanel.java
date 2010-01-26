/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BackGroundImagePanel.java
 *
 * Created on 14/01/2009, 12:49:43 PM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.semanticwb.openoffice.ConfigurationListURI;
import org.semanticwb.openoffice.ui.icons.ImageLoader;
import org.semanticwb.openoffice.ui.wizard.Login;

/**
 *
 * @author victor.lorenzana
 */
public class BackGroundImagePanel extends javax.swing.JPanel implements WindowFocusListener
{
    private static final String EMPTY_STRING = "";
    private static final String NL = "\r\n";
    private int numTry = 0;        
    ConfigurationListURI configurationListURI = new ConfigurationListURI();
    private Image imgFondo;
    private DialogLogin parent;
    /** Creates new form BackGroundImagePanel */
    public BackGroundImagePanel(DialogLogin parent)
    {
        this.parent=parent;
        parent.addWindowFocusListener(this);
        preInit();
        initComponents();
        this.setPreferredSize(new Dimension(500, 300));
        this.jComboBoxWebAddress.removeAllItems();
        for (URI uri : configurationListURI.getAddresses())
        {
            this.jComboBoxWebAddress.addItem(uri);
        }
        if (this.jComboBoxWebAddress.getSelectedItem() == null)
        {
            this.jComboBoxWebAddress.requestFocus();
        }
        else
        {
            if (this.jTextFieldClave.getText().equals(EMPTY_STRING))
            {
                this.jTextFieldClave.requestFocus();
            }
            else
            {
                this.jPassword.requestFocus();
            }
        }
        //this.().setDefaultButton(this.jButtonAccept);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPassword = new javax.swing.JLabel();
        jLabelWebAddress = new javax.swing.JLabel();
        jLabelClave = new javax.swing.JLabel();
        jTextFieldClave = new javax.swing.JTextField();
        jPassword = new javax.swing.JPasswordField();
        jComboBoxWebAddress = new javax.swing.JComboBox();
        jButtonAvanced = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonAccept = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();

        setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        setPreferredSize(new java.awt.Dimension(500, 300));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jLabelPassword.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabelPassword.setForeground(new java.awt.Color(51, 102, 153));
        jLabelPassword.setLabelFor(jPassword);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel"); // NOI18N
        jLabelPassword.setText(bundle.getString("CONTRASEÑA:")); // NOI18N

        jLabelWebAddress.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabelWebAddress.setForeground(new java.awt.Color(51, 102, 153));
        jLabelWebAddress.setLabelFor(jComboBoxWebAddress);
        jLabelWebAddress.setText(bundle.getString("SITIO:")); // NOI18N

        jLabelClave.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabelClave.setForeground(new java.awt.Color(51, 102, 153));
        jLabelClave.setLabelFor(jTextFieldClave);
        jLabelClave.setText(bundle.getString("CLAVE:")); // NOI18N

        jTextFieldClave.setFont(new java.awt.Font("Tahoma", 0, 14));

        jPassword.setFont(new java.awt.Font("Tahoma", 0, 14));

        jComboBoxWebAddress.setEditable(true);
        jComboBoxWebAddress.setFont(new java.awt.Font("Tahoma", 0, 14));
        jComboBoxWebAddress.setAutoscrolls(true);
        jComboBoxWebAddress.setName("WebAddress"); // NOI18N
        jComboBoxWebAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxWebAddressActionPerformed(evt);
            }
        });

        jButtonAvanced.setBackground(new java.awt.Color(51, 102, 153));
        jButtonAvanced.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButtonAvanced.setText(bundle.getString("AVANZADO")); // NOI18N
        jButtonAvanced.setToolTipText(bundle.getString("CONFIGURACIÓN_DE_PROXY")); // NOI18N
        jButtonAvanced.setBorder(null);
        jButtonAvanced.setBorderPainted(false);
        jButtonAvanced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAvancedActionPerformed(evt);
            }
        });

        jButtonCancel.setBackground(new java.awt.Color(51, 102, 153));
        jButtonCancel.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButtonCancel.setText(bundle.getString("CANCELAR")); // NOI18N
        jButtonCancel.setBorder(null);
        jButtonCancel.setBorderPainted(false);
        jButtonCancel.setDoubleBuffered(true);
        jButtonCancel.setMargin(new java.awt.Insets(0, 14, 2, 14));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonAccept.setBackground(new java.awt.Color(51, 102, 153));
        jButtonAccept.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButtonAccept.setText(bundle.getString("ACEPTAR")); // NOI18N
        jButtonAccept.setBorder(null);
        jButtonAccept.setBorderPainted(false);
        jButtonAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAcceptActionPerformed(evt);
            }
        });

        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/delete.png"))); // NOI18N
        jButtonDelete.setToolTipText(bundle.getString("BORRAR_CONEXIÓN")); // NOI18N
        jButtonDelete.setContentAreaFilled(false);
        jButtonDelete.setEnabled(false);
        jButtonDelete.setFocusPainted(false);
        jButtonDelete.setFocusable(false);
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelClave)
                    .addComponent(jLabelWebAddress)
                    .addComponent(jLabelPassword))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxWebAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addComponent(jPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(jTextFieldClave, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonAvanced, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jButtonAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxWebAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelWebAddress)
                    .addComponent(jButtonDelete))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelClave))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAvanced, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAccept, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addContainerGap(123, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxWebAddressActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxWebAddressActionPerformed
    {//GEN-HEADEREND:event_jComboBoxWebAddressActionPerformed
        this.jButtonDelete.setEnabled(false);
        if (this.jComboBoxWebAddress.getSelectedItem() != null)
        {
            this.jButtonDelete.setEnabled(true);
            String sUri = this.jComboBoxWebAddress.getSelectedItem().toString();
            try
            {
                URI uri = new URI(sUri);
                this.jTextFieldClave.setText(configurationListURI.getLogin(uri));
            }
            catch (URISyntaxException use)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel").getString("ERROR_AL_ESCRIBIR_LA_DIRECCIÓN_WEB_")+" "+ NL +java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel").getString("DETALLE:_")+" "+use.getMessage(), "Dirección Web", JOptionPane.ERROR_MESSAGE);
            }
        }
}//GEN-LAST:event_jComboBoxWebAddressActionPerformed

    private void jButtonAvancedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAvancedActionPerformed
    {//GEN-HEADEREND:event_jButtonAvancedActionPerformed
        DialogConfigProxy dialogConfigProxy = new DialogConfigProxy();
        dialogConfigProxy.setLocationRelativeTo(this);
        dialogConfigProxy.setVisible(true);
}//GEN-LAST:event_jButtonAvancedActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        parent.setVisible(false);
        parent.canceled = true;
}//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonAcceptActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAcceptActionPerformed
    {//GEN-HEADEREND:event_jButtonAcceptActionPerformed

        numTry++;
        if (numTry < 3)
        {
            if (this.jComboBoxWebAddress.getSelectedItem() != null)
            {
                String sUri = this.jComboBoxWebAddress.getSelectedItem().toString();
                try
                {
                    URI uri = new URI(sUri);
                    if (this.jTextFieldClave.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel").getString("DEBE_INDICAR_LA_CLAVE_DE_ACCESO"), Login.getDescription(), JOptionPane.ERROR_MESSAGE);
                        this.jTextFieldClave.requestFocus();
                        return;
                    }
                    if (this.jPassword.getPassword().length == 0)
                    {
                        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel").getString("DEBE_INDICAR_LA_CONTRASEÑA_DE_ACCESO"), Login.getDescription(), JOptionPane.ERROR_MESSAGE);
                        this.jPassword.requestFocus();
                        return;
                    }
                    //configurationListURI.addUserConfiguration(uri, this.jTextFieldClave.getText());
                    parent.webAddress = uri;
                    parent.login = this.jTextFieldClave.getText();
                    parent.password = new String(this.jPassword.getPassword());
                    parent.setVisible(false);
                    parent.canceled = false;
                }
                catch (URISyntaxException use)
                {
                    JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel").getString("LA_DIRECCIÓN_WEB_NO_ES_VÁLIDA"), Login.getDescription(), JOptionPane.ERROR_MESSAGE);
                    this.jComboBoxWebAddress.requestFocus();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel").getString("DEBE_INDICAR_UNA_DIRECCIÓN_WEB"), Login.getDescription(), JOptionPane.ERROR_MESSAGE);
                this.jComboBoxWebAddress.requestFocus();
            }

        }
        else
        {
            parent.setVisible(false);
            parent.canceled = true;
        }
    // TODO: Agregar logica de acceso
}//GEN-LAST:event_jButtonAcceptActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDeleteActionPerformed
    {//GEN-HEADEREND:event_jButtonDeleteActionPerformed

        URI uri = (URI) this.jComboBoxWebAddress.getSelectedItem();
        if (uri != null)
        {
            int res = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel").getString("¿DESEA_BORRAR_ESTA_CONFIGURACIÓN_DE_CONEXIÓN?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/BackGroundImagePanel").getString("BORRADO_DE_CONFIGURACIÓN"), JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION)
            {
                configurationListURI.removeAddress(uri);
                this.jComboBoxWebAddress.removeItem(uri);

            }
        }
}//GEN-LAST:event_jButtonDeleteActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_formFocusGained
    {//GEN-HEADEREND:event_formFocusGained
        if (this.jComboBoxWebAddress.getSelectedItem() == null)
        {
            this.jComboBoxWebAddress.requestFocus();
        }
        else
        {
            if (this.jTextFieldClave.getText().equals(EMPTY_STRING))
            {
                this.jTextFieldClave.requestFocus();
            }
            else
            {
                this.jPassword.requestFocus();
            }
        }
    }//GEN-LAST:event_formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonAccept;
    private javax.swing.JButton jButtonAvanced;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JComboBox jComboBoxWebAddress;
    private javax.swing.JLabel jLabelClave;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelWebAddress;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JTextField jTextFieldClave;
    // End of variables declaration//GEN-END:variables

    private void preInit()
    {
        imgFondo = ImageLoader.images.get("splash").getImage();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        if (imgFondo != null)
        {
            g.drawImage(imgFondo, 0, 0, 500, 300, this);
        }
    }

    public void windowGainedFocus(WindowEvent e)
    {
        if (this.jComboBoxWebAddress.getSelectedItem() == null)
        {
            this.jComboBoxWebAddress.requestFocus();
        }
        else
        {
            if (this.jTextFieldClave.getText().equals(EMPTY_STRING))
            {
                this.jTextFieldClave.requestFocus();
            }
            else
            {
                this.jPassword.requestFocus();
            }
        }
    }

    public void windowLostFocus(WindowEvent e)
    {

    }

    
}
