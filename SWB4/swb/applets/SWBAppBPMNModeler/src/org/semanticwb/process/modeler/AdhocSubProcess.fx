/*
 * AdhocSubProcess.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class AdhocSubProcess extends SubProcess
{
    public override function create(): Node
    {
        type=TYPE_ADHOC;
        return super.create();
    }
}
