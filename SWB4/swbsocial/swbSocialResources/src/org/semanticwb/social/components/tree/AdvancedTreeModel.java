/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.tree;


import java.util.Iterator;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase que controladora del modelo de datos del árbol de navegación.
 */
public class AdvancedTreeModel extends DefaultTreeModel<Element> {
    /**
     *
     */
    private static final long serialVersionUID = -9145887024839938518L;

    DefaultTreeNode<Element> _root;

    public AdvancedTreeModel(ElementTreeNode ElementTreeNode) {
        super(ElementTreeNode);
        _root = ElementTreeNode;
    }

    /**
     * remove the nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code>
     *
     * @param parent
     *            The parent of nodes are removed
     * @param indexFrom
     *            the lower index of the change range
     * @param indexTo
     *            the upper index of the change range
     * @throws IndexOutOfBoundsException
     *             - indexFrom < 0 or indexTo > number of parent's children
     */
    public void remove(DefaultTreeNode<Element> parent, int indexFrom, int indexTo) throws IndexOutOfBoundsException {
        DefaultTreeNode<Element> stn = parent;
        for (int i = indexTo; i >= indexFrom; i--)
            try {
                stn.getChildren().remove(i);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
    }

    /*
     * Remueve un elemento del árbol
     * @param target DefaultTreeNode<Element> nodo a eliminar
     */
    public void remove(DefaultTreeNode<Element> target) throws IndexOutOfBoundsException {
        int index = 0;
        DefaultTreeNode<Element> parent = null;
        // find the parent and index of target
        parent = dfSearchParent(_root, target);
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index).equals(target)) {
                break;
            }
        }
        remove(parent, index, index);
    }

    /**
     * insert new nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code> by new nodes <code>newNodes</code>
     *
     * @param parent
     *            The parent of nodes are inserted
     * @param indexFrom
     *            the lower index of the change range
     * @param indexTo
     *            the upper index of the change range
     * @param newNodes
     *            New nodes which are inserted
     * @throws IndexOutOfBoundsException
     *             - indexFrom < 0 or indexTo > number of parent's children
     */
    public void insert(DefaultTreeNode<Element> parent, int indexFrom, int indexTo, DefaultTreeNode<Element>[] newNodes)
            throws IndexOutOfBoundsException {
        DefaultTreeNode<Element> stn = parent;
        for (int i = indexFrom; i <= indexTo; i++) {
            try {
                stn.getChildren().add(i, newNodes[i - indexFrom]);
            } catch (Exception exp) {
                throw new IndexOutOfBoundsException("Out of bound: " + i + " while size=" + stn.getChildren().size());
            }
        }
    }

    /**
     * append new nodes which parent is <code>parent</code> by new nodes
     * <code>newNodes</code>
     *
     * @param parent
     *            The parent of nodes are appended
     * @param newNodes
     *            New nodes which are appended
     */
    public void add(DefaultTreeNode<Element> parent, DefaultTreeNode<Element>[] newNodes) {
        DefaultTreeNode<Element> stn = (DefaultTreeNode<Element>) parent;

        for (int i = 0; i < newNodes.length; i++)
            stn.getChildren().add(newNodes[i]);

    }


    /*
     * Metodo que encuentra el nodo padre de un determinado nodo, apartir de un nodo
     * @param node DefaultTreeNode<Element> apartir del cual se va ha buscar
     * @param target DefaultTreeNode<Element> que se va ha buscar
     */
    private DefaultTreeNode<Element> dfSearchParent(DefaultTreeNode<Element> node, DefaultTreeNode<Element> target) {
        if (node.getChildren() != null && node.getChildren().contains(target)) {
            return node;
        } else {
            int size = getChildCount(node);
            for (int i = 0; i < size; i++) {
                DefaultTreeNode<Element> parent = dfSearchParent((DefaultTreeNode<Element>) getChild(node, i), target);
                if (parent != null) {
                    return parent;
                }
            }
        }
        return null;
    }

    /*
     * Metodo que encuentra mediante un uri, un DefaultreeNode
     */
    public ElementTreeNode findNode(String uri, String modelID, TreeNode<Element> treeNodeInit)
    {
        System.out.println("findNode Uri:"+uri+", treeNodeInit name:"+treeNodeInit.getData().getName()+",uri:"+treeNodeInit.getData().getUri());
        Iterator<TreeNode<Element>> itChilds=treeNodeInit.getChildren().iterator();
        while(itChilds.hasNext())
        {
            ElementTreeNode treeNode=(ElementTreeNode)itChilds.next();
            if(treeNode.getData().getUri().equalsIgnoreCase(uri) && treeNode.getData().getModelID().equalsIgnoreCase(modelID))
            {
                return treeNode;
            }else if(treeNode.getChildCount()>0){
                ElementTreeNode tmpNode=findNode(uri, modelID, treeNode);
                System.out.println("tmpNode:"+tmpNode.getData().getUri());
                if(tmpNode!=null)
                {
                    System.out.println("Regresa tmpNode:"+tmpNode.getData().getUri());
                    return tmpNode;
                }
            }
        }
        return null;
    }

}