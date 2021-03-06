/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Element;
import java.util.Date;
import java.io.File;
import org.semanticwb.process.modeler.TransactionSubProcess;
import org.semanticwb.process.modeler.TimerStartEvent;
import org.semanticwb.process.modeler.RuleStartEvent;
import org.semanticwb.process.modeler.SignalStartEvent;
import org.semanticwb.process.modeler.MultipleStartEvent;
import org.semanticwb.process.modeler.AdhocSubProcess;
import org.semanticwb.process.modeler.TimerIntermediateCatchEvent;
import org.semanticwb.process.modeler.IntermediateCatchEvent;
import org.semanticwb.process.modeler.RuleIntermediateCatchEvent;
import org.semanticwb.process.modeler.SignalIntermediateCatchEvent;
import org.semanticwb.process.modeler.MultipleIntermediateCatchEvent;
import org.semanticwb.process.modeler.MultipleIntermediateThrowEvent;
import org.semanticwb.process.modeler.LinkIntermediateCatchEvent;
import org.semanticwb.process.modeler.IntermediateThrowEvent;
import org.semanticwb.process.modeler.ErrorIntermediateCatchEvent;
import org.semanticwb.process.modeler.CancelationIntermediateCatchEvent;
import org.semanticwb.process.modeler.CancelationEndEvent;
import org.w3c.dom.DOMException;
import java.lang.IllegalArgumentException;
import javax.xml.transform.TransformerException;
import org.semanticwb.process.modeler.ExclusiveGateway;
import org.semanticwb.process.modeler.ExclusiveIntermediateEventGateway;
import org.semanticwb.process.modeler.ParallelGateway;
import org.semanticwb.process.modeler.ParallelStartEventGateway;
import org.semanticwb.process.modeler.InclusiveGateway;
import org.semanticwb.process.modeler.ExclusiveStartEventGateway;
import org.semanticwb.process.modeler.ComplexGateway;
import org.semanticwb.process.modeler.AnnotationArtifact;
import javafx.util.Sequences;
import org.semanticwb.process.modeler.EventBasedGateway;

/**
 *   Clase que implementa la funcionalidad para generar un archivo XPDL a partir
 *   de los elementos en el modelador.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public class XPDLTransformer {
    def namespaceUri: String = "http://www.wfmc.org/2008/XPDL2.1";
    def xsi: String = "http://www.w3.org/2001/XMLSchema-instance";
    def xsiLocation = "http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/docs/bpmnxpdl_31.xsd";
    def namespacePrefix: String = "xpdl";
    def toolId: String = "SemanticWebBuilder_Process_Modeler";
    def vendorName: String = "INFOTEC";
    def xpdlVersion: String = "2.1";
    def ORIENTATION_HORIZONTAL = "HORIZONTAL";
    def ORIENTATION_VERTICAL = "VERTICAL";
    var doc: Document;
    var partners: Element;
    var pools: Element;
    var messages: Element;
    var associations: Element;
    var artifacts: Element;
    var workflows: Element;
    var pkg:Element;

    postinit  {
        var dbfac = DocumentBuilderFactory.newInstance();
        var docBuilder = dbfac.newDocumentBuilder();
        doc = docBuilder.newDocument();
        try {
            doc.setXmlStandalone(false);
            doc.setXmlVersion("1.0");
        } catch(ex : DOMException) {
            ex.printStackTrace();
        }
        
        workflows = null;
        artifacts = null;
        associations = null;
        getPackageDefinition();
    }

    /**Recorre los elementos en el modelador para generar las definiciones correspondientes en XPDL*/
    public function getXPDLDocument(modeler: Modeler) {//: Document {
        for (ele in modeler.contents) {
            if (ele instanceof Pool) {
                addPoolDefinition(ele as Pool);
            } else if (ele instanceof Artifact) {
                if (artifacts == null) {
                    artifacts = doc.createElementNS(namespaceUri, "{namespacePrefix}:Artifacts");
                    addChild(pkg, artifacts);
                }
                addChild(artifacts, getArtifactDefinition(ele as Artifact));
            } else if (ele instanceof DataObject) {
                if (artifacts == null) {
                    artifacts = doc.createElementNS(namespaceUri, "{namespacePrefix}:Artifacts");
                    addChild(pkg, artifacts);
                }
                addChild(artifacts, getDataObjectDefinition(ele as DataObject))
            } else if (ele instanceof AssociationFlow) {
                if (associations == null) {
                    associations = doc.createElementNS(namespaceUri, "{namespacePrefix}:Associations");
                    addChild(pkg, associations);
                }
                addChild(associations, getAssociationDefinition(ele as AssociationFlow));
            }
        }
        
        //return doc;
    }

    /**Obtiene la definición XPDL de una asociación*/
    public function getAssociationDefinition(assoc: AssociationFlow) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Association");

        addAttribute(ret, "Id", assoc.getURI());
        addAttribute(ret, "Source", assoc.ini.getURI());
        addAttribute(ret, "Target", assoc.end.getURI());
        if (assoc instanceof DirectionalAssociation) {
            addAttribute(ret, "AssociationDirection", "From");
        } else {
            addAttribute(ret, "AssociationDirection", "None");
        }
        addChild(ret, getConnectorGraphicsInfos(assoc));
        return ret;
    }

    /**Guarda la estructura del documento XPDL en un archivo*/
    public function saveXPDL(file : File) {
        var transf = TransformerFactory.newInstance();
        var trans = transf.newTransformer();
        try {
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
        } catch(ex : IllegalArgumentException) {
            ex.printStackTrace();
        }

        var result = new StreamResult(file);
        var source = new DOMSource(doc);
        try {
            trans.transform(source, result);
        } catch(ex : TransformerException) {
            ex.printStackTrace();
        }
    }

    /**Obtiene la definición XPDL de la información gráfica agregada por distintas herramientas a un elemento*/
    function getGraphicsInfos(ge: GraphicalElement): Element {
        var graphicInfos = doc.createElementNS(namespaceUri, "{namespacePrefix}:NodeGraphicsInfos");
        var graphicInfo = doc.createElementNS(namespaceUri, "{namespacePrefix}:NodeGraphicsInfo");
        var coords = doc.createElementNS(namespaceUri, "{namespacePrefix}:Coordinates");

        addChild(graphicInfo, coords);
        addChild(graphicInfos, graphicInfo);

        addAttribute(graphicInfo, "ToolId", toolId);
        addAttribute(graphicInfo, "IsVisible", "{ge.canView()}");
        addAttribute(graphicInfo, "Width", "{ge.w}");
        addAttribute(graphicInfo, "Height", "{ge.h}");

        if (ge.getGraphParent() instanceof Lane) {
            addAttribute(graphicInfo, "LaneId", "{ge.getGraphParent().uri}");
        }

        addAttribute(coords, "XCoordinate", "{ge.localToScene(ge.layoutBounds.minX, ge.layoutBounds.minY).x}");
        addAttribute(coords, "YCoordinate", "{ge.localToScene(ge.layoutBounds.minX, ge.layoutBounds.minY).y}");
        return graphicInfos;
    }

    /**Obtiene la definición XPDL de la información gráfica agregada por SWBP Modeler a un elemento*/
    function getConnectorGraphicsInfos(conn: ConnectionObject): Element {
        var graphicInfos = doc.createElementNS(namespaceUri, "{namespacePrefix}:ConnectorGraphicsInfos");
        var graphicInfo = doc.createElementNS(namespaceUri, "{namespacePrefix}:ConnectorGraphicsInfo");        

        addChild(graphicInfos, graphicInfo);
        addAttribute(graphicInfo, "ToolId", toolId);
        addAttribute(graphicInfo, "IsVisible", "{conn.canView()}");

        for (p in conn.getPoints()) {
            var coords = doc.createElementNS(namespaceUri, "{namespacePrefix}:Coordinates");
            addAttribute(coords, "XCoordinate", "{p.x}");
            addAttribute(coords, "YCoordinate", "{p.y}");
            addChild(graphicInfo, coords);
        }
        return graphicInfos;
    }

    /**Obtiene la definición XPDL del paquete de procesos*/
    public function getPackageDefinition() {
        if (pkg == null) {
            pkg = doc.createElementNS(namespaceUri, "{namespacePrefix}:Package");
            try {
                doc.appendChild(pkg);
            } catch (e: DOMException) {
                e.printStackTrace();
            }
        }
        pools = doc.createElementNS(namespaceUri, "{namespacePrefix}:Pools");

        addAttribute(pkg, "xmlns", namespaceUri);
        addAttribute(pkg, "xmlns:xsi", xsi);
        addAttribute(pkg, "xsi:location", xsiLocation);
        //TODO: Ver cómo se van a crear los IDs y nombres para los paquetes (pueden ser los grupos de procesos?)
        addAttribute(pkg, "Id", "processID");
        addAttribute(pkg, "Name", "processName");

        addChild(pkg, getPackageHeader());
        addChild(pkg, pools);
    }

    /**Obtiene la definición XPDL del encabezado del paquete de procesos*/
    function getPackageHeader() : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:PackageHeader");
        var version = doc.createElementNS(namespaceUri, "{namespacePrefix}:XPDLVersion");
        var vendor = doc.createElementNS(namespaceUri, "{namespacePrefix}:Vendor");
        var created = doc.createElementNS(namespaceUri, "{namespacePrefix}:Created");

        try {
            version.setTextContent(xpdlVersion);
            vendor.setTextContent(vendorName);
            created.setTextContent(new Date().toString());
        } catch (e: DOMException) {
            e.printStackTrace();
        }

        addChild(ret, version);
        addChild(ret, vendor);
        addChild(ret, created);
        return ret;
    }

    /**Agrega la definición XPDL de un Pool a la lista de Pools del documento,
     de igual manera agrega una definición de proceso a la lista de flujos de trabajo*/
    function addPoolDefinition(pool: Pool) {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Pool");
        var lanes = doc.createElementNS(namespaceUri, "{namespacePrefix}:Lanes");

        addAttribute(ret, "BoundaryVisible", "true");
        addAttribute(ret, "Id", pool.getURI());
        addAttribute(ret, "MainPool", "false");
        addAttribute(ret, "Name", pool.getTitle());
        addAttribute(ret, "Orientation", ORIENTATION_HORIZONTAL);
        addAttribute(ret, "Process", pool.getURI());

        addChild(ret, getGraphicsInfos(pool));
        if (not pool.lanes.isEmpty()) {
            for (lane in pool.lanes) {
                addChild(lanes, getLaneDefinition(lane));
            }
            addChild(ret, lanes);
        }
        addChild(pools, ret);
        addWorkFlowDefinition(pool);
    }

    /**agrega una definición XPDL de un subproceso*/
    function addActivitySetDefinition(subprocess: SubProcess, actSet: Element) : Void {
        var connections:ConnectionObject[];
        var aset = doc.createElementNS(namespaceUri, "{namespacePrefix}:ActivitySet");
        var activities = doc.createElementNS(namespaceUri, "{namespacePrefix}:Activities");
        var transitions = doc.createElementNS(namespaceUri, "{namespacePrefix}:Transitions");

        addAttribute(aset, "Id", "{subprocess.getURI()}set");
        addAttribute(aset, "Name", subprocess.getTitle());
        if (subprocess instanceof AdhocSubProcess) {
            addAttribute(aset, "AdHoc", "true");
            addAttribute(aset, "AdHocOrdering", "Parallel");
        } else {
            addAttribute(aset, "AdHoc", "false");
        }

        var hasActivities = false;
        var hasTransitions = false;
        for (child in subprocess.getContainerChilds()) {
            addChild(activities, getActivityDefinition(child));
            if (hasActivities == false) {
                hasActivities = true;
            }
            if (child instanceof SubProcess) {
                addActivitySetDefinition(child as SubProcess, actSet);
            }
        }

        for (con in subprocess.modeler.contents where con instanceof ConnectionObject) {
            var c = con as ConnectionObject;
            if (c instanceof AssociationFlow) {

            } else if (c.ini.container.equals(subprocess) or c.end.container.equals(subprocess)) {
                if (hasTransitions == false) {
                    hasTransitions = true;
                }
                if (Sequences.indexOf(connections, c) == -1) {
                    insert c into connections;
                }
            }
        }

        if (hasActivities) {
            addChild(aset, activities);
        }

        if (hasTransitions) {
            for (c in connections) {
                addChild(transitions, getTransitionDefinition(c));
            }
            addChild(aset, transitions);
        }
        addChild(actSet, aset);
    }

    /**Obtiene la definición XPDL de una actividad (cuaquier elemento en el modelo)*/
    function getActivityDefinition(ge: GraphicalElement) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Activity");

        addAttribute(ret, "StartQuantity", "1");
        addAttribute(ret, "CompletionQuantity", "1");
        addAttribute(ret, "Id", ge.getURI());
        addAttribute(ret, "Name", ge.getTitle());
        addAttribute(ret, "IsForCompensation", "{ge.isForCompensation}");

        if (ge instanceof TransactionSubProcess) {
            addAttribute(ret, "IsATransaction", "true");
        }

        if (ge instanceof SubProcess) {
            var block = doc.createElementNS(namespaceUri, "{namespacePrefix}:BlockActivity");
            addAttribute(block, "ActivitySetId", "{ge.getURI()}set");
            addAttribute(block, "View", "COLLAPSED");
            addChild(ret, block);
        }

        if (ge.isLoop) {
            var loop = doc.createElementNS(namespaceUri, "{namespacePrefix}:Loop");
            var standard = doc.createElementNS(namespaceUri, "{namespacePrefix}:LoopStandard");

            addAttribute(standard, "TestTime", "After");
            addAttribute(loop, "LoopType", "Standard");
            addChild(loop, standard);
            addChild(ret, loop);
        }

        if (ge.isSequentialMultiInstance or ge.isMultiInstance) {
            var loop = doc.createElementNS(namespaceUri, "{namespacePrefix}:Loop");
            var multi = doc.createElementNS(namespaceUri, "{namespacePrefix}:LoopMultiInstance");

            addAttribute(multi, "MI_FlowCondition", "All");
            if (ge.isSequentialMultiInstance) {
                addAttribute(multi, "MI_Ordering", "Sequential");
            } else if (ge.isMultiInstance) {
                addAttribute(multi, "MI_Ordering", "Parallel");
            }
            addAttribute(loop, "LoopType", "MultiInstance");
            addChild(loop, multi);
            addChild(ret, loop);
        }
        
        if (ge instanceof Event) {
            addChild(ret, getEventDefinition(ge as Event));
        }

        if (ge instanceof Task) {
            addChild(ret, getTaskDefinition(ge as Task));
        }

        if (ge instanceof Gateway) {
            addChild(ret, getGatewayDefinition(ge as Gateway));
        }

        addTransitionRestrictions(ret, ge);
        addChild(ret, getGraphicsInfos(ge));
        return ret;
    }

    /**Obtiene la definición XPDL de un artefacto*/
    function getArtifactDefinition(artif: Artifact) : Element {
        var artifact = doc.createElementNS(namespaceUri, "{namespacePrefix}:Artifact");

        addAttribute(artifact, "Id", artif.getURI());
        addAttribute(artifact, "Name", artif.getURI());
        if (artif instanceof AnnotationArtifact) {
            addAttribute(artifact, "ArtifactType", "Annotation");
            addAttribute(artifact, "TextAnnotation", artif.getTitle());

        } else if (artif instanceof GroupArtifact) {
            addAttribute(artifact, "ArtifactType", "Group");
        }

        addChild(artifact, getGraphicsInfos(artif));
        return artifact;
    }

    /**Obtiene la definición XPDL de un Objeto de datos*/
    function getDataObjectDefinition(dataObject: DataObject) : Element {
        var artifact = doc.createElementNS(namespaceUri, "{namespacePrefix}:Artifact");
        var data = doc.createElementNS(namespaceUri, "{namespacePrefix}:DataObject");

        addAttribute(artifact, "Id", dataObject.getURI());
        addAttribute(artifact, "Name", dataObject.getURI());
        addAttribute(data, "Id", dataObject.getURI());
        addAttribute(data, "Name", dataObject.getTitle());
        addAttribute(artifact, "ArtifactType", "DataObject");
        addChild(artifact, data);
        addChild(artifact, getGraphicsInfos(dataObject));
        
        return artifact;
    }

    /**Obtiene las restricciones de flujos de salida y entrada del elemento*/
    function addTransitionRestrictions(activity: Element, ge: GraphicalElement) : Void {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:TransitionRestrictions");
        var tr = doc.createElementNS(namespaceUri, "{namespacePrefix}:TransitionRestriction");
        var type = "Exclusive";
        var etype = "Data";
        var addOuts = false;
        var addIns = false;

        if (ge.getOutputConnectionObjects().size() > 1) {
            for (ele in ge.getOutputConnectionObjects() where ele instanceof SequenceFlow) {
                if (not addOuts) {
                    addOuts = true;
                }
            }
        }

        if (ge.getInputConnectionObjects().size() > 1) {
            for (ele in ge.getInputConnectionObjects() where ele instanceof SequenceFlow) {
                if (not addIns) {
                    addIns = true;
                }
            }
        }

        if (ge instanceof ExclusiveGateway) {
            type = "Exclusive";
        } else if (ge instanceof InclusiveGateway) {
            type = "Inclusive";
        } else if (ge instanceof ComplexGateway) {
            type = "Complex";
        } else if (ge instanceof ParallelGateway) {
            type = "Parallel";
        } else if (ge instanceof EventBasedGateway) {
            type = "Exclusive";
            etype= "Event";
        } else if (ge instanceof ExclusiveStartEventGateway) {
            type = "Exclusive";
            etype= "Event";
        } else if (ge instanceof ParallelStartEventGateway) {
            type = "Parallel";
            etype= "Event";
        }

        if (addOuts) {
            var split = doc.createElementNS(namespaceUri, "{namespacePrefix}:Split");
            var refs = doc.createElementNS(namespaceUri, "{namespacePrefix}:TransitionRefs");

            if (ge instanceof Gateway) {
                addAttribute(split, "type", type);
                addAttribute(split, "ExclusiveType", etype);
            } else {
                addAttribute(split, "type", "Inclusive");
            }

            for (ele in ge.getOutputConnectionObjects() where ele instanceof SequenceFlow) {
                var ref = doc.createElementNS(namespaceUri, "{namespacePrefix}:TransitionRef");
                addAttribute(ref, "Id", ele.getURI());
                addChild(refs, ref);
            }
            addChild(split, refs);
            addChild(tr, split);
            addChild(ret, tr);
        }

        if (addIns) {
            var join = doc.createElementNS(namespaceUri, "{namespacePrefix}:Join");

            if (ge instanceof Gateway) {
                addAttribute(join, "type", type);
            } else {
                addAttribute(join, "type", "Exclusive");
            }
            addChild(tr, join);
            addChild(ret, tr);
        }
        
        if (addIns or addOuts) {
            addChild(activity, ret);
        }
    }

    /**Obtiene la definición XPDL de un Gateway*/
    function getGatewayDefinition(gateway: Gateway) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Route");
        var instantiate = "false";
        //TODO: Agregar las restricciones de los flujos de la compuerta
        if (gateway instanceof ExclusiveGateway) {
            addAttribute(ret, "GatewayType", "Exclusive");
            addAttribute(ret, "ExclusiveType", "Data");
            addAttribute(ret, "MarkerVisible", "true");
        } else if (gateway instanceof ExclusiveIntermediateEventGateway) {
            addAttribute(ret, "GatewayType", "Exclusive");
            addAttribute(ret, "ExclusiveType", "Event");
            addAttribute(ret, "MarkerVisible", "false");
        } else if (gateway instanceof ParallelGateway) {
            addAttribute(ret, "GatewayType", "Parallel");
            addAttribute(ret, "ExclusiveType", "Data");
            addAttribute(ret, "MarkerVisible", "false");
        } else if (gateway instanceof ParallelStartEventGateway) {
            addAttribute(ret, "GatewayType", "Parallel");
            addAttribute(ret, "ExclusiveType", "Event");
            addAttribute(ret, "MarkerVisible", "false");
            instantiate = "true";
        } else if (gateway instanceof InclusiveGateway) {
            addAttribute(ret, "GatewayType", "Inclusive");
            addAttribute(ret, "ExclusiveType", "Data");
            addAttribute(ret, "MarkerVisible", "false");
        } else if (gateway instanceof ExclusiveStartEventGateway) {
            addAttribute(ret, "GatewayType", "Exclusive");
            addAttribute(ret, "ExclusiveType", "Event");
            addAttribute(ret, "MarkerVisible", "false");
            instantiate = "true";
        } else if (gateway instanceof ComplexGateway) {
            addAttribute(ret, "GatewayType", "Complex");
            addAttribute(ret, "ExclusiveType", "Data");
            addAttribute(ret, "MarkerVisible", "false");
        }        
        addAttribute(ret, "Instantiate", instantiate);

        return ret;
    }

    /**Obtiene la definición XPDL de una tarea*/
    function getTaskDefinition(task: Task) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Implementation");
        var tsk = doc.createElementNS(namespaceUri, "{namespacePrefix}:Task");
        
        if (task instanceof UserTask) {
            var tskuser = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskUser");
            addAttribute(tskuser, "Implementation", "Unspecified");
            addChild(tsk, tskuser);
            addChild(ret, tsk);
        } else if (task instanceof ServiceTask) {
            var tskserv = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskService");
            addAttribute(tskserv, "Implementation", "WebService");
            addChild(tsk, tskserv);
            addChild(ret, tsk);
        } else if (task instanceof ScriptTask) {
            var tskserv = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskScript");
            addChild(tskserv, doc.createElementNS(namespaceUri, "{namespacePrefix}:Script"));
            addChild(tsk, tskserv);
            addChild(ret, tsk);
        } else if (task instanceof ManualTask) {
            var tskuser = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskManual");
            addChild(tsk, tskuser);
            addChild(ret, tsk);
        } else if (task instanceof SendTask) {
            var tskserv = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskSend");
            addAttribute(tskserv, "Implementation", "WebService");
            addChild(tsk, tskserv);
            addChild(ret, tsk);
        } else if (task instanceof ReceiveTask) {
            var tskserv = doc.createElementNS(namespaceUri, "{namespacePrefix}:TaskReceive");
            addAttribute(tskserv, "Implementation", "WebService");
            addChild(tsk, tskserv);
            addChild(ret, tsk);
        } else {
            addChild(ret, doc.createElementNS(namespaceUri, "{namespacePrefix}:No"));
        }

        return ret;
    }

    /**Agrega la definición XPDL de un flujo de proceso a la lista de flujos de procesos del documento XPDL*/
    public function addWorkFlowDefinition(pool: Pool) {
        var connections: ConnectionObject[];
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:WorkflowProcess");
        var header = doc.createElementNS(namespaceUri, "{namespacePrefix}:ProcessHeader");
        var created = doc.createElementNS(namespaceUri, "{namespacePrefix}:Created");
        var activitySets = doc.createElementNS(namespaceUri, "{namespacePrefix}:ActivitySets");
        var activities = doc.createElementNS(namespaceUri, "{namespacePrefix}:Activities");
        var transitions = doc.createElementNS(namespaceUri, "{namespacePrefix}:Transitions");

        try {
            created.setTextContent(new Date().toString());
        } catch (e: DOMException) {
            e.printStackTrace();
        }

        addAttribute(ret, "Id", pool.getURI());
        addAttribute(ret, "Name", pool.getTitle());
        addChild(header, created);
        addChild(ret, header);

        if (workflows == null) {
            workflows = doc.createElementNS(namespaceUri, "{namespacePrefix}:WorkflowProcesses");
            addChild(pkg, workflows);
        }

        var hasActivities = false;
        var hasActivitySets = false;
        var hasTransitions = false;
        if (not pool.lanes.isEmpty()) {
            for (lane in pool.lanes) {
                for (child in lane.getgraphChilds()) {
                    if (child instanceof Artifact or child instanceof DataObject) {
                        
                    } else {
                        addChild(activities, getActivityDefinition(child));

                        if (hasActivities == false) {
                            hasActivities = true;
                        }
                        if (child instanceof SubProcess) {
                            if (hasActivitySets == false) {
                                hasActivitySets = true;
                            }
                            addActivitySetDefinition(child as SubProcess, activitySets);
                        }
                    }
                }
            }
        } else {
            for (child in pool.getgraphChilds()) {
                if (child instanceof Artifact or child instanceof DataObject) {
                    
                } else {
                    addChild(activities, getActivityDefinition(child));

                    if (hasActivities == false) {
                        hasActivities = true;
                    }
                    if (child instanceof SubProcess) {
                        if (hasActivitySets == false) {
                            hasActivitySets = true;
                        }
                        addActivitySetDefinition(child as SubProcess, activitySets);
                    }
                }
            }
        }

        for (conn in pool.modeler.contents where conn instanceof ConnectionObject) {
            var c = conn as ConnectionObject;
            if (c instanceof AssociationFlow) {

            } else if (c.ini.getPool().equals(pool) or c.end.getPool().equals(pool)) {
                if (c.ini.container == null and c.end.container == null) {
                    if (hasTransitions == false) {
                        hasTransitions = true;
                    }
                    if (Sequences.indexOf(connections, c) == -1) {
                        insert c into connections;
                    }
                }
            }
        }

        if (hasActivities) {
            addChild(ret, activities);
        }
        if (hasActivitySets) {
            addChild(ret, activitySets);
        }
        if (hasTransitions) {
            for (c in connections) {
                addChild(transitions, getTransitionDefinition(c));
            }
            addChild(ret, transitions);
        }

        addChild(workflows, ret);
    }

    /**Obtiene la definición XPDL de los objetos de conexión del diagrama*/
    function getTransitionDefinition(conn: ConnectionObject) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Transition");
        addAttribute(ret, "Id", conn.getURI());
        addAttribute(ret, "Name", conn.getTitle());
        addAttribute(ret, "From", conn.ini.getURI());
        addAttribute(ret, "To", conn.end.getURI());

        if (conn instanceof ConditionalFlow) {
            addAttribute(ret, "Condition", conn.getTitle());
        }
        addChild(ret, getConnectorGraphicsInfos(conn));
        return ret;
    }

    /**Obtiene la definición XPDL de un Lane*/
    function getLaneDefinition(lane: Lane) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Lane");

        addAttribute(ret, "Id", lane.getURI());
        addAttribute(ret, "Name", lane.getTitle());
        if (lane.getGraphParent() instanceof Lane) {
            addAttribute(ret, "ParentLane", lane.getGraphParent().getURI());
        }
        addAttribute(ret, "ParentPool", lane.getPool().getURI());
        addChild(ret, getGraphicsInfos(lane));
        
        return ret;
    }

    /**Obtiene la definición XPDL de un evento*/
    function getEventDefinition(evt: Event) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:Event");
        if (evt instanceof StartEvent) {
            addChild(ret, getStartEventDefinition(evt as StartEvent));
        } else if (evt instanceof IntermediateCatchEvent or evt instanceof IntermediateThrowEvent) {
            addChild(ret, getInterEventDefinition(evt as Event));
        } else if (evt instanceof EndEvent) {
            addChild(ret, getEndEventDefinition(evt as EndEvent));
        }

        return ret;
    }

    /**Obtiene la definición XPDL de un disparador por timer*/
    function getTriggerTimerDefinition() : Element {
        var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerTimer");
        addChild(resMessage, doc.createElementNS(namespaceUri, "{namespacePrefix}:TimeDate"));
        return resMessage;
    }

    /**Obtiene la definición XPDL de un disparador por mensaje*/
    function getTriggerMessageDefinition(isCatch: Boolean) : Element {
        var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerResultMessage");
        if (isCatch) {
            addAttribute(resMessage, "CatchThrow", "CATCH");
        } else {
            addAttribute(resMessage, "CatchThrow", "THROW");
        }
        return resMessage;
    }

    /**Obtiene la definición XPDL de un disparador por señal*/
    function getTriggerSignalDefinition(isCatch: Boolean) : Element {
        var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerResultSignal");
        if (isCatch) {
            addAttribute(resMessage, "CatchThrow", "CATCH");
        } else {
            addAttribute(resMessage, "CatchThrow", "THROW");
        }
        return resMessage;
    }

    /**Obtiene la definición XPDL de un dispadador por enlace*/
    function getTriggerLinkDefinition(isCatch: Boolean) : Element {
        var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerResultLink");
        if (isCatch) {
            addAttribute(resMessage, "CatchThrow", "CATCH");
        } else {
            addAttribute(resMessage, "CatchThrow", "THROW");
        }
        return resMessage;
    }

    /**Obtiene la definición XPDL de un disparador múltiple*/
    function getTriggerMultipleDefinition(isCatch: Boolean) : Element {
        var resMessage = doc.createElementNS(namespaceUri, "{namespacePrefix}:TriggerMultiple");
        addChild(resMessage, getTriggerMessageDefinition(isCatch));
        addChild(resMessage, getTriggerTimerDefinition());
        return resMessage;
    }

    /**Obtiene la definición XPDL de un evento de inicio*/
    function getStartEventDefinition(evt: StartEvent) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:StartEvent");
        var trig = "None";
        
        if (evt instanceof TimerStartEvent) {
            addChild(ret, getTriggerTimerDefinition());
            trig = "Timer";
        } else if (evt instanceof MessageStartEvent) {
            addAttribute(ret, "Implementation", "Unspecified");
            addChild(ret, getTriggerMessageDefinition(true));
            trig = "Message";
        } else if (evt instanceof RuleStartEvent) {
            trig = "Conditional"
        } else if (evt instanceof SignalStartEvent) {
            addChild(ret, getTriggerSignalDefinition(true));
            trig = "Signal";
        } else if (evt instanceof MultipleStartEvent) {
            addChild(ret, getTriggerMultipleDefinition(true));
            trig = "Multiple";
        }

        addAttribute(ret, "Trigger", trig);
        return ret;
    }

    /**Obtiene la definición XPDL de un evento intermedio*/
    function getInterEventDefinition(evt: Event) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:IntermediateEvent");
        var trig = "None";

        if (evt instanceof TimerIntermediateCatchEvent) {
            addChild(ret, getTriggerTimerDefinition());
            trig = "Timer";
        } else if (evt instanceof MessageIntermediateCatchEvent) {
            addAttribute(ret, "Implementation", "Unspecified");
            addChild(ret, getTriggerMessageDefinition(true));
            trig = "Message";
        }  else if (evt instanceof MessageIntermediateThrowEvent) {
            addAttribute(ret, "Implementation", "Unspecified");
            addChild(ret, getTriggerMessageDefinition(false));
            trig = "Message";
        } else if (evt instanceof RuleIntermediateCatchEvent) {
            trig = "Conditional"
        } else if (evt instanceof SignalIntermediateCatchEvent) {
            addChild(ret, getTriggerSignalDefinition(true));
            trig = "Signal";
        } else if (evt instanceof SignalIntermediateThrowEvent) {
            addChild(ret, getTriggerSignalDefinition(false));
            trig = "Signal";
        } else if (evt instanceof MultipleIntermediateCatchEvent) {
            addChild(ret, getTriggerMultipleDefinition(true));
            trig = "Multiple";
        } else if (evt instanceof MultipleIntermediateThrowEvent) {
            addChild(ret, getTriggerMultipleDefinition(false));
            trig = "Multiple";
        } else if (evt instanceof LinkIntermediateCatchEvent) {
            trig = "Link";
            addChild(ret, getTriggerLinkDefinition(true));
        } else if (evt instanceof LinkIntermediateThrowEvent) {
            trig = "Link";
            addChild(ret, getTriggerLinkDefinition(false));
        } else if (evt instanceof ErrorIntermediateCatchEvent) {
            trig = "Error";
        } else if (evt instanceof CancelationIntermediateCatchEvent ) {
            trig = "Cancel";
        } else if (evt instanceof CompensationIntermediateCatchEvent or evt instanceof CompensationIntermediateThrowEvent) {
            trig = "Compensation";
        }

        addAttribute(ret, "Trigger", trig);
        return ret;
    }

    /**Obtiene la definición XPDL de un evento final*/
    function getEndEventDefinition(evt: EndEvent) : Element {
        var ret = doc.createElementNS(namespaceUri, "{namespacePrefix}:EndEvent");
        var trig = "None";

        if (evt instanceof MessageEndEvent) {
            addChild(ret, getTriggerMessageDefinition(false));
            addAttribute(ret, "Implementation", "Unspecified");
            trig = "Message";
        } else if (evt instanceof ErrorEndEvent) {
            trig = "Error"
        } else if (evt instanceof CancelationEndEvent) {
            trig = "Cancel";
        } else if (evt instanceof CompensationEndEvent) {
            trig = "Compensation";
        } else if (evt instanceof SignalEndEvent) {
            addChild(ret, getTriggerSignalDefinition(false));
            trig = "Signal";
        } else if (evt instanceof MultipleEndEvent) {
            addChild(ret, getTriggerMultipleDefinition(false));
            trig = "Multiple";
        } else if (evt instanceof TerminationEndEvent) {
            trig = "Terminate";
        }
        addAttribute(ret, "Result", trig);
        return ret;
    }

    /**Agrega un atributo al nodo especificado con el valor especificado*/
    function addAttribute(ele: Element, att : String, val : String) {
        try {
            ele.setAttribute(att, val);
        } catch(ex : DOMException) {
            ex.printStackTrace();
        }
    }

    /**Agrega un nodo como hijo del nodo especificado*/
    function addChild(ele: Element, child: Element) {
        try {
            ele.appendChild(child);
        } catch(ex : DOMException) {
            ex.printStackTrace();
        }
    }
}
