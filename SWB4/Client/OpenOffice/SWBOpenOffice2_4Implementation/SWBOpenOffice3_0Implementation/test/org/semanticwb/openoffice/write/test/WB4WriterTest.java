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
package org.semanticwb.openoffice.write.test;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import ooo.connector.BootstrapSocketConnector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.openoffice.Configuration;
import org.semanticwb.openoffice.ConfigurationListURI;
import org.semanticwb.openoffice.DocumentType;
import org.semanticwb.openoffice.ErrorLog;
import org.semanticwb.openoffice.SaveDocumentFormat;
import org.semanticwb.openoffice.WBException;
import org.semanticwb.openoffice.writer.WB4WriterApplication;
import org.semanticwb.openoffice.writer.WB4Writer;

/**
 *
 * @author victor.lorenzana
 */
public class WB4WriterTest
{

    XComponentContext xContext;
    XComponent xCompDest = null;
    XDesktop oDesktop = null;
    File sUrlDestiny = new File("C:\\temp\\articulo2.odt");
    File tempDir = new File("c:/temp/demo/");

    public WB4WriterTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        File home = new File(System.getProperty("user.home"));
        System.setProperty(ConfigurationListURI.CONFIGURATION, home.getPath() + "/list.xml");
        System.setProperty(Configuration.CONFIGURATION_PROPERTY_NAME, home.getPath() + "/config.xml");
        System.setProperty(ErrorLog.CONFIGURATION, home.getPath());
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
        try
        {
            String oooExeFolder = "C:/Archivos de programa/OpenOffice.org 3/program";
            xContext =  BootstrapSocketConnector.bootstrap(oooExeFolder);

            //xContext = Bootstrap.bootstrap();
            // Obtener la factoria de servicios de OpenOffice   
            XMultiComponentFactory xMCF = xContext.getServiceManager();

            // Obtener la ventana principal (Desktop) de OpenOffice   
            Object oRawDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
            oDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oRawDesktop);

            // Obtener interfaz XComponentLoader del XDesktop   
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);

            // Cargar el documento en una nueva ventana oculta del XDesktop   
            PropertyValue[] loadProps = new PropertyValue[0];
            /*loadProps[0] = new PropertyValue();
            loadProps[0].Name = "Hidden";
            loadProps[0].Value = new Boolean(false);*/
            String url = "file:///" + sUrlDestiny.getPath().replace('\\', '/');
            xCompDest = xCompLoader.loadComponentFromURL(url, "_blank", 0, loadProps);

        }
        catch (com.sun.star.uno.Exception e)
        {
            e.printStackTrace(System.out);
        }
        catch (BootstrapException be)
        {
            be.printStackTrace(System.out);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    @After
    public void tearDown()
    {
        xCompDest.dispose();
        oDesktop.terminate();
        xCompDest = null;
        oDesktop = null;
        DeleteTemporalDirectory(this.tempDir);

    }

    public void DeleteTemporalDirectory(File dir)
    {
        File[] files = dir.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    file.delete();
                }
                else
                {
                    DeleteTemporalDirectory(file);
                    file.delete();
                }
            }
        }
        dir.delete();
    }

    @Test
    @Ignore
    public void getLocalPathTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            File actual = writer.getLocalPath();
            File expected = sUrlDestiny;
            Assert.assertEquals(actual, expected);
        }
        catch (Exception wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void getCustomPropertiesTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            Map<String, String> properties = writer.getCustomProperties();
            for (String prop : properties.keySet())
            {
                System.out.println(prop + "=" + properties.get(prop));
            }
            Assert.assertEquals(properties.size(), 4);
        }
        catch (WBException wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void getDocumentTypeTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            DocumentType actual = writer.getDocumentType();
            Assert.assertEquals(actual, DocumentType.WORD);
        }
        catch (WBException wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void saveAsHTMLTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            File actual = writer.saveAsHtml(tempDir);
            Assert.assertTrue(actual.exists());
        }
        catch (WBException wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void saveTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.save();
        }
        catch (WBException wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void saveAsSaveDocumentFormatHTMLTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            File actual = writer.saveAs(tempDir, SaveDocumentFormat.HTML);
            Assert.assertTrue(actual.exists());
        }
        catch (WBException wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void saveCustomPropertiesTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            HashMap<String, String> properties = new HashMap<String, String>();
            properties.put("id", "1");
            properties.put("tp", "Hola");
            writer.saveCustomProperties(properties);
        }
        catch (WBException wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void openTest()
    {
        WB4WriterApplication writer = new WB4WriterApplication(this.xContext);
        writer.open(DocumentType.WORD);

    }

    @Test
    @Ignore
    public void publishTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.publish();
        }
        catch (Throwable wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void showContentsInFlowTest()
    {
        try
        {            
            WB4Writer.showContentsInFlow();
        }
        catch (Throwable wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }


    @Test
    @Ignore
    public void showDocumentDetail()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.showDocumentDetail();
        }
        catch (Throwable wbe)
        {
            wbe.printStackTrace();
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void saveToSiteTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.saveToSite();
        }
        catch (Throwable wbe)
        {
            wbe.printStackTrace();
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void deleteAssociationTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.deleteAssociation();
        }
        catch (Throwable wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void deleteTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.delete();
        }
        catch (Throwable wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void showDocumentInfoTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.showDocumentInfo();
        }
        catch (Throwable wbe)
        {
            wbe.printStackTrace();
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void changePasswordTest()
    {
        try
        {
            WB4WriterApplication.changePassword();
        }
        catch (Throwable wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void addLinkTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.insertLink();
        }
        catch (Throwable wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void showAbout()
    {
        try
        {
            WB4WriterApplication.showAbout();

        }
        catch (Throwable wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void createWebPageTest()
    {
        try
        {
            WB4WriterApplication.createPage();
        }
        catch (Throwable wbe)
        {
            wbe.printStackTrace();
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void openDocumentTest()
    {

        WB4WriterApplication writer = new WB4WriterApplication(this.xContext);
        //try
        //{
        writer.open(DocumentType.WORD);
    //}
    //catch ( WBException wbe )
    //{
    //    Assert.fail(wbe.getMessage());
    //}

    }

    @Test
    @Ignore
    public void getAllAttachmentsTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            List<File> attachments = writer.getAllAttachments();
            Assert.assertEquals(attachments.size(), 2);
        }
        catch (WBException wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test
    @Ignore
    public void insertLinkDocTest()
    {
        try
        {
            WB4Writer writer = new WB4Writer(this.xContext);
            writer.insertLinkDoc();
        }
        catch (WBException wbe)
        {
            Assert.fail(wbe.getMessage());
        }
    }

    @Test(expected = WBException.class)
    @Ignore
    public void openDocumentExceptionTest() throws WBException
    {
        WB4WriterApplication writer = new WB4WriterApplication(this.xContext);
        writer.open(new File("c:\\demo.doc"));
    }
}
