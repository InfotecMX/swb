package org.semanticwb.portal.admin.resources.reports;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;
import org.semanticwb.portal.admin.resources.reports.jrresources.*;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRLoggedUniqueDataDetail;

public class WBALoginUniqueReport extends GenericResource{
    private static Logger log = SWBUtils.getLogger(WBALoginUniqueReport.class);

    private static final String S_REPORT_IDAUX = "_";
    private static final ArrayList idaux = new ArrayList(1);
    private static final int I_REPORT_TYPE = 7;
    
    public String strRscType;
    
    static {
        idaux.add(S_REPORT_IDAUX);            
    }

    @Override
    public void init(){
        Portlet base = getResourceBase();        
        try {
            strRscType = base.getPortletType().getPortletClassName();
        }catch (Exception e) {
            strRscType = "WBALoginUniqueReport";
        }
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState())) {
            processRequest(request, response, paramsRequest);
        }
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        if(paramsRequest.getMode().equalsIgnoreCase("graph")) {
            doGraph(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_excel")) {
            doRepExcel(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_xml")) {
            doRepXml(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_pdf")) {
            doRepPdf(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_rtf")) {
            doRepRtf(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache"); 
        PrintWriter out = response.getWriter();
        Portlet base = getResourceBase();
        
        final int I_ACCESS = 0;        
        //StringBuffer sb_ret = new StringBuffer();        
        HashMap hm_repository = new HashMap();
        int i_access = 0;
        String rtype;

        try{
            // Evaluates if there are repositories
            Iterator<UserRepository> it_repository = SWBContext.listUserRepositorys();
            while(it_repository.hasNext()) {
                UserRepository ur_repository = it_repository.next();
                // Get access level of this user on this topicmap and if level is greater than "0" then user have access
                // TODO
//                i_access = AdmFilterMgr.getInstance().haveAccess2UserRep(paramsRequest.getUser(),ur_repository.getName());
//                if(I_ACCESS < i_access){
                    hm_repository.put(ur_repository.getId(), ur_repository.getSemanticObject().getDisplayName(paramsRequest.getUser().getLanguage()));
//                }}
            }
            
            // If there are repositories it continues
            if(hm_repository.size() > I_ACCESS){
                String address = paramsRequest.getTopic().getUrl();
                String repositoryName = request.getParameter("wb_repository");
                int groupDates;
                try {
                    groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
                }catch(NumberFormatException e) {
                    groupDates = 0;
                }
                String fecha1 = request.getParameter("wb_fecha1")==null ? "":request.getParameter("wb_fecha1");
                String fecha11 = request.getParameter("wb_fecha11")==null ? "":request.getParameter("wb_fecha11"); 
                String fecha12 = request.getParameter("wb_fecha12")==null ? "":request.getParameter("wb_fecha12");
                
                String topicId = paramsRequest.getTopic().getId();
                if(topicId.lastIndexOf("Daily") != -1) {
                    rtype = "0";
                }else if(topicId.lastIndexOf("Monthly") != -1) {
                    rtype = "1";
                }else {
                    rtype = request.getParameter("wb_rtype");
                }
                if(rtype == null) {
                    rtype = "0";
                }

                // javascript
                out.println("\n<script type=\"text/javascript\">");
                
                out.println("dojo.require(\"dijit.form.DateTextBox\");");
                out.println("dojo.addOnLoad(doBlockade);");
                
                out.println("function getParams(accion) {");
                out.println("   var params = '?';");
                out.println("   params = params + 'wb_repository=' + dojo.byId('wb_repository').value;");
                out.println("   params = params + '&wb_rtype=' + dojo.byId('wb_rtype').value;");
                out.println("   if(accion == 0) {");
                out.println("       params = params + '&wb_rep_type=' + getTypeSelected();");
                out.println("       params = params + '&wb_fecha1=' + dojo.byId('wb_fecha1').value;");
                out.println("       params = params + '&wb_fecha11=' + dojo.byId('wb_fecha11').value;");
                out.println("       params = params + '&wb_fecha12=' + dojo.byId('wb_fecha12').value;");
                out.println("   }else {");
                out.println("       params = params + '&wb_year13=' + dojo.byId('wb_year13').options[dojo.byId('wb_year13').selectedIndex].value;");
                out.println("   }");
                out.println("   return params;");
                out.println("}");
                
                out.println("\nfunction doXml(accion, size) { ");
                out.println("\n   var params = getParams(accion);");
                out.println("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml")+"\"+params,\"graphWindow\",size);    ");
                out.println("\n}");
                
                out.println("\nfunction doExcel(accion, size) { ");
                out.println("\n   var params = getParams(accion);");
                out.println("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_excel")+"\"+params,\"graphWindow\",size);    ");
                out.println("\n}");
                
                out.println("\nfunction doGraph(accion, size) { ");
                out.println("\n   var params = getParams(accion);");
                out.println("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("graph")+"\"+params,\"graphWindow\",size);    ");
                out.println("\n }");
                
                out.println("\nfunction doPdf(accion, size) { ");
                out.println("\n   var params = getParams(accion);");
                out.println("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_pdf")+"\"+params,\"graphWindow\",size);    ");
                out.println("\n}");
                
                out.println("\nfunction doRtf(accion, size) { ");
                out.println("\n   var params = getParams(accion);");
                out.println("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_rtf")+"\"+params,\"graphWindow\",size);    ");
                out.println("\n}");
                
                out.println("\n function getTypeSelected(){");
                out.println("\n     var strType = \"0\";");
                out.println("\n     for(i=0;i<window.document.frmrep.wb_rep_type.length;i++){");
                out.println("\n       if(window.document.frmrep.wb_rep_type[i].checked==true){");
                out.println("\n           strType=window.document.frmrep.wb_rep_type[i].value;");
                out.println("\n       }");
                out.println("\n     }");
                out.println("\n     return strType;");
                out.println("\n }");
                
                out.println("\n function doApply() { ");
                out.println("\n     window.document.frmrep.submit(); ");
                out.println("\n }");                

                out.println(" function doBlockade() {");
                out.println("     if(window.document.frmrep.wb_rep_type[0].checked){");
                out.println("       dojo.byId('wb_fecha1').disabled = false;");
                out.println("       dojo.byId('wb_fecha11').disabled = true;");
                out.println("       dojo.byId('wb_fecha12').disabled = true;");                
                out.println("     }");
                out.println("     if(window.document.frmrep.wb_rep_type[1].checked){");
                out.println("       dojo.byId('wb_fecha1').disabled = true;");
                out.println("       dojo.byId('wb_fecha11').disabled = false;");
                out.println("       dojo.byId('wb_fecha12').disabled = false;");
                out.println("     }");
                out.println(" }");
                out.println("\n</script>");
                
                out.println("\n<div id=\"swb-admin\">");
                out.println("\n<fieldset>");
                out.println("\n<legend>" + paramsRequest.getLocaleString("login_report") + "</legend>");
                
                out.println("\n<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
                out.println("\n<table border=\"0\" width=\"95%\" align=\"center\">");
                out.println("\n<tr><td width=\"100\"></td><td width=\"120\"></td><td></td><td></td></tr>");
                out.println("\n<tr>");
                out.println("\n<td colspan=4>");
                if(rtype.equals("0")){
                    out.println(paramsRequest.getLocaleString("description_daily"));
                }
                else{
                    out.println(paramsRequest.getLocaleString("description_monthly"));
                }
                out.println("\n</td>");
                out.println("\n</tr>");
                
                out.println("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");                
                out.println("\n<tr>");
                out.println("\n <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doXml('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\" />&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doExcel('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\" name=\"btnExcel\" />&nbsp;");                
                out.println("   <input type=\"button\" onClick=\"doPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"PDF\" name=\"btnPdf\" />&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"RTF\" name=\"btnRtf\" />&nbsp;");                
                out.println("   <input type=\"button\" onClick=\"doGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\" value=\"" + paramsRequest.getLocaleString("graph") + "\" name=\"btnGraph\" />&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\" />");
                out.println("\n </td>");
                out.println("\n</tr>");                
                out.println("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                
                out.println("\n<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("repository") + ":</td>");
                out.println("<td colspan=\"2\"><select id=\"wb_repository\" name=\"wb_repository\">");
                Iterator<String> itKeys = hm_repository.keySet().iterator();
                while(itKeys.hasNext()) {
                    String key = itKeys.next();
                    out.println("\n<option value=\""+ key + "\"");
                        if(key.equalsIgnoreCase(repositoryName)) {
                            out.println(" selected=\"selected\"");
                        }
                    out.println(">" + (String)hm_repository.get(key) + "</option>");
                }                
                out.println("</select>");                
                out.println("</td>");
                out.println("\n<td>&nbsp;</td>");
                out.println("\n</tr>");

                if(rtype.equals("0")) { // REPORTE DIARIO
                    out.println("\n<tr>");
                    out.println("\n<td>");
                    out.println("<label>");
                    out.println("<input type=\"radio\" value=\"0\" name=\"wb_rep_type\" id=\"wb_rep_type_0\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates==0) {
                        out.println(" checked=\"checked\"");
                    }
                    out.println(" />");
                    out.println("&nbsp;" + paramsRequest.getLocaleString("by_day"));
                    out.println("</label></td>");
                    out.println("\n<td colspan=\"2\">");
                    out.println("<input type=\"text\" name=\"wb_fecha1\" id=\"wb_fecha1\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha1+"\">");
                    out.println("</td>");
                    out.println("<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"0\" /></td>");
                    out.println("\n</tr>");
                    out.println("\n<tr>");
                    out.println("\n<td colspan=4>&nbsp;</td>");
                    out.println("\n</tr>");

                    out.println("\n<tr>");
                    out.println("\n<td>");
                    out.println("\n<label>");
                    out.println("\n<input type=\"radio\" value=\"1\" name=\"wb_rep_type\" id=\"wb_rep_type_1\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates!=0) {
                        out.println(" checked=\"checked\"");
                    }
                    out.println(" />");
                    out.println("&nbsp;" + paramsRequest.getLocaleString("by_interval_dates"));
                    out.println("</label></td>");
                    out.println("\n<td>");
                    out.println("<input type=\"text\" name=\"wb_fecha11\" id=\"wb_fecha11\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha11+"\">");
                    out.println("</td>");
                    out.println("\n<td>");
                    out.println("<input type=\"text\" name=\"wb_fecha12\" id=\"wb_fecha12\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha12+"\">");
                    out.println("</td>");
                    out.println("\n<td>&nbsp;</td>");
                    out.println("\n</tr>");
                    
                    out.println("\n<tr>");
                    out.println("<td colspan=\"4\" align=\"left\">");
                    if(request.getParameter("wb_rtype")==null || repositoryName==null ) {
                        out.println("&nbsp;");
                    }else {
                        out.println("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">");                            
                        out.println("\n<tr>");
                        out.println("\n<td>");
                        /*response.getWriter().print(sb_ret.toString());
                        sb_ret.delete(0,sb_ret.length());*/
                        WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                        JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_DAILY_HTML;
                        HashMap params = new HashMap();
                        params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
                        params.put("site", filter.getSite());
                        try {
                            JRResource jrResource = new JRHtmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                        out.println("\n</td>");
                        out.println("\n</tr>");
                        out.println("\n</table>");
                        out.println("<hr size=\"1\" noshade>");                      
                    }
                    out.println("\n</td>");
                    out.println("\n</tr>");
                }else { // REPORTE MENSUAL
                    GregorianCalendar gc_now = new GregorianCalendar();
                    int year13 = request.getParameter("wb_year13")==null ? gc_now.get(Calendar.YEAR):Integer.parseInt(request.getParameter("wb_year13"));
                    out.println("\n<tr>");
                    out.println("\n<td>" + paramsRequest.getLocaleString("year") + ":</td>");                    
                    out.println("\n<td colspan=\"2\"><select id=\"wb_year13\" name=\"wb_year13\">");
                    for (int i = 2000; i < 2021; i++) {
                        out.println("<option value=\"" + i + "\"");
                        if (year13==i) {
                            out.println(" selected=\"selected\"");
                        }
                        out.println(">" + i + "</option>");
                    }
                    out.println("\n</select>");
                    out.println("\n</td>");                    
                    out.println("\n<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"1\" /></td>");                        
                    out.println("\n</tr>");
                    
                    out.println("\n<tr>");
                    out.println("<td colspan=\"4\">");
                    if(request.getParameter("wb_rtype")==null || repositoryName==null ) {
                        out.println("&nbsp;");
                    }else {
                        out.println("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">");                         
                        out.println("\n<tr><td>\n");

                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(repositoryName);
                        filter.setIdaux(idaux.iterator());
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(year13);                            
                        JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_MONTHLY_HTML;
                        HashMap params = new HashMap();
                        params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
                        params.put("site", filter.getSite());
                        try {
                            JRResource jrResource = new JRHtmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);                            
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                        out.println("\n</td></tr>");                            
                        out.println("\n</table>");
                        out.println("<hr size=\"1\" noshade>");
                    }
                    out.println("\n</td>");
                    out.println("\n</tr>");
                }
                out.println("\n</table></form>");
                out.println("\n</fieldset></div>");
            }
            else {   // There are not sites and displays a message
                out.println("\n<form method=\"Post\" action=\"" + paramsRequest.getTopic().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
                out.println("\n<table border=0 width=\"100%\">");
                out.println("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("\n<tr>");
                out.println("\n<td>&nbsp;</td>");
                out.println("\n<td colspan=\"2\" align=\"center\">" + paramsRequest.getLocaleString("no_repositories_found") + "</td>");
                out.println("\n<td>&nbsp;</td>");
                out.println("\n</tr>");
                out.println("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("\n</table></form>");
            }
        }catch (Exception e) {
            log.error("Error on method DoView() resource " + strRscType + " with id " + base.getId(), e);            
        }
        out.flush();
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");
        Portlet base = getResourceBase();        
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_DAILY_GRAPH;                
                try {                    
                    JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String repositoryName = request.getParameter("wb_repository")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", repositoryName);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(repositoryName);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_MONTHLY_GRAPH;                        
                try {
                    JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }
        }
        catch (Exception e){
            log.error("Error on method doGraph() resource " + strRscType + " with id " + base.getId(), e);
        }
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doRepExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=\"gar.xls\"");
        Portlet base = getResourceBase();        
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_DAILY;                
                try {
                    JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String repositoryName = request.getParameter("wb_repository")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", repositoryName);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(repositoryName);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_MONTHLY;                        
                try {
                    JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }
        }
        catch (Exception e){
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), e);
        }
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doRepXml(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/xml;charset=iso-8859-1");
        Portlet base = getResourceBase();        
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_DAILY;                
                try {
                    JRResource jrResource = new JRXmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String repositoryName = request.getParameter("wb_repository")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", repositoryName);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(repositoryName);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_MONTHLY;                        
                try {
                    JRResource jrResource = new JRXmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }
        }
        catch (Exception e){            
            log.error("Error on method doRepXml() resource" + " " + strRscType + " " + "with id" + " " + base.getId(), e);
        }
    }

    public void doRepPdf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("application/pdf");
        Portlet base = getResourceBase();        
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_DAILY;                
                try {
                    JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String repositoryName = request.getParameter("wb_repository")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", repositoryName);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(repositoryName);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_MONTHLY;                        
                try {
                    JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }
        }
        catch (Exception e){
            log.error("Error on method doRepPdf() resource " + strRscType + " with id " + base.getId(), e);
        }
    }
    
    public void doRepRtf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("application/rtf");
        response.setHeader("Content-Disposition", "inline; filename=\"gar.rtf\"");
        Portlet base = getResourceBase();
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_DAILY;                
                try {
                    JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String repositoryName = request.getParameter("wb_repository")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", repositoryName);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(repositoryName);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                JRDataSourceable dataDetail = new JRLoggedUniqueDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LOGGED_UNIQUE_MONTHLY;                        
                try {
                    JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }
        }
        catch (Exception e){
            log.error("Error on method doRepRtf() resource " + strRscType + " with id " + base.getId(), e);
        }
    }
    
    /**
     * @param request
     * @param paramsRequest
     * @return
     */
    private WBAFilterReportBean buildFilter(HttpServletRequest request, SWBParamRequest paramsRequest) throws SWBResourceException, IncompleteFilterException {
        WBAFilterReportBean filterReportBean = null;        
        String repositoryName = request.getParameter("wb_repository")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
        int groupDates;
        try {
            groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
        }catch(NumberFormatException e) {
            groupDates = 0;
        }
        String fecha1 = request.getParameter("wb_fecha1");
        String fecha11 = request.getParameter("wb_fecha11");
        String fecha12 = request.getParameter("wb_fecha12");        
        if(groupDates==0 && fecha1==null) {
            throw new IncompleteFilterException("Falta la fecha");
        }
        if(groupDates==1 && (fecha11==null || fecha12==null)) {
            throw new IncompleteFilterException("Faltan las fechas");
        }

        try {
            if(groupDates==0) { // radio button was 0. Select only one date
                String[] numFecha = fecha1.split("-");
                filterReportBean = new WBAFilterReportBean();
                filterReportBean.setSite(repositoryName);
                filterReportBean.setIdaux(idaux.iterator());
                filterReportBean.setType(I_REPORT_TYPE);
                filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayI(Integer.parseInt(numFecha[2]));
                
            }else { // radio button was 1. Select between two dates
                filterReportBean = new WBAFilterReportBean();
                filterReportBean.setSite(repositoryName);
                filterReportBean.setIdaux(idaux.iterator());
                filterReportBean.setType(I_REPORT_TYPE);
                String[] numFecha = fecha11.split("-");
                filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayI(Integer.parseInt(numFecha[2]));

                numFecha = fecha12.split("-");
                filterReportBean.setYearF(Integer.parseInt(numFecha[0]));
                filterReportBean.setMonthF(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayF(Integer.parseInt(numFecha[2]));
            }
        }
        catch (Exception e){
            log.error("Error on method buildFilter() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }
        return filterReportBean;
    }
}

