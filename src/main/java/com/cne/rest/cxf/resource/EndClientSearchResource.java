package com.cne.rest.cxf.resource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.util.MultiValueMap;

import com.cne.rest.cxf.locale.LocaleManager;
import com.cne.rest.cxf.model.EndClientSearchPM;
import com.cne.rest.cxf.model.UIElement;
import com.cne.rest.cxf.service.EndClientSearchPSCImpl;
import com.cne.rest.cxf.service.IEndClientSearchPSC;
import com.cne.rest.cxf.util.LocaleProvider;
import com.cne.rest.cxf.variant.VariantManager;
import com.cne.rest.cxf.variant.VariantName;


/**
 * 
 * @author lenovo
 *
 */
@Path("/endclientsearch")
public class EndClientSearchResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EndClientSearchResource.class);
    
    @SuppressWarnings("rawtypes")
    private IEndClientSearchPSC endClientSearchPSC = new EndClientSearchPSCImpl();
    
    private VariantManager variantManager;
    
    private LocaleManager<EndClientSearchPM> localeManager;
    
    @Path("/execute")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String execute(String requestPayload) {
        
        LOGGER.info("Request Payload: " + requestPayload);

        String response = null;
        
        try {
            
            if (requestPayload != null) {
                response = endClientSearchPSC.execute(requestPayload);
            } else {
                throw new Exception("Request Payload is NULL!");
            }
           
        } catch (Exception e) {
            Writer output = new StringWriter();
            PrintWriter pw = new PrintWriter(output);
            e.printStackTrace(pw);
            
            LOGGER.error(output.toString());
        }
        
        
        return "{\""+ response +"\"}";
    }
    
    @Path("/execute/{variants:.*}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String executeGet(@PathParam("variants") List<PathSegment> variants, @Context UriInfo queryParam) {

        if (queryParam != null) {
            
            System.out.println("Looping thru QueryParamMap...");
            
            for(Map.Entry<String, List<String>> queryParamMap : queryParam.getQueryParameters(false).entrySet()) {
               String key = queryParamMap.getKey();
               System.out.println("QueryParam Key: " + key + ": ");
               
               for(String value : queryParamMap.getValue()) {
                   System.out.println("Value: " + value);
               }
            }
            
            System.out.println();
            
            //response = "{\"result\" : \"" + LocaleProvider.applyLocale("lbl.title", queryParam.getQueryParameters(false).getFirst("lang"))+"\"}";
        }

        String response = "{\"response\" : \"success\"}";
        
        return response;
    }
    
    @Path("/execute/{variants:.*}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeGET(String requestPayload, @PathParam("variants") List<PathSegment> variants, @Context UriInfo queryParam) {
        
        
        System.out.println("Request Payload: " + requestPayload);
        
        System.out.println();
        LOGGER.info("Path Param: ");
        
        List<VariantName> variantList = new ArrayList<>();
        
        for(PathSegment pathsegment: variants) {
            System.out.println(pathsegment.toString());
            variantList.add(new VariantName(pathsegment.toString()));
        }
        
        EndClientSearchPM endclientsearchpm = new EndClientSearchPM();
        UIElement firstname = new UIElement();
        firstname.setLabel("Carlos");
        
        UIElement lastname = new UIElement();
        lastname.setLabel("Esplana");
        
        endclientsearchpm.setFirstName(firstname);
        endclientsearchpm.setLastName(lastname);
        
        String response = "{\"result\" : \"default value\"}";
        
        if (queryParam != null) {
            
            System.out.println();
            for(Map.Entry<String, List<String>> queryParamMap : queryParam.getQueryParameters(false).entrySet()) {
               String key = queryParamMap.getKey();
               System.out.println("QueryParam Key: " + key + ": ");
               
               for(String value : queryParamMap.getValue()) {
                   System.out.println("Value: " + value);
               }
            }
            
            System.out.println();
            
            //response = "{\"result\" : \"" + LocaleProvider.applyLocale("lbl.title", queryParam.getQueryParameters(false).getFirst("lang"))+"\"}";
        }
        
        try {
            variantManager.applyAll(variantList);
            
            localeManager.setLanguage(queryParam.getQueryParameters(false).getFirst("lang"));
            localeManager.applyAll(endclientsearchpm);
            
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        
        return Response.ok(endclientsearchpm).build();
    }

    /**
     * 
     * @param variantManager
     */
    public void setVariantManager(VariantManager variantManager) {
        this.variantManager = variantManager;
    }

    /**
     * 
     * @param localeManager
     */
    public void setLocaleManager(LocaleManager<EndClientSearchPM> localeManager) {
        this.localeManager = localeManager;
    }
    
    
}   
