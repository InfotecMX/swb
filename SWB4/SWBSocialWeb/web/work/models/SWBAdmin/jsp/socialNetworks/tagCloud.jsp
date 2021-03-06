<%-- 
    Document   : tagCloud
    Created on : 14/06/2013, 09:53:02 AM
    Author     : francisco.jimenez
--%>

<%@page import="org.openjena.riot.SysRIOT"%>
<%@page import="org.semanticwb.social.PunctuationSign"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Set"%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.jsoup.safety.Whitelist"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.Language"%>
<%@page import="java.util.Comparator"%>
<%@page import="org.semanticwb.social.SocialAdmin"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.Stream"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%!

    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        return true;
    }
    
    public static <String, Integer extends Comparable<Integer>> Map<String, Integer> sortByValues(final Map<String, Integer> map) {
        Comparator<String> valueComparator =  new Comparator<String>() {
            public int compare(String k1, String k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0)
                    return 1;
                else
                    return compare;
            }
        };
        
        Map<String, Integer> sortedByValues = new TreeMap<String, Integer>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
    
    public static int randomId(){
        Random rand = new Random();;
        int min=1 , max=Integer.MAX_VALUE;
        return rand.nextInt(max - min + 1) + min;
    }
    
%>

<%
    //System.out.println("suri in tagsCloud:" + request.getParameter("suri"));
    String suri = request.getParameter("suri");
    String noTopic = request.getParameter("noTopic");
    //System.out.println("noTopic:" + noTopic);
    Stream stream = null;
    SocialTopic socialT = null;
    Iterator <PostIn> itPostIns = null;//The posts
    //long noOfPosts = 0L;
    long startTime = System.currentTimeMillis();

    try{
    if(suri != null) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        if(semObj.createGenericInstance() instanceof Stream){//Tag Cloud for stream
            //System.out.println("is stream");
            stream = (Stream)semObj.getGenericInstance();
            itPostIns=stream.listPostInStreamInvs();//The posts
            //itPostIns = sortByCreatedSet(itPostIns, true).iterator();//Newer first
            //SWBUtils.Collections.sizeOf(stream.listPostInStreamInvs());//Number of recovered posts
        }else if(semObj.createGenericInstance() instanceof SocialTopic){//Tag Cloud for social topic
            //System.out.println("is social topic");
            socialT = (SocialTopic)semObj.getGenericInstance();
            itPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialT, socialT.getSocialSite());//The posts
            //itPostIns = sortByCreatedSet(itPostIns, true).iterator();//Newer first
            //SWBUtils.Collections.sizeOf(PostIn.ClassMgr.listPostInBySocialTopic(socialT, socialT.getSocialSite()));//Number of recovered posts
        }else{
            return;
        }
    }else{
        return;
    }
    }catch(Exception e){
        //System.out.println("\n\n\n*******************");
        e.printStackTrace();
    }
    
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT);
    if(stream != null){
        renderURL.setParameter("sID", stream.getId());
        renderURL.setParameter("wsite", stream.getSemanticObject().getModel().getName());
    }
    renderURL.setParameter("dialog", "close");
    renderURL.setParameter("suri", suri);
    renderURL.setParameter("reloadTap", "true");

    Map<String, Integer> cloudTags = new TreeMap<String,Integer>();//Tree to save the words

    //SocialAdmin socialAdminSite=(SocialAdmin)SWBContext.getAdminWebSite();    
    
    //itPostIns=stream.listPostInStreamInvs();//The posts
    //itPostIns = SWBComparator.sortByCreatedSet(itPostIns, false).iterator();
    
    //SWBUtils.Collections.sizeOf(stream.listPostInStreamInvs());//Number of recovered posts
        
    //int posts = 0;
    int randomId = randomId();
    //long startTime = System.currentTimeMillis();
    String[] enStopWords=SWBSocialUtil.Classifier.getEnglishStopWords();
    List listEnStopWords=Arrays.asList(enStopWords);
    //System.out.println("That:"+listEnStopWords.contains("that"));
    //int percent = (int)(noOfPosts*0.6);//Only process 60% of stream
    //System.out.println("Size of sample: " + percent);
    while(itPostIns.hasNext()){        
        if(cloudTags.size() >= 4000){
            //System.out.println("Too large Word List");
            break;        
        }/*else if( posts > percent){
            System.out.println("Has reached the %");
            break;
        }*/
        PostIn postIn = itPostIns.next();
        
        if(noTopic != null && noTopic.equals("true")){//When the tag cloud is called from tab "Mensajes Recibidos sin Tema"
            if(postIn.getSocialTopic() != null){//Don't process posts with Social topics
                continue;
            }
        }                
        if(postIn.getMsg_Text()!=null)
        {//If post has message
            String msg=null;
            msg=postIn.getMsg_Text().toLowerCase();            
            String lang=null;
            Language postInLang=postIn.getMsg_lang(); 
            if(postInLang!=null) lang=postInLang.getId(); 
            if(lang!=null)
            {
                //if(lang.equals("es"))
                {
                    msg = SWBSocialUtil.Strings.removePrepositions(msg); 
                }   
                //msg = Jsoup.clean(msg, Whitelist.simpleText());
                msg = SWBSocialResUtil.Util.replaceSpecialCharactersAndHtmlCodes(msg, false);
                msg = Jsoup.clean(msg, Whitelist.simpleText());
                msg = SWBSocialUtil.Strings.removePuntualSigns(msg, SWBContext.getGlobalWebSite());
                
                String[] mgsWords=msg.split("\\s+");  //Dividir valores
                for(int i = 0; i < mgsWords.length ; i++)
                {
                    String word = mgsWords[i];
                    if(word.length() <= 2 ){//If is a short word (rt, fw, the, etc)
                        continue;
                    }
                    boolean isStopWord=true;
                    if(lang.equals("es") && !Arrays.asList(SWBSocialUtil.Classifier.getSpanishStopWords()).contains(word)){
                        isStopWord=false;
                    }else if(lang.equals("en") && !listEnStopWords.contains(word)){
                        isStopWord=false;
                    }
                    //System.out.println("Tag lang:"+lang+", word:"+word+",iscontained:"+iscontained+",isStopWord:"+isStopWord);
                    if(!isStopWord && !isInteger(word))
                    {
                        Integer frequency = cloudTags.get(word);
                        if(frequency == null){//New word
                            cloudTags.put(word, 1);
                        }else if(frequency > 0){//Repeated word
                            cloudTags.put(word, ++frequency);
                        }
                    }
                }                
           }
       }
    }    
    /* 
    Iterator<String> itList=listEnStopWords.iterator();
    while(itList.hasNext())
    {
        String xword=itList.next();
        System.out.println("xword:"+xword+"Fin");
    }*/
%>
<div id="<%=suri%>/<%=randomId%>/CanvasContainer" align="center">
	<canvas width="500" height="500" id="<%=suri%>/<%=randomId%>/Canvas">
	</canvas>
</div>

<div hidden="true">
    <ul id="tags<%=randomId%>">
<%
try{
        Map<String, Integer> cloudTagsSorted = sortByValues(cloudTags);//Descending order
        Iterator itSorted = cloudTagsSorted.entrySet().iterator();
        int i=1;
        int mappingValue=100; //The most frequent word will have 100 as its weight
        Integer lastValue = 0;
        
        //System.out.println("Size of sorted:" + cloudTagsSorted.size());
        while(itSorted.hasNext()){            
            Map.Entry tag = (Map.Entry)itSorted.next();//entry
            //System.out.println(i + " : " + tag);

            if(tag.getKey().toString().isEmpty()){
                continue;
            }
            //System.out.println("-->" + tag.getKey());
            if(i==1){//The word at the top is the most frequent, so it must have 100                
                //out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + SWBUtils.TEXT.replaceSpecialCharacters((String)tag.getKey(), false) + "</a></li>"); 
                out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>"); 
            }else{
                if(lastValue.equals(tag.getValue())){//If several words have the same frequency, use the same mappingValue
                    out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>");
                }else{//If current word is different from the word before, use a lower value
                    mappingValue--;
                    out.println("<li><a href=\"#\" title=\"" + mappingValue + "\" data-weight=\"" + mappingValue +"\" onclick=\"submitUrl('" + renderURL.setParameter("search", (String)tag.getKey()) + "',this); return false;\">" + tag.getKey() + "</a></li>");
                }
            }
            lastValue = (Integer)tag.getValue();
            
            if(i++==50){//show only the 50 most frequent words
                break;
            }
        }
    }catch(Exception e){
        e.printStackTrace(); 
    }
    
    long estimatedTime = System.currentTimeMillis() - startTime;    
    System.out.println("Elapsed time of processing:"  +  estimatedTime);
%>
    </ul>
</div>

<!--
<div><input type="button" value="Pausar" onclick="TagCanvas.Pause('<%//=suri%>/myCanvas');"/>  
     <input type="button" value="Continuar" onclick="TagCanvas.Resume('<%//=suri%>/myCanvas');"/>
     <input type="button" value="Reload" onclick="TagCanvas.Resume('<%//=suri%>/myCanvas');"/>
</div>
-->
<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        
        try{
            TagCanvas.interval = 20;
            TagCanvas.textFont = 'Impact,Arial Black,sans-serif';
            //TagCanvas.textColour = '#00f';
            //TagCanvas.textColour = null;
            TagCanvas.textHeight = 30;
            TagCanvas.weightSizeMin=15;
            TagCanvas.weightSizeMax=40;
            TagCanvas.outlineColour = '#f00';
            TagCanvas.outlineThickness = 5;
            TagCanvas.maxSpeed = 0.04;
            TagCanvas.minBrightness = 0.1;
            TagCanvas.depth = 0.92;
            TagCanvas.pulsateTo = 0.2;
            TagCanvas.pulsateTime = 0.75;
            TagCanvas.initial = [0.1,-0.1];
            TagCanvas.decel = 0.98;
            TagCanvas.reverse = true;
            TagCanvas.hideTags = false;
            TagCanvas.shadow = '#ccf';
            TagCanvas.shadowBlur = 1;
            TagCanvas.weight = true;
            TagCanvas.weightFrom = 'data-weight';
            TagCanvas.tooltip ='native';
            var gradient = { 0: '#FF0000', // red 
                             0.25: '#FF00FF', // fucsia
                             0.50: '#FFA500', //  orange
                             0.75: '#FFFF00', // yellow
                             1: '#0000FF' // blue
   			   };
            TagCanvas.weightGradient = gradient;
            
            TagCanvas.Start('<%=suri%>/<%=randomId%>/Canvas', 'tags<%=randomId%>', {weightMode:'both'});
	}catch(e){
            document.getElementById('<%=suri%>/<%=randomId%>/CanvasContainer').style.display ='none';
	}
   </script>
</div>