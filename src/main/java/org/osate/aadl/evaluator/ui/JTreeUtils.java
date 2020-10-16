package org.osate.aadl.evaluator.ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * from: https://www.logicbig.com/tutorials/java-swing/jtree-expand-collapse-all-nodes.html
 * @author avld
 */
public class JTreeUtils
{
    
    private JTreeUtils()
    {
        // do nothing
    }
    
    public static void setTreeExpandedState( JTree tree , boolean expanded )
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
        setNodeExpandedState( tree , node , expanded );
    }

    public static void setNodeExpandedState( JTree tree , DefaultMutableTreeNode node , boolean expanded )
    {
        ArrayList<DefaultMutableTreeNode> list = Collections.list( node.children() );
        
        for ( DefaultMutableTreeNode treeNode : list )
        {
            setNodeExpandedState( tree , treeNode , expanded );
        }
        
        if ( !expanded && node.isRoot() )
        {
            return ;
        }
        
        TreePath path = new TreePath( node.getPath() );
        
        if ( expanded )
        {
            tree.expandPath( path );
        }
        else
        {
            tree.collapsePath( path );
        }
    }
}