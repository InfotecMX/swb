/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import java.io.File;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.ObjectGroup;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class TemplateSrv 
{
    private static Logger log = SWBUtils.getLogger(SWBUtils.class);
    
    public Template createTemplate(WebSite website, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
        Template template=website.createTemplate();
        template.setTitle(titulo);
        template.setDescription(description);
        template.addGroup(objectgroup);
        template.setStatus(1);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", template.getName(), template.getURI(), "create Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating Template", e);
        }

        return template;
    }
    
    public Template createTemplate(WebSite website, String id, String fileName, String content, HashMap attaches, String titulo, String description, ObjectGroup objectgroup, User user) throws SWBException {
        Template template=website.createTemplate(id);
        
        template.setTitle(titulo);
        template.setDescription(description);
        template.addGroup(objectgroup);
        template.setStatus(1);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", template.getName(), template.getURI(), "create Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating Template", e);
        }

        return template;
    }

    public boolean removeTemplate(WebSite website, String id,User user) throws SWBException {
        boolean deleted = false;
        website.removeTemplate(id);
        deleted = true;

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove Template", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing Template", e);
        }

        return deleted;
    }
    
    public static boolean resetTemplates(WebSite website, Template template, User user) 
        {
            try {
                String rutawork = (String) SWBInstance.getWorkPath();
                File dir = new File(rutawork + "/sites/" + website.getName() + "/templates/" + template.getURI());
                if (dir != null && dir.exists() && dir.isDirectory()) {
                    File listado[] = dir.listFiles();
                    for (int i = 0; i < listado.length; i++) {
                        if (listado[i].isDirectory() && !listado[i].getName().equals(String.valueOf(template.getActualVersion().getValue()))) {
                            boolean flag = SWBUtils.IO.removeDirectory(listado[i].getPath());
                            if (flag) {
                                listado[i].delete();
                            }
                        }
                    }
                    boolean b = true;
                    String lastV = template.getActualVersion().getValue();
                    if (!lastV.equals("1")) {
                        File ActualVDir = new File(rutawork + "/sites/" + "topicmap" + "/templates/" + "templateid" + "/" + lastV);
                        File f = new File(rutawork + "/sites/" + "topicmap" + "/templates/" + "templateid" + "/1");
                        f.mkdir();
                        String sSource = "sites/" + "topicmap" + "/templates/" + "templateid" + "/" + lastV;
                        String sTarget = "sites/" + "topicmap" + "/templates/" + "templateid" + "/1";
                        b = SWBUtils.IO.copyStructure(ActualVDir.getPath() + "/", f.getPath() + "/", true, sSource, sTarget);
                    }
                    if (b) {
                        VersionInfo verInfo = website.createVersionInfo();
                        verInfo.setValue("1");
                        template.setActualVersion(verInfo);
                        template.setLastVersion(verInfo);
                        
                        //logeo
                        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", template.getURI(), template.getURI(), "remove Template versions", null);
                        try {
                            swbAdmLog.create();
                        } catch (Exception e) {
                            throw new SWBException("Error removing Template versions", e);
                        }

                        return true;
                    }
                }
            } catch (Exception e) {
                log.error("Error while removing template version width id:" + "templateid" + "-TemplateSrv:RemoveTemplateVersion",e);
            }
            return false;
        }
}
