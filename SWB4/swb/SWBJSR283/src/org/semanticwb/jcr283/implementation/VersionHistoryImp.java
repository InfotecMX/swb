/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
import javax.jcr.AccessDeniedException;
import javax.jcr.NodeIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.version.LabelExistsVersionException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.model.GenericIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class VersionHistoryImp extends NodeImp implements VersionHistory
{

    private final static Logger log = SWBUtils.getLogger(VersionHistoryImp.class);
    private static final String JCR_PREDECESSORS = "jcr:predecessors";
    private static final String JCR_ROOT_VERSION_NAME = "jcr:rootVersion";
    private static final String JCR_SUCCESSORS = "jcr:successors";
    private static final String JCR_VERSIONABLE_UUID = "jcr:versionableUuid";    
    private VersionImp jcr_rootVersion;
    private NodeImp versionableNode;
    protected VersionHistoryImp(org.semanticwb.jcr283.repository.model.VersionHistory vh, NodeImp parent, int index, String path, int depth, SessionImp session) throws RepositoryException
    {
        super(vh, parent, index, path, depth, session);
        PropertyImp prop = nodeManager.getProtectedProperty(this.getPathFromName(JCR_VERSIONABLE_UUID));
        if (prop.getLength() == -1)
        {
            throw new RepositoryException("The versionableUuid property was not found");
        }
        String versionableId = prop.getString();
        versionableNode = (NodeImp) session.getNodeByIdentifier(versionableId);
        String childPathRootVersion = getPathFromName(JCR_ROOT_VERSION_NAME);
        if (nodeManager.hasNode(childPathRootVersion))
        {
            jcr_rootVersion = (VersionImp) nodeManager.getProtectedNode(childPathRootVersion, session);
        }
        else
        {
            GenericIterator<Base> childs = vh.listNodes();
            while (childs.hasNext())
            {
                Base child = childs.next();
                if (child.getName().equals(JCR_ROOT_VERSION_NAME))
                {

                    org.semanticwb.jcr283.repository.model.Version versionNode = new org.semanticwb.jcr283.repository.model.Version(child.getSemanticObject());
                    jcr_rootVersion = (VersionImp) createNodeImp(versionNode, this, this.getIndex() + 1, childPathRootVersion, session);
                    nodeManager.addNode(jcr_rootVersion);
                    break;
                }
            }
        }
        if (jcr_rootVersion == null)
        {
            String path_jcr_rootVersion = this.getPathFromName(JCR_ROOT_VERSION_NAME);
            if (!nodeManager.hasNode(path_jcr_rootVersion))
            {
                jcr_rootVersion = (VersionImp) this.insertNode(JCR_ROOT_VERSION_NAME);
            }
            else
            {
                jcr_rootVersion = (VersionImp) nodeManager.getProtectedNode(path_jcr_rootVersion, session);
            }
        }

    }
    
    protected VersionHistoryImp(NodeTypeImp nodeType, NodeDefinitionImp nodeDefinition, String name, NodeImp parent, int index, String path, int depth, SessionImp session, String id, boolean isnew) throws RepositoryException
    {
        super(nodeType, nodeDefinition, name, parent, index, path, depth, session, id, isnew);
        
        String path_jcr_rootVersion = this.getPathFromName(JCR_ROOT_VERSION_NAME);
        if (!nodeManager.hasNode(path_jcr_rootVersion))
        {
            jcr_rootVersion = (VersionImp) this.insertNode(JCR_ROOT_VERSION_NAME);
        }
        else
        {
            jcr_rootVersion = (VersionImp) nodeManager.getProtectedNode(path_jcr_rootVersion, session);
        }
        
    }
    public void init(NodeImp versionableNode) throws RepositoryException
    {
        this.versionableNode=versionableNode;
        String pathBaseVersion = versionableNode.getPathFromName("jcr:baseVersion");
        PropertyImp prop = nodeManager.getProtectedProperty(pathBaseVersion);
        if (prop.getLength() == -1)
        {
            prop.set(session.getValueFactory().createValue(jcr_rootVersion));
        }



        PropertyImp jcr_versionable_uuid = nodeManager.getProtectedProperty(this.getPathFromName(JCR_VERSIONABLE_UUID));
        if (jcr_versionable_uuid.getLength() == -1)
        {
            jcr_versionable_uuid.set(valueFactoryImp.createValue(versionableNode.id));
        }
    }

    NodeImp insertVersionNode(String nameToAdd) throws RepositoryException
    {
        VersionImp newversion = (VersionImp) this.insertNode(nameToAdd, null);
        newversion.init(versionableNode);
        VersionImp baseverion =   session.getWorkspaceImp().getVersionManagerImp().getBaseVersionImp(this.versionableNode);


        PropertyImp jcr_predecessors = nodeManager.getProtectedProperty(newversion.getPathFromName(JCR_PREDECESSORS));
        jcr_predecessors.addValue(valueFactoryImp.createValue(baseverion));

        PropertyImp jcr_successors = nodeManager.getProtectedProperty(baseverion.getPathFromName(JCR_SUCCESSORS));
        jcr_successors.addValue(valueFactoryImp.createValue(newversion));

        return newversion;
    }

    @Deprecated
    public String getVersionableUUID() throws RepositoryException
    {
        return getVersionableIdentifier();
    }

    public String getVersionableIdentifier() throws RepositoryException
    {
        return versionableNode.getIdentifier();
    }

    public Version getRootVersion() throws RepositoryException
    {
        return jcr_rootVersion;
    }

    public VersionIterator getAllLinearVersions() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionIterator getAllVersions() throws RepositoryException
    {
        HashSet<VersionImp> getAllVersions = new HashSet<VersionImp>();
        nodeManager.loadChilds(this, session, false);
        for (NodeImp node : nodeManager.getProtectedChildNodes(path))
        {
            getAllVersions.add((VersionImp) node);
        }
        return new VersionIteratorImp(getAllVersions);
    }

    public NodeIterator getAllLinearFrozenNodes() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator getAllFrozenNodes() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version getVersion(String versionName) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version getVersionByLabel(String label) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addVersionLabel(String versionName, String label, boolean moveLabel) throws LabelExistsVersionException, VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeVersionLabel(String label) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasVersionLabel(String label) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasVersionLabel(Version version, String label) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getVersionLabels() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getVersionLabels(Version version) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeVersion(String versionName) throws ReferentialIntegrityException, AccessDeniedException, UnsupportedRepositoryOperationException, VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
}
