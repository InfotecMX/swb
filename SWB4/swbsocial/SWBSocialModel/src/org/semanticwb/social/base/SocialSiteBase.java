package org.semanticwb.social.base;


   /**
   * Clase que hereda de swb:WebSite. Es un tipo de website Social. De esta manera se puede contar con todos los elementos en el arbol de navegación en la administración, y otros elementos utiles para Social Site. 
   */
public abstract class SocialSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Localeable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Countryable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Trashable
{
   /**
   * Clase que hereda de swb:WebSite. Es un tipo de website Social. De esta manera se puede contar con todos los elementos en el arbol de navegación en la administración, y otros elementos utiles para Social Site.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialSite");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialSite");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialSite for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialSite for all models
       * @return Iterator of org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialSite
       * @param id Identifier for org.semanticwb.social.SocialSite
       * @return A org.semanticwb.social.SocialSite
       */
        public static org.semanticwb.social.SocialSite getSocialSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.social.SocialSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.social.SocialSite)
                    {
                        ret=(org.semanticwb.social.SocialSite)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.social.SocialSite
       * @param id Identifier for org.semanticwb.social.SocialSite
       * @return A org.semanticwb.social.SocialSite
       */
        public static org.semanticwb.social.SocialSite createSocialSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.social.SocialSite)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialSite
       * @param id Identifier for org.semanticwb.social.SocialSite
       */
        public static void removeSocialSite(String id)
        {
            org.semanticwb.social.SocialSite obj=getSocialSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialSite
       * @param id Identifier for org.semanticwb.social.SocialSite
       * @return true if the org.semanticwb.social.SocialSite exists, false otherwise
       */

        public static boolean hasSocialSite(String id)
        {
            return (getSocialSite(id)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.social.SocialSite
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialSite with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.social.SocialSite
       */

        public static java.util.Iterator<org.semanticwb.social.SocialSite> listSocialSiteByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialSiteBase.ClassMgr getSocialSiteClassMgr()
    {
        return new SocialSiteBase.ClassMgr();
    }

   /**
   * Constructs a SocialSiteBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialSite
   */
    public SocialSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
