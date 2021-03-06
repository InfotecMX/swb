/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.social.Childrenable;
import org.semanticwb.social.SocialAdmin;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.TreeNodePage;
import org.semanticwb.social.components.tree.AdvancedTreeModel;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.components.tree.Element;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.event.TreeDataEvent;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */

/*
 * Clase de utilerías para los recursos de swbsocial
 */
public class SWBSocialResourceUtils {

    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_DOUBLECLICK = "doubleClick";
    public static final String ACTION_REMOVE = "remove";
    public static final String ACTION2= "updateTree";
    private static String SOCIALADMINID="swbsocial";
    
    /**
     * Holds a reference to a log utility.
     * <p>Mantiene una referencia a la utiler&iacute;a de generaci&oacute;n de bit&aacute;coras.</p>
     */
    private static Logger log = SWBUtils.getLogger(SWBSocialResourceUtils.class);
    /**
     * Holds a reference to an object of this class.
     * <p>Mantiene una referencia a un objeto de esta clase.</p>
     */
    static private SWBSocialResourceUtils instance;
    static ArrayList<String> aDoubles = new ArrayList();
    static HashMap<String, String> hmapChanges = new HashMap();

    
     /**
     * Creates a new object of this class.
     */
    public SWBSocialResourceUtils() {
        
    }
   
    /**
     * Retrieves a reference to the only one existing object of this class.
     * <p>Obtiene una referencia al &uacute;nico objeto existente de esta clase.</p>
     * @param applicationPath a string representing the path for this application
     * @return a reference to the only one existing object of this class
     */
    
    static public synchronized SWBSocialResourceUtils createInstance() {
        System.out.println("Entra a SWBSocialResourceUtils/createInstance");
        if (instance == null) {
            instance = new SWBSocialResourceUtils();
        }
         
        return instance;
    }
    
    
    
    //PARA EL USO DESDE LOS RECURSOS DE SWB
    
    public static class Resources
    {
        
        
         /*
         * Metodo cuya funcionalidad es la de actualizar un nodo del árbol,
         * basicamente se actualiza solamente en su título, para que este se
         * vea reflejado en el mismo árbol
         * Este metodo es utilizado desde el IndexAdm.zul o desde cualquier archivo .zul
         * @param item ElementTreeNode a actualizar
         * @param title String a colocar en el item a actualizar
         */
        /**
        public static void updateTreeTitleNode(ElementTreeNode item, String title) {
             Element element=item.getData();
             element.setName(title);
             item.setData(element);
        } 
        **/
        
         /**
         * Metodo que busca un nodo en el modelo del árbol de navegación
         * @param request Este objeto debe contener como parametros:itemUri - Uri del nodo del árbol y wsite - sitio de tipo social site en donde se encuentra contenido el nodo a buscar
         * @return nodo encontrado o nulo si no lo encuentra
         */
        public static ElementTreeNode getComponentbyUri(HttpServletRequest request)
        {
            try
            {
                if(request.getParameter("itemUri")!=null)
                {
                    AdvancedTreeModel advTreeModel=(AdvancedTreeModel)request.getSession().getAttribute("elemenetTreeModel");
                    return advTreeModel.findNode(request.getParameter("itemUri"),request.getParameter("wsite"), advTreeModel.getRoot());
                }
            }catch(Exception e)
            {
                log.error(e);
            }
            return null;
        }
        
        
        /*
         * Metodo que crea el nodo de la nueva marca y la pasa al archivo zul cnf_GenericZulTreeUpdate, para que este levante un evento y
         * sea dicho evento manejado en el indexAdm.zul y este actualice el árbol, ya que no se puede actualizar sin que sea en un listener (Evento)
         */
        public static void createNewBrandNode(HttpServletRequest request, User user, WebSite newSite)
        {
            try
            {
                WebSite adminWebSite=WebSite.ClassMgr.getWebSite(SOCIALADMINID);
                
                DisplayObject displayObj=(DisplayObject)SocialSite.sclass.getDisplayObject().createGenericInstance();
                String sIconImg="off_"+displayObj.getIconClass();
                //rootTreeNode.insert(new ElementTreeNode(new Element(newSite.getTitle(), newSite.getURI(), "/work/models/"+adminWebSite.getId()+"/admin/img/"+sIconImg),getTreeCategoryNodes(newSite, adminWebSite, user),true), rootTreeNode.getChildCount());
                ElementTreeNode newBrandNode=new ElementTreeNode(new Element(newSite.getTitle(), newSite.getURI(), "/work/models/"+adminWebSite.getId()+"/admin/img/"+sIconImg),getTreeCategoryNodes(newSite, user),true);
                request.setAttribute("action", "createNewBrand");
                request.setAttribute("treeItem", newBrandNode);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/work/models/"+adminWebSite.getId()+"/admin/zul/cnf_GenericZulTreeUpdate.zul");
                dispatcher.include(request, new SWBResponse());
            }catch(Exception e)
            {
                log.debug(e);
            }
        }
        
        
        /**
         * Este metodo es utilizado desde los recursos de swb.
         * Metodo que elimina un nodo del árbol.
         * Cuando una clase controlada desde el árbol es de tipo social_DeleteControlable (interface de la ontologpia), el usuario
         * desarrollador puede agregar un recurso para manejar el evento "Eliminar" del árbol para las instancias de dicha clase,
         * en dicho recurso el usurio puede eliminar todo los datos especificos que desee para dicha instancia (Base de datos, filesystem, conexiones a webservices, etc)
         * y despues debe mandar llamar a este metodo para que se elimine en cascada todo lo que tiene que ver con dicha instancia en el modelo semantico.
         * @param request
         * @param paramRequest 
         */
        public static void removeTreeNode(HttpServletRequest request, SWBParamRequest paramRequest)
        {
            try
            {
                String action=(String)request.getAttribute("action");
                
                if(action!=null && action.equals(SWBSocialResourceUtils.ACTION_REMOVE))
                {
                    String objUri=(String)request.getAttribute("objUri");
                    if(objUri!=null)
                    {
                        SemanticObject semObj=SemanticObject.createSemanticObject(objUri);
                        Semantic.removeSemObjTreeNode(semObj);
                    }
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/work/models/"+paramRequest.getWebPage().getWebSiteId()+"/admin/zul/cnf_GenericZulTreeUpdate.zul");
                dispatcher.include(request, new SWBResponse());
            }catch(Exception e)
            {
                log.debug(e);
            }
        }
        
        /**
         * Metodo que actualiza un nodo (su título) desde un recurso de swb
         * @param request
         * @param paramRequest
         * @param treeNode
         * @param title 
         */
        public static void refreshNode(HttpServletRequest request, TreeNode treeNode)
        {
            try
            {
                if(treeNode==null) return;
                ElementTreeNode etn=(ElementTreeNode)treeNode;
                SemanticObject semObj=SemanticObject.createSemanticObject(etn.getData().getUri());
                etn.getData().setName(semObj.getProperty(Descriptiveable.swb_title));
                WebSite adminWebSite=WebSite.ClassMgr.getWebSite(SOCIALADMINID);
                request.setAttribute("action","refreshNodeTitle"); 
                RequestDispatcher dispatcher = request.getRequestDispatcher("/work/models/"+adminWebSite.getId()+"/admin/zul/cnf_GenericZulTreeUpdate.zul");
                dispatcher.include(request, new SWBResponse());
            }catch(Exception e)
            {
                log.debug(e);
            }
        }
        
        
        /**
         * Este metodo es utilizado desde los recursos de swb.
         * Metodo que inserta un nodo del árbol.
         * @param request
         * @param paramRequest 
         */
        
        public static void insertTreeNode(HttpServletRequest request, ElementTreeNode parentNode, SWBClass swbClass)
        {
            try
            {
                WebSite adminWebSite=WebSite.ClassMgr.getWebSite(SOCIALADMINID);
                System.out.println("wsiteA Jorge:"+adminWebSite);
                request.setAttribute("treeItem", parentNode);
                request.setAttribute("swbClass", swbClass);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/work/models/"+adminWebSite.getId()+"/admin/zul/cnf_GenericZulTreeUpdate.zul");
                dispatcher.include(request, new SWBResponse());
            }catch(Exception e)
            {
                log.debug(e);
            }
        }
        
         /*
         * Metodo que sirve para crear los nodos de tipo categorías a un nuevo nodo de tipo Marca.
         */
        private static ElementTreeNode[] getTreeCategoryNodes(SWBModel model, User user)
        {
            try
            {
                ArrayList<TreeNodePage> alist=new ArrayList();
                WebSite adminWebSite=WebSite.ClassMgr.getWebSite(SOCIALADMINID);
                WebPage treePageHome=adminWebSite.getWebPage("TreeCategoryNodes");
                int cont=0;
                Iterator <WebPage> itChilds=treePageHome.listVisibleChilds(user.getLanguage());
                while(itChilds.hasNext())
                {
                    WebPage page=itChilds.next();
                    if(page instanceof TreeNodePage)
                    {
                        TreeNodePage treeNodePage=(TreeNodePage)page;
                        alist.add(treeNodePage);
                        cont++;
                    }
                }
                ElementTreeNode[] elementTreeNode=new ElementTreeNode[cont];
                Iterator<TreeNodePage> itTreeNodes=alist.iterator();
                int cont2=0;
                while(itTreeNodes.hasNext())
                {
                    TreeNodePage treeNode=itTreeNodes.next();
                    String iconImgPath=SWBPortal.getWebWorkPath()+treeNode.getWorkPath()+"/"+treeNode.social_wpImg.getName()+"_"+treeNode.getId()+"_"+treeNode.getWpImg();
                    elementTreeNode[cont2]=new ElementTreeNode(new Element(treeNode.getTitle(), treeNode.getId(), treeNode.getZulResourcePath(), iconImgPath, null, model.getId()));
                    cont2++;
                }
                return elementTreeNode;
            }catch(Exception e)
            {
                log.error(e);
            }
            return new ElementTreeNode[0];
        }
        
         /**
         * Este metodo es utilizado desde los recursos de swb.
         * Metodo que coloca un mensaje en la barra de estado.
         * @param request
         * @param paramRequest 
         */
        
        public static void setStatusMessage(HttpServletRequest request, String message)
        {
            try
            {
                WebSite adminWebSite=WebSite.ClassMgr.getWebSite(SOCIALADMINID);
                request.setAttribute("action", "statusMsg");
                request.setAttribute("message", message);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/work/models/"+adminWebSite.getId()+"/admin/zul/cnf_GenericZulTreeUpdate.zul");
                dispatcher.include(request, new SWBResponse());
            }catch(Exception e)
            {
                log.debug(e);
            }
        }
    }
    
    
    public static class Semantic
    {
        
        /**
         * Elimina un objeto semantico, si este objeto es de tipo (interface de la ontología) Childrenable, entonces, primero borra todos
         * los hijos y despues el objeto semantico requerido.
         * @param semObj Objeto semantico que se requiere eliminar
         */
        public static void removeSemObjTreeNode(SemanticObject semObj)
        {
           if(semObj!=null)
           {
               if(semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable))
               {
                   if(SWBSocialResourceUtils.Semantic.removeObjChildrenable(semObj))
                   {
                        semObj.remove();
                   }
               }else
               {
                    semObj.remove();
               }
           }
        }

        /**
         * Metodo para eliminar los todos los hijos de un elementos que sea de tipo Childrenale, de manera recursiva.
         * @param semObj SemanticObject en el cual buscara nodos hijos, si los tiene los elimina
         * @return true: Si eliminó satisfactoriamente, false: si no lo hizo.
         */
        public static boolean removeObjChildrenable(SemanticObject semObj) {
            try {
                Childrenable childrenable = (Childrenable) semObj.getGenericInstance();
                Iterator<Childrenable> itChildren = childrenable.listChildrenObjInvs();
                while (itChildren.hasNext()) {
                    Childrenable children = itChildren.next();
                    if (children.listChildrenObjInvs().hasNext()) {
                        if(removeObjChildrenable(children.getSemanticObject()))
                        {
                            //System.out.println("Hijo que elimina-0:"+children.getSemanticObject());
                            children.getSemanticObject().remove();
                        }
                    } else {
                        //System.out.println("Hijo que elimina-1:"+children.getSemanticObject());
                        children.getSemanticObject().remove();
                    }
                }
                return true;
            } catch (Exception e) {
                log.error(e);
            }
            return false;
        }
       
    }
    
    //COMIENZAN METODOS PARA USO EN ZKOSS
    
    public static class Zkoss 
    {
        
         /**
         * Metodo que actualiza un nodo del arbol de navegación
         * los desarrolladores ya tuvieron que haber actualizado información del nodo(ej. título, icono, etc) por ellos mismos 
         * esto con: ElementTreeNode.getData().setName(""), etc
         * y despues mandar llamar este metodo para que se les refresque el árbol mostrando ese nodo con esas propiedades modificadas
         * @param treeNode
         * @param title 
        */
        /*
        public static void refreshNodeData(TreeNode treeNode)
        {
            if(treeNode!=null)
            {
                EventQueue<Event> eq = EventQueues.lookup("refreshNodo2Tree", EventQueues.SESSION, true);
                eq.publish(new Event("onRefreshNode", null, treeNode));
            }
        }*/
        
        /**
         * Metodo que actualiza el título a un nodo del arbol de navegación
         * Este metodo se utiliza para que los desarrolladores vean el cambio de datos de un nodo
         * ya que de lo contrario, el nodo aparecería siempre con el mismo nombre.
         * @param treeNode
         * @param title 
        */
        
        public static void refreshNode(TreeNode treeNode)
        {
            if(treeNode!=null)
            {
                ElementTreeNode etn=(ElementTreeNode)treeNode;
                SemanticObject semObj=SemanticObject.createSemanticObject(etn.getData().getUri());
                System.out.println("etn.getData().getUri():"+etn.getData().getUri());
                if(semObj!=null)    //NO ES UNA CATEGORÍA, SI ES NULO, QUIERE DECIR QUE SI ES UNA CATEGORÍA.
                {
                    etn.getData().setName(semObj.getProperty(Descriptiveable.swb_title));
                }     
                EventQueue<Event> eq = EventQueues.lookup("refreshNodo2Tree", EventQueues.SESSION, true);
                eq.publish(new Event("onRefreshNode", null, treeNode));
            }
        }
        
        
        
        /**
        * Refreshes the given node
        * Este metodo es llamado desde el archivo indexAdm.zul, mediante el disparo de evento de actualización de nodos de esta misma clase(ej. metodo "refreshNodeName",
        * pero es posible que también pueda ser llamado desde un recurso (Probar)
        * @param node The node to refresh
        */
        public static void refreshNodeFromZul(TreeNode node) {
            if (node != null) {
                TreeNode parent = node.getParent();
                if (parent != null) 
                {
                    int index = parent.getIndex(node);
                    if (index >= 0) {
                            node.getModel().fireEvent(parent, index, index, TreeDataEvent.CONTENTS_CHANGED);
                            node.getModel().fireEvent(parent, index, index, TreeDataEvent.INTERVAL_REMOVED);
                            node.getModel().fireEvent(parent, index, index, TreeDataEvent.INTERVAL_ADDED);
                    }
                }
            }
        }
        
        
         /*
         * Este metodo se debe llamar desde un zul o un controlador de zul.
         * Metodo que actualiza un nodo del árbol, hace lo mismo que el metodo anterior updateTreeNode, solo que este ultimo es para ser llamado desde recurso swb.
         * Cuando una clase controlada desde el árbol es de tipo social_DeleteControlable (interface de la ontologpia), el usuario
         * desarrollador puede agregar un recurso para manejar el evento "Eliminar" del árbol para las instancias de dicha clase,
         * en dicho recurso el usurio puede eliminar todo los datos especificos que desee para dicha instancia (Base de datos, filesystem, conexiones a webservices, etc)
         * y despues debe mandar llamar a este metodo para que se elimine en cascada todo lo que tiene que ver con dicha instancia en el modelo semantico.
         * @param request
         * @param paramRequest 
         */
        
        public static void updateTreeNode(Map<String, Object> requestScope)
        {
            try
            {
                String action=(String)requestScope.get("action");
                if(action!=null && action.equals(SWBSocialResourceUtils.ACTION_REMOVE))
                {
                    String objUri=(String)requestScope.get("objUri");
                    if(objUri!=null)
                    {
                        SemanticObject semObj=SemanticObject.createSemanticObject(objUri);
                        Semantic.removeSemObjTreeNode(semObj);
                    }
                    ElementTreeNode treeItem=(ElementTreeNode)requestScope.get("treeItem");
                    if(treeItem!=null)
                    {
                        EventQueue<Event> eq = EventQueues.lookup("removeNodo2Tree", EventQueues.SESSION, true);
                        eq.publish(new Event("onRemoveNode", null, treeItem));
                    }
                }
            }catch(Exception e)
            {
                log.debug(e);
            }
        }
        
        /**
         * Metodo que inserta un nodo en el árbol de navegación
         * @param parentNode   Nodo a insertar
         * @param newSWBClass SWBClass del nodo a insertar, este es la nueva instancia del obheto semantico creado en swb y que se desea
         * visualizar en el árbol de navegación. Se visualizaría como un nodo hijo del nodo que se recibe por medio del parametro parentNode
         */
        
        public static void insertNode2Tree(ElementTreeNode parentNode, SWBClass newSWBClass)
        {
            if(parentNode!=null)
            {
                EventQueue<Event> eq = EventQueues.lookup("insertNode2Tree", EventQueues.SESSION, true);
                TreeNodeRefresh treeNodeRefresh=new TreeNodeRefresh(parentNode, newSWBClass);
                eq.publish(new Event("onCreateNode", null, treeNodeRefresh));
            }
        }

        
        
        /*
         * Envía mensajes a la barra de estatus
         */
        public static void setStatusMessage(String message)
        {
            EventQueue<Event> eq = EventQueues.lookup("updateMsgWin", EventQueues.SESSION, true);
            eq.publish(new Event("onUpdateMsgWin", null, message));
        }
        
         /*
         * Metodo utilizado desde el archivo indexAdm.zul y puede ser utilizado desde cualquier .zul 
         * Metodo cuya funcionalidad es la de insertar un nodo al árbol de navegación y que se refleje en el mismo
         * @param parentItem item padre del que se desea insertar
         * @param swbClass SWBClass del nuevo elemento a insertar
         * @param wpage WebPage de la categoría donde se desea insertar un nuevo nodo
         */
        public static void insertTreeNode(ElementTreeNode parentNode, SWBClass swbClass) {
            if (swbClass instanceof Descriptiveable) {
                try
                {
                    String ImgAdminPathBase = "/work/models/";
                    //Tomo un sitio de tipo SocialAdmin (Se considera que solo debe haber uno) y este debe de tener
                    //imagenes en la ruta /work/models/[iddesitio]/admin/img/
                    SocialAdmin socialAdm=SocialAdmin.ClassMgr.listSocialAdmins().next();
                    ImgAdminPathBase+=socialAdm.getId()+"/admin/img/";
                    

                    String categoryID=null;
                    Descriptiveable descripTable = (Descriptiveable) swbClass;
                    DisplayObject displayObj = (DisplayObject) swbClass.getSemanticObject().getSemanticClass().getDisplayObject().createGenericInstance();
                    String iconImg=displayObj.getIconClass();
                    if(swbClass!=null && swbClass.getSemanticObject().getSemanticClass().isSubClass(Activeable.swb_Activeable))
                    {
                        iconImg="off_"+iconImg;
                    }

                    if (parentNode.getData().getUri().indexOf("#") > -1) //El parentItem no es una categoría, entonces se asume que es childrenable el nuevo nodo
                    {
                        SemanticObject semObj = SemanticObject.getSemanticObject(parentNode.getData().getUri());
                        if (semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable)) {
                            //Asumimos entonces que swbClass es de tipo Childrenable, ya que el nodo donde se esta creando es de tipo Childrenable
                            Childrenable childrenable = (Childrenable) swbClass;
                            childrenable.setParentObj((Childrenable) semObj.getGenericInstance());
                            categoryID=parentNode.getData().getCategoryID();
                        }
                    }else{
                        categoryID=parentNode.getData().getUri();
                    }
                    String modelID=swbClass.getSemanticObject().getModel().getModelObject().getId();
                    parentNode.insert(new ElementTreeNode(new Element(descripTable.getTitle(), descripTable.getURI(), null, ImgAdminPathBase + iconImg,
                            categoryID, modelID), new ElementTreeNode[0], true), parentNode.getChildCount());
                }catch(Exception e)
                {
                    log.error(e);
                }
            }
        }
       
    }
}
