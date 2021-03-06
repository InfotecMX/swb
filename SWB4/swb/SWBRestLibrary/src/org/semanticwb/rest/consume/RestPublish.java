/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import org.semanticwb.model.WebSite;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.base.GenericObjectBase;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;

/**
 *
 * @author victor.lorenzana
 */
public class RestPublish
{

    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_XML = "application/xml";
    private static final String ELEMENT = "element";
    private static final String MEDIA_TYPE = "mediaType";
    private static final String NAME = "name";
    private static final String QUERY = "query";
    private static final String REQUIRED = "required";
    private static final String STATUS = "status";
    private static final String STYLE = "style";
    private static final String TYPE = "type";
    private static final String VALUE = "value";
    private static final String XSD_ANYURI = "xsd:anyURI";
    private static final String XSD_BOOLEAN = "xsd:boolean";
    private static final String XSD_BYTE = "xsd:byte";
    private static final String XSD_DATE = "xsd:date";
    private static final String XSD_DATETIME = "xsd:datetime";
    private static final String XSD_DOUBLE = "xsd:double";
    private static final String XSD_FLOAT = "xsd:float";
    private static final String XSD_INT = "xsd:int";
    private static final String XSD_LONG = "xsd:long";
    private static final String XSD_SHORT = "xsd:short";
    private static final Logger log = SWBUtils.getLogger(RestPublish.class);
    private static final String REST_MODELURI = "rest:modeluri";
    private static final String REST_CLASSURI = "rest:classuri";
    private static final String REST_ID = "rest:id";
    private static final String REST_URI = "rest:uri";
    private static final String XSD_PREFIX = "xsd";
    private static final String REST_RESOURCE_PREFIX = "swbrest";
    //public static final String XLINK_NS = "http://www.w3.org/1999/xlink";
    private static final String XSD_STRING = "xsd:string";
    public static final String REST_RESOURCES_2010 = "http://www.semanticwb.org/rest/2010";
    private static final Set<SemanticClass> classes = Collections.synchronizedSet(new HashSet<SemanticClass>());    
    public static final String WADL_NS_2006 = "http://research.sun.com/wadl/2006/10";
    private static final String WADL_XSD_LOCATION_2006 = "https://wadl.dev.java.net/wadl20061109.xsd";
    public static final String WADL_NS_2009 = "http://wadl.dev.java.net/2009/02";
    private static final String WADL_XSD_LOCATION_2009 = "http://www.w3.org/Submission/wadl/wadl.xsd";
    private String servlet;

    static
    {

        System.setProperty(RepresentationBase.ORG_SEMANTICWB_REST_REPRESENTATIONBASE + XWWWFormUrlEncoded.APPLICATION_XWWW_FORM_URL_ENCODED, XWWWFormUrlEncoded.class.getCanonicalName());
        System.setProperty(RepresentationBase.ORG_SEMANTICWB_REST_REPRESENTATIONBASE + AtomXML.APPLICATION_ATOM_XML, AtomXML.class.getCanonicalName());
        System.setProperty(RepresentationBase.ORG_SEMANTICWB_REST_REPRESENTATIONBASE + MultipartFormData.MULTIPART_FORM_DATA, MultipartFormData.class.getCanonicalName());
    }

    public static void addSemanticClass(SemanticClass clazz)
    {
        classes.add(clazz);
    }

    public static void removeSemanticClass(SemanticClass clazz)
    {
        classes.remove(clazz);
    }

    public static void addSemanticClass(URI clazz)
    {
        SemanticClass clazzToAdd = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(clazz.toString());
        if (clazzToAdd != null)
        {
            log.debug("Adding class " + clazzToAdd.getURI() + " to Rest publication");
            classes.add(clazzToAdd);
        }
    }

    public RestPublish(String servlet)
    {
        this.servlet = servlet;
    }

    public RestPublish()
    {
    }

    public static URI resolve(String spath, URI basePath)
    {
        URI path = basePath;
        try
        {
            URI uriPath = new URI(spath);
            if (!uriPath.isAbsolute())
            {
                URI base = basePath;
                if (!basePath.toString().endsWith("/"))
                {
                    String newpath = basePath.toString() + "/";
                    base = new URI(newpath);
                }
                URI temp = base.resolve(uriPath);
                path = temp.normalize();
            }
            else
            {
                path = uriPath;
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return path;
    }

    private Document getClsMgrXSD(SemanticClass clazz)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        schema.setAttribute("targetNamespace", clazz.getURI());
        //schema.setAttribute("xmlns:xlink", XLINK_NS);
        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(clazz.getURI());
        schema.setAttributeNode(attr);
        doc.appendChild(schema);
        HashSet<SemanticClass> ranges = new HashSet<SemanticClass>();
        addClassManagerResultToXSD(schema, clazz, ranges);
        return doc;
    }

    private Document getErrorXSD()
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        schema.setAttribute("targetNamespace", REST_RESOURCES_2010);
        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(REST_RESOURCES_2010);
        schema.setAttributeNode(attr);

        doc.appendChild(schema);
        schema.setPrefix(XSD_PREFIX);
        Element element = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
        schema.appendChild(element);
        element.setAttribute(NAME, "Error");
        element.setPrefix(XSD_PREFIX);
        Element complexType = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "complexType");
        complexType.setPrefix(XSD_PREFIX);
        element.appendChild(complexType);

        Element sequence = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "sequence");
        sequence.setPrefix(XSD_PREFIX);
        complexType.appendChild(sequence);


        Element message = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
        message.setPrefix(XSD_PREFIX);
        message.setAttribute(NAME, "Message");
        message.setAttribute(TYPE, XSD_STRING);
        sequence.appendChild(message);
        message.setAttribute("minOccurs", "1");
        message.setAttribute("maxOccurs", "unbounded");
        return doc;
    }

    

    

    

    private Document getXSD(final SemanticClass clazz, final String version)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        schema.setAttribute("targetNamespace", clazz.getURI());
        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(clazz.getURI());
        schema.setAttributeNode(attr);

        schema.setAttribute("xmlns:" + clazz.getPrefix(), clazz.getURI());
        //schema.setAttribute("xmlns:xlink", XLINK_NS);
        schema.setAttribute("xmlns:swbrest", REST_RESOURCES_2010);
        doc.appendChild(schema);
        schema.setPrefix(XSD_PREFIX);
        addXSD(schema, clazz);

        return doc;
    }

    private void addClassManagerResultToXSD(final Element schema, final SemanticClass clazz, HashSet<SemanticClass> ranges)
    {
        Document doc = schema.getOwnerDocument();
        try
        {
            Class main = Class.forName(clazz.getClassName());
            Class msgr = getClassManager(main);
            for (Method m : msgr.getDeclaredMethods())
            {
                if (Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) && (m.getName().startsWith("has") || m.getName().startsWith("list")))
                {
                    Class returnType = m.getReturnType();
                    Element methodElement = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
                    methodElement.setPrefix(XSD_PREFIX);
                    methodElement.setAttribute(NAME, m.getName());
                    schema.appendChild(methodElement);
                    if (returnType instanceof Class && isGenericObject(returnType)) // is GenericObject
                    {
                        // gets SemanticClass;
                        Field sclass = ((Class) returnType).getField("sclass");
                        Object objClass = sclass.get(null);
                        if (objClass instanceof SemanticClass)
                        {
                            SemanticClass returnSemanticClazz = (SemanticClass) objClass;
                            addProperties(returnSemanticClazz, methodElement, ranges);
                        }
                    }
                    else if (returnType.equals(Iterator.class)) // GenericIterator
                    {
                        if (m.getGenericReturnType() instanceof ParameterizedType)
                        {
                            if (((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments() != null && ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments().length > 0)
                            {
                                Type actual = ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments()[0];
                                if (actual instanceof Class)
                                {
                                    Class _returnType = (Class) actual;
                                    Field sclass = ((Class) _returnType).getField("sclass");
                                    Object objClass = sclass.get(null);
                                    if (objClass instanceof SemanticClass)
                                    {
                                        SemanticClass returnSemanticClazz = (SemanticClass) objClass;
                                        Element complex = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "complexType");
                                        complex.setPrefix(XSD_PREFIX);
                                        methodElement.appendChild(complex);
                                        Element sequence = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "sequence");
                                        sequence.setPrefix(XSD_PREFIX);
                                        complex.appendChild(sequence);
                                        Element child = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
                                        child.setPrefix(XSD_PREFIX);
                                        child.setAttribute(NAME, returnSemanticClazz.getName());
                                        child.setAttribute(TYPE, XSD_ANYURI);
                                        Element shortURI = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "attribute");
                                        shortURI.setPrefix(XSD_PREFIX);
                                        shortURI.setAttribute(NAME, "shortURI");
                                        shortURI.setAttribute(TYPE, XSD_STRING);
                                        child.appendChild(shortURI);

                                        Element href = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "attribute");
                                        href.setPrefix(XSD_PREFIX);
                                        href.setAttribute(NAME, "href");
                                        href.setAttribute(TYPE, XSD_STRING);
                                        child.appendChild(href);
                                        sequence.appendChild(child);
                                    }
                                }
                            }

                        }

                    }
                    else
                    {
                        if (returnType instanceof Class)
                        {
                            methodElement.setAttribute(TYPE, classToxsd((Class) returnType));
                        }
                    }
                }
            }
        }
        catch (Exception cnfe)
        {
            log.error(cnfe);
        }

    }

    private void addProperties(SemanticClass clazz, Element element, HashSet<SemanticClass> ranges)
    {
        Document doc = element.getOwnerDocument();
        Element complexType = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "complexType");
        complexType.setPrefix(XSD_PREFIX);
        element.appendChild(complexType);

        Element sequence = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "sequence");
        sequence.setPrefix(XSD_PREFIX);
        complexType.appendChild(sequence);

        Iterator<SemanticProperty> props = clazz.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                if (prop.isDataTypeProperty())
                {
                    Element property = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
                    property.setPrefix(XSD_PREFIX);
                    property.setAttribute(NAME, prop.getName());
                    sequence.appendChild(property);
                    String type = XSD_STRING;
                    if (prop.isBoolean())
                    {
                        type = SemanticVocabulary.XMLS_BOOLEAN;
                    }
                    else if (prop.isBinary())
                    {
                        type = SemanticVocabulary.XMLS_BASE64BINARY;
                    }
                    else if (prop.isByte())
                    {
                        type = SemanticVocabulary.XMLS_BYTE;
                    }
                    else if (prop.isDate())
                    {
                        type = SemanticVocabulary.XMLS_DATE;
                    }
                    else if (prop.isDateTime())
                    {
                        type = SemanticVocabulary.XMLS_DATETIME;
                    }
                    else if (prop.isDouble())
                    {
                        type = SemanticVocabulary.XMLS_DOUBLE;
                    }
                    else if (prop.isFloat())
                    {
                        type = SemanticVocabulary.XMLS_FLOAT;
                    }
                    else if (prop.isInt())
                    {
                        type = SemanticVocabulary.XMLS_INT;
                    }
                    else if (prop.isLong())
                    {
                        type = SemanticVocabulary.XMLS_LONG;
                    }
                    else if (prop.isShort())
                    {
                        type = SemanticVocabulary.XMLS_SHORT;
                    }
                    type = type.replace(W3C_XML_SCHEMA_NS_URI + "#", "xsd:");
                    property.setAttribute(TYPE, type);
                    if (prop.getName().startsWith("has"))
                    {
                        property.setAttribute("minOccurs", "0");
                        property.setAttribute("maxOccurs", "unbounded");
                    }

                }
                else
                {
                    SemanticClass range = prop.getRangeClass();
                    if (range != null)
                    {
                        ranges.add(range);
                        Element property = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
                        sequence.appendChild(property);
                        property.setAttribute(NAME, prop.getName());
                        property.setAttribute(TYPE, XSD_ANYURI);
                        if (prop.getName().startsWith("has"))
                        {
                            property.setAttribute("minOccurs", "0");
                            property.setAttribute("maxOccurs", "unbounded");
                        }
                        property.setPrefix(XSD_PREFIX);
                        //property.setAttribute("type", range.getName());
                        Element attribute = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "attribute");
                        attribute.setPrefix(XSD_PREFIX);
                        property.appendChild(attribute);
                        attribute.setAttribute(NAME, "href");
                        attribute.setAttribute(TYPE, XSD_STRING);
                    }
                }
            }
        }
    }

    private void addXSD(final Element schema, final SemanticClass clazz)
    {
        Document doc = schema.getOwnerDocument();
        boolean exists = false;
        NodeList childs = schema.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            Node node = childs.item(i);
            if (node instanceof Element)
            {
                Element element = (Element) node;
                String name = element.getAttribute(NAME);
                if (name.equals(clazz.getName()))
                {
                    exists = true;
                    break;
                }
            }
        }

        if (!exists && clazz.isSWBClass())
        {
            HashSet<SemanticClass> ranges = new HashSet<SemanticClass>();
            Element element = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
            schema.appendChild(element);
            element.setAttribute(NAME, clazz.getName());
            element.setPrefix(XSD_PREFIX);
            addProperties(clazz, element, ranges);
        }
    }

    private void addPOSTMethod(Document doc, Element resource, SemanticClass clazz, String WADL_NS)
    {
        Element method = doc.createElementNS(WADL_NS, "method");
        method.setAttribute(NAME, "POST");
        method.setAttribute("id", "add" + clazz.getName());
        resource.appendChild(method);

        Element request = doc.createElementNS(WADL_NS, "request");
        method.appendChild(request);




        Iterator<SemanticProperty> props = clazz.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                if (prop.isDataTypeProperty())
                {
                    String type = XSD_STRING;
                    if (prop.isBoolean())
                    {
                        type = SemanticVocabulary.XMLS_BOOLEAN;
                    }
                    else if (prop.isBinary())
                    {
                        type = SemanticVocabulary.XMLS_BASE64BINARY;
                    }
                    else if (prop.isByte())
                    {
                        type = SemanticVocabulary.XMLS_BYTE;
                    }
                    else if (prop.isDate())
                    {
                        type = SemanticVocabulary.XMLS_DATE;
                    }
                    else if (prop.isDateTime())
                    {
                        type = SemanticVocabulary.XMLS_DATETIME;
                    }
                    else if (prop.isDouble())
                    {
                        type = SemanticVocabulary.XMLS_DOUBLE;
                    }
                    else if (prop.isFloat())
                    {
                        type = SemanticVocabulary.XMLS_FLOAT;
                    }
                    else if (prop.isInt())
                    {
                        type = SemanticVocabulary.XMLS_INT;
                    }
                    else if (prop.isLong())
                    {
                        type = SemanticVocabulary.XMLS_LONG;
                    }
                    else if (prop.isShort())
                    {
                        type = SemanticVocabulary.XMLS_SHORT;
                    }
                    type = type.replace(W3C_XML_SCHEMA_NS_URI + "#", "xsd:");
                    Element param = doc.createElementNS(WADL_NS, "param");
                    param.setAttribute(NAME, prop.getName());
                    param.setAttribute(STYLE, QUERY);
                    param.setAttribute(TYPE, type);
                    if (prop.getName().startsWith("has"))
                    {
                        param.setAttribute("repeating", "true");
                    }
                    request.appendChild(param);
                }
                else
                {
                    Element param = doc.createElementNS(WADL_NS, "param");
                    param.setAttribute(NAME, prop.getName());
                    param.setAttribute(STYLE, QUERY);
                    param.setAttribute(TYPE, XSD_ANYURI);
                    if (prop.getName().startsWith("has"))
                    {
                        param.setAttribute("repeating", "true");
                    }
                    request.appendChild(param);
                }
            }

        }
        Element param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, REST_CLASSURI);
        param.setAttribute(STYLE, QUERY);
        param.setAttribute("fixed", clazz.getURI());
        param.setAttribute(TYPE, XSD_STRING);
        param.setAttribute(REQUIRED, "true");
        request.appendChild(param);

        param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, REST_MODELURI);
        param.setAttribute(STYLE, QUERY);
        param.setAttribute(TYPE, XSD_STRING);
        param.setAttribute(REQUIRED, "true");
        request.appendChild(param);

        Iterator<WebSite> sites = WebSite.ClassMgr.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            if (!(site.getURI().equals(SWBContext.getAdminWebSite().getURI()) || site.getURI().equals(SWBContext.getGlobalWebSite().getURI())))
            {
                Element option = doc.createElementNS(WADL_NS, "option");
                option.setAttribute(VALUE, site.getShortURI());
                param.appendChild(option);
            }
        }

        if (!clazz.isAutogenId())
        {
            param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, REST_ID);
            param.setAttribute(STYLE, QUERY);
            param.setAttribute(TYPE, XSD_STRING);
            param.setAttribute(REQUIRED, "true");
            request.appendChild(param);
        }

        configureCommonsElements(method, request, WADL_NS, clazz.getPrefix() + ":Created");
    }

    private void addPUTMethod(Document doc, Element resource, SemanticClass clazz, String WADL_NS)
    {
        Element method = doc.createElementNS(WADL_NS, "method");
        method.setAttribute(NAME, "PUT");
        method.setAttribute("id", "update" + clazz.getName());
        resource.appendChild(method);

        Element request = doc.createElementNS(WADL_NS, "request");
        method.appendChild(request);



        Element param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, REST_URI);
        param.setAttribute(STYLE, QUERY);
        param.setAttribute(TYPE, XSD_ANYURI);

        request.appendChild(param);

        Iterator<SemanticProperty> props = clazz.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                if (prop.isDataTypeProperty())
                {
                    String type = XSD_STRING;
                    if (prop.isBoolean())
                    {
                        type = SemanticVocabulary.XMLS_BOOLEAN;
                    }
                    else if (prop.isBinary())
                    {
                        type = SemanticVocabulary.XMLS_BASE64BINARY;
                    }
                    else if (prop.isByte())
                    {
                        type = SemanticVocabulary.XMLS_BYTE;
                    }
                    else if (prop.isDate())
                    {
                        type = SemanticVocabulary.XMLS_DATE;
                    }
                    else if (prop.isDateTime())
                    {
                        type = SemanticVocabulary.XMLS_DATETIME;
                    }
                    else if (prop.isDouble())
                    {
                        type = SemanticVocabulary.XMLS_DOUBLE;
                    }
                    else if (prop.isFloat())
                    {
                        type = SemanticVocabulary.XMLS_FLOAT;
                    }
                    else if (prop.isInt())
                    {
                        type = SemanticVocabulary.XMLS_INT;
                    }
                    else if (prop.isLong())
                    {
                        type = SemanticVocabulary.XMLS_LONG;
                    }
                    else if (prop.isShort())
                    {
                        type = SemanticVocabulary.XMLS_SHORT;
                    }
                    type = type.replace(W3C_XML_SCHEMA_NS_URI + "#", "xsd:");
                    param = doc.createElementNS(WADL_NS, "param");
                    param.setAttribute(NAME, prop.getName());
                    param.setAttribute(STYLE, QUERY);
                    param.setAttribute(TYPE, type);
                    if (prop.getName().startsWith("has"))
                    {
                        param.setAttribute("repeating", "true");
                    }
                    request.appendChild(param);
                }
                else
                {
                    param = doc.createElementNS(WADL_NS, "param");
                    param.setAttribute(NAME, prop.getName());
                    param.setAttribute(STYLE, QUERY);
                    param.setAttribute(TYPE, XSD_ANYURI);
                    if (prop.getName().startsWith("has"))
                    {
                        param.setAttribute("repeating", "true");
                    }
                    request.appendChild(param);
                }
            }

        }
        configureCommonsElements(method, request, WADL_NS, clazz.getPrefix() + ":Updated");
    }

    private void createMethod(Document doc, Element resource, SemanticClass clazz, Method m, String WADL_NS, String id)
    {
        Element method = doc.createElementNS(WADL_NS, "method");
        method.setAttribute(NAME, "GET");
        method.setAttribute("id", id);
        resource.appendChild(method);

        Element request = doc.createElementNS(WADL_NS, "request");
        method.appendChild(request);

        Element param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, "method");
        param.setAttribute(STYLE, QUERY);
        param.setAttribute("fixed", m.getName());
        param.setAttribute(TYPE, XSD_STRING);
        param.setAttribute(REQUIRED, "true");
        request.appendChild(param);

        param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, "classuri");
        param.setAttribute(STYLE, QUERY);
        param.setAttribute("fixed", clazz.getURI());
        param.setAttribute(TYPE, XSD_STRING);
        param.setAttribute(REQUIRED, "true");
        request.appendChild(param);

        for (Class classparam : m.getParameterTypes())
        {
            if (isGenericObject(classparam))
            {
                param = doc.createElementNS(WADL_NS, "param");
                param.setAttribute(NAME, classparam.getName().toLowerCase());
                param.setAttribute(STYLE, QUERY);
                param.setAttribute(TYPE, XSD_ANYURI);
                param.setAttribute(REQUIRED, "true");
                request.appendChild(param);
            }
            else
            {
                param = doc.createElementNS(WADL_NS, "param");
                param.setAttribute(NAME, classparam.getName().toLowerCase());
                param.setAttribute(STYLE, QUERY);
                param.setAttribute(TYPE, classToxsd(classparam));
                param.setAttribute(REQUIRED, "true");
                request.appendChild(param);
            }
        }
        configureCommonsElements(method, request, WADL_NS, clazz.getPrefix() + ":" + m.getName());
    }

    private static boolean isGenericObject(Class clazz)
    {
        try
        {
            clazz.asSubclass(GenericObject.class);
            return true;
        }
        catch (ClassCastException e)
        {
            //e.printStackTrace();
            return false;
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return false;
    }

    public static String classToxsd(Class clazz)
    {
        String getXSDType = XSD_STRING;
        if (clazz.equals(Boolean.class))
        {
            getXSDType = XSD_BOOLEAN;
        }
        else if (clazz.equals(Byte.class))
        {
            getXSDType = XSD_BYTE;
        }
        else if (clazz.equals(Date.class))
        {
            getXSDType = XSD_DATE;
        }
        else if (clazz.equals(Timestamp.class))
        {
            getXSDType = XSD_DATETIME;
        }
        else if (clazz.equals(Double.class))
        {
            getXSDType = XSD_DOUBLE;
        }
        else if (clazz.equals(Float.class))
        {
            getXSDType = XSD_FLOAT;
        }
        else if (clazz.equals(Integer.class))
        {
            getXSDType = XSD_INT;
        }
        else if (clazz.equals(Long.class))
        {
            getXSDType = XSD_LONG;
        }
        else if (clazz.equals(Short.class))
        {
            getXSDType = XSD_SHORT;
        }
        return getXSDType;

    }

    public static Class xsdToClass(String xsdType)
    {
        xsdType = xsdType.replace("xsd:", SemanticVocabulary.XMLS_URI);
        if (xsdType.equals(SemanticVocabulary.XMLS_BOOLEAN))
        {
            return Boolean.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_DATE))
        {
            return Date.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_DATETIME))
        {
            return Timestamp.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_BYTE))
        {
            return Byte.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_DOUBLE))
        {
            return Double.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_FLOAT))
        {
            return Float.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_INT) || xsdType.equals(SemanticVocabulary.XMLS_INTEGER))
        {
            return Integer.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_LONG))
        {
            return Long.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_SHORT))
        {
            return Short.class;
        }
        if (xsdType.equals(SemanticVocabulary.XMLS_URI + "anyURI"))
        {
            return URI.class;
        }

        return String.class;


    }

    private void addDELETEMethod(Document doc, Element resource, SemanticClass clazz, String WADL_NS)
    {
        Element method = doc.createElementNS(WADL_NS, "method");
        method.setAttribute(NAME, "DELETE");
        method.setAttribute("id", "delete" + clazz.getName());
        resource.appendChild(method);

        Element request = doc.createElementNS(WADL_NS, "request");
        method.appendChild(request);

        Element param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, "uri");
        param.setAttribute(STYLE, QUERY);
        param.setAttribute(TYPE, XSD_STRING);
        param.setAttribute(REQUIRED, "true");
        request.appendChild(param);
        configureCommonsElements(method, request, WADL_NS, clazz.getPrefix() + ":Deleted");
    }

    private void configureCommonsElements(Element method, Element request, String WADL_NS, String elementType)
    {
        Document doc = method.getOwnerDocument();
        Element param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, "format");
        param.setAttribute(STYLE, QUERY);
        request.appendChild(param);

        Element option = doc.createElementNS(WADL_NS, "option");
        option.setAttribute(VALUE, "xml");
        option.setAttribute(MEDIA_TYPE, APPLICATION_XML);
        param.appendChild(option);

        option = doc.createElementNS(WADL_NS, "option");
        option.setAttribute(VALUE, "json");
        option.setAttribute(MEDIA_TYPE, APPLICATION_JSON);
        param.appendChild(option);

        if (WADL_NS.equals(WADL_NS_2009))
        {
            Element error = doc.createElementNS(WADL_NS, "response");
            method.appendChild(error);
            Element representation = doc.createElementNS(WADL_NS, "representation");
            error.appendChild(representation);
            representation.setAttribute(MEDIA_TYPE, APPLICATION_XML);
            error.setAttribute(STATUS, "400");
            representation.setAttribute(ELEMENT, REST_RESOURCE_PREFIX + ":Error");
        }

        Element response = doc.createElementNS(WADL_NS, "response");
        method.appendChild(response);
        Element representation = doc.createElementNS(WADL_NS, "representation");
        response.appendChild(representation);
        representation.setAttribute(MEDIA_TYPE, APPLICATION_XML);
        if (WADL_NS.equals(WADL_NS_2009))
        {
            response.setAttribute(STATUS, "200");
            representation.setAttribute(ELEMENT, elementType);
        }

        representation = doc.createElementNS(WADL_NS, "representation");
        response.appendChild(representation);
        representation.setAttribute(MEDIA_TYPE, APPLICATION_JSON);
    }

    private void addGetMethod(Document doc, Element resource, SemanticClass clazz, String WADL_NS)
    {
        Element method = doc.createElementNS(WADL_NS, "method");
        method.setAttribute(NAME, "GET");
        method.setAttribute("id", "get" + clazz.getName());
        resource.appendChild(method);

        Element request = doc.createElementNS(WADL_NS, "request");
        method.appendChild(request);

        Element param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, "uri");
        param.setAttribute(STYLE, QUERY);
        param.setAttribute(TYPE, XSD_STRING);
        param.setAttribute(REQUIRED, "true");
        request.appendChild(param);

        configureCommonsElements(method, request, WADL_NS, clazz.getPrefix() + ":" + clazz.getName());
    }

    private void executeMethod(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Document doc = null;
        try
        {
            doc = getExecuteMethod(request);
        }
        catch (Exception e)
        {
            doc = getError(e.getMessage());
        }
        showDocument(response, doc);
    }

    private void showDocument(HttpServletResponse response, Document doc) throws IOException
    {
        PrintWriter out = response.getWriter();
        String charset = Charset.defaultCharset().name();
        response.setContentType(APPLICATION_XML + "; charset=" + charset);
        String xml = SWBUtils.XML.domToXml(doc, charset, true);
        out.print(xml);
        out.close();
    }

    private void showJSON(HttpServletResponse response, JSONObject jSONObject) throws IOException
    {
        PrintWriter out = response.getWriter();
        String charset = Charset.defaultCharset().name();
        response.setContentType(APPLICATION_XML + "; charset=" + charset);
        String xml = jSONObject.toString();
        out.print(xml);
        out.close();
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String version = "2009";
        if ("2006".equals(request.getParameter("version")))
        {
            version = "2006";
        }
        Document doc = getWADL(request, version);
        showDocument(response, doc);
    }

    private void showCLSMGRXSD(HttpServletResponse response, SemanticClass clazz) throws IOException
    {
        Document doc = getClsMgrXSD(clazz);
        showDocument(response, doc);
    }

    private void showCreted(HttpServletRequest request, HttpServletResponse response, SemanticObject obj) throws IOException
    {
        if ("json".equals(request.getParameter("format")))
        {
            try
            {
                showJSON(response, getCreatedAsJSON(obj.getURI()));
            }
            catch (Exception e)
            {
                showError(request, response, e.getMessage());
            }
        }
        else
        {
            Document doc = getCreatedAsXML(obj);
            showDocument(response, doc);
        }

    }

    private void showUpdated(HttpServletRequest request, HttpServletResponse response,SemanticObject obj) throws IOException
    {
        if ("json".equals(request.getParameter("format")))
        {
            try
            {
                showJSON(response, getUpdatedAsJSON(true));
            }
            catch (Exception e)
            {
                showError(request, response, e.getMessage());
            }
        }
        else
        {
            Document doc = getUpdatedAsXml(obj);
            showDocument(response, doc);
        }

    }

    private void showObject(HttpServletRequest request, HttpServletResponse response, SemanticObject obj) throws IOException
    {


        if ("json".equals(request.getParameter("format")))
        {

            try
            {
                showJSON(response, serializeAsJSON(obj, request));
            }
            catch (Exception e)
            {
                showError(request, response, e.getMessage());
            }
        }
        else
        {
            Document doc = serializeAsXML(obj, request);
            showDocument(response, doc);
        }

    }

    /*private void showXSD(HttpServletRequest request, HttpServletResponse response, SemanticClass clazz) throws IOException
    {
        String version = "2009";
        if ("2006".equals(request.getParameter("version")))
        {
            version = "2006";
        }
        Document doc = getXSD(clazz, version);
        showDocument(response, doc);
    }*/

    private void serializeAsJSON(SemanticObject obj, JSONObject jSONObject, HttpServletRequest request) throws JSONException
    {
        Iterator<SemanticProperty> props = obj.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                if (prop.isDataTypeProperty())
                {
                    if (prop.getName().startsWith("has"))
                    {
                        Iterator<SemanticLiteral> values = obj.listLiteralProperties(prop);
                        while (values.hasNext())
                        {
                            SemanticLiteral value = values.next();
                            String name = prop.getName();
                            String data = value.getString();
                            jSONObject.accumulate(name, data);
                        }
                    }
                    else
                    {
                        String name = prop.getName();
                        String data = obj.getProperty(prop);
                        jSONObject.accumulate(name, data);
                    }
                }
                else
                {
                    SemanticClass range = prop.getRangeClass();
                    if (range != null)
                    {
                        if (prop.getName().startsWith("has"))
                        {
                            Iterator<SemanticObject> values = obj.listObjectProperties(prop);
                            while (values.hasNext())
                            {
                                SemanticObject value = values.next();
                                if (value != null)
                                {
                                    String name = prop.getName();
                                    JSONObject data = new JSONObject();
                                    data.put("uri", value.getURI());
                                    data.put("href", getPathForObject(value, request));
                                    jSONObject.accumulate(name, data);
                                }
                            }
                        }
                        else
                        {
                            SemanticObject value = obj.getObjectProperty(prop);
                            if (value != null)
                            {
                                String name = prop.getName();
                                JSONObject data = new JSONObject();
                                data.put("uri", value.getURI());
                                data.put("href", getPathForObject(value, request));
                                jSONObject.accumulate(name, data);
                            }
                        }
                    }
                }
            }
        }
    }

    private void serialize(SemanticObject obj, Element name, HttpServletRequest request)
    {
        Document doc = name.getOwnerDocument();
        Iterator<SemanticProperty> props = obj.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                if (prop.isDataTypeProperty())
                {
                    if (prop.getName().startsWith("has"))
                    {
                        Iterator<SemanticLiteral> values = obj.listLiteralProperties(prop);
                        while (values.hasNext())
                        {
                            SemanticLiteral value = values.next();
                            Element eprop = doc.createElementNS(obj.getSemanticClass().getURI(), prop.getName());
                            name.appendChild(eprop);
                            Text tvalue = doc.createTextNode(value.getString());
                            eprop.appendChild(tvalue);
                        }
                    }
                    else
                    {
                        Element eprop = doc.createElementNS(obj.getSemanticClass().getURI(), prop.getName());
                        name.appendChild(eprop);
                        Text value = doc.createTextNode(obj.getProperty(prop));
                        eprop.appendChild(value);
                    }
                }
                else
                {
                    SemanticClass range = prop.getRangeClass();
                    if (range != null)
                    {
                        if (prop.getName().startsWith("has"))
                        {
                            Iterator<SemanticObject> values = obj.listObjectProperties(prop);
                            while (values.hasNext())
                            {
                                SemanticObject value = values.next();
                                if (value != null)
                                {
                                    Element eprop = doc.createElementNS(obj.getSemanticClass().getURI(), prop.getName());
                                    name.appendChild(eprop);
                                    //eprop.setAttributeNS(XLINK_NS, "xlink:href", getPathForObject(value, request));
                                    Text data = doc.createTextNode(value.getURI());
                                    eprop.appendChild(data);
                                }
                            }
                        }
                        else
                        {
                            Element eprop = doc.createElementNS(obj.getSemanticClass().getURI(), prop.getName());
                            name.appendChild(eprop);
                            SemanticObject value = obj.getObjectProperty(prop);
                            if (value != null)
                            {
                                //eprop.setAttributeNS(XLINK_NS, "xlink:href", getPathForObject(value, request));
                                Text data = doc.createTextNode(value.getURI());
                                eprop.appendChild(data);
                            }
                        }
                    }
                }
            }
        }
    }

    private JSONObject serializeAsJSON(SemanticObject obj, HttpServletRequest request) throws Exception
    {
        JSONObject jSONObject = new JSONObject();
        serializeAsJSON(obj, jSONObject, request);
        return jSONObject;
    }

    private Document serializeAsXML(SemanticObject obj, HttpServletRequest request)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element name = doc.createElementNS(obj.getSemanticClass().getURI(), obj.getSemanticClass().getName());
        //name.setAttribute("xmlns:xlink", XLINK_NS);
        name.setAttribute("xmlns", obj.getSemanticClass().getURI());
        doc.appendChild(name);
        serialize(obj, name, request);
        return doc;
    }

    private Document getWADL(HttpServletRequest servletRequest, String version)
    {
        String WADL_NS = WADL_NS_2009;
        String WADL_XSD_LOCATION = WADL_XSD_LOCATION_2009;
        if ("2006".equals(version))
        {
            WADL_NS = WADL_NS_2006;
            WADL_XSD_LOCATION = WADL_XSD_LOCATION_2006;
        }
        Document doc = SWBUtils.XML.getNewDocument();
        Element application = doc.createElementNS(WADL_NS, "application");


        application.setAttribute("xmlns:xsi", W3C_XML_SCHEMA_INSTANCE_NS_URI);
        application.setAttribute("xmlns:xsd", W3C_XML_SCHEMA_NS_URI);
        //application.setAttribute("xmlns:swbrest", REST_RESOURCES_2010);

        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(WADL_NS);
        application.setAttributeNode(attr);

        Attr schemaLocation = doc.createAttributeNS(W3C_XML_SCHEMA_INSTANCE_NS_URI, "schemaLocation");
        schemaLocation.setValue(WADL_NS + " " + WADL_XSD_LOCATION);
        schemaLocation.setPrefix("xsi");
        application.setAttributeNodeNS(schemaLocation);


        Set<String> prefixes = new HashSet<String>();

        for (SemanticClass clazz : classes)
        {
            if (!prefixes.contains(clazz.getPrefix()))
            {
                application.setAttribute("xmlns:" + clazz.getPrefix(), clazz.getURI());
                prefixes.add(clazz.getPrefix());
            }
        }



        Element grammars = doc.createElementNS(WADL_NS, "grammars");
        application.appendChild(grammars);

        Element include = doc.createElementNS(WADL_NS, "include");
        grammars.appendChild(include);
        include.setAttribute("href", servletRequest.getRequestURI() + "?error=xsd");

        for (SemanticClass clazz : classes)
        {
            try
            {
                include = doc.createElementNS(WADL_NS, "include");
                grammars.appendChild(include);
                include.setAttribute("href", servletRequest.getRequestURI() + "?clsmgr=xsd&classuri=" + URLEncoder.encode(clazz.getURI(), "utf-8"));
            }
            catch (Exception e)
            {
                log.error(e);
            }


            String xsd = URLEncoder.encode(clazz.getURI());
            include = doc.createElementNS(WADL_NS, "include");
            grammars.appendChild(include);
            include.setAttribute("href", servletRequest.getRequestURI() + "?xsd=" + xsd);
        }

        doc.appendChild(application);
        Element resources = doc.createElementNS(WADL_NS, "resources");

        String base = servletRequest.getRequestURI();
        int pos = base.indexOf(".");
        if (pos != -1)
        {
            pos = base.lastIndexOf("/");
            if (pos != -1)
            {
                base = base.substring(0, pos);
            }
        }

        resources.setAttribute("base", base);
        base = servletRequest.getRequestURI();

        pos = base.lastIndexOf("/");
        if (pos != -1)
        {
            base = base.substring(pos + 1);
        }


        String resourcepath = base;
        application.appendChild(resources);
        for (SemanticClass clazz : classes)
        {
            Element resource = doc.createElementNS(WADL_NS, "resource");
            resources.appendChild(resource);

            String idresource = clazz.getPrefix() + "_" + clazz.getName();
            if (servlet != null)
            {
                resourcepath = idresource;
            }
            resource.setAttribute("path", resourcepath);
            resource.setAttribute("id", idresource);
            addGetMethod(doc, resource, clazz, WADL_NS);
            addPUTMethod(doc, resource, clazz, WADL_NS);
            addPOSTMethod(doc, resource, clazz, WADL_NS);
            addDELETEMethod(doc, resource, clazz, WADL_NS);

            Element functions = doc.createElementNS(WADL_NS, "resource");
            functions.setAttribute("path", "functions");
            functions.setAttribute("id", "functions");
            resource.appendChild(functions);

            Element resourceMethodsModel = doc.createElementNS(WADL_NS, "resource");
            resourceMethodsModel.setAttribute("path", "model");
            resourceMethodsModel.setAttribute("id", "model");
            functions.appendChild(resourceMethodsModel);



            try
            {
                Class clazzjava = Class.forName(clazz.getClassName());
                Class superclazz = clazzjava.getSuperclass();
                if (superclazz.getName().endsWith("Base"))
                {
                    for (Class c : superclazz.getDeclaredClasses())
                    {
                        if (c.getName().endsWith("ClassMgr"))
                        {
                            Class mgr = c;

                            for (Method m : mgr.getDeclaredMethods())
                            {
                                if (Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) && (m.getName().startsWith("has") || m.getName().startsWith("list")))
                                {
                                    String id = m.getName();
                                    if (hasModel(m))
                                    {
                                        createMethod(doc, resourceMethodsModel, clazz, m, WADL_NS, id);
                                    }
                                    else
                                    {
                                        createMethod(doc, functions, clazz, m, WADL_NS, id);
                                    }

                                }
                            }
                            break;
                        }
                    }
                }
            }
            catch (ClassNotFoundException clnfe)
            {
                log.error(clnfe);
            }


        }
        return doc;
    }

    private boolean hasModel(Method method)
    {
        for (Class parameterClass : method.getParameterTypes())
        {
            if (parameterClass.equals(org.semanticwb.model.SWBModel.class))
            {
                return true;
            }
        }
        return false;
    }

    public Document getError(String error)
    {
        String[] errors = new String[1];
        errors[0] = error;
        return getError(errors);
    }

    public Document getError(String[] errors)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element eError = doc.createElement("Error");

        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        eError.setAttributeNode(xmlns);

        doc.appendChild(eError);
        for (String error : errors)
        {
            Element message = doc.createElement("Message");
            Text text = doc.createTextNode(error);
            message.appendChild(text);
            eError.appendChild(message);
        }
        return doc;
    }

    private Class getClassManager(Class clazz) throws Exception
    {
        Class superclazz = clazz.getSuperclass();
        if (superclazz.getName().endsWith("Base"))
        {
            for (Class c : superclazz.getDeclaredClasses())
            {
                if (c.getName().endsWith("ClassMgr"))
                {
                    return c;
                }
            }
        }
        throw new Exception("The class is not a SemanticClass");
    }

    private boolean checkParameters(Method m, HttpServletRequest request)
    {
        for (Class parameter : m.getParameterTypes())
        {
            String parameterName = parameter.getName();
            String value = request.getParameter(parameterName);
            if (value != null)
            {
                try
                {
                    Object parameterValue = convert(value, parameter);
                    if (parameterValue == null)
                    {
                        return false;
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    private Object convert(String value, Class clazz) throws Exception
    {
        if (isGenericObject(clazz))
        {
            GenericObject go = SemanticObject.createSemanticObject(value).createGenericInstance();
            return go;
        }
        else
        {
            String type = classToxsd(clazz);
            return get(value, type);
        }
    }

    private Object[] getParameters(Method m, HttpServletRequest request) throws Exception
    {
        ArrayList<Object> getParameters = new ArrayList<Object>();
        for (Class parameter : m.getParameterTypes())
        {
            String parameterName = parameter.getName();
            String value = request.getParameter(parameterName);
            if (value != null)
            {
                Object parameterValue = convert(value, parameter);
                getParameters.add(parameterValue);
            }
            else
            {
                throw new Exception();
            }
        }
        return getParameters.toArray(new Object[getParameters.size()]);
    }

    public Document getExecuteMethod(HttpServletRequest request, String methodName, SemanticClass clazz) throws Exception
    {
        String namespace=clazz.getURI();
        Class javaClazz = Class.forName(clazz.getClassName());
        Class mgr = getClassManager(javaClazz);
        for (Method m : mgr.getMethods())
        {

            if (m.getName().equals(methodName))
            {
                if (checkParameters(m, request) && Modifier.isStatic(m.getModifiers()) && Modifier.isPublic(m.getModifiers()))
                {
                    try
                    {
                        Object[] args = getParameters(m, request);
                        Object resinvoke = m.invoke(null, args);
                        if (resinvoke != null)
                        {
                            if (resinvoke instanceof SemanticObject)
                            {
                                SemanticObject so = (SemanticObject) resinvoke;
                                return serializeAsXML(so, request);
                            }
                            if (resinvoke instanceof GenericIterator)
                            {
                                Document doc = SWBUtils.XML.getNewDocument();
                                Element res = doc.createElementNS(namespace, m.getName());
                                doc.appendChild(res);

                                Attr xmlns = doc.createAttribute("xmlns");
                                xmlns.setValue(clazz.getURI());
                                res.setAttributeNode(xmlns);



                                //res.setAttribute("xmlns:xlink", XLINK_NS);
                                GenericIterator gi = (GenericIterator) resinvoke;
                                if (gi.hasNext())
                                {
                                    GenericObject go = gi.next();
                                    SemanticObject obj = go.getSemanticObject();
                                    Element name = doc.createElementNS(obj.getSemanticClass().getURI(), obj.getSemanticClass().getName());
                                    //name.setAttributeNS(XLINK_NS, "xlink:href", getPathForObject(obj, request));
                                    name.setAttribute("shortURI", obj.getShortURI());

                                    Text data = doc.createTextNode(obj.getURI());
                                    name.appendChild(data);
                                    res.appendChild(name);
                                }
                                while (gi.hasNext())
                                {
                                    GenericObject go = gi.next();
                                    SemanticObject obj = go.getSemanticObject();
                                    Element name = doc.createElementNS(namespace, obj.getSemanticClass().getName());
                                    //name.setAttributeNS(XLINK_NS, "xlink:href", getPathForObject(obj, request));
                                    name.setAttribute("shortURI", obj.getShortURI());

                                    Text data = doc.createTextNode(obj.getURI());
                                    name.appendChild(data);
                                    res.appendChild(name);
                                }
                                return doc;
                            }
                        }
                        else
                        {
                            Document doc = SWBUtils.XML.getNewDocument();
                            return doc;
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                        throw e;
                    }
                }
            }
        }
        throw new Exception("The method " + methodName + " was not found");
    }

    private String getPathForObject(SemanticObject obj, HttpServletRequest request)
    {
        String path = request.getRequestURI() + "?uri=" + obj.getShortURI();
        if (servlet != null)
        {
            path = SWBPlatform.getContextPath() + "/" + servlet + "/" + obj.getSemanticClass().getPrefix() + "_" + obj.getSemanticClass().getName() + "/" + obj.getShortURI();
        }
        return path;
    }

    public Document getExecuteMethod(HttpServletRequest request, String methodName) throws Exception
    {

        if (methodName.startsWith("_"))
        {
            methodName = methodName.substring(1);
        }
        String classUri = request.getParameter("classuri");
        SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(classUri);
        if (clazz == null)
        {
            throw new Exception("The class " + classUri + " was not found");
        }
        return getExecuteMethod(request, methodName, clazz);


    }

    public Document getExecuteMethod(HttpServletRequest request) throws Exception
    {
        String methodName = request.getParameter("method");
        if (methodName == null)
        {
            throw new Exception("The parameter method was not found");
        }
        if (methodName.startsWith("_"))
        {
            methodName = methodName.substring(1);
        }
        return getExecuteMethod(request, methodName);

    }

    public JSONObject getCreatedAsJSON(String uri) throws RestException
    {
        JSONObject jSONObject = new JSONObject();
        try
        {
            jSONObject.put("Created", uri);
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
        return jSONObject;
    }

    public Document getCreatedAsXML(SemanticObject obj)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element created = doc.createElement("Created");
        doc.appendChild(created);
        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(obj.getURI());
        created.appendChild(xmlns);
        Text data = doc.createTextNode(obj.getURI());
        created.appendChild(created);
        return doc;
    }

    public JSONObject getUpdatedAsJSON(boolean isUpdated) throws RestException
    {
        JSONObject jSONObject = new JSONObject();
        try
        {
            jSONObject.put("Updated", isUpdated);
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
        return jSONObject;
    }

    public Document getUpdatedAsXml(SemanticObject obj)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element updated = doc.createElement("Updated");
        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(obj.getSemanticClass().getURI());
        updated.appendChild(xmlns);
        doc.appendChild(updated);
        Text data = doc.createTextNode(Boolean.toString(true));
        updated.appendChild(data);
        return doc;
    }

    public JSONObject getDeletedAsJSON(boolean isdeleted) throws RestException
    {
        JSONObject jSONObject = new JSONObject();
        try
        {
            jSONObject.put("Deleted", isdeleted);
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
        return jSONObject;
    }

    public Document getDeletedAsXML(SemanticObject obj)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element deleted = doc.createElement("Deleted");
        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(obj.getSemanticClass().getURI());
        deleted.appendChild(xmlns);
        doc.appendChild(deleted);
        Text data = doc.createTextNode(Boolean.toString(true));
        deleted.appendChild(data);
        return doc;
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String msg) throws IOException
    {
        String[] error = new String[1];
        error[0] = msg;
        showError(response, error);
    }

    private void showError(HttpServletResponse response, String[] msg) throws IOException
    {
        Document doc = getError(msg);
        showDocument(response, doc);
    }
    private void showErrorXSD(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Document doc = getErrorXSD();
        showDocument(response, doc);
    }

    public static Object get(String value, String dataType) throws Exception
    {
        dataType = dataType.replace("xsd:", SemanticVocabulary.XMLS_URI);
        if (dataType.equals(SemanticVocabulary.XMLS_BOOLEAN))
        {
            return Boolean.parseBoolean(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DATE))
        {
            return SWBUtils.TEXT.iso8601DateParse(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DATETIME))
        {
            return Timestamp.valueOf(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_BYTE))
        {
            return Byte.parseByte(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DOUBLE))
        {
            return Double.parseDouble(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_FLOAT))
        {
            return Float.parseFloat(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_INT) || dataType.equals(SemanticVocabulary.XMLS_INTEGER))
        {
            return Integer.parseInt(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_LONG))
        {
            return Long.parseLong(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_SHORT))
        {
            return Short.parseShort(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_URI))
        {
            String uri = value;
            uri = fixURI(uri);
            SemanticObject ovalue = SemanticObject.createSemanticObject(uri);
            if (ovalue == null)
            {
                throw new Exception("The object with uri " + value + " was not found");
            }
            return ovalue;
        }
        return value;
    }

    private void validate(String value, String dataType) throws Exception
    {
        dataType = dataType.replace("xsd:", SemanticVocabulary.XMLS_URI);
        if (dataType.equals(SemanticVocabulary.XMLS_BOOLEAN))
        {
            if (!(value.equals("true") || value.equals("false")))
            {
                throw new Exception("The value is invalid");
            }
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DATE))
        {
            SWBUtils.TEXT.iso8601DateParse(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DATETIME))
        {
            Timestamp.valueOf(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_BYTE))
        {
            Byte.parseByte(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DOUBLE))
        {
            Double.parseDouble(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_FLOAT))
        {
            Float.parseFloat(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_INT) || dataType.equals(SemanticVocabulary.XMLS_INTEGER))
        {
            Integer.parseInt(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_LONG))
        {
            Long.parseLong(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_SHORT))
        {
            Short.parseShort(value);
        }
    }

    private static String fixURI(String uri)
    {
        if (uri != null && uri.indexOf(":") != -1)
        {
            if (uri.indexOf("%3A") != -1)
            {
                uri = uri.replace("%3A", ":");
            }
            if (uri.indexOf("%23") != -1)
            {
                uri = uri.replace("%23", "#");
            }
            if (uri.indexOf("#") == -1)
            {
                uri = SemanticObject.shortToFullURI(uri);
            }
        }
        return uri;
    }

    private void updateProperties(HttpServletRequest request, SemanticObject obj) throws Exception
    {
        Iterator<SemanticProperty> props = obj.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                String[] values = request.getParameterValues(prop.getName());
                if (prop.isDataTypeProperty())
                {
                    String type = XSD_STRING;
                    if (prop.isBoolean())
                    {
                        type = SemanticVocabulary.XMLS_BOOLEAN;
                    }
                    else if (prop.isBinary())
                    {
                        type = SemanticVocabulary.XMLS_BASE64BINARY;
                    }
                    else if (prop.isByte())
                    {
                        type = SemanticVocabulary.XMLS_BYTE;
                    }
                    else if (prop.isDate())
                    {
                        type = SemanticVocabulary.XMLS_DATE;
                    }
                    else if (prop.isDateTime())
                    {
                        type = SemanticVocabulary.XMLS_DATETIME;
                    }
                    else if (prop.isDouble())
                    {
                        type = SemanticVocabulary.XMLS_DOUBLE;
                    }
                    else if (prop.isFloat())
                    {
                        type = SemanticVocabulary.XMLS_FLOAT;
                    }
                    else if (prop.isInt())
                    {
                        type = SemanticVocabulary.XMLS_INT;
                    }
                    else if (prop.isLong())
                    {
                        type = SemanticVocabulary.XMLS_LONG;
                    }
                    else if (prop.isShort())
                    {
                        type = SemanticVocabulary.XMLS_SHORT;
                    }
                    type = type.replace(W3C_XML_SCHEMA_NS_URI + "#", "xsd:");
                    if (prop.getName().startsWith("has"))
                    {
                        if (values != null)
                        {
                            // Validate
                            for (String value : values)
                            {
                                validate(value, type);
                            }
                            // set the values                            
                            for (String value : values)
                            {
                                SemanticLiteral literal = SemanticLiteral.valueOf(prop, value);
                                obj.addLiteralProperty(prop, literal);
                            }

                        }

                    }
                    else
                    {
                        if (values != null)
                        {
                            if (values.length != 1)
                            {
                                throw new Exception("The property " + prop.getName() + " has single value");
                            }
                            if (values[0] != null)
                            {
                                Object value = get(values[0], type);
                                if (value instanceof Date)
                                {
                                    obj.setDateProperty(prop, (Date) value);
                                }
                                if (value instanceof Timestamp)
                                {
                                    obj.setDateTimeProperty(prop, (Timestamp) value);
                                }
                                if (value instanceof Boolean)
                                {
                                    obj.setBooleanProperty(prop, ((Boolean) value).booleanValue());
                                }
                                else
                                {
                                    obj.setProperty(prop, value.toString());
                                }
                            }


                        }

                    }
                }
                else
                {
                    if (prop.getName().startsWith("has"))
                    {
                        if (values != null)
                        {
                            // Validate
                            for (String value : values)
                            {
                                String uri = value;
                                uri = fixURI(uri);
                                SemanticObject testobj = SemanticObject.createSemanticObject(uri);
                                if (testobj == null)
                                {
                                    throw new Exception("The object with uri " + value + " was not found");
                                }
                                obj.addObjectProperty(prop, testobj);

                            }

                        }

                    }
                    else
                    {
                        if (values != null)
                        {
                            if (values.length != 1)
                            {
                                throw new Exception("The property " + prop.getName() + " has single value");
                            }
                            if (values[0] != null)
                            {
                                String uri = values[0];
                                uri = fixURI(uri);
                                SemanticObject testobj = SemanticObject.createSemanticObject(uri);
                                if (testobj == null)
                                {
                                    throw new Exception("The object with uri " + values[0] + " was not found");
                                }
                                obj.addObjectProperty(prop, testobj);
                            }

                        }

                    }
                }
            }
        }
    }

    private void showDeleted(HttpServletRequest request, HttpServletResponse response,SemanticObject obj) throws IOException
    {
        if ("json".equals(request.getParameter("format")))
        {
            try
            {
                showJSON(response, getDeletedAsJSON(true));
            }
            catch (Exception e)
            {
                showError(request, response, e.getMessage());
            }
        }
        else
        {
            Document doc = getDeletedAsXML(obj);
            showDocument(response, doc);
        }

    }

    private void showObject(HttpServletRequest request, HttpServletResponse response, List<String> path, SemanticClass clazz) throws RestException, IOException
    {
        String uri = path.get(0);
        uri = fixURI(uri);
        SemanticObject obj = SemanticObject.createSemanticObject(uri);
        if (obj == null || !obj.getSemanticClass().equals(clazz))
        {
            throw new RestException("The object " + path.get(0) + " was not found");
        }
        else
        {
            showObject(request, response, obj);
        }

    }

    private void showContext(HttpServletRequest request, HttpServletResponse response, List<String> path) throws IOException
    {
        String classURI = path.get(0);
        int pos = classURI.indexOf("_");
        if (pos != -1)
        {
            path.remove(0);
            String prefix = classURI.substring(0, pos);
            String name = classURI.substring(pos + 1);
            for (SemanticClass clazz : classes)
            {
                if (prefix.equals(clazz.getPrefix()) && name.equals(clazz.getName()))
                {
                    try
                    {
                        if (path.isEmpty())
                        {
                            Document doc = getExecuteMethod(request, "list" + clazz.getNameInPlural(), clazz);
                            showDocument(response, doc);
                            return;
                        }
                        else
                        {
                            showObject(request, response, path, clazz);
                            return;
                        }
                    }
                    catch (Exception e)
                    {
                        showError(request, response, e.getMessage());
                    }
                }
            }
        }
        response.setStatus(404);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if (request.getMethod().toLowerCase().equals("delete"))
        {
            if (request.getParameter("uri") != null)
            {
                String uri = request.getParameter("uri");
                uri = fixURI(uri);
                SemanticObject obj = SemanticObject.createSemanticObject(uri);
                if (obj != null)
                {
                    SemanticClass clazz = obj.getSemanticClass();
                    boolean isUpdatable = false;
                    for (SemanticClass clazzCatalog : classes)
                    {
                        if (clazzCatalog.equals(clazz))
                        {
                            isUpdatable = true;
                            break;
                        }
                    }
                    if (!isUpdatable)
                    {
                        response.setStatus(400);
                        showError(request, response, "The object was not found");
                        return;
                    }
                    uri = obj.getShortURI();
                    obj.remove();
                    showDeleted(request, response, obj);
                }
                else
                {
                    showDeleted(request, response, obj);
                    return;
                }


            }
            else
            {
                showError(request, response, "The parameter uri was not found");
                return;

            }
        }
        else if (request.getMethod().toLowerCase().equals("get"))
        {
            if (servlet == null)
            {
                /*if (request.getParameter(XSD_PREFIX) != null)
                {
                    String uri = request.getParameter(XSD_PREFIX);
                    SemanticObject obj = SemanticObject.createSemanticObject(uri);
                    if (obj != null)
                    {
                        SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
                        showXSD(request, response, clazz);
                    }

                }*/
                if (request.getParameter("error") != null)
                {
                    showErrorXSD(request, response);
                }                               
                else if (request.getParameter("clsmgr") != null)
                {
                    if (request.getParameter("classuri") != null)
                    {
                        String uri = request.getParameter("classuri");
                        uri = fixURI(uri);
                        SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
                        if (clazz != null)
                        {
                            showCLSMGRXSD(response, clazz);
                        }
                        else
                        {
                            showError(request, response, "The class with uri " + request.getParameter("classuri") + " was not found");
                        }
                    }
                }
                else if (request.getParameter("method") != null && request.getParameter("classuri") != null)
                {
                    executeMethod(request, response);
                }
                else if (request.getParameter("uri") != null)
                {
                    String uri = request.getParameter("uri");
                    uri = fixURI(uri);

                    SemanticObject obj = SemanticObject.createSemanticObject(uri);
                    if (obj != null)
                    {
                        showObject(request, response, obj);
                    }
                    else
                    {
                        response.setStatus(400);
                        showError(request, response, "The objct with uri " + request.getParameter("uri") + " was not found");
                    }


                }
                else
                {
                    String context = SWBPortal.getContextPath();
                    String uri = request.getRequestURI();
                    if (uri.startsWith(context))
                    {
                        uri = uri.substring(context.length()).trim();
                    }
                    int pos = uri.indexOf(".");
                    if (pos == -1)
                    {
                        if (uri.startsWith("/"))
                        {
                            uri = uri.substring(1);
                        }
                        String[] path = uri.split("/");
                        if (path.length <= 1)
                        {
                            showList(request, response);
                        }
                        else
                        {
                            ArrayList<String> listPath = new ArrayList<String>();
                            listPath.addAll(Arrays.asList(path));
                            listPath.remove(0);
                            showContext(request, response, listPath);
                        }
                    }
                    else
                    {
                        showList(request, response);
                    }
                }
            }
            else
            {
                String requestURI = request.getRequestURI();
                String context = SWBPortal.getContextPath();
                if (requestURI.startsWith(context))
                {
                    requestURI = requestURI.substring(context.length()).trim();
                }
                String[] path = requestURI.split("/");
                if (path.length <= 1)
                {
                    /*if (request.getParameter(XSD_PREFIX) != null)
                    {
                        String uri = request.getParameter(XSD_PREFIX);
                        SemanticObject obj = SemanticObject.createSemanticObject(uri);
                        if (obj != null)
                        {
                            SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
                            showXSD(request, response, clazz);
                        }

                    }*/
                    if (request.getParameter("error") != null)
                    {
                        showErrorXSD(request, response);
                    }                    
                    else
                    {
                        showList(request, response);
                    }
                }
                else
                {
                    ArrayList<String> listPath = new ArrayList<String>();
                    listPath.addAll(Arrays.asList(path));
                    listPath.remove(0);
                    showContext(request, response, listPath);
                }
            }
        }
        if (request.getMethod().toLowerCase().equals("put"))
        {
            String uri = request.getParameter(REST_URI);
            if (uri == null || uri.equals(""))
            {
                showError(request, response, "The uri parameter was not found");
            }
            else
            {
                uri = fixURI(uri);
                SemanticObject obj = SemanticObject.createSemanticObject(uri);
                if (obj != null)
                {
                    SemanticClass clazz = obj.getSemanticClass();
                    boolean isUpdatable = false;
                    for (SemanticClass clazzCatalog : classes)
                    {
                        if (clazzCatalog.equals(clazz))
                        {
                            isUpdatable = true;
                            break;
                        }
                    }
                    if (!isUpdatable)
                    {
                        response.setStatus(400);
                        showError(request, response, "The object was not found");
                        return;
                    }
                    try
                    {
                        updateProperties(request, obj);
                        showUpdated(request, response, obj);
                    }
                    catch (Exception e)
                    {
                        response.setStatus(400);
                        showError(request, response, e.getMessage());
                        return;
                    }
                }
                else
                {
                    showUpdated(request, response, obj);
                }
            }
        }
        else if (request.getMethod().toLowerCase().equals("post"))
        {
            String classuri = request.getParameter(REST_CLASSURI);
            String modeluri = request.getParameter(REST_MODELURI);
            if (classuri == null)
            {
                response.setStatus(400);
                showError(request, response, "The parameter rest:classuri is required");
                return;
            }
            if (modeluri == null)
            {
                response.setStatus(400);
                showError(request, response, "The parameter modeluri is required");
                return;
            }
            classuri = fixURI(classuri);
            SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(classuri);
            if (clazz == null)
            {
                response.setStatus(400);
                showError(request, response, "The class " + request.getParameter("classuri") + " was not found");
                return;
            }
            boolean isPublished = false;
            for (SemanticClass clazzCatalog : classes)
            {
                if (clazzCatalog.equals(clazz))
                {
                    isPublished = true;
                    break;
                }
            }
            if (!isPublished)
            {
                response.setStatus(400);
                showError(request, response, "Can not create a object with class " + request.getParameter("classuri"));
                return;
            }
            if (!clazz.isAutogenId())
            {
                if (request.getParameter("id") == null)
                {
                    response.setStatus(400);
                    showError(request, response, "The parameter id is required");
                    return;
                }
            }
            modeluri = fixURI(modeluri);
            SemanticObject objmodel = SemanticObject.createSemanticObject(modeluri);
            if (objmodel == null)
            {
                response.setStatus(400);
                showError(request, response, "The model " + request.getParameter(REST_MODELURI) + " was not found");
                return;
            }
            if (!objmodel.getSemanticClass().isSWBModel())
            {
                response.setStatus(400);
                showError(request, response, "The object " + request.getParameter(REST_MODELURI) + " is not a model");
                return;
            }

            GenericObject model = (GenericObjectBase) objmodel.createGenericInstance();
            if (!(model instanceof WebSite))
            {
                response.setStatus(400);
                showError(request, response, "The model " + request.getParameter(REST_MODELURI) + " was not found");
                return;
            }
            WebSite site = (WebSite) model;
            if (site.getURI().equals(SWBContext.getAdminWebSite().getURI()) || site.getURI().equals(SWBContext.getGlobalWebSite().getURI()))
            {
                response.setStatus(400);
                showError(request, response, "The model " + request.getParameter(REST_MODELURI) + " was not found");
                return;
            }
            String id = null;
            if (clazz.isAutogenId())
            {
                id = String.valueOf(site.getSemanticObject().getModel().getCounter(clazz));
            }
            else
            {
                id = request.getParameter(REST_ID);
                if (id == null)
                {
                    response.setStatus(400);
                    showError(request, response, "The object parameter rest:id was not found");
                    return;
                }
                SemanticObject objtest = site.getSemanticObject().getModel().createSemanticObjectById(id, clazz);
                if (objtest != null)
                {
                    response.setStatus(400);
                    showError(request, response, "The parameter rest:id is invalid, the id already exists");
                    return;
                }
            }
            GenericObject newobj = site.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, clazz), clazz);
            // update properties
            try
            {
                updateProperties(request, newobj.getSemanticObject());
                showCreted(request, response, newobj.getSemanticObject());
            }
            catch (Exception e)
            {
                newobj.getSemanticObject().remove();
                response.setStatus(400);
                showError(request, response, e.getMessage());
                return;
            }
        }

    }
}
