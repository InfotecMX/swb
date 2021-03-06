package org.semanticwb.process.model.base;


public abstract class ProcessRuleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRule");
    public static final org.semanticwb.platform.SemanticProperty swp_processRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processRule");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessRuleRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessRuleRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessRuleRef for all models
       * @return Iterator of org.semanticwb.process.model.ProcessRuleRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef>(it, true);
        }

        public static org.semanticwb.process.model.ProcessRuleRef createProcessRuleRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessRuleRef.ClassMgr.createProcessRuleRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessRuleRef
       * @param id Identifier for org.semanticwb.process.model.ProcessRuleRef
       * @param model Model of the org.semanticwb.process.model.ProcessRuleRef
       * @return A org.semanticwb.process.model.ProcessRuleRef
       */
        public static org.semanticwb.process.model.ProcessRuleRef getProcessRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessRuleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessRuleRef
       * @param id Identifier for org.semanticwb.process.model.ProcessRuleRef
       * @param model Model of the org.semanticwb.process.model.ProcessRuleRef
       * @return A org.semanticwb.process.model.ProcessRuleRef
       */
        public static org.semanticwb.process.model.ProcessRuleRef createProcessRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessRuleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessRuleRef
       * @param id Identifier for org.semanticwb.process.model.ProcessRuleRef
       * @param model Model of the org.semanticwb.process.model.ProcessRuleRef
       */
        public static void removeProcessRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessRuleRef
       * @param id Identifier for org.semanticwb.process.model.ProcessRuleRef
       * @param model Model of the org.semanticwb.process.model.ProcessRuleRef
       * @return true if the org.semanticwb.process.model.ProcessRuleRef exists, false otherwise
       */

        public static boolean hasProcessRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessRuleRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRuleRef with a determined ProcessRule
       * @param value ProcessRule of the type org.semanticwb.process.model.ProcessRule
       * @param model Model of the org.semanticwb.process.model.ProcessRuleRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessRuleRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefByProcessRule(org.semanticwb.process.model.ProcessRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRuleRef with a determined ProcessRule
       * @param value ProcessRule of the type org.semanticwb.process.model.ProcessRule
       * @return Iterator with all the org.semanticwb.process.model.ProcessRuleRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefByProcessRule(org.semanticwb.process.model.ProcessRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processRule,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessRuleRefBase.ClassMgr getProcessRuleRefClassMgr()
    {
        return new ProcessRuleRefBase.ClassMgr();
    }

   /**
   * Constructs a ProcessRuleRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessRuleRef
   */
    public ProcessRuleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ProcessRule
   * @param value ProcessRule to set
   */

    public void setProcessRule(org.semanticwb.process.model.ProcessRule value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processRule, value.getSemanticObject());
        }else
        {
            removeProcessRule();
        }
    }
   /**
   * Remove the value for ProcessRule property
   */

    public void removeProcessRule()
    {
        getSemanticObject().removeProperty(swp_processRule);
    }

   /**
   * Gets the ProcessRule
   * @return a org.semanticwb.process.model.ProcessRule
   */
    public org.semanticwb.process.model.ProcessRule getProcessRule()
    {
         org.semanticwb.process.model.ProcessRule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processRule);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessRule)obj.createGenericInstance();
         }
         return ret;
    }
}
