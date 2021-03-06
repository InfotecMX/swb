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
*  http://www.webbuilder.org.mx 
**/ 
 
﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WBOffice4.Interfaces
{
    public class ElementInfo
    {
        public String id;
        public String title;
        public bool active;
        public String type;


        public override bool Equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (GetType() != obj.GetType())
            {
                return false;
            }
            ElementInfo other = (ElementInfo)obj;
            if ((this.id == null) ? (other.id != null) : !this.id.Equals(other.id))
            {
                return false;
            }
            if ((this.type == null) ? (other.type != null) : !this.type.Equals(other.type))
            {
                return false;
            }
            return true;
        }


        public override int GetHashCode()
        {
            int hash = 3;
            hash = 97 * hash + (this.id != null ? this.id.GetHashCode() : 0);
            hash = 97 * hash + (this.type != null ? this.type.GetHashCode() : 0);
            return hash;
        }



        public override String ToString()
        {
            return title.ToString();
        }
    }
}
