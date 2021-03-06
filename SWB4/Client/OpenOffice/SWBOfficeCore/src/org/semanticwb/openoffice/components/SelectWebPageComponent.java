/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SelectWebPageComponent.java
 *
 * Created on 3/09/2010, 10:38:14 AM
 */
package org.semanticwb.openoffice.components;

import java.awt.Cursor;
import java.util.HashSet;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class SelectWebPageComponent extends javax.swing.JPanel implements NodeEvent
{
    WebPage selected;
    private HashSet<NodeEvent> events = new HashSet<NodeEvent>();
    private boolean showTool;
    private String site;

    /** Creates new form SelectWebPageComponent */
    public SelectWebPageComponent(boolean showTool)
    {
        initComponents();
        this.showTool = showTool;
    }

    public SelectWebPageComponent(boolean showTool, String site)
    {
        initComponents();
        this.showTool = showTool;
        this.site=site;
    }

    public void init()
    {
        DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
        selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTreeSite.setSelectionModel(selectionModel);
        SelectWebPageModel model=new SelectWebPageModel(site);
        model.addAddNodeListener(this);
        jTreeSite.setModel(model);
        jTreeSite.setCellRenderer(new TreeRender());
        jTreeSite.setRootVisible(false);
        if (!showTool) {
            this.jPanel2.setVisible(showTool);
        }
        this.updateUI();
    }
    public WebPage getSelectedWebPage()
    {
        return selected;
    }
    public void addAddNodeListener(NodeEvent event)
    {
        events.add(event);
    }

    public void selectNode(DefaultMutableTreeNode node)
    {
        for (NodeEvent event : events) {
            event.selectNode(node);
        }
    }

    public void addNode(DefaultMutableTreeNode node)
    {
        for (NodeEvent event : events) {
            event.addNode(node);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAddPage = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeSite = new javax.swing.JTree();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(50, 100));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButtonAddPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/icon_agregarpag.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectPage"); // NOI18N
        jButtonAddPage.setToolTipText(bundle.getString("AGREGAR_PÁGINA_AL_SITIO_WEB")); // NOI18N
        jButtonAddPage.setEnabled(false);
        jButtonAddPage.setFocusable(false);
        jButtonAddPage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAddPage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonAddPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddPageActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAddPage);

        jPanel2.add(jToolBar1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jTreeSite.setAutoscrolls(true);
        jTreeSite.setRootVisible(false);
        jTreeSite.setRowHeight(20);
        jTreeSite.setShowsRootHandles(true);
        jTreeSite.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                jTreeSiteTreeWillExpand(evt);
            }
        });
        jTreeSite.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeSiteValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeSite);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddPageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddPageActionPerformed
    {//GEN-HEADEREND:event_jButtonAddPageActionPerformed
        if (showTool) {
            Object selectedObject = this.jTreeSite.getSelectionPath().getLastPathComponent();
            if (selectedObject != null && selectedObject instanceof WebPage) {
                WebPage siteNode = (WebPage) selectedObject;
                OfficeApplication.createPage(siteNode.getWebPageInfo());
                siteNode.removeAllChildren();
                siteNode.reLoadChilds();
                jTreeSite.expandPath(this.jTreeSite.getSelectionPath());
                jTreeSite.updateUI();
            }
        }
}//GEN-LAST:event_jButtonAddPageActionPerformed

    private void jTreeSiteTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException//GEN-FIRST:event_jTreeSiteTreeWillExpand
    {//GEN-HEADEREND:event_jTreeSiteTreeWillExpand
        Object selectedObject = evt.getPath().getLastPathComponent();
        if (selectedObject instanceof Site && ((Site) selectedObject).getChildCount() == 1 && !(((Site) selectedObject).getChildAt(0) instanceof WebPage))
        {
            Site siteNode=(Site)selectedObject;
            siteNode.loadHome();
            jTreeSite.expandPath(this.jTreeSite.getSelectionPath());
            jTreeSite.updateUI();
        }
        if (selectedObject instanceof WebPage && ((WebPage) selectedObject).getChildCount() == 1 && !(((WebPage) selectedObject).getChildAt(0) instanceof WebPage)) {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                WebPage node = (WebPage) selectedObject;
                node.loadChilds();
                jTreeSite.expandPath(this.jTreeSite.getSelectionPath());
                jTreeSite.updateUI();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
}//GEN-LAST:event_jTreeSiteTreeWillExpand

    private void jTreeSiteValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_jTreeSiteValueChanged
    {//GEN-HEADEREND:event_jTreeSiteValueChanged
        Object selectedObject = evt.getPath().getLastPathComponent();
        if (selectedObject instanceof WebPage)
        {
            selected=(WebPage)selectedObject;
        }
        if (selectedObject instanceof DefaultMutableTreeNode)
        {
            for(NodeEvent event : events)
            {
                event.selectNode((DefaultMutableTreeNode)selected);
            }
        }
        
    }//GEN-LAST:event_jTreeSiteValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddPage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTreeSite;
    // End of variables declaration//GEN-END:variables
}
