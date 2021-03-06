/*
 * Event.fx
 *
 * Created on 13/02/2010, 11:28:15 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import org.semanticwb.process.modeler.ConditionalFlow;
import org.semanticwb.process.modeler.ModelerUtils;

/**
 * @author javier.solis
 */
public def CATCH_TIMER="w_timer";
public def CATCH_MESSAGE="w_message";
public def CATCH_RULE="w_conditinal";
public def CATCH_LINK="w_link";
public def CATCH_MULTIPLE="w_multiple";
public def CATCH_SIGNAL="w_signal";
public def CATCH_PARALLEL="w_parallel";
public def CATCH_SCALATION="w_scalation";
public def CATCH_ERROR="w_error";
public def CATCH_COMPENSATION="w_compensation";
public def CATCH_CANCELATION="w_cancelation";
public def THROW_MESSAGE="b_message";
public def THROW_ERROR="b_error";
public def THROW_CANCELATION="b_cancelation";
public def THROW_COMPENSATION="b_compensation";
public def THROW_SIGNAL="b_signal";
public def THROW_MULTIPLE="b_multiple";
public def THROW_SCALATION="b_scalation";
public def THROW_TERMINATION="b_termination";
public def THROW_LINK="b_link";

public class Event extends FlowNode
{
    var ix:Number;                          //offset imagen x
    var iy:Number;                          //offset imagen x
    var is:Number=1;                        //image scale
    protected var scaleOff:Number=0;                  //scale offset
    protected var strokeDash : Float[];
    protected var cancelActivity: Boolean = false;
    override var graphParent on replace {
        if (not (graphParent instanceof Activity) and not isInterrupting) {
            isInterrupting = true;
        }
        cancelActivity = graphParent instanceof Activity;
    }

    protected var colorAdjust=ColorAdjust
    {
        hue:0
        brightness:0
        contrast:1
        saturation:0
    }

    public var message=ImageView
    {
        x: bind x-ix-.5;
        y: bind y-iy-.5;
        scaleX: bind is+scaleOff;
        scaleY: bind is+scaleOff;
        effect: colorAdjust;
    };

    public override function create(): Node
    {
        blocksMouse = true;
        setCommonMenuOptions();
        w=30;
        h=30;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y + 30
            width: bind w + 60
            height: bind h
        }

        shape= Circle
        {
            centerX: bind x
            centerY: bind y
            radius: bind w/2
            strokeDashArray: bind if (isInterrupting) null else [2, 5]
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        }

        setType(type);

        return Group
        {
            content: [
                shape,
                text,
                message
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }

    public override function setType(type:String):Void
    {
        super.setType(type);
        message.visible=true;
        if(type.equals(CATCH_TIMER))
        {
            message.styleClass = "modifierTimer";
            ix=11;
            iy=10.7;
            is=0.95;
        }else if(type.equals(CATCH_LINK))
        {
            message.styleClass = "modifierLinkCatch";
            ix=10;
            iy=10;
            is=0.9;
        }else if(type.equals(CATCH_MESSAGE))
        {
            message.styleClass = "modifierMessageCatch";
            ix=9;
            iy=8;
            is=1;
        }else if(type.equals(CATCH_RULE))
        {
            message.styleClass = "modifierRule";
            ix = 9.5;
            iy = 9.5;
            is = 1;
        }else if(type.equals(CATCH_SIGNAL))
        {
            message.styleClass = "modifierSignalCatch";
            ix=10;
            iy=11;
            is=1.1;
        }else if(type.equals(CATCH_MULTIPLE))
        {
            message.styleClass = "modifierMultipleCatch";
            ix=11;
            iy=12;
            is=1;
        }else if(type.equals(CATCH_PARALLEL))
        {
            message.styleClass = "modifierParallel";
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(CATCH_SCALATION))
        {
            message.styleClass = "modifierScalationCatch";
            ix=9;
            iy=12;
            is=1;
        }else if(type.equals(CATCH_ERROR))
        {
            message.styleClass = "modifierErrorCatch";
            ix=10;
            iy=10;
            is=1;
        }else if(type.equals(CATCH_COMPENSATION))
        {
            message.styleClass = "modifierCompensateCatch";
            ix=11;
            iy=6;
            is=1;
        }else if(type.equals(CATCH_CANCELATION))
        {
            message.styleClass = "modifierCancelCatch";
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(THROW_LINK))
        {
            message.styleClass = "modifierLinkThrow";
            ix=10;
            iy=10;
            is=0.9;
        }else if(type.equals(THROW_MESSAGE))
        {
            message.styleClass = "modifierMessageThrow";
            ix=9;
            iy=8;
            is=1;
        }else if(type.equals(THROW_SIGNAL))
        {
            message.styleClass = "modifierSignalThrow";
            ix=10;
            iy=10;
            is=1.1;
        }else if(type.equals(THROW_MULTIPLE))
        {
            message.styleClass = "modifierMultipleThrow";
            ix=11;
            iy=12;
            is=1;
        }else if(type.equals(THROW_SCALATION))
        {
            message.styleClass = "modifierScalationThrow";
            ix=9;
            iy=12;
            is=1;
        }else if(type.equals(THROW_ERROR))
        {
            message.styleClass = "modifierErrorThrow";
            ix=10;
            iy=10;
            is=1;
        }else if(type.equals(THROW_COMPENSATION))
        {
            message.styleClass = "modifierCompensateThrow";
            ix=11;
            iy=6;
            is=1;
        }else if(type.equals(THROW_CANCELATION))
        {
            message.styleClass = "modifierCancelThrow";
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(THROW_TERMINATION))
        {
            message.styleClass = "modifierTerminate";
            ix=8;
            iy=8;
            is=1.1;
        }else
        {
             message.visible=false;
        }
    }

    public override function canStartLink(link: ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        if (link instanceof ConditionalFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError53");
        }
        return ret;
    }

    override public function getBounds() : Bounds {
        return Bounds {
            topLeft: Point {
                x: x - w / 2
                y: y - h / 2
            },
            bottomRight: Point {
                x: x - w / 2 +w
                y: y - h / 2 +h
            }
        }
    }
}