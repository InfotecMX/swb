package org.semanticwb.social.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge.jimenez
 */
public class SocialCreateUser extends GenericResource
{

    private static Logger log = SWBUtils.getLogger(SocialCreateUser.class);


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        StringBuilder ret = new StringBuilder();
        SWBResourceURL url = paramRequest.getActionUrl();
         ret.append("<script type=\"text/javascript\">\n"+
        "           dojo.require(\"dojo.parser\");\n"+
        "                   dojo.require(\"dijit.layout.ContentPane\");\n"+
        "                   dojo.require(\"dijit.form.FilteringSelect\");\n"+
        "                   dojo.require(\"dijit.form.CheckBox\");\n"+
                 "function validpwd(pwd){\n" +
                 "var ret=true;\n"+
                 ((SWBPlatform.getSecValues().isDifferFromLogin())?
                     "if (dijit.byId('Ulogin').textbox.value == pwd) { ret=false;}":"")+"\n"+
                 ((SWBPlatform.getSecValues().getMinlength()>0)?
                     "if (pwd.length < "+SWBPlatform.getSecValues().getMinlength()+") { ret=false;}":"")+"\n"+
                 ((SWBPlatform.getSecValues().getComplexity()==1)?
                     "if (!pwd.match(/^.*(?=.*[a-zA-Z])(?=.*[0-9])().*$/) ) { ret=false;}":"")+"\n"+
                 ((SWBPlatform.getSecValues().getComplexity()==2)?
                     "if (!pwd.match(/^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W])().*$/) ) { ret=false;}":"")+"\n"+
                 "return ret;\n"+
                  "}\n"+
        "        </script>\n");
        //http://www.semanticwebbuilder.org/swb4/ontology#User
        out.println("<div dojoType=\"dijit.layout.ContentPane\" style=\"border:0px; width:100%; height:100%\">");
        ret.append("<form id=\""+User.swb_User.getClassName()+"/create\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        ret.append("action=\""+url.setAction(SWBResourceURL.Action_ADD)+"\" ");
        ret.append("onSubmit=\"submitForm('"+User.swb_User.getClassName()+"/create');return false;\" method=\"POST\">");
        ret.append("\t<fieldset>\n\t<table>\n\t\t<tr>\n\t\t\t<td align=\"right\">\n\t\t\t\t<label>").
                append(paramRequest.getLocaleString("userRep")).append("</label>");
        ret.append("\n\t\t\t</td>\n\t\t\t<td>");
        Iterator<UserRepository> itur = SWBContext.listUserRepositories();
        ret.append("\n\t\t\t\t<select dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" name=\"userRepository\" id=\"userRepository\" >");
        while (itur.hasNext())
        {
            UserRepository ur = itur.next();
            ret.append("\n\t\t\t\t\t<option value=\"" + ur.getId() + "\">" + ur.getDisplayTitle(paramRequest.getUser().getLanguage()) + "</option>"); //todo Add Language
        }
        ret.append("\n\t\t\t\t</select>\n\t\t\t</td>\n\t\t</tr>");
        ret.append("\n\t\t<tr>\n\t\t\t<td align=\"right\">\n\t\t\t\t<label>"+paramRequest.getLocaleString("userID")
                +" <em>*</em></label>\n\t\t\t</td>\n\t\t\t<td>");
        ret.append("<input type=\"text\" id=\"Ulogin\" name=\"login\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" " +
                "promptMessage=\""+paramRequest.getLocaleString("userMsgID")
                +"\" invalidMessage=\""+paramRequest.getLocaleString("userErrID")
                +"\" isValid=\"return canAddLogin(dijit.byId('userRepository').value,this.textbox.value);\" trim=\"true\" />");
        ret.append("\n\t\t\t</td>\n\t\t</tr>");
        ret.append("\n\t\t<tr>\n\t\t\t<td align=\"right\">\n\t\t\t\t<label>"+paramRequest.getLocaleString("userPWD")
                +" <em>*</em></label>\n\t\t\t</td>\n\t\t\t<td>");
        ret.append("<input type=\"password\" name=\"passwd\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" ");
        ret.append("promptMessage=\""+paramRequest.getLocaleString("userMsgPWD")
                +"\" invalidMessage=\""+paramRequest.getLocaleString("userErrPWD")+"\" trim=\"true\" isValid=\"return validpwd(this.textbox.value);\" />");
        ret.append("\n\t\t\t</td>\n\t\t</tr>\n\t<tr>\n\t\t<td align=\"center\" colspan=\"2\">");
        ret.append("<button dojoType='dijit.form.Button' type=\"submit\">"+paramRequest.getLocaleString("SveBtn")+"</button>\n");
        ret.append("<button dojoType='dijit.form.Button' onclick=\"dijit.byId('" + User.swb_User.getClassName() + "/create').hide();\">"+paramRequest.getLocaleString("CnlBtn")+"</button>\n");
        ret.append("\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\t</fieldset>\n</form>");
        ret.append("</div>");
        out.println(ret.toString());
    }



    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String action = (response.getAction() != null) ? response.getAction() : "";
        if(action.equals(SWBResourceURL.Action_ADD)){
            String usrep = request.getParameter("userRepository");
            String login = request.getParameter("login");
            String password = request.getParameter("passwd");
            if (null==usrep||null==login||login.length()==0||null==password||password.length()==0) {
                response.setMode(SWBResourceURL.Mode_VIEW);
                return;
            }
            UserRepository ur = SWBContext.getUserRepository(usrep);
            //System.out.println("UR:"+ur);
            if (null!=ur.getUserByLogin(login)){
                response.setMode(SWBResourceURL.Mode_VIEW);
                return;
            }
            User user = ur.createUser();
            //System.out.println("UC:"+user);
            user.setLogin(login);
            user.setPassword(password);
            response.setMode(SWBResourceURL.Mode_EDIT);
            response.setRenderParameter("suri", user.getURI());
            response.setRenderParameter("login", user.getLogin());
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*
        ret.append("<script type=\"text/javascript\">\n");
        ret.append("addNewTab('"+request.getParameter("suri")+"','"+SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp','"+request.getParameter("login")+"');\n");
        ret.append("</script>");
        */
        String jspResponse = "/swbadmin/jsp/social/objectTab.jsp";
        try {
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }
}