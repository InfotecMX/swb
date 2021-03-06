<%-- 
    Document   : pieChar
    Created on : 08-ago-2013, 11:41:35
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.Country"%>

<!DOCTYPE html>
<%
    SWBResourceURL urlRender = paramRequest.getRenderUrl();


    String suri = request.getParameter("suri");
    if (suri == null) {
        return;
    }
    SemanticObject semObj = SemanticObject.getSemanticObject(suri);
    if (semObj == null) {
        return;
    }
    String args = "?objUri=" + semObj.getEncodedURI();
    String lang = paramRequest.getUser().getLanguage();
    String idModel = paramRequest.getWebPage().getWebSiteId();
    args += "&lang=" + lang;
    args += "&idModel=" + idModel;


    String title = "";
    if (semObj.getGenericInstance() instanceof Descriptiveable) {
        title = ((Descriptiveable) semObj.getGenericInstance()).getDisplayTitle(lang);
    }


%>

<%!    public String reemplazar(String cadena) {

        String original = "��������������u�������������������";
        // Cadena de caracteres ASCII que reemplazar�n los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = cadena;
        if (output != null) {
            for (int i = 0; i < original.length(); i++) {
                // Reemplazamos los caracteres especiales.
                output = output.replace(original.charAt(i), ascii.charAt(i));
            }//for i
        }
        return output;
    }%>

<!DOCTYPE html>
<meta charset="utf-8">
<head>
    <style type="text/css">         
        @import "/swbadmin/css/swbsocial.css";          
        html, body, #main{
            overflow: auto;
        }
    </style>
    <script src="http://d3js.org/d3.v3.min.js"></script>   
    <script type="text/javascript" >
        function post()
        {
            //alert('entro');
            var content='';               
            
            var url='<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>';
            var strToEval='dojo.xhrPost({form: url,timeout: 3000,content: {'+ content +' }})';
               
            var xhrArgs = {
                form: url,
                handleAs: "text",
                load: function(data){
                    //dojo.byId("response").innerHTML = "Form posted.";
                    alert('respuesta: '+data);
                    funcion();
                },
                error: function(error){
                    alert('error'+error);
                    // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
                    // docs server.
                    //dojo.byId("response").innerHTML = "Form posted.";
                }
            }
                
            var deferred = dojo.xhrPost(xhrArgs);
                
                
        }
            
        function sendform(id, funcion)
        {
            //alert('entro al send form');
            var _form = dojo.byId(id);
                
            var xhrArgs = {
                form: _form,
                handleAs: "text",
                load: function(data){
                    //dojo.byId("response").innerHTML = "Form posted.";
                    //alert('respuesta: '+data);
                    funcion();
                },
                error: function(error){
                    //alert('error'+error);
                    // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
                    // docs server.
                    //dojo.byId("response").innerHTML = "Form posted.";
                }
            }
                
            var deferred = dojo.xhrPost(xhrArgs);
                
            return deferred;
        }         
    
    </script>

</head>


<div id="graficador">
    <div id="pieGenderParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("gender", lang)%></h1>
            <a id="hrefGender" href="<%=urlRender.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"  onclick="return confirm('�Desea exportar a excel?')" class="excel">Exportar excel</a>
        </div>
        <div id="pieGender">
        </div>
        <div class="grafOptions">            
            <div>
                <input  id="todogen"  type="radio" name="gender" value="all" checked >  
                <label title="Todos" for="todogen">Todos</label>
                <div id="allGender">
                </div>
            </div>
            <div>
                <input id="masc"  type="radio" name="gender" value="male" > 
                <label title="Masculino" for="masc">Masculino</label>
                <div id="mascGender">
                </div>
            </div>
            <div>
                <input id="feme" type="radio" name="gender"  value="female" > 
                <label title="Femenino" for="feme">Femenino</label>
                <div id="femGender">    
                </div>
            </div>
            <div>
                <input id="indegen" type="radio" name="gender"  value="nodefine" >
                <label title="No definido" for="indegen">No definido</label>
                <div id="nodefGender">    
                </div>
            </div>          
        </div>
        <div class="clear"></div>   
    </div>
    <script>    
        function setChartLabels(labelId, positive, negative, neutral){
            var paraPositives= document.createElement("p");   
            var paraNegatives= document.createElement("p");   
            var paraNeutrals= document.createElement("p");   

            var nodePositives = document.createTextNode( positive );
            var nodeNegatives= document.createTextNode( negative );
            var nodeNeutrals = document.createTextNode( neutral );

            paraPositives.appendChild(nodePositives);
            paraNegatives.appendChild(nodeNegatives);
            paraNeutrals.appendChild(nodeNeutrals);
            var element =   document.getElementById(labelId);
            element.appendChild(paraPositives);
            element.appendChild(paraNegatives);
            element.appendChild(paraNeutrals);
        }
        function pieGender(parametro, cont, isFirstLoad){   
            document.getElementById('pieGender').innerHTML="";
            var val = document.querySelector('input[name="gender"]:checked').value;            

            document.getElementById("hrefGender").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
            var opciones =  document.getElementsByName("gender");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = true;
            }
            var xArray = new Array();
            var color = d3.scale.category10();
            var width = 760,
            height = 300,
            offset = 20,
            radius = Math.min(width, height) / 2;

            var pie = d3.layout.pie()      
            .value(function(d) { //console.log('gender valuepieCharts'+d.value2); 
                return d.value2; });        
    
            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);

            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
            
            d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieGender.jsp<%=args%>&filter="+parametro, function(error, data) {
                    
               
                var svg = d3.select("#pieGender").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
        
                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles

                   
                d3.selectAll("input[name=gender]")
                .on("change", change);
      
                function change() {
                    // console.log('entro a change');
                     
                    var opciones =  document.getElementsByName("gender");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
                    pieGender(this.value, cont,false);//for future loads of the chart the last param is false
                    var value = this.value;
                    pie.value(function(d) { return d[value]; }); // change the value function
                    path = path.data(pie); // compute the new angles
                    path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                }
  
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) {
                        return arc(i(t));
                    };}
      
                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.3")
                .style("fill", function(d) { return d.data.color; });

                var tooltips = svg.select("#pieGender")
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                //.append("span")
                .attr('class', 'd3-tip')
                .html(function(d) {                
                    return "<strong>"+d.data.label+"</strong><br>"+d.data.value1+"/"+d.data.value2+"%";
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("click", function(d) {
                    if(confirm('�Desea exportar a excel?')){
                        var filter = d.data.label; 
                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "gender").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
                    }
                })
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                           
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });
                    
             
                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });    

                //Obtenemos los valores para los radios

        
                var total;
                for (var i = 0; i < data.length; i++) {
                    if(data[i].emptyData && isFirstLoad){
                        setChartLabels('allGender',0,0,0);
                        setChartLabels('mascGender',0,0,0);
                        setChartLabels('femGender',0,0,0);
                        setChartLabels('nodefGender',0,0,0);
                    }
                }
                if(cont == 0){
                    for (var i = 0; i < data.length; i++) {
                        xArray.push(data[i].valor);                        
                    }  
                    
                    if(xArray.length!=1){                      
                   
                        for (var x = 0; x < data.length; x++) {  
                            total = data[x].label3;
                            //console.log("total"+total);
                            setChartLabels('allGender',total.positivos,total.negativos,total.neutros);
                            break;
                            cont++;
                        } 
                                          
                        for (var j = 0; j <xArray.length ; j++) {                               
                            var myJSONObject = xArray[j];                            
                            if(j==0){
                                setChartLabels('mascGender',myJSONObject.positivos,myJSONObject.negativos,myJSONObject.neutros);
                            }else if(j==1){
                                setChartLabels('femGender',myJSONObject.positivos,myJSONObject.negativos,myJSONObject.neutros);
                            }else if(j==2){
                                setChartLabels('nodefGender',myJSONObject.positivos,myJSONObject.negativos,myJSONObject.neutros);
                            }
                        
                        }
                    }
                    cont++;
             
                }       
                var opciones =  document.getElementsByName("gender");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = false;
                }
         
            });
                
        }
    
        pieGender('all', '0', true);//only the first load the last param is true
    
    </script>

<%/*
    <div id="pieEducationParent">
        <div class="grafTit">
            <h1><=SWBSocialResUtil.Util.getStringFromGenericLocale("education", lang)></h1>                
            <a id="hrefEducation" href="<=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>" onclick="return confirm('�Desea exportar a excel?')"  class="excel">Exportar excel</a>
</div>
        <div id="pieEducation" >
        </div>
        <div class="grafOptions">
            <form id="formEducation" name="formEducation">
                <div>
                    <input id="todoedu" type="radio" name="education" value="all" checked>
                    <label title="Todos" for="todoedu">Todos</label>
                    <div id="todoEduDiv"></div>
                </div>
                <div>
                    <input id="secu" type="radio" name="education" value="secundaria" >
                    <label title="Secundaria" for="secu">Secundaria</label>
                    <div id="secuEduDiv"></div>
                </div>
                <div>
                    <input id="medi" type="radio" name="education" value="mediosuperior" > 
                    <label title="Medio" for="medi">Medio Superior</label>
                    <div id="medioEduDiv"></div>
                </div>
                <div>
                    <input id="inge" type="radio" name="education" value="graduado" >
                    <label title="Graduado" for="inge">Graduado</label>
                    <div id="graduadoEduDiv"></div>
                </div>
                <div>
                    <input id="indeedu" type="radio" name="education" value="undefined" >
                    <label title="No definido" for="indeedu">No definido</label>
                    <div id="nodefinidoEduDiv"></div>
                </div>
            </form>
        </div>
        <div class="clear"></div>   
    </div>
    <script>
        function pieEducation(parametro, cont, isFirstLoad){   
            document.getElementById('pieEducation').innerHTML="";
            var val = document.querySelector('input[name="education"]:checked').value;
            document.getElementById("hrefEducation").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>&filterGeneral="+val+"&isFirstLoad"+isFirstLoad ;
     
            var opciones =  document.getElementsByName("education");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = true;
            }
                    
            var educationArray = new Array();
            var color = d3.scale.category10();
            var width = 760,
            height = 300,
            offset = 20,
            radius = Math.min(width, height) / 2;


            var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { //console.log('eduaction value : '+d.value2);
                return d.value2; });    
            
    
            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);

            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
        

                    
            d3.json("<%=SWBPlatform.getContextPath()>/work/models/<%=SWBContext.getAdminWebSite().getId()>/jsp/stream/pieEducation.jsp<%=args>&filter="+parametro, function(error, data) {
            

                document.getElementById("pieEducation").removeAttribute("style");
                var svg = d3.select("#pieEducation").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + (height / 2 + offset)+")");
                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles
      
      
                d3.selectAll("input[name=education]")
                .on("change", change);
            
                function change() {
                    //console.log('entro a change');
                    var opciones =  document.getElementsByName("education");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
                    pieEducation(this.value, cont, false);
                    var value = this.value;
                    pie.value(function(d) { return d[value]; }); // change the value function
                    path = path.data(pie); // compute the new angles
                    path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                }
  
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) {
                        return arc(i(t));
                    };}
                       
            
                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.6")
                .style("fill", function(d) { return d.data.color; });


                var tooltips = svg.select("#pieEducation")
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                .attr('class', 'd3-tip')
                .html(function(d) {                
                    return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("click", function(d) {
                    if(confirm('�Desea exportar a excel?')){
                        var filter = d.data.label;            
                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "education").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
                    }
                })                        
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });
            

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });

                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });    
              
                for (var i = 0; i < data.length; i++) {
                    if(data[i].emptyData && isFirstLoad){
                        setChartLabels('todoEduDiv',0,0,0);
                        setChartLabels('secuEduDiv',0,0,0);
                        setChartLabels('medioEduDiv',0,0,0);
                        setChartLabels('graduadoEduDiv',0,0,0);
                        setChartLabels('nodefinidoEduDiv',0,0,0);
                    }
                }
                for (var i = 0; i < data.length; i++) {               
                    educationArray.push(data[i].valor);                                            
                } 
               
               
                if(educationArray.length!=1){                      
                 
                    var total;
                    if(cont == 0){
                        for (var x = 0; x < data.length; x++) {  
                            total = data[x].label3;
                            setChartLabels('todoEduDiv',total.positivos,total.negativos,total.neutros);
                            break;
                        } 
                        
                        for (var j = 0; j <educationArray.length ; j++) {
                            var myJSONObject = educationArray[j];
                            if(j==0){
                                setChartLabels('secuEduDiv',myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }else if(j==1){
                                setChartLabels('medioEduDiv',myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }else if(j==2){
                                setChartLabels('graduadoEduDiv',myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);                    
                            }else if(j==3){
                                setChartLabels('nodefinidoEduDiv',myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }
                       
                        }      
                        cont++;
       
                    }
                }
                var opciones =  document.getElementsByName("education");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = false;
                }
                                  
            });       
        }
    
        pieEducation('all', '0', true);
                
    </script>
*/
%>
<%/*
<div  id="pieRelationParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("statusRelation", lang)></h1>
            <a id="hrefRelation" href="<%=urlRender.setMode("exportExcel").setParameter("type", "relation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>" onclick="return confirm('�Desea exportar a excel?')"  class="excel">Exportar excel</a>
        </div> 
        <div id="pieRelationShipStatus">
        </div>        
        <div class="grafOptions">
            <form id="formRelation" name="formRelation">
                <div>
                    <input  id="todorel"type="radio" name="relation" value="all" checked>
                    <label title="Todos" for="todorel">Todos</label>
                    <div id="rAll"></div>
                </div>
                <div>
                    <input id="solt" type="radio" name="relation" value="single">
                    <label title="Soltero(a)" for="solt">Soltero(a)</label>
                    <div id="rSingle"></div>
                </div>
                <div>
                    <input id="casa" type="radio" name="relation" value="married">
                    <label title="Casado(a)" for="casa">Casado(a)</label>
                    <div id="rMarried"></div>
                </div>
                <div>
                    <input id="viud" type="radio" name="relation" value="widowed">
                    <label title="Viudo(a)" for="viud">Viudo(a)</label>
                    <div id="rWidowed"></div>
                </div>
                <div>
                    <input id="divo" type="radio" name="relation" value="divorced">
                    <label title="Divorciado(a)" for="divo">Divorciado(a)</label>
                    <div id="rDivorced"></div>
                </div>
                <div>
                    <input id="inderel" type="radio" name="relation" value="undefined">
                    <label title="No Definido" for="inderel">Todos</label>
                    <div id="rUndefined"></div>
                </div>
            </form>
        </div>
        <div class="clear"></div>
    </div>
    <script>
        function pieRelation(parametro, cont, isFirstLoad){   
            document.getElementById('pieRelationShipStatus').innerHTML="";
            var val = document.querySelector('input[name="relation"]:checked').value;
            document.getElementById("hrefRelation").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "relation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>&filterGeneral="+val ;
            var opciones =  document.getElementsByName("relation");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = true;
            }
                    
            var relationArray = new Array();
            var width = 760,
            height = 300,
            radius = Math.min(width, height) / 2;

            var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.value2; });
    
            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);
    
            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);

            d3.json("<%=SWBPlatform.getContextPath()>/work/models/<%=SWBContext.getAdminWebSite().getId()>/jsp/stream/pieRelationShipStatus.jsp<%=args>&filter="+parametro, function(error, data) {
            
                document.getElementById("pieRelationShipStatus").removeAttribute("style");
            
                var svg = d3.select("#pieRelationShipStatus").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles

                   
                d3.selectAll("input[name=relation]")
                .on("change", change);
      
                function change() {
                    //console.log('entro a change');
                    var opciones =  document.getElementsByName("relation");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
                    pieRelation(this.value, cont, false);
                    var value = this.value;
                    pie.value(function(d) { return d[value]; }); // change the value function
                    path = path.data(pie); // compute the new angles
                    path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                }
  
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) {
                        return arc(i(t));
                    };}
            
                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.6")
                .style("fill", function(d) { return d.data.color; });

                var tooltips = svg.select("#pieRelationShipStatus")
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                //.append("span")
                .attr('class', 'd3-tip')
                .html(function(d) {                
                    return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";;
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("click", function(d) {
                    if(confirm('�Desea exportar a excel?')){
                        var filter = d.data.label;            
                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "relation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
                    }
                })     
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });

                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });
        
                if(cont == 0){
                    
                    for (var i = 0; i < data.length; i++) {               
                        relationArray.push(data[i].valor);                                            
                    }                      
                    var total;
                    for (var i = 0; i < data.length; i++) {
                        if(data[i].emptyData && isFirstLoad){
                            setChartLabels('rAll',0,0,0);
                            setChartLabels('rSingle',0,0,0);
                            setChartLabels('rMarried',0,0,0);
                            setChartLabels('rWidowed',0,0,0);
                            setChartLabels('rDivorced',0,0,0);
                            setChartLabels('rUndefined',0,0,0);
                        }
                    }
                    if(relationArray.length!=1){
              
              
                        for (var x = 0; x < data.length; x++) {  
                            total = data[x].label3;
                            setChartLabels('rAll',total.positivos,total.negativos,total.neutros);
                            break;
                            cont++;
                        }                 
                        
              
                                          
                        for (var j = 0; j <relationArray.length ; j++) {
                            var myJSONObject = relationArray[j];

                            if(j==0){
                                setChartLabels('rSingle',myJSONObject.positivos,myJSONObject.negativos,myJSONObject.neutros);
                            }else if(j==1){
                                setChartLabels('rMarried',myJSONObject.positivos,myJSONObject.negativos,myJSONObject.neutros);
                            }else if(j==2){
                                setChartLabels('rWidowed',myJSONObject.positivos,myJSONObject.negativos,myJSONObject.neutros);
                            }else if(j==3){
                                setChartLabels('rDivorced',myJSONObject.positivos,myJSONObject.negativos,myJSONObject.neutros);
                            }else if(j==4){
                                setChartLabels('rUndefined',myJSONObject.positivos,myJSONObject.negativos,myJSONObject.neutros);
                            }
                        
                        } 
                    }
                    cont++;

                }
                var opciones =  document.getElementsByName("relation");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = false;
                }
            });
    
        }
    
        pieRelation('all', '0', true);
                

    </script>
*/
%>
<%/*
    <div id="lifeStageParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("lifeStage", lang)></h1>
            <a  id="hrefLife" href="<%=urlRender.setMode("exportExcel").setParameter("type", "lifeStage").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>" onclick="return confirm('�Desea exportar a excel?')" class="excel">Exportar excel</a>
        </div> 
        <div id="profileLifeStage">      
        </div>
        <div class="grafOptions">
            <form id="formEducation" name="formRelation">
                <div>
                    <input id="todolif" type="radio" name="life" value="all" checked="">
                    <label title="Todos" for="todolif">Todos</label>
                    <div id="lAll"></div>                        
                </div>
                <div>
                    <input id="nino" type="radio" name="life" value="child">
                    <label title="Ni�o(a): 0-12 a�os" for="nino">Ni�o(a): 0-12 a�os</label>
                    <div id="lChild"></div>                        
                </div>
                <div>
                    <input id="adol" type="radio" name="life" value="teenAge">
                    <label title="Adolescente: 13-18 a�os" for="adol">Adolescente: 13-18 a�os</label>
                    <div id="lTeenAge"></div>                        
                </div>
                <div>
                    <input id="jove" type="radio" name="life" value="young">
                    <label title="Joven: 19-30 a�os" for="jove">Joven: 19-30 a�os</label>
                    <div id="lYoung"></div>                        
                </div>
                <div>
                    <input id="adjo" type="radio" name="life" value="youngAdult">
                    <label title="Adulto joven: 31-45 a�os" for="adjo">Adulto joven: 31-45 a�os</label>
                    <div id="lYoungAdult"></div>                        
                </div>
                <div>
                    <input id="adul" type="radio" name="life" value="adult">
                    <label title="Adulto: 46-65 a�os" for="adul">Adulto: 46-65 a�os</label>
                    <div id="lAdul"></div>                        
                </div>
                <div>
                    <input id="terc" type="radio" name="life" value="thirdAge">
                    <label title="Tercera edad: +66 a�os" for="terc">Tercera edad: +66 a�os</label>
                    <div id="lThirdAge"></div>                        
                </div>
                <div>
                    <input id="indelif" type="radio" name="life" value="nodefine">
                    <label title="No definido " for="indelif">No definido </label>
                    <div id="lNodefine"></div>                        
                </div>
            </form>
        </div>
        <div class="clear"></div>
    </div>

    <script>
    
        function pieLifeStage(parametro, cont, isFirstLoad){   
            document.getElementById('profileLifeStage').innerHTML="";

            var val = document.querySelector('input[name="life"]:checked').value;
            document.getElementById("hrefLife").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "lifeStage").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>&filterGeneral="+val ;
            var opciones =  document.getElementsByName("life");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = true;
            }

            var width = 760,
            height = 300,
            radius = Math.min(width, height) / 2;
            var lifeStageArray = new Array();
    
            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);

    
            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
     
        
            var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.value2; });

     

            d3.json("<%=SWBPlatform.getContextPath()>/work/models/<%=SWBContext.getAdminWebSite().getId()>/jsp/stream/pieLifeStage.jsp<%=args>&filter="+parametro, function(error, data) {
            
                document.getElementById("profileLifeStage").removeAttribute("style");
            
                var svg = d3.select("#profileLifeStage").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles

                   
                d3.selectAll("input[name=life]")
                .on("change", change);
      
                function change() {
                    // console.log('entro a change');
                    var opciones =  document.getElementsByName("life");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
                    pieLifeStage(this.value, cont, false);
                    var value = this.value;
                    //clearTimeout(timeout);
                    pie.value(function(d) { return d[value]; }); // change the value function
                    path = path.data(pie); // compute the new angles
                    path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                }
  
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) {
                        return arc(i(t));
                    };}
            
            
                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.6")
                .style("fill", function(d) { return d.data.color; });

                var tooltips = svg.select("#profileLifeStage")
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                //.append("span")
                .attr('class', 'd3-tip')
                .html(function(d) {                
                    return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("click", function(d) {
                    if(confirm('�Desea exportar a excel?')){
                        var filter = d.data.label;            
                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "lifeStage").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
                    }
                })  
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });

                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });
        
                var total;
                for (var i = 0; i < data.length; i++) {               
                    lifeStageArray.push(data[i].valor);                                            
                }
                
                for (var i = 0; i < data.length; i++) {
                    if(data[i].emptyData && isFirstLoad){
                        setChartLabels('lAll',0,0,0);
                        setChartLabels('lChild',0,0,0);
                        setChartLabels('lTeenAge',0,0,0);
                        setChartLabels('lYoung',0,0,0);
                        setChartLabels('lYoungAdult',0,0,0);
                        setChartLabels('lAdul',0,0,0);
                        setChartLabels('lThirdAge',0,0,0);
                        setChartLabels('lNodefine',0,0,0);
                    }
                }
            
                if(lifeStageArray.length!=1){
                
        
                    if(cont == 0){
                        for (var x = 0; x < data.length; x++) {  
                            total = data[x].label3;                            
                            setChartLabels('lAll',total.positivos,total.negativos,total.neutros);
                            break;
                            cont++;
                        }
                                          
                        for (var j = 0; j <lifeStageArray.length ; j++) {
                            var myJSONObject = lifeStageArray[j];
                            
                            if(j==0){
                                setChartLabels('lChild', myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }else if(j==1){
                                setChartLabels('lTeenAge', myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }else if(j==2){
                                setChartLabels('lYoung', myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }else if(j==3){
                                setChartLabels('lYoungAdult', myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }else if(j==4){
                                setChartLabels('lAdul', myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }else if(j==5){
                                setChartLabels('lThirdAge', myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }else if(j==6){
                                setChartLabels('lNodefine', myJSONObject.positivos, myJSONObject.negativos, myJSONObject.neutros);
                            }
                            cont++;
                        }
                    }

                }
                var opciones =  document.getElementsByName("life");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = false;
                }
            });
    
        }
    
        pieLifeStage('all', '0',true);
    </script>
*/%>
    <!-- grafica de mexico -->
    <div id="profileGeoLocationParent">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("location", lang)%> M�xico</h1>
            <a id="hrefGeo" href="<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang).setParameter("country", "MX")%>" onclick="return confirm('�Desea exportar a excel?')"  class="excel">Exportar excel</a>
        </div> 
        <div id="profileGeoLocation">
        </div>     
        <div class="grafOptions">
            <div>
                <input id="todogeo" type="radio" name="geo" value="all" checked="">
                <label title="Todos" for="todogeo">Todos</label>
                <div id="todogeoDiv"></div>
            </div>

            <div class="geoselect">
                <%
                    Iterator<CountryState> i = CountryState.ClassMgr.listCountryStates();
                    while (i.hasNext()) {
                        CountryState c = i.next();
                        if (c != null && c.getCountry().getId().equals("MX")) {
                %>
                <input id="bcn<%=c.getTitle()%>" type="radio" name="geo" value="<%=reemplazar(c.getTitle())%>">
                <label for="bcn<%=c.getTitle()%>"><%=c.getTitle()%></label>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>

    <script>
        function pieGeo(parametro, cont){
            document.getElementById('profileGeoLocation').innerHTML="";
            //console.log('THE PARAM GEO:' + parametro);        
        
            var acentosP = "����������������������������������������������";
            var originalP = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

            for (var i=0; i<acentosP.length; i++) {
                parametro = parametro.replace(acentosP.charAt(i), originalP.charAt(i));
            }
 
            var val = document.querySelector('input[name="geo"]:checked').value;
            document.getElementById("hrefGeo").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("country", "MX").setParameter("lang", lang)%>&filterGeneral="+val ;
     
            var opciones =  document.getElementsByName("geo");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = true;
            }
                    
            var width = 760,
            height = 300,
            radius = Math.min(width, height) / 2;
            var geoArray = new Array();

            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);

            var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.value2; });

            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
    
   

            d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieProfileGeoLocation.jsp<%=args%>&filter="+parametro, function(error, data) {

                document.getElementById("profileGeoLocation").removeAttribute("style");
            
                var svg = d3.select("#profileGeoLocation").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles

                   
                d3.selectAll("input[name=geo]")
                .on("change", change);
      
                function change() {
                    //  console.log('entro a change');
                    var opciones =  document.getElementsByName("geo");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
                    pieGeo(this.value, cont);
                    var value = this.value;
                    pie.value(function(d) { return d[value]; }); // change the value function
                    path = path.data(pie); // compute the new angles
                    path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                }
  
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) {
                        return arc(i(t));
                    };}
            
                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.6")
                .style("fill", function(d) { return d.data.color; });

                var tooltips = svg.select("#profileGeoLocation")
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                //.append("span")
                .attr('class', 'd3-tip')      
                .html(function(d) {                
                    return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";;
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("click", function(d) {
                    
                    if(confirm('�Desea exportar a excel?')){
                        var filter = d.data.label;  
                        var acentos = "����������������������������������������������";
                        var original = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

                        for (var i=0; i<acentos.length; i++) {
                            filter = filter.replace(acentos.charAt(i), original.charAt(i));
                        }
                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("country", "MX").setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
                        //document.hrefGeo.href = url;
                    }
                }) 
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });

                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });

               
                if(cont  == 0){
                    
                    ////if(data.length!=1){
                    for (var x = data.length-1; x < data.length; x++) {  
                        var to;
                        to = data[x].valor;
                        var paraPositives= document.createElement("p");   
                        var paraNegatives= document.createElement("p");   
                        var paraNeutrals= document.createElement("p");   

                        var nodPositives = document.createTextNode(to.positivos);
                        var nodNegatives= document.createTextNode(to.negativos);
                        var nodNeutrals = document.createTextNode(to.neutros);
                            
                        paraPositives.appendChild(nodPositives);
                        paraNegatives.appendChild(nodNegatives);
                        paraNeutrals.appendChild(nodNeutrals);
                        
                        var element = document.getElementById("todogeoDiv");                            
                        element.appendChild(paraPositives);
                        element.appendChild(paraNegatives);
                        element.appendChild(paraNeutrals);
                        break;
                        cont++;
                    }       
                        
                    /* for (var i = 0; i < data.length; i++) {        
                            if(data[i].label==="No definido"){
                           
                                var pPositives= document.createElement("p");   
                                var pNegatives= document.createElement("p");   
                                var pNeutrals= document.createElement("p");   
                                var myJSONObject = data[i].valor; 
                        
                                var nodePositives = document.createTextNode(myJSONObject.positivos);
                                var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                                var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                                pPositives.appendChild(nodePositives);
                                pNegatives.appendChild(nodeNegatives);
                                pNeutrals.appendChild(nodeNeutros);
                        
                                var element=document.getElementById("indegeoDiv");
                                element.appendChild(pPositives);
                                element.appendChild(pNegatives);
                                element.appendChild(pNeutrals);
                            }                       
                        }  */
                }
                cont++;
                //  }
                var opciones =  document.getElementsByName("geo");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = false;
                }
            });
    
        }  
        
        pieGeo('all', '0'); 
    </script>








    <!-- grafica de estados unidos -->

    <div id="profileGeoLocationParentEU">
        <div class="grafTit">
            <h1><%=SWBSocialResUtil.Util.getStringFromGenericLocale("location", lang)%> Estados Unidos</h1>
            <a id="hrefGeoEU" href="<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang).setParameter("country", "US")%>" onclick="return confirm('�Desea exportar a excel?')" class="excel">Exportar excel</a>
        </div> 
        <div id="profileGeoLocationEU">
        </div>     
        <div class="grafOptions">
            <div>
                <input id="todogeoEU" type="radio" name="geoUS" value="all" checked="">
                <label title="Todos" for="todogeoEU">Todos</label>
                <div id="todogeoDivEU"></div>
            </div>

            <div class="geoselectEU">
                <%
                    Iterator<CountryState> ieU = CountryState.ClassMgr.listCountryStates();
                    while (ieU.hasNext()) {
                        CountryState c = ieU.next();
                        if (c != null && c.getCountry().getId().equals("US")) {
                %>
                <input id="bcn<%=c.getTitle()%>" type="radio" name="geoUS" value="<%=c.getTitle()%>">
                <label for="bcn<%=c.getTitle()%>"><%=c.getTitle()%></label>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>

    <script>
        function pieGeoEU(parametro, cont){
            document.getElementById('profileGeoLocationEU').innerHTML="";
            console.log('THE PARAM GEOEU:' + parametro);        
        
            var acentosP = "����������������������������������������������";
            var originalP = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

       
 
            var val = document.querySelector('input[name="geoUS"]:checked').value;
            document.getElementById("hrefGeoEU").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("country", "US").setParameter("lang", lang)%>&filterGeneral="+val ;
     
            var opciones =  document.getElementsByName("geoUS");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = true;
            }
                    
            var width = 760,
            height = 300,
            radius = Math.min(width, height) / 2;
            var geoArray = new Array();

            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);

            var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.value2; });

            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
    
   

            d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieProfileGeoLocationEU.jsp<%=args%>&filter="+parametro, function(error, data) {

                document.getElementById("profileGeoLocationEU").removeAttribute("style");
            
                var svg = d3.select("#profileGeoLocationEU").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles

                   
                d3.selectAll("input[name=geoUS]")
                .on("change", change);
      
                function change() {
                    //  console.log('entro a change');
                    var opciones =  document.getElementsByName("geoUS");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
                    pieGeoEU(this.value, cont);
                    var value = this.value;
                    pie.value(function(d) { return d[value]; }); // change the value function
                    path = path.data(pie); // compute the new angles
                    path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                }
  
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) {
                        return arc(i(t));
                    };}
            
                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.6")
                .style("fill", function(d) { return d.data.color; });

                var tooltips = svg.select("#profileGeoLocationEU")
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                //.append("span")
                .attr('class', 'd3-tip')      
                .html(function(d) {                
                    return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";;
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("click", function(d) {
                    if(confirm('�Desea exportar a excel?')){
                        var filter = d.data.label;  
                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "geoLocation").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
                    }
                }) 
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });

                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });
        
               
                if(cont  == 0){
              
                    //   if(data.length!=1){
                    for (var x = data.length-1; x < data.length; x++) {  
                        var to;
                        to = data[x].valor;
                        var paraPositives= document.createElement("p");   
                        var paraNegatives= document.createElement("p");   
                        var paraNeutrals= document.createElement("p");   
                            

                        var nodPositives = document.createTextNode(to.positivos);
                        var nodNegatives= document.createTextNode(to.negativos);
                        var nodNeutrals = document.createTextNode(to.neutros);
                            
                        paraPositives.appendChild(nodPositives);
                        paraNegatives.appendChild(nodNegatives);
                        paraNeutrals.appendChild(nodNeutrals);
                        
                        var element = document.getElementById("todogeoDivEU");                            
                        element.appendChild(paraPositives);
                        element.appendChild(paraNegatives);
                        element.appendChild(paraNeutrals);
                        break;
                        cont++;
                    }       
                        
                    for (var i = 0; i < data.length; i++) {        
                        if(data[i].label==="No definido"){
                           
                            var pPositives= document.createElement("p");   
                            var pNegatives= document.createElement("p");   
                            var pNeutrals= document.createElement("p");   
                            var myJSONObject = data[i].valor; 
                        
                            var nodePositives = document.createTextNode(myJSONObject.positivos);
                            var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                            var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                            pPositives.appendChild(nodePositives);
                            pNegatives.appendChild(nodeNegatives);
                            pNeutrals.appendChild(nodeNeutros);
                        
                            var element=document.getElementById("indegeoDivEU");
                            element.appendChild(pPositives);
                            element.appendChild(pNegatives);
                            element.appendChild(pNeutrals);
                        }                       
                    }  
                    // }
                    cont++;
                }
                var opciones =  document.getElementsByName("geoUS");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = false;
                }
            });
    
        }  
        
        pieGeoEU('all', '0'); 
    </script>






    <!--  grafica lenguajes-->

    <div id="profileLanguageParent">
        <div class="grafTit">
            <h1> Lenguajes </h1>
            <a id="hrefLanguage" href="<%=urlRender.setMode("exportExcel").setParameter("type", "languages").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>" onclick="return confirm('�Desea exportar a excel?')" class="excel">Exportar excel</a>
        </div> 
        <div id="profileLanguage">
        </div>     
        <div class="grafOptions">
            <div>
                <input id="todolanguage" type="radio" name="language" value="all" checked="">
                <label title="Todos" for="todolanguage">Todos</label>
                <div id="todoLanguageDiv"></div>
            </div>
            <div class="languageselect">

                <%
                    // Iterator languagei=  Language.ClassMgr.listLanguages(SWBContext.getAdminWebSite());
                    Iterator languagei = Language.ClassMgr.listLanguages(SWBSocialUtil.getConfigWebSite());
                    while (languagei.hasNext()) {
                        Language language = (Language) languagei.next();
                        if (language != null) {

                %>
                <input id="bcn<%=language.getDisplayTitle(lang)%>" type="radio" name="language" value="<%=reemplazar(language.getDisplayTitle(lang))%>">
                <label for="bcn<%=language.getDisplayTitle(lang)%>"><%=language.getDisplayTitle(lang)%></label>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>
    <script>
        function pieLanguage(parametro, cont){
            document.getElementById('profileLanguage').innerHTML="";
            //console.log('THE PARAM GEO:' + parametro);        
        
            var acentosP = "����������������������������������������������";
            var originalP = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

            for (var i=0; i<acentosP.length; i++) {
                parametro = parametro.replace(acentosP.charAt(i), originalP.charAt(i));
            }
 
            var val = document.querySelector('input[name="language"]:checked').value;
            document.getElementById("hrefLanguage").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "languages").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
            var opciones =  document.getElementsByName("language");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = true;
            }
                    
            var width = 760,
            height = 300,
            radius = Math.min(width, height) / 2;
            var geoArray = new Array();

            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);

            var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.value2; });

            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
    
   

            d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieLanguage.jsp<%=args%>&filter="+parametro, function(error, data) {

                document.getElementById("profileLanguage").removeAttribute("style");
            
                var svg = d3.select("#profileLanguage").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles

                   
                d3.selectAll("input[name=language]")
                .on("change", change);
      
                function change() {
                    //  console.log('entro a change');
                    var opciones =  document.getElementsByName("language");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
                    pieLanguage(this.value, cont);
                    var value = this.value;
                    pie.value(function(d) { return d[value]; }); // change the value function
                    path = path.data(pie); // compute the new angles
                    path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                }
  
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) {
                        return arc(i(t));
                    };}
            
                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.6")
                .style("fill", function(d) { return d.data.color; });

                var tooltips = svg.select("#profileLanguage")
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                //.append("span")
                .attr('class', 'd3-tip')      
                .html(function(d) {                
                    return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";;
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("click", function(d) {
                    if(confirm('�Desea exportar a excel?')){
                        var filter = d.data.label;  
                        var acentos = "����������������������������������������������";
                        var original = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

                        for (var i=0; i<acentos.length; i++) {
                            filter = filter.replace(acentos.charAt(i), original.charAt(i));
                        }

                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "languages").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
                    }
                }) 
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });

                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });
        
                var o=   parseInt(data.length);
                var z =  o - 1;

                if(cont  == 0){
                    
                    //  if(data.length!=1){
                       
                    for (var x =z; x < data.length; x++) {  
                        var to;
                        to = data[x].valor;
                        var paraPositives= document.createElement("p");   
                        var paraNegatives= document.createElement("p");   
                        var paraNeutrals= document.createElement("p");   
                            

                        var nodPositives = document.createTextNode(to.positivos);
                        var nodNegatives= document.createTextNode(to.negativos);
                        var nodNeutrals = document.createTextNode(to.neutros);
                            
                        paraPositives.appendChild(nodPositives);
                        paraNegatives.appendChild(nodNegatives);
                        paraNeutrals.appendChild(nodNeutrals);
                        
                        var element = document.getElementById("todoLanguageDiv");               // gaby            
                        element.appendChild(paraPositives);
                        element.appendChild(paraNegatives);
                        element.appendChild(paraNeutrals);
                        break;
                        cont++;
                    }       
                        
                    for (var i = 0; i < data.length; i++) {        
                        if(data[i].label==="No definido"){
                           
                            var pPositives= document.createElement("p");   
                            var pNegatives= document.createElement("p");   
                            var pNeutrals= document.createElement("p");   
                            var myJSONObject = data[i].valor; 
                        
                            var nodePositives = document.createTextNode(myJSONObject.positivos);
                            var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                            var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                            pPositives.appendChild(nodePositives);
                            pNegatives.appendChild(nodeNegatives);
                            pNeutrals.appendChild(nodeNeutros);
                        
                            //var element=document.getElementById("todoLanguageDiv");
                            //element.appendChild(pPositives);
                            //element.appendChild(pNegatives);
                            //element.appendChild(pNeutrals);
                        }                       
                    }  
                    //  }
                    cont++;
                }
                var opciones =  document.getElementsByName("language");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = false;
                }
            });
    
        }  
        
        pieLanguage('all', '0'); 
    </script>







    <!--  grafica Countrys-->

    <div id="profileCountryParent">
        <div class="grafTit">
            <h1> Global </h1>
            <a id="hrefCountry" href="<%=urlRender.setMode("exportExcel").setParameter("type", "geolocationCountry").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"  onclick="return confirm('�Desea exportar a excel?')" class="excel">Exportar excel</a>
        </div> 
        <div id="profileCountry">
        </div>     
        <div class="grafOptions">
            <div>
                <input id="todocountry" type="radio" name="country" value="all" checked="">
                <label title="Todos" for="todocountry">Todos</label>
                <div id="todoCountryDiv"></div>
            </div>
            <div class="countryselect">
                <input id="bcnNodefinido" type="radio" name="country" value="No definido">
                <label for="bcnNodefinido">No definido</label>
                <%
                    // Iterator languagei=  Language.ClassMgr.listLanguages(SWBContext.getAdminWebSite());
                    WebSite ss = SWBSocialUtil.getConfigWebSite();
                    Iterator<Country> country = Country.ClassMgr.listCountries(ss);
                    while (country.hasNext()) {
                        Country co = country.next();
                        if (co != null) {
                %>
                <input id="bcn<%=co.getId()%>" type="radio" name="country" value="<%=reemplazar(co.getTitle())%>">
                <label for="bcn<%=co.getId()%>"><%=co.getTitle()%></label>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>
    <script>
        function pieCountry(parametro, cont){
            document.getElementById('profileCountry').innerHTML="";
            //console.log('THE PARAM GEO:' + parametro);        
        
            var acentosP = "����������������������������������������������";
            var originalP = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

            for (var i=0; i<acentosP.length; i++) {
                parametro = parametro.replace(acentosP.charAt(i), originalP.charAt(i));
            }
 
            var val = document.querySelector('input[name="country"]:checked').value;
            document.getElementById("hrefCountry").href= "<%=urlRender.setMode("exportExcel").setParameter("type", "geolocationCountry").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>&filterGeneral="+val ;
     
            var opciones =  document.getElementsByName("country");//.disabled=false;
            for(var i=0; i<opciones.length; i++) {        
                opciones[i].disabled = true;
            }
                    
            var width = 760,
            height = 300,
            radius = Math.min(width, height) / 2;
            var geoArray = new Array();

            var arc = d3.svg.arc()
            .outerRadius(radius - 20)
            .innerRadius(radius - 100);

            var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.value2; });

            var arcOver = d3.svg.arc()
            .outerRadius(radius - 10)
            .innerRadius(0);
    
   

            d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/pieMapCountry.jsp<%=args%>&filter="+parametro, function(error, data) {

                document.getElementById("profileCountry").removeAttribute("style");
            
                var svg = d3.select("#profileCountry").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
                .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

                var path = svg.datum(data).selectAll("path")
                .data(pie)
                .enter().append("path")
                .attr("fill", function(d, i) { return d.data.color; })
                .attr("d", arc)
                .each(function(d) { this._current = d; }); // store the initial angles

                   
                d3.selectAll("input[name=country]")
                .on("change", change);
      
                function change() {
                    //  console.log('entro a change');
                    var opciones =  document.getElementsByName("country");//.disabled=false;
                    for(var i=0; i<opciones.length; i++) {        
                        opciones[i].disabled = true;
                    }
                    pieCountry(this.value, cont);
                    var value = this.value;
                    pie.value(function(d) { return d[value]; }); // change the value function
                    path = path.data(pie); // compute the new angles
                    path.transition().duration(750).attrTween("d", arcTween); // redraw the arcs
                }
  
                function arcTween(a) {
                    var i = d3.interpolate(this._current, a);
                    this._current = i(0);
                    return function(t) {
                        return arc(i(t));
                    };}
            
                var gl = svg.selectAll(".arcOver")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arcOver")
                .style("visibility","hidden");
            
                gl.append("path")
                .attr("d", arcOver)
                .style("fill-opacity", "0.6")
                .style("fill", function(d) { return d.data.color; });

                var tooltips = svg.select("#profileCountry")
                .data(pie(data))
                .enter().append("div")
                .attr("class","chartToolTip")
                .style("display", "none")
                .style("position", "absolute")
                .style("z-index", "10");

                tooltips.append("p")
                //.append("span")
                .attr('class', 'd3-tip')      
                .html(function(d) {                
                    return ""+d.data.label+"<br><center>"+d.data.value1+"/"+d.data.value2+"%</center>";;
                });       
        
        
                var g = svg.selectAll(".arc")
                .data(pie(data))
                .enter().append("g")
                .attr("class", "arc")
                .on("mouseover", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","visible"); 
                    d3.select(tooltips[0][i])
                    .style("display","block");
                })
                .on("mouseout", function(d, i) {
                    d3.select(gl[0][i]).style("visibility","hidden"); 
                    d3.select(tooltips[0][i])
                    .style("display","none");
                    d3.select(gl[0][i]).style("fill",function(d) {
                        return d.data.color;
                    });
                })
                .on("click", function(d) {
                    if(confirm('�Desea exportar a excel?')){
                        var filter = d.data.label;  
                        var acentos = "����������������������������������������������";
                        var original = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc";

                        for (var i=0; i<acentos.length; i++) {
                            filter = filter.replace(acentos.charAt(i), original.charAt(i));
                        }

                        var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "geolocationCountry").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri).setParameter("lang", lang)%>"+"&filter="+filter+"&filterGeneral="+val;
                        document.location.href = url;
                    }
                }) 
                .on("mousemove", function(d, i) {
                    d3.select(tooltips[0][i])
                    .style("top", d3.event.pageY-10+"px")
                    .style("left", d3.event.pageX+10+"px")
                });

                //Create slices
                g.append("path")
                .attr("d", arc)
                .style("stroke", "white")
                .style("stroke-width", "2")
                .style("fill", function(d, i) {
                    return  d.data.color;
                });

                svg
                .append("text")
                .text("title")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
                .attr("x","0")
                .attr("y",function(d) {
                    return - width/2;
                });
        
               
                if(cont  == 0){
                    var temp =  parseInt(data.length);
                    var fin= temp -1 ; 
                    // if(data.length!=1){
                    for (var x = fin; x < data.length; x++) {  
                        var to;
                        to = data[x].valor;
                        var paraPositives= document.createElement("p");   
                        var paraNegatives= document.createElement("p");   
                        var paraNeutrals= document.createElement("p");   

                        var nodPositives = document.createTextNode(to.positivos);
                        var nodNegatives= document.createTextNode(to.negativos);
                        var nodNeutrals = document.createTextNode(to.neutros);
                            
                        paraPositives.appendChild(nodPositives);
                        paraNegatives.appendChild(nodNegatives);
                        paraNeutrals.appendChild(nodNeutrals);
                        
                        var element = document.getElementById("todoCountryDiv");               // gaby            
                        element.appendChild(paraPositives);
                        element.appendChild(paraNegatives);
                        element.appendChild(paraNeutrals);
                        break;
                        cont++;
                    }       
                        
                    /*   for (var i = 0; i < data.length; i++) {        
                            if(data[i].label==="No definido"){
                           
                                var pPositives= document.createElement("p");   
                                var pNegatives= document.createElement("p");   
                                var pNeutrals= document.createElement("p");   
                                var myJSONObject = data[i].valor; 
                        
                                var nodePositives = document.createTextNode(myJSONObject.positivos);
                                var nodeNegatives = document.createTextNode(myJSONObject.negativos);
                                var nodeNeutros = document.createTextNode(myJSONObject.neutros );             
                        
                                pPositives.appendChild(nodePositives);
                                pNegatives.appendChild(nodeNegatives);
                                pNeutrals.appendChild(nodeNeutros);
                        
                                //var element=document.getElementById("todoLanguageDiv");
                                //element.appendChild(pPositives);
                                //element.appendChild(pNegatives);
                                //element.appendChild(pNeutrals);
                            }                       
                        }  */
                    //    }
                    cont++;
                }
                var opciones =  document.getElementsByName("country");//.disabled=false;
                for(var i=0; i<opciones.length; i++) {        
                    opciones[i].disabled = false;
                }
            });
    
        }  
        
        pieCountry('all', '0'); 
    </script>





</div>
