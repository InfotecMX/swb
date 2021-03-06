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
package org.semanticwb.xforms.ui;


import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class XFStaticText.
 * 
 * @author  jorge.jimenez
 */
public class XFStaticText extends XformsBaseImp
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(XFStaticText.class);
    
    /** The value. */
    protected String value=null;
    
    /** The is inhead. */
    protected boolean isInhead=false;
    
    /** The rdf element. */
    protected RDFElement rdfElement=null;
   
    /**
     * Instantiates a new xF static text.
     * 
     * @param rdfElement the rdf element
     */
    public XFStaticText(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value)
    {
        this.value=value;
    }
    
    /**
     * Sets the checks if is inhead.
     * 
     * @param isInhead the new checks if is inhead
     */
    public void setisInhead(boolean isInhead){
        this.isInhead=isInhead;
    }
    
    // Gets
    
    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue()
    {
        return value;
    }
    
    /**
     * Checks if is inhead.
     * 
     * @return true, if is inhead
     */
    public boolean isInhead(){
        return isInhead;
    }
    
    /**
     * Sets the rdf attributes.
     */
    public void setRDFAttributes(){
        if(rdfElement.getValue()!=null) {
            value=rdfElement.getValue();            
        }       
        isInhead=rdfElement.isInhead();
    }
  
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXml()
     */
    @Override
    public String getXml() 
    {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<![CDATA[");
            
            strbXml.append(value);
            
            strbXml.append("]]>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#setXml(java.lang.String)
     */
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
