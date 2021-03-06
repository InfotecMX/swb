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
package org.semanticwb.portal.api;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.chiba.web.WebFactory;
import org.chiba.web.session.XFormsSession;
import org.chiba.web.session.XFormsSessionManager;
import org.chiba.web.session.impl.DefaultXFormsSessionManagerImpl;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletInputStream;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.util.XmlBundle;
import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericXformsResource.
 * 
 * @author jorge.jimenez
 */
public class GenericXformsResource extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(GenericXformsResource.class);
    
    /** The bundles. */
    static Hashtable bundles = new Hashtable();
    
    /** The bundle. */
    XmlBundle bundle = null;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        String name = getClass().getName();
        String className = name;
        bundle = (XmlBundle) bundles.get(name);
        if (bundle == null) {
            { //Para archivos de Administración
                bundle = new XmlBundle(className, name);
                bundles.put(name, bundle);
                //Colocar el xForms Inst
                String name_xFormsInst = name + "_inst";
                bundle = new XmlBundle(className, name_xFormsInst);
                bundles.put(name_xFormsInst, bundle);
            }
            { //Para archivos de Vista (Front-End)
                name = name + "_V";
                bundle = new XmlBundle(className, name);
                bundles.put(name, bundle);
                //Colocar el xForms Inst
                String name_xFormsInst = name + "_inst";
                bundle = new XmlBundle(className, name_xFormsInst);
                bundles.put(name_xFormsInst, bundle);
            }
        }
    }

    /**
     * Load xform.
     * 
     * @param className the class name
     * @param name the name
     * @param lang the lang
     * @return the string
     */
    public String loadXform(String className, String name, String lang) {
        bundle = (XmlBundle) bundles.get(name);
        if (bundle == null) {
            {
                bundle = new XmlBundle(className, name);
                bundles.put(name, bundle);
            }
        }
        return bundle.getBundle(name, new java.util.Locale(lang));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("loadInstance")) {
            doLoadInstance(request, response, paramRequest); //Utilizado para cargar el modelo
        } else if (paramRequest.getMode().equals("process")) {
            doProcess(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Carga instancia, ya sea la de inicio o una ya grabada en BD del recurso en cuestión.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     */
    public void doLoadInstance(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        try {
            if (request.getParameter("wbmode") != null && request.getParameter("wbmode").equals("view")) { //Se desea la instancia de vista (Front-End)
                //Carga la instancia de archivo
                String res = initViewModel(request, paramsRequest);
                //response.getOutputStream().write(res.getBytes());
                response.getWriter().print(res);
            } else { //Se desea la instancia de Administración
                //response.getOutputStream().write(initAdminModel(request,paramsRequest).getBytes());
                response.getWriter().print(initAdminModel(request, paramsRequest));
            }
        } catch (Exception e) {
            log.error(e);
        }
        return;
    }

    /**
     * Metodo que proporciona una instancia de inicio para el recurso xForms, busca un archivo NombreClase_inst_locale.
     * 
     * @param request the request
     * @param paramsRequest the params request
     * @return the string
     */
    public String initViewModel(HttpServletRequest request, SWBParamRequest paramsRequest) // Inicializa el modelo de la forma, puede leerse de un archivo xml
    {
        String instanceName = getClass().getName() + "_V_inst";
        if (request.getParameter("instanceName") != null) {
            instanceName = request.getParameter("instanceName");
        }
        return bundle.getBundle(instanceName, new java.util.Locale(paramsRequest.getUser().getLanguage()));
    }

    /**
     * Metodo que proporciona una instancia de inicio para el recurso xForms, busca un archivo NombreClase_inst_locale.
     * 
     * @param request the request
     * @param paramsRequest the params request
     * @return the string
     */
    public String initAdminModel(HttpServletRequest request, SWBParamRequest paramsRequest) // Inicializa el modelo de la forma, puede leerse de un archivo xml
    {
        Resource base = getResourceBase();
        Document dom = null;
        if (base.getXml() != null && base.getXml().trim().length() > 0) {
            dom = SWBUtils.XML.xmlToDom(base.getXml());
            NodeList nviewNode = dom.getElementsByTagName("wbadm");
            if (nviewNode.getLength() == 0) { //Carga la instancia de archivo
                return initAdminModelFromFile(request, paramsRequest);
            } else { //Carga la instancia de BD
                for (int i = 0; i < nviewNode.getLength(); i++) {
                    try {
                        Document domAdmin = toDocument(nviewNode.item(0).getFirstChild());
                        return SWBUtils.XML.domToXml(domAdmin);
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        } else {
            // De lo contrario que cargue una instancia inicial del modelo
            return initAdminModelFromFile(request, paramsRequest);
        }
        return "";
    }

    /**
     * Metodo que proporciona una instancia de inicio para el recurso xForms, busca un archivo NombreClase_inst_locale.
     * 
     * @param request the request
     * @param paramsRequest the params request
     * @return the string
     */
    public String initAdminModelFromFile(HttpServletRequest request, SWBParamRequest paramsRequest) // Inicializa el modelo de la forma, puede leerse de un archivo xml
    {
        String instanceName = getClass().getName() + "_inst";
        if (request.getAttribute("instanceName") != null) {
            instanceName = (String) request.getAttribute("instanceName");
        }
        return bundle.getBundle(instanceName, new java.util.Locale(paramsRequest.getUser().getLanguage()));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        doMethod(request, response, paramRequest);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        doMethod(request, response, paramRequest);
    }

    /**
     * Do method.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void doMethod(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String xml = null;
        String replaceVal = null;
        org.chiba.web.servlet.WebUtil.nonCachingResponse(response);

        String action = request.getParameter("action");
        if (action == null || (action != null && action.trim().equals(""))) {
            action = (String) request.getAttribute("action");
            if (action == null || (action != null && action.trim().equals(""))) {
                action = paramsRequest.getAction();
            }
        }
        if (action.equals("add") || action.equals("edit")) { //Cuando es alta o edición de la forma
            String instanceName = getClass().getName();
            if (paramsRequest.getMode().equals(paramsRequest.Mode_VIEW) || paramsRequest.getMode().equals("process")) {
                instanceName = instanceName + "_V";
            }
            if (request.getAttribute("xformsDoc") != null) {
                xml = SWBUtils.XML.domToXml((Document) request.getAttribute("xformsDoc"));
            }
            if (xml == null) { //Toma de bundle
                xml = bundle.getBundle(instanceName, new java.util.Locale(paramsRequest.getUser().getLanguage()));
            }
            if (xml != null && xml.trim().length() > 0) {
                try {
                    Document dom = SWBUtils.XML.xmlToDom(xml);

                    NodeList nSub = dom.getElementsByTagName("xforms:submission");
                    if (nSub.getLength() > 0) {
                        replaceVal = nSub.item(0).getAttributes().getNamedItem("replace").getNodeValue();
                    }
                    request.setAttribute("replaceVal", replaceVal);

                    request.setAttribute(WebFactory.XFORMS_NODE, dom);
                    configxForms(request, response, paramsRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Configx forms.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     */
    private void configxForms(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        HttpSession session = request.getSession(true);
        request.setAttribute(WebFactory.SCRIPTED, "true");
        log.info("Start Filter XForm Servlet");

        XFormsSession xFormsSession = null;
        try {
            XFormsSessionManager sessionManager = DefaultXFormsSessionManagerImpl.getXFormsSessionManager();
            xFormsSession = sessionManager.createXFormsSession(request, response, session);
            xFormsSession.setBaseURI(request.getRequestURL().toString());
            xFormsSession.setXForms();

            SWBResourceURL url = null;
            //String wbPRequestURL=null;
            { // set instance model src uri
                url = paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);
                url.setAction("update");
                url.setMode("loadInstance");
                url.setParameter("instanceName", (String) request.getAttribute("instanceName"));
                url.setParameter("instance", (String) request.getAttribute("instance"));
                url.setParameter("wbmode", (String) request.getAttribute("wbmode"));
                String wbmode = (String) request.getAttribute("wbmode");
                if (wbmode != null) {
                    url.setParameter("wbmode", wbmode);
                } else {
                    url.setParameter("wbmode", paramsRequest.getMode());
                }
                xFormsSession.getAdapter().setContextParam("wbInstance", url.toString());
            }
            xFormsSession.init();

            SWBResourceURL processurl = paramsRequest.getRenderUrl();
            //processurl.setCallMethod(processurl.Call_DIRECT);
            processurl.setMode("process");
            processurl.setParameter("wbmode", paramsRequest.getMode());
            processurl.setParameter("replaceVal", (String) request.getAttribute("replaceVal"));
            processurl.setAction(processurl.Action_EDIT);
            xFormsSession.getAdapter().setContextParam("wbRedirect", processurl.toString());
            xFormsSession.handleRequest();
            PrintWriter out = response.getWriter();
            out.println("<br><a href=\"" + paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_ADMIN) + "\">admin GenericXforms</a>");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (xFormsSession != null) {
                try {
                    xFormsSession.close(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        log.info("End Render XForm Servlet");
    }

    /**
     * Do process.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     */
    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        try {
            request.setAttribute("wbmode", request.getParameter("wbmode"));
            String sdata = null;
            if (paramsRequest.getAction().equals(paramsRequest.Action_EDIT)) {
                DocumentBuilderFactory docBF = SWBUtils.XML.getDocumentBuilderFactory();
                org.w3c.dom.Document doc = null;
                try {
                    DocumentBuilder docBuil = docBF.newDocumentBuilder();
                    ServletInputStream in = request.getInputStream();
                    String xml = SWBUtils.IO.readInputStream(in);
                    //doc = docBuil.parse(in);
                    doc = SWBUtils.XML.xmlToDom(xml);
                } catch (Exception e) {
                    log.error(e);
                }

                uploadFiles(doc);
                saveData(request, response, paramsRequest, doc);
            }
            try {
                String replaceVal = request.getParameter("replaceVal");
                if (replaceVal != null) {
                    if (replaceVal.equals("all")) {
                        /*
                        SWBResourceURL url = paramsRequest.getRenderUrl();
                        url.setMode(url.Mode_VIEW);
                        response.sendRedirect(url.toString());
                         **/
                        doMethod(request, response, paramsRequest);
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Save data.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @param dom the dom
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void saveData(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, Document dom) throws SWBResourceException, IOException {
        response.getOutputStream().println(processData(dom));
    }

    /**
     * Process data.
     * 
     * @param dom the dom
     * @return the string
     */
    public String processData(Document dom) {
        Resource base = getResourceBase();
        String data = null;
        //RecResource recRsc=base.
        try {
            Document domtmp = SWBUtils.XML.getNewDocument();
            //Document dom = SWBUtils.XML.xmlToDom(data);
            if (dom.getElementsByTagName("data") != null) {
                Element ndata = domtmp.createElement("data");
                domtmp.appendChild(ndata);
                NodeList nlist = dom.getElementsByTagName("data").item(0).getChildNodes();
                Node nodeEle = null;
                for (int i = 0; i < nlist.getLength(); i++) {
                    Node node = nlist.item(i);
                    if (node.getNodeName().equalsIgnoreCase("#text")) {
                        continue;
                    }
                    if (!node.getNodeName().startsWith("wb_")) {
                        nodeEle = domtmp.importNode(node, true);
                        ndata.appendChild(nodeEle);
                    }
                }
            }
            data = SWBUtils.XML.domToXml(domtmp);
            String xml = base.getXml();
            int pos = -1;
            if (data != null) {
                if ((pos = data.indexOf("<?xml version=")) > -1) {
                    pos = data.indexOf(">", pos);
                    data = data.substring(pos + 1);
                }
            }
            if (xml != null) {
                if ((pos = xml.indexOf("<resource>")) > -1) {
                    xml = xml.substring(pos + 10, xml.indexOf("</resource>"));
                }
                //Graba modo Admin
                if ((pos = xml.indexOf("<wbadm>")) > -1) {
                    xml = xml.substring(0, pos + 7) + data + xml.substring(xml.indexOf("</wbadm>"));
                }
            } else { // Insertar el xml como nuevo
                xml = "<wbadm>" + data + "</wbadm>";
            }
            base.setXml("<resource>" + xml + "</resource>");
        } catch (Exception e) {
            log.error(e);
        }
        return data;
    }

    /**
     * Upload files.
     * 
     * @param doc the doc
     */
    private void uploadFiles(Document doc) {
        Resource resource = getResourceBase();
        NodeList nListUploads = doc.getFirstChild().getChildNodes();
        for (int i = 0; i < nListUploads.getLength(); i++) {
            Node node = nListUploads.item(i);
            NamedNodeMap nNodeMap = node.getAttributes();
            if (nNodeMap != null && nNodeMap.getLength() > 0) //Tiene atributos
            {
                Node fileNode = nNodeMap.getNamedItem("file");
                if (fileNode != null) //El nodo es de tipo file
                {
                    String fileName = fileNode.getNodeValue();
                    int pos = fileName.lastIndexOf("\\");
                    if (pos > -1) {
                        fileName = fileName.substring(pos + 1);
                    }
                    if (node.getFirstChild() != null && node.getFirstChild().getNodeValue() != null) {
                        //byte[] decodedData = Base64.decode(node.getFirstChild().getNodeValue());
                        String decodedData = SWBUtils.TEXT.decodeBase64(node.getFirstChild().getNodeValue());
                        if (decodedData != null) {
                            File file = new File(SWBPortal.getWorkPath() + resource.getWorkPath());
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            try {
                                FileOutputStream fos = new FileOutputStream(SWBPortal.getWorkPath() + resource.getWorkPath() + "/" + fileName);
                                fos.write(decodedData.getBytes());
                                fos.close();
                            } catch (Exception e) {
                                log.error(e);
                            }
                            node.getFirstChild().setNodeValue(fileName);
                        }
                    } else {
                        if (fileName == null) {
                            fileName = "";
                        }
                        node.appendChild(doc.createTextNode(fileName));
                    }
                    fileNode.setNodeValue("");
                }
                Node fileType = nNodeMap.getNamedItem("type");
                if (fileType != null) //El nodo es de tipo type
                {
                    fileType.setNodeValue("");
                }
            }
        }
    }

    /**
     * Sets the data.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @param xformsFiles the xforms files
     * @param action the action
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void setData(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest, String xformsFiles, String action) throws SWBResourceException, IOException {
        //Carga archivo xml de formulario y lo agrega como atributo del request
        request.setAttribute("xformsDoc", SWBUtils.XML.xmlToDom(loadXform(getClass().getName(), xformsFiles, paramsRequest.getUser().getLanguage())));
        //Carga archivo xml de instancia del formulario
        loadXform(getClass().getName(), xformsFiles + "_inst", paramsRequest.getUser().getLanguage());
        request.setAttribute("instanceName", xformsFiles + "_inst");  //Agrega instancia del formulario como atributo del request
        request.setAttribute("action", action); //Agrega una acción como atributo del request
    }
    //TODO: PASAR LOS SIGUIENTES METODOS A SWBUitls en version SWB 4.0
    /**
     * Comvierte un Node a Document.
     * 
     * @param node the node
     * @return the document
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Document toDocument(Node node) throws SWBResourceException, IOException {
        // ensure xerces dom
        /*
        if (node instanceof org.apache.xerces.dom.DocumentImpl) {
        return (Document) node;
        }*/
        Document document = getDocumentBuilder().newDocument();
        if (node instanceof Document) {
            node = ((Document) node).getDocumentElement();
        }
        document.appendChild(document.importNode(node, true));
        return document;
    }

    /**
     * Comvierte un Node a Document.
     * 
     * @return the document builder
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private DocumentBuilder getDocumentBuilder() throws SWBResourceException, IOException {
        // ensure xerces dom
        //DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilderFactory factory = SWBUtils.XML.getDocumentBuilderFactory();
        try {
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            factory.setAttribute("http://apache.org/xml/properties/dom/document-class-name", "org.apache.xerces.dom.DocumentImpl");
            return factory.newDocumentBuilder();
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
}
