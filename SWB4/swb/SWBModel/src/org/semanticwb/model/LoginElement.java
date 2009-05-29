package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class LoginElement extends org.semanticwb.model.base.LoginElementBase 
{
    static Logger log = SWBUtils.getLogger(LoginElement.class);
    public LoginElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        log.debug("Type: "+type);
        if(type.equals("dojo"))
        {
            setAttribute("isValid", "return validateElement('"+prop.getName()+"','"+getValidateURL(obj, prop)+"',this.textbox.value);");
        }else
        {
            setAttribute("isValid", null);
        }
        return super.renderElement(request, obj, prop, type, mode, lang);
    }

    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) throws FormValidateException
    {
        super.validate(request, obj, prop);

    String login=request.getParameter("usrLogin");
    log.debug("obj: "+obj.getDisplayName());
    String model=null;
    User cu = null;
    if (obj.instanceOf(User.sclass)) {

           cu= new User(obj);
           model=cu.getUserRepository().getId();

    } else if (obj.instanceOf(UserRepository.sclass)){
        UserRepository ur = new UserRepository(obj);
        model=ur.getId();
    }
    //String model=request.getParameter("model");
    //System.out.println("login:"+login+" model:"+model);
    if(login==null || login.length()==0 || login.indexOf(' ')>-1 || model==null)
    {
        throw new FormValidateException(getLocaleString("errEmpty","Login vacío o con espacios"));
        //System.out.println("false");
    }else
    {
        if(isValidId(login))
        {
            if (cu!=null && cu.getLogin().equalsIgnoreCase(login)) return;

            User tmpobj = SWBContext.getUserRepository(model).getUserByLogin(login);

            if(tmpobj!=null)
            {

                throw new FormValidateException(getLocaleString("errBusy","Login ya ocupado"));
                //System.out.println("false");
            }else
            {
                return;
                //System.out.println("true");
            }
        }else
        {
            throw new FormValidateException(getLocaleString("errInvalid","Login con caracteres inválidos"));
            //System.out.println("false");
        }
    }

    }

    private boolean isValidId(String id)
    {
        boolean ret=true;
        if(id!=null)
        {
            for(int x=0;x<id.length();x++)
            {
                char ch=id.charAt(x);
                if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_'))
                {
                    ret=false;
                    break;
                }
            }
        }else
        {
            ret=false;
        }
        return ret;
    }

}
