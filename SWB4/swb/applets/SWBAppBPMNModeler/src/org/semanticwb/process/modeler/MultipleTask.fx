/*
 * MultipleTask.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class MultipleTask extends Task
{
    public override function create(): Node
    {
        type=TYPE_MULTIPLE;
        return super.create();
    }
}
