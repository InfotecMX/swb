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

package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class ExclusiveGateway extends org.semanticwb.process.model.base.ExclusiveGatewayBase 
{
    public ExclusiveGateway(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user,instance.getSourceInstance().getAction());
    }

    @Override
    public void nextObject(FlowNodeInstance instance, User user)
    {
        //SI LA COMPUERTA ES DIVERGENTE, EVALUAR LAS CONDICIONES
        //System.out.println("nextObject:"+getId()+" "+getFlowNodeType().getClass().getName()+" "+getFlowNodeType().getTitle());
        DefaultFlow def=null;
        boolean execute=false;
        Iterator<ConnectionObject> it=listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(!(connectionObject instanceof DefaultFlow))
            {
                if(connectionObject.evaluate(instance, user))
                {
                    connectionObject.execute(instance, user);
                    execute=true;
                    break;
                }
            }else
            {
                def=(DefaultFlow)connectionObject;
            }
        }
        if(!execute && def!=null)def.execute(instance, user);

        //SI LA COMPUERTA ES CONVERGENTE, PASAR EL CONTROL CON EL PRIMER FLUJO DE LLEGADA
    }
}
