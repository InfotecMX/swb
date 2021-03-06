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
 * CentralRegistrationClass.java
 *
 * Created on 2008.12.03 - 13:26:51
 *
 */
package org.semanticwb.openoffice;

import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 *
 * @author victor.lorenzana
 */
public class CentralRegistrationClass
{

    public static XSingleComponentFactory __getComponentFactory(String sImplementationName)
    {
        String regClassesList = getRegistrationClasses();
        StringTokenizer t = new StringTokenizer(regClassesList, " ");
        while (t.hasMoreTokens())
        {
            String className = t.nextToken();
            if (className != null && className.length() != 0)
            {
                try
                {
                    Class regClass = Class.forName(className);
                    Method writeRegInfo = regClass.getDeclaredMethod("__getComponentFactory", new Class[]
                            {
                                String.class
                            });
                    Object result = writeRegInfo.invoke(regClass, sImplementationName);
                    if (result != null)
                    {
                        return (XSingleComponentFactory) result;
                    }
                }
                catch (ClassNotFoundException ex)
                {
                    ex.printStackTrace();
                }
                catch (ClassCastException ex)
                {
                    ex.printStackTrace();
                }
                catch (SecurityException ex)
                {
                    ex.printStackTrace();
                }
                catch (NoSuchMethodException ex)
                {
                    ex.printStackTrace();
                }
                catch (IllegalArgumentException ex)
                {
                    ex.printStackTrace();
                }
                catch (InvocationTargetException ex)
                {
                    ex.printStackTrace();
                }
                catch (IllegalAccessException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean __writeRegistryServiceInfo(XRegistryKey xRegistryKey)
    {        
        boolean bResult = true;
        String regClassesList = getRegistrationClasses();
        StringTokenizer t = new StringTokenizer(regClassesList, " ");
        while (t.hasMoreTokens())
        {
            String className = t.nextToken();
            if (className != null && className.length() != 0)
            {
                try
                {
                    Class regClass = Class.forName(className);
                    Method writeRegInfo = regClass.getDeclaredMethod("__writeRegistryServiceInfo", new Class[]
                            {
                                XRegistryKey.class
                            });
                    Object result = writeRegInfo.invoke(regClass, xRegistryKey);
                    bResult &= ((Boolean) result).booleanValue();
                }
                catch (ClassNotFoundException ex)
                {
                    ex.printStackTrace();
                }
                catch (ClassCastException ex)
                {
                    ex.printStackTrace();
                }
                catch (SecurityException ex)
                {
                    ex.printStackTrace();
                }
                catch (NoSuchMethodException ex)
                {
                    ex.printStackTrace();
                }
                catch (IllegalArgumentException ex)
                {
                    ex.printStackTrace();
                }
                catch (InvocationTargetException ex)
                {
                    ex.printStackTrace();
                }
                catch (IllegalAccessException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        return bResult;
    }

    private static String getRegistrationClasses()
    {
        CentralRegistrationClass c = new CentralRegistrationClass();
        String name = c.getClass().getCanonicalName().replace('.', '/').concat(".class");
        try
        {
            Enumeration<URL> urlEnum = c.getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (urlEnum.hasMoreElements())
            {
                URL url = urlEnum.nextElement();
                String file = url.getFile();

                JarURLConnection jarConnection =
                        (JarURLConnection) url.openConnection();
                Manifest mf = jarConnection.getManifest();

                Attributes attrs = (Attributes) mf.getAttributes(name);
                if (attrs != null)
                {
                    String classes = attrs.getValue("RegistrationClasses");
                    return classes;
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return "";
    }

    /** Creates a new instance of CentralRegistrationClass */
    private CentralRegistrationClass()
    {   
        File home=new File(System.getProperty("user.home"));
        System.setProperty(ConfigurationListURI.CONFIGURATION, home.getPath()+"/list.xml");
        System.setProperty(Configuration.CONFIGURATION_PROPERTY_NAME, home.getPath()+"/config.xml");
        System.setProperty(ErrorLog.CONFIGURATION, home.getPath());
    }
}
