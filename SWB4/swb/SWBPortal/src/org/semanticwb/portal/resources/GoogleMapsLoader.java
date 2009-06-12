/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Recurso administrador de google maps para WebBuilder.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class GoogleMapsLoader extends GenericAdmResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //Assert location info
        String info = request.getParameter("info");
        if (info == null || info.equals("")) {
            info = "";
        }

        //Assert location latitude
        String latitude = request.getParameter("lat");
        if (latitude == null || latitude.equals("")) {
            latitude = "19.432216";
        }

        //Assert location longitude
        String longitude = request.getParameter("long");
        if (longitude == null || longitude.equals("")) {
            longitude = "-99.131076";
        }

        //Assert location homepage
        String wikiUrl = request.getParameter("wikiUrl");
        if (wikiUrl == null || wikiUrl.equals("")) {
            wikiUrl = "#";
        }

        sbf.append("<script src=\"http://maps.google.com/maps?file=api&v=2&sensor=false&key=" + getResourceBase().getAttribute("mapKey") + "\"" +
                  " type=\"text/javascript\"></script>");

        sbf.append("<table>\n" +
                   "  <tr>\n" +
                   "    <td width=" + getResourceBase().getAttribute("mapWidth","300") + ">\n" +
                   "      <div id=\"" + getResourceBase().getId() + "/map\" style=\"width:" + 
                   getResourceBase().getAttribute("mapWidth","300") + "px; height:" +
                   getResourceBase().getAttribute("mapHeight","400")+ "px;\"></div>\n" +
                   "    </td>\n" +
                   "  </tr>" +
                "</table>");
        
        sbf.append("<script type=\"text/javascript\">\n" +
                   "  function load() {\n" +
                   "    if (GBrowserIsCompatible()) {\n" +
                   "      var map = new GMap2(document.getElementById('" + getResourceBase().getId() + "/map'));\n" +
                   "      map.setCenter(new GLatLng(" + latitude + ", "+ longitude +"), 11);\n" +                   
                   "      map.addControl(new GLargeMapControl());\n" +
                   "      map.addControl(new GScaleControl());\n" +
                   "      map.addControl(new GMapTypeControl());\n" +
                   "      var punto = new GLatLng("+ latitude + "," + longitude + ");\n" +
                   "      var marcador = new GMarker(punto);\n" +
                   "      map.addOverlay(marcador);\n" +
                   "      marcador.openInfoWindowHtml('<p>" + info + "</p><hr>" +
                          paramRequest.getLocaleString("msgWP") +" <a href=\"" + wikiUrl + "\">" + wikiUrl + "</a>');\n" +
                   "    }\n"+
                   "  }\n" +
                   "  load();\n"+
                   "</script>\n\n");
        out.println(sbf.toString());
    }
}
