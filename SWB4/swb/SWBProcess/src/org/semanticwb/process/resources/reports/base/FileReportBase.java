package org.semanticwb.process.resources.reports.base;


public abstract class FileReportBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass rep_Report=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#Report");
    public static final org.semanticwb.platform.SemanticProperty rep_fileNameReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#fileNameReport");
    public static final org.semanticwb.platform.SemanticProperty rep_extension=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#extension");
    public static final org.semanticwb.platform.SemanticClass rep_FileReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#FileReport");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#FileReport");

    public static class ClassMgr
    {
       /**
       * Returns a list of FileReport for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.resources.reports.FileReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FileReport> listFileReports(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.resources.reports.FileReport for all models
       * @return Iterator of org.semanticwb.process.resources.reports.FileReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FileReport> listFileReports()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport>(it, true);
        }

        public static org.semanticwb.process.resources.reports.FileReport createFileReport(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.resources.reports.FileReport.ClassMgr.createFileReport(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.resources.reports.FileReport
       * @param id Identifier for org.semanticwb.process.resources.reports.FileReport
       * @param model Model of the org.semanticwb.process.resources.reports.FileReport
       * @return A org.semanticwb.process.resources.reports.FileReport
       */
        public static org.semanticwb.process.resources.reports.FileReport getFileReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.FileReport)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.resources.reports.FileReport
       * @param id Identifier for org.semanticwb.process.resources.reports.FileReport
       * @param model Model of the org.semanticwb.process.resources.reports.FileReport
       * @return A org.semanticwb.process.resources.reports.FileReport
       */
        public static org.semanticwb.process.resources.reports.FileReport createFileReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.FileReport)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.resources.reports.FileReport
       * @param id Identifier for org.semanticwb.process.resources.reports.FileReport
       * @param model Model of the org.semanticwb.process.resources.reports.FileReport
       */
        public static void removeFileReport(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.resources.reports.FileReport
       * @param id Identifier for org.semanticwb.process.resources.reports.FileReport
       * @param model Model of the org.semanticwb.process.resources.reports.FileReport
       * @return true if the org.semanticwb.process.resources.reports.FileReport exists, false otherwise
       */

        public static boolean hasFileReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFileReport(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.FileReport with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.reports.FileReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.FileReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FileReport> listFileReportByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.FileReport with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.reports.FileReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FileReport> listFileReportByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.FileReport with a determined FileNameReport
       * @param value FileNameReport of the type org.semanticwb.process.resources.reports.Report
       * @param model Model of the org.semanticwb.process.resources.reports.FileReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.FileReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FileReport> listFileReportByFileNameReport(org.semanticwb.process.resources.reports.Report value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_fileNameReport, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.FileReport with a determined FileNameReport
       * @param value FileNameReport of the type org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.FileReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FileReport> listFileReportByFileNameReport(org.semanticwb.process.resources.reports.Report value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_fileNameReport,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.FileReport with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.reports.FileReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.FileReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FileReport> listFileReportByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.FileReport with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.reports.FileReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.FileReport> listFileReportByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FileReportBase.ClassMgr getFileReportClassMgr()
    {
        return new FileReportBase.ClassMgr();
    }

   /**
   * Constructs a FileReportBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FileReport
   */
    public FileReportBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
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
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
   /**
   * Sets the value for the property FileNameReport
   * @param value FileNameReport to set
   */

    public void setFileNameReport(org.semanticwb.process.resources.reports.Report value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(rep_fileNameReport, value.getSemanticObject());
        }else
        {
            removeFileNameReport();
        }
    }
   /**
   * Remove the value for FileNameReport property
   */

    public void removeFileNameReport()
    {
        getSemanticObject().removeProperty(rep_fileNameReport);
    }

   /**
   * Gets the FileNameReport
   * @return a org.semanticwb.process.resources.reports.Report
   */
    public org.semanticwb.process.resources.reports.Report getFileNameReport()
    {
         org.semanticwb.process.resources.reports.Report ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_fileNameReport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.reports.Report)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
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
* Gets the Extension property
* @return String with the Extension
*/
    public String getExtension()
    {
        return getSemanticObject().getProperty(rep_extension);
    }

/**
* Sets the Extension property
* @param value long with the Extension
*/
    public void setExtension(String value)
    {
        getSemanticObject().setProperty(rep_extension, value);
    }
}
