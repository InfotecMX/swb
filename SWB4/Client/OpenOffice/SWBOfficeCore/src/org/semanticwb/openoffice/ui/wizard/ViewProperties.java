/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ViewProperties.java
 *
 * Created on 10/02/2009, 06:37:12 PM
 */
package org.semanticwb.openoffice.ui.wizard;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class ViewProperties extends WizardPage
{

    public static final String VIEW_PROPERTIES = "PROPERTIES";
    private String repositoryName,  contentID;
    private final JLabel label = new JLabel("No se tienen propiedades para este tipo de contenido, puede continuar.");
    private final JPanel panel = new JPanel();

    public ViewProperties(String repositoryName, String contentID)
    {
        this.repositoryName = repositoryName;
        this.contentID = contentID;
        initComponents();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        loadProperties();
    }

    @Override
    public WizardPanelNavResult allowNext(String arg, Map map, Wizard wizard)
    {
        return allowFinish(arg, map, wizard);
    }

    @Override
    public WizardPanelNavResult allowFinish(String arg, Map map, Wizard wizard)
    {
        WizardPanelNavResult result = WizardPanelNavResult.PROCEED;
        Map<PropertyInfo, String> properties = this.panelPropertyEditor1.getProperties();
        for (PropertyInfo prop : this.panelPropertyEditor1.getProperties().keySet())
        {

            String value = this.panelPropertyEditor1.getProperties().get(prop);
            if (value.isEmpty() && prop.isRequired)
            {
                JOptionPane.showMessageDialog(this, "¡Debe indicar " + prop + "!", getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                return WizardPanelNavResult.REMAIN_ON_PAGE;
            }
        }
        PropertyInfo[] props = new PropertyInfo[properties.keySet().size()];
        Object[] values = new Object[properties.keySet().size()];
        int i = 0;
        for (PropertyInfo prop : properties.keySet())
        {
            Object value = properties.get(prop);
            props[i] = prop;
            values[i] = value;
            i++;
        }
        try
        {
            OfficeApplication.getOfficeDocumentProxy().validateViewValues(repositoryName, contentID, props, values);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return WizardPanelNavResult.REMAIN_ON_PAGE;
        }
        map.put(VIEW_PROPERTIES, properties);
        return result;
    }

    private void loadProperties()
    {
        this.remove(this.panelPropertyEditor1);
        this.remove(panel);
        this.add(panel);
        try
        {
            HashMap<PropertyInfo, Object> properties = new HashMap<PropertyInfo, Object>();
            PropertyInfo[] props=OfficeApplication.getOfficeDocumentProxy().getResourceProperties(repositoryName, contentID);
            if (props.length > 0)
            {
                this.remove(panel);
                this.add(this.panelPropertyEditor1);
                for (PropertyInfo info : props)
                {
                    Object defaultValue = null;
                    if (info.type.equalsIgnoreCase("string"))
                    {
                        defaultValue = "";
                    }
                    if (info.type.equalsIgnoreCase("integer"))
                    {
                        defaultValue = 0;
                    }
                    if (info.type.equalsIgnoreCase("boolean"))
                    {
                        defaultValue = false;
                    }
                    properties.put(info, defaultValue);
                }
                panelPropertyEditor1.loadProperties(properties);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getDescription()
    {
        return "Propiedades de presentación";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPropertyEditor1 = new org.semanticwb.openoffice.components.PanelPropertyEditor();

        setLayout(new java.awt.BorderLayout());
        add(panelPropertyEditor1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.semanticwb.openoffice.components.PanelPropertyEditor panelPropertyEditor1;
    // End of variables declaration//GEN-END:variables
}

