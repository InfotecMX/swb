package org.semanticwb.jcr283.repository.model.base;


public abstract class ResourceBase extends org.semanticwb.jcr283.repository.model.Base implements org.semanticwb.jcr283.repository.model.MimeType
{
       public static final org.semanticwb.platform.SemanticClass nt_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#resource");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#resource");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Resource> listResources(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Resource>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Resource> listResources()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Resource>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.Resource getResource(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Resource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.Resource createResource(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Resource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeResource(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasResource(String id, org.semanticwb.model.SWBModel model)
       {
           return (getResource(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Resource> listResourceByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Resource> listResourceByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Resource> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Resource> listResourceByNode(org.semanticwb.jcr283.repository.model.Base hasnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNode, hasnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Resource> listResourceByNode(org.semanticwb.jcr283.repository.model.Base hasnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Resource> it=new org.semanticwb.model.GenericIterator(hasnode.getSemanticObject().getModel().listSubjects(swbrep_hasNode,hasnode.getSemanticObject()));
       return it;
   }
    }

    public ResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getMimeType()
    {
        return getSemanticObject().getProperty(jcr_mimeType);
    }

    public void setMimeType(String value)
    {
        getSemanticObject().setProperty(jcr_mimeType, value);
    }

    public String getEncoding()
    {
        return getSemanticObject().getProperty(jcr_encoding);
    }

    public void setEncoding(String value)
    {
        getSemanticObject().setProperty(jcr_encoding, value);
    }
}
