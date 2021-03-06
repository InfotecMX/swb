/*
 * InclusiveGateway.fx
 *
 * Created on 13/02/2010, 11:24:44 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
/**
 * @author javier.solis
 */

public class InclusiveGateway extends Gateway
{
    public override function create(): Node
    {
        blocksMouse = true;
        initializeCustomNode();
        w=50;
        h=50;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y: bind y + 40
            width: bind w + 60
            height: bind h
        }

        var actions: MenuItem[] = [
            MenuItem {
                caption: ##"actType"
                items: [
                    MenuItem {
                        caption: ##"actExclusive"
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = ExclusiveGateway {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:exclusivegateway:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actComplex"
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = ComplexGateway {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:complexgateway:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    }
                ]
            },
            MenuItem {isSeparator: true},
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

        shape= Polygon
        {
            points: [w/2,0,w,h/2,w/2,h,0,h/2]
            styleClass: "gateway"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        return Group
        {
            content: [
                Group {
                    content: [
                        shape, Circle
                        {
                            centerX: w/2
                            centerY: h/2
                            radius: w/4
                            styleClass: "modifierGateway"
                        }
                    ]
                    translateX: bind x - w/2
                    translateY: bind y - w/2
                    scaleX: bind s;
                    scaleY: bind s;
                }, text
            ]
            visible: bind canView()
        };
    }

    override public function canStartLink(link: ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        var count = 0;

        for (ele in getOutputConnectionObjects()) {
            if (ele instanceof DefaultFlow) {
                count++;
            }
        }

        if (not (link instanceof ConditionalFlow or link instanceof DefaultFlow)) {
            ModelerUtils.setErrorMessage(##"msgError1");
            ret = false;
        }

        if (link instanceof DefaultFlow and count > 0) {
            ModelerUtils.setErrorMessage(##"msgError2");
            ret = false;
        }
        return ret;
    }

    override public function getDefaultFlow() : ConnectionObject {
        var link = ConditionalFlow {
            modeler:modeler
            uri:"new:conditionalflow:{modeler.toolBar.counter++}"
        }
        return link;
    }

    override public function copy() : GraphicalElement {
        var t = InclusiveGateway {
            title: this.title
            description: this.description
            type: this.type
            modeler: this.modeler
            container: this.container
            x: this.x + 10
            y: this.y + 10
            uri: "new:inclusivegateway:{modeler.toolBar.counter++}"
        }
        return t;
    }
}