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
 * DialogRegularPeriods.java
 *
 * Created on 12/02/2009, 03:45:09 PM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Frame;
import javax.swing.JOptionPane;
import org.jdom.output.DOMOutputter;
import org.semanticwb.openoffice.ui.icons.ImageLoader;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class DialogRegularPeriods extends java.awt.Dialog
{
    private static final String DAY = "day";
    private static final String EMPTY_STRING = "";
    private static final String INTERVAL = "interval";
    private static final String ITERATIONS = "iterations";
    private static final String MONTH = "month";
    private static final String MONTHLY = "monthly";
    private static final String MONTHS = "months";
    private static final String ONE = "1";
    private static final String THREE = "3";
    private static final String TWO = "2";
    private static final String WDAYS = "wdays";
    private static final String WEEK = "week";
    private static final String WEEKLY = "weekly";
    private static final String YEARLY = "yearly";
    private static final String YEARS = "years";
    private static final String ZERO = "0";

    /** Creates new form DialogRegularPeriods */
    public DialogRegularPeriods()
    {
        super((Frame)null, ModalityType.TOOLKIT_MODAL);
        initComponents();
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.setModal(true);
        this.setLocationRelativeTo(null);
    }

    public org.jdom.Element getElement()
    {
        org.jdom.Element interval = null;
        if (this.jCheckBoxUseRegularPeriods.isSelected())
        {
            interval = new org.jdom.Element(INTERVAL);
            org.jdom.Element elem = null;
            org.jdom.Element iterations = new org.jdom.Element(ITERATIONS);
            interval.addContent(iterations);
            if (this.periodweek.isSelected())
            {
                int x = 0;
                org.jdom.Element weekly = new org.jdom.Element(WEEKLY);
                iterations.addContent(weekly);
                if (this.wday1.isSelected())
                {
                    x += 1;
                }
                if (this.wday2.isSelected())
                {
                    x += 2;
                }
                if (this.wday3.isSelected())
                {
                    x += 4;
                }
                if (this.wday4.isSelected())
                {
                    x += 8;
                }
                if (this.wday5.isSelected())
                {
                    x += 16;
                }
                if (this.wday6.isSelected())
                {
                    x += 32;
                }
                if (this.wday7.isSelected())
                {
                    x += 64;
                }
                elem = new org.jdom.Element(WDAYS);
                elem.setText(String.valueOf(x));
                weekly.addContent(elem);
            }
            if (this.periodmonth.isSelected())
            {
                org.jdom.Element monthly = new org.jdom.Element(MONTHLY);
                iterations.addContent(monthly);
                if (this.periodmont1.isSelected())
                {
                    if (this.mday.getValue() != null)
                    {
                        elem = new org.jdom.Element(DAY);
                        elem.setText(mday.getValue().toString());
                        monthly.addContent(elem);
                    }
                    if (this.mmonth.getValue() != null)
                    {
                        elem = new org.jdom.Element(MONTHS);
                        elem.setText(mmonth.getValue().toString());
                        monthly.addContent(elem);
                    }
                }
                else if (this.periodmont2.isSelected())
                {
                    int x = 0;
                    if (this.mweek.getSelectedItem() != null)
                    {
                        elem = new org.jdom.Element(WEEK);
                        elem.setText(String.valueOf(mweek.getSelectedIndex() + 1));
                        monthly.addContent(elem);
                    }
                    if (this.mday1.isSelected())
                    {
                        x += 1;
                    }
                    if (this.mday2.isSelected())
                    {
                        x += 2;
                    }
                    if (this.mday3.isSelected())
                    {
                        x += 4;
                    }
                    if (this.mday4.isSelected())
                    {
                        x += 8;
                    }
                    if (this.mday5.isSelected())
                    {
                        x += 16;
                    }
                    if (this.mday6.isSelected())
                    {
                        x += 32;
                    }
                    if (this.mday7.isSelected())
                    {
                        x += 64;
                    }
                    elem = new org.jdom.Element(WDAYS);
                    elem.setText(String.valueOf(x));
                    monthly.addContent(elem);
                    if (this.mmonth2.getValue() != null)
                    {
                        elem = new org.jdom.Element(MONTHS);
                        elem.setText(mmonth2.getValue().toString());
                        monthly.addContent(elem);
                    }
                }
            }
            if (this.periodyear.isSelected())
            {
                org.jdom.Element yearly = new org.jdom.Element(YEARLY);
                iterations.addContent(yearly);
                if (this.periodyear1.isSelected())
                {
                    if (this.yday.getValue() != null)
                    {
                        elem = new org.jdom.Element(DAY);
                        elem.setText(yday.getValue().toString());
                        yearly.addContent(elem);
                    }
                    elem = new org.jdom.Element(MONTH);
                    elem.setText(String.valueOf(ymonth.getSelectedIndex() + 1));
                    yearly.addContent(elem);

                    if (this.yyear.getValue() != null)
                    {
                        elem = new org.jdom.Element(YEARS);
                        elem.setText(yyear.getValue().toString());
                        yearly.addContent(elem);
                    }
                }
                else if (this.periodyear2.isSelected())
                {
                    int x = 0;
                    if (this.yweek.getSelectedItem() != null)
                    {
                        elem = new org.jdom.Element(WEEK);
                        elem.setText(String.valueOf(yweek.getSelectedIndex() + 1));
                        yearly.addContent(elem);
                    }
                    if (this.yday1.isSelected())
                    {
                        x += 1;
                    }
                    if (this.yday2.isSelected())
                    {
                        x += 2;
                    }
                    if (this.yday3.isSelected())
                    {
                        x += 4;
                    }
                    if (this.yday4.isSelected())
                    {
                        x += 8;
                    }
                    if (this.yday5.isSelected())
                    {
                        x += 16;
                    }
                    if (this.yday6.isSelected())
                    {
                        x += 32;
                    }
                    if (this.yday7.isSelected())
                    {
                        x += 64;
                    }
                    elem = new org.jdom.Element(WDAYS);
                    elem.setText(String.valueOf(x));
                    yearly.addContent(elem);
                    if (this.ymonth2.getSelectedItem() != null)
                    {
                        elem = new org.jdom.Element(MONTH);
                        elem.setText(String.valueOf(ymonth2.getSelectedIndex() + 1));
                        yearly.addContent(elem);
                    }
                    if (this.yyear2.getValue() != null)
                    {
                        elem = new org.jdom.Element(YEARS);
                        elem.setText(yyear2.getValue().toString());
                        yearly.addContent(elem);
                    }
                }
            }
        }
        return interval;
    }

    public void setDocument(org.jdom.Document document)
    {
        init(document);
    }

    private void init(org.jdom.Document document)
    {
        DOMOutputter out = new DOMOutputter();
        try
        {
            org.w3c.dom.Document xml = out.output(document);
            if (document != null)
            {
                if (xml.getElementsByTagName(ITERATIONS).getLength() > 0)
                {
                    this.jCheckBoxUseRegularPeriods.setSelected(true);
                }
                if (xml.getElementsByTagName(ITERATIONS).getLength() > 0)
                {
                    String[] siteminterval = new String[9];
                    String tipo = ZERO;
                    NodeList nodosweekly = xml.getElementsByTagName(WEEKLY);
                    if (nodosweekly.getLength() > 0)
                    {
                        tipo = ONE;
                    }
                    NodeList nodosmonthly = xml.getElementsByTagName(MONTHLY);
                    if (nodosmonthly.getLength() > 0)
                    {
                        tipo = TWO;
                    }
                    NodeList nodosyearly = xml.getElementsByTagName(YEARLY);
                    if (nodosyearly.getLength() > 0)
                    {
                        tipo = THREE;
                    }

                    if (xml.getElementsByTagName(WEEKLY).getLength() > 0)
                    {
                        periodweek.setSelected(true);

                    }
                    if (xml.getElementsByTagName(MONTHLY).getLength() > 0)
                    {
                        periodweek.setSelected(true);
                    }
                    if (xml.getElementsByTagName(YEARLY).getLength() > 0)
                    {
                        periodyear.setSelected(true);
                    }

                    NodeList nodositera = xml.getElementsByTagName(ITERATIONS);
                    for (int i = 0; i < nodositera.getLength(); i++)
                    {
                        Element xmlitera = (Element) nodositera.item(i);
                        for (int l = 0; l < xmlitera.getChildNodes().getLength(); l++)
                        {
                            NodeList nodosit = xmlitera.getElementsByTagName(WEEKLY);
                            for (int j = 0; j < nodosit.getLength(); j++)
                            {
                                Element xmlit = (Element) nodosit.item(j);
                                for (int k = 0; k < xmlit.getChildNodes().getLength(); k++)
                                {
                                    if (xmlit.getChildNodes().item(l).getNodeName().equals(WDAYS)) //días
                                    {
                                        siteminterval[2] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                }
                            }
                            NodeList nodosit1 = xmlitera.getElementsByTagName(MONTHLY);
                            for (int j = 0; j < nodosit1.getLength(); j++)
                            {
                                Element xmlit = (Element) nodosit1.item(j);
                                for (int k = 0; k < xmlit.getChildNodes().getLength(); k++)
                                {
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(WDAYS)) //días
                                    {
                                        siteminterval[2] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(WEEK)) //semanas
                                    {
                                        siteminterval[3] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(MONTHS)) //meses
                                    {
                                        siteminterval[4] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(DAY)) //día específico
                                    {
                                        siteminterval[6] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                }
                            }
                            NodeList nodosit2 = xmlitera.getElementsByTagName(YEARLY);
                            for (int j = 0; j < nodosit2.getLength(); j++)
                            {
                                Element xmlit = (Element) nodosit2.item(j);
                                for (int k = 0; k < xmlit.getChildNodes().getLength(); k++)
                                {
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(WDAYS)) //días
                                    {
                                        siteminterval[2] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(WEEK)) //semanas
                                    {
                                        siteminterval[3] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(MONTHS)) //meses
                                    {
                                        siteminterval[4] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(YEARS)) //años
                                    {
                                        siteminterval[5] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(DAY)) //día específico
                                    {
                                        siteminterval[6] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                    if (xmlit.getChildNodes().item(k).getNodeName().equals(MONTH)) //mes específico
                                    {
                                        siteminterval[7] = xmlit.getChildNodes().item(k).getNodeValue();
                                    }
                                }
                            }
                        }
                    }
                    if (tipo.equals(ONE))
                    {
                        this.periodweek.setSelected(true);
                        String[] days =
                        {
                            EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING};
                        int dias = Integer.parseInt(siteminterval[2]);
                        int res = 0;
                        int cdias = 0;
                        while (dias > 0)
                        {
                            res = dias % 2;
                            days[cdias] = String.valueOf(res);
                            dias = dias / 2;
                            cdias++;
                        }
                        for (int i = 0; i < days.length; i++)
                        {
                            if (days[i] != null)
                            {
                                if (i == 0 && days[i].equals(ONE))
                                {
                                    this.wday1.setSelected(true);
                                }
                                if (i == 1 && days[i].equals(ONE))
                                {
                                    this.wday2.setSelected(true);
                                }
                                if (i == 2 && days[i].equals(ONE))
                                {
                                    this.wday3.setSelected(true);
                                }
                                if (i == 3 && days[i].equals(ONE))
                                {
                                    this.wday4.setSelected(true);
                                }
                                if (i == 4 && days[i].equals(ONE))
                                {
                                    this.wday5.setSelected(true);
                                }
                                if (i == 5 && days[i].equals(ONE))
                                {
                                    this.wday6.setSelected(true);
                                }
                                if (i == 6 && days[i].equals(ONE))
                                {
                                    this.wday7.setSelected(true);
                                }
                            }
                        }
                    }
                    if (tipo.equals(TWO))
                    {
                        this.periodmonth.setSelected(true);
                        if (siteminterval[6] != null)
                        {
                            this.periodmont1.setSelected(true);
                            this.mday.setValue(Integer.parseInt(siteminterval[6]));
                            this.mmonth.setValue(Integer.parseInt(siteminterval[4]));
                        }
                        else
                        {
                            this.periodmont2.setSelected(true);
                            String[] days =
                            {
                                EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING};
                            int dias = Integer.parseInt(siteminterval[2]);
                            int res = 0;
                            int cdias = 0;
                            while (dias > 0)
                            {
                                res = dias % 2;
                                days[cdias] = String.valueOf(res);
                                dias = dias / 2;
                                cdias++;
                            }
                            for (int i = 0; i < days.length; i++)
                            {
                                if (i == 0 && days[i].equals(ONE))
                                {
                                    this.mday1.setSelected(true);
                                }
                                if (i == 1 && days[i].equals(ONE))
                                {
                                    this.mday2.setSelected(true);
                                }
                                if (i == 2 && days[i].equals(ONE))
                                {
                                    this.mday3.setSelected(true);
                                }
                                if (i == 3 && days[i].equals(ONE))
                                {
                                    this.mday4.setSelected(true);
                                }
                                if (i == 4 && days[i].equals(ONE))
                                {
                                    this.mday5.setSelected(true);
                                }
                                if (i == 5 && days[i].equals(ONE))
                                {
                                    this.mday6.setSelected(true);
                                }
                                if (i == 6 && days[i].equals(ONE))
                                {
                                    this.mday7.setSelected(true);
                                }
                            }
                            if (siteminterval[3] != null)
                            {
                                this.mweek.setSelectedItem(Integer.parseInt(siteminterval[3]) - 1);
                            }
                        }
                    }
                    if (tipo.equals(THREE))
                    {
                        this.periodyear.setSelected(true);
                        if (siteminterval[6] != null)
                        {
                            this.periodyear1.setSelected(true);
                            this.yday.setValue(Integer.parseInt(siteminterval[6]));
                            if (siteminterval[7] != null)
                            {
                                this.ymonth.setSelectedIndex(Integer.parseInt(siteminterval[7]) - 1);
                            }
                            this.yyear.setValue(Integer.parseInt(siteminterval[5]));
                        }
                        else
                        {
                            this.periodyear2.setSelected(true);

                            String[] days =
                            {
                                EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING};
                            int dias = Integer.parseInt(siteminterval[2]);
                            int res = 0;
                            int cdias = 0;
                            while (dias > 0)
                            {
                                res = dias % 2;
                                days[cdias] = String.valueOf(res);
                                dias = dias / 2;
                                cdias++;
                            }
                            for (int i = 0; i < days.length; i++)
                            {
                                if (i == 0 && days[i].equals(ONE))
                                {
                                    this.yday1.setSelected(true);
                                }
                                if (i == 1 && days[i].equals(ONE))
                                {
                                    this.yday2.setSelected(true);
                                }
                                if (i == 2 && days[i].equals(ONE))
                                {
                                    this.yday3.setSelected(true);
                                }
                                if (i == 3 && days[i].equals(ONE))
                                {
                                    this.yday4.setSelected(true);
                                }
                                if (i == 4 && days[i].equals(ONE))
                                {
                                    this.yday5.setSelected(true);
                                }
                                if (i == 5 && days[i].equals(ONE))
                                {
                                    this.yday6.setSelected(true);
                                }
                                if (i == 6 && days[i].equals(ONE))
                                {
                                    this.yday7.setSelected(true);
                                }
                            }
                            if (siteminterval[3] != null)
                            {
                                this.yweek.setSelectedIndex(Integer.parseInt(siteminterval[3]) - 1);
                            }
                            if (siteminterval[7] != null)
                            {
                                this.ymonth2.setSelectedIndex(Integer.parseInt(siteminterval[7]) - 1);
                            }
                            this.yyear2.setValue(Integer.parseInt(siteminterval[5]));
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupPeriodicidad = new javax.swing.ButtonGroup();
        buttonGroupMensual = new javax.swing.ButtonGroup();
        buttonGroupAnual = new javax.swing.ButtonGroup();
        jPanelBody = new javax.swing.JPanel();
        jPanelCommands = new javax.swing.JPanel();
        jButtonok = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jPanelConfigurations = new javax.swing.JPanel();
        jPanelWeek = new javax.swing.JPanel();
        periodweek = new javax.swing.JRadioButton();
        wday2 = new javax.swing.JCheckBox();
        wday3 = new javax.swing.JCheckBox();
        wday4 = new javax.swing.JCheckBox();
        wday5 = new javax.swing.JCheckBox();
        wday6 = new javax.swing.JCheckBox();
        wday7 = new javax.swing.JCheckBox();
        wday1 = new javax.swing.JCheckBox();
        jPanelOthers = new javax.swing.JPanel();
        jPanelMonth = new javax.swing.JPanel();
        periodmonth = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        mweek = new javax.swing.JComboBox();
        mday2 = new javax.swing.JCheckBox();
        mday3 = new javax.swing.JCheckBox();
        mday4 = new javax.swing.JCheckBox();
        mday5 = new javax.swing.JCheckBox();
        mday6 = new javax.swing.JCheckBox();
        mday7 = new javax.swing.JCheckBox();
        mday1 = new javax.swing.JCheckBox();
        periodmont2 = new javax.swing.JRadioButton();
        periodmont1 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        mday = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        mmonth = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mmonth2 = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jPanelYear = new javax.swing.JPanel();
        periodyear = new javax.swing.JRadioButton();
        periodyear2 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        yweek = new javax.swing.JComboBox();
        yday2 = new javax.swing.JCheckBox();
        yday3 = new javax.swing.JCheckBox();
        yday6 = new javax.swing.JCheckBox();
        yday7 = new javax.swing.JCheckBox();
        yday1 = new javax.swing.JCheckBox();
        yday4 = new javax.swing.JCheckBox();
        yday5 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        ymonth2 = new javax.swing.JComboBox();
        periodyear1 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        yday = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        ymonth = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        yyear = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        yyear2 = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jPanelUseRegularPeriods = new javax.swing.JPanel();
        jCheckBoxUseRegularPeriods = new javax.swing.JCheckBox();

        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogRegularPeriods"); // NOI18N
        setTitle(bundle.getString("CALENDARIZACIÓN_CON_PERIODOS_REGULARES")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanelBody.setPreferredSize(new java.awt.Dimension(600, 410));
        jPanelBody.setLayout(new java.awt.BorderLayout());

        jPanelCommands.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanelCommands.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonok.setText(bundle.getString("ACEPTAR")); // NOI18N
        jButtonok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonokActionPerformed(evt);
            }
        });
        jPanelCommands.add(jButtonok);

        jButtonCancel.setText(bundle.getString("CANCELAR")); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        jPanelCommands.add(jButtonCancel);

        jPanelBody.add(jPanelCommands, java.awt.BorderLayout.SOUTH);

        jPanelConfigurations.setLayout(new java.awt.BorderLayout());

        jPanelWeek.setBorder(javax.swing.BorderFactory.createTitledBorder("Periodicidad Semanal"));
        jPanelWeek.setPreferredSize(new java.awt.Dimension(100, 90));

        buttonGroupPeriodicidad.add(periodweek);
        periodweek.setSelected(true);
        periodweek.setText(bundle.getString("SEMANAL")); // NOI18N

        wday2.setText(bundle.getString("LUNES")); // NOI18N

        wday3.setText(bundle.getString("MARTES")); // NOI18N

        wday4.setText(bundle.getString("MIÉRCOLES")); // NOI18N

        wday5.setText(bundle.getString("JUEVES")); // NOI18N

        wday6.setText(bundle.getString("VIERNES")); // NOI18N

        wday7.setText(bundle.getString("SABADO")); // NOI18N

        wday1.setText(bundle.getString("DOMINGO")); // NOI18N

        javax.swing.GroupLayout jPanelWeekLayout = new javax.swing.GroupLayout(jPanelWeek);
        jPanelWeek.setLayout(jPanelWeekLayout);
        jPanelWeekLayout.setHorizontalGroup(
            jPanelWeekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWeekLayout.createSequentialGroup()
                .addComponent(periodweek)
                .addGap(52, 52, 52)
                .addGroup(jPanelWeekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(wday2)
                    .addComponent(wday7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWeekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelWeekLayout.createSequentialGroup()
                        .addComponent(wday3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(wday4)
                        .addGap(2, 2, 2)
                        .addComponent(wday5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(wday6))
                    .addComponent(wday1))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanelWeekLayout.setVerticalGroup(
            jPanelWeekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWeekLayout.createSequentialGroup()
                .addGroup(jPanelWeekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelWeekLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(periodweek))
                    .addGroup(jPanelWeekLayout.createSequentialGroup()
                        .addGroup(jPanelWeekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wday2)
                            .addComponent(wday3)
                            .addComponent(wday4)
                            .addComponent(wday5)
                            .addComponent(wday6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelWeekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wday7)
                            .addComponent(wday1))))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanelConfigurations.add(jPanelWeek, java.awt.BorderLayout.NORTH);

        jPanelOthers.setLayout(new java.awt.BorderLayout());

        jPanelMonth.setBorder(javax.swing.BorderFactory.createTitledBorder("Periodicidad Mensual"));
        jPanelMonth.setPreferredSize(new java.awt.Dimension(100, 130));

        buttonGroupPeriodicidad.add(periodmonth);
        periodmonth.setText(bundle.getString("MENSUAL")); // NOI18N

        jLabel1.setText(bundle.getString("EL:")); // NOI18N

        mweek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Primer", "Segundo", "Tercer", "Cuarto", "Último" }));
        mweek.setToolTipText(bundle.getString("SELECIONE_LA_SEMANA")); // NOI18N

        mday2.setText(bundle.getString("LU")); // NOI18N

        mday3.setText(bundle.getString("MA")); // NOI18N

        mday4.setText(bundle.getString("MI")); // NOI18N

        mday5.setText(bundle.getString("JU")); // NOI18N

        mday6.setText(bundle.getString("VI")); // NOI18N

        mday7.setText(bundle.getString("SA")); // NOI18N

        mday1.setText(bundle.getString("DO")); // NOI18N

        buttonGroupMensual.add(periodmont2);
        periodmont2.setSelected(true);

        buttonGroupMensual.add(periodmont1);

        jLabel2.setText(bundle.getString("EL:")); // NOI18N

        mday.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        jLabel3.setText(bundle.getString("DE_CADA:")); // NOI18N

        mmonth.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        jLabel4.setText(bundle.getString("MESES")); // NOI18N

        jLabel5.setText(bundle.getString("DE_CADA:")); // NOI18N

        mmonth2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        jLabel6.setText(bundle.getString("MESES")); // NOI18N

        javax.swing.GroupLayout jPanelMonthLayout = new javax.swing.GroupLayout(jPanelMonth);
        jPanelMonth.setLayout(jPanelMonthLayout);
        jPanelMonthLayout.setHorizontalGroup(
            jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMonthLayout.createSequentialGroup()
                .addComponent(periodmonth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelMonthLayout.createSequentialGroup()
                        .addComponent(periodmont1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addComponent(mday)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(mmonth, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMonthLayout.createSequentialGroup()
                        .addComponent(periodmont2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(mweek, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mday6)
                            .addComponent(mday2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mday7)
                            .addComponent(mday3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMonthLayout.createSequentialGroup()
                                .addComponent(mday4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mday5)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mmonth2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6))
                            .addComponent(mday1))
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        jPanelMonthLayout.setVerticalGroup(
            jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMonthLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMonthLayout.createSequentialGroup()
                        .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(mmonth2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(5, 5, 5)
                        .addComponent(periodmonth))
                    .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mweek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(periodmont2)
                        .addComponent(jLabel1))
                    .addGroup(jPanelMonthLayout.createSequentialGroup()
                        .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mday2)
                            .addComponent(mday4)
                            .addComponent(mday5)
                            .addComponent(mday3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mday6)
                            .addComponent(mday1)
                            .addComponent(mday7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanelMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(mmonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(periodmont1)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        jPanelOthers.add(jPanelMonth, java.awt.BorderLayout.NORTH);

        jPanelYear.setBorder(javax.swing.BorderFactory.createTitledBorder("Periodicidad Anual"));
        jPanelYear.setPreferredSize(new java.awt.Dimension(500, 160));

        buttonGroupPeriodicidad.add(periodyear);
        periodyear.setText(bundle.getString("ANUAL")); // NOI18N

        buttonGroupAnual.add(periodyear2);
        periodyear2.setSelected(true);

        jLabel7.setText(bundle.getString("EL:")); // NOI18N

        yweek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Primer", "Segundo", "Tercer", "Cuarto", "Último" }));
        yweek.setToolTipText(bundle.getString("SELECCIONE_LA_SEMANA")); // NOI18N

        yday2.setText(bundle.getString("LU")); // NOI18N

        yday3.setText(bundle.getString("MA")); // NOI18N

        yday6.setText(bundle.getString("VI")); // NOI18N

        yday7.setText(bundle.getString("SA")); // NOI18N

        yday1.setText(bundle.getString("DO")); // NOI18N

        yday4.setText(bundle.getString("MI")); // NOI18N

        yday5.setText(bundle.getString("JU")); // NOI18N

        jLabel8.setText(bundle.getString("DE:")); // NOI18N

        ymonth2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        buttonGroupAnual.add(periodyear1);

        jLabel9.setText(bundle.getString("EL:")); // NOI18N

        yday.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        jLabel10.setText(bundle.getString("DE:")); // NOI18N

        ymonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        jLabel11.setText(bundle.getString("DE_CADA:")); // NOI18N

        yyear.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel12.setText(bundle.getString("AÑOS")); // NOI18N

        jLabel13.setText(bundle.getString("DE_CADA:")); // NOI18N

        yyear2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel14.setText(bundle.getString("AÑOS")); // NOI18N

        javax.swing.GroupLayout jPanelYearLayout = new javax.swing.GroupLayout(jPanelYear);
        jPanelYear.setLayout(jPanelYearLayout);
        jPanelYearLayout.setHorizontalGroup(
            jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelYearLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(periodyear)
                .addGap(8, 8, 8)
                .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelYearLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(periodyear2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yweek, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yday6)
                            .addComponent(yday2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yday7)
                            .addComponent(yday3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelYearLayout.createSequentialGroup()
                                .addComponent(yday4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yday5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ymonth2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelYearLayout.createSequentialGroup()
                                .addComponent(yday1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yyear2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14))))
                    .addGroup(jPanelYearLayout.createSequentialGroup()
                        .addComponent(periodyear1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yday, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ymonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)))
                .addGap(35, 35, 35))
        );
        jPanelYearLayout.setVerticalGroup(
            jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelYearLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(periodyear)
                .addGap(75, 75, 75))
            .addGroup(jPanelYearLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(yday2)
                .addContainerGap(82, Short.MAX_VALUE))
            .addGroup(jPanelYearLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(yday3)
                .addContainerGap(82, Short.MAX_VALUE))
            .addGroup(jPanelYearLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(yday4)
                .addContainerGap(82, Short.MAX_VALUE))
            .addGroup(jPanelYearLayout.createSequentialGroup()
                .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelYearLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(yweek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(periodyear2))
                        .addGap(3, 3, 3)
                        .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(yday1)
                                .addComponent(yday7))
                            .addComponent(yday6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(25, 25, 25))
                    .addGroup(jPanelYearLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yday5)
                            .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ymonth2, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(yyear2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(24, 24, 24)))
                .addGroup(jPanelYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(periodyear1)
                    .addComponent(jLabel9)
                    .addComponent(yday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(ymonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(yyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap())
        );

        jPanelOthers.add(jPanelYear, java.awt.BorderLayout.CENTER);

        jPanelConfigurations.add(jPanelOthers, java.awt.BorderLayout.CENTER);

        jPanelBody.add(jPanelConfigurations, java.awt.BorderLayout.CENTER);

        add(jPanelBody, java.awt.BorderLayout.CENTER);

        jPanelUseRegularPeriods.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanelUseRegularPeriods.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jCheckBoxUseRegularPeriods.setText(bundle.getString("USAR_PERIODOS_REGULARES")); // NOI18N
        jPanelUseRegularPeriods.add(jCheckBoxUseRegularPeriods);

        add(jPanelUseRegularPeriods, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonokActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonokActionPerformed
    {//GEN-HEADEREND:event_jButtonokActionPerformed
        if(this.periodweek.isSelected())
        {            
            if(!(wday1.isSelected() || wday2.isSelected() || wday3.isSelected() || wday4.isSelected() || wday5.isSelected() || wday6.isSelected() || wday7.isSelected()))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogRegularPeriods").getString("DEBE_INDICAR_POR_LO_MENOS_UN_DÍA_DE_LA_SEMANA_PARA_UN_PERIODO_SEMANAL"),this.getTitle(),JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                this.periodweek.requestFocus();
                return;
            }
        }
        if(this.periodmonth.isSelected() && periodmont2.isSelected())
        {
            if(!(mday1.isSelected() || mday2.isSelected() || mday3.isSelected() || mday4.isSelected() || mday5.isSelected() || mday6.isSelected() || mday7.isSelected()))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogRegularPeriods").getString("DEBE_INDICAR_POR_LO_MENOS_UN_DÍA_DE_LA_SEMANA_PARA_UN_PERIODO_MENSUAL"),this.getTitle(),JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                this.periodmont2.requestFocus();
                return;
            }
        }

        if(this.periodyear.isSelected() && periodyear2.isSelected())
        {
            if(!(mday1.isSelected() || mday2.isSelected() || mday3.isSelected() || mday4.isSelected() || mday5.isSelected() || mday6.isSelected() || mday7.isSelected()))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogRegularPeriods").getString("DEBE_INDICAR_POR_LO_MENOS_UN_DÍA_DE_LA_SEMANA_PARA_UN_PERIODO_ANUAL"),this.getTitle(),JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                this.periodyear2.requestFocus();
                return;
            }
        }
        this.setVisible(false);
    }//GEN-LAST:event_jButtonokActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupAnual;
    private javax.swing.ButtonGroup buttonGroupMensual;
    private javax.swing.ButtonGroup buttonGroupPeriodicidad;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonok;
    private javax.swing.JCheckBox jCheckBoxUseRegularPeriods;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelBody;
    private javax.swing.JPanel jPanelCommands;
    private javax.swing.JPanel jPanelConfigurations;
    private javax.swing.JPanel jPanelMonth;
    private javax.swing.JPanel jPanelOthers;
    private javax.swing.JPanel jPanelUseRegularPeriods;
    private javax.swing.JPanel jPanelWeek;
    private javax.swing.JPanel jPanelYear;
    private javax.swing.JSpinner mday;
    private javax.swing.JCheckBox mday1;
    private javax.swing.JCheckBox mday2;
    private javax.swing.JCheckBox mday3;
    private javax.swing.JCheckBox mday4;
    private javax.swing.JCheckBox mday5;
    private javax.swing.JCheckBox mday6;
    private javax.swing.JCheckBox mday7;
    private javax.swing.JSpinner mmonth;
    private javax.swing.JSpinner mmonth2;
    private javax.swing.JComboBox mweek;
    private javax.swing.JRadioButton periodmont1;
    private javax.swing.JRadioButton periodmont2;
    private javax.swing.JRadioButton periodmonth;
    private javax.swing.JRadioButton periodweek;
    private javax.swing.JRadioButton periodyear;
    private javax.swing.JRadioButton periodyear1;
    private javax.swing.JRadioButton periodyear2;
    private javax.swing.JCheckBox wday1;
    private javax.swing.JCheckBox wday2;
    private javax.swing.JCheckBox wday3;
    private javax.swing.JCheckBox wday4;
    private javax.swing.JCheckBox wday5;
    private javax.swing.JCheckBox wday6;
    private javax.swing.JCheckBox wday7;
    private javax.swing.JSpinner yday;
    private javax.swing.JCheckBox yday1;
    private javax.swing.JCheckBox yday2;
    private javax.swing.JCheckBox yday3;
    private javax.swing.JCheckBox yday4;
    private javax.swing.JCheckBox yday5;
    private javax.swing.JCheckBox yday6;
    private javax.swing.JCheckBox yday7;
    private javax.swing.JComboBox ymonth;
    private javax.swing.JComboBox ymonth2;
    private javax.swing.JComboBox yweek;
    private javax.swing.JSpinner yyear;
    private javax.swing.JSpinner yyear2;
    // End of variables declaration//GEN-END:variables
}
