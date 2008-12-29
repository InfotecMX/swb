/*
 * SummaryPublish.java
 *
 * Created on 3 de junio de 2008, 03:58 PM
 */
package org.semanticwb.openoffice.ui.wizard;

import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author  victor.lorenzana
 */
public class SummaryPublish extends javax.swing.JPanel
{

    
    
    public SummaryPublish()
    {
        initComponents();
    }
    /** Creates new form SummaryPublish */
    public SummaryPublish(String contentId, String repositoryName)
    {
        this();
        loadVersions(contentId,repositoryName);
    }
    public void loadVersions(String contentId, String repositoryName)
    {        
        try
        {            
            DefaultTableModel model=(DefaultTableModel)jTableSummary1.getModel(); 
            int rows=model.getRowCount();
            for(int i=1;i<=rows;i++)
            {
                model.removeRow(0);
            }
            IOfficeDocument document = OfficeApplication.getOfficeDocumentProxy();
            for (VersionInfo versionInfo : document.getVersions(repositoryName, contentId))
            {
                String date=OfficeApplication.iso8601dateFormat.format(versionInfo.created);
                String[] rowData={versionInfo.nameOfVersion,date,versionInfo.user};
                model.addRow(rowData);
            }
        }
        catch (Exception e)
        {
        }
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelPreview = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableSummary1 = new javax.swing.JTable();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanelPreview.setLayout(new javax.swing.BoxLayout(jPanelPreview, javax.swing.BoxLayout.LINE_AXIS));

        jTableSummary1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Versión", "Fecha de creación", "Creador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSummary1.setFocusable(false);
        jTableSummary1.setRowSelectionAllowed(false);
        jScrollPane3.setViewportView(jTableSummary1);

        jPanelPreview.add(jScrollPane3);

        jTabbedPane1.addTab("Versiones Existentes", jPanelPreview);

        add(jTabbedPane1);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelPreview;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableSummary1;
    // End of variables declaration//GEN-END:variables
}
