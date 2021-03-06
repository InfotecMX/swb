/*
 * ModelerUtils.fx
 *
 * Created on 10/03/2010, 01:00:38 AM
 */

package org.semanticwb.process.modeler;

import javafx.reflect.*;
import javafx.scene.*;
import java.awt.image.BufferedImage;
import javafx.scene.input.MouseEvent;
import javafx.util.StringLocalizer;

/**
 * @author javier.solis
 */

public var clickedNode: Node;
public var version = "0.1.0.0";
public var splash = Splash{};
var resize = ResizeNode{};
var errorMessage: String;
var localizer: StringLocalizer;

var toolTip=ToolTip
{
    visible:false
}

public var dialog = ModalDialog {
    w: 390
    h: 180
    borderSize: 1
    title: "Propiedades"
    visible: false
}

//public var idialog = IndicatorsDialog {
//    w: 390
//    h: 180
//    borderSize: 1
//    title: "Indicadores"
//    visible: false
//}

public function setDialogNode(node: GraphicalElement) {
    dialog.setBoundNode(node);
}

public function setErrorMessage(message:String)
{
    errorMessage=message;
    toolTip.wrap = false;
    showToolTip(ToolTip.TOOLTIP_ERROR, "ERROR", message, 0, getToolTip().scene.height-getToolTip().boundsInParent.height);
}

public function getLocalizedString(key: String): String {
    localizer.key = key;
    return localizer.localizedString;
}

public function setLocalizer (loc: StringLocalizer) : Void {
    localizer = loc;
}

// Primer menu flotante
public var popup=MenuPopup {
    items: [
        MenuItem {
           caption: "Item1"
           action: function(e: MouseEvent) {
               //println("Item1 clicked");
           }
        },
        MenuItem {
           caption: "Item2"
           action: function(e: MouseEvent) {
               //println("Item2 clicked");
           }
        }
    ]
};

public function getToolTip():ToolTip
{
    return toolTip;
}

public function getResizeNode():ResizeNode
{
    return resize;
}

public function startToolTip(title:String, text: String, x: Number, y: Number)
{
    toolTip.wrap = true;
    showToolTip(ToolTip.TOOLTIP_NORMAL, title, text, x, y);
}

public function startToolTip(text: String, x: Number, y: Number)
{
    toolTip.wrap = true;
    showToolTip(ToolTip.TOOLTIP_NORMAL, "", text, x, y);
}

public function showToolTip(type: String, title:String, text: String, x: Number, y: Number) {
    toolTip.title = title;
    toolTip.setType(type);
    toolTip.text=text;
    toolTip.x=x;
    toolTip.y=y;
    toolTip.show();
}

public function stopToolTip()
{
    toolTip.hide();
}

public function setResizeNode(node:Node)
{
    if(node==null)
    {
        resize.attachedNode=null;
    }else if(node instanceof GraphicalElement)
    {
        resize.attachedNode=node as GraphicalElement;
    }else
    {
        resize.attachedNode=null;
    }

}

public function renderToImage(node:Node, minx:Integer, miny:Integer, width:Integer, height:Integer) : BufferedImage
{
//    var _bounds=new com.sun.javafx.geom.Bounds2D();
//    var _affine=new com.sun.javafx.geom.transform.Affine2D();
//    var bounds=node.impl_getPGNode().getContentBounds(_bounds, _affine);

    var context = FXLocal.getContext();
    var nodeClass = context.findClass("javafx.scene.Node");
    var getFXNode = nodeClass.getFunction("impl_getPGNode");
    var sgNode = (getFXNode.invoke(context.mirrorOf(node)) as FXLocal.ObjectValue).asObject();
    var g2dClass = (context.findClass("java.awt.Graphics2D") as FXLocal.ClassType).getJavaImplementationClass();
    var boundsClass=(context.findClass("com.sun.javafx.geom.Bounds2D") as FXLocal.ClassType).getJavaImplementationClass();
    //var affineClass=(context.findClass("com.sun.javafx.geom.transform.Affine2D") as FXLocal.ClassType).getJavaImplementationClass();
    var affineClass=(context.findClass("com.sun.javafx.geom.transform.BaseTransform") as FXLocal.ClassType).getJavaImplementationClass();

    // getContentBounds() method have different signature in JavaFX 1.2
    var getBounds = sgNode.getClass().getMethod("getContentBounds",boundsClass,affineClass);
    var bounds = getBounds.invoke(sgNode, new com.sun.javafx.geom.Bounds2D(), new com.sun.javafx.geom.transform.Affine2D()) as com.sun.javafx.geom.Bounds2D;
    //var bounds=node.impl_getPGNode().getContentBounds(new com.sun.javafx.geom.Bounds2D(), new com.sun.javafx.geom.transform.Affine2D());

    // Same with render() method
    var paintMethod = sgNode.getClass().getMethod("render", g2dClass, boundsClass, affineClass);
    var img = new java.awt.image.BufferedImage(width+minx, height+miny, java.awt.image.BufferedImage.TYPE_INT_ARGB);
    var g2 =img.createGraphics();
    paintMethod.invoke(sgNode,g2, bounds, new com.sun.javafx.geom.transform.Affine2D());
    g2.dispose();

    var img2 = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
    var g =img2.createGraphics();
    g.drawImage(img, -minx, -miny, null);
    g.dispose();
  
    return img2;
}


public class ModelerUtils {

}