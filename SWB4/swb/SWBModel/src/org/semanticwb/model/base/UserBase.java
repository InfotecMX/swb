package org.semanticwb.model.base;


public class UserBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.model.Roleable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_usrSecondLastName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecondLastName");
    public static final org.semanticwb.platform.SemanticProperty swb_usrEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrEmail");
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_userGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty swb_usrFirstName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrFirstName");
    public static final org.semanticwb.platform.SemanticProperty swb_usrLanguage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLanguage");
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorites=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorites");
    public static final org.semanticwb.platform.SemanticProperty swb_userFavorites=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userFavorites");
    public static final org.semanticwb.platform.SemanticProperty swb_usrPasswordChanged=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPasswordChanged");
    public static final org.semanticwb.platform.SemanticProperty swb_usrLastName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLastName");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticProperty swb_usrLastLogin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLastLogin");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_usrLogin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLogin");
    public static final org.semanticwb.platform.SemanticProperty swb_usrPassword=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPassword");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRole");
    public static final org.semanticwb.platform.SemanticProperty swb_usrSecurityQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecurityQuestion");
    public static final org.semanticwb.platform.SemanticProperty swb_usrSecurityAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecurityAnswer");

    public UserBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getUsrSecondLastName()
    {
        return getSemanticObject().getProperty(swb_usrSecondLastName);
    }

    public void setUsrSecondLastName(String usrSecondLastName)
    {
        getSemanticObject().setProperty(swb_usrSecondLastName, usrSecondLastName);
    }

    public String getUsrEmail()
    {
        return getSemanticObject().getProperty(swb_usrEmail);
    }

    public void setUsrEmail(String usrEmail)
    {
        getSemanticObject().setProperty(swb_usrEmail, usrEmail);
    }

    public void setGroup(org.semanticwb.model.UserGroup usergroup)
    {
        getSemanticObject().setObjectProperty(swb_userGroup, usergroup.getSemanticObject());
    }

    public void removeGroup()
    {
        getSemanticObject().removeProperty(swb_userGroup);
    }

    public org.semanticwb.model.UserGroup getGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_userGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public String getUsrFirstName()
    {
        return getSemanticObject().getProperty(swb_usrFirstName);
    }

    public void setUsrFirstName(String usrFirstName)
    {
        getSemanticObject().setProperty(swb_usrFirstName, usrFirstName);
    }

    public String getLanguage()
    {
        return getSemanticObject().getProperty(swb_usrLanguage);
    }

    public void setLanguage(String usrLanguage)
    {
        getSemanticObject().setProperty(swb_usrLanguage, usrLanguage);
    }

    public void setUserFavorites(org.semanticwb.model.UserFavorites userfavorites)
    {
        getSemanticObject().setObjectProperty(swb_userFavorites, userfavorites.getSemanticObject());
    }

    public void removeUserFavorites()
    {
        getSemanticObject().removeProperty(swb_userFavorites);
    }

    public org.semanticwb.model.UserFavorites getUserFavorites()
    {
         org.semanticwb.model.UserFavorites ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_userFavorites);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserFavorites)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public java.util.Date getUsrPasswordChanged()
    {
        return getSemanticObject().getDateProperty(swb_usrPasswordChanged);
    }

    public void setUsrPasswordChanged(java.util.Date usrPasswordChanged)
    {
        getSemanticObject().setDateProperty(swb_usrPasswordChanged, usrPasswordChanged);
    }

    public String getUsrLastName()
    {
        return getSemanticObject().getProperty(swb_usrLastName);
    }

    public void setUsrLastName(String usrLastName)
    {
        getSemanticObject().setProperty(swb_usrLastName, usrLastName);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(swb_active, active);
    }

    public java.util.Date getUsrLastLogin()
    {
        return getSemanticObject().getDateProperty(swb_usrLastLogin);
    }

    public void setUsrLastLogin(java.util.Date usrLastLogin)
    {
        getSemanticObject().setDateProperty(swb_usrLastLogin, usrLastLogin);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getUsrLogin()
    {
        return getSemanticObject().getProperty(swb_usrLogin);
    }

    public void setUsrLogin(String usrLogin)
    {
        getSemanticObject().setProperty(swb_usrLogin, usrLogin);
    }

    public String getUsrPassword()
    {
        //Implement this method in User object
        throw new org.semanticwb.SWBMethodImplementationRequiredException();
    }

    public void setUsrPassword(String usrPassword)
    {
        //Implement this method in User object
        throw new org.semanticwb.SWBMethodImplementationRequiredException();
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, getSemanticObject().listObjectProperties(swb_hasRole));    }

    public void addRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().addObjectProperty(swb_hasRole, role.getSemanticObject());
    }

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(swb_hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(swb_hasRole,role.getSemanticObject());
    }

    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public int getUsrSecurityQuestion()
    {
        return getSemanticObject().getIntProperty(swb_usrSecurityQuestion);
    }

    public void setUsrSecurityQuestion(int usrSecurityQuestion)
    {
        getSemanticObject().setLongProperty(swb_usrSecurityQuestion, usrSecurityQuestion);
    }

    public String getUsrSecurityAnswer()
    {
        return getSemanticObject().getProperty(swb_usrSecurityAnswer);
    }

    public void setUsrSecurityAnswer(String usrSecurityAnswer)
    {
        getSemanticObject().setProperty(swb_usrSecurityAnswer, usrSecurityAnswer);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator((org.semanticwb.platform.SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }

    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return new org.semanticwb.model.UserRepository(getSemanticObject().getModel().getModelObject());
    }
}
