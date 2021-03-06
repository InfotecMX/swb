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
using System.Collections;
using System.Reflection;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("value")]
    [XmlInclude(typeof(IntValue))]
    [XmlInclude(typeof(DoubleValue))]
    [XmlInclude(typeof(DateTimeValue))]
    [XmlInclude(typeof(BooleanValue))]
    [XmlInclude(typeof(StringValue))]
    [XmlInclude(typeof(FloatValue))]
    [XmlInclude(typeof(ArrayValue))]
    [XmlInclude(typeof(StructValue))]
    [XmlInclude(typeof(Member))]
    public sealed class StructValue : Value
    {
        [XmlArray("struct")]
        [XmlArrayItem("member", Type = typeof(Value))]
        public ArrayList structElement { get; set; }

        public StructValue()
        {
            structElement = new ArrayList();
        }
        public StructValue(object obj)
        {
            structElement = new ArrayList();
            foreach(FieldInfo fieldInfo in obj.GetType().GetFields())
            {
                object value=fieldInfo.GetValue(obj);
                if (value != null)
                {
                    Value ovalue = XmlRpcClient.GetParameter(value);
                    Member member = new Member(fieldInfo.Name, ovalue);
                    structElement.Add(member);
                }
            }
        }
    }
}
