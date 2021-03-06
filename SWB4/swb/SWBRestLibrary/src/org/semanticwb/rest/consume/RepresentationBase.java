/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public abstract class RepresentationBase implements RepresentationRequest
{
    private static final Logger log = SWBUtils.getLogger(RepresentationBase.class);
    public static final String ORG_SEMANTICWB_REST_REPRESENTATIONBASE = "org.semanticwb.rest.RepresentationBase/";
    protected static final String CONTENT_TYPE = "Content-Type";
    protected final Set<Parameter> parameters = new HashSet<Parameter>();
    //protected String mediaType;
    protected Method method;
    protected ResponseDefinition responseDefinition;

    protected RepresentationBase()
    {
    }

    static RepresentationRequest createRepresenatationRequest(Element representation, Method method) throws RestException
    {
        String mediaType = representation.getAttribute("mediaType");
        if (mediaType == null)
        {
            throw new RestException("The mediaType atributo was not found");
        }

        try
        {

            Class<RepresentationRequest> repclass = RestSource.getRepresentationRequest(mediaType);
            if (repclass != null)
            {
                Object objrep = repclass.newInstance();
                if (objrep instanceof RepresentationRequest)
                {
                    RepresentationRequest representationInfo = (RepresentationRequest) objrep;
                    representationInfo.setMethod(method);
                    NodeList params = representation.getChildNodes();
                    for (int i = 0; i < params.getLength(); i++)
                    {
                        if (params.item(i) instanceof Element && ((Element) params.item(i)).getTagName().equals("param"))
                        {
                            Parameter param = Parameter.createParamterInfo((Element) params.item(i));
                            representationInfo.addParameter(param);
                        }
                    }
                    return representationInfo;
                }
                else
                {
                    throw new RestException("The representatin " + mediaType + " is not supported");
                }
            }
            else
            {
                throw new RestException("The representatin " + mediaType + " is not supported");
            }
        }
        catch (IllegalAccessException cnfe)
        {
            throw new RestException("The representatin " + mediaType + " is not supported", cnfe);
        }
        catch (InstantiationException cnfe)
        {
            throw new RestException("The representatin " + mediaType + " is not supported", cnfe);
        }
    }

    public Method getMethod()
    {
        return method;
    }

    private boolean exists(String parameterName, List<ParameterValue> values) throws RestException
    {
        for (ParameterValue value : values)
        {
            if (parameterName.equals(value.getName()) && value.getValue() != null)
            {
                return true;
            }
        }
        return false;
    }
    private Object convert(Object value,Class clazz)
    {
        Object convert=null;
        if(clazz.getName().equals("java.lang.String"))
        {
            convert=value.toString();
        }
        if(clazz.getName().equals("java.net.URI"))
        {
            try
            {
                convert=new URI(value.toString());
            }
            catch(Exception e)
            {
                log.debug(e);
            }
        }
        if(clazz.getName().equals("java.lang.Double"))
        {
            try
            {
                convert=Double.parseDouble(value.toString());
            }
            catch(Exception e)
            {
                log.debug(e);
            }
        }
        if(clazz.getName().equals("java.lang.Long"))
        {
            try
            {
                convert=Long.parseLong(value.toString());
            }
            catch(Exception e)
            {
                log.debug(e);
            }
        }
        if(clazz.getName().equals("java.lang.Float"))
        {
            try
            {
                convert=Float.parseFloat(value.toString());
            }
            catch(Exception e)
            {
                log.debug(e);
            }
        }
        if(clazz.getName().equals("java.lang.Integer"))
        {
            try
            {
                convert=Integer.parseInt(value.toString());
            }
            catch(Exception e)
            {
                log.debug(e);
            }
        }
        return convert;
    }
    private void valid(Parameter parameter, ParameterValue value) throws RestException
    {
        if (value.getValue() == null)
        {
            throw new RestException("The value is null");
        }
        if (!value.getValue().getClass().equals(parameter.getType()))
        {
            Object newvalue=convert(value.getValue(),parameter.getType());
            if(newvalue==null)
            {
                throw new RestException("Value invalid required :" + parameter.getType().getName() + " value type: " + value.getValue().getClass().getName());
            }
            else
            {
                value.setValue(newvalue);
            }
        }

    }

    private void valid(Parameter parameter, List<ParameterValue> values) throws RestException
    {
        for (ParameterValue value : values)
        {
            if (value == null)
            {
                throw new RestException("The value is null");
            }
            if (parameter.getName().equals(value.getName()))
            {
                valid(parameter, value);
            }
        }
    }

    public void checkParameters(List<ParameterValue> values) throws RestException
    {
        for (Parameter parameter : this.getRequiredParameters())
        {
            if (!exists(parameter.getName(), values))
            {
                throw new RestException("The parameter " + parameter.getName() + " was not found");
            }
            valid(parameter, values);
        }
        for (Parameter parameter : this.getOptionalParameters())
        {
            valid(parameter, values);
        }

        for (Parameter parameter : this.method.getRequiredParameters())
        {
            if (!exists(parameter.getName(), values))
            {
                throw new RestException("The parameter " + parameter.getName() + " was not found");
            }
            valid(parameter, values);
        }
        for (Parameter parameter : this.method.getOptionalParameters())
        {
            valid(parameter, values);
        }
    }

    public Parameter[] getRequiredParameters()
    {
        ArrayList<Parameter> getRequiredParameters = new ArrayList<Parameter>();
        for (Parameter p : parameters)
        {
            if (p.isRequired() && !p.isFixed())
            {
                getRequiredParameters.add(p);
            }
        }
        getRequiredParameters.addAll(Arrays.asList(method.getRequiredParameters()));
        return getRequiredParameters.toArray(new Parameter[getRequiredParameters.size()]);
    }

    public Parameter[] getOptionalParameters()
    {
        ArrayList<Parameter> getOptionalParameters = new ArrayList<Parameter>();
        for (Parameter p : parameters)
        {
            if (!p.isRequired() && !p.isFixed())
            {
                getOptionalParameters.add(p);
            }
        }
        getOptionalParameters.addAll(Arrays.asList(method.getOptionalParameters()));
        return getOptionalParameters.toArray(new Parameter[getOptionalParameters.size()]);
    }

    public Parameter[] getAllParameters()
    {
        ArrayList<Parameter> getAllParameters = new ArrayList<Parameter>();
        getAllParameters.addAll(parameters);
        getAllParameters.addAll(Arrays.asList(method.getOptionalParameters()));
        return getAllParameters.toArray(new Parameter[getAllParameters.size()]);
    }

    protected RepresentationResponse processResponse(HttpURLConnection con) throws IOException, InstantiationException, IllegalAccessException, RestException, ExecutionRestException
    {
        int responseCode = con.getResponseCode();
        String contentType=con.getHeaderField(CONTENT_TYPE);
        if (contentType != null)
        {            
            for (ResponseDefinition definition : method.getResponseDefinitions())
            {
                if (definition.getMediaType().equals(contentType) && definition.getStatus() == responseCode)
                {
                    Class clazz = RestSource.getRepresentationResponse(contentType);
                    Object obj = clazz.newInstance();
                    if (obj instanceof RepresentationResponse)
                    {
                        RepresentationResponse repResponse = (RepresentationResponse) obj;
                        repResponse.setMethod(method);
                        repResponse.setStatus(responseCode);
                        repResponse.setURL(con.getURL());
                        repResponse.process(con);
                        for (ResponseDefinition def : this.method.definitionResponses)
                        {
                            if (def.getMediaType().equals(contentType) && def.getStatus()==responseCode)
                            {
                                def.validateResponse(repResponse.getResponse());
                                return repResponse;
                            }
                        }
                        
                    }
                }
            }
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), con.getURL(), "The response " + contentType + " is not supported");
        }
        else
        {
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), con.getURL(), "The content-type was not found");
        }
    }
}
