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
 * SelectVersionToOpen.java
 *
 * Created on 3 de junio de 2008, 11:55 AM
 */
package org.semanticwb.openoffice.ui.wizard;

import java.awt.Cursor;
import java.net.URL;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.ContentInfo;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.ui.dialogs.DialogPreview;

/**
 *
 * @author  victor.lorenzana
 */
public class SelectVersionToOpen extends WizardPage
{

    public static final String VERSION = "version";
    private static final String CONTENTID = "?contentId=";
    private static final String GTW = "gtw";
    private static final String NAME = "&name=";
    private static final String PATH_SEPARATOR = "/";
    private static final String REPOSITORYNAME = "&repositoryName=";
    private static final String TYPE = "&type=";
    private static final String VERSIONNAME = "&versionName=";
    private String type;
    /** Creates new form SelectVersionToOpen */
    public SelectVersionToOpen(String type)
    {
        initComponents();
        this.type=type;
        ListSelectionModel listSelectionModel = jTableVersion.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener()
        {

            public void valueChanged(ListSelectionEvent e)
            {
                jButtonView.setEnabled(false);
                if (e.getFirstIndex() != -1)
                {
                    jButtonView.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void renderingPage()
    {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        ContentInfo content = (ContentInfo) Search.map.get(Search.CONTENT);
        DefaultTableModel model = (DefaultTableModel) this.jTableVersion.getModel();
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            model.removeRow(0);
        }
        try
        {
            String workspace = Search.map.get(Search.WORKSPACE).toString();
            for (VersionInfo info : OfficeApplication.getOfficeDocumentProxy().getVersions(workspace, content.id))
            {
                String date = OfficeApplication.iso8601dateFormat.format(info.created);
                Object[] value =
                {
                    content, content.descripcion, info, date
                };
                model.addRow(value);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public static String getDescription()
    {
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectVersionToOpen").getString("VERSIÓN_DE_CONTENIDO");
    }

    @Override
    public WizardPanelNavResult allowNext(String arg, Map map, Wizard wizard)
    {
        WizardPanelNavResult result = WizardPanelNavResult.PROCEED;
        if (this.jTableVersion.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectVersionToOpen").getString("!DEBE_INDICAR_UNA_VERSIÓN!"), SelectVersionToOpen.getDescription(), JOptionPane.ERROR_MESSAGE);
            this.jTableVersion.requestFocus();
            result = WizardPanelNavResult.REMAIN_ON_PAGE;
        }
        else
        {
            Object selection = this.jTableVersion.getModel().getValueAt(this.jTableVersion.getSelectedRow(), 2);
            map.put(VERSION, selection);
        }
        return result;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVersion = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonView = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jTableVersion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Titulo", "Descripción", "Version", "Fecha de creación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableVersion);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 25));

        jButtonView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/see.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectVersionToOpen"); // NOI18N
        jButtonView.setToolTipText(bundle.getString("VER_CONTENIDO")); // NOI18N
        jButtonView.setEnabled(false);
        jButtonView.setFocusable(false);
        jButtonView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonView);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

private void jButtonViewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonViewActionPerformed
{//GEN-HEADEREND:event_jButtonViewActionPerformed
    if (Search.map.get(Search.WORKSPACE) != null && jTableVersion.getSelectedRow() != -1)
    {
        String workspace = Search.map.get(Search.WORKSPACE).toString();
        DefaultTableModel model = (DefaultTableModel) jTableVersion.getModel();

        VersionInfo version = (VersionInfo) model.getValueAt(jTableVersion.getSelectedRow(), 2);
        String name = null;
        try
        {            
            name = OfficeApplication.getOfficeDocumentProxy().createPreview(workspace, version.contentId, version.nameOfVersion,type);
            String urlproxy = OfficeApplication.getOfficeApplicationProxy().getWebAddress().toString();
            if (!urlproxy.endsWith(PATH_SEPARATOR+GTW))
            {
                if (!urlproxy.endsWith(PATH_SEPARATOR))
                {
                    urlproxy += PATH_SEPARATOR;
                }
                if (!urlproxy.endsWith(GTW))
                {
                    urlproxy += GTW;
                }
            }
            URL url = new URL(urlproxy + CONTENTID + version.contentId + VERSIONNAME + version.nameOfVersion + REPOSITORYNAME + workspace + NAME + name+TYPE+type);
            String title = OfficeApplication.getOfficeDocumentProxy().getTitle(workspace, version.contentId) + " (" + version.nameOfVersion + ") ";
            DialogPreview dialogPreview = new DialogPreview(url, false, title);
            dialogPreview.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (name != null)
            {
                try
                {
                    OfficeApplication.getOfficeDocumentProxy().deletePreview(name);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}//GEN-LAST:event_jButtonViewActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonView;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableVersion;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
