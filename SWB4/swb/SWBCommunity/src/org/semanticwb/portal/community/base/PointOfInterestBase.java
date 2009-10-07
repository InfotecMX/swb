package org.semanticwb.portal.community.base;


public class PointOfInterestBase extends org.semanticwb.portal.community.DirectoryObject implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_PointOfInterest=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PointOfInterest");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PointOfInterest");

    public PointOfInterestBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.PointOfInterest> listPointOfInterests(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PointOfInterest>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.PointOfInterest> listPointOfInterests()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PointOfInterest>(it, true);
    }

    public static org.semanticwb.portal.community.PointOfInterest getPointOfInterest(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.PointOfInterest)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.PointOfInterest createPointOfInterest(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.PointOfInterest)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePointOfInterest(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPointOfInterest(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPointOfInterest(id, model)!=null);
    }
}
