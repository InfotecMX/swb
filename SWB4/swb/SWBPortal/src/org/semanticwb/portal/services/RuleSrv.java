/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.Rule;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class RuleSrv {

    public Rule createRule(WebSite website, String title, String description, User user) throws SWBException {
        Rule rule = website.createRule();
        rule.setTitle(title);
        rule.setDescription(description);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", rule.getURI(), rule.getURI(), "create Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating rule", e);
        }
        return rule;

    }

    public Rule createRule(WebSite website, String id, String title, String description, User user) throws SWBException {
        Rule rule = website.createRule(id);
        rule.setTitle(title);
        rule.setDescription(description);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", rule.getURI(), rule.getURI(), "create Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating rule", e);
        }
        return rule;

    }

    public boolean removeRule(WebSite website, String id, User user) throws SWBException {
        boolean deleted = false;
        website.removeRule(id);
        deleted = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", id, id, "remove Rule", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing rule", e);
        }
        return deleted;

    }
}
