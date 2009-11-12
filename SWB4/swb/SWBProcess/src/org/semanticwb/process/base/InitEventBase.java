package org.semanticwb.process.base;


public class InitEventBase extends org.semanticwb.process.Event implements org.semanticwb.process.FlowObject,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_ie_next=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ie_next");
       public static final org.semanticwb.platform.SemanticClass swbps_InitEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InitEvent");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InitEvent");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.InitEvent> listInitEvents(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.InitEvent>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.InitEvent> listInitEvents()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.InitEvent>(it, true);
       }

       public static org.semanticwb.process.InitEvent createInitEvent(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.InitEvent.ClassMgr.createInitEvent(String.valueOf(id), model);
       }

       public static org.semanticwb.process.InitEvent getInitEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.InitEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.InitEvent createInitEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.InitEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeInitEvent(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasInitEvent(String id, org.semanticwb.model.SWBModel model)
       {
           return (getInitEvent(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.InitEvent> listInitEventByNext(org.semanticwb.process.FlowObject ie_next,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.InitEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_ie_next, ie_next.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.InitEvent> listInitEventByNext(org.semanticwb.process.FlowObject ie_next)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.InitEvent> it=new org.semanticwb.model.GenericIterator(ie_next.getSemanticObject().getModel().listSubjects(swbps_ie_next,ie_next.getSemanticObject()));
       return it;
   }
    }

    public InitEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setNext(org.semanticwb.process.FlowObject value)
    {
        getSemanticObject().setObjectProperty(swbps_ie_next, value.getSemanticObject());
    }

    public void removeNext()
    {
        getSemanticObject().removeProperty(swbps_ie_next);
    }


    public org.semanticwb.process.FlowObject getNext()
    {
         org.semanticwb.process.FlowObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_ie_next);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObject)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
