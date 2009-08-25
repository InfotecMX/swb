/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * Relation.java
 *
 * Created on 18 de febrero de 2002, 18:13
 */

package applets.mapsadm;

import java.awt.*;
import java.applet.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

/**
 *
 * @author  Administrador
 */
public class SelectRelation extends javax.swing.JFrame {

    private Hashtable obj;
    private Vector objSort;
    private AppObject topic;
    private javax.swing.table.DefaultTableModel table;
    
    private boolean edit=false;
    private boolean adding=false;
    private int edtRow;
    private Locale locale=Locale.getDefault();
    
    /** Creates new form Relation */
    public SelectRelation(Locale locale) {
        this.locale=locale;
        initComponents();
        
        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                formComponentResized(evt);
            }
        });
        
        this.setSize(450,210);
        this.move(400,400);
        setResizable(true);
    }

    public SelectRelation(Hashtable obj,Vector objSort,AppObject topic,javax.swing.table.DefaultTableModel table,Locale locale) {
        this(locale);
        this.obj=obj;
        this.objSort=objSort;
        this.topic=topic;
        this.table=table;
        initCombos();
    }
    
    public void edit(int row)
    {
        edtRow=row;
        edit=true;
        jComboBox1.setSelectedItem(table.getValueAt(row,0));
        jComboBox2.setSelectedItem(table.getValueAt(row,1));
        jComboBox3.setSelectedItem(table.getValueAt(row,2));
        jComboBox4.setSelectedItem(table.getValueAt(row,3));
    }
    
    public void setParams(Hashtable obj,Vector objSort,AppObject topic,javax.swing.table.DefaultTableModel table)
    {
        this.obj=obj;
        this.objSort=objSort;
        this.topic=topic;
        this.table=table;
        initCombos();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        
        getContentPane().setLayout(null);
        
        setTitle(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.title"));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        jButton1.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("AceptarfrmRelation.btn.accept"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        getContentPane().add(jButton1);
        jButton1.setBounds(110, 130, 90, 26);
        
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(150, 10, 280, 25);
        
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        
        getContentPane().add(jComboBox2);
        jComboBox2.setBounds(150, 40, 280, 25);
        
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.lbl.type"));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 10, 110, 16);
        
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.lbl.role"));
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 40,110, 16);
        
        jButton2.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.btn.cancel"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        getContentPane().add(jButton2);
        jButton2.setBounds(250, 130, 85, 26);
        
        getContentPane().add(jComboBox3);
        jComboBox3.setBounds(150, 70, 280, 25);
        
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.lbl.related"));
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 70, 110, 16);
        
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        
        getContentPane().add(jComboBox4);
        jComboBox4.setBounds(150, 100, 280, 25);
        
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.lbl.relatedRole"));
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 100, 110, 16);
        
        pack();
    }//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt)
    {
        //450
        //280
        //110,130
        //250,130
        int dif=(int)evt.getComponent().getSize().getWidth()-450;
        jComboBox1.setSize(280+dif, 25);
        jComboBox2.setSize(280+dif, 25);
        jComboBox3.setSize(280+dif, 25);
        jComboBox4.setSize(280+dif, 25);
        jButton1.setLocation(110+dif/2,130);
        jButton2.setLocation(250+dif/2,130);
        jComboBox1.updateUI();
        jComboBox2.updateUI();
        jComboBox3.updateUI();
        jComboBox4.updateUI();
        jButton1.updateUI();
        jButton2.updateUI();
        // TODO add your handling code here:
        //System.out.println(evt);
    }    
    
    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // Add your handling code here:
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // Add your handling code here:
        if(evt.getActionCommand().equals("comboBoxChanged")&&!adding)
        {
            if(jComboBox4.getSelectedItem().equals(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.others")))
            {
                jComboBox4.removeItem(jComboBox4.getSelectedItem());
                Enumeration it=objSort.elements();
                while(it.hasMoreElements())
                {
                    Object obj=it.nextElement();
                    jComboBox4.addItem(obj);
                }
            }
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // Add your handling code here:
        if(evt.getActionCommand().equals("comboBoxChanged")&&!adding)
        {
            if(jComboBox2.getSelectedItem().equals(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.others")))
            {
                jComboBox2.removeItem(jComboBox2.getSelectedItem());
                Enumeration it=objSort.elements();
                while(it.hasMoreElements())
                {
                    Object obj=it.nextElement();
                    jComboBox2.addItem(obj);
                }
            }
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // Add your handling code here:
        
        if(evt.getActionCommand().equals("comboBoxChanged"))
        {
            if(jComboBox1.getSelectedItem().equals(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.others")))
            {
                jComboBox1.removeItem(jComboBox1.getSelectedItem());
                Enumeration it=objSort.elements();
                while(it.hasMoreElements())
                {
                    Object obj=it.nextElement();
                    jComboBox1.addItem(obj);
                    //jComboBox2.addItem(obj);
                    //jComboBox4.addItem(obj);
                }
                return;
            }
            AppObject auxsel=(AppObject)jComboBox1.getSelectedItem();
            

            Vector auxtp=new Vector();
            Enumeration it=obj.elements();
            while(it.hasMoreElements())
            {
                AppObject auxobj=(AppObject)it.nextElement();
                Enumeration ass=auxobj.assoc.elements();
                while(ass.hasMoreElements())
                {
                    AppAssoc auxass=(AppAssoc)ass.nextElement();
                    if(auxass.idtype.length()>0)
                    {
                        AppObject tp=(AppObject)obj.get(auxass.idtype);
                        if(tp==auxsel)
                        {
                            if(auxass.role.length()>0)
                            {
                                AppObject ro=(AppObject)obj.get(auxass.role);
                                if(!auxtp.contains(ro))
                                {
                                    auxtp.add(ro);
                                }
                            }
                        }
                    }
                }
            }
            
            adding=true;
            jComboBox2.removeAllItems();
            jComboBox4.removeAllItems();
            if(auxtp.size()>0)
            {
                it=auxtp.elements();
                while(it.hasMoreElements())
                {
                    Object obj=it.nextElement();
                    jComboBox2.addItem(obj);
                    jComboBox4.addItem(obj);
                }
                jComboBox2.addItem(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.others"));
                jComboBox4.addItem(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.others"));
            }
            else
            {
                it=objSort.elements();
                while(it.hasMoreElements())
                {
                    Object obj=it.nextElement();
                    jComboBox2.addItem(obj);
                    jComboBox4.addItem(obj);
                }
            }
            adding=false;
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!edit)
        {
            table.addRow(new Object[]{jComboBox1.getSelectedItem(),jComboBox2.getSelectedItem(),jComboBox3.getSelectedItem(),jComboBox4.getSelectedItem()});
        }
        else
        {
            table.setValueAt(jComboBox1.getSelectedItem(),edtRow,0);
            table.setValueAt(jComboBox2.getSelectedItem(),edtRow,1);
            table.setValueAt(jComboBox3.getSelectedItem(),edtRow,2);
            table.setValueAt(jComboBox4.getSelectedItem(),edtRow,3);
        }
        dispose();
        //this.hide();
        // Add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.hide();
        dispose();
        //System.exit(0);
        // Add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        this.hide();
        dispose();
        //System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        new SelectRelation(Locale.getDefault()).show();
    }
    
    public void initCombos()
    {
        edit=false;
        Vector auxtp=new Vector();
        Enumeration it=obj.elements();
        while(it.hasMoreElements())
        {
            AppObject auxobj=(AppObject)it.nextElement();
            Enumeration ass=auxobj.assoc.elements();
            while(ass.hasMoreElements())
            {
                AppAssoc auxass=(AppAssoc)ass.nextElement();
                if(auxass.idtype.length()>0)
                {
                    AppObject tp=(AppObject)obj.get(auxass.idtype);
                    if(!auxtp.contains(tp))
                    {
                        auxtp.add(tp);
                    }
                }
            }
        }

        jComboBox1.removeAllItems();
        it=auxtp.elements();
        while(it.hasMoreElements())
        {
            jComboBox1.addItem(it.nextElement());
        }
        jComboBox1.addItem(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("frmRelation.others"));
        
        jComboBox3.removeAllItems();
        it=objSort.elements();
        while(it.hasMoreElements())
        {
            jComboBox3.addItem(it.nextElement());
        }
        jComboBox3.setSelectedItem(topic);
        
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables

}
