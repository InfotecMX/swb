package org.semanticwb.bsc.element.base;


   /**
   * Persiste los atributos de un indicador 
   */
public abstract class IndicatorBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.Activeable,org.semanticwb.bsc.Seasonable,org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.bsc.Committable,org.semanticwb.bsc.Recognizable,org.semanticwb.model.Descriptiveable,org.semanticwb.bsc.Status,org.semanticwb.bsc.Serializable,org.semanticwb.model.Undeleteable
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Usuario que se asigna como responsable de conseguir el objetivo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_champion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#champion");
   /**
   * Persiste la unidad de medida de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_unitMesure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#unitMesure");
   /**
   * Persiste información de un facilitador de champion en una indicador. Este facilitador se encarga de facilitarte a los champions la actualización de datos en el indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_championFacilitator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#championFacilitator");
    public static final org.semanticwb.platform.SemanticClass bsc_Objective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");
    public static final org.semanticwb.platform.SemanticProperty bsc_objectiveInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#objectiveInv");
   /**
   * Persiste información de una nota a la fórmula de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_notesFormula=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#notesFormula");
   /**
   * Persiste una fuente de información de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_informationSource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#informationSource");
   /**
   * Persiste información de la descripción de metas en un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_goals=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#goals");
   /**
   * Persiste información de una periodicidad
   */
    public static final org.semanticwb.platform.SemanticClass bsc_MeasurementFrequency=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MeasurementFrequency");
   /**
   * Persiste la periodicidad (frecuencia de medida) de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_periodicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#periodicity");
   /**
   * Persiste información de una fórmula para el cálculo de un indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_formula=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#formula");
   /**
   * Persiste los atributos de un indicador
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Indicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");

    public static class ClassMgr
    {
       /**
       * Returns a list of Indicator for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicators(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Indicator for all models
       * @return Iterator of org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicators()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator>(it, true);
        }

        public static org.semanticwb.bsc.element.Indicator createIndicator(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Indicator.ClassMgr.createIndicator(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return A org.semanticwb.bsc.element.Indicator
       */
        public static org.semanticwb.bsc.element.Indicator getIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Indicator)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return A org.semanticwb.bsc.element.Indicator
       */
        public static org.semanticwb.bsc.element.Indicator createIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Indicator)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       */
        public static void removeIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return true if the org.semanticwb.bsc.element.Indicator exists, false otherwise
       */

        public static boolean hasIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (getIndicator(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByPeriod(org.semanticwb.bsc.accessory.Period value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriod, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByPeriod(org.semanticwb.bsc.accessory.Period value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriod,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined State
       * @param value State of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByState(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasState, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined State
       * @param value State of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByState(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasState,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Champion
       * @param value Champion of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByChampion(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_champion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Champion
       * @param value Champion of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByChampion(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_champion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ChampionFacilitator
       * @param value ChampionFacilitator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByChampionFacilitator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_championFacilitator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ChampionFacilitator
       * @param value ChampionFacilitator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByChampionFacilitator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_championFacilitator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Objective
       * @param value Objective of the type org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByObjective(org.semanticwb.bsc.element.Objective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_objectiveInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Objective
       * @param value Objective of the type org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByObjective(org.semanticwb.bsc.element.Objective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_objectiveInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Periodicity
       * @param value Periodicity of the type org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByPeriodicity(org.semanticwb.bsc.accessory.MeasurementFrequency value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_periodicity, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Periodicity
       * @param value Periodicity of the type org.semanticwb.bsc.accessory.MeasurementFrequency
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByPeriodicity(org.semanticwb.bsc.accessory.MeasurementFrequency value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_periodicity,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a IndicatorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Indicator
   */
    public IndicatorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.accessory.Period
   * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> listPeriods()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period>(getSemanticObject().listObjectProperties(bsc_hasPeriod));
    }

   /**
   * Gets true if has a Period
   * @param value org.semanticwb.bsc.accessory.Period to verify
   * @return true if the org.semanticwb.bsc.accessory.Period exists, false otherwise
   */
    public boolean hasPeriod(org.semanticwb.bsc.accessory.Period value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasPeriod,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Period
   * @param value org.semanticwb.bsc.accessory.Period to add
   */

    public void addPeriod(org.semanticwb.bsc.accessory.Period value)
    {
        getSemanticObject().addObjectProperty(bsc_hasPeriod, value.getSemanticObject());
    }
   /**
   * Removes all the Period
   */

    public void removeAllPeriod()
    {
        getSemanticObject().removeProperty(bsc_hasPeriod);
    }
   /**
   * Removes a Period
   * @param value org.semanticwb.bsc.accessory.Period to remove
   */

    public void removePeriod(org.semanticwb.bsc.accessory.Period value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasPeriod,value.getSemanticObject());
    }

   /**
   * Gets the Period
   * @return a org.semanticwb.bsc.accessory.Period
   */
    public org.semanticwb.bsc.accessory.Period getPeriod()
    {
         org.semanticwb.bsc.accessory.Period ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasPeriod);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.Period)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.bsc.accessory.State
   * @return A GenericIterator with all the org.semanticwb.bsc.accessory.State
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> listStates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State>(getSemanticObject().listObjectProperties(bsc_hasState));
    }

   /**
   * Gets true if has a State
   * @param value org.semanticwb.bsc.accessory.State to verify
   * @return true if the org.semanticwb.bsc.accessory.State exists, false otherwise
   */
    public boolean hasState(org.semanticwb.bsc.accessory.State value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasState,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a State
   * @param value org.semanticwb.bsc.accessory.State to add
   */

    public void addState(org.semanticwb.bsc.accessory.State value)
    {
        getSemanticObject().addObjectProperty(bsc_hasState, value.getSemanticObject());
    }
   /**
   * Removes all the State
   */

    public void removeAllState()
    {
        getSemanticObject().removeProperty(bsc_hasState);
    }
   /**
   * Removes a State
   * @param value org.semanticwb.bsc.accessory.State to remove
   */

    public void removeState(org.semanticwb.bsc.accessory.State value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasState,value.getSemanticObject());
    }

   /**
   * Gets the State
   * @return a org.semanticwb.bsc.accessory.State
   */
    public org.semanticwb.bsc.accessory.State getState()
    {
         org.semanticwb.bsc.accessory.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasState);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.State)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Champion
   * @param value Champion to set
   */

    public void setChampion(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_champion, value.getSemanticObject());
        }else
        {
            removeChampion();
        }
    }
   /**
   * Remove the value for Champion property
   */

    public void removeChampion()
    {
        getSemanticObject().removeProperty(bsc_champion);
    }

   /**
   * Gets the Champion
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getChampion()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_champion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

/**
* Gets the UnitMesure property
* @return String with the UnitMesure
*/
    public String getUnitMesure()
    {
        return getSemanticObject().getProperty(bsc_unitMesure);
    }

/**
* Sets the UnitMesure property
* @param value long with the UnitMesure
*/
    public void setUnitMesure(String value)
    {
        getSemanticObject().setProperty(bsc_unitMesure, value);
    }
   /**
   * Sets the value for the property ChampionFacilitator
   * @param value ChampionFacilitator to set
   */

    public void setChampionFacilitator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_championFacilitator, value.getSemanticObject());
        }else
        {
            removeChampionFacilitator();
        }
    }
   /**
   * Remove the value for ChampionFacilitator property
   */

    public void removeChampionFacilitator()
    {
        getSemanticObject().removeProperty(bsc_championFacilitator);
    }

   /**
   * Gets the ChampionFacilitator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getChampionFacilitator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_championFacilitator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        //Override this method in Indicator object
        return getSemanticObject().getProperty(bsc_prefix,false);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        //Override this method in Indicator object
        getSemanticObject().setProperty(bsc_prefix, value,false);
    }
   /**
   * Sets the value for the property Objective
   * @param value Objective to set
   */

    public void setObjective(org.semanticwb.bsc.element.Objective value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_objectiveInv, value.getSemanticObject());
        }else
        {
            removeObjective();
        }
    }
   /**
   * Remove the value for Objective property
   */

    public void removeObjective()
    {
        getSemanticObject().removeProperty(bsc_objectiveInv);
    }

   /**
   * Gets the Objective
   * @return a org.semanticwb.bsc.element.Objective
   */
    public org.semanticwb.bsc.element.Objective getObjective()
    {
         org.semanticwb.bsc.element.Objective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_objectiveInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Objective)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the NotesFormula property
* @return String with the NotesFormula
*/
    public String getNotesFormula()
    {
        return getSemanticObject().getProperty(bsc_notesFormula);
    }

/**
* Sets the NotesFormula property
* @param value long with the NotesFormula
*/
    public void setNotesFormula(String value)
    {
        getSemanticObject().setProperty(bsc_notesFormula, value);
    }

/**
* Gets the Commited property
* @return boolean with the Commited
*/
    public boolean isCommited()
    {
        return getSemanticObject().getBooleanProperty(bsc_commited);
    }

/**
* Sets the Commited property
* @param value long with the Commited
*/
    public void setCommited(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_commited, value);
    }

/**
* Gets the Undeleteable property
* @return boolean with the Undeleteable
*/
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

/**
* Sets the Undeleteable property
* @param value long with the Undeleteable
*/
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

/**
* Gets the InformationSource property
* @return String with the InformationSource
*/
    public String getInformationSource()
    {
        return getSemanticObject().getProperty(bsc_informationSource);
    }

/**
* Sets the InformationSource property
* @param value long with the InformationSource
*/
    public void setInformationSource(String value)
    {
        getSemanticObject().setProperty(bsc_informationSource, value);
    }

/**
* Gets the Goals property
* @return String with the Goals
*/
    public String getGoals()
    {
        return getSemanticObject().getProperty(bsc_goals);
    }

/**
* Sets the Goals property
* @param value long with the Goals
*/
    public void setGoals(String value)
    {
        getSemanticObject().setProperty(bsc_goals, value);
    }
   /**
   * Sets the value for the property Periodicity
   * @param value Periodicity to set
   */

    public void setPeriodicity(org.semanticwb.bsc.accessory.MeasurementFrequency value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_periodicity, value.getSemanticObject());
        }else
        {
            removePeriodicity();
        }
    }
   /**
   * Remove the value for Periodicity property
   */

    public void removePeriodicity()
    {
        getSemanticObject().removeProperty(bsc_periodicity);
    }

   /**
   * Gets the Periodicity
   * @return a org.semanticwb.bsc.accessory.MeasurementFrequency
   */
    public org.semanticwb.bsc.accessory.MeasurementFrequency getPeriodicity()
    {
         org.semanticwb.bsc.accessory.MeasurementFrequency ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_periodicity);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.MeasurementFrequency)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Serial property
* @return int with the Serial
*/
    public int getSerial()
    {
        return getSemanticObject().getIntProperty(bsc_serial);
    }

/**
* Sets the Serial property
* @param value long with the Serial
*/
    public void setSerial(int value)
    {
        getSemanticObject().setIntProperty(bsc_serial, value);
    }

/**
* Gets the Formula property
* @return String with the Formula
*/
    public String getFormula()
    {
        return getSemanticObject().getProperty(bsc_formula);
    }

/**
* Sets the Formula property
* @param value long with the Formula
*/
    public void setFormula(String value)
    {
        getSemanticObject().setProperty(bsc_formula, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
