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

package org.semanticwb.openoffice.ui.wizard;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.netbeans.spi.wizard.WizardPage;
import org.semanticwb.office.interfaces.LanguageInfo;

/**
 *
 * @author victor.lorenzana
 */
public class LanguageEditor extends WizardPage {

    LanguageInfo[] languages;
    /** Creates new form LanguageEditor */
    public LanguageEditor() {
        initComponents();
    }

    public void setLenguages(LanguageInfo[] languages)
    {
        this.languages=languages;
        DefaultTableModel model=(DefaultTableModel)this.jTableLenguages.getModel();
        int rows=model.getRowCount();
        for(int i=0;i<rows;i++)
        {
            model.removeRow(0);
        }
        for(LanguageInfo language :  languages)
        {
            Object[] data=new Object[]{language,""};
            model.addRow(data);
        }
    }
    public LanguageInfo[] getLanguages()
    {
        return languages;
    }
    public String[] getTitles()
    {
        ArrayList<String> values=new ArrayList<String>();
        DefaultTableModel model=(DefaultTableModel)this.jTableLenguages.getModel();
        int rows=model.getRowCount();
        for(int i=0;i<rows;i++)
        {
            Object value=model.getValueAt(i,1);
            values.add(value.toString());
        }
        return values.toArray(new String[values.size()]);
    }
    public void setLenguages(LanguageInfo[] lenguages,String[] titles)
    {
        DefaultTableModel model=(DefaultTableModel)this.jTableLenguages.getModel();
        this.setLenguages(lenguages);
        int irow=0;
        for(String title : titles)
        {
            model.setValueAt(title, irow, 1);
            irow++;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLenguages = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jTableLenguages.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lenguaje", "Titulo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableLenguages);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableLenguages;
    // End of variables declaration//GEN-END:variables

}
