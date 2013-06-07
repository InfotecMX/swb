<%-- 
    Document   : view
    Created on : 3/06/2013, 12:34:14 PM
    Author     : Sabino
--%>

<%@page import="java.util.Set"%>
<%@page import="com.infotec.lodp.swb.resources.UseAppBean"%>
<%@page import="com.infotec.lodp.swb.resources.UsersSatBean"%>
<%@page import="com.infotec.lodp.swb.resources.UseDSBean"%>
<%@page import="com.infotec.lodp.swb.resources.AplByDSBean"%>
<%@page import="com.infotec.lodp.swb.resources.DSByIntBean"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="java.lang.reflect.Field"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="com.infotec.lodp.swb.Comment"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.infotec.lodp.swb.resources.StatisticsResource"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    String statistic = request.getParameter("statistic");
    String urlSite = wpage.getUrl();
    String urlstatistic = urlSite+"?statistic=";
%>
<%  if(statistic==null){%>
        <h1>Estadísticas</h1>
        <span><a href="<%=urlstatistic+"1"%>">Dataset por institución</a></span> <br>
        <span><a href="<%=urlstatistic+"2"%>">Dataset por aplicación</a></span> <br>
        <span><a href="<%=urlstatistic+"3"%>">Uso de datasets</a></span> <br>
        <span><a href="<%=urlstatistic+"4"%>">Uso de aplicaciones</a></span> <br>
        <span><a href="<%=urlstatistic+"5"%>">Satisfación de usuarios</a></span>
<%  }else{
        List columns = StatisticsResource.getNameOfColumns(statistic, paramRequest);        
%>
        <h1><%=columns.get(0)%></h1>
        <table border>
            <tr>
        <%for(int i=1; i<columns.size(); i++){%>                
                <th>
                    <%=columns.get(i)%>
                    <%String urlstatistic2 = urlstatistic+statistic+"&column="+i+"&asc=";%>
                    <a href="<%=urlstatistic2+"true"%>">asc</a>
                    <a href="<%=urlstatistic2+"false"%>">des</a>
                </th>
        <%}%> 
            </tr>
        <% 
        long count = 0;        
        String column = request.getParameter("column");        
        String asc = request.getParameter("asc");        
        if(statistic.trim().equals("1")){
            if(asc==null){asc="true";}
            if(column==null){column="1";}            
            Set<DSByIntBean> list = StatisticsResource.getDSByIntBean(wsite,column,asc);            
            for(DSByIntBean dsbyint: list){ 
                count += dsbyint.getNumDS();
        %>
            <tr>
                <td><%=dsbyint.getInstitution()%></td>
                <td><%=dsbyint.getNumDS()%></td>
            </tr>
        <%  }%>
            </table> 
            <span>Total de datasets: <%=count%></span>
         <%
         }
        if(statistic.trim().equals("2")){
            if(asc==null){asc="true";}
            if(column==null){column="1";}
            Set<AplByDSBean> list = StatisticsResource.getAplByDS(wsite,column,asc);            
            for(AplByDSBean aplbyds : list){ 
                count+= aplbyds.getNumApp();
        %>    
            <tr>
                <td><%=aplbyds.getDataset()%></td>
                <td><%=aplbyds.getInstitution()%></td>
                <td><%=aplbyds.getNumApp()%></td>
            </tr>
        <% }%>
            </table>
            <span>Total de aplicaciones: <%=count%></span>
         <%
         }
        if(statistic.trim().equals("3")){
            if(asc==null){asc="true";}
            if(column==null){column="2";}
            Set<UseDSBean> list = StatisticsResource.getDSUse(wsite,column,asc);            
            for(UseDSBean dataset : list){
                count++;
        %> 
            <tr>
                <td><%=dataset.getTotalHits()%></td>
                <td><%=dataset.getInstitution()%></td>
                <td><%=dataset.getDataset()%></td>
                <td><%=dataset.getHits()%></td>
                <td><%=StatisticsResource.formatFecha(dataset.getLastDownload())%></td>
                <td><%=dataset.getViews()%></td>
                <td><%=StatisticsResource.formatFecha(dataset.getLastView())%></td>
            </tr>
        <%  }%>
            </table> 
            <span>Total de datasets: <%=count%></span>
         <%}
         if(statistic.trim().equals("4")){
            if(asc==null){asc="true";}
            if(column==null){column="2";}
            Set<UseAppBean> list = StatisticsResource.getAppUse(wsite,column,asc);            
            for(UseAppBean appl : list){
                count++;
        %>
            <tr>                
                <td><%=appl.getTotalHits()%></td>
                <td><%=appl.getInstitution()%></td>
                <td><%=appl.getApplication()%></td>
                <td><%=appl.getHits()%></td>
                <td><%=StatisticsResource.formatFecha(appl.getLastDownload())%></td>
                <td><%=appl.getViews()%></td>
                <td><%=StatisticsResource.formatFecha(appl.getLastView())%></td>
            </tr>            
         <%  }%>
            </table> 
            <span>Total de datasets: <%=count%></span>
         <%}
         if(statistic.trim().equals("5")){
            if(asc==null){asc="true";}
            if(column==null){column="1";}            
            Set<UsersSatBean> list = StatisticsResource.getUsersSat(wsite,column,asc);             
            for(UsersSatBean userssat : list){
                count++;
        %>   
            <tr>                
                <td><%=userssat.getInstitution()%></td>
                <td><%=userssat.getDataset()%></td>
                <td><%=userssat.getAverage()%></td>                   
                <td><%=userssat.getNumComments()%></td>                
            </tr>           
         <%  }%>
            </table> 
            <span>Total de datasets: <%=count%></span>
         <%}%>
       
        <%
         SWBResourceURL urlCSV = paramRequest.getRenderUrl();
         urlCSV.setCallMethod(SWBResourceURL.Call_DIRECT);
         urlCSV.setParameter("statistic",statistic);
         urlCSV.setParameter("column",column);
         urlCSV.setParameter("asc",asc);
         urlCSV.setMode(StatisticsResource.MODE_FILE);         
        %>
        
         <a href="<%=urlCSV.toString()%>">Descargar CSV</a>
<%}%>
