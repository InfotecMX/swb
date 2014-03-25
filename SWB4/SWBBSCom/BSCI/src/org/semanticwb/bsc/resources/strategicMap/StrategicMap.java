
package org.semanticwb.bsc.resources.strategicMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.DifferentiatorGroup;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.element.Perspective;
import org.semanticwb.bsc.element.Theme;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Recurso Mapa Estrat&eacute;gico. Permite generar un recurso para SWB que se
 * encarga de visualizar Perspectivas, Temas, Grupo de diferenciadores,
 * Objectivos y Diferenciadores.
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class StrategicMap extends GenericResource {

    /**
     * Genera la vista del mapa Estrat&eacute;gico.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        Resource base = getResourceBase();
        WebSite webSite = base.getWebSite();
        PrintWriter out = response.getWriter();
        
        if (webSite instanceof BSC) {
            Period period = getPeriod(request);
            if(period != null) {
                //Resource base = paramRequest.getResourceBase();
                BSC bsc = (BSC)webSite;
                StringBuilder svg = new StringBuilder();
                svg.append("\n<div id=\"emap_"+bsc.getId()+"\">\n");
//                try {
//                    Document dom = bsc.getDom(period);    
//                    svg.append(getSvg(dom));
//                }catch(XPathException xpe) {
//                }
                svg.append("</div>\n");
//                CausalMap map = new CausalMap();
//                CausalArrows arrows = new CausalArrows(map);
//                out.println(arrows.draw(bsc, period, base));
            } else {
                out.println("<p>" + paramRequest.getLocaleString("errorPeriod") + "</p>");
            }
        }
    }

    /**
     * Permite capturar y almacenar la informaci&oacute;n para configurar un
     * mapa estrat&eacute;gico
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();
        String id = paramRequest.getResourceBase().getId();
        SWBResourceURL url = paramRequest.getActionUrl().setAction("update");
        Resource base = paramRequest.getResourceBase();
        WebSite ws = paramRequest.getWebPage().getWebSite();

        String amountPerspective = base.getData("amountPerspective") == null ? ""
                : base.getData("amountPerspective");
        String amountTheme = base.getData("amountTheme") == null ? ""
                : base.getData("amountTheme");
        String amountObjective = base.getData("amountObjective") == null ? ""
                : base.getData("amountObjective");
        String widthHorizontalObjective = base.getData("widthHorizontalObjective") == null ? ""
                : base.getData("widthHorizontalObjective");
        String widthVerticalObjective = base.getData("widthVerticalObjective") == null ? ""
                : base.getData("widthVerticalObjective");
        String amountDifferentiatorGroup = base.getData("amountDifferentiatorGroup") == null ? ""
                : base.getData("amountDifferentiatorGroup");
        String amountDifferentiator = base.getData("amountDifferentiator") == null ? ""
                : base.getData("amountDifferentiator");
        String widthHorizontalDifferentiator = base.getData("widthHorizontalDifferentiator") == null ? ""
                : base.getData("widthHorizontalDifferentiator");
        String colorRelOO = base.getData("colorRelOO") == null ? "" : base.getData("colorRelOO");
        String colorRelOT = base.getData("colorRelOT") == null ? "" : base.getData("colorRelOT");
        String colorRelTO = base.getData("colorRelTO") == null ? "" : base.getData("colorRelTO");
        String colorRelTT = base.getData("colorRelTT") == null ? "" : base.getData("colorRelTT");
        String colorRelPP = base.getData("colorRelPP") == null ? "" : base.getData("colorRelPP");
        String ty_vision = base.getData("ty_vision") == null ? "" : base.getData("ty_vision");
        String bg_vision = base.getData("bg_vision") == null ? "" : base.getData("bg_vision");
        String ty_mision = base.getData("ty_mision") == null ? "" : base.getData("ty_mision");
        String bg_mision = base.getData("bg_mision") == null ? "" : base.getData("bg_mision");

        sb.append("<script type=\"text/javascript\">\n");
        sb.append("  dojo.require('dijit.form.Form');\n");
        sb.append("</script>\n");
        sb.append("\n<script type=\"text/javascript\">\n");
        sb.append("<!--\n");
        sb.append("var swOk=0, optionObj;");
        sb.append("\nfunction jsValida()");
        sb.append("{");
        sb.append("   var ele=document.getElementById(\"frmAdm");
        sb.append(id);
        sb.append("\");");
        sb.append("   var ele1=document.getElementById(\"widthHorizontalObjective\");");
        sb.append("   var ele2=document.getElementById(\"widthVerticalObjective\");");
        sb.append("   var ele3=document.getElementById(\"widthHorizontalDifferentiator\");");

        sb.append("   var ele5=document.getElementById(\"amountDifferentiator\");");
        sb.append("   var ele6=document.getElementById(\"amountObjective\");");
        sb.append("   var ele7=document.getElementById(\"amountDifferentiator\");");
        sb.append("   var ele8=document.getElementById(\"amountPerspective\");");
        sb.append("   var ele9=document.getElementById(\"amountTheme\");");
        sb.append("   var ele10=document.getElementById(\"amountDifferentiatorGroup\");");
        sb.append("   if(!isNumber(ele1)) return false;");
        sb.append("   if(!isNumber(ele2)) return false;");
        sb.append("   if(!isNumber(ele3)) return false;");
        sb.append("   if(!isNumber(ele5)) return false;");
        sb.append("   if(!isNumber(ele6)) return false;");
        sb.append("   if(!isNumber(ele7)) return false;");
        sb.append("   if(!isNumber(ele8)) return false;");
        sb.append("   if(!isNumber(ele9)) return false;");
        sb.append("   if(!isNumber(ele10)) return false;");
        sb.append("   return true;");
        sb.append("}");

        sb.append("\nfunction isNumber(pIn)");
        sb.append("\n{");
        sb.append("\n   pCaracter=pIn.value;");
        sb.append("\n   for (var i=0;i<pCaracter.length;i++)");
        sb.append("\n   {");
        sb.append("\n       var sByte=pCaracter.substring(i,i+1);");
        sb.append("\n       if (sByte<\"0\" || sByte>\"9\")");
        sb.append("\n       {");
        sb.append("\n           pIn.focus();");
        sb.append("\n           alert('" + SWBUtils.TEXT.getLocaleString("locale_swb_util",
                "usrmsg_WBResource_loadIsNumber_msg") + ".');");
        sb.append("\n           return false;");
        sb.append("\n       }");
        sb.append("\n   }");
        sb.append("\n   return true;");
        sb.append("\n}");

        sb.append("\n-->");
        sb.append("\n</script>");

        sb.append("\n<div class=\"swbform\">");
        sb.append("\n<form type=\"dijit.form.Form\" class=\"swbform\" id=\"frmAdm");
        sb.append(id);
        sb.append("\" onsubmit=\"return jsValida()\" name=\"frmAdm");
        sb.append(id);
        sb.append("\" action=\"");
        sb.append(url);
        sb.append("\" method=\"post\" >");
        sb.append("\n<input type=\"hidden\" name=\"suri\" value=\"");
        sb.append(id);
        sb.append("\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configPerspec"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountPerspective\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountPerspective\" name=\"amountPerspective\" type=\"text\" ");
        sb.append(" regExp=\"\\d+\" value=\"");
        sb.append(amountPerspective);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe");
        sb.append(" ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        Iterator<Perspective> itPers = Perspective.ClassMgr.listPerspectives(ws);
        while (itPers.hasNext()) {
            Perspective perspective = itPers.next();
            String colorTextPerspective = base.getData("ty_perspective" + perspective.getId())
                    == null ? "" : base.getData("ty_perspective" + perspective.getId());
            String viewPerspective = base.getData("perspective" + id + perspective.getId()) == null ? ""
                    : base.getData("perspective" + id + perspective.getId());

            sb.append("\n<ul class=\"swbform-ul\">");
            sb.append("\n<li class=\"swbform-li\">");
            sb.append(perspective.getTitle());
            sb.append(": </li>");

            String select = viewPerspective.equals("") ? "" : "checked";
            sb.append("\n<li class=\"swbform-li\"><input id=\"perspective");
            sb.append(id);
            sb.append(perspective.getId());
            sb.append("\" name=\"perspective");
            sb.append(id);
            sb.append(perspective.getId());
            sb.append("\" type=\"checkbox\" value=\"");
            sb.append(perspective.getId());
            sb.append("\" ");
            sb.append(" data-dojo-type=\"dijit.form.CheckBox\" ");
            sb.append(select);
            sb.append(" class=\"swbform-label\">");
            sb.append("\n<label for=\"perspective");
            sb.append(id);
            sb.append(perspective.getId());
            sb.append("\">");
            sb.append(paramRequest.getLocaleString("showPerspective"));
            sb.append("\n</label></li>");
            sb.append("\n</ul>");

            String strPers = "ty_perspective" + perspective.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strPers);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("letterColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strPers);
            sb.append("\" name=\"");
            sb.append(strPers);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorTextPerspective);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");
        }

        sb.append("\n</fieldset>");
        sb.append("\n<div id=\"configcol\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configThemes"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configThemes"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountTheme\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountTheme\" name=\"amountTheme\" type=\"text\" regExp=\"\\d+\"");
        sb.append(" value=\"");
        sb.append(amountTheme);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ");
        sb.append("ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");

        Iterator<Theme> itTheme = Theme.ClassMgr.listThemes(ws);
        while (itTheme.hasNext()) {
            Theme theme = itTheme.next();
            String colorBgTheme = base.getData("bg_theme_" + theme.getId()) == null ? ""
                    : base.getData("bg_theme_" + theme.getId());
            String colorTxtTheme = base.getData("ty_theme_" + theme.getId()) == null ? ""
                    : base.getData("ty_theme_" + theme.getId());
            sb.append("\n<ul class=\"swbform-ul\">");
            sb.append("\n<li class=\"swbform-li\">");
            sb.append(theme.getTitle());
            sb.append(": </li>");

            String strTheme = "ty_theme_" + theme.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strTheme);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("letterColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strTheme);
            sb.append("\" name=\"");
            sb.append(strTheme);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorTxtTheme);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

            String strTheme1 = "bg_theme_" + theme.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strTheme1);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("bgColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strTheme1);
            sb.append("\" name=\"");
            sb.append(strTheme1);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorBgTheme);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");
            sb.append("\n</ul>");
        }
        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<div id=\"configObj\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configObjec"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configObjec"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"widthHorizontalObjective\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("heightHoriz"));
        sb.append(": </label>");
        sb.append("\n<input id=\"widthHorizontalObjective\" name=\"widthHorizontalObjective\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"");
        sb.append(widthHorizontalObjective);
        sb.append("\" ");
        sb.append("dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\"><label for=\"widthVerticalObjective\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("heightVert"));
        sb.append(": </label>");
        sb.append("\n<input id=\"widthVerticalObjective\" name=\"widthVerticalObjective\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"");
        sb.append(widthVerticalObjective);
        sb.append("\"");
        sb.append(" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountObjective\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountObjective\" name=\"amountObjective\" type=\"text\" regExp=\"\\d+\"");
        sb.append(" value=\"");
        sb.append(amountObjective);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ");
        sb.append("ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");

        sb.append("\n</fieldset>");
        sb.append("\n</div>");


        sb.append("\n<div id=\"configGDiffere\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configGDiff"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configGDiff"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\"><label for=\"amountDifferentiatorGroup\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountDifferentiatorGroup\" name=\"amountDifferentiatorGroup\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"");
        sb.append(amountDifferentiatorGroup);
        sb.append("\"");
        sb.append(" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"Captura cantidad.\"");
        sb.append(" invalidMessage=\"El valor proporcionado debe ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        Iterator<DifferentiatorGroup> itDiffeG = DifferentiatorGroup.ClassMgr.listDifferentiatorGroups(ws);
        while (itDiffeG.hasNext()) {
            DifferentiatorGroup diffG = itDiffeG.next();
            String colorBgTheme = base.getData("bg_diffG_" + diffG.getId()) == null ? ""
                    : base.getData("bg_diffG_" + diffG.getId());
            String colorTxtTheme = base.getData("ty_diffG_" + diffG.getId()) == null ? ""
                    : base.getData("ty_diffG_" + diffG.getId());
            sb.append("\n<ul class=\"swbform-ul\">");
            sb.append("\n<li class=\"swbform-li\">");
            sb.append(diffG.getTitle());
            sb.append(": </li>");

            String strTheme = "ty_diffG_" + diffG.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strTheme);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("letterColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strTheme);
            sb.append("\" name=\"");
            sb.append(strTheme);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorTxtTheme);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");

            String strTheme1 = "bg_diffG_" + diffG.getId();
            sb.append("\n<li class=\"swbform-li\"><label for=\"");
            sb.append(strTheme1);
            sb.append("\" class=\"swbform-label\">");
            sb.append(paramRequest.getLocaleString("bgColor"));
            sb.append(": </label>");
            sb.append("\n<input id=\"");
            sb.append(strTheme1);
            sb.append("\" name=\"");
            sb.append(strTheme1);
            sb.append("\" type=\"text\" ");
            sb.append("value=\"");
            sb.append(colorBgTheme);
            sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
            sb.append("\n</li>");
            sb.append("\n</ul>");

        }
        sb.append("\n</fieldset>");
        sb.append("\n</div>");


        sb.append("\n<div id=\"configDife\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configDiff"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configDiff"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"widthHorizontalDifferentiator\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("heightHoriz"));
        sb.append(": </label>");
        sb.append("\n<input id=\"widthHorizontalDifferentiator\" name=\"widthHorizontalDifferentiator\" ");
        sb.append("type=\"text\" regExp=\"\\d+\" ");
        sb.append("value=\"");
        sb.append(widthHorizontalDifferentiator);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado ");
        sb.append("debe ser númerico.\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"amountDifferentiator\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("amountText"));
        sb.append(": </label>");
        sb.append("\n<input id=\"amountDifferentiator\" name=\"amountDifferentiator\" type=\"text\" ");
        sb.append("regExp=\"\\d+\" value=\"");
        sb.append(amountDifferentiator);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\" ");
        sb.append("promptMessage=\"Captura cantidad.\" invalidMessage=\"El valor proporcionado debe ");
        sb.append("ser númerico.\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");
        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<div id=\"configCausal\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("confCausal"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("confCausal"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelOO\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssOO"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelOO\" name=\"colorRelOO\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelOO);
        sb.append( "\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelOT\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssOT"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelOT\" name=\"colorRelOT\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelOT);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelTO\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssTO"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelTO\" name=\"colorRelTO\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelTO);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelTT\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssTT"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelTT\" name=\"colorRelTT\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelTT);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");
        
        sb.append("\n<li class=\"swbform-li\">");
        sb.append("\n<label for=\"colorRelPP\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("cssPP"));
        sb.append(": </label>");
        sb.append("\n<input id=\"colorRelPP\" name=\"colorRelPP\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(colorRelPP);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");        
        
        String select = base.getData("margins") == null ? "" : "checked";
        sb.append("\n<li class=\"swbform-li\"><input id=\"margins");
        sb.append("\" name=\"margins");
        sb.append("\" type=\"checkbox\" value=\"margins\" ");
        sb.append(" data-dojo-type=\"dijit.form.CheckBox\" ");
        sb.append(select);
        sb.append(" class=\"swbform-label\">");
        sb.append("\n<label for=\"margins\">");
        sb.append(paramRequest.getLocaleString("addMargin"));
        sb.append("\n</label></li>");

        sb.append("\n</ul>");

        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<div id=\"configHeaders\\/");
        sb.append(id);
        sb.append("\" dojoType=\"dijit.TitlePane\" title=\"");
        sb.append(paramRequest.getLocaleString("configVM"));
        sb.append("\"  ");
        sb.append("open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
        sb.append("\n<fieldset>");
        sb.append("\n<legend>");
        sb.append(paramRequest.getLocaleString("configVM"));
        sb.append("</legend>");
        sb.append("\n<ul class=\"swbform-ul\">");

        String strVision = "ty_vision";
        sb.append("\n<li class=\"swbform-li\"><label for=\"");
        sb.append(strVision);
        sb.append("\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("letterColorV"));
        sb.append(": </label>");
        sb.append("\n<input id=\"");
        sb.append(strVision);
        sb.append("\" name=\"");
        sb.append(strVision);
        sb.append("\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(ty_vision);
        sb.append( "\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        String strVision1 = "bg_vision";
        sb.append("\n<li class=\"swbform-li\"><label for=\"");
        sb.append(strVision1);
        sb.append("\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("bgColorV"));
        sb.append(": </label>");
        sb.append("\n<input id=\"");
        sb.append(strVision1);
        sb.append( "\" name=\"");
        sb.append(strVision1);
        sb.append("\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(bg_vision);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        String strMision = "ty_mision";
        sb.append("\n<li class=\"swbform-li\"><label for=\"");
        sb.append(strMision);
        sb.append("\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("letterColorM"));
        sb.append(": </label>");
        sb.append("\n<input id=\"");
        sb.append(strMision);
        sb.append("\" name=\"");
        sb.append(strMision);
        sb.append("\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(ty_mision);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");

        String strMision1 = "bg_mision";
        sb.append("\n<li class=\"swbform-li\"><label for=\"");
        sb.append(strMision1);
        sb.append("\" class=\"swbform-label\">");
        sb.append(paramRequest.getLocaleString("bgColorM"));
        sb.append(": </label>");
        sb.append("\n<input id=\"");
        sb.append(strMision1);
        sb.append("\" name=\"");
        sb.append(strMision1);
        sb.append("\" type=\"text\" ");
        sb.append("value=\"");
        sb.append(bg_mision);
        sb.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
        sb.append("\n</li>");
        sb.append("\n</ul>");

        sb.append("\n</fieldset>");
        sb.append("\n</div>");

        sb.append("\n<fieldset>");
        sb.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\" value=\"Submit\" >");
        sb.append(paramRequest.getLocaleString("save"));
        sb.append("</button>&nbsp;");
        sb.append("\n <button dojoType=\"dijit.form.Button\" type=\"reset\" value=\"Reset\">");
        sb.append(paramRequest.getLocaleString("cancel"));
        sb.append("</button>");
        sb.append("\n</fieldset>");

        sb.append("\n</form>");
        sb.append("\n</div>");
        if ((request.getParameter("statusMsg")) != null
                && (!request.getParameter("statusMsg").isEmpty())) {
            sb.append("\n<script type=\"text/javascript\" language=\"JavaScript\">");
            sb.append("\n   alert('");
            sb.append(request.getParameter("statusMsg"));
            sb.append("');");
            sb.append("\n   window.location.href='");
            sb.append(paramRequest.getRenderUrl().setAction("edit"));
            sb.append("';");
            sb.append("\n</script>");
        }
        out.println(sb.toString());
    }

    /**
     * Realiza las operaciones de almacenamiento de la configuraci&oacute;n para
     * la visualizaci&oacute;n del mapa estrat&eacute;gico.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String action = response.getAction();
        Resource base = response.getResourceBase();
        WebSite ws = response.getWebPage().getWebSite();
        String id = base.getId();
        if ("update".equals(action)) {
            Enumeration enumAttri = request.getParameterNames();

            Iterator<Perspective> itPers = Perspective.ClassMgr.listPerspectives(ws);
            while (itPers.hasNext()) {
                Perspective perspective = itPers.next();
                String showTitlePerspective = "show_" + perspective.getTitle() + perspective.getId();
                String viewPerspective = "perspective" + id + perspective.getId();
                base.setData(showTitlePerspective, null);
                base.setData(viewPerspective, null);
            }
            base.setData("margins", null);

            while (enumAttri.hasMoreElements()) {
                Object elem = enumAttri.nextElement();
                String data = (String) request.getParameter(elem.toString());
                base.setData(elem.toString(), data);
            }
        }
        response.setRenderParameter("statusMsg", "Actualización exitosa");
        response.setAction(SWBResourceURL.Mode_ADMIN);
    }

    /**
     * Obtiene el periodo seleccionado actual
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param ws sitio web
     * @return un objeto {@code Periodo} que representa el Periodo actual
     * seleccionado
     */
    private Period getPeriod(HttpServletRequest request)
    {
        WebSite model = getResourceBase().getWebSite();
        Period period = null;
        
        HttpSession session = request.getSession(true);
        final String pid = (String)session.getAttribute(model.getId());
        if(Period.ClassMgr.hasPeriod(pid, model)) {
            period = Period.ClassMgr.getPeriod(pid, model);
        }
        
//        if (request.getSession(true).getAttribute(id) != null) {
//            String pid = (String) request.getSession(true).getAttribute(id);
//            if(Period.ClassMgr.hasPeriod(pid, ws)) {
//                period = Period.ClassMgr.getPeriod(pid, ws);
//            }
//        } 
//        if(period == null) {
//            BSC bsc = (BSC) ws;
//            period = bsc.listValidPeriods().iterator().next();
//        }
        return period;
    }
    
    private String urlBase = null;
    
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        WebPage wp = base.getWebSite().getWebPage(Objective.class.getSimpleName());
        urlBase = wp.getUrl() + "?suri=";
    }
    
    public static final String HEADER_PREFIX = "head_";
    public static final int MARGEN_LEFT = 10; // Especifica el margen izquierdo del rectángulo de una perspectiva
    public static final int MARGEN_RIGHT = 100; // Especifica el margen derecho del rectángulo de una perspectiva
    public static final int MARGEN_TOP = 20; // Especifica el margen superior del rectángulo de una perspectiva
    public static final int MARGEN_BOTTOM = 20; // Especifica el margen inferior del rectángulo de una perspectiva
    
    public static final int HEADER_HEIGHT = 150; // altura del encabezado
    public static final int HEADER_1 = 24; // tamaño de fuente para título del mapa
    public static final int HEADER_2 = 18; // tamaño de fuente para misión, visión
    public static final int HEADER_3 = 16; // tamaño de fuente para temas
    public static final int HEADER_4 = 14; // tamaño de fuente para diferenciadores
    public static final int HEADER_5 = 12; // tamaño de fuente para objetivos
    
    public static final int BOX_SPACING = 16; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_TOP = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_BOTTOM = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    
    public static final int PADDING_TOP = 4; // Especifica el espacio libre arriba entre rectángulos para pintar las ligas
    public static final int PADDING_LEFT = 2; // Especifica el espacio libre a la izquieerda entre rectángulos para pintar las ligas
    public static final int PADDING_RIGHT = 2; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final int PADDING_DOWN = 4; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final String SVG_NS_URI = "http://www.w3.org/2000/svg";
    public static final String XLNK_NS_URI = "http://www.w3.org/1999/xlink";
    
    private int width, height;
    public int getWidth() {
        return width;
    }
    private void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    private void setHeight(int height) {
        this.height = height;
    }
    
    public Document getDom(Document documentBSC) throws XPathExpressionException, NumberFormatException
    {
//        Resource base = getResourceBase();
//        final BSC scorecard = (BSC)base.getWebSite();
//        setWidth(assertValue(base.getAttribute("width", "1024")));
//        setHeight(assertValue(base.getAttribute("height", "1400")));
        width = 1024;
        height = 1400;
        //final Period period = getPeriod(request);
        //Document documentBSC = scorecard.getDom(period);
        
        //Document documentBSC = scorecard.getDom();
        Element root = documentBSC.getDocumentElement();        
        root.setAttribute("width", Integer.toString(width));
        root.setAttribute("height", Integer.toString(height));
                
//        root.setAttribute("width", Integer.toString(width));
//        root.setAttribute("height", Integer.toString(height));
//        final int width = 1024;
//        final int height = 1400;
                
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        //header
        Element header = documentBSC.createElement("header");
        header.setAttribute("id", HEADER_PREFIX+"DADT");
        header.setAttribute("width", Integer.toString(width));
        header.setAttribute("height", Integer.toString(HEADER_HEIGHT));
        header.setAttribute("x", "0");
        header.setAttribute("y", "1");
        String expression = "/bsc/title";
        Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/mission";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/vision";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/logo";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        
        expression = "/bsc/perspective[1]";
        Node p1 = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);        
        root.insertBefore(header, p1);
        
        final int px;
        final int pw, ph;
        final int perspCount;
        String uri;
        Boolean hiddenTheme;
        
        //para cada perspectiva: width, height, x, y
        expression = "/bsc/perspective";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        perspCount = nodeList.getLength();
        pw = width-MARGEN_LEFT-MARGEN_RIGHT;
        ph = (height-HEADER_HEIGHT)/perspCount -  MARGEN_TOP - MARGEN_BOTTOM;
        px = MARGEN_LEFT;
        
        //lista de perspectivas
        for(int j=0; j<perspCount; j++) {
            int h_ = ph;
            Node nodep = nodeList.item(j);
            if(nodep.getNodeType()==Node.ELEMENT_NODE)
            {
                //perspectiva
                Element p = (Element)nodep;
                uri = p.getAttribute("id");
                p.setAttribute("width", Integer.toString(pw));
                p.setAttribute("x", Integer.toString(px));
                
                //diferenciadores de la perspectiva
                expression = "/bsc/perspective[@id='"+uri+"']/diffgroup[1]/diff";
                NodeList nlDiffs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlDiffsCount = nlDiffs.getLength();
                final boolean hasDifferentiators = nlDiffsCount>0;
                if(hasDifferentiators) {
                    final int dw = pw/nlDiffsCount;
                    for(int k=0; k<nlDiffsCount; k++) {
                        Node noded = nlDiffs.item(k);
                        Element d = (Element)noded;
                        d.setAttribute("width", Integer.toString(dw-BOX_SPACING));
                        d.setAttribute("x", Integer.toString(px + k*dw + BOX_SPACING));
                    }
                }
                
                //lista de temas por perspectiva                
                expression = "/bsc/perspective[@id='"+uri+"']/themes/theme";
                NodeList nlThms = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlThmsCount = nlThms.getLength();
                final  boolean hasThemes = nlThmsCount>0;
                if(hasThemes)
                {
                    final int tw = pw/nlThmsCount;
                    int tx;
                    for(int k=0; k<nlThmsCount; k++)
                    {
                        Node nodet = nlThms.item(k);
                        if(nodet.getNodeType()==Node.ELEMENT_NODE) {
                            Element t = (Element)nodet;
                            uri = t.getAttribute("id");
                            hiddenTheme = Boolean.parseBoolean(t.getAttribute("hidden"));
                            t.setAttribute("width", Integer.toString(tw-BOX_SPACING));
                            tx = px + k*tw + BOX_SPACING;
                            t.setAttribute("x", Integer.toString(tx));
                            
                            //relaciones con este tema
                            expression = "//rel[@to='"+uri+"']";
                            NodeList nlRels = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                            for(int v=0; v<nlRels.getLength(); v++) {
                                Node noder = nlRels.item(v);
                                if(noder!=null && noder.getNodeType()==Node.ELEMENT_NODE) {
                                    Element rel = (Element)noder;
                                    rel.setAttribute("rx", Integer.toString(tx+tw/2));
                                }
                            }
                            
                            //lista de objetivos por tema
                            expression = "//theme[@id='"+uri+"']/obj";
                            NodeList nlObjs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                            final int nlObjsCount = nlObjs.getLength();
                            if(nlObjsCount>0)
                            {
                                int hObj;
                                if(hiddenTheme) {
                                    hObj = h_/(nlObjsCount);
                                    t.setAttribute("height","0");
                                }else {
                                    //hObj = h_/(nlObjsCount+1);
                                    hObj = (h_-HEADER_3)/nlObjsCount;
                                    //t.setAttribute("height",Integer.toString(hObj));
                                    t.setAttribute("height", Integer.toString(HEADER_3));
                                }
                                for(int l=0; l<nlObjsCount; l++)
                                {
                                    Node nodeo = nlObjs.item(l);
                                    int ox = tx;
                                    if(nodeo.getNodeType()==Node.ELEMENT_NODE) {
                                        Element o = (Element)nodeo;
                                        uri = o.getAttribute("id");                                
                                        o.setAttribute("width", Integer.toString(tw-PADDING_RIGHT));
                                        o.setAttribute("height", Integer.toString(hObj-PADDING_TOP));
                                        o.setAttribute("x", Integer.toString(ox));  
                                        o.setAttribute("href",  urlBase+uri);

                                        //relaciones con este objetivo
                                        expression = "//rel[@to='"+uri+"']";
                                        nlRels = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                                        for(int m=0; m<nlRels.getLength(); m++) {
                                            Node noder = nlRels.item(m);
                                            if(noder.getNodeType()==Node.ELEMENT_NODE) {
                                                Element rel = (Element)noder;
                                                rel.setAttribute("rx", Integer.toString(ox+tw/2));
                                            }
                                        }
                                    }
                                } //lista de objetivos por tema
                            }
                        }
                    } //lista de temas
                }
            }
        } //lista de perspectivas
        
        
        
        // TODO imagen logo
        // corregir los 0?1:
        // atributo height de los diferenciadores. Esto depende del numero de tema+objetivos
        // atributo href de los objetivos
        // corregir coordenadas x,y
        return documentBSC;
    }
    
//    public String getSvg() throws XPathExpressionException
//    {
//        Document xmlBSC = getDom()
//    }
    
    public String getSvg(Document documentBSC) throws XPathExpressionException
    {
        Resource base = getResourceBase();
        String  id, txt, expression;

        int w, h, w_, h_;
        int x, y=0, x_, y_;
        StringBuilder SVGjs = new StringBuilder();
//        final String emapId = base.getWebSiteId();
        final String emapId = "bgj001";
        
        //Document documentBSC = getDom();
        Element rootBSC = documentBSC.getDocumentElement();
        width = assertValue(rootBSC.getAttribute("width"));
        height = assertValue(rootBSC.getAttribute("height"));
        width = 1024;
        height = 1400;
    
        SVGjs.append("<script type=\"text/javascript\">").append("\n");
        SVGjs.append(" var SVG_ = '"+SVG_NS_URI+"';").append("\n");
        SVGjs.append(" var XLINK_ = '"+XLNK_NS_URI+"';").append("\n");
        SVGjs.append(" var svg = document.createElementNS(SVG_,'svg'); ").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'id','"+emapId+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'width','"+width+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'height','"+height+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'viewBox','0,0,"+width+","+height+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'version','1.1');").append("\n");
        SVGjs.append(" document.body.appendChild(svg);").append("\n");
        
        SVGjs.append(" var defs = document.createElementNS('http://www.w3.org/2000/svg', 'defs');").append("\n");
        SVGjs.append(" var marker = document.createElementNS('http://www.w3.org/2000/svg', 'marker');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'id', 'arrow_1');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'viewBox', '0 0 10 10');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'refX', '8');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'refY', '5');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'markerUnits', 'strokeWidth');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'markerWidth', '4');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'markerHeight', '4');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'orient', 'auto');").append("\n");
        SVGjs.append(" var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');").append("\n");
        SVGjs.append(" path.setAttributeNS(null,'d', 'M0 0 L10 5 L0 10 z');").append("\n");
        SVGjs.append(" path.setAttributeNS(null,'fill', 'red');").append("\n");
        SVGjs.append(" path.setAttributeNS(null,'stroke', 'red');").append("\n");
        SVGjs.append(" path.setAttributeNS(null,'stroke-width', '0');").append("\n");
        SVGjs.append(" marker.appendChild(path);").append("\n");
        SVGjs.append(" defs.appendChild(marker);").append("\n");
        SVGjs.append(" svg.appendChild(defs);").append("\n");
        
        SVGjs.append(" var r;").append("\n");
        SVGjs.append(" var w;").append("\n");
        SVGjs.append(" var g;").append("\n");
        SVGjs.append(" var txt;").append("\n");
        SVGjs.append(" var rect;").append("\n");
        SVGjs.append(" var path;").append("\n");
        SVGjs.append(" var lnk;").append("\n");
        SVGjs.append(" var to;").append("\n");
        SVGjs.append(" var parent;").append("\n");
        SVGjs.append(" var matxTo;").append("\n");
        SVGjs.append(" var matxFrm;").append("\n");
        SVGjs.append(" var posTo;").append("\n");
        SVGjs.append(" var posFrm;").append("\n");
        
        XPath xPath = XPathFactory.newInstance().newXPath();
        expression = "/bsc/header";
        Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        if(node!=null && node instanceof Element)
        {
            NamedNodeMap attrs = node.getAttributes();
            id = attrs.getNamedItem("id").getNodeValue();
            w = assertValue(attrs.getNamedItem("width").getNodeValue());
            h = assertValue(attrs.getNamedItem("height").getNodeValue());
            x = assertValue(attrs.getNamedItem("x").getNodeValue());
            y = assertValue(attrs.getNamedItem("y").getNodeValue());

            SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
            SVGjs.append(" g.setAttributeNS(null,'id','"+id+"');").append("\n");
            SVGjs.append(" svg.appendChild(g);").append("\n");

            x_ = x;
            y_ = y + topPadding(HEADER_1);
            w_ = w;

            // título mapa
            expression = "/bsc/header/title";
            txt = (String) xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
            SVGjs.append(" txt = createText('"+txt+"',"+(x_+w_/2)+","+y_+","+HEADER_1+",'Verdana');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
//            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_1+","+x_+","+y_+");").append("\n");
//            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
//            SVGjs.append(" framingRect(rect,'"+id+"_ptitle',"+w_+","+vPadding(HEADER_1)+","+x_+","+y_+");").append("\n");
//            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");

            y_ = y + vPadding(HEADER_1) + topPadding(HEADER_2) + PADDING_DOWN;
            w_ = w/3;
            h_ = h - vPadding(HEADER_1) - PADDING_DOWN;
            // pleca Mision
            SVGjs.append(" txt = createText('Mision',"+(x_+w_/2)+","+y_+","+HEADER_2+",'Verdana');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
//            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_2+","+x_+","+y_+");").append("\n");
//            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
//            SVGjs.append(" framingRect(rect,'"+id+"_pmission',"+w_+","+vPadding(HEADER_2)+","+x_+","+y_+");").append("\n");
//            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
            // pleca Vision
            SVGjs.append(" txt = createText('Vision',"+(x_+5*w_/2)+","+y_+","+HEADER_2+",'Verdana');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
//            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_2+","+(x_+2*w_)+","+y_+");").append("\n");
//            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
//            SVGjs.append(" framingRect(rect,'"+id+"_pvision',"+w_+","+vPadding(HEADER_2)+","+(x_+2*w_)+","+y_+");").append("\n");
//            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
            // logo
            expression = "/bsc/header/logo";
            node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
            if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
                SVGjs.append(" rect = createRect('"+id+"_lg"+"',"+w_+","+h_+","+(x_+w_)+","+(y_-HEADER_2)+",0,0,'none',1,'red',1,1);").append("\n");
                SVGjs.append(" g.appendChild(rect);").append("\n");
            }

            // contenido Mision
            y_ = y_ + vPadding(HEADER_2);
            h_ = h_ - vPadding(HEADER_2);    
            expression = "/bsc/header/mission";
            txt = (String) xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
            SVGjs.append(" txt = createText('"+txt+"',"+x_+","+y_+",14,'Verdana');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+x_+","+y_+");").append("\n");
            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
            SVGjs.append(" framingRect(rect,'"+id+"_cmission',"+w_+","+h_+","+x_+","+y_+");").append("\n");
            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
            // contenido Vision
            expression = "/bsc/header/vision";
            txt = (String) xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
            SVGjs.append(" txt = createText('"+txt+"',"+(x_+2*w_)+","+y_+",14,'Verdana');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+h_+","+(x_+2*w_)+","+y_+");").append("\n");
            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
            SVGjs.append(" framingRect(rect,'"+id+"_cvision',"+w_+","+h_+","+(x_+2*w_)+","+y_+");").append("\n");
            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
        }
        
        String title;
        y = y + HEADER_HEIGHT + MARGEN_TOP;
        SVGjs.append(" var y = "+y+";").append("\n");
        StringBuilder info;
        // lista de perspectivas
        expression = "/bsc/perspective";
        NodeList nlPersp = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        for(int j=0; j<nlPersp.getLength(); j++) {
            node = nlPersp.item(j);
            if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
                NamedNodeMap attrs = node.getAttributes();
                String pid = attrs.getNamedItem("id").getNodeValue();
                int pw = assertValue(attrs.getNamedItem("width").getNodeValue());
                int px = assertValue(attrs.getNamedItem("x").getNodeValue());

                // título de la perspectiva
                expression = "/bsc/perspective[@id='"+pid+"']/title";
                String title_ = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);

                SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
                SVGjs.append(" g.setAttributeNS(null,'id','"+pid+"');").append("\n");
                SVGjs.append(" svg.appendChild(g);").append("\n");
                SVGjs.append(" g.setAttributeNS(null,'transform','translate("+px+",'+y+')');").append("\n");
                SVGjs.append(" var y_ = "+PADDING_TOP+";").append("\n");
        
                // diferenciadores de la perspectiva
                expression = "/bsc/perspective[@id='"+pid+"']/diffgroup[1]/diff";
                NodeList nlDiffs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                boolean hasDifferentiators = nlDiffs.getLength()>0;
                if(hasDifferentiators)
                {
                    SVGjs.append(" y_ += "+BOX_SPACING+";").append("\n");
                    for(int k=0; k<nlDiffs.getLength(); k++)
                    {
                        Node nodeD = nlDiffs.item(k);
                        if(nodeD!=null && nodeD.getNodeType()==Node.ELEMENT_NODE)
                        {
                            attrs = nodeD.getAttributes();
                            String did = attrs.getNamedItem("id").getNodeValue();
                            w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                            x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                            SVGjs.append(" txt = createText('"+nodeD.getFirstChild().getNodeValue()+"',"+x_+",y_,"+HEADER_4+",'Verdana');").append("\n");
                            SVGjs.append(" g.appendChild(txt);").append("\n");
                            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+topPadding(HEADER_4)+","+x_+",y_);").append("\n");
                            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                            SVGjs.append(" framingRect(rect,'"+did+"',"+w_+",rect.height.baseVal.value,"+x_+",y_);").append("\n");
                            SVGjs.append(" rect.y.baseVal.value = y_-"+HEADER_4+";").append("\n");
                            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                        }
                    }
                }
        
                SVGjs.append(" var y__;").append("\n");

                // temas de la perspectiva
                if(hasDifferentiators) {
                    SVGjs.append(" y__ = y_ + rect.height.baseVal.value + "+BOX_SPACING+";").append("\n");
                }else {
                    SVGjs.append(" y__ = y_ + "+BOX_SPACING+";").append("\n");
                }
                expression = "/bsc/perspective[@id='"+pid+"']/themes/theme";
                NodeList nlThms = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                for(int l=0; l<nlThms.getLength(); l++)
                {
                    Node nodeT = nlThms.item(l);
                    if(nodeT!=null && nodeT.getNodeType()==Node.ELEMENT_NODE)
                    {
                        attrs = nodeT.getAttributes();
                        boolean isHidden = Boolean.parseBoolean(attrs.getNamedItem("hidden").getNodeValue());
                        String tid = attrs.getNamedItem("id").getNodeValue();
                        w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                        x_ = assertValue(attrs.getNamedItem("x").getNodeValue());

                        // r guarda algunas valores de la perspectiva actual para después recuperarlas por su identificador
                        // esto resulta muy conveniente para construir los paths de relaciones causa/efecto
                        SVGjs.append(" r = document.createElementNS(SVG_,'rect');").append("\n");
                        SVGjs.append(" r.setAttributeNS(null,'id','w_"+pid+"');").append("\n");
                        SVGjs.append(" r.setAttributeNS(null,'width',"+w_+");").append("\n");
                        SVGjs.append(" defs.appendChild(r);").append("\n");                

                        // rectángulo tema
                        if(!isHidden)
                        {
                            expression = "/bsc/perspective[@id='"+pid+"']/themes/theme[@id='"+tid+"']/title";
                            title = (String)xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING);
                            SVGjs.append(" txt = createText('"+title+"',"+x_+",y__,"+HEADER_3+",'Verdana');").append("\n");
                            SVGjs.append(" g.appendChild(txt);").append("\n");
                            SVGjs.append(" fixParagraphToWidth(txt,"+w_+","+x_+");").append("\n");
                            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                            SVGjs.append(" framingRect(rect,'"+tid+"',"+w_+",rect.height.baseVal.value,"+x_+",y__);").append("\n");
                            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                        }

                        // relaciones causa-efecto con este tema
                        expression = "//theme[@id='"+tid+"']/rel";
                        NodeList nlRels = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                        SVGjs.append(" console.log('el tema "+tid+" ');");
                        SVGjs.append(" console.log('tiene "+nlRels.getLength()+" relaciones ');");
                        for(int n=0; n<nlRels.getLength(); n++)
                        {
                            Node nodeR = nlRels.item(n);
                            if(nodeR!=null && nodeR.getNodeType()==Node.ELEMENT_NODE)
                            {
                                attrs = nodeR.getAttributes();
                                String to = attrs.getNamedItem("to").getNodeValue();
                                String parent = attrs.getNamedItem("parent").getNodeValue();
                                SVGjs.append(" to = document.getElementById('"+to+"');").append("\n");
                                SVGjs.append(" parent = document.getElementById('"+parent+"');").append("\n");

                                SVGjs.append(" r = document.getElementById('w_"+parent+"');").append("\n");
                                SVGjs.append(" if(r) {").append("\n");
                                SVGjs.append("     w = r.width.baseVal.value;").append("\n");
                                SVGjs.append("     w = w/2;").append("\n");
                                SVGjs.append(" }else {").append("\n");
                                SVGjs.append("     w = 0;").append("\n");
                                SVGjs.append(" }").append("\n");

                                SVGjs.append(" if(to && parent) {").append("\n");
                                SVGjs.append("   console.log('to='+to+', parent='+parent);").append("\n");
                                SVGjs.append("   matxTo = parent.getCTM();").append("\n");
                                SVGjs.append("   posTo = svg.createSVGPoint();").append("\n");
                                SVGjs.append("   posTo.x = to.x.baseVal.value + w;").append("\n");
                                SVGjs.append("   posTo.y = to.y.baseVal.value;").append("\n");
                                SVGjs.append("   posTo = posTo.matrixTransform(matxTo);").append("\n");
                                SVGjs.append("   console.log('pos='+posTo+', x='+posTo.x+', y='+posTo.y);").append("\n");
                                SVGjs.append("   matxFrm = g.getCTM();").append("\n");
                                SVGjs.append("   posFrm = svg.createSVGPoint();").append("\n");
                                SVGjs.append("   posFrm.x = rect.x.baseVal.value;").append("\n");
                                SVGjs.append("   posFrm.y = rect.y.baseVal.value;").append("\n");
                                SVGjs.append("   posFrm = posFrm.matrixTransform(matxFrm);").append("\n");
                                SVGjs.append("   console.log('pos='+posFrm+', x='+posFrm.x+', y='+posFrm.y);").append("\n");
                                SVGjs.append("   path = createArrow(posFrm.x+'_'+posFrm.y+'_'+posTo.x+'_'+posTo.y,posFrm.x+"+(w_/2)+",posFrm.y,posTo.x,posTo.y);").append("\n");
                                SVGjs.append("   svg.appendChild(path);").append("\n");
                                SVGjs.append(" }").append("\n");
                            }
                        }

                        // lista de objetivos
                        if(!isHidden) {
                            SVGjs.append(" y_ = y__ + rect.height.baseVal.value + "+BOX_SPACING+";").append("\n"); 
                        }else {
                            SVGjs.append(" y_ = y__ + "+BOX_SPACING+";").append("\n");
                        }

                        expression = "//theme[@id='"+tid+"']/obj";
                        NodeList nlObjs = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                        for(int m=0; m<nlObjs.getLength(); m++)
                        {
                            Node nodeO = nlObjs.item(m);
                            if(nodeO!=null && nodeO.getNodeType()==Node.ELEMENT_NODE)
                            {
                                attrs = nodeO.getAttributes();
                                String oid = attrs.getNamedItem("id").getNodeValue();
                                String href = attrs.getNamedItem("href").getNodeValue();
                                w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                                x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                                txt = attrs.getNamedItem("status").getNodeValue();

                                info = new StringBuilder();
                                expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/prefix";
                                info.append(xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING));
                                info.append(" ");
                                expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/title";
                                info.append(xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING));
                                info.append(" ");
                                expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/sponsor";
                                info.append(xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING));
                                info.append(" ");
                                expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/frequency";
                                info.append(xPath.compile(expression).evaluate(documentBSC, XPathConstants.STRING));

                                // rectángulo objetivo
                                SVGjs.append(" lnk = createLink('"+href+"');").append("\n");
                                SVGjs.append(" g.appendChild(lnk);").append("\n");
                                SVGjs.append(" txt = createText('"+info+"',"+x_+",y_,"+HEADER_5+",'Verdana');").append("\n");
                                SVGjs.append(" lnk.appendChild(txt);").append("\n");
                                SVGjs.append(" fixParagraphToWidth(txt,"+w_+","+x_+");").append("\n");
                                SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                                SVGjs.append(" framingRect(rect,'"+oid+"',"+w_+",0,"+x_+",y_);").append("\n");
                                SVGjs.append(" g.insertBefore(rect,lnk);").append("\n");

                                SVGjs.append(" y_ = y_ + rect.height.baseVal.value + "+BOX_SPACING+";").append("\n");

                                //relaciones causa-efecto con este objetivo
                                expression = "//theme[@id='"+tid+"']/obj[@id='"+oid+"']/rel";
                                nlRels = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                                for(int n=0; n<nlRels.getLength(); n++)
                                {
                                    Node nodeR = nlRels.item(n);
                                    if(nodeR!=null && nodeR.getNodeType()==Node.ELEMENT_NODE)
                                    {
                                        attrs = nodeR.getAttributes();
                                        String to = attrs.getNamedItem("to").getNodeValue();
                                        String parent = attrs.getNamedItem("parent").getNodeValue();
                                        SVGjs.append(" to = document.getElementById('"+to+"');").append("\n");
                                        SVGjs.append(" parent = document.getElementById('"+parent+"');").append("\n");

                                        SVGjs.append(" r = document.getElementById('w_"+parent+"');").append("\n");
                                        SVGjs.append(" if(r) {").append("\n");
                                        SVGjs.append("     w = r.width.baseVal.value;").append("\n");
                                        SVGjs.append("     w = w/2;").append("\n");
                                        SVGjs.append(" }else {").append("\n");
                                        SVGjs.append("     w = 0;").append("\n");
                                        SVGjs.append(" }").append("\n");

                                        SVGjs.append(" if(to && parent) {").append("\n");
                                        SVGjs.append("   console.log('to='+to.id+', parent='+parent.id);").append("\n");
                                        SVGjs.append("   matxTo = parent.getCTM();").append("\n");
                                        SVGjs.append("   posTo = svg.createSVGPoint();").append("\n");
                                        SVGjs.append("   posTo.x = to.x.baseVal.value + w;").append("\n");
                                        SVGjs.append("   posTo.y = to.y.baseVal.value;").append("\n");
                                        SVGjs.append("   posTo = posTo.matrixTransform(matxTo);").append("\n");
                                        SVGjs.append("   console.log('pos='+posTo+', x='+posTo.x+', y='+posTo.y);").append("\n");
                                        SVGjs.append("   matxFrm = g.getCTM();").append("\n");
                                        SVGjs.append("   posFrm = svg.createSVGPoint();").append("\n");
                                        SVGjs.append("   posFrm.x = rect.x.baseVal.value;").append("\n");
                                        SVGjs.append("   posFrm.y = rect.y.baseVal.value;").append("\n");
                                        SVGjs.append("   posFrm = posFrm.matrixTransform(matxFrm);").append("\n");
                                        SVGjs.append("   console.log('pos='+posFrm+', x='+posFrm.x+', y='+posFrm.y);").append("\n");
                                        SVGjs.append("   path = createArrow(posFrm.x+'_'+posFrm.y+'_'+posTo.x+'_'+posTo.y,posFrm.x+"+(w_/2)+",posFrm.y,posTo.x,posTo.y);").append("\n");
                                        SVGjs.append("   svg.appendChild(path);").append("\n");
                                        SVGjs.append(" }").append("\n");
                                    }
                                }
                            } //objetivo
                        } //lista de objetivos 
                    } //tema
                } // lista de temas

                // caja de la perspectiva
                SVGjs.append(" rect = getBBoxAsRectElement(g);").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'id','"+pid+"_rct');").append("\n");
                SVGjs.append(" if(rect.height.baseVal.value<150) {").append("\n");
                SVGjs.append("   rect.height.baseVal.value = 150;").append("\n");
                SVGjs.append(" }").append("\n");
                SVGjs.append(" var h_ = Math.round(rect.height.baseVal.value);").append("\n");
                SVGjs.append(" rect.height.baseVal.value = h_;").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'fill','green');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'fill-opacity','0.3');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'stroke','red');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null, 'stroke-opacity','1');").append("\n");
                SVGjs.append(" g.insertBefore(rect,g.firstChild);").append("\n");
                // título de la perspectiva
                SVGjs.append(" txt = createText('"+title_+"',("+px+"+h_/2),h_,"+HEADER_3+",'Verdana');").append("\n");
                //SVGjs.append(" txt.setAttributeNS(null,'textLength',rect.height.baseVal.value);").append("\n");
                //SVGjs.append(" txt.setAttributeNS(null,'lengthAdjust','spacingAndGlyphs');").append("\n");
                SVGjs.append(" txt.setAttributeNS(null,'transform','rotate(270,"+px+",'+h_+')');").append("\n");
                SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
                SVGjs.append(" g.appendChild(txt);").append("\n");

                SVGjs.append(" y = y + h_ + "+MARGEN_BOTTOM+";").append("\n");
            } // perspectiva
        } // lista de perspectivas


        SVGjs.append("").append("\n");

        SVGjs.append("function createLink(url) {").append("\n");
        SVGjs.append("  var a = document.createElementNS(SVG_ ,'a');").append("\n");
        SVGjs.append("  a.setAttributeNS(XLINK_,'href',url);").append("\n");
        SVGjs.append("  a.setAttributeNS(null,'title','Ver detalle...');").append("\n");
        SVGjs.append("  return a;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function createArrow(id,x1,y1,x2,y2) {").append("\n");
        SVGjs.append("  var arrow = createPath(id,x1,y1,x2,y2);").append("\n");
        SVGjs.append("  arrow.setAttributeNS(null, 'marker-end', 'url(#arrow_1)');").append("\n");
        SVGjs.append("  return arrow;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function createPath(id,x1,y1,x2,y2) {").append("\n");
        SVGjs.append("  var path = document.createElementNS(SVG_,'path');").append("\n");
        SVGjs.append("  var d = 'M'+x1+','+y1'+' L'+(x1-5)+','+y1+' L'+(width-3)+','+y1+' L'+(width-3)+','+y2+' L'+x2+','+y2").append("\n");
        SVGjs.append("  path.setAttributeNS(null, 'd', 'M'+x1+','+y1+' L'+x2+','+y2);").append("\n");
        SVGjs.append("  path.style.stroke = '#000';").append("\n");
        SVGjs.append("  path.style.strokeWidth = '3px';").append("\n");
        //SVGjs.append("  path.style.markerEnd = '3px';").append("\n");
        SVGjs.append("  ").append("\n");
        SVGjs.append("  return path;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function createText(text,x,y,fontsize,fontfamily) {").append("\n");
        SVGjs.append("  var txt = document.createElementNS(SVG_,'text');").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'x',x);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'y',y);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'font-size',fontsize);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'font-family',fontfamily);").append("\n");
        SVGjs.append("  txt.textContent=text;").append("\n");
        SVGjs.append("  return txt;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function createRect(id,width,height,x,y,rx,ry,fill,fillopacity,stroke,strokewidth, strokeopacity) {").append("\n");
        SVGjs.append("  var rect = document.createElementNS(SVG_,'rect');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'id',id);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'width',width);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'height',height);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'x',x);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'y',y);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'rx',rx);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'ry',ry);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill',fill);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill-opacity',fillopacity);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'stroke',stroke);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-width',strokewidth);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-opacity',strokeopacity);").append("\n");
        SVGjs.append("  return rect;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function framingRect(rect,id,width, height, x, y) {").append("\n");
        SVGjs.append("    rect.setAttributeNS(null,'id',id);").append("\n");
        SVGjs.append("    rect.x.baseVal.value = x;").append("\n");
        SVGjs.append("    rect.y.baseVal.value = y;").append("\n");
        SVGjs.append("    rect.width.baseVal.value = width;").append("\n");
        SVGjs.append("    rect.height.baseVal.value = height;").append("\n");
        SVGjs.append("    rect.setAttributeNS(null,'fill','none');").append("\n");
        SVGjs.append("    rect.setAttributeNS(null,'stroke','black');").append("\n");
        SVGjs.append("    rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function fixParagraphAtBounding(text_element, width, height, x, y) {").append("\n");
        SVGjs.append("    var dy = getFontSize(text_element);").append("\n");
        SVGjs.append("    var text = text_element.textContent;").append("\n");
        SVGjs.append("    var words = text.split(' ');").append("\n");
        SVGjs.append("    var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("    var text_node = document.createTextNode(words[0]);").append("\n");

        SVGjs.append("    text_element.textContent='';").append("\n");
        SVGjs.append("    tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("    text_element.appendChild(tspan_element);").append("\n");

        SVGjs.append("    var h;").append("\n");

        SVGjs.append("    for(var i=1; i<words.length; i++)").append("\n");
        SVGjs.append("    {").append("\n");
        SVGjs.append("        h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("        var len = tspan_element.firstChild.data.length;").append("\n");
        SVGjs.append("        tspan_element.firstChild.data += ' ' + words[i];").append("\n");

        SVGjs.append("        if (tspan_element.getComputedTextLength() > width)").append("\n");
        SVGjs.append("        {").append("\n");
        SVGjs.append("            dy = dy - (h/height);").append("\n");
        SVGjs.append("            text_element.setAttributeNS(null, 'font-size', dy);").append("\n");
        SVGjs.append("            var childElements = text_element.getElementsByTagName('tspan');").append("\n");
        SVGjs.append("            for (var j=0; j<childElements.length; j++) {").append("\n");
        SVGjs.append("                if(childElements[j].getAttribute('dy')) {").append("\n");
        SVGjs.append("                    childElements[j].setAttributeNS(null,'dy',dy);").append("\n");
        SVGjs.append("                }").append("\n");
        SVGjs.append("            }").append("\n");
        SVGjs.append("            h = getBoundingHeight(text_element);").append("\n");

        SVGjs.append("            if (tspan_element.getComputedTextLength() > width)").append("\n");
        SVGjs.append("            {").append("\n");
        SVGjs.append("                tspan_element.firstChild.data = tspan_element.firstChild.data.slice(0, len);").append("\n");  // Remove added word

        SVGjs.append("                var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("                tspan_element.setAttributeNS(null, 'x', x);").append("\n");
        SVGjs.append("                tspan_element.setAttributeNS(null, 'dy', dy);").append("\n");
        SVGjs.append("                text_node = document.createTextNode(words[i]);").append("\n");
        SVGjs.append("                tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("                text_element.appendChild(tspan_element);").append("\n");
        SVGjs.append("            }").append("\n");
        SVGjs.append("        }").append("\n");
        SVGjs.append("    }").append("\n");

        SVGjs.append("    h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("    while(h>height && dy>0) {").append("\n");
        SVGjs.append("        dy--;").append("\n");
        SVGjs.append("        text_element.setAttributeNS(null, 'font-size', dy);").append("\n");

        SVGjs.append("        var childElements = text_element.getElementsByTagName('tspan');").append("\n");
        SVGjs.append("        for (var i=0; i < childElements.length; i++) {").append("\n");
        SVGjs.append("            if(childElements[i].getAttribute('dy')) {").append("\n");
        SVGjs.append("                childElements[i].setAttributeNS(null,'dy',dy-0.5);").append("\n");
        SVGjs.append("            }").append("\n");
        SVGjs.append("        }").append("\n");
        SVGjs.append("        h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("    }").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append(" function getFontSize(text_element) {").append("\n");
        SVGjs.append("  var fs_ = window.getComputedStyle(text_element, null).getPropertyValue('font-size');").append("\n");
        SVGjs.append("  var fs = fs_.replace( /\\D+/g, '');").append("\n");
        SVGjs.append("  return fs;").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append(" function getBoundingHeight(objNode) {").append("\n");
        SVGjs.append("  if(!objNode.getBBox) {").append("\n");
        SVGjs.append("      if(objNode.correspondingUseElement) {").append("\n");
        SVGjs.append("          objNode = objNode.correspondingUseElement;").append("\n");
        SVGjs.append("      }").append("\n");
        SVGjs.append("  }").append("\n");
        SVGjs.append("  var bbox = objNode.getBBox();").append("\n");
        SVGjs.append("  return bbox.height;").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append(" function getBBoxAsRectElement(objNode) {").append("\n");
        SVGjs.append("  if(!objNode.getBBox) {").append("\n");
        SVGjs.append("    if(objNode.correspondingUseElement)").append("\n");
        SVGjs.append("      objNode = objNode.correspondingUseElement;").append("\n");
        SVGjs.append("  }").append("\n");
        SVGjs.append("  var bbox = objNode.getBBox();").append("\n");
        SVGjs.append("  var rect = document.createElementNS(SVG_, 'rect');").append("\n");
        SVGjs.append("  rect.x.baseVal.value = bbox.x;").append("\n");
        SVGjs.append("  rect.y.baseVal.value = bbox.y;").append("\n");
        SVGjs.append("  rect.width.baseVal.value = bbox.width;").append("\n");
        SVGjs.append("  rect.height.baseVal.value = bbox.height;").append("\n");
        SVGjs.append("  return rect;").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append("</script>");
        return SVGjs.toString();
    }
    
    public Document getSvg_(Document documentBSC) throws XPathExpressionException
    {
        Resource base = getResourceBase();
        String w_, h_, x_, y_;
        String id, expression;
        int w, h, x, y;
        Element t, e, r, s, use;
        
        // XML del BSC
        Element rootBSC = documentBSC.getDocumentElement();
        w_ = rootBSC.getAttribute("width");        
        h_ = rootBSC.getAttribute("height");
        
DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
Document documentSVG = impl.createDocument(svgNS, "svg", null);        
        
        Element rootSVG = documentSVG.getDocumentElement();
        w_ = rootBSC.getAttribute("width");
        rootSVG.setAttributeNS(null, "width", w_);
        h_ = rootBSC.getAttribute("height");
        rootSVG.setAttributeNS(null, "height", h_);
        rootSVG.setAttributeNS(null, "viewBox", "0,0,"+w_+","+h_);
        
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        // <header>
        expression = "/bsc/header";
        Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        if(node!=null && node instanceof Element) {
            NamedNodeMap attrs = node.getAttributes();
            id = attrs.getNamedItem("id").getNodeValue();
            s = documentSVG.createElementNS(svgNS, "symbol");
            s.setAttributeNS(null, "id", id);
            rootSVG.appendChild(s);
            
            use = documentSVG.createElementNS(svgNS, "use");
            x = Integer.parseInt(attrs.getNamedItem("x").getNodeValue());
            y = Integer.parseInt(attrs.getNamedItem("y").getNodeName());
            w = Integer.parseInt(attrs.getNamedItem("width").getNodeName());
            h = Integer.parseInt(attrs.getNamedItem("height").getNodeValue());
            use.setAttributeNS(null, "x", Integer.toString(x));
            use.setAttributeNS(null, "y", Integer.toString(y));
            use.setAttributeNS(null, "width", Integer.toString(w));
            use.setAttributeNS(null, "height", Integer.toString(h));
            use.setAttributeNS(null, "xlink:href", attrs.getNamedItem("id").getNodeValue());
            
            NodeList nl = node.getChildNodes();
            if(nl.getLength()>0)
            {
                w = w/nl.getLength();
                h = Integer.parseInt(attrs.getNamedItem("height").getNodeValue());
                
                // <title>
                expression = "/bsc/header/title";
                Node n = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
                if(n!=null && node instanceof Element) {
                    Text title = documentSVG.createTextNode(n.getNodeValue());
                    t = documentSVG.createElementNS(svgNS, "text");
                    t.setAttributeNS(null, "x", Integer.toString(x));
                    t.setAttributeNS(null, "y", Integer.toString(y+12));
                    t.setAttributeNS(null, "font-size", "12");
                    t.appendChild(title);
                    s.appendChild(t);                 
                }
                
                // <mission>
                expression = "/bsc/header/mission";
                n = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
                if(n!=null && node instanceof Element) {
                    r = documentSVG.createElementNS(svgNS, "rect");
                    r.setAttributeNS(null, "x", Integer.toString(x+PADDING_LEFT));
                    r.setAttributeNS(null, "y", Integer.toString(y+PADDING_TOP));
                    r.setAttributeNS(null, "width", Integer.toString(w-BOX_SPACING));
                    r.setAttributeNS(null, "height", Integer.toString(h));
                    r.setAttributeNS(null, "style", "fill:none;stroke:black;stroke-width:1");
                    s.appendChild(r);
                }
                
                // <logo>
                x+=w;
                expression = "/bsc/header/logo";
                n = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
                if(n!=null && node instanceof Element) {
                    r = documentSVG.createElementNS(svgNS, "rect");
                    r.setAttributeNS(null, "x", Integer.toString(x+PADDING_LEFT));
                    r.setAttributeNS(null, "y", Integer.toString(y+PADDING_TOP));
                    r.setAttributeNS(null, "width", Integer.toString(w-BOX_SPACING));
                    r.setAttributeNS(null, "height", Integer.toString(h));
                    r.setAttributeNS(null, "style", "fill:none;stroke:black;stroke-width:1");
                    s.appendChild(r);
                }
                
                // <vision>
                x+=w;
                expression = "/bsc/header/vision";
                n = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
                if(n!=null && node instanceof Element) {
                    r = documentSVG.createElementNS(svgNS, "rect");
                    r.setAttributeNS(null, "x", Integer.toString(x+PADDING_LEFT));
                    r.setAttributeNS(null, "y", Integer.toString(y+PADDING_TOP));
                    r.setAttributeNS(null, "width", Integer.toString(w-BOX_SPACING));
                    r.setAttributeNS(null, "height", Integer.toString(h));
                    r.setAttributeNS(null, "style", "fill:none;stroke:black;stroke-width:1");
                    s.appendChild(r);
                }
            }
            rootSVG.appendChild(use);
        }
        
        
        
        return documentSVG;
    }
    
    private int assertValue(final String textVal)
    {
        int val;
        try {
            val = Integer.parseInt(textVal);
        }catch(NumberFormatException nfe) {
            val = 0;
        }catch(NullPointerException nulle) {
            val = 0;
        }
        return val;
    }
    
    private int vPadding(int value) {
        return value+PADDING_TOP+PADDING_DOWN;
    }
    
    private int topPadding(int value) {
        return value+PADDING_TOP;
    }
    
    private int bottomPadding(int value) {
        return value+PADDING_TOP;
    }
    
    private int hPadding(int value) {
        return value+PADDING_LEFT+PADDING_RIGHT;
    }
}
