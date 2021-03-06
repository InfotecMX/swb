package org.semanticwb.domotic.model.base;


public abstract class DomActionBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomEvent");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domEventInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domEventInv");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomRule");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasDomRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasDomRule");
    public static final org.semanticwb.platform.SemanticClass swb4d_StartTimerAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#StartTimerAction");
    public static final org.semanticwb.platform.SemanticProperty swb4d_getStartTimerActionInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#getStartTimerActionInv");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomAction for all models
       * @return Iterator of org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction>(it, true);
        }

        public static org.semanticwb.domotic.model.DomAction createDomAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomAction.ClassMgr.createDomAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomAction
       * @param id Identifier for org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return A org.semanticwb.domotic.model.DomAction
       */
        public static org.semanticwb.domotic.model.DomAction getDomAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomAction
       * @param id Identifier for org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return A org.semanticwb.domotic.model.DomAction
       */
        public static org.semanticwb.domotic.model.DomAction createDomAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomAction
       * @param id Identifier for org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       */
        public static void removeDomAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomAction
       * @param id Identifier for org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return true if the org.semanticwb.domotic.model.DomAction exists, false otherwise
       */

        public static boolean hasDomAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomActionBase.ClassMgr getDomActionClassMgr()
    {
        return new DomActionBase.ClassMgr();
    }

   /**
   * Constructs a DomActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomAction
   */
    public DomActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
   /**
   * Sets the value for the property DomEvent
   * @param value DomEvent to set
   */

    public void setDomEvent(org.semanticwb.domotic.model.DomEvent value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domEventInv, value.getSemanticObject());
        }else
        {
            removeDomEvent();
        }
    }
   /**
   * Remove the value for DomEvent property
   */

    public void removeDomEvent()
    {
        getSemanticObject().removeProperty(swb4d_domEventInv);
    }

   /**
   * Gets the DomEvent
   * @return a org.semanticwb.domotic.model.DomEvent
   */
    public org.semanticwb.domotic.model.DomEvent getDomEvent()
    {
         org.semanticwb.domotic.model.DomEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domEventInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomEvent)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomRule
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomRule
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomRule> listDomRules()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomRule>(getSemanticObject().listObjectProperties(swb4d_hasDomRule));
    }

   /**
   * Gets true if has a DomRule
   * @param value org.semanticwb.domotic.model.DomRule to verify
   * @return true if the org.semanticwb.domotic.model.DomRule exists, false otherwise
   */
    public boolean hasDomRule(org.semanticwb.domotic.model.DomRule value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasDomRule,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DomRule
   * @param value org.semanticwb.domotic.model.DomRule to add
   */

    public void addDomRule(org.semanticwb.domotic.model.DomRule value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasDomRule, value.getSemanticObject());
    }
   /**
   * Removes all the DomRule
   */

    public void removeAllDomRule()
    {
        getSemanticObject().removeProperty(swb4d_hasDomRule);
    }
   /**
   * Removes a DomRule
   * @param value org.semanticwb.domotic.model.DomRule to remove
   */

    public void removeDomRule(org.semanticwb.domotic.model.DomRule value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasDomRule,value.getSemanticObject());
    }

   /**
   * Gets the DomRule
   * @return a org.semanticwb.domotic.model.DomRule
   */
    public org.semanticwb.domotic.model.DomRule getDomRule()
    {
         org.semanticwb.domotic.model.DomRule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasDomRule);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomRule)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
   /**
   * Sets the value for the property GetStartTimerAction
   * @param value GetStartTimerAction to set
   */

    public void setGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_getStartTimerActionInv, value.getSemanticObject());
        }else
        {
            removeGetStartTimerAction();
        }
    }
   /**
   * Remove the value for GetStartTimerAction property
   */

    public void removeGetStartTimerAction()
    {
        getSemanticObject().removeProperty(swb4d_getStartTimerActionInv);
    }

   /**
   * Gets the GetStartTimerAction
   * @return a org.semanticwb.domotic.model.StartTimerAction
   */
    public org.semanticwb.domotic.model.StartTimerAction getGetStartTimerAction()
    {
         org.semanticwb.domotic.model.StartTimerAction ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_getStartTimerActionInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.StartTimerAction)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the DomiticSite
   * @return a instance of org.semanticwb.domotic.model.DomiticSite
   */
    public org.semanticwb.domotic.model.DomiticSite getDomiticSite()
    {
        return (org.semanticwb.domotic.model.DomiticSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
