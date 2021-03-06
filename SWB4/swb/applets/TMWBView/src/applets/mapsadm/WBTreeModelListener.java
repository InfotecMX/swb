/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/


/*
 * WBTreeModelListener.java
 *
 * Created on 5 de septiembre de 2002, 16:05
 */

package applets.mapsadm;

import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.*;

/**
 *
 * @author  Administrador
 * @version 
 */
public class WBTreeModelListener implements TreeModelListener {

    JApplet tree;
    
    /** Creates new WBTreeModelListener */
    public WBTreeModelListener(JApplet tree) {
        this.tree=tree;
    }

    public void treeStructureChanged(javax.swing.event.TreeModelEvent treeModelEvent) {
        //if(tree instanceof WBTree)
        //    ((WBTree)tree).namesChanged(treeModelEvent,"treeStructureChanged");
        if(tree instanceof TMWBAdmin)
            ((TMWBAdmin)tree).namesChanged(treeModelEvent,"treeStructureChanged");
    }
    
    public void treeNodesChanged(javax.swing.event.TreeModelEvent treeModelEvent) {
        //if(tree instanceof WBTree)
        //    ((WBTree)tree).namesChanged(treeModelEvent,"treeNodesChanged");
        if(tree instanceof TMWBAdmin)
            ((TMWBAdmin)tree).namesChanged(treeModelEvent,"treeNodesChanged");
    }
    
    public void treeNodesRemoved(javax.swing.event.TreeModelEvent treeModelEvent) {
        //if(tree instanceof WBTree)
        //    ((WBTree)tree).namesChanged(treeModelEvent,"treeNodesRemoved");
        if(tree instanceof TMWBAdmin)
            ((TMWBAdmin)tree).namesChanged(treeModelEvent,"treeNodesRemoved");
    }
    
    public void treeNodesInserted(javax.swing.event.TreeModelEvent treeModelEvent) {
        //if(tree instanceof WBTree)
        //    ((WBTree)tree).namesChanged(treeModelEvent,"treeNodesInserted");
        if(tree instanceof TMWBAdmin)
            ((TMWBAdmin)tree).namesChanged(treeModelEvent,"treeNodesInserted");
    }
    
}