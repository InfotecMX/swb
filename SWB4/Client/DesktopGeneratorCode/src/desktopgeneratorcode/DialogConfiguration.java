/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogConfiguration.java
 *
 * Created on 29/09/2009, 04:35:21 PM
 */
package desktopgeneratorcode;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.util.HashSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author victor.lorenzana
 */
public class DialogConfiguration extends javax.swing.JDialog
{

    private HashSet<OWL> owlsBaseCommons = new HashSet<OWL>();
    private HashSet<OWL> owlsProyect = new HashSet<OWL>();
    private JFileChooser filechooser;
    BaseReferencesConfiguration config = new BaseReferencesConfiguration();

    /** Creates new form DialogConfiguration */
    public DialogConfiguration(java.awt.Frame parent, JFileChooser filechooser)
    {
        super(parent, true);
        initComponents();
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.filechooser = filechooser;
        this.setLocationRelativeTo(null);
        try
        {
            for (String key : config.getKeys())
            {
                File file=new File(config.get(key));
                if(file.exists())
                {
                    OWL owl = new OWL(file);
                    owlsBaseCommons.add(owl);
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"El archivo "+file.getAbsolutePath()+" no se encontró, este archivo esta definido como un archivo común para la generacion de proyectos", "Error", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
                }


            }
        }
        catch (Exception ge)
        {
            JOptionPane.showMessageDialog(this, ge.getMessage(), "Error", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
        }
        loadBaseCommonsOWL();
    }

    

    public OWL[] getOWLBaseCommons()
    {
        return owlsBaseCommons.toArray(new OWL[owlsBaseCommons.size()]);
    }

    public OWL[] getOWLBaseProyect()
    {
        return owlsProyect.toArray(new OWL[owlsProyect.size()]);
    }

    public void setOWLBaseProyect(OWL[] owls)
    {
        owlsProyect = new HashSet<OWL>();
        for (OWL owl : owls)
        {
            owlsProyect.add(owl);
        }
        loadProyectOWL();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jPanelButtons = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jPanelCenter = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelOWLS = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelCommons = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanelProyect = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopgeneratorcode.DesktopGeneratorCodeApp.class).getContext().getResourceMap(DialogConfiguration.class);
        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setModal(true);
        setName("Form"); // NOI18N
        setResizable(false);

        jPanelButtons.setName("jPanelButtons"); // NOI18N
        jPanelButtons.setPreferredSize(new java.awt.Dimension(400, 40));

        jButtonCancel.setText(resourceMap.getString("jButtonCancel.text")); // NOI18N
        jButtonCancel.setName("jButtonCancel"); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelButtonsLayout = new org.jdesktop.layout.GroupLayout(jPanelButtons);
        jPanelButtons.setLayout(jPanelButtonsLayout);
        jPanelButtonsLayout.setHorizontalGroup(
            jPanelButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap(338, Short.MAX_VALUE)
                .add(jButtonCancel)
                .addContainerGap())
        );
        jPanelButtonsLayout.setVerticalGroup(
            jPanelButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jButtonCancel)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelButtons, java.awt.BorderLayout.SOUTH);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 30));

        jButtonAdd.setIcon(resourceMap.getIcon("jButtonAdd.icon")); // NOI18N
        jButtonAdd.setText(resourceMap.getString("jButtonAdd.text")); // NOI18N
        jButtonAdd.setToolTipText(resourceMap.getString("jButtonAdd.toolTipText")); // NOI18N
        jButtonAdd.setFocusable(false);
        jButtonAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAdd.setName("jButtonAdd"); // NOI18N
        jButtonAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAdd);

        jButtonRemove.setIcon(resourceMap.getIcon("jButtonRemove.icon")); // NOI18N
        jButtonRemove.setText(resourceMap.getString("jButtonRemove.text")); // NOI18N
        jButtonRemove.setToolTipText(resourceMap.getString("jButtonRemove.toolTipText")); // NOI18N
        jButtonRemove.setFocusable(false);
        jButtonRemove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRemove.setName("jButtonRemove"); // NOI18N
        jButtonRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonRemove);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jPanelCenter.setName("jPanelCenter"); // NOI18N
        jPanelCenter.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanelOWLS.setName("jPanelOWLS"); // NOI18N
        jPanelOWLS.setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanelCommons.setName("jPanelCommons"); // NOI18N
        jPanelCommons.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OWL", "Ruta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N

        jPanelCommons.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(resourceMap.getString("jPanelCommons.TabConstraints.tabTitle"), jPanelCommons); // NOI18N

        jPanelProyect.setName("jPanelProyect"); // NOI18N
        jPanelProyect.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OWL", "Ruta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setName("jTable2"); // NOI18N
        jScrollPane3.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable2.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N

        jPanelProyect.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("OWL del proyecto", jPanelProyect);

        jPanelOWLS.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setTopComponent(jPanelOWLS);

        jPanel4.setMinimumSize(new java.awt.Dimension(23, 100));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(409, 100));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jTextArea1.setMinimumSize(new java.awt.Dimension(0, 100));
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel4);

        jPanelCenter.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddActionPerformed
    {//GEN-HEADEREND:event_jButtonAddActionPerformed

        filechooser.addChoosableFileFilter(new FileFilter()
        {

            @Override
            public boolean accept(File f)
            {
                return f.getName().endsWith(".owl") || f.isDirectory();
            }

            @Override
            public String getDescription()
            {
                return "Archivo de Ontologia (*.owl)";
            }
        });
        filechooser.setDialogTitle("Agregar archivo de ontologia");
        filechooser.setMultiSelectionEnabled(true);
        int res = filechooser.showDialog(this, "Agregar");
        if (res == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
        if (jTabbedPane1.getSelectedIndex() == 0)
        {
            for (File file : filechooser.getSelectedFiles())
            {
                try
                {
                    OWL owl = new OWL(file);
                    if (!owlsBaseCommons.contains(owl))
                    {
                        this.owlsBaseCommons.add(owl);
                    }
                }
                catch (GenerationException ge)
                {
                    JOptionPane.showMessageDialog(this, ge.getMessage(), "Error", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                }
            }
            loadBaseCommonsOWL();
        }
        if (jTabbedPane1.getSelectedIndex() == 1)
        {
            for (File file : filechooser.getSelectedFiles())
            {
                try
                {
                    OWL owl = new OWL(file);
                    if (!owlsProyect.contains(owl))
                    {
                        this.owlsProyect.add(owl);
                    }
                }
                catch (GenerationException ge)
                {
                    JOptionPane.showMessageDialog(this, ge.getMessage(), "Error", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                }
            }
            loadProyectOWL();
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        if (!this.jTextArea1.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Existen errores en la salida, favor de corregirlos", "Error al guardar", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (OWL owl : owlsBaseCommons)
        {
            config.add(owl.getLocation(), owl.getLocation());
        }
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRemoveActionPerformed
    {//GEN-HEADEREND:event_jButtonRemoveActionPerformed
        if(this.jTabbedPane1.getSelectedIndex()==0)
        {
            DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
            if(jTable1.getSelectedRow()!=-1)
            {
                OWL owl=(OWL)model.getValueAt(jTable1.getSelectedRow(), 0);
                int res=JOptionPane.showConfirmDialog(this, "¿Desea eliminar el owl "+ owl +"?","Eliminar OWL",JOptionPane.YES_NO_OPTION);
                if(res==JOptionPane.YES_OPTION)
                {
                    owlsBaseCommons.remove(owl);
                    loadBaseCommonsOWL();
                }
            }
        }
        else
        {
            DefaultTableModel model = (DefaultTableModel) this.jTable2.getModel();
            if(jTable2.getSelectedRow()!=-1)
            {
                OWL owl=(OWL)model.getValueAt(jTable2.getSelectedRow(), 0);
                int res=JOptionPane.showConfirmDialog(this, "¿Desea eliminar el owl "+ owl +"?","Eliminar OWL",JOptionPane.YES_NO_OPTION);
                if(res==JOptionPane.YES_OPTION)
                {
                    owlsProyect.remove(owl);
                }
                loadProyectOWL();
            }
        }
    }//GEN-LAST:event_jButtonRemoveActionPerformed
    private void loadBaseCommonsOWL()
    {
        this.jTextArea1.setText("");
        DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            model.removeRow(0);
        }
        for (OWL owl : owlsBaseCommons)
        {
            Object[] data =
            {
                owl, owl.getLocation()
            };
            model.addRow(data);
            for (String namespace : owl.getRequiredNamespaces())
            {
                boolean foundRequiredNamespace = false;
                for (OWL owlbase : owlsBaseCommons)
                {
                    if (owlbase.getNamespace() != null && owlbase.getNamespace().equals(namespace))
                    {
                        foundRequiredNamespace = true;
                        break;
                    }
                }
                for (OWL owlbase : owlsProyect)
                {
                    if (owlbase.getNamespace() != null && owlbase.getNamespace().equals(namespace))
                    {
                        foundRequiredNamespace = true;
                        break;
                    }
                }
                if (!foundRequiredNamespace)
                {
                    this.jTextArea1.append(owl.getName() + ": No se encontro el archio owl para el espacio de nombres " + namespace + "\r\n");
                }
            }
        }
    }

    private void loadProyectOWL()
    {
        this.jTextArea1.setText("");
        DefaultTableModel model = (DefaultTableModel) this.jTable2.getModel();
        int rows = model.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            model.removeRow(0);
        }
        for (OWL owl : owlsProyect)
        {
            for (String namespace : owl.getRequiredNamespaces())
            {
                boolean foundRequiredNamespace = false;
                for (OWL owlbase : owlsBaseCommons)
                {
                    if (owlbase.getNamespace() != null && owlbase.getNamespace().equals(namespace))
                    {
                        foundRequiredNamespace = true;
                        break;
                    }
                }
                for (OWL owlbase : owlsProyect)
                {
                    if (owlbase.getNamespace() != null && owlbase.getNamespace().equals(namespace))
                    {
                        foundRequiredNamespace = true;
                        break;
                    }
                }
                if (!foundRequiredNamespace)
                {
                    this.jTextArea1.append(owl.getName() + ": No se encontro el archio owl para el espacio de nombres " + namespace + "\r\n");
                }
            }
            Object[] data =
            {
                owl, owl.getLocation()
            };
            model.addRow(data);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelCommons;
    private javax.swing.JPanel jPanelOWLS;
    private javax.swing.JPanel jPanelProyect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
