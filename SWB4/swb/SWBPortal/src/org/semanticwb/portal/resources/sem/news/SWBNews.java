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
package org.semanticwb.portal.resources.sem.news;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.Device;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceCollectionCategory;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBNews.
 */
public class SWBNews extends org.semanticwb.portal.resources.sem.news.base.SWBNewsBase
{

    //private static Calendar calendar = Calendar.getInstance();
    /**
     * The Constant log.
     */
    private static final Logger log = SWBUtils.getLogger(SWBNews.class);

    /**
     * Instantiates a new sWB news.
     */
    public SWBNews()
    {
    }

    /**
     * Instantiates a new sWB news.
     *
     * @param base the base
     */
    public SWBNews(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    
    /**
     * Gets the news by month.
     *
     * @param contents the contents
     * @return the news by month
     */
    public static Map<String, List<SWBNewContent>> getNewsByMonth(List<SWBNewContent> contents)
    {
        Calendar calendar = Calendar.getInstance();
        TreeMap<String, List<SWBNewContent>> getNewsByMonth = new TreeMap<String, List<SWBNewContent>>();
        for (SWBNewContent content : contents)
        {
            if (content.getPublishDate() != null && content.getResourceBase().isValid() && content.getResourceBase().isActive() && !content.getResourceBase().isDeleted())
            {
                calendar.setTime(content.getPublishDate());
                int newmonth = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String key = newmonth + "_" + year;
                List<SWBNewContent> contentsMonth = getNewsByMonth.get(key);
                if (contentsMonth == null)
                {
                    contentsMonth = new ArrayList<SWBNewContent>();
                    getNewsByMonth.put(key, contentsMonth);
                }
                contentsMonth.add(content);
            }
        }
        return getNewsByMonth;
    }

    /**
     * Gets the years.
     *
     * @param contents the contents
     * @return the years
     */
    public static String[] getYears(List<SWBNewContent> contents)
    {
        HashSet<String> getyears = new HashSet<String>();
        Calendar calendar = Calendar.getInstance();
        for (SWBNewContent content : contents)
        {
            if (content.getPublishDate() != null && content.getResourceBase().isValid() && content.getResourceBase().isActive() && !content.getResourceBase().isDeleted())
            {
                calendar.setTime(content.getPublishDate());
                int new_year = calendar.get(Calendar.YEAR);
                getyears.add(String.valueOf(new_year));
            }
        }
        return getyears.toArray(new String[getyears.size()]);
    }

    /**
     * Checks for news.
     *
     * @param contents the contents
     * @param month the month
     * @param year the year
     * @return true, if successful
     */
    public static boolean hasNews(List<SWBNewContent> contents, int month, int year)
    {
        Calendar calendar = Calendar.getInstance();
        for (SWBNewContent content : contents)
        {
            if (content.getPublishDate() != null && content.getResourceBase().isValid() && content.getResourceBase().isActive() && !content.getResourceBase().isDeleted())
            {
                calendar.setTime(content.getPublishDate());
                if (month == calendar.get(Calendar.MONTH) && year == calendar.get(Calendar.YEAR))
                {
                    return true;
                }
            }

        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (paramRequest.getMode().equals("month"))
        {
            if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY)
            {
                doStategy(request, response, paramRequest);
            }
            else
            {
                doShowNewsByMoth(request, response, paramRequest);
            }
        }
        else if (paramRequest.getMode().equals("title"))
        {
            doTitle(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("meta"))
        {
            doMeta(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("rss"))
        {
            doRss(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("strategy"))
        {
            doStategy(request, response, paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Gets the news.
     *
     * @param uri the uri
     * @param user the user
     * @return the news
     */
    private List<SWBNewContent> getNews(String uri, User user)
    {
        List<SWBNewContent> news = new ArrayList<SWBNewContent>();
        GenericIterator<Resource> resources = null;
        if (this.getCollection() != null && this.getCollection().getResourceCollectionType().getResourceClassName().equals(SWBNewContent.class.getCanonicalName()))
        {
            if (this.getCategory() == null)
            {
                resources = this.getCollection().listResources();
            }
            else
            {
                boolean isCategoryOfCallection = false;
                GenericIterator<ResourceCollectionCategory> categories = this.getCollection().listCategories();
                while (categories.hasNext())
                {
                    ResourceCollectionCategory category = categories.next();
                    if (category.getURI().equals(this.getCategory().getURI()))
                    {
                        isCategoryOfCallection = true;
                        break;
                    }
                }
                if (isCategoryOfCallection)
                {
                    resources = this.getCategory().listResources();
                }
                else
                {
                    resources = this.getCollection().listResources();
                }
            }
        }

        if (resources != null)
        {
            while (resources.hasNext())
            {
                Resource resource = resources.next();                
                Iterator<CalendarRef> refs = resource.listCalendarRefs();
                boolean isOnSchedule = true;
                while (refs.hasNext())
                {
                    CalendarRef ref = refs.next();
                    if (ref.isValid() && ref.isActive() && ref.getCalendar().isActive() && ref.getCalendar().isValid())
                    {
                        if (!ref.getCalendar().isOnSchedule())
                        {
                            isOnSchedule = false;
                        }
                    }
                }
                Device d = resource.getDevice();
                boolean device = true;
                if (d != null)
                {
                    if (!user.hasDevice(d))
                    {
                        device = false;

                    }
                }
                
                if (device && isOnSchedule && resource.isActive() && !resource.isDeleted() && user.haveAccess(resource) && resource.isValid())
                {
                    SWBNewContent object = (SWBNewContent) resource.getResourceData().createGenericInstance();
                    if (uri == null)
                    {
                        try
                        {
                            object.setResourceBase(resource);
                            news.add(object);
                        }
                        catch (SWBResourceException e)
                        {
                            log.error(e);

                        }
                    }
                    else
                    {
                        if (uri.equals(resource.getURI()) || uri.equals(resource.getId()))
                        {

                            try
                            {
                                object.setResourceBase(resource);
                                news.add(object);
                            }
                            catch (SWBResourceException e)
                            {
                                log.error(e);

                            }
                        }
                    }

                }
            }
        }
        return news;
    }

    /**
     * Do show news by moth.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doShowNewsByMoth(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        if (request.getParameter("month") != null)
        {

            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (request.getParameter("year") != null)
            {
                year = Integer.parseInt(request.getParameter("year"));
            }
            try
            {
                int month = Integer.parseInt(request.getParameter("month"));
                String key = month + "_" + year;
                if (month >= 0 && month <= 12)
                {
                    List<SWBNewContent> news = getNews(null, paramRequest.getUser());
                    news = getNewsByMonth(news).get(key);
                    String path = basePath + "newsByMonth.jsp";
                    RequestDispatcher dis = request.getRequestDispatcher(path);
                    try
                    {
                        request.setAttribute("paramRequest", paramRequest);
                        request.setAttribute("news", news);
                        request.setAttribute("this", this);
                        dis.include(request, response);
                    }
                    catch (IOException e)
                    {
                        log.error(e);
                    }
                    catch (ServletException e)
                    {
                        log.error(e);
                    }
                    return;
                }
            }
            catch (NumberFormatException nfe)
            {
                log.error(nfe);
            }
        }
        String languser = "es";
        if (paramRequest.getUser().getLanguage() != null)
        {
            languser = paramRequest.getUser().getLanguage();
        }
        Locale userlocale = new Locale(languser);
        DateFormatSymbols ds = new DateFormatSymbols(userlocale);
        String uri = request.getRequestURI();
        StringTokenizer st = new StringTokenizer(uri, "/");
        if (st.countTokens() == 9)
        {
            ArrayList<String> values = new ArrayList<String>();
            while (st.hasMoreTokens())
            {
                String data = st.nextToken();
                values.add(data);
            }
            String s_month = values.get(7);
            String s_year = values.get(8);
            try
            {
                int iYear = Integer.parseInt(s_year);
                int iMonth = -1;
                for (String month : ds.getMonths())
                {
                    iMonth++;
                    if (month.equalsIgnoreCase(s_month))
                    {
                        String key = iMonth + "_" + iYear;
                        if (iMonth >= 0 && iMonth <= 12)
                        {
                            List<SWBNewContent> news = getNews(null, paramRequest.getUser());
                            news = getNewsByMonth(news).get(key);
                            String path = basePath + "newsByMonth.jsp";
                            RequestDispatcher dis = request.getRequestDispatcher(path);
                            try
                            {
                                request.setAttribute("paramRequest", paramRequest);
                                request.setAttribute("news", news);
                                request.setAttribute("this", this);
                                dis.include(request, response);
                            }
                            catch (IOException e)
                            {
                                log.error(e);
                            }
                            catch (ServletException e)
                            {
                                log.error(e);
                            }
                            return;
                        }
                        break;
                    }
                }

            }
            catch (NumberFormatException nfe)
            {
            }
        }
        doView(request, response, paramRequest);
    }

    public void doTitle(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        List<SWBNewContent> news = getNews(null, paramRequest.getUser());
        String path = basePath + "title.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        String url = paramRequest.getWebPage().getUrl();
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("news", news);
            request.setAttribute("url", url);
            request.setAttribute("this", this);
            dis.include(request, response);
        }
        catch (IOException e)
        {
            log.error(e);
        }
        catch (ServletException e)
        {
            log.error(e);
        }
    }

    public void doMeta(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        List<SWBNewContent> news = getNews(null, paramRequest.getUser());
        String path = basePath + "meta.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        String url = paramRequest.getWebPage().getUrl();
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("news", news);
            request.setAttribute("url", url);
            request.setAttribute("this", this);
            dis.include(request, response);
        }
        catch (IOException e)
        {
            log.error(e);
        }
        catch (ServletException e)
        {
            log.error(e);
        }
    }

    /**
     * Do rss.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRss(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        List<SWBNewContent> news = getNews(null, paramRequest.getUser());
        String path = basePath + "rss.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        /*SWBResourceURL url=paramRequest.getRenderUrl();
         url.setCallMethod(url.Call_CONTENT);
         url.setMode(url.Mode_VIEW);*/
        String url = paramRequest.getWebPage().getUrl();
        try
        {
            response.setContentType("application/rss+xml;charset=UTF-8");
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("news", news);
            request.setAttribute("url", url);
            request.setAttribute("this", this);
            dis.include(request, response);
        }
        catch (IOException e)
        {
            log.error(e);
        }
        catch (ServletException e)
        {
            log.error(e);
        }
    }

    public void doStategy(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        String path = basePath + "strategy.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }
        catch (IOException e)
        {
            log.error(e);
        }
        catch (ServletException e)
        {
            log.error(e);
        }
    }

    @Override
    public String getResourceCacheID(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException
    {
        Resource base = paramRequest.getResourceBase();
        String uri = request.getParameter("uri");

        if (uri != null && !uri.startsWith("http:"))
        {
            String uriSite = paramRequest.getWebPage().getWebSite().getSemanticObject().getURI();
            int pos = uriSite.indexOf("#");
            if (pos != -1)
            {
                uriSite = uriSite.substring(0, pos);
            }
            uri = uriSite + "/Resource#" + uri;
        }

        if (uri != null)
        {
            uri = uri.trim();
            if (uri.equals(""))
            {
                uri = null;
            }
        }

        if (uri == null)
        {
            StringTokenizer st = new StringTokenizer(request.getRequestURI(), "/");
            if (st.countTokens() >= 4)
            {
                ArrayList<String> values = new ArrayList<String>();
                while (st.hasMoreTokens())
                {
                    values.add(st.nextToken());
                }
                uri = values.get(3);
                if (uri.startsWith("_"))
                {
                    uri = null;
                }
                if ("_rid".equals(uri) && values.size() >= 6)
                {
                    uri = values.get(5);
                }
            }
        }
        if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY)
        {
            String key = SWBResourceCachedMgr.getKey(base);
            key = "strategy_" + key;
            return key;

        }
        else if (uri != null && paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT)
        {
            String key = SWBResourceCachedMgr.getKey(base);
            key = "content_" + uri + "_" + key;
            return key;
        }
        else
        {
            return super.getResourceCacheID(request, paramRequest);
        }
    }


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        String path = basePath + "content.jsp";

        if (paramRequest.getArgument("mode") != null && "rss".equals(paramRequest.getArgument("mode")))
        {
            doRss(request, response, paramRequest);
            return;
        }
        if (paramRequest.getArgument("mode") != null && "title".equals(paramRequest.getArgument("mode")))
        {
            doTitle(request, response, paramRequest);
            return;
        }

        String uri = request.getParameter("uri");

        if (uri != null && !uri.startsWith("http:"))
        {
            String uriSite = paramRequest.getWebPage().getWebSite().getSemanticObject().getURI();
            int pos = uriSite.indexOf("#");
            if (pos != -1)
            {
                uriSite = uriSite.substring(0, pos);
            }
            uri = uriSite + "/Resource#" + uri;
        }

        if (uri != null)
        {
            uri = uri.trim();
            if (uri.equals(""))
            {
                uri = null;
            }
        }

        if (uri == null)
        {
            StringTokenizer st = new StringTokenizer(request.getRequestURI(), "/");
            if (st.countTokens() >= 4)
            {
                ArrayList<String> values = new ArrayList<String>();
                while (st.hasMoreTokens())
                {
                    values.add(st.nextToken());
                }
                uri = values.get(3);
                if (uri.startsWith("_"))
                {
                    uri = null;
                }
                if ("_rid".equals(uri) && values.size() >= 6)
                {
                    uri = values.get(5);
                }
            }
        }
        if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY)
        {
            path = basePath + "strategy.jsp";
            uri = null;
        }

        List<SWBNewContent> news = getNews(uri, paramRequest.getUser());
        if (uri != null && paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT)
        {
            // busca el objeto
            for (SWBNewContent content : news)
            {
                if (content.getResourceBase().getURI().equals(uri) || content.getResourceBase().getId().equals(uri))
                {
                    if (content.getResourceBase().isValid() && paramRequest.getUser().haveAccess(content.getResourceBase()))
                    {
                        path = basePath + "shownew.jsp";
                        RequestDispatcher dis = request.getRequestDispatcher(path);
                        try
                        {
                            request.setAttribute("paramRequest", paramRequest);
                            request.setAttribute("content", content);
                            request.setAttribute("this", this);
                            dis.include(request, response);
                        }
                        catch (IOException e)
                        {
                            log.error(e);
                        }
                        catch (ServletException e)
                        {
                            log.error(e);
                        }
                        return;
                    }
                }
            }
        }
        // ordena por fecha

        SortNews sort = new SortNews();
        Collections.sort(news, sort);

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("news", news);
            request.setAttribute("this", this);
            dis.include(request, response);
        }
        catch (IOException e)
        {
            log.error(e);
        }
        catch (ServletException e)
        {
            log.error(e);
        }
    }
}

class SortNews implements java.util.Comparator<SWBNewContent>
{

    @Override
    public int compare(SWBNewContent o1, SWBNewContent o2)
    {
        if (o1.getPublishDate() != null && o2.getPublishDate() != null)
        {
            return o2.getPublishDate().compareTo(o1.getPublishDate());
        }
        return 0;
    }
}
