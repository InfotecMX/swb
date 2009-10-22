package org.semanticwb.model.base;


public class UserBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable,org.semanticwb.model.Roleable,org.semanticwb.model.Traceable,org.semanticwb.model.Expirable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Referensable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
       public static final org.semanticwb.platform.SemanticProperty swb_usrCountry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrCountry");
       public static final org.semanticwb.platform.SemanticClass swb_UserFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");
       public static final org.semanticwb.platform.SemanticProperty swb_userFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userFavorite");
       public static final org.semanticwb.platform.SemanticProperty swb_usrLastName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLastName");
       public static final org.semanticwb.platform.SemanticProperty swb_externalID=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#externalID");
       public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendarRef");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_usrSecondLastName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecondLastName");
       public static final org.semanticwb.platform.SemanticProperty swb_expiration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#expiration");
       public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
       public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroup");
       public static final org.semanticwb.platform.SemanticProperty swb_usrEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrEmail");
       public static final org.semanticwb.platform.SemanticProperty swb_usrFirstName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrFirstName");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swb_usrLanguage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLanguage");
       public static final org.semanticwb.platform.SemanticProperty swb_hasUserType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserType");
       public static final org.semanticwb.platform.SemanticProperty swb_usrPasswordChanged=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPasswordChanged");
       public static final org.semanticwb.platform.SemanticClass swb_AdminFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminFilter");
       public static final org.semanticwb.platform.SemanticProperty swb_hasAdminFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasAdminFilter");
       public static final org.semanticwb.platform.SemanticProperty swb_ursPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#ursPhoto");
       public static final org.semanticwb.platform.SemanticProperty swb_usrLastLogin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLastLogin");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swb_usrPassword=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPassword");
       public static final org.semanticwb.platform.SemanticProperty swb_usrLogin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLogin");
       public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
       public static final org.semanticwb.platform.SemanticProperty swb_hasRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRole");
       public static final org.semanticwb.platform.SemanticProperty swb_usrSecurityQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecurityQuestion");
       public static final org.semanticwb.platform.SemanticProperty swb_usrSecurityAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecurityAnswer");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");

       public static java.util.Iterator<org.semanticwb.model.User> listUsers(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.model.User> listUsers()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(it, true);
       }

       public static org.semanticwb.model.User createUser(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.model.User.ClassMgr.createUser(String.valueOf(id), model);
       }

       public static org.semanticwb.model.User getUser(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.User)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.model.User createUser(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.User)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeUser(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasUser(String id, org.semanticwb.model.SWBModel model)
       {
           return (getUser(id, model)!=null);
       }
    }

    public UserBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setCountry(org.semanticwb.model.Country value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_usrCountry, value.getSemanticObject());
    }

    public void removeCountry()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_usrCountry);
    }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByCountry(org.semanticwb.model.Country usrcountry,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_usrCountry, usrcountry.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByCountry(org.semanticwb.model.Country usrcountry)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(usrcountry.getSemanticObject().getModel().listSubjects(ClassMgr.swb_usrCountry,usrcountry.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Country getCountry()
    {
         org.semanticwb.model.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_usrCountry);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Country)obj.createGenericInstance();
         }
         return ret;
    }

    public void setUserFavorite(org.semanticwb.model.UserFavorite value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_userFavorite, value.getSemanticObject());
    }

    public void removeUserFavorite()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_userFavorite);
    }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByUserFavorite(org.semanticwb.model.UserFavorite userfavorite,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_userFavorite, userfavorite.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByUserFavorite(org.semanticwb.model.UserFavorite userfavorite)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(userfavorite.getSemanticObject().getModel().listSubjects(ClassMgr.swb_userFavorite,userfavorite.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.UserFavorite getUserFavorite()
    {
         org.semanticwb.model.UserFavorite ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_userFavorite);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserFavorite)obj.createGenericInstance();
         }
         return ret;
    }

    public String getLastName()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_usrLastName);
    }

    public void setLastName(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_usrLastName, value);
    }

    public String getExternalID()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_externalID);
    }

    public void setExternalID(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_externalID, value);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_active, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasCalendarRef));
    }

    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        if(calendarref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasCalendarRef,calendarref.getSemanticObject());
    }

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasCalendarRef, value.getSemanticObject());
    }

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasCalendarRef);
    }

    public void removeCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasCalendarRef,calendarref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasCalendarRef, hascalendarref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasCalendarRef,hascalendarref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_modifiedBy);
    }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getSecondLastName()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_usrSecondLastName);
    }

    public void setSecondLastName(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_usrSecondLastName, value);
    }

    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_expiration);
    }

    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_expiration, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasUserGroup));
    }

    public boolean hasUserGroup(org.semanticwb.model.UserGroup usergroup)
    {
        if(usergroup==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasUserGroup,usergroup.getSemanticObject());
    }

    public void addUserGroup(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasUserGroup, value.getSemanticObject());
    }

    public void removeAllUserGroup()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasUserGroup);
    }

    public void removeUserGroup(org.semanticwb.model.UserGroup usergroup)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasUserGroup,usergroup.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByUserGroup(org.semanticwb.model.UserGroup hasusergroup,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasUserGroup, hasusergroup.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByUserGroup(org.semanticwb.model.UserGroup hasusergroup)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(hasusergroup.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasUserGroup,hasusergroup.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.UserGroup getUserGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasUserGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }

    public String getEmail()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_usrEmail);
    }

    public void setEmail(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_usrEmail, value);
    }

    public String getFirstName()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_usrFirstName);
    }

    public void setFirstName(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_usrFirstName, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public String getLanguage()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_usrLanguage);
    }

    public void setLanguage(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_usrLanguage, value);
    }

    public java.util.Iterator<String> listUserTypes()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(ClassMgr.swb_hasUserType);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addUserType(String usertype)
    {
        getSemanticObject().setProperty(ClassMgr.swb_hasUserType, usertype);
    }

    public void removeAllUserType()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasUserType);
    }

    public void removeUserType(String usertype)
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasUserType,usertype);
    }

    public java.util.Date getPasswordChanged()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_usrPasswordChanged);
    }

    public void setPasswordChanged(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_usrPasswordChanged, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminFilter> listAdminFilters()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminFilter>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasAdminFilter));
    }

    public boolean hasAdminFilter(org.semanticwb.model.AdminFilter adminfilter)
    {
        if(adminfilter==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasAdminFilter,adminfilter.getSemanticObject());
    }

    public void addAdminFilter(org.semanticwb.model.AdminFilter value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasAdminFilter, value.getSemanticObject());
    }

    public void removeAllAdminFilter()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasAdminFilter);
    }

    public void removeAdminFilter(org.semanticwb.model.AdminFilter adminfilter)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasAdminFilter,adminfilter.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByAdminFilter(org.semanticwb.model.AdminFilter hasadminfilter,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasAdminFilter, hasadminfilter.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByAdminFilter(org.semanticwb.model.AdminFilter hasadminfilter)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(hasadminfilter.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasAdminFilter,hasadminfilter.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.AdminFilter getAdminFilter()
    {
         org.semanticwb.model.AdminFilter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasAdminFilter);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.AdminFilter)obj.createGenericInstance();
         }
         return ret;
    }

    public String getPhoto()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_ursPhoto);
    }

    public void setPhoto(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_ursPhoto, value);
    }

    public java.util.Date getLastLogin()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_usrLastLogin);
    }

    public void setLastLogin(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_usrLastLogin, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getPassword()
    {
        //Override this method in User object
        return getSemanticObject().getProperty(ClassMgr.swb_usrPassword,false);
    }

    public void setPassword(String value)
    {
        //Override this method in User object
        getSemanticObject().setProperty(ClassMgr.swb_usrPassword, value,false);
    }

    public String getLogin()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_usrLogin);
    }

    public void setLogin(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_usrLogin, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasRole));
    }

    public boolean hasRole(org.semanticwb.model.Role role)
    {
        if(role==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasRole,role.getSemanticObject());
    }

    public void addRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasRole, value.getSemanticObject());
    }

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasRole,role.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByRole(org.semanticwb.model.Role hasrole,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRole, hasrole.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.User> listUserByRole(org.semanticwb.model.Role hasrole)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(hasrole.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRole,hasrole.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    public int getSecurityQuestion()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swb_usrSecurityQuestion);
    }

    public void setSecurityQuestion(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swb_usrSecurityQuestion, value);
    }

    public String getSecurityAnswer()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_usrSecurityAnswer);
    }

    public void setSecurityAnswer(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_usrSecurityAnswer, value);
    }

    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return (org.semanticwb.model.UserRepository)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
