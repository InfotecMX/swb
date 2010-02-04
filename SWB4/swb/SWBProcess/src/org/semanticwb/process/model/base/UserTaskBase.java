package org.semanticwb.process.model.base;


public abstract class UserTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.process.model.Observable,org.semanticwb.process.model.Performable,org.semanticwb.process.model.Implementable,org.semanticwb.process.model.Modelable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.IOAble,org.semanticwb.process.model.Messageable,org.semanticwb.process.model.Assignable
{
       public static final org.semanticwb.platform.SemanticClass swp_UserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#UserTask");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#UserTask");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTasks(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTasks()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask>(it, true);
       }

       public static org.semanticwb.process.model.UserTask createUserTask(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.UserTask.ClassMgr.createUserTask(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.UserTask getUserTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.UserTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.UserTask createUserTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.UserTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeUserTask(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasUserTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (getUserTask(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByInMessageRef(org.semanticwb.process.model.Message inmessageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_inMessageRef, inmessageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByInMessageRef(org.semanticwb.process.model.Message inmessageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(inmessageref.getSemanticObject().getModel().listSubjects(swp_inMessageRef,inmessageref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByLane(org.semanticwb.process.model.Lane lane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_lane, lane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByLane(org.semanticwb.process.model.Lane lane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(lane.getSemanticObject().getModel().listSubjects(swp_lane,lane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByIORule(org.semanticwb.process.model.Expression hasiorule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasIORule, hasiorule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByIORule(org.semanticwb.process.model.Expression hasiorule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(hasiorule.getSemanticObject().getModel().listSubjects(swp_hasIORule,hasiorule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByLoopCondition(org.semanticwb.process.model.Expression loopcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_loopCondition, loopcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByLoopCondition(org.semanticwb.process.model.Expression loopcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(loopcondition.getSemanticObject().getModel().listSubjects(swp_loopCondition,loopcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition, complexmiflowcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(complexmiflowcondition.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition,complexmiflowcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByOutMessageRef(org.semanticwb.process.model.Message outmessageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_outMessageRef, outmessageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByOutMessageRef(org.semanticwb.process.model.Message outmessageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(outmessageref.getSemanticObject().getModel().listSubjects(swp_outMessageRef,outmessageref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByMessageRef(org.semanticwb.process.model.Message messageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_messageRef, messageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByMessageRef(org.semanticwb.process.model.Message messageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(messageref.getSemanticObject().getModel().listSubjects(swp_messageRef,messageref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByMICondition(org.semanticwb.process.model.Expression micondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_mICondition, micondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTaskByMICondition(org.semanticwb.process.model.Expression micondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTask> it=new org.semanticwb.model.GenericIterator(micondition.getSemanticObject().getModel().listSubjects(swp_mICondition,micondition.getSemanticObject()));
       return it;
   }
    }

    public UserTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setInMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_inMessageRef, value.getSemanticObject());
    }

    public void removeInMessageRef()
    {
        getSemanticObject().removeProperty(swp_inMessageRef);
    }


    public org.semanticwb.process.model.Message getInMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_inMessageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }

    public String getImplementation()
    {
        return getSemanticObject().getProperty(swp_implementation);
    }

    public void setImplementation(String value)
    {
        getSemanticObject().setProperty(swp_implementation, value);
    }

    public void setOutMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_outMessageRef, value.getSemanticObject());
    }

    public void removeOutMessageRef()
    {
        getSemanticObject().removeProperty(swp_outMessageRef);
    }


    public org.semanticwb.process.model.Message getOutMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outMessageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }

    public void setMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_messageRef, value.getSemanticObject());
    }

    public void removeMessageRef()
    {
        getSemanticObject().removeProperty(swp_messageRef);
    }


    public org.semanticwb.process.model.Message getMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_messageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }
}
