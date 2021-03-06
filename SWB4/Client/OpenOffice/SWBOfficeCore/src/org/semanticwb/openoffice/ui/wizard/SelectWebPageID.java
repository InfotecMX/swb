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
 * SelectWebPageID.java
 *
 * Created on 27/01/2009, 06:34:55 PM
 */
package org.semanticwb.openoffice.ui.wizard;

import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.JOptionPane;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.components.WebPage;

/**
 *
 * @author victor.lorenzana
 */
public class SelectWebPageID extends WizardPage
{

    public static final String WEBPAGEID = "WEBPAGEID";
    private WebPageInfo parent;
    /** Creates new form SelectWebPageID */
    public SelectWebPageID()
    {
        initComponents();
    }

     public SelectWebPageID(WebPageInfo parent)
    {
        initComponents();
        this.parent=parent;
    }

    public static String getDescription()
    {
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectWebPageID").getString("IDENTIFICADOR_DE_PÁGINA");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldWebPageID = new javax.swing.JTextField();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectWebPageID"); // NOI18N
        jLabel1.setText(bundle.getString("IDENTIFICADOR_DE_PÁGINA:")); // NOI18N

        jTextFieldWebPageID.setToolTipText("");
        jTextFieldWebPageID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldWebPageIDKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldWebPageID, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldWebPageID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(228, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private String getId(String titulo)
    {
        String newtitulo = "";
        char[] caracteres = titulo.toCharArray();
        for (int i = 0; i < caracteres.length; i++)
        {
            char c = caracteres[i];
            if (c >= 48 && c <= 57) // 0 - 9
            {
                newtitulo += c;
            }
            if (c >= 65 && c <= 90) // A - Z
            {
                newtitulo += c;
            }
            if (c >= 97 && c <= 122) // a - z
            {
                newtitulo += c;
            }
            if (c == 32) // espacio
            {
                newtitulo += "_";
            }
            if (c == 241) // ñ
            {
                newtitulo += "n";
            }
            if (c == 209) // Ñ
            {
                newtitulo += "N";
            }
            if (c >= 224 && c <= 229)	// a
            {
                newtitulo += "a";
            }
            if (c >= 232 && c <= 235)	// e
            {
                newtitulo += "e";
            }
            if (c >= 236 && c <= 239)	// i
            {
                newtitulo += "i";
            }
            if (c >= 242 && c <= 246)	// o
            {
                newtitulo += "o";
            }
            if (c >= 249 && c <= 252)	// u
            {
                newtitulo += "u";
            }
            if (c >= 192 && c <= 197)	// A
            {
                newtitulo += "A";
            }
            if (c >= 200 && c <= 203)	// E
            {
                newtitulo += "E";
            }
            if (c >= 204 && c <= 207)	// I
            {
                newtitulo += "I";
            }
            if (c >= 210 && c <= 214)	// O
            {
                newtitulo += "O";
            }
            if (c >= 217 && c <= 220)	// U
            {
                newtitulo += "U";
            }
            else
            {
                newtitulo += "";
            }
        }
        return newtitulo;
    }

    @Override
    protected void renderingPage()
    {

        Map map = this.getWizardDataMap();
        String titulo = map.get(TitleAndDescription.TITLE).toString();
        this.jTextFieldWebPageID.setText(getId(titulo));
        this.jTextFieldWebPageID.requestFocus();
        super.renderingPage();

    }

    private void jTextFieldWebPageIDKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldWebPageIDKeyTyped
    {//GEN-HEADEREND:event_jTextFieldWebPageIDKeyTyped
        int ichar = evt.getKeyCode();
        if (ichar == KeyEvent.VK_BACK_SPACE)
        {
            return;
        }

        if (jTextFieldWebPageID.getText().length() > 50)
        {
            evt.consume();
        }

        if (evt.getKeyChar() >= 'A' && evt.getKeyChar() <= 'Z') // A-Z
        {
            return;
        }
        else if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') // 0 - 9
        {
            return;
        }
        else if (evt.getKeyChar() >= 'a' && evt.getKeyChar() <= 'z') // a - z
        {
            return;
        }
        else if (evt.getKeyChar() == '_')
        {
            return;
        }
        else
        {
            evt.consume();

        }
    }//GEN-LAST:event_jTextFieldWebPageIDKeyTyped

    @Override
    public WizardPanelNavResult allowNext(String arg, Map map, Wizard wizard)
    {
        return allowFinish(arg, map, wizard);
    }

    @Override
    public WizardPanelNavResult allowFinish(String arg, Map map, Wizard wizard)
    {
        WizardPanelNavResult result = WizardPanelNavResult.REMAIN_ON_PAGE;
        if (jTextFieldWebPageID.getText().isEmpty())
        {
            jTextFieldWebPageID.requestFocus();
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectWebPageID").getString("¡DEBE_INDICAR_UN_IDENTIFICADOR!"), getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            map.put(WEBPAGEID, jTextFieldWebPageID.getText());
            result = WizardPanelNavResult.PROCEED;
        }
        WebPage pageSelected = (WebPage) this.getWizardDataMap().get(SelectPage.WEBPAGE);
        WebSiteInfo site = new WebSiteInfo();
        if(pageSelected==null && this.parent!=null)
        {
            site.id = this.parent.siteID;
        }
        else
        {
            site.id = pageSelected.getSite();
        }
        
        
        boolean exists = false;
        try
        {
            exists = OfficeApplication.getOfficeApplicationProxy().existsPage(site, this.jTextFieldWebPageID.getText());
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            result = WizardPanelNavResult.REMAIN_ON_PAGE;
            return result;
        }
        if (exists)
        {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectWebPageID").getString("¡YA_EXISTE_UNA_PÁGINA_CON_ESE_IDENTIFICADOR!"), getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            result = WizardPanelNavResult.REMAIN_ON_PAGE;
        }
        return result;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextFieldWebPageID;
    // End of variables declaration//GEN-END:variables
}
