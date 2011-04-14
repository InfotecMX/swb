package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class Process extends org.semanticwb.process.model.base.ProcessBase 
{
    public Process(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Crea una instancia del Proceso
     * @param fobj
     * @param pinst
     * @return
     */
    public ProcessInstance createInstance()
    {
        //System.out.println("createInstance process:"+this);
        ProcessInstance inst=null;
        inst=this.getProcessSite().createProcessInstance();
        inst.setProcessType(this);
        inst.setStatus(Instance.STATUS_INIT);

        Iterator<ItemAware> it=listRelatedItemAware().iterator();
        while (it.hasNext())
        {
            ItemAware item = it.next();
            SemanticClass scls=null;
            SemanticProperty sprop=null;
            SWBModel model=this.getProcessSite();
            if(item instanceof DataStore)
            {
                DataStore store=(DataStore)item;
                if(store.getDataStoreClass()!=null)
                {
                    scls=store.getDataStoreClass().transformToSemanticClass();
                }
            }else if(item instanceof DataObjectItemAware)
            {
                DataObjectItemAware data=(DataObjectItemAware)item;
                if(data.getDataObjectProperty()!=null)
                {
                    sprop=data.getDataObjectProperty().transformToSemanticProperty();
                }else if(data.getDataObjectClass()!=null)
                {
                    scls=data.getDataObjectClass().transformToSemanticClass();
                }
                model=this.getProcessSite().getProcessDataInstanceModel();
            }

            if(scls!=null)
            {
                ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
                ref.setItemAware(item);

                long id=model.getSemanticModel().getCounter(scls);
                SemanticObject ins=model.getSemanticModel().createSemanticObjectById(String.valueOf(id), scls);
                ref.setProcessObject((SWBClass)ins.createGenericInstance());
                inst.addItemAwareReference(ref);
                //System.out.println("addItemAwareReference:"+ref);
            }
            //TODO: Si es una propiedad
        }

        return inst;
    }

    /**
     * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware Globales)
     * @return
     */
    public List<ItemAware> listRelatedItemAware()
    {
        //System.out.println("getRelatedItemAwareClasses:"+this);
        ArrayList<ItemAware> arr=new ArrayList();
        Iterator<GraphicalElement> it=listContaineds();
        while (it.hasNext())
        {
            GraphicalElement graphicalElement = it.next();
            if(graphicalElement instanceof ItemAware)
            {
                ItemAware item=(ItemAware)graphicalElement;

                if(!item.listInputConnectionObjects().hasNext() && !item.listOutputConnectionObjects().hasNext())
                {
                    arr.add(item);
                }
            }
        }
        return arr;
    }

    /**
     * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware Globales)
     * @return
     */
    public List<ItemAware> listHerarquicalRelatedItemAware()
    {
        //System.out.println("getHerarquicalRelatedItemAwareClasses:"+this);
        return listRelatedItemAware();
    }

}
