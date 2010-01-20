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
package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;

/**
 *
 * @author Jei
 */
public class SemanticProperty
{

    private Property m_prop;
    private SemanticProperty m_inverse;
    private Boolean isObjectProperty = null;
    private Boolean isDataTypeProperty = null;
    private Boolean hasInverse = null;
    private boolean isInverse = false;
    private Boolean isExternalInvocation = null;
    private Boolean isInheritProperty = null;
    private Boolean isNotObservable = null;
    private Boolean isNotCodeGeneration = null;
    private Boolean isRemoveDependency = null;
    private Boolean isCloneDependency = null;
    private Boolean isHeraquicalRelation = null;
    private Boolean isRequired = null;
    private Boolean isUsedAsName = null;
    private Boolean isLocaleable = null;
    private String m_propertyCodeName = null;
    private String m_defaultValue = null;
    private SemanticObject displayProperty = null;
    private boolean dispProperty = false;
    private int cardinality = 0;
    private boolean cardinalityCheck = false;
    private boolean rangeCheck = false;
    private Resource range = null;
    private HashMap<String, ArrayList<Restriction>> restrictions = null;
    private HashMap<String, SemanticClass> allvalues = null;

    public SemanticProperty(Property prop)
    {
        this.m_prop = prop;
        if (m_prop instanceof OntProperty)
        {
            if (hasInverse())
            {
                m_inverse = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(((OntProperty) m_prop).getInverse());
                m_inverse.isInverse = true;
                m_inverse.m_inverse = this;
                //System.out.println(prop+" hasInverse "+m_inverse);
            }

            restrictions = new HashMap();
            allvalues = new HashMap();
            Iterator<Restriction> it = ((OntProperty) m_prop).listReferringRestrictions();
            while (it.hasNext())
            {
                Restriction restriction = it.next();
                Iterator<OntClass> it2 = restriction.listSubClasses();
                while (it2.hasNext())
                {
                    OntClass ontClass = it2.next();
                    ArrayList<Restriction> list = restrictions.get(ontClass.getURI());
                    if (list == null)
                    {
                        list = new ArrayList();
                        restrictions.put(ontClass.getURI(), list);
                    }
                    list.add(restriction);
                    //System.out.println(prop+" restriction "+ontClass);
                }
            }
        }
    }

    public SemanticObject getSemanticObject()
    {
        return SWBPlatform.getSemanticMgr().getSchema().getSemanticObject(getURI());
    }

    public Property getRDFProperty()
    {
        return m_prop;
    }

    public String getName()
    {
        return m_prop.getLocalName();
    }

    public String getPrefix()
    {
        return m_prop.getModel().getNsURIPrefix(m_prop.getNameSpace());
    }

    public String getPropId()
    {
        return getPrefix() + ":" + getName();
    }

    public String getLabel()
    {
        return getLabel(null);
    }

    public String getLabel(String lang)
    {
        String ret = null;
        if (m_prop instanceof OntProperty)
        {
            ret = ((OntProperty) m_prop).getLabel(lang);
        }
        return ret;
    }

    public String getPropertyCodeName()
    {
        if (m_propertyCodeName == null)
        {
            try
            {
                Property prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_PROPERTYCODENAME).getRDFProperty();
                //System.out.println("Class:"+m_class+" ->"+className);
                m_propertyCodeName = m_prop.getRequiredProperty(prop).getString();
                //System.out.println("Class:"+m_class+" ->"+className);
                if (m_propertyCodeName == null)
                {
                    m_propertyCodeName = SemanticObject.class.getName();
                }
            }
            catch (Exception pnf)
            {
                m_propertyCodeName = getName();
            }
            //log.trace("getClassName:"+m_className);
        }
        return m_propertyCodeName;
    }

    public String getDefaultValue()
    {
        if (m_defaultValue == null)
        {
            Property prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_DEFAULTVALUE).getRDFProperty();
            Statement st = m_prop.getProperty(prop);
            if (st != null)
            {
                m_defaultValue = st.getString();
            }
        }
        return m_defaultValue;
    }

    public String getURI()
    {
        return m_prop.getURI();
    }

    /**
     * Regresa URI codificado para utilizar en ligas de html
     * @return URI Codificado
     */
    public String getEncodedURI()
    {
        return URLEncoder.encode(getURI());
    }

    public SemanticLiteral getRequiredProperty(SemanticProperty prop)
    {
        SemanticLiteral ret = null;
        Statement st = m_prop.getProperty(prop.getRDFProperty());
        if (st != null)
        {
            ret = new SemanticLiteral(st);
        }
        return ret;
    }

    public boolean isLocaleable()
    {
        if (isLocaleable == null)
        {
            isLocaleable = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_LOCALEABLE));
            if (st != null)
            {
                isLocaleable = st.getBoolean();
            }
        }
        return isLocaleable;
    }

    public boolean isUsedAsName()
    {
        if (isUsedAsName == null)
        {
            isUsedAsName = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_CANUSEDASNAME));
            if (st != null)
            {
                isUsedAsName = st.getBoolean();
            }
        }
        return isUsedAsName;
    }

    public boolean isRequired()
    {
        if (isRequired == null)
        {
            isRequired = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_REQUIRED));
            if (st != null)
            {
                isRequired = st.getBoolean();
            }
        }
        return isRequired;
    }

    /**
     * Si esta propiedad se utiliza para definir la relacio padre-hijo en el arbol de navegacion
     * @return
     */
    public boolean isHeraquicalRelation()
    {
        if (isHeraquicalRelation == null)
        {
            isHeraquicalRelation = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_HERARQUICALRELATION));
            if (st != null)
            {
                isHeraquicalRelation = st.getBoolean();
            }
        }
        return isHeraquicalRelation;
    }

    /**
     * Esta propiedad se utiliza para eliminar el objeto relacionado, si el objeto de dominio se elimina
     * @return
     */
    public boolean isRemoveDependency()
    {
        if (isRemoveDependency == null)
        {
            isRemoveDependency = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_REMOVEDEPENDENCY));
            if (st != null)
            {
                isRemoveDependency = st.getBoolean();
            }
        }
        return isRemoveDependency;
    }

    /**
     * Esta propiedad se utiliza para clonar el objeto relacionado, si el objeto de dominio se clona
     * @return
     */
    public boolean isCloneDependency()
    {
        if (isCloneDependency == null)
        {
            isCloneDependency = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_CLONEDEPENDENCY));
            if (st != null)
            {
                isCloneDependency = st.getBoolean();
            }
        }
        return isCloneDependency;
    }

    /**
     * Esta propiedad se utiliza para desabilitar el log de cambios de la propiedad
     * @return
     */
    public boolean isNotObservable()
    {
        if (isNotObservable == null)
        {
            isNotObservable = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_NOTOBSERVABLE));
            if (st != null)
            {
                isNotObservable = st.getBoolean();
            }
        }
        return isNotObservable;
    }

    /**
     * Esta propiedad se utiliza para desabilitar la generacion de código de la propiedad
     * @return
     */
    public boolean isNotCodeGeneration()
    {
        if (isNotCodeGeneration == null)
        {
            isNotCodeGeneration = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_NOTCODEGENERATION));
            if (st != null)
            {
                isNotCodeGeneration = st.getBoolean();
            }
        }
        return isNotCodeGeneration;
    }

    /**
     * Define si la apropiedad es heredable a los hijos
     * @return
     */
    public boolean isInheritProperty()
    {
        if (isInheritProperty == null)
        {
            isInheritProperty = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_INHERITPROPERTY));
            if (st != null)
            {
                isInheritProperty = st.getBoolean();
            }
        }
        return isInheritProperty;
    }

    /**
     * Si esta propiedad se utiliza para definir la relacio hijo-padre en el arbol de navegacion
     * @return
     */
    public boolean isInverseHeraquicalRelation()
    {
        boolean ret = false;
        SemanticProperty inv = getInverse();
        if (inv != null && inv.isHeraquicalRelation())
        {
            ret = true;
        }
        return ret;
    }

    public boolean isExternalInvocation()
    {
        if (isExternalInvocation == null)
        {
            isExternalInvocation = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_EXTERNALINVOCATION));
            if (st != null)
            {
                isExternalInvocation = st.getBoolean();
            }
        }
        return isExternalInvocation;
    }

    public SemanticObject getDisplayProperty()
    {
        if (!dispProperty)
        {
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY));
            if (st != null)
            {
                displayProperty = SemanticObject.createSemanticObject(st.getResource());
                dispProperty = true;
            }
        }
        return displayProperty;
    }

    public String getDisplayName()
    {
        return getDisplayName(null);
    }

    public String getDisplayName(String lang)
    {
        String ret = null;
        SemanticObject obj = getDisplayProperty();
        if (obj != null)
        {
            if (lang != null)
            {
                ret = obj.getProperty(obj.getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL), null, lang);
            }
            else
            {
                ret = obj.getProperty(obj.getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL), null, "es");
            }
            if (ret == null)
            {
                ret = obj.getProperty(obj.getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL));
            }
        }
        if (ret == null)
        {
            ret = getLabel(lang);
        }
        if (ret == null)
        {
            ret = getLabel();
        }
        if (ret == null)
        {
            ret = getName();
        }
        //System.out.println("Prop:"+obj+" "+ret);
        return ret;
    }
//    
//    public String getViewGroup()
//    {
//        String ret=null;
//        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_PROPGROUP));
//        if(st!=null)
//        {
//            return st.getString();
//        }
//        return ret;
//    }       
//    
//    public int getSortIndex()
//    {
//        int ret=99999999;
//        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_PROPINDEX));
//        if(st!=null)
//        {
//            return st.getInt();
//        }
//        return ret;
//    }    

    @Override
    public String toString()
    {
        return m_prop.toString();
    }

    @Override
    public int hashCode()
    {
        return m_prop.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return hashCode() == obj.hashCode();
    }

    public SemanticClass getDomainClass()
    {
        if (hasInverse())
        {
            return m_inverse.getRangeClass();
        }
        SemanticClass ret = null;
        Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_DOMAIN));
        if (stm != null)
        {
            String domclsid = stm.getResource().getURI();
            if (domclsid != null)
            {
                ret = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(domclsid);
                //TODO: eliminar esto cuando se separe el vocabulario por ontologia
                if (ret == null)
                {
                    ret = new SemanticClass(((OntModel) stm.getResource().getModel()).getOntClass(domclsid));
                    SWBPlatform.getSemanticMgr().getVocabulary().registerClass(ret);
                }
            }

        }
        return ret;
    }

    public SemanticClass getRangeClass()
    {
        if (hasInverse())
        {
            return m_inverse.getDomainClass();
        }
        SemanticClass ret = null;
        Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_RANGE));
        if (stm != null)
        {
            ret = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
        }
        return ret;
    }

    public Resource getRange()
    {
        if (!rangeCheck)
        {
            Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_RANGE));
            if (stm != null)
            {
                range = stm.getResource();

            }
            rangeCheck = true;
        }
        return range;
    }

    public int getCardinality()
    {
        if (!cardinalityCheck)
        {
            String n = getPropertyCodeName();
            if (n == null)
            {
                n = getName();
            }
            if (n.startsWith("has"))
            {
                cardinality = 0;
            }
            else
            {
                cardinality = 1;
            }
            cardinalityCheck = true;
        }
        return cardinality;
    }

    public boolean isObjectProperty()
    {
        if (isObjectProperty == null)
        {
            isObjectProperty = false;
            Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
            if (stm != null)
            {
                isObjectProperty = SemanticVocabulary.OWL_OBJECTPROPERTY.equals(stm.getResource().getURI());
                if (!isObjectProperty)
                {
                    OntClass ontClassDataType = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().createClass(SemanticVocabulary.OWL_OBJECTPROPERTY);
                    if (ontClassDataType.hasSubClass(stm.getResource()))
                    {
                        isObjectProperty = Boolean.TRUE;
                    }
                }
            }
        }
        return isObjectProperty;
    }

    public boolean isDataTypeProperty()
    {
        if (isDataTypeProperty == null)
        {
            isDataTypeProperty = false;
            Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
            if (stm != null)
            {
                isDataTypeProperty = SemanticVocabulary.OWL_DATATYPEPROPERTY.equals(stm.getResource().getURI());                
                if (!isDataTypeProperty)
                {
                    OntClass ontClassDataType = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().createClass(SemanticVocabulary.OWL_DATATYPEPROPERTY);
                    if (ontClassDataType.hasSubClass(stm.getResource()))
                    {
                        isDataTypeProperty = Boolean.TRUE;
                    }
                }
            }
        }
        return isDataTypeProperty;
    }

    /**
     * Esta propiedad es la inversa de otra (no genera statements)
     * @return
     */
    public boolean hasInverse()
    {
        if (hasInverse == null)
        {
            hasInverse = false;
            if (m_prop instanceof OntProperty)
            {
                hasInverse = ((OntProperty) m_prop).hasInverse();
            }
        }
        return hasInverse;
    }

    /**
     * Esta propiedad es normal pero tiene una inversa
     * @return
     */
    public boolean isInverseOf()
    {
        return isInverse;
    }

    public SemanticProperty getInverse()
    {
        return m_inverse;
    }

    public boolean isBoolean()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_BOOLEAN))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isInt()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_INT))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isBinary()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_BASE64BINARY))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isLong()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_LONG))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isDate()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_DATE))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isDateTime()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_DATETIME))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isString()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_STRING))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isFloat()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_FLOAT))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isXML()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.RDF_XMLLITERAL))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isDouble()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_DOUBLE))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isByte()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_BYTE))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isShort()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_SHORT))
        {
            ret = true;
        }
        return ret;
    }

    public boolean isNumeric()
    {
        return isInt() || isLong() || isByte() || isDouble() || isFloat() || isShort();
    }

    public SemanticClass getAllValuesFromRestrictionClass(SemanticClass cls)
    {
        SemanticClass rcls = allvalues.get(cls.getURI());
        if (rcls == null)
        {
            ArrayList list = restrictions.get(cls.getURI());
            if (list != null)
            {
                Iterator<Restriction> it = list.iterator();
                while (it.hasNext())
                {
                    Restriction restriction = it.next();
                    if (restriction.isAllValuesFromRestriction())
                    {
                        rcls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(restriction.getProperty(m_prop.getModel().getProperty("http://www.w3.org/2002/07/owl#allValuesFrom")).getResource().getURI());
                    }
                }
            }
            allvalues.put(cls.getURI(), rcls);
        }
        return rcls;
    }
}
