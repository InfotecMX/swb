/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gesti?n de procesos de negocio mediante el uso de 
* tecnolog?a sem?ntica, que permite el modelado, configuraci?n, ejecuci?n y monitoreo de los procesos de negocio
* de una organizaci?n, as? como el desarrollo de componentes y aplicaciones orientadas a la gesti?n de procesos.
* 
* Mediante el uso de tecnolog?a sem?ntica, SemanticWebBuilder Process puede generar contextos de informaci?n
* alrededor de alg?n tema de inter?s o bien integrar informaci?n y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la informaci?n se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creaci?n original del Fondo de 
* Informaci?n y Documentaci?n para la Industria INFOTEC.
* 
* INFOTEC pone a su disposici?n la herramienta SemanticWebBuilder Process a trav?s de su licenciamiento abierto 
* al p?blico (?open source?), en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC 
* lo ha dise?ado y puesto a su disposici?n; aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los t?rminos y 
* condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garant?a sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni impl?cita ni 
* expl?cita, siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposici?n la
* siguiente direcci?n electr?nica: 
*  http://www.semanticwebbuilder.org.mx
**/
 


/*
 * Search.java
 *
 * Created on 10 de enero de 2005, 04:22 PM
 */

package applets.htmleditor;

import java.util.Locale;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class Replace extends javax.swing.JDialog
{
    
    int ret=0;
    static boolean mcase=false;
    static boolean mwhole=false;
    static String replace="";
    
    Object editor=null;
    
    Locale locale=new Locale("es");     
    
    /** Creates new form Search */
    public Replace(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
        //setSize(430,150);
        setLocation(200,200);        
        jTextField1.setText("");
        setTitle("Find");
        //setFocusableWindowState(false);
        setFocusable(false);
        jCheckBox1.setSelected(mcase);
        jCheckBox2.setSelected(mwhole);
        jTextField2.setText(replace);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        butFind = new javax.swing.JButton();
        butClose = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        butReplace = new javax.swing.JButton();
        butReplaceAll = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Webbuilder");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("findwhat"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jLabel1, gridBagConstraints);

        jTextField1.setText("jTextField1");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                jTextField1KeyTyped(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 7, 0);
        getContentPane().add(jTextField1, gridBagConstraints);

        butFind.setFont(new java.awt.Font("Dialog", 0, 12));
        butFind.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("find"));
        butFind.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                butFindActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(butFind, gridBagConstraints);

        butClose.setFont(new java.awt.Font("Dialog", 0, 12));
        butClose.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("close"));
        butClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                butCloseActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 5, 5);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(butClose, gridBagConstraints);

        jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 11));
        jCheckBox1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("matchCase"));
        jCheckBox1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jCheckBox1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCheckBox1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(jCheckBox1, gridBagConstraints);

        jCheckBox2.setFont(new java.awt.Font("Dialog", 0, 11));
        jCheckBox2.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("WholeWords"));
        jCheckBox2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jCheckBox2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCheckBox2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(jCheckBox2, gridBagConstraints);

        butReplace.setFont(new java.awt.Font("Dialog", 0, 12));
        butReplace.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("replace"));
        butReplace.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                butReplaceActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(butReplace, gridBagConstraints);

        butReplaceAll.setFont(new java.awt.Font("Dialog", 0, 12));
        butReplaceAll.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("replaceAll"));
        butReplaceAll.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                butReplaceAllActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(butReplaceAll, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel2.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("replaceWith"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jLabel2, gridBagConstraints);

        jTextField2.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 7, 0);
        getContentPane().add(jTextField2, gridBagConstraints);

        pack();
    }//GEN-END:initComponents

    private void butReplaceAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butReplaceAllActionPerformed
    {//GEN-HEADEREND:event_butReplaceAllActionPerformed
        // Add your handling code here:
        if(editor instanceof TemplateEditor)
        {
            ((TemplateEditor)editor).goInit();
            while(((TemplateEditor)editor).findStr(getText(),mcase,mwhole))
            {
                ((TemplateEditor)editor).replaceStr(getReplaceText());
            }
        }
        ret=0;
        setVisible(false);        
        dispose();
    }//GEN-LAST:event_butReplaceAllActionPerformed

    private void butReplaceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butReplaceActionPerformed
    {//GEN-HEADEREND:event_butReplaceActionPerformed
        // Add your handling code here:
        if(editor instanceof TemplateEditor)
        {
            ((TemplateEditor)editor).replaceStr(getReplaceText());
            ((TemplateEditor)editor).findStr(getText(),mcase,mwhole);
        }
    }//GEN-LAST:event_butReplaceActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextField1KeyTyped
    {//GEN-HEADEREND:event_jTextField1KeyTyped
        // Add your handling code here:
        if(evt.getKeyChar()=='\n')
        {
            submit();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBox2ActionPerformed
    {//GEN-HEADEREND:event_jCheckBox2ActionPerformed
        // Add your handling code here:
        mwhole=jCheckBox2.isSelected();        
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBox1ActionPerformed
    {//GEN-HEADEREND:event_jCheckBox1ActionPerformed
        // Add your handling code here:
        mcase=jCheckBox1.isSelected();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void butFindActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butFindActionPerformed
    {//GEN-HEADEREND:event_butFindActionPerformed
        // Add your handling code here:
        if(editor instanceof TemplateEditor)
        {
            ((TemplateEditor)editor).findStr(getText(),mcase,mwhole);
        }
    }//GEN-LAST:event_butFindActionPerformed

    private void submit()
    {
        // Add your handling code here:
        if(editor instanceof TemplateEditor)
        {
            ((TemplateEditor)editor).findStr(getText(),mcase,mwhole);
        }
        ret=0;
        setVisible(false);        
        dispose();
    }
    
    
    private void butCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butCloseActionPerformed
    {//GEN-HEADEREND:event_butCloseActionPerformed
        // Add your handling code here:
        replace=jTextField2.getText();
        setVisible(false);
        dispose();    
        ret=2;
    }//GEN-LAST:event_butCloseActionPerformed
    
    public int returnCode()
    {
        return ret;
    }
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt)//GEN-FIRST:event_closeDialog
    {
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    public void setText(String text)
    {
        jTextField1.setText(text);
    }
    
    public String getText()
    {
        return jTextField1.getText();
    }
    
    public String getReplaceText()
    {
        return jTextField2.getText();
    }
    
    
    public boolean matchCase()
    {
        return jCheckBox1.isSelected();
    }
    
    public boolean matchWhole()
    {
        return jCheckBox2.isSelected();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        new Replace(new javax.swing.JFrame(), true).show();
    }
    
    /**
     * Getter for property editor.
     * @return Value of property editor.
     */
    public java.lang.Object getEditor()
    {
        return editor;
    }    
    
    /**
     * Setter for property editor.
     * @param editor New value of property editor.
     */
    public void setEditor(java.lang.Object editor)
    {
        this.editor = editor;
    }
    
    /**
     * Getter for property locale.
     * @return Value of property locale.
     */
    public Locale getLocale()
    {
        return locale;
    }
    
    /**
     * Setter for property locale.
     * @param locale New value of property locale.
     */
    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butClose;
    private javax.swing.JButton butFind;
    private javax.swing.JButton butReplace;
    private javax.swing.JButton butReplaceAll;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
    
}
