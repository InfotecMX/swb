/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.JDOMException;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public class MakeRequest
{

    private static final Logger log = SWBUtils.getLogger(MakeRequest.class);

    private void sendResponse(String objresponse, HttpServletResponse response) throws IOException
    {
        Charset utf8 = Charset.forName("utf-8");
        response.setContentType("application/json;charset=" + utf8.name());
        OutputStream out = response.getOutputStream();
        out.write("throw 1; < don't be evil' >".getBytes());
        out.write(objresponse.getBytes(utf8));
        out.close();
    }

    private void getDocument(URL url, String headers, HttpServletResponse response) throws IOException, JDOMException, JSONException
    {        
        try
        {            
            int code=500;
            String xml="";
            try
            {
                Document doc=SocialContainer.getXML(url);
                Charset charset=Charset.defaultCharset();
                xml =SWBUtils.XML.domToXml(doc, charset.name(), true);                
                code=200;
            }
            catch(RequestException e)
            {
                code=e.getCode();
            }

            JSONObject responseJSONObject = new JSONObject();
            response.setContentType("application/json");
            JSONObject body = new JSONObject();
            body.put("body", xml);
            responseJSONObject.put(url.toString(), body);
            responseJSONObject.put("rc", code);
            sendResponse(responseJSONObject.toString(4), response);
        }
        catch (IOException e)
        {            
            log.debug(e);
            throw e;
        }
        /*catch (JDOMException e)
        {
        log.debug(e);
        throw e;
        }*/
        catch (JSONException e)
        {
            log.debug(e);
            throw e;
        }

    }




    private void sendResponse(Document objresponse, HttpServletResponse response) throws IOException
    {
        Charset defaultCharset = Charset.defaultCharset();
        response.setContentType("text/xml;charset=" + defaultCharset.name());
        OutputStream out = response.getOutputStream();
        String xml = SWBUtils.XML.domToXml(objresponse, defaultCharset.name(), false);        
        out.write(xml.getBytes());
        out.close();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();        
        log.debug("MakeRequest request.getQueryString(): " + request.getQueryString());
        log.debug("MakeRequest request.getContentType(): " + request.getContentType());
        String refresh = request.getParameter("refresh");
        log.debug("refresh: " + refresh);
        String url = request.getParameter("url");
        log.debug("url: " + url);
        String httpMethod = request.getParameter("httpMethod");
        log.debug("httpMethod: " + httpMethod);
        String headers = request.getParameter("headers");
        log.debug("headers: " + headers);
        String postData = request.getParameter("postData");
        log.debug("postData: " + postData);        
        String authz = request.getParameter("authz");
        log.debug("authz: " + authz);
        String contentType = request.getParameter("contentType");
        log.debug("contentType: " + contentType);
        String numEntries = request.getParameter("numEntries");
        log.debug("numEntries: " + numEntries);
        String getSummaries = request.getParameter("getSummaries");
        log.debug("getSummaries: " + getSummaries);
        String signOwner = request.getParameter("signOwner");
        log.debug("signOwner: " + signOwner);
        String container = request.getParameter("container");
        log.debug("container: " + container);
        String bypassSpecCache = request.getParameter("bypassSpecCache");
        log.debug("bypassSpecCache: " + bypassSpecCache);
        String getFullHeaders = request.getParameter("getFullHeaders");
        log.debug("getFullHeaders: " + getFullHeaders);
        String gadget = request.getParameter("gadget");
        log.debug(gadget);
        Gadget g = SocialContainer.getGadget(gadget, site);


        /* para cumplir con el siguiente código
         * switch (params.CONTENT_TYPE) {
        case "JSON":
        case "FEED":
          resp.data = gadgets.json.parse(resp.text);
          if (!resp.data) {
            resp.errors.push("500 Failed to parse JSON");
            resp.rc = 500;
            resp.data = null;
          }
          break;
        case "DOM":
          var dom;
          if (typeof ActiveXObject != 'undefined') {
            dom = new ActiveXObject("Microsoft.XMLDOM");
            dom.async = false;
            dom.validateOnParse = false;
            dom.resolveExternals = false;
            if (!dom.loadXML(resp.text)) {
              resp.errors.push("500 Failed to parse XML");
              resp.rc = 500;
            } else {
              resp.data = dom;
            }
          } else {
            var parser = new DOMParser();
            dom = parser.parseFromString(resp.text, "text/xml");
            if ("parsererror" === dom.documentElement.nodeName) {
              resp.errors.push("500 Failed to parse XML");
              resp.rc = 500;
            } else {
              resp.data = dom;
            }
          }
          break;
        default:
          resp.data = resp.text;
          break;
      }
         */
        if (g != null)
        {
            if (httpMethod.trim().equalsIgnoreCase("GET"))
            {
                if ("DOM".equals(contentType.trim()))
                {
                    try
                    {
                        getDocument(new URL(url), headers, response);

                        return;
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                        response.sendError(505, e.getLocalizedMessage());
                        return;
                    }
                }
                else if ("FEED".equals(contentType.trim()))
                {
                    try
                    {
                        getDocument(new URL(url), headers, response);
                        return;
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                        response.sendError(505, e.getLocalizedMessage());
                        return;
                    }
                }
                else if ("JSON".equals(contentType.trim()))
                {

                }

            }
            else
            {
                response.sendError(404);
            }
        }
        response.sendError(404);
    }
}
