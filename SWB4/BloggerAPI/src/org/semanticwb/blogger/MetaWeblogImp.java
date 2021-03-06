/**
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
**/ 
package org.semanticwb.blogger;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.XSLTransformer;
import org.semantic.blogger.interfaces.CategoryInfo;
import org.semantic.blogger.interfaces.MetaWeblog;
import org.semantic.blogger.interfaces.Post;
import org.semantic.blogger.interfaces.UserBlog;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.repository.RepositoryManagerLoader;


/**
 *
 * @author victor.lorenzana
 */
public final class MetaWeblogImp implements MetaWeblog
{
    static Logger log = SWBUtils.getLogger(MetaWeblogImp.class);
    private static final String BLOG_TYPE_NAME = "Blog";
    private static final String BLOG_POST_NAME = "Post";    
    private static final String BLOG_MODEL_PREFIX = "blognode";
    private static final String BLOG_OWNER_PROPERTY = BLOG_MODEL_PREFIX + ":owner";
    private static final String BLOG_DESCRIPTION = BLOG_MODEL_PREFIX + ":description";
    private static final String BLOG_CATEGORY_PROPERTY = BLOG_MODEL_PREFIX + ":category";
    private static final String BLOG_NAME_PROPERTY = BLOG_MODEL_PREFIX + ":name";
    private static final String BLOG_TITLE_PROPERTY = BLOG_MODEL_PREFIX + ":title";
    private static final String BLOG_URL_PROPERTY = BLOG_MODEL_PREFIX + ":url";
    private static final String BLOG_USERID_PROPERTY = BLOG_MODEL_PREFIX + ":userid";
    private static final String BLOG_RSSURL_PROPERTY = BLOG_MODEL_PREFIX + ":rssUrl";
    private static final String BLOG_DATE_CREATED_PROPERTY = BLOG_MODEL_PREFIX + ":dateCreated";
    private static final String BLOG_HTMLURL_PROPERTY = BLOG_MODEL_PREFIX + ":htmlUrl";
    private static final String ID_SEPARATOR = ":";
    private static final String SPARQL = "SPARQL";
    private static final RepositoryManagerLoader loader=RepositoryManagerLoader.getInstance();
    static
    {
        /*try
        {
            loader.addNamespace(BLOG_MODEL_PREFIX, new URI(BLOG_MODEL_URI));
        }
        catch ( Exception e )
        {
            e.printStackTrace(System.out);
        }
        try
        {
            loader.addNamespace(BLOG_PREFIX, new URI(BLOG_URI));
        }
        catch ( Exception e )
        {
            e.printStackTrace(System.out);
        }*/
    }

    /**
     * Gets the Id for a blogId or postID
     * @param key The key for a blogId or postID
     * @return The Id for the post or blog
     * @throws java.lang.Exception
     */
    private static String getID(String key) throws Exception
    {
        String[] values = key.split(ID_SEPARATOR);
        if ( values.length == 2 )
        {
            return values[1];
        }
        else
        {
            throw new Exception("The format for the blogID or postID is invalid");
        }
    }

    /**
     * Gets the Repository Name inside the key
     * @param key Key of the blog or post
     * @return The RepositoryName
     * @throws java.lang.Exception
     */
    private static String getRepositoryName(String key) throws Exception
    {
        String[] values = key.split(ID_SEPARATOR);
        if ( values.length == 2 )
        {
            return values[0];
        }
        else
        {
            throw new Exception("The format for the blogID or postID is invalid");
        }
    }

    /**
     * Remove a post into a repository
     * @param postid The Id of the post
     * @param username The userName to access to the repository
     * @param password The password to access to the repository
     * @throws java.lang.Exception
     */
    public void removePost(String postid, String username, String password) throws Exception
    {
        if(!isAuthenticate(username, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        Session session = null;
        try
        {
            String repositoryName = getRepositoryName(postid);
            postid = getID(postid);
            session = loader.openSession(repositoryName, username, password);
            Node nodePost = session.getNodeByUUID(postid);
            nodePost.remove();
            session.save();
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * Remove a Blog
     * @param blogid The blogID
     * @param username The username to access to the repository
     * @param password The password to access to the repository
     * @throws java.lang.Exception
     */
    public void removeBlog(String blogid, String username, String password) throws Exception
    {
        if(!isAuthenticate(username, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        Session session = null;
        try
        {
            String repositoryName = getRepositoryName(blogid);
            blogid = getID(blogid);
            session = loader.openSession(repositoryName, username, password);
            Node nodeBlog = session.getNodeByUUID(blogid);
            if ( nodeBlog.hasNode("post") )
            {
                throw new Exception("The blog can not be removed, because the blog caotains posts");
            }
            nodeBlog.remove();
            session.save();
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * Update a Blog
     * @param repositoryName The repository of the blog
     * @param name The name of the blog
     * @param url The new Url
     * @param username The userName to access to the repository
     * @param password The password to access to the repository
     * @return The ID of the Blog
     * @throws java.lang.Exception
     */
    public String updateBlog(String repositoryName, String name, String description, String url, String username, String password) throws Exception
    {
        if(!isAuthenticate(username, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        return createBlog(repositoryName, name, description, url, username, password);
    }

    private User getUser(String login)
    {
        UserRepository ur = SWBContext.getAdminWebSite().getUserRepository();
        return ur.getUserByLogin(login);
    }
    private boolean isAuthenticate(String pUserName, String pPassword)
    {
        UserRepository ur = SWBContext.getAdminWebSite().getUserRepository();
        String context = ur.getLoginContext();
        Subject subject = new Subject();
        LoginContext lc;
        try
        {
            SWB4CallbackHandlerBlogger callbackHandler = new SWB4CallbackHandlerBlogger(pUserName, pPassword);
            lc = new LoginContext(context, subject, callbackHandler);
            lc.login();
            return true;
        }
        catch (Exception e)
        {
            log.debug("Can't log User", e);
        }
        return false;

    }
    /**
     * Create a blog into the repository
     * @param repositoryName Name of repository to create the blog
     * @param name Name of the blog
     * @param url Url of the blog
     * @param username User name to create the blog into de repository
     * @param password Password for the user to create the blog into the repository
     * @return The UUID created
     * @throws java.lang.Exception
     */
    public String createBlog(String repositoryName, String name, String description, String url, String username, String password) throws Exception
    {
        if(!isAuthenticate(username, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        Session session = null;
        if ( repositoryName.indexOf(ID_SEPARATOR) == -1 )
        {
            throw new Exception("The name of the repository contains the character '" + ID_SEPARATOR + "' , and is not posible to use this repository to create a blog");
        }
        try
        {
            session = loader.openSession(repositoryName, username, password);
            Query query = null; 
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                query = session.getWorkspace().getQueryManager().createQuery("SELECT DISTINCT ?x WHERE {?x " + BLOG_NAME_PROPERTY + " ?name FILTER (?name=\"" + name + "\").  and rdf:type "+ BLOG_MODEL_PREFIX+":"+BLOG_TYPE_NAME +". }", "SPARQL");
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//blog[@"+ BLOG_NAME_PROPERTY +"='" + name + "']", Query.XPATH);
            }
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            Node blogNode = null;
            if ( nodeIterator.hasNext() )
            {
                blogNode = nodeIterator.nextNode();
                blogNode.setProperty(BLOG_NAME_PROPERTY, name);
                blogNode.setProperty(BLOG_URL_PROPERTY, url);
                blogNode.setProperty(BLOG_DESCRIPTION, description);
            }
            else
            {
                Node root = session.getRootNode();
                blogNode = root.addNode("blog", BLOG_MODEL_PREFIX+":"+BLOG_TYPE_NAME);
                blogNode.setProperty(BLOG_NAME_PROPERTY, name);
                blogNode.setProperty(BLOG_URL_PROPERTY, url);
                session.save();
            }
            return blogNode.getUUID();
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * 
     * Returns the blogs that an user can publish posts
     * @param appkey The appkey is not used
     * @param username The UserName to access to the repository
     * @param password The password to access to the repository
     * @return An array of blogs that the user can publish
     * @see UserBlog
     */
    public UserBlog[] getUsersBlogs(String appkey, String username, String password) throws Exception
    {
        if(!isAuthenticate(username, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        User user=getUser(username);
        HashSet<UserBlog> blogs = new HashSet<UserBlog>();
        Session session = null;
        for ( String repositoryName : loader.getWorkspaces() )
        {
            try
            {
                session = loader.openSession(repositoryName, username, password);
                Query query =null;
                if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
                {
                    query = session.getWorkspace().getQueryManager().createQuery("SELECT DISTINCT ?x WHERE {?x " + BLOG_OWNER_PROPERTY + " ?owner FILTER (?owner=\"" + user.getLogin() + "\").  and rdf:type "+ BLOG_MODEL_PREFIX+":"+BLOG_TYPE_NAME +". }", "SPARQL");
                }
                else
                {
                    query = session.getWorkspace().getQueryManager().createQuery("//blog["+ BLOG_OWNER_PROPERTY +"]='"+ user.getLogin() +"'", Query.XPATH);
                }
                
                QueryResult result = query.execute();
                NodeIterator nodeIterator = result.getNodes();
                while (nodeIterator.hasNext())
                {
                    UserBlog userblog = new UserBlog();
                    Node nodeBlog = nodeIterator.nextNode();
                    userblog.blogName = nodeBlog.getProperty(BLOG_NAME_PROPERTY).getString();
                    userblog.blogid = repositoryName + ID_SEPARATOR + nodeBlog.getUUID();
                    userblog.isAdmin = false;
                    userblog.url = nodeBlog.getProperty(BLOG_URL_PROPERTY).getString();
                    blogs.add(userblog);
                }
            }
            catch ( Exception ex )
            {
                throw ex;
            }
            finally
            {
                if ( session != null )
                {
                    session.logout();
                }
            }
        }
        return blogs.toArray(new UserBlog[blogs.size()]);
    }

    /**
     * Retrieves a list of posts that were created recently. The results are returned in descending chronolocial order with the most recent post first in the list.
     * @param blogid the Blog ID
     * @param username The user name
     * @param password The password
     * @param numberOfPosts Number of máx posts to retrive
     * @return The list of recently posts
     * @throws java.lang.Exception
     */
    public Post[] getRecentPosts(String blogid, String username, String password, int numberOfPosts) throws Exception
    {
        if(!isAuthenticate(username, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        ArrayList<Post> posts = new ArrayList<Post>();
        Session session = null;
        try
        {
            String repositoryName = getRepositoryName(blogid);
            blogid = getID(blogid);
            session = loader.openSession(repositoryName, username, password);
            Node blogNode = session.getNodeByUUID(blogid);
            NodeIterator postNodes = blogNode.getNodes("post");
            while (postNodes.hasNext())
            {
                Node postNode = postNodes.nextNode();
                Post value = new Post();
                ArrayList<String> categories = new ArrayList<String>();
                if ( postNode.hasProperty(BLOG_CATEGORY_PROPERTY) )
                {
                    for ( Value categoryValue : postNode.getProperty(BLOG_CATEGORY_PROPERTY).getValues() )
                    {
                        categories.add(categoryValue.getString());
                    }
                }
                value.categories = categories.toArray(new String[categories.size()]);
                value.dateCreated = postNode.getProperty(BLOG_DATE_CREATED_PROPERTY).getDate().getTime();
                value.description = postNode.getProperty(BLOG_DESCRIPTION).getString();
                value.postid = repositoryName + ID_SEPARATOR + postNode.getUUID();
                value.title = postNode.getProperty(BLOG_TITLE_PROPERTY).getString();
                value.userid = postNode.getProperty(BLOG_USERID_PROPERTY).getString();
                posts.add(value);
            }
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
        Post[] postsToReturn = posts.toArray(new Post[posts.size()]);
        Arrays.sort(postsToReturn);
        if ( postsToReturn.length > numberOfPosts )
        {
            Post[] temp = new Post[numberOfPosts];
            System.arraycopy(postsToReturn, 0, temp, 0, numberOfPosts);
            postsToReturn = temp;
        }
        return postsToReturn;
    }

    /**
     * Gets the URI to show the categories using Html
     * @param blogNode The Node of the blog, that contains the categories
     * @return A URI to show the categories, using Html
     * @throws RepositoryException
     */
    private String getHtmlUrlForCategories(Node blogNode) throws RepositoryException
    {
        String url = blogNode.getProperty(BLOG_URL_PROPERTY).getString();
        url += "/mode/categorieshtml";
        return url;
    }

    /**
     * Gets the URI to access to the categories using RSS
     * @param blogNode The Node of the blog, that contains the categories
     * @return A URI to show the categories, using RSS
     * @throws RepositoryException
     */
    private String getRssUrlForCategories(Node blogNode) throws RepositoryException
    {
        String url = blogNode.getProperty(BLOG_URL_PROPERTY).getString();
        url += "/mode/categoriesrss";
        return url;
    }
    /**
     * Shows the post into a category using Html format
     * @param blogid The blog ID, The blog ID must contain the repositoryName as part the ID
     * @param categoryName The categoryName to show
     * @param xslt The Document Template in Xslt format to create the html
     * @param response The response
     * @param userid The userID to access to repository
     * @param password The password to access to repository
     * @throws java.lang.Exception
     */
    
    public void showCategoriesAsHtml(String blogid, String categoryName,Document xslt, HttpServletResponse response, String userid, String password) throws Exception
    {
        if(!isAuthenticate(userid, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        OutputStream out=response.getOutputStream();
        response.setContentType("text/html");
        Document document = getCategoriesAsRss(blogid, categoryName, userid, password);        
        XSLTransformer transformer=new XSLTransformer(xslt);
        Document transformed=transformer.transform(document);
        XMLOutputter xMLOutputter = new XMLOutputter();
        xMLOutputter.output(transformed, out);
    }
    /**
     * Shows the post into a category using Rss format
     * @param blogid The blog ID, The blog ID must contain the repositoryName as part the ID
     * @param categoryName The categoryName to show
     * @param response The response
     * @param userid The userID to access to repository
     * @param password The password to access to repository
     * @throws java.lang.Exception
     */

    public void showCategoriesAsRss(String blogid, String categoryName, HttpServletResponse response, String userid, String password) throws Exception
    {
        if(!isAuthenticate(userid, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        OutputStream out=response.getOutputStream();
        response.setContentType("text/xml");
        Document document = getCategoriesAsRss(blogid, categoryName, userid, password);
        XMLOutputter xMLOutputter = new XMLOutputter();
        xMLOutputter.output(document, out);
    }
    

    private Document getCategoriesAsRss(String blogid, String categoryName, String userid, String password) throws Exception
    {
        if(!isAuthenticate(userid, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        Document document = new Document();
        Element rss = new Element("rss");
        rss.setAttribute("version", "2.0");
        Element chanel = new Element("chanel");
        document.addContent(rss);
        rss.addContent(chanel);
        Session session = null;
        try
        {
            String repositoryName = getRepositoryName(blogid);
            blogid = getID(blogid);
            session = loader.openSession(repositoryName, userid, password);
            Node blogNode = session.getNodeByUUID(blogid);
            {
                Element title = new Element("title");
                Element link = new Element("link");
                Element description = new Element("description");

                title.setText(categoryName);
                description.setText(blogNode.getProperty(BLOG_DESCRIPTION).getString());
                link.setText(blogNode.getProperty(BLOG_URL_PROPERTY).getString());
                chanel.addContent(title);
                chanel.addContent(link);
                chanel.addContent(description);
            }Query query = null;
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                query = session.getWorkspace().getQueryManager().createQuery("/blog/post[@"+BLOG_MODEL_PREFIX+":category='" + categoryName + "']",SPARQL);
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("/blog/post[@"+BLOG_MODEL_PREFIX+":category='" + categoryName + "']", Query.XPATH);
            }
            QueryResult queryResult = query.execute();
            NodeIterator nodes = queryResult.getNodes();
            while (nodes.hasNext())
            {
                Node nodePost = nodes.nextNode();
                if ( nodePost.getParent().getUUID().equals(blogid) )
                {
                    Element item = new Element("item");
                    Element title = new Element("title");
                    Element link = new Element("link");
                    Element pubDate = new Element("pubDate");
                    Element description = new Element("description");
                    Element category = new Element("category");

                    title.setText(nodePost.getProperty(BLOG_TITLE_PROPERTY).getString());
                    description.setText(nodePost.getProperty(BLOG_DESCRIPTION).getString());
                    link.setText(nodePost.getParent().getProperty(BLOG_URL_PROPERTY).getString());
                    pubDate.setText(nodePost.getProperty(BLOG_DATE_CREATED_PROPERTY).getString());
                    category.setText(categoryName);

                    chanel.addContent(item);
                    item.addContent(title);
                    item.addContent(link);
                    item.addContent(description);
                    item.addContent(pubDate);
                }
            }
            return document;

        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }

    }

    /**
     * Edit a post sent to the server
     * @param postid The post to be updated
     * @param userid The user ID that is updating the post
     * @param password The password of the user
     * @param post The new version of the post
     * @param publish If the post can be published
     * @return True if the pos was updated
     * @throws java.lang.Exception
     */
    public boolean editPost(String postid, String userid, String password, Post post, boolean publish) throws Exception
    {
        if(!isAuthenticate(userid, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        Session session = null;
        try
        {
            String repositoryName = getRepositoryName(postid);
            postid = getID(postid);
            session = loader.openSession(repositoryName, userid, password);
            Node postNode = session.getNodeByUUID(postid);
            postNode.checkout();
            postNode.setProperty(BLOG_TITLE_PROPERTY, post.title);
            if ( post.userid != null )
            {
                postNode.setProperty(BLOG_USERID_PROPERTY, post.userid);
            }
            else
            {
                postNode.setProperty(BLOG_USERID_PROPERTY, userid);
            }
            postNode.setProperty(BLOG_DESCRIPTION, post.description);
            if ( post.categories != null )
            {
                postNode.setProperty(BLOG_CATEGORY_PROPERTY, post.categories);
                for ( String category : post.categories )
                {
                    createCategory(repositoryName, postNode.getParent().getUUID(), category, category, userid, password);
                }
            }
            session.save();
            postNode.checkin();
            return true;
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * Gets a post in the repository
     * @param postid ID of the post
     * @param userid User ID to access to the repository
     * @param password Password of the user
     * @return Th
     * @throws java.lang.Exception
     */
    public Post getPost(String postid, String userid, String password) throws Exception
    {
        if(!isAuthenticate(userid, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        Session session = null;
        try
        {
            String repositoryName = getRepositoryName(userid);
            postid = getID(postid);
            session = loader.openSession(repositoryName, userid, password);
            Node postNode = session.getNodeByUUID(postid);
            Post value = new Post();
            ArrayList<String> categories = new ArrayList<String>();
            if ( postNode.hasProperty(BLOG_CATEGORY_PROPERTY) )
            {
                for ( Value categoryValue : postNode.getProperty(BLOG_CATEGORY_PROPERTY).getValues() )
                {
                    categories.add(categoryValue.getString());
                }
            }
            value.categories = categories.toArray(new String[categories.size()]);
            value.dateCreated = postNode.getProperty(BLOG_DATE_CREATED_PROPERTY).getDate().getTime();
            value.description = postNode.getProperty(BLOG_DESCRIPTION).getString();
            value.postid = postid;
            value.title = postNode.getProperty(BLOG_TITLE_PROPERTY).getString();
            value.userid = postNode.getProperty(BLOG_USERID_PROPERTY).getString();
            return value;
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * Create a new post into a blog
     * @param blogid The blog ID
     * @param userid The userID
     * @param password The password
     * @param post The post information
     * @param publish If the post must be published
     * @return The Post ID
     * @throws java.lang.Exception
     * @see Post
     */
    public String newPost(String blogid, String userid, String password, Post post, boolean publish) throws Exception
    {
        if(!isAuthenticate(userid, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        Session session = null;
        try
        {
            String repositoryName = getRepositoryName(blogid);
            blogid = getID(blogid);
            session = loader.openSession(repositoryName, userid, password);
            Node blogNode = session.getNodeByUUID(blogid);
            Node postNode = blogNode.addNode(BLOG_MODEL_PREFIX+":"+BLOG_POST_NAME);
            postNode.setProperty(BLOG_TITLE_PROPERTY, post.title);
            if ( post.userid != null )
            {
                postNode.setProperty(BLOG_USERID_PROPERTY, post.userid);
            }
            else
            {
                postNode.setProperty(BLOG_USERID_PROPERTY, userid);
            }
            postNode.setProperty(BLOG_DESCRIPTION, post.description);
            postNode.setProperty(BLOG_DATE_CREATED_PROPERTY, Calendar.getInstance());
            if ( post.categories != null )
            {

                Value[] categories=new Value[post.categories.length];
                int i=0;
                for(String categoryName : post.categories)
                {
                    Node nodeCategory=null;
                    Query query =null;
                    if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
                    {
                        query=session.getWorkspace().getQueryManager().createQuery("//blog/category["+BLOG_NAME_PROPERTY+"='" + categoryName + "']", SPARQL);
                    }
                    else
                    {
                        query=session.getWorkspace().getQueryManager().createQuery("//blog/category["+BLOG_NAME_PROPERTY+"='" + categoryName + "']", Query.XPATH);
                    }
                    NodeIterator nodeit=query.execute().getNodes();
                    if(nodeit.hasNext())
                    {
                        nodeCategory=nodeit.nextNode();
                    }
                    if(nodeCategory==null)
                    {
                        createCategory(repositoryName, blogid, categoryName, "", userid, password);
                    }
                    categories[i]=session.getValueFactory().createValue(nodeCategory);
                    i++;
                }
                postNode.setProperty(BLOG_CATEGORY_PROPERTY, categories);                
            }
            session.save();
            return repositoryName + ID_SEPARATOR + postNode.getUUID();
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * Create a Category into the blog repository
     * @param blogid The blog Id where the category is going to be created
     * @param name Name of the categoey
     * @param description The description of the category
     * @param username The UserName to access to the repository
     * @param password The Password to access to the repository
     * @return A CategoryInfo with the information created
     * @throws java.lang.Exception
     */
    public CategoryInfo createCategory(String repositoryName, String blogid, String name, String description, String username, String password) throws Exception
    {
        if(!isAuthenticate(username, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        Session session = null;
        try
        {

            session = loader.openSession(repositoryName, username, password);
            Query query = null;
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                query = session.getWorkspace().getQueryManager().createQuery("SELECT DISTINCT ?x WHERE {?x " + BLOG_NAME_PROPERTY + " ?name FILTER (?name=\"" + name + "\").  and rdf:type "+ BLOG_MODEL_PREFIX+":"+"Category" +". }",SPARQL);
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//blog/category["+BLOG_NAME_PROPERTY+"='" + name + "']", Query.XPATH);
            }
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            CategoryInfo category = new CategoryInfo();
            Node categoryNode = null;
            if ( nodeIterator.hasNext() )
            {
                categoryNode = nodeIterator.nextNode();
            }
            else
            {
                Node nodeBlog = session.getNodeByUUID(blogid);
                categoryNode = nodeBlog.addNode("category");
                categoryNode.setProperty(BLOG_NAME_PROPERTY, name);
                categoryNode.setProperty(BLOG_DESCRIPTION, description);
                categoryNode.setProperty(BLOG_HTMLURL_PROPERTY, getHtmlUrlForCategories(nodeBlog));
                categoryNode.setProperty(BLOG_RSSURL_PROPERTY, getRssUrlForCategories(nodeBlog));
                session.save();
            }
            category.categoryId = repositoryName + ID_SEPARATOR + categoryNode.getUUID();
            category.categoryName = categoryNode.getProperty(BLOG_NAME_PROPERTY).getString();
            category.description = categoryNode.getProperty(BLOG_DESCRIPTION).getString();
            category.htmlUrl = categoryNode.getProperty(BLOG_HTMLURL_PROPERTY).getString();
            category.rssUrl = categoryNode.getProperty(BLOG_RSSURL_PROPERTY).getString();
            return category;
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * Gets the categories created into the blog repository
     * @param blogid The BlogID of the repository
     * @param username The userName to access to repository
     * @param password The password of the user to access to the repository
     * @return An array of CategoryInfo, with the information about of the Categories
     * @throws java.lang.Exception
     */
    public CategoryInfo[] getCategories(String blogid, String username, String password) throws Exception
    {
        if(!isAuthenticate(username, password))
        {
            throw new Exception("The user can not be authenticated");
        }
        ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
        Session session = null;
        try
        {
            String repositoryName = getRepositoryName(blogid);
            blogid = getID(blogid);
            session = loader.openSession(repositoryName, username, password);
            Query query = null;
            if (session.getRepository().getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {
                query = session.getWorkspace().getQueryManager().createQuery("//blog/category", SPARQL);
            }
            else
            {
                query = session.getWorkspace().getQueryManager().createQuery("//blog/category", Query.XPATH);
            }
            
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node categoryNode = nodeIterator.nextNode();
                CategoryInfo category = new CategoryInfo();
                category.categoryId = repositoryName + ID_SEPARATOR + categoryNode.getUUID();
                category.categoryName = categoryNode.getProperty(BLOG_NAME_PROPERTY).getString();
                category.description = categoryNode.getProperty(BLOG_DESCRIPTION).getString();
                category.htmlUrl = categoryNode.getProperty(BLOG_HTMLURL_PROPERTY).getString();
                category.rssUrl = categoryNode.getProperty(BLOG_RSSURL_PROPERTY).getString();
                categories.add(category);
            }

        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
        return categories.toArray(new CategoryInfo[categories.size()]);
    }
}
