package org.semanticwb.model.base;

public interface UserGroupRefableBase extends org.semanticwb.model.Referensable
{
   /**
   * Referencia a un objeto de tipo UserGroup 
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroupRef");
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs();
    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef value);
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs();

   /**
   * Adds the UserGroupRef
   * @param value An instance of org.semanticwb.model.UserGroupRef
   */
    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value);

   /**
   * Remove all the values for the property UserGroupRef
   */
    public void removeAllUserGroupRef();

   /**
   * Remove a value from the property UserGroupRef
   * @param value An instance of org.semanticwb.model.UserGroupRef
   */
    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef value);

/**
* Gets the UserGroupRef
* @return a instance of org.semanticwb.model.UserGroupRef
*/
    public org.semanticwb.model.UserGroupRef getUserGroupRef();
}
