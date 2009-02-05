package org.semanticwb.model.base;


public class PFlowRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Deleteable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pflow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pflow");
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");

    public PFlowRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.PFlowRef getPFlowRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PFlowRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(org.semanticwb.model.PFlowRef.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(org.semanticwb.model.PFlowRef.class, it, true);
    }

    public static org.semanticwb.model.PFlowRef createPFlowRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PFlowRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.model.PFlowRef createPFlowRef(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.model.PFlowRef.createPFlowRef(String.valueOf(id), model);
    }

    public static void removePFlowRef(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPFlowRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPFlowRef(id, model)!=null);
    }

    public void setPflow(org.semanticwb.model.PFlow pflow)
    {
        getSemanticObject().setObjectProperty(swb_pflow, pflow.getSemanticObject());
    }

    public void removePflow()
    {
        getSemanticObject().removeProperty(swb_pflow);
    }

    public org.semanticwb.model.PFlow getPflow()
    {
         org.semanticwb.model.PFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pflow);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlow)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, deleted);
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return new org.semanticwb.model.WebSite(getSemanticObject().getModel().getModelObject());
    }
}
