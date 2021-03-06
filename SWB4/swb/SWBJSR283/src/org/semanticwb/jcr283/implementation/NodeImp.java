/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;
import javax.jcr.AccessDeniedException;
import javax.jcr.Binary;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidLifecycleTransitionException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.ItemVisitor;
import javax.jcr.MergeException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.version.ActivityViolationException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class NodeImp extends ItemImp implements Node
{

    private static final String ALL = "*";
    private static final String JCR_CREATED = "jcr:created";
    private static final String JCR_LASTMODIFIED = "jcr:lastModified";
    private static final String JCR_LASTMODIFIEDBY = "jcr:lastModifiedBy";
    private static final String JCR_MIXINTYPES = "jcr:mixinTypes";
    private static final String JCR_NAME = "jcr:name";
    private static final String JCR_PRIMARYTYPE = "jcr:primaryType";
    private static final String JCR_UUID = "jcr:uuid";
    private static final String JCR_ISCHECKEDOUT = "jcr:isCheckedOut";
    private static final String JCR_CREATEDBY = "jcr:createdBy";
    private static final String JCR_VERSION_HISTORY = "jcr:versionHistory";
    private static final String MIX_CREATED = "mix:created";
    private static final String MIX_LAST_MODIFIED = "mix:lastModified";
    private static final String MIX_REFERENCEABLE = "mix:referenceable";
    private static final String MIX_SIMPLEVERSIONABLE = "mix:simpleVersionable";
    private static final String MIX_VERSIONABLE = "mix:versionable";
    private static final String NT_VERSION = "nt:version";
    private static final String SEPARATOR = "/";
    private static final String THE_NODE_IS_NOT_VERSIONABLE = "The node is not versionable";
    private final static Logger log = SWBUtils.getLogger(NodeImp.class);
    
    private SemanticObject obj = null;
    private final int index;
    protected final NodeTypeImp nodeType;
    protected final NodeTypeManagerImp nodeTypeManager;    
    private final VersionManagerImp versionManagerImp;

    protected NodeImp(Base base, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        this(NodeTypeManagerImp.loadNodeType(base.getSemanticObject().getSemanticClass()), NodeDefinitionImp.getNodeDefinition(base, parent, NodeTypeManagerImp.loadNodeType(base.getSemanticObject().getSemanticClass())), base.getName(), parent, index, path, depth, session, base.getId(), false);
        this.obj = base.getSemanticObject();
        loadStoredProperties(false);
        for (PropertyImp prop : nodeManager.getAllChildProperties(path))
        {
            prop.loadValues();
        }
    }

    protected NodeImp(NodeDefinitionImp definition, Base base, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        this(NodeTypeManagerImp.loadNodeType(base.getSemanticObject().getSemanticClass()), definition, base.getName(), parent, index, path, depth, session, base.getId(), false);
        this.obj = base.getSemanticObject();
        loadStoredProperties(false);
        for (PropertyImp prop : nodeManager.getAllChildProperties(path))
        {
            prop.loadValues();
        }
    }

    protected NodeImp(NodeTypeImp nodeType, NodeDefinitionImp nodeDefinition, String name, NodeImp parent, int index, String path, int depth, SessionImp session, String id, boolean isnew)
    {
        super(nodeDefinition, name, parent, path, depth, session,id);
        this.index = index;
        this.isNew = isnew;        
        this.nodeType = nodeType;
        this.nodeTypeManager = session.getWorkspaceImp().getNodeTypeManagerImp();
        versionManagerImp = session.getWorkspaceImp().getVersionManagerImp();
        loadProperties(false);
        if (isnew)
        {
            init();
            try
            {
                PropertyImp jcr_name = nodeManager.getProtectedProperty(getPathFromName(JCR_NAME));
                jcr_name.set(valueFactoryImp.createValue(name));
            }
            catch (Exception e)
            {
                log.debug(e);
            }

        }

    }

    private void init()
    {
        initPrimaryType();
        initReferenceable();
        initMixLastModified();
        initMixCreated();
        initSimpleVersionable();
        initversionNode();
        initVersionable();
    }

    private void initVersionable()
    {
        try
        {
            if (this.isVersionable())
            {
                initVersionHistory();
            }
        }
        catch (RepositoryException re)
        {
            log.error(re);
        }
    }

    private void initVersionHistory() throws RepositoryException
    {
        PropertyImp prop = nodeManager.getProtectedProperty(getPathFromName(JCR_VERSION_HISTORY));
        if (prop.getLength() == -1)
        {
            log.trace("Initilizing versionHistory for node " + path);
            NodeImp jcr_version_Storage = versionManagerImp.getVersionStorage();
            if (jcr_version_Storage == null)
            {
                throw new RepositoryException("The version storage was not found");
            }
            NodeImp temp = jcr_version_Storage.insertNode("jcr:versionHistory");
            VersionHistoryImp history = (VersionHistoryImp) temp;
            history.init(this);
            prop.set(valueFactoryImp.createValue(history));
            nodeManager.addNode(history);
        }
    }

    private void initPrimaryType()
    {
        try
        {
            String propertyPath = getPathFromName(JCR_PRIMARYTYPE);
            PropertyImp prop = nodeManager.getProtectedProperty(propertyPath);
            if (prop.getLength() == -1)
            {
                prop.set(valueFactoryImp.createValue(nodeType.getName()));
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    private void initversionNode()
    {
        try
        {
            if (isVersionNode())
            {
                String propertyPath = getPathFromName(JCR_CREATED);
                PropertyImp prop = nodeManager.getProperty(propertyPath);
                if (prop.getLength() == -1)
                {
                    prop.set(valueFactoryImp.createValue(Calendar.getInstance()));
                }
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
    }

    private void initSimpleVersionable()
    {
        try
        {
            if (isSimpleVersionable())
            {
                String propertyPath = getPathFromName(JCR_ISCHECKEDOUT);
                PropertyImp prop = nodeManager.getProtectedProperty(propertyPath);
                if (prop.getLength() == -1)
                {
                    prop.set(valueFactoryImp.createValue(true));
                }
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
    }

    private void initMixCreated()
    {
        try
        {
            if (isMixCreated())
            {
                String propertyPath = getPathFromName(JCR_CREATED);
                PropertyImp prop = nodeManager.getProtectedProperty(propertyPath);
                if (prop.getLength() == -1)
                {
                    prop.set(valueFactoryImp.createValue(Calendar.getInstance()));
                }
                propertyPath = getPathFromName(JCR_CREATEDBY);
                prop = nodeManager.getProperty(propertyPath);
                if (prop.getLength() == -1)
                {
                    prop.set(valueFactoryImp.createValue(session.getUserID()));
                }

            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
    }

    private void initMixLastModified()
    {
        try
        {
            if (isMixLastModified())
            {
                String propertyPath = getPathFromName(JCR_LASTMODIFIED);
                PropertyImp prop = nodeManager.getProtectedProperty(propertyPath);
                if (prop.getLength() == -1)
                {
                    prop.set(valueFactoryImp.createValue(Calendar.getInstance()));

                }
                propertyPath = getPathFromName(JCR_LASTMODIFIEDBY);
                prop = nodeManager.getProperty(propertyPath);
                if (prop.getLength() == -1)
                {
                    prop.set(valueFactoryImp.createValue(session.getUserID()));
                }
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
    }

    private void initReferenceable()
    {
        try
        {
            if (isReferenceable())
            {
                String propertyPath = getPathFromName(JCR_UUID);
                PropertyImp prop = nodeManager.getProtectedProperty(propertyPath);
                if (prop.getLength() == -1)
                {
                    prop.set(valueFactoryImp.createValue(this.id));
                }
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
    }

    @SuppressWarnings(value = "deprecation")
    public void saveData() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        Base base = null;
        if (obj == null)
        {
            // create new Node
            SemanticClass sclass = this.nodeType.getSemanticClass();
            String workspacename = session.getWorkspaceImp().getName();
            org.semanticwb.jcr283.repository.model.Workspace model = org.semanticwb.jcr283.repository.model.Workspace.ClassMgr.getWorkspace(workspacename);
            log.trace("Creating a semantic node node with id :" + id + " and class " + sclass.getURI());
            obj = model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass).getSemanticObject();
            base = new Base(obj);
            base.setName(this.name);
        }
        else
        {
            base = new Base(obj);
        }
        if (parent == null && !path.equals(SEPARATOR))
        {
            if (parent.getSemanticObject() == null)
            {
                // TODO:ERROR
                throw new RepositoryException("The parent node is null");
            }
            else
            {
                base.setParentNode(new Base(parent.getSemanticObject()));
            }
        }

        if (isModified)
        {
            log.trace("Saving node " + this.path);
            for (NodeImp child : nodeManager.getChildNodes(this))
            {
                child.save();
            }
            for (NodeImp child : nodeManager.getProtectedChildNodes(this.path))
            {
                child.saveData();
            }
            //saveproperties
            for (PropertyImp prop : nodeManager.getChildProperties(this))
            {
                prop.save();
            }
            for (PropertyImp prop : nodeManager.getProtectedChildProperties(path))
            {
                prop.saveData();
            }
            for (NodeImp child : nodeManager.getDeletedChildNodes(path))
            {
                if (child.obj != null)
                {
                    Base.ClassMgr.removeBase(base.getId(), base.getWorkspace());
                }
            }
            nodeManager.clearDeletedChildNodes(path);

        }
        if (base.getParentNode() == null && !path.equals(SEPARATOR))
        {
            base.setParentNode(new Base(parent.obj));
        }
        this.isNew = false;
        this.isModified = false;

    }

    public SemanticObject getSemanticObject()
    {
        return obj;
    }

    private void loadStoredProperties(boolean replace)
    {
        // stored properties
        if (obj != null)
        {
            Iterator<SemanticProperty> props = obj.listProperties();
            while (props.hasNext())
            {
                SemanticProperty semanticProperty = props.next();
                if (semanticProperty.getSemanticObject() != null && semanticProperty.getSemanticObject().getSemanticClass() != null)
                {
                    SemanticClass propertyClazz = semanticProperty.getSemanticObject().getSemanticClass();
                    if (propertyClazz.equals(NodeTypeImp.objectClazz) || propertyClazz.equals(NodeTypeImp.dataClazz))
                    {
                        try
                        {
                            String nameProperty = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                            String pathProperty = getPathFromName(nameProperty);
                            if (!nodeManager.hasProperty(pathProperty))
                            {
                                log.trace("loading property " + pathProperty + " for node " + path);
                                PropertyImp prop = new PropertyImp(semanticProperty, this, pathProperty, this.session);
                                prop.loadValues();
                                nodeManager.addProperty(prop, prop.path, this.path, replace);
                            }
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }

                    }
                }

            }
        }
    }

    private void loadProperties(boolean replace)
    {
        // declared properties
        for (PropertyDefinitionImp propDef : nodeType.getPropertyDefinitionsImp())
        {
            if (propDef.getSemanticProperty() != null)
            {
                SemanticProperty semanticProperty = propDef.getSemanticProperty();
                try
                {
                    String nameProperty = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                    String pathProperty = getPathFromName(nameProperty);
                    if (!nodeManager.hasProperty(pathProperty))
                    {
                        log.trace("Loading property " + pathProperty + " for node " + path);
                        PropertyImp prop = new PropertyImp(semanticProperty, this, pathProperty, this.session);
                        nodeManager.addProperty(prop, prop.path, this.path, false);
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                }

            }
        }



    }

    public Node addNode(String relPath) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        return addNode(relPath, null);
    }

    NodeImp insertNode(String nameToAdd) throws RepositoryException
    {
        return this.insertNode(nameToAdd, null);
    }

    public static final NodeImp createNodeImp(NodeTypeImp nodeType, NodeDefinitionImp nodeDefinition, String name, NodeImp parent, int index, String path, SessionImp session, String id) throws RepositoryException
    {
        if (nodeType.getSemanticClass().equals(org.semanticwb.jcr283.repository.model.Version.sclass))
        {
            return new VersionImp(nodeType, nodeDefinition, name, parent, index, path, index, session, id, true);
        }
        else if (nodeType.getSemanticClass().equals(org.semanticwb.jcr283.repository.model.VersionHistory.sclass))
        {
            return new VersionHistoryImp(nodeType, nodeDefinition, name, parent, index, path, index, session, id, true);
        }
        else
        {
            return new NodeImp(nodeType, nodeDefinition, name, parent, index, path, parent.getDepth() + 1, session, id, true);
        }
    }

    public final static NodeImp createNodeImp(Base base, NodeImp parent, int index, String path, SessionImp session) throws RepositoryException
    {
        if (base.getSemanticObject().getSemanticClass() == org.semanticwb.jcr283.repository.model.Version.sclass && base instanceof org.semanticwb.jcr283.repository.model.Version)
        {
            return new VersionImp((org.semanticwb.jcr283.repository.model.Version) base, (VersionHistoryImp) parent, index, path, parent.getDepth() + 1, session);
        }
        else if (base.getSemanticObject().getSemanticClass() == org.semanticwb.jcr283.repository.model.VersionHistory.sclass && base instanceof org.semanticwb.jcr283.repository.model.VersionHistory)
        {
            return new VersionHistoryImp((org.semanticwb.jcr283.repository.model.VersionHistory) base, parent, index, path, parent.getDepth() + 1, session);
        }
        else
        {
            return new NodeImp(base, parent, index, path, parent.getDepth() + 1, session);
        }
    }

    NodeImp insertNode(String nameToAdd, String primaryNodeTypeName) throws RepositoryException
    {
        NodeTypeImp primaryNodeType = null;
        if (primaryNodeTypeName != null)
        {
            primaryNodeType = nodeTypeManager.getNodeTypeImp(primaryNodeTypeName);
            if (primaryNodeType == null)
            {
                throw new NoSuchNodeTypeException("The NodeType " + primaryNodeTypeName + " was not found");
            }
        }
        NodeDefinitionImp childDefinition = NodeDefinitionImp.getNodeDefinition(nameToAdd, this, primaryNodeType);
        if (childDefinition == null)
        {
            throw new ConstraintViolationException("The node can not be added");
        }


        if (primaryNodeTypeName == null)
        {
            primaryNodeType = childDefinition.getDefaultPrimaryTypeImp();
            primaryNodeTypeName = primaryNodeType.getName();
        }
        boolean isConformToRequired = false;

        for (NodeTypeImp required : childDefinition.getRequiredPrimaryTypesImp())
        {
            if (required.equals(primaryNodeType) || primaryNodeType.isNodeType(required.getName()))
            {
                isConformToRequired = true;
                break;
            }
        }
        if (!isConformToRequired)
        {
            throw new ConstraintViolationException("The NodeType " + primaryNodeTypeName + " is not part of required node types ");
        }
        if (!childDefinition.allowsSameNameSiblings() && nodeManager.hasNode(path, nameToAdd))
        {
            throw new ItemExistsException("There is a node with the same name in the node " + this.path);
        }
        String newId = UUID.randomUUID().toString();
        String childpath = getPathFromName(nameToAdd);
        int childIndex = nodeManager.countNodes(nameToAdd, this, session, false, newId);
        if (childIndex > 0)
        {
            childpath += "[" + childIndex + "]";
        }
        log.trace("Creating the node " + nameToAdd);
        NodeImp newChild = createNodeImp(primaryNodeType, childDefinition, nameToAdd, this, childIndex, childpath, session, newId);
        this.isModified = true;
        return nodeManager.addNode(newChild);

    }

    public Node addNode(String relPath, String primaryNodeTypeName) throws ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, RepositoryException
    {
        if (this.definition.isProtected())
        {
            throw new ConstraintViolationException("The node " + path + " is protected");
        }
        if (!isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        String absPath = normalizePath(relPath);
        String nameToAdd = extractName(absPath);
        if (!isValidName(nameToAdd))
        {
            throw new RepositoryException("The name for the new node is invalid");
        }
        String tempPath = absPath;
        if (!tempPath.endsWith(SEPARATOR))
        {
            tempPath += SEPARATOR;
        }
        String parentPath = normalizePath("." + tempPath + "../");
        NodeImp nodeParent = nodeManager.getNode(parentPath, session);
        if (nodeParent == null)
        {
            throw new PathNotFoundException("The node with path " + relPath + " was not found");
        }
        return nodeParent.insertNode(nameToAdd, primaryNodeTypeName);


    }

    public void orderBefore(String srcChildRelPath, String destChildRelPath) throws UnsupportedRepositoryOperationException, VersionException, ConstraintViolationException, ItemNotFoundException, LockException, RepositoryException
    {
        if (this.definition.isProtected())
        {
            throw new ConstraintViolationException("The node " + path + " is protected");
        }
        if (!isValidRelativePath(srcChildRelPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + srcChildRelPath);
        }
        if (!isValidRelativePath(destChildRelPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + destChildRelPath);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Property setProperty(String name, Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, value, value.getType());
    }

    public Property setProperty(String name, Value value, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, new Value[]
                {
                    value
                }, type);
    }

    public Property setProperty(String name, Value[] values) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        int type = 0;
        return setProperty(name, values, type);
    }

    public Property setProperty(String name, Value[] values, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        if (!this.getDefinition().getDeclaringNodeType().canSetProperty(name, values))
        {
            // TODO: ERROR
            throw new ConstraintViolationException("The node can not set this property");
        }
        Property prop = this.getProperty(name);
        if (prop != null)
        {
            prop.setValue(values);
        }
        return prop;
    }

    public Property setProperty(String name, String[] values) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        ArrayList<Value> ovalues = new ArrayList<Value>();
        for (String value : values)
        {
            ovalues.add(valueFactoryImp.createValue(value));
        }
        return setProperty(name, ovalues.toArray(new Value[ovalues.size()]));
    }

    public Property setProperty(String name, String[] values, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        ArrayList<Value> ovalues = new ArrayList<Value>();
        for (String value : values)
        {
            ovalues.add(valueFactoryImp.createValue(value));
        }
        return setProperty(name, ovalues.toArray(new Value[ovalues.size()]), type);
    }

    public Property setProperty(String name, String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, String value, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value), type);
    }

    @Deprecated
    public Property setProperty(String name, InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, Binary value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, double value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, BigDecimal value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, long value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, Calendar value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, Node value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Node getNode(String relPath) throws PathNotFoundException, RepositoryException
    {
        if (NodeImp.isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        NodeImp node = nodeManager.getNode(relPath, session);
        if (node == null)
        {
            throw new PathNotFoundException("The node with path " + relPath + " was not found");
        }
        return node;
    }

    public NodeIterator getNodes() throws RepositoryException
    {
        nodeManager.loadChilds(this, session, false);
        NodeIteratorImp iterator = new NodeIteratorImp(nodeManager.getChildNodes(this));
        return iterator;
    }

    public NodeIterator getNodes(String namePattern) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator getNodes(String[] nameGlobs) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Property getProperty(String relPath) throws PathNotFoundException, RepositoryException
    {
        if (!isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        String pathAbsProperty = normalizePath(relPath);
        PropertyImp prop = nodeManager.getProperty(pathAbsProperty);
        if (prop == null)
        {
            throw new PathNotFoundException();
        }
        return prop;
    }

    public PropertyIterator getProperties() throws RepositoryException
    {
        HashSet<PropertyImp> props = new HashSet<PropertyImp>();
        for (PropertyImp prop : nodeManager.getChildProperties(this))
        {
            if (!prop.getDefinition().isProtected())
            {
                props.add(prop);
            }
        }
        return new PropertyIteratorImp(props);
    }

    public PropertyIterator getProperties(String namePattern) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getProperties(String[] nameGlobs) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Item getPrimaryItem() throws ItemNotFoundException, RepositoryException
    {
        NodeType defaultPrimaryNodeType = this.getDefinition().getDefaultPrimaryType();
        String primaryItemName = defaultPrimaryNodeType.getPrimaryItemName();
        if (primaryItemName != null)
        {
            nodeManager.loadChilds(this, session, false);
            String primaryItemNamePath = getPathFromName(primaryItemName);
            NodeImp node = nodeManager.getNode(primaryItemNamePath, session);
            if (node == null)
            {
                PropertyImp prop = nodeManager.getProperty(primaryItemNamePath);
                if (prop == null)
                {
                    throw new ItemNotFoundException();
                }
                else
                {
                    return prop;
                }
            }
            else
            {
                return node;
            }
        }
        else
        {
            throw new ItemNotFoundException();
        }
    }

    @Deprecated
    public String getUUID() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (!isReferenceable())
        {
            throw new UnsupportedRepositoryOperationException("The node is not referenceable");
        }
        String propPath = getPathFromName(JCR_UUID);
        PropertyImp prop = nodeManager.getProtectedProperty(propPath);
        return prop.getString();
    }

    public String getIdentifier() throws RepositoryException
    {
        return id;
    }

    public int getIndex() throws RepositoryException
    {
        return index;
    }

    public PropertyIterator getReferences() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getReferences(String name) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getWeakReferences() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getWeakReferences(String name) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasNode(String relPath) throws RepositoryException
    {
        if (!NodeImp.isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        String nodeAbsPath = normalizePath(relPath);
        return nodeManager.hasNode(nodeAbsPath);
    }

    public boolean hasProperty(String relPath) throws RepositoryException
    {
        if (!isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        String pathAbsProperty = normalizePath(relPath);
        return nodeManager.hasProperty(pathAbsProperty);
    }

    public boolean hasNodes() throws RepositoryException
    {
        return nodeManager.hasChildNodes(path);
    }

    public boolean hasProperties() throws RepositoryException
    {
        return nodeManager.hasChildNodes(path);
    }

    public NodeType getPrimaryNodeType() throws RepositoryException
    {
        return nodeType;
    }

    public NodeType[] getMixinNodeTypes() throws RepositoryException
    {
        HashSet<NodeType> getMixinNodeTypes = new HashSet<NodeType>();
        for (NodeType superNodeType : ((NodeDefinitionImp) this.definition).getDeclaringNodeType().getSupertypes())
        {
            if (superNodeType.isMixin())
            {
                getMixinNodeTypes.add(superNodeType);
            }
        }
        String jcr_mixinTypesPath = getPathFromName(JCR_MIXINTYPES);
        PropertyImp prop = nodeManager.getProtectedProperty(jcr_mixinTypesPath);
        for (Value value : prop.getValues())
        {
            String type = value.getString();
            NodeTypeManagerImp nodeTypeManagerImp = nodeTypeManager;
            try
            {
                NodeTypeImp superNodeType = nodeTypeManagerImp.getNodeTypeImp(type);
                if (superNodeType.isMixin())
                {
                    getMixinNodeTypes.add(superNodeType);
                }
            }
            catch (Exception e)
            {
                log.debug(e);
            }
        }
        return getMixinNodeTypes.toArray(new NodeType[getMixinNodeTypes.size()]);
    }

    public boolean isNodeType(String nodeTypeName) throws RepositoryException
    {
        for (NodeType mixinNodeType : getMixinNodeTypes())
        {
            if (mixinNodeType.isNodeType(nodeTypeName))
            {
                return true;
            }
        }
        return false;
    }

    public void setPrimaryType(String nodeTypeName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        if (this.definition.isProtected())
        {
            throw new ConstraintViolationException("The node " + path + " is protected");
        }
        if (!isNodeType(nodeTypeName))
        {
            throw new NoSuchNodeTypeException(nodeTypeName + " is not a NodeType");
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addMixin(String mixinName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        if (this.definition.isProtected())
        {
            throw new ConstraintViolationException("The node " + path + " is protected");
        }
        NodeTypeImp mixNodeType = nodeTypeManager.getNodeTypeImp(mixinName);
        if (!this.canAddMixin(mixinName))
        {
            throw new ConstraintViolationException("The mixin can be added");
        }
        PropertyImp prop = nodeManager.getProtectedProperty(getPathFromName(JCR_MIXINTYPES));
        Value[] values = prop.getValues();
        boolean exists = false;
        for (Value value : values)
        {
            if (value.getString().equals(mixinName))
            {
                exists = true;
            }
        }
        if (!exists)
        {
            Value newValue = valueFactoryImp.createValue(mixinName);
            prop.addValue(newValue);
        }
        for (PropertyDefinitionImp propDef : mixNodeType.getPropertyDefinitionsImp())
        {
            if (propDef.getSemanticProperty() != null)
            {
                SemanticProperty semanticProperty = propDef.getSemanticProperty();
                String nameProperty = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                String pathProperty = getPathFromName(nameProperty);
                if (!nodeManager.hasProperty(pathProperty))
                {
                    log.trace("Loading property " + pathProperty + " for node " + path);
                    PropertyImp propMix = new PropertyImp(semanticProperty, this, pathProperty, session);
                    nodeManager.addProperty(propMix, propMix.path, path, false);
                }
            }
        }
        init();
    }

    public void removeMixin(String mixinName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        if (this.definition.isProtected())
        {
            throw new ConstraintViolationException("The node " + path + " is protected");
        }
        NodeTypeImp mixNodeType = nodeTypeManager.getNodeTypeImp(mixinName);
        for (NodeType supertypes : ((NodeDefinitionImp) this.definition).getDefaultPrimaryType().getDeclaredSupertypes())
        {
            if (supertypes.equals(mixNodeType))
            {
                throw new ConstraintViolationException("The mix in can not be deleted, the mixin is declared super nodetype");
            }
        }
        PropertyImp prop = nodeManager.getProtectedProperty(getPathFromName(JCR_MIXINTYPES));
        Value[] values = prop.getValues();
        HashSet<Value> newValues = new HashSet<Value>();
        for (Value value : values)
        {
            if (!value.getString().equals(mixinName))
            {
                newValues.add(value);
            }
        }
        prop.setValue(newValues.toArray(new Value[newValues.size()]));

        for (PropertyDefinitionImp propDef : mixNodeType.getPropertyDefinitionsImp())
        {
            if (propDef.getSemanticProperty() != null)
            {
                SemanticProperty semanticProperty = propDef.getSemanticProperty();
                String nameProperty = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                nodeManager.removeProperty(getPathFromName(nameProperty), path);
            }
        }
    }

    public boolean canAddMixin(String mixinName) throws NoSuchNodeTypeException, RepositoryException
    {
        NodeType mixNodeType = nodeTypeManager.getNodeType(mixinName);
        if (!mixNodeType.isMixin())
        {
            throw new NoSuchNodeTypeException("Tne nodeType is not mixin");
        }
        for (NodeType supertypes : ((NodeDefinitionImp) this.definition).getDefaultPrimaryType().getSupertypes())
        {
            if (supertypes.equals(mixNodeType))
            {
                return false;
            }
        }
        if (nodeManager.hasProperty(getPathFromName(JCR_MIXINTYPES)))
        {
            PropertyImp jcr_mixinTypes = nodeManager.getProtectedProperty(getPathFromName(JCR_MIXINTYPES));
            for (Value value : jcr_mixinTypes.getValues())
            {
                String mix = value.getString();
                if (mix.equals(mixinName))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public NodeDefinition getDefinition() throws RepositoryException
    {
        return ((NodeDefinitionImp) this.definition);
    }

    private boolean isReferenceable() throws RepositoryException
    {
        return this.isNodeType(MIX_REFERENCEABLE);
    }

    private boolean isVersionNode() throws RepositoryException
    {
        return this.isNodeType(NT_VERSION);
    }

    private boolean isMixCreated() throws RepositoryException
    {
        return this.isNodeType(MIX_CREATED);
    }

    private boolean isMixLastModified() throws RepositoryException
    {
        return this.isNodeType(MIX_LAST_MODIFIED);
    }

    public boolean isVersionable() throws RepositoryException
    {
        return this.isNodeType(MIX_VERSIONABLE);
    }

    private boolean isSimpleVersionable() throws RepositoryException
    {
        return this.isNodeType(MIX_SIMPLEVERSIONABLE);
    }

    @Deprecated
    public Version checkin() throws VersionException, UnsupportedRepositoryOperationException, InvalidItemStateException, LockException, RepositoryException
    {
        return versionManagerImp.checkin(path);
    }

    @Deprecated
    public void checkout() throws UnsupportedRepositoryOperationException, LockException, ActivityViolationException, RepositoryException
    {
        if (!isSimpleVersionable())
        {
            throw new UnsupportedRepositoryOperationException(THE_NODE_IS_NOT_VERSIONABLE);
        }
        PropertyImp prop = nodeManager.getProtectedProperty(this.getPathFromName(JCR_ISCHECKEDOUT));
        if (prop.getLength() == -1)
        {
            prop.set(valueFactoryImp.createValue(true));
        }
    }

    @Deprecated
    public void doneMerge(Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Deprecated
    public void cancelMerge(Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(String srcWorkspace) throws NoSuchWorkspaceException, AccessDeniedException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Deprecated
    public NodeIterator merge(String srcWorkspace, boolean bestEffort) throws NoSuchWorkspaceException, AccessDeniedException, MergeException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getCorrespondingNodePath(String workspaceName) throws ItemNotFoundException, NoSuchWorkspaceException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator getSharedSet() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeSharedSet() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeShare() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCheckedOut() throws RepositoryException
    {
        if (!isSimpleVersionable())
        {
            return true;
        }
        PropertyImp jcr_ischeckout = nodeManager.getProtectedProperty(getPathFromName(JCR_ISCHECKEDOUT));
        if (jcr_ischeckout.getLength() != -1)
        {
            return jcr_ischeckout.getBoolean();
        }
        return true;
    }

    @Deprecated
    public void restore(String versionName, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Deprecated
    public void restore(Version version, boolean removeExisting) throws VersionException, ItemExistsException, InvalidItemStateException, UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Deprecated
    public void restore(Version version, String relPath, boolean removeExisting) throws PathNotFoundException, ItemExistsException, VersionException, ConstraintViolationException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Deprecated
    public void restoreByLabel(String versionLabel, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Deprecated
    public VersionHistory getVersionHistory() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (!isVersionable())
        {
            throw new UnsupportedRepositoryOperationException(THE_NODE_IS_NOT_VERSIONABLE);
        }
        return versionManagerImp.getVersionHistory(path);
    }

    @Deprecated
    public Version getBaseVersion() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return versionManagerImp.getBaseVersionImp(this);
    }

    @Deprecated
    public Lock lock(boolean isDeep, boolean isSessionScoped) throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        return session.getWorkspaceImp().getLockManager().lock(path, isDeep, isSessionScoped, Long.MAX_VALUE,session.getUserID());
    }

    @Deprecated
    public Lock getLock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, RepositoryException
    {
        return session.getWorkspaceImp().getLockManager().getLock(path);
    }

    @Deprecated
    public void unlock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        session.getWorkspaceImp().getLockManager().unlock(path);
    }

    @Deprecated
    public boolean holdsLock() throws RepositoryException
    {
        return session.getWorkspaceImp().getLockManager().holdsLock(path);
    }

    public boolean isLocked() throws RepositoryException
    {
        return session.getWorkspaceImp().getLockManager().isLocked(path);
    }

    public void followLifecycleTransition(String transition) throws UnsupportedRepositoryOperationException, InvalidLifecycleTransitionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getAllowedLifecycleTransistions() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isNode()
    {
        return true;
    }

    @Override
    public void accept(ItemVisitor visitor) throws RepositoryException
    {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final NodeImp other = (NodeImp) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public void validate() throws ConstraintViolationException, RepositoryException
    {
        for (NodeDefinition childNodeDefinition : nodeType.getChildNodeDefinitions())
        {
            if (childNodeDefinition.isMandatory())
            {
                String childNodeDefinitionName = childNodeDefinition.getName();
                if (!childNodeDefinitionName.equals(ALL))
                {
                    String pathChild = this.getPathFromName(childNodeDefinitionName);
                    if (nodeManager.hasNode(pathChild))
                    {
                        NodeImp childNode = nodeManager.getNode(pathChild);
                        if (childNode.isModified)
                        {
                            childNode.validate();
                        }
                    }
                    else
                    {
                        throw new ConstraintViolationException("The node " + childNodeDefinitionName + " was not found for the node " + path);
                    }
                }
            }
        }
        for (PropertyDefinition propDef : nodeType.getPropertyDefinitions())
        {
            if (propDef.isMandatory())
            {
                String propertyDefinitionName = propDef.getName();
                if (!propertyDefinitionName.equals(ALL))
                {
                    String pathChild = this.getPathFromName(propertyDefinitionName);
                    if (nodeManager.hasProperty(pathChild))
                    {
                        PropertyImp prop = nodeManager.getAllProperty(pathChild);
                        prop.validate();
                    }
                    else
                    {
                        throw new ConstraintViolationException("The property " + propertyDefinitionName + " was not found for the node " + path);
                    }
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return path + " (" + nodeType.getName() + "," + this.getClass().getCanonicalName() + ")";
    }
}
