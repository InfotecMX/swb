/*
 * IntermediateThrowEvent.fx
 *
 * Created on 13/02/2010, 11:30:48 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

/**
 * @author javier.solis
 */

public class IntermediateThrowEvent extends ThrowEvent
{
    public override var over on replace {
        if (over and not selected) {
            shape.styleClass = "interEventHover";
        } else if (not selected) {
            shape.styleClass = "interEvent";
        }
    }

    public override var selected on replace {
        if (selected) {
            shape.styleClass = "interEventFocused";
        } else {
            shape.styleClass = "interEvent";
        }
    }
    
    public override function create(): Node
    {
        blocksMouse = true;
        initializeCustomNode();
        w=30;
        h=30;
        scaleOff=-0.1;
        
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y + 30
            width: bind w + 60
            height: bind h
        }

        colorAdjust.hue=-0.83;
        colorAdjust.brightness=-0.07;
        colorAdjust.contrast=0.25;
        colorAdjust.saturation=1;

        shape= Circle
        {
            centerX: bind x
            centerY: bind y
            radius: bind w/2
            styleClass: "interEvent"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        var actions: MenuItem[] = [
            MenuItem {
                caption: ##"actCopy"
                action: function (e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = copy();
                    modeler.setCopyNode(t);
                }
            },
            MenuItem {
                caption: ##"actCut"
                action: function (e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = cut();
                    modeler.setCopyNode(t);
                    ModelerUtils.setResizeNode(null);
                }
            }
        ];

        insert actions before menuOptions[0];

        setType(type);

        return Group
        {
            content: [
                shape,
                Circle
                {
                    centerX: bind x
                    centerY: bind y
                    radius: bind w/2-3
                    styleClass: "interEvent"
                    //id: "marker"
                },
                message,text
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }

    public override function canAttach(parent:GraphicalElement):Boolean
    {
        var ret = true;
        if(not(parent instanceof Pool or parent instanceof Lane))
        {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError23");
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);
        var c = 0;

        for(ele in getInputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                c++;
            }
        }
     
        if (link instanceof SequenceFlow and c != 0) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError21");
        }
        return ret;
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        var c = 0;

        for(ele in getOutputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                c++;
            }
        }

        if (link instanceof SequenceFlow and c != 0) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError22");
        }
        return ret;
    }

    override public function copy() : GraphicalElement {
        var t = IntermediateThrowEvent {
            title: this.title
            description: this.description
            type: this.type
            modeler: this.modeler
            container: this.container
            x: this.x + 10
            y: this.y + 10
            uri: "new:interevent:{modeler.toolBar.counter++}"
        }
        return t;
    }
}