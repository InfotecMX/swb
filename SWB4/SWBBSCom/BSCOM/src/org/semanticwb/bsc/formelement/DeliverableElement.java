package org.semanticwb.bsc.formelement;

import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;

/**
 * Form Element que presentar&aacute; la vista para administrar entregables
 * (Liga para su edici&oacute;n y eliminaci&oacute;n)
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class DeliverableElement extends org.semanticwb.bsc.formelement.base.DeliverableElementBase {

    /**
     * La constante Mode_VIEW.
     */
    private static final String Mode_VIEW = "view";
    /**
     * La constante Mode_RELOAD.
     */
    private static final String Mode_RELOAD = "reload";
    /**
     * La constante Action_REMOVE.
     */
    private static final String Action_REMOVE = "aDelete";

    /**
     * Crea instancias de esta clase a partir de un objeto sem&aacute;ntico
     *
     * @param base el objeto sem&aacute;ntico a utilizar para crear la nueva
     * instancia
     */
    public DeliverableElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Genera el c&oacute;digo HTML para representar la administraci&eacute;n de
     * los Entregables de los elementos del BSC.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son: {@literal edit},
     * {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        String modeTmp = request.getParameter("modeTmp");
        String downloadEle = request.getAttribute("downloadEle") != null
                ? request.getAttribute("downloadEle").toString() : null;

        if (modeTmp == null && mode.equals(SWBFormMgr.MODE_VIEW) && downloadEle == null) {
            toReturn.append(renderModeView(request, obj, prop, propName, type, mode, lang));
        } else if (modeTmp == null || (modeTmp != null && Mode_VIEW.equals(modeTmp))) {
            toReturn.append(renderElementView(request, obj, prop, propName, type, mode, lang));
        } else if (Mode_RELOAD.equals(modeTmp)) {
            toReturn.append(renderElementReload(request, obj, prop, type));
        }
        return toReturn.toString();
    }

    /**
     * Procesa la informaci&oacute;n enviada por el elemento de forma, para
     * almacenarla en la propiedad del objeto indicado.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop,
            String propName) {
        String action = request.getParameter("_action");
        String usrWithGrants = request.getParameter("usrWithGrants");
        if (Action_REMOVE.equals(action)) {
            String suri = request.getParameter("suriDeliv");
            String sobj = request.getParameter("obj");
            SemanticObject semObj = SemanticObject.getSemanticObject(suri);
            SemanticObject semObjDeliv = SemanticObject.getSemanticObject(sobj);
            if (semObjDeliv.createGenericInstance() instanceof Initiative) {
                Initiative objectDeliv = (Initiative) semObjDeliv.createGenericInstance();
                Deliverable deliv = (Deliverable) semObj.createGenericInstance();
                objectDeliv.removeDeliverable(deliv);
                deliv.remove();
            }
            renderElementReload(request, obj, prop, FormElementURL.URLTYPE_RENDER);
        }
        getRenderURL(obj, prop, FormElementURL.URLTYPE_RENDER, null, "es").
                setParameter("usrWithGrants", usrWithGrants);
    }

    /**
     * Presenta la vista de los entregables asociados al elemento BSC,
     * aqu&iacute; se getiona los permisos para los usuarios
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son: {@literal edit},
     * {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    public String renderElementView(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        String suri = (String) request.getParameter("suri");

        String usrWithGrants = (String) request.getAttribute("usrWithGrants") == null
                ? (String) request.getParameter("usrWithGrants")
                : (String) request.getAttribute("usrWithGrants");

        if (suri != null) {
            SemanticObject semObj = SemanticObject.getSemanticObject(URLDecoder.decode(suri));
            Initiative element = null;
            if (semObj != null && semObj.createGenericInstance() instanceof Initiative) {
                element = (Initiative) semObj.createGenericInstance();
                Iterator<Deliverable> itDeliverables = element.listDeliverables();
                FormElementURL urlFE = getRenderURL(obj, prop, type, mode, lang);
                urlFE.setParameter("modeTmp", Mode_RELOAD);
                urlFE.setParameter("suri", suri);
                urlFE.setParameter("usrWithGrants", usrWithGrants);

                toReturn.append("\n<script type=\"text/javascript\">");
                toReturn.append("\n  dojo.require(\"dijit.Dialog\");");
                toReturn.append("\n  dojo.require(\"dijit.form.Button\");");
                toReturn.append("\n  dojo.require(\"dojo.parser\");");
                toReturn.append("\n  dojo.require(\"dojox.layout.ContentPane\");");

                toReturn.append("\nfunction processUrlDeliv(url,id)");
                toReturn.append("\n{");
                toReturn.append("\n    dojo.xhrPost({");
                toReturn.append("\n        url: url,");
                toReturn.append("\n        load: function(response)");
                toReturn.append("\n        {");
                toReturn.append("\n             if(id != null && document.getElementById(id) != null){");
                toReturn.append("\n               document.getElementById(id).innerHTML ");
                toReturn.append("= response;");
                toReturn.append("\n             } else {");
                toReturn.append("\n               processUrlDeliv('");
                toReturn.append(urlFE.setContentType("text/html; charset=UTF-8"));
                toReturn.append("', 'swbDeliverable');");
                toReturn.append("\n                 dijit.byId('swbDialog3').show();");
                toReturn.append("\n             }");
                toReturn.append("\n        },");
                toReturn.append("\n        error: function(response)");
                toReturn.append("\n        {");
                toReturn.append("\n            return response;");
                toReturn.append("\n        },");
                toReturn.append("\n        handleAs: \"text\"");
                toReturn.append("\n    });");
                toReturn.append("\n}");
                toReturn.append("\n</script>");

                toReturn.append("\n<div dojoType=\"dijit.Dialog\" class=\"col-lg-2 col-lg-offset-5 co-md-8 col-sm-8 col-sm-offset-2 col-xs-12 swb-ventana-dialogo\" "); //soria
                toReturn.append("id=\"swbDialog3\" title=\"");
                toReturn.append(getLocaleString("successfulOperation", lang));
                toReturn.append("");
                toReturn.append("\" >\n");
                toReturn.append("\n<div class=\"panelDialog panelDialog-default\">");
                toReturn.append("\n<div class=\"swb-panel-cuerpo\">");
                toReturn.append("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" ");
                toReturn.append("id=\"swbDialogImp3\" executeScripts=\"true\">\n");
                toReturn.append("          <p class=\"text-center bold\"><strong>");
                toReturn.append(getLocaleString("successfulOperation", lang));
                toReturn.append("</strong></p>\n");
                toReturn.append("<div class=\"btn-group col-lg-2 col-lg-offset-3 col-md-12 \">");
                toReturn.append("<button dojoType=\"dijit.form.Button\" class=\"swb-boton-enviar\" ");
                toReturn.append("onclick=\"dijit.byId('swbDialog3').hide()\">");
                toReturn.append(getLocaleString("success", lang));
                toReturn.append("</button>");
                toReturn.append("  </div>\n");

                toReturn.append("  </div>\n");
                toReturn.append("  </div>\n");
                toReturn.append("  </div>\n");
                toReturn.append("</div>\n");
                if (itDeliverables.hasNext()) {
                    final SWBModel scorecard = (SWBModel) obj.getModel().getModelObject().getGenericInstance();
                    final String scorecardId = scorecard.getId();
                    Object periodId = request.getSession(true).getAttribute(scorecardId);
                    if (periodId != null && Period.ClassMgr.hasPeriod(periodId.toString(), scorecard)) {
                        Period period = Period.ClassMgr.getPeriod(periodId.toString(), scorecard);
                        toReturn.append("\n<div class=\"table-responsive\" id=\"swbDeliverable\">");
                        toReturn.append(listDeliverables(itDeliverables, suri, obj, prop, type, period, usrWithGrants));
                        toReturn.append("\n</div>");
                    }
                }
            }
        }
        return toReturn.toString();
    }

    /**
     * Vista utilizada para recargar la vista despu&eacute;s de ejecutar la
     * acci&oacute;n, de eliminaci&oacute;n.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son: {@literal edit},
     * {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    private String renderElementReload(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type) {
        StringBuilder toReturn = new StringBuilder();
        String suri = (String) request.getParameter("suri");
        String usrWithGrants = (String) request.getAttribute("usrWithGrants") == null
                ? (String) request.getParameter("usrWithGrants")
                : (String) request.getAttribute("usrWithGrants");
        if (suri != null) {
            SemanticObject semObj = SemanticObject.getSemanticObject(URLDecoder.decode(suri));
            Initiative element = null;
            if (semObj != null && semObj.createGenericInstance() instanceof Initiative) {
                element = (Initiative) semObj.createGenericInstance();
                Iterator<Deliverable> itDeliverables = element.listDeliverables();

                final BSC scorecard = element.getBSC();
                final String scorecardId = scorecard.getId();
                Object periodId = request.getSession(true).getAttribute(scorecardId);
                if (periodId != null && Period.ClassMgr.hasPeriod(periodId.toString(), scorecard)) {
                    Period period = Period.ClassMgr.getPeriod(periodId.toString(), scorecard);
                    toReturn.append(listDeliverables(itDeliverables, suri, obj, prop, type, period, usrWithGrants));
                }
            }
        }
        return toReturn.toString();
    }

    /**
     * Lista en HTML los entregables.
     *
     * @param itDeliverables el iterador con los entregables
     * @param suri el objeto que contiene el formElement
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @param usrWithGrants define si existen permisos para editar o eliminar
     * los archivos adjuntos.
     * @return el objeto String que representa el c&oacute;digo HTML con el
     * conjunto de entregables.
     */
    private String listDeliverables(Iterator<Deliverable> itDeliverables, String suri,
            SemanticObject obj, SemanticProperty prop, String lang, Period period, String usrWithGrants) {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("\n<table class=\"table tabla-detalle\">");
        toReturn.append("\n<tbody>");
        itDeliverables = SWBComparator.sortByCreated(itDeliverables, false);

        while (itDeliverables.hasNext()) {
            Deliverable deliverable = itDeliverables.next();
            if (deliverable.isActive() && deliverable.isValid()) {
                toReturn.append("\n<tr>");

                FormElementURL urlRemove = getProcessURL(obj, prop);
                urlRemove.setParameter("_action", Action_REMOVE);
                urlRemove.setParameter("suriDeliv", deliverable.getURI());
                urlRemove.setParameter("obj", suri);
                urlRemove.setParameter("usrWithGrants", usrWithGrants);
                toReturn.append("\n<td>");
                toReturn.append("<span class=\"");
//                if (deliverable.getAutoStatus() != null && deliverable.getAutoStatus().getStatus()
//                        != null && deliverable.getAutoStatus().getIconClass() != null) {
//                    toReturn.append(deliverable.getAutoStatus().getIconClass());
//                } else {
//                    toReturn.append("indefinido");
//                }
                toReturn.append(deliverable.getAutoStatusIconClass());
                toReturn.append("\"></span>");
                toReturn.append("<span class=\"");
                toReturn.append(deliverable.getStatusIconClass(period));
                toReturn.append("\"></span>");
                toReturn.append("\n</td>");

//                toReturn.append("\n<td>");
//                toReturn.append("<div");
//                toReturn.append(" class=\"");
//                if (deliverable.getStatusAssigned() != null && deliverable.getStatusAssigned().
//                        getStatus() != null && deliverable.getStatusAssigned().getIconClass()
//                        != null) {
//                    toReturn.append(deliverable.getStatusAssigned().getIconClass());
//                } else {
//                    toReturn.append("indefinido");
//                }
//                toReturn.append("\"></div>");
//                toReturn.append("\n</td>");
                toReturn.append("\n<td>");
                toReturn.append("\n<a class=\"swb-url-lista detalle-archivos\" href=\"Deliverable");
                toReturn.append("?suri=");
                toReturn.append(deliverable.getEncodedURI());
                toReturn.append("\">");
                toReturn.append(deliverable.getTitle() == null ? "" : deliverable.getTitle());
                toReturn.append("\n</a>");
                toReturn.append("\n</td>");
                toReturn.append("\n<td>");
                toReturn.append(deliverable.getActualStart() == null ? ""
                        : SWBUtils.TEXT.getStrDate(deliverable.getActualStart(), "es", "dd/mm/yyyy"));
                toReturn.append(deliverable.getActualEnd() == null ? "" : " - ");
                toReturn.append(deliverable.getActualEnd() == null ? ""
                        : SWBUtils.TEXT.getStrDate(deliverable.getActualEnd(), "es", "dd/mm/yyyy"));
                toReturn.append("\n</td>");

                if ("true".equals(usrWithGrants)) {
                    toReturn.append("\n<td class=\"swb-td-accion\">");
                    toReturn.append("\n<a href=\"#\" onclick=\"if(confirm(\'");
                    toReturn.append("¿");
                    toReturn.append(getLocaleString("alertDeleteElement", lang));
                    toReturn.append(" \\'");
                    toReturn.append(deliverable.getTitle());
                    toReturn.append("\\' ?\')){ ");
                    toReturn.append("processUrlDeliv('");
                    toReturn.append(urlRemove);
                    toReturn.append("', 'null'); ");
                    toReturn.append("} else { return false;}");
                    toReturn.append("\">");

//                    toReturn.append("<i class=\"fa fa-trash-o fa-lg swb-boton-accion\" title=\"");
//                    toReturn.append(getLocaleString("delete", lang));
//                    toReturn.append("\"></i>");
                    toReturn.append("<span class=\"glyphicon glyphicon-trash\"></span>");
                    toReturn.append("\n</a>");
                    toReturn.append("\n</td>");
                }
                toReturn.append("\n</tr>");
            }
        }
        toReturn.append("\n</tbody>");
        toReturn.append("\n</table>");
        return toReturn.toString();
    }

    /**
     * Presenta la vista de los entregables asociados al elemento BSC,
     * aqu&iacute; se getiona los permisos para los usuarios
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son: {@literal edit},
     * {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    public String renderModeView(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        String suri = (String) request.getParameter("suri");
        if (suri == null) {
            suri = (String) request.getAttribute("suri");
        }

        if (suri != null) {
            SemanticObject semObj = SemanticObject.getSemanticObject(URLDecoder.decode(suri));
            Initiative element = null;
            if (semObj != null && semObj.createGenericInstance() instanceof Initiative) {
                element = (Initiative) semObj.createGenericInstance();
                Iterator<Deliverable> itDeliverables = element.listDeliverables();
                toReturn.append("\n<table width=\"98%\">");
                itDeliverables = SWBComparator.sortByCreated(itDeliverables, false);

                while (itDeliverables.hasNext()) {
                    Deliverable deliverable = itDeliverables.next();
                    toReturn.append("\n<tr>");
                    toReturn.append("\n<td>");
                    toReturn.append("\n<div>");
                    toReturn.append(deliverable.getDisplayTitle(lang) == null ? deliverable.getTitle() : deliverable.getDisplayTitle(lang));
                    toReturn.append("\n</div>");
                    toReturn.append("\n</td>");

                    toReturn.append("\n<td>");
                    toReturn.append(deliverable.getCreated() == null
                            ? SWBUtils.TEXT.getStrDate(new Date(), lang, "dd/mm/yyyy")
                            : SWBUtils.TEXT.getStrDate(deliverable.getCreated(), lang, "dd/mm/yyyy"));
                    toReturn.append("\n</td>");

                    toReturn.append("</tr>");
                }
                toReturn.append("\n</table>");
            }
        }
        return toReturn.toString();
    }
}
