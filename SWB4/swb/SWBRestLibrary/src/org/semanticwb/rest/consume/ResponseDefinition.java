/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author victor.lorenzana
 */
public final class ResponseDefinition
{
    private static final Logger log = SWBUtils.getLogger(RestPublish.class);
    private final int status;
    private final String mediaType;
    private ParameterDefinition[] parameters;
    private Document docschema;
    private Element elementDefinition;
    private ResponseDefinition(int status, String mediaType)
    {
        this.status = status;
        this.mediaType = mediaType;        
    }   
    public Element getElementDefinition()
    {
        return elementDefinition;
    }
    public void validateResponse(Object resp) throws RestException
    {
        if(resp instanceof Document)
        {
            Document document=(Document)resp;
            if(elementDefinition!=null && docschema!=null)
            {
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Charset charset=Charset.defaultCharset();
                String schemafile=SWBUtils.XML.domToXml(docschema,charset.name(),true);
                StringReader reader = new StringReader(schemafile);
                Source schemaFile = new StreamSource(reader);
                try
                {
                    Schema schema = factory.newSchema(schemaFile);
                    Validator validator = schema.newValidator();
                    DOMSource source = new DOMSource(document);
                    validator.validate(source);
                }
                catch (IOException ioe)
                {
                    log.debug(ioe);
                    throw new RestException(ioe);
                }
                catch (SAXException saxe)
                {
                    log.debug(saxe);
                    throw new RestException(saxe);

                }
            }
        }        
    }
    public int getStatus()
    {
        return status;
    }

    public String getMediaType()
    {
        return mediaType;
    }

    public static Element getSchema(Document doc, String localname, String namespace)
    {
        NodeList schemas = doc.getElementsByTagNameNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, "schema");
        for (int i = 0; i < schemas.getLength(); i++)
        {
            Element schema = (Element) schemas.item(i);
            String targetNamespace = schema.getAttribute("targetNamespace");
            if (namespace.equals(targetNamespace))
            {
                // this is the schema
                NodeList childs = schema.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++)
                {
                    Node child = childs.item(j);
                    if (child instanceof Element && XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(child.getNamespaceURI()))
                    {
                        Element eDefinition = (Element) child;
                        if (eDefinition.getLocalName().equals("element"))
                        {
                            String name = eDefinition.getAttribute("name");
                            if (localname.equals(name))
                            {
                                return schema;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    private static Element getElementDefinition(Document doc, String localname, String namespace)
    {
        NodeList schemas = doc.getElementsByTagNameNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, "schema");
        for (int i = 0; i < schemas.getLength(); i++)
        {
            Element schema = (Element) schemas.item(i);
            String targetNamespace = schema.getAttribute("targetNamespace");
            if (namespace.equals(targetNamespace))
            {
                // this is the schema
                NodeList childs = schema.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++)
                {
                    Node child = childs.item(j);
                    if (child instanceof Element && XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(child.getNamespaceURI()))
                    {
                        Element eDefinition = (Element) child;
                        if (eDefinition.getLocalName().equals("element"))
                        {
                            String name = eDefinition.getAttribute("name");
                            if (localname.equals(name))
                            {
                                return eDefinition;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void extractDefinitions(Element parent, ArrayList<ParameterDefinition> parameterDefinitions,Method method,Element schema)
    {
        NodeList childs = parent.getChildNodes();
        for (int j = 0; j < childs.getLength(); j++)
        {
            if (childs.item(j) instanceof Element)
            {
                Element child=(Element)childs.item(j);
                if(child.getLocalName().equals("complexType"))
                {
                    NodeList complexNodeChilds=child.getChildNodes();
                    for(int i=0;i<complexNodeChilds.getLength();i++)
                    {
                        if(complexNodeChilds.item(i) instanceof Element && ((Element)complexNodeChilds.item(i)).getLocalName().equals("sequence"))
                        {
                            Element sequence=(Element)complexNodeChilds.item(i);
                            extractDefinitions(sequence, parameterDefinitions,method,schema);
                        }
                    }
                }
                else
                {
                    if(child.getLocalName().equals("element") || child.getLocalName().equals("attribute"))
                    {
                        try
                        {
                            ParameterDefinition parameter = ParameterDefinition.createParameterDefinition(child,method,schema);
                            parameterDefinitions.add(parameter);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static ResponseDefinition[] createResponseDefinition(Element response, Method method) throws RestException
    {
        ArrayList<ResponseDefinition> definitions = new ArrayList<ResponseDefinition>();
        int istatus = 200;
        String status = response.getAttribute("status");
        if (status != null && !status.trim().equals(""))
        {
            istatus = Integer.parseInt(status);
        }
        NodeList childs = response.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            if (childs.item(i) instanceof Element && ((Element) childs.item(i)).getNamespaceURI() != null && ((Element) childs.item(i)).getNamespaceURI().equals(response.getNamespaceURI()))
            {
                if (((Element) childs.item(i)).getTagName().equals("representation"))
                {
                    Element representation = (Element) childs.item(i);
                    if (representation.getAttribute("mediaType") == null || representation.getAttribute("mediaType").trim().equals(""))
                    {
                        throw new RestException("The attribute mediaType was not found");
                    }
                    String mediaType = representation.getAttribute("mediaType");
                    Element responseDefinition = null;
                    ResponseDefinition definition = new ResponseDefinition(istatus, mediaType);
                    definitions.add(definition);
                    if (representation.getAttribute("element") != null && !representation.getAttribute("element").trim().equals(""))
                    {
                        String elementName = representation.getAttribute("element");
                        String namespace = method.getResource().getServiceInfo().getNamespaceURI();
                        int pos = elementName.indexOf(":");
                        if (pos != -1)
                        {
                            String prefix = elementName.substring(0, pos);
                            elementName = elementName.substring(pos + 1);
                            namespace = response.getOwnerDocument().getDocumentElement().getAttribute("xmlns:" + prefix);
                            if (namespace == null || namespace.trim().equals(""))
                            {
                                throw new RestException("The namespace for the prefix " + prefix + " was not found");
                            }
                        }
                        // busca el elemento  en los schemas
                        responseDefinition = getElementDefinition(response.getOwnerDocument(), elementName, namespace);
                        if (responseDefinition == null)
                        {
                            throw new RestException("The element " + elementName + " was not found");
                        }
                        definition.elementDefinition=representation;
                        Element schema=getSchema(response.getOwnerDocument(), elementName, namespace);
                        Document docschema=SWBUtils.XML.getNewDocument();
                        Node importedNodeschema=docschema.importNode(schema, true);
                        docschema.appendChild(importedNodeschema);
                        definition.docschema=docschema;
                        ArrayList<ParameterDefinition> parameterDefinitions = new ArrayList<ParameterDefinition>();
                        extractDefinitions(responseDefinition, parameterDefinitions,method,schema);
                        definition.parameters=parameterDefinitions.toArray(new ParameterDefinition[parameterDefinitions.size()]);
                    }                    
                    
                }
            }
        }
        return definitions.toArray(new ResponseDefinition[definitions.size()]);
    }

    public ParameterDefinition[] getParameters()
    {
        return parameters;
    }
}
