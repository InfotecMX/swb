/*
 * GateWay.fx
 *
 * Created on 13/02/2010, 11:23:01 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class Gateway extends FlowNode
{
    public override function create(): Node
    {
        initializeCustomNode();
        stroke=Color.web(Styles.color_gateway);
        cursor=Cursor.HAND;
        w=50;
        h=50;
        shape= Polygon
        {
            points: [w/2,0,w,h/2,w/2,h,0,h/2]
            style: Styles.style_gateway
            //smooth: true;
        };

        return Group
        {
            content: [
                Group
                {
                    content: [
                        shape
                    ]
                    translateX: bind x - w/2
                    translateY: bind y - w/2
                    scaleX: bind s;
                    scaleY: bind s;
                    opacity: bind o;
                    effect: Styles.dropShadow
                }
            ]
            visible: bind canView()
        };
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        
        if (link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage("Gateway cannot have outgoing MessageFlow");
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage("Gateway cannot have incoming MessageFlow");
        }
        if (link instanceof AssociationFlow) {
            if (not(link.ini instanceof Artifact)) {
                ret = false;
            }
            ModelerUtils.setErrorMessage("Gateway cannot have incoming AssociationFlow if it does not come from an Artifact");
        }
        return ret;
    }

}

