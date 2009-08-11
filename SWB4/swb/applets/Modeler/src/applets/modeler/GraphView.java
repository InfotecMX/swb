/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 


/*
 * GraphView.java
 *
 * Created on 16 de enero de 2006, 12:01 PM
 */

package applets.modeler;

import applets.modeler.elements.GraphElement;
import applets.modeler.elements.StartElement;
import java.applet.Applet;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class GraphView extends javax.swing.JPanel
{
    private ArrayList ele=new ArrayList();
    private GraphElement focus=null;
    private CommandListener commandListener=null;
    
    private GraphElement overElement=null;
    
    private Applet applet=null;
    
    private boolean enabled=true;
    private boolean dragEnabled=true;
    
    private GraphElement startElement=null;
    
    private Runnable repaint = new Runnable() {
        public void run() { 
            repaint();
        }
    };    
    
    private Locale locale=new Locale("es");
    
    /** Creates new form GraphView */
    public GraphView()
    {
        initComponents();
        startElement=new StartElement();
        startElement.setLocation(100,20);
        addElement(startElement);
        startElement._init();
    }
    
    public void addElement(GraphElement element)
    {
        element.setContainer(this);
        ele.add(element);
    }
    
    public void removeElement(GraphElement element)
    {
        ele.remove(element);
        
        element.unLinkParent();
        Iterator it=element.getChilds();
        while(it.hasNext())
        {
            removeElement((GraphElement)it.next());
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenuItem1.setText(java.util.ResourceBundle.getBundle("applets/modeler/GraphView",locale).getString("delete"));
        jMenuItem1.setActionCommand("DELETE");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuItem1);

        jPopupMenu1.add(jSeparator1);

        jMenuItem2.setText(java.util.ResourceBundle.getBundle("applets/modeler/GraphView",locale).getString("add_space"));
        jMenuItem2.setActionCommand("ADD_SPACE");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem2ActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText(java.util.ResourceBundle.getBundle("applets/modeler/GraphView",locale).getString("delete_space"));
        jMenuItem3.setActionCommand("DELETE_SPACE");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem3ActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuItem3);

        setLayout(null);

        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                formMouseMoved(evt);
            }
        });

    }//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem3ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        if(focus!=null)
        {
            focus.removeChildSpace();
        }
        update();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        if(focus!=null)
        {
            focus.addChildSpace();
        }
        update();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        if(focus!=null)
        {
            removeElement(focus);
            focus=null;
        }        
        update();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseMoved
    {//GEN-HEADEREND:event_formMouseMoved
        
        //if(!enabled)return;
        // TODO add your handling code here:
        GraphElement aux=getElement(evt.getPoint());
        if(aux!=overElement)
        {
            if(aux!=null)
            {
                if(overElement!=null)
                {
                    overElement._onMouseExited(evt);
                }
                aux._onMouseEntered(evt);
                //setCursor(new Cursor(Cursor.HAND_CURSOR));
                //setToolTipText(aux.getToolTipText());
            }else
            {
                if(overElement!=null)
                {
                    overElement._onMouseExited(evt);
                }                
                //setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                //setToolTipText(null);
            }
            overElement=aux;
        }
        if(aux!=null)
        {
            aux._onMouseMoved(evt);            
        }
        
    }//GEN-LAST:event_formMouseMoved

    private void formMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseClicked
    {//GEN-HEADEREND:event_formMouseClicked
        // TODO add your handling code here:
        if(!enabled || !dragEnabled)return;
        //System.out.println(evt);
        focus=getClickElement(evt.getPoint());
        if(focus!=null)
        {
            if(evt.getButton()==evt.BUTTON1)
            {
                focus._onMouseClicked(evt);
            }else if(evt.getButton()==evt.BUTTON3)
            {
                showMenu(focus,evt.getPoint()); 
            }
        }
        update();
    }//GEN-LAST:event_formMouseClicked

    private void formMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseReleased
    {//GEN-HEADEREND:event_formMouseReleased
        // TODO add your handling code here:
        if(!enabled)return;
        //System.out.println(evt);
        //if(evt.getButton()==evt.BUTTON1)
        {        
            if(focus!=null)
            {
                dropElement(focus,evt.getPoint());
                focus.endDrag(evt.getPoint());
            }
        }
        update();
    }//GEN-LAST:event_formMouseReleased

    private void formMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMousePressed
    {//GEN-HEADEREND:event_formMousePressed
        // TODO add your handling code here:
        if(!enabled)return;
        //System.out.println(evt);
        focus=getClickElement(evt.getPoint());
        //if(evt.getButton()==evt.BUTTON1)
        {        
            if(focus!=null)
            {
                focus.startDrag(evt.getPoint());
            }else
            {
               if(commandListener!=null)
               {
                   Class cls=commandListener.getCommandElement(commandListener.getActualCommand());
                   if(cls!=null)
                   {
                       try
                       {
                           Object obj=cls.newInstance();
                           if(obj instanceof GraphElement)
                           {
                               GraphElement e=(GraphElement)obj;
                               //e.setLocation((int)evt.getPoint().getX()-e.getWidth()/2,(int)evt.getPoint().getY()-e.getHeight()/2);
                               e.setLocation(evt.getPoint());
                               addElement(e);
                               e._init();
                               focus=e;
                               e.setFocus();
                               focus.startDrag(evt.getPoint());
                               focus.setFocus();
                               focus.onIDECreated();
                           }
                       }catch(Exception e){e.printStackTrace();}
                   }
                   commandListener.setActualCommand(commandListener.getPointerCommand());
               }
            }            
        }
        update();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseDragged
    {//GEN-HEADEREND:event_formMouseDragged
        // TODO add your handling code here:
        if(!enabled)return;
        if(!dragEnabled && focus!=startElement)return;
        if(focus!=null)
        {
            focus.unLinkParent();
            focus.drag(evt.getPoint());
        }
        repaint();
    }//GEN-LAST:event_formMouseDragged
    
    public void update()
    {
        SwingUtilities.invokeLater(repaint);
        updateUI();
    }
    
    public void showMenu(GraphElement e,Point p)
    {
        if(e!=null)
        {
            boolean show=false;
            if(e.isDeletable())
            {
                jMenuItem1.setEnabled(true);
                show=true;
            }else
            {
                jMenuItem1.setEnabled(false);
            }
            if(e.getNumChilds()<e.getMaxChilds())
            {
                jMenuItem2.setEnabled(true);
                show=true;
            }else
            {
                jMenuItem2.setEnabled(false);
            }
            if(e.getNumChilds()>e.getMinChilds() && e.canHaveChild())
            {
                jMenuItem3.setEnabled(true);
                show=true;
            }else
            {
                jMenuItem3.setEnabled(false);
            }
            
            if(show)jPopupMenu1.show(this, p.x,p.y);
        }
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        Iterator it=ele.iterator();
        while(it.hasNext())
        {
            GraphElement e=(GraphElement)it.next();
            if(!e.isParentClosed())
            {
                e.paint(g);
            }
        }
    }
    
    public GraphElement getElement(Point p)
    {
        ArrayList list=new ArrayList(ele);
        Collections.reverse(list);
        Iterator it=list.iterator();
        //Iterator it=ele.iterator();
        GraphElement ret=null;
        while(it.hasNext())
        {
            GraphElement e=(GraphElement)it.next();
            if(!e.isParentClosed())
            {
                if(e.contains(p))
                {
                    if(ret==null || e.isChildOf(ret) || (ret.contains(e.getLocation()) && ret.getWidth()>e.getWidth()))
                    {
                        ret=e;
                    }
                }
            }
        }        
        return ret;
    }    
    
    public GraphElement getClickElement(Point p)
    {
        GraphElement ret=getElement(p);
        if(ret!=null)
        {
            ret.setFocus();
            ele.remove(ret);
            ele.add(ret);
        }
        return ret;
    }
    
    public boolean dropElement(GraphElement de, Point point)
    {
        ArrayList list=new ArrayList(ele);
        Collections.reverse(list);
        Iterator it=list.iterator();
        while(it.hasNext())
        {
            GraphElement e=(GraphElement)it.next();
            if(!e.isParentClosed())
            {
                if(e!=de)
                {
                    if(e.linkChild(de,point))
                        return true;
                }
            }
        }        
        return false;
    }    
    
    public Dimension getPreferredSize()
    {
        //System.out.println("getPreferredSize");
        int maxX=0;
        int maxY=0;
        
        Iterator it=ele.iterator();
        while(it.hasNext())
        {
            GraphElement e=(GraphElement)it.next();
            if(e.getParent()==null && e.getExternalParent()==null)
            {
                if(!e.isParentClosed())
                {
                    Rectangle rec=e.getContainerBounds();
                    int mx=(int)rec.getMinX();
                    int my=(int)rec.getMinY();
                    if(mx<0)mx=-mx;
                    else mx=0;
                    if(my<0)my=-my;
                    else my=0;
                    rec.translate(mx,my);
                    e.setX(e.getX()+mx);
                    e.setY(e.getY()+my);
                    int auxX=(int)rec.getMaxX();
                    int auxY=(int)rec.getMaxY();
                    if(auxX>maxX)maxX=auxX;
                    if(auxY>maxY)maxY=auxY;
                }
            }
        }
        return new Dimension(maxX+20,maxY+20);
    }
    
    /**
     * Getter for property commandListener.
     * @return Value of property commandListener.
     */
    public applets.modeler.CommandListener getCommandListener()
    {
        return commandListener;
    }
    
    /**
     * Setter for property commandListener.
     * @param commandListener New value of property commandListener.
     */
    public void setCommandListener(applets.modeler.CommandListener commandListener)
    {
        this.commandListener = commandListener;
    }
    
    public ArrayList getElements()
    {
        return ele;
    }
    
    /**
     * Getter for property applet.
     * @return Value of property applet.
     */
    public java.applet.Applet getApplet()
    {
        return applet;
    }
    
    /**
     * Setter for property applet.
     * @param applet New value of property applet.
     */
    public void setApplet(java.applet.Applet applet)
    {
        this.applet = applet;
    }
    
    /**
     * Getter for property enabled.
     * @return Value of property enabled.
     */
    public boolean isEnabled()
    {
        return enabled;
    }
    
    /**
     * Setter for property enabled.
     * @param enabled New value of property enabled.
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    /**
     * Getter for property dragEnabled.
     * @return Value of property dragEnabled.
     */
    public boolean isDragEnabled()
    {
        return dragEnabled;
    }
    
    /**
     * Setter for property dragEnabled.
     * @param dragEnabled New value of property dragEnabled.
     */
    public void setDragEnabled(boolean dragEnabled)
    {
        this.dragEnabled = dragEnabled;
    }
    
    /**
     * Getter for property startElement.
     * @return Value of property startElement.
     */
    public applets.modeler.elements.GraphElement getStartElement()
    {
        return startElement;
    }
    
    /**
     * Getter for property locale.
     * @return Value of property locale.
     */
    public Locale getLocale()
    {
        return locale;
    }
    
    /**
     * Setter for property locale.
     * @param locale New value of property locale.
     */
    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
    
}
