<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    Member member = Member.getMember(user, wpage);

    String uri = request.getParameter("uri");
    EventElement event = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
    if(event!=null && event.canView(member)) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");        
        
        event.incViews();
%>
<table border="0" cellspacing="10">
    <tr>
        <td valign="top">
            <img src="<%=event.getEventImage()%>" alt="<%=event.getDescription()%>" border="0" />
        </td>
        <td valign="top">
                <%=event.getTitle()%> (<%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%>)<BR>
                <hr>
                <p><%=event.getDescription()%></p>
                <p>Dirigido a: <%= event.getAudienceType()%></p>
                <p>Inicia: <b><%= dateFormat.format(event.getStartDate())%></b> a las <b><%=timeFormat.format(event.getStartTime())%></b></p>
                <p>Termina: <b><%= dateFormat.format(event.getEndDate())%></b> a las <b><%=timeFormat.format(event.getEndTime())%></b></p>
                <p>Lugar: <%= event.getPlace()%></p>
                <p>Asistentes:
                <%
                Iterator<User> users = event.listAttendants();
                while (users.hasNext()) {
                    User m = users.next();
                    %>
                    <%=m.getFullName()%>
                    <%if(users.hasNext()) {
                    %>,&nbsp;<%
                    }                
                }
                %></p>
                <p><%=event.getViews()%> vistas</p>
        </td>
    </tr>
</table>
<script type="text/javascript">
    var img = document.getElementById('img_<%=event.getId()%>');
    if( img.width>img.height && img.width>450) {
        img.width = 450;
        img.height = 370;
    }else {
        if(img.height>370) {
            img.width = 370;
            img.height = 450;
        }
    }
</script>
<%
            }
%>
<%
event.renderGenericElements(request, out, paramRequest);
SWBResourceURL back = paramRequest.getRenderUrl().setParameter("act", "daily");
back.setParameter("year", request.getParameter("year"));
back.setParameter("month", request.getParameter("month"));
back.setParameter("day", request.getParameter("day"));
%>

<center>
    <a href="<%=back%>">Regresar</a>
    <%
            back = paramRequest.getRenderUrl().setParameter("act", "edit");
            back.setParameter("year", request.getParameter("year"));
            back.setParameter("month", request.getParameter("month"));
            back.setParameter("day", request.getParameter("day"));
            back.setParameter("uri", event.getURI());
    %>
    <%if (event.canModify(member)) {%><a href="<%=paramRequest.getActionUrl().setParameter("act", "attend").setParameter("uri", event.getURI())%>">Asistir al evento</a><%}%>
    <%if (event.canModify(member)) {%><a href="<%=back%>">Editar Información</a><%}%>
    <%if (event.canModify(member)) {%><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", event.getURI())%>">Eliminar Evento</a><%}%>
</center>