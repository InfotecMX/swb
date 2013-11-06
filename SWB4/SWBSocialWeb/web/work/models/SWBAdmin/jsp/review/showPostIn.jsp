<%-- 
    Document   : showPostIn
    Created on : 03-jun-2013, 13:01:48
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    org.semanticwb.model.User user = paramRequest.getUser();
    System.out.println("postInJsp:" + request.getAttribute("postIn"));
    if (request.getAttribute("postIn") == null) {
        return;
    }

    SemanticObject semObj = (SemanticObject) request.getAttribute("postIn");
    if (semObj == null) {
        return;
    }

    WebSite wsite = WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if (wsite == null) {
        return;
    }

    PostIn postIn = (PostIn) semObj.getGenericInstance();
    //Un mensaje de entrada siempre debe estar atachado a un usuario de la red social de la que proviene, de esta manera, es como desde swbsocial
    //se responderķa a un mensaje
    if (postIn.getPostInSocialNetworkUser() == null) {
        return;
    }

    SocialNetworkUser socialNetworkUser = postIn.getPostInSocialNetworkUser();
    String userPhoto = socialNetworkUser.getSnu_photoUrl(); //Sacar la foto de la redSocial;
    if(userPhoto == null || userPhoto.isEmpty()){
        userPhoto = "/swbadmin/css/images/profileDefImg.jpg";
    }    
%>
             

<div class="swbform swbpopup usr-pop">
    <div class="perfilgral">
        <div class="perfil">           
            <img src="<%=userPhoto%>"/>
            <p><%=socialNetworkUser.getSnu_name()%></p>
        </div>
        <p><strong><%=socialNetworkUser.getFollowers()%></strong> <%=SWBSocialUtil.Util.getStringFromGenericLocale("followers", user.getLanguage())%></p>
        <p><strong><%=socialNetworkUser.getFriends()%></strong> <%=SWBSocialUtil.Util.getStringFromGenericLocale("friends", user.getLanguage())%></p>
        <p><strong><%=socialNetworkUser.getSnu_klout()%></strong> Klout</p>

        <div class="clear"></div>
    </div>
    <jsp:include page="/work/models/SWBAdmin/jsp/post/postActions.jsp">
        <jsp:param name="postUri" value="<%=postIn.getURI()%>" />
    </jsp:include>
    <table class="tabla1">
        <thead>
            <tr>               
                <th>
                    <span><%=paramRequest.getLocaleString("post")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("sentiment")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("intensity")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("replies")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("place")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("prioritary")%></span>
                </th>
            </tr>
        </thead>


        <tbody>
            <tr>

                <%
                    if (semObj.getGenericInstance() instanceof MessageIn) {
                        MessageIn message = (MessageIn) semObj.getGenericInstance();
                %>
                <td><span><%=SWBUtils.TEXT.encode(message.getMsg_Text(), "utf8")%></span>
                </td>
                <%
                } else if (semObj.getGenericInstance() instanceof PhotoIn) {
                    PhotoIn photo = (PhotoIn) semObj.getGenericInstance();
                    //System.out.println("Name:"+Photo.social_Photo.getName()); 
                    //System.out.println("ClassID:"+Photo.social_Photo.getClassId()); 
                    //System.out.println("Canonical:"+Photo.social_Photo.getCanonicalName());
                    //Puse ese tolowercase porque el nombre de la propiedad lo pone en mayuscula, quien sabe porque, si esta en minuscula 
%>
                <td>
                    <%
                        Iterator<String> itPhotos = photo.listPhotos();
                        while (itPhotos.hasNext()) {
                            String sphoto = itPhotos.next();
                    %>   

                    <img src="<%=sphoto%>">
                    <%
                        }
                    %>     
                    <%=SWBUtils.TEXT.encode(photo.getMsg_Text(), "utf8")%>
                </td>
                <%
                } else if (semObj.getGenericInstance() instanceof VideoIn) {
                    VideoIn video = (VideoIn) semObj.getGenericInstance();
                    String videoFormat = "";
                    String videoUrl = video.getVideo();
                    String fileext = null;
                    if (videoUrl != null) {
                        int pos = videoUrl.lastIndexOf(".");
                        if (pos > -1) {
                            fileext = videoUrl.substring(pos);
                            int pos1 = fileext.indexOf("?");
                            if (pos1 > -1) {
                                fileext = fileext.substring(0, pos1);
                            }
                        }
                        if (videoUrl.toLowerCase().contains("www.youtube.com")) {//show player from youtube
                            videoFormat = "youtube";
                        } else if (fileext.toLowerCase().equals(".mp4")) {
                            videoFormat = "mp4";
                        } else if (fileext.toLowerCase().equals(".swf") || fileext.toLowerCase().equals(".mov")) {
                            videoFormat = "flash";
                        } else if (fileext.toLowerCase().equals(".flv")) {
                            videoFormat = "flv";
                        } else if (fileext.toLowerCase().equals(".wav")) {
                            videoFormat = "wav";
                        } else if (fileext.equals(".wmv")) {
                            videoFormat = "wmv";
                        }
                    } else {%>
                <p><h1>VIDEO NO DISPONIBLE</p></h1>
                <%            }
                %>    

                <%
                    if (videoFormat.equals("flv")) {
                %>

                <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                <br>
                <object id="video" type="application/x-shockwave-flash" data="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" width="400" height="200">
                    <param name="movie" value="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" />
                    <param name="FlashVars" value="flv=<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>"/>
                </object>
                <%
                } else if (videoFormat.equals("flash")) {
                %>

                <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                <br>
                <object width="400" height="200" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"   codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"> 
                    <param name="SRC" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                    <embed src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200"></embed>
                </object>



                <%} else if (videoFormat.equals("mp4")) {
                %>   
                <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                <br>
                <video width="400" height="200" controls>
                    <source src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" type="video/mp4">
                    <object data="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200">
                        <embed src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200" autostart="false">    
                    </object>
                </video>

                <%
                } else if (videoFormat.equals("wav")) {
                %>
                <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                <br>
                <object width="400" height="200" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab">
                    <param name="src" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                    <param name="controller" value="true">
                </object>

                <%
                } else if (videoFormat.equals("wmv")) {

                %>    

                <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                <br>
                <object width="400" height="200" type="video/x-ms-asf" url="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" data="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
                    <param name="url" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                    <param name="filename" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                    <param name="autostart" value="1">
                    <param name="uiMode" value="full">
                    <param name="autosize" value="1">
                    <param name="playcount" value="1"> 
                    <embed type="application/x-mplayer2" src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200" autostart="true" showcontrols="true" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/"></embed>
                </object>

                <%
                    }

                %>
                    <br/><br/><%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                </td>

                <%
                    }
                %>
                <td>

                    <%
                        if (postIn.getPostSentimentalType() == 0) {
                    %>
                    ---
                    <% } else if (postIn.getPostSentimentalType() == 1) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/pos.png">
                    <%
                    } else if (postIn.getPostSentimentalType() == 2) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/neg.png">
                    <%
                        }
                    %>
                </td>
                <td>
                    <!--<%=postIn.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postIn.getPostSentimentalType() == 1 ? paramRequest.getLocaleString("medium") : postIn.getPostSentimentalType() == 2 ? paramRequest.getLocaleString("high") : "---"%>-->

                    <%
                        if (postIn.getPostIntesityType() == 0) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ibaja.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("low")%>">
                    <%        } else if (postIn.getPostIntesityType() == 1) {
                    %>    
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/imedia.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("medium")%>">
                    <%
                    } else if (postIn.getPostIntesityType() == 2) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ialta.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("high")%>">
                    <%
                    } else {
                    %>
                    ----
                    <%}%>
                </td> 
                <td align="center">
                    <%=postIn.getPostShared()%>
                </td>
                <td align="center">
                    <%=postIn.getPostPlace() == null ? "---" : postIn.getPostPlace()%>
                </td> 
                <td align="center">
                    <%=postIn.isIsPrioritary() ? SWBSocialUtil.Util.getStringFromGenericLocale("yes", user.getLanguage()) : SWBSocialUtil.Util.getStringFromGenericLocale("not", user.getLanguage())%>
                </td> 
            </tr>

        </tbody>
    </table>
</div>

