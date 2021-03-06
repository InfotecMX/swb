/*
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
 */
package org.semanticwb.bigdata.test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author javier.solis.g
 */
public class CleanNT
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //http://admon.mapas.gob.mx/work/models/vgn/vgn_RecursoDigital/11583-1495/
        //<http://www.vgn.swb#vgn_RecursoDigital:11583-1495> <http://www.sfp.gob.mx/vgn#hrefVistaPrevia> "hrefVistaPrevia_11583-1495_11583.jpg" .
        //<http://www.vgn.swb#vgn_RecursoDigital:11583-1495> <http://www.sfp.gob.mx/vgn#pathFileSystem> "pathFileSystem_11583-1495_11583.jpg" .

        //<http://www.vgn.swb#vgn_Coleccion:467-254> <http://www.semanticwebbuilder.org/swb4/ontology#created> "2011-12-12T15:25:50.003" .
        //<http://www.vgn.swb#vgn_Coleccion:467-254> <http://www.semanticwebbuilder.org/swb4/ontology#updated> "2011-12-12T15:25:50.003" .
        //<http://www.vgn.swb#vgn_RecursoDigital:2556-1> <http://www.sfp.gob.mx/vgn#formatoRecurso> <http://www.vgn.swb#vgn_FormatoRecurso:17> .

        
        //Clean VGN
        /*
        try
        {
            FileInputStream in=new FileInputStream("/programming/proys/hackaton_data/vgn_db/vgn.nt");
            FileOutputStream ot=new FileOutputStream("/programming/proys/hackaton_data/vgn_db/vgn2.nt");
            PrintWriter out=new PrintWriter(ot);
            DataInputStream din=new DataInputStream(in);

            int x=0;
            int y=0;
            String line=null;
            while((line=din.readLine())!=null)
            {
                x++;if(x%10000==0)System.out.println(x+" "+y+" "+line);
                if(line.indexOf("<http://www.vgn.swb#vgn_RecursoGeograficoBusqueda:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#vgn_Bitacora:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#vgn_ConsolaEventoCapa:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#swb_")>-1)continue;
                if(line.indexOf("<http://user.vgn.swb#vgn_Usuario:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#WebPage:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#Resource:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#catalog_SWBCatalogResource:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#catalogvgn_CatalogResource:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#counter:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#Template:")>-1)continue;
                if(line.indexOf("<http://www.semanticwb.org/uradm#")>-1)continue;
                out.println(line);
                y++;
            }
            out.flush();
            out.close();
            ot.close();
        }catch(Exception e){e.printStackTrace();}
        */
/*        
        try
        {
            FileInputStream in=new FileInputStream("/programming/proys/hackaton_data/vgng_db/vgng.nt");
            FileOutputStream ot=new FileOutputStream("/programming/proys/hackaton_data/vgng_db/vgng2.nt");
            PrintWriter out=new PrintWriter(ot);
            DataInputStream din=new DataInputStream(in);

            int x=0;
            int y=0;
            String line=null;
            while((line=din.readLine())!=null)
            {
                x++;if(x%10000==0)System.out.println(x+" "+y+" "+line);
                if(line.indexOf("<http://www.vgng.swb#vgn_RecursoGeograficoBusqueda:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#swb_")>-1)continue;
                if(line.indexOf("<http://user.vgng.swb#vgn_Usuario:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#WebPage:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#Resource:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#catalog_SWBCatalogResource:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#catalogvgn_CatalogResource:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#counter:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#Template:")>-1)continue;
                if(line.indexOf("<http://www.semanticwb.org/uradm#")>-1)continue;
                out.println(line);
                y++;
            }
            out.flush();
            out.close();
            ot.close();
        }catch(Exception e){e.printStackTrace();}
*/        
/*    
        try
        {
            FileInputStream in=new FileInputStream("/programming/proys/hackaton_data/vgn_db/vgn2.nt");
            FileOutputStream ot=new FileOutputStream("/programming/proys/hackaton_data/vgn_db/vgn3.nt");
            PrintWriter out=new PrintWriter(ot);
            DataInputStream din=new DataInputStream(in);

            int x=0;
            int y=0;
            String line=null;
            while((line=din.readLine())!=null)
            {
                x++;if(x%10000==0)System.out.println(x+" "+y+" "+line);
                if(line.indexOf("<http://www.semanticwebbuilder.org/swb4/ontology#")>-1)continue;
                
                line=line.replace("<http://www.sfp.gob.mx/vgn#", "<http://datosabiertos.gob.mx/ontology/mapas.owl#");
                line=line.replace("<http://www.vgn.swb#vgn_","<http://datosabiertos.gob.mx/data/map_");
                line=line.replace("<http://www.vgng.swb#vgn_","<http://datosabiertos.gob.mx/data/map_");
                
                if(line.indexOf("<http://www.vgn.swb#")>-1)continue;                
                
                out.println(line);
                y++;
            }
            out.flush();
            out.close();
            ot.close();
        }catch(Exception e){e.printStackTrace();}       
*/        
        
        try
        {
            FileInputStream in=new FileInputStream("/programming/proys/hackaton_data/vgng_db/vgng2.nt");
            FileOutputStream ot=new FileOutputStream("/programming/proys/hackaton_data/vgng_db/vgng3.nt");
            PrintWriter out=new PrintWriter(ot);
            DataInputStream din=new DataInputStream(in);

            int x=0;
            int y=0;
            String line=null;
            while((line=din.readLine())!=null)
            {
                x++;if(x%10000==0)System.out.println(x+" "+y+" "+line);
                if(line.indexOf("<http://www.semanticwebbuilder.org/swb4/ontology#")>-1)continue;
                
                line=line.replace("<http://www.sfp.gob.mx/vgn#", "<http://datosabiertos.gob.mx/ontology/mapas.owl#");
                line=line.replace("<http://www.vgn.swb#vgn_","<http://datosabiertos.gob.mx/data/map_");
                line=line.replace("<http://www.vgng.swb#vgn_","<http://datosabiertos.gob.mx/data/map_");
                
                if(line.indexOf("<http://www.vgng.swb#")>-1)continue;                
                
                out.println(line);
                y++;
            }
            out.flush();
            out.close();
            ot.close();
        }catch(Exception e){e.printStackTrace();}         
      
    }
}
