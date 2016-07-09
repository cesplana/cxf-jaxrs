package com.cne.rest.cxf.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cne.rest.cxf.model.EndClientSearchPM;
import com.cne.rest.cxf.model.ExternalContext;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author lenovo
 *
 */

public class EndClientSearchPSCImpl implements IEndClientSearchPSC<ExternalContext, EndClientSearchPM> {
    
    private final Logger logger = LoggerFactory.getLogger(EndClientSearchPSCImpl.class);
    
    private List<EndClientSearchPM> endClientSearchPMList = new ArrayList<>();
    
    public EndClientSearchPSCImpl() {
        // Default Contructor
    }
    
    @Override
    public List<EndClientSearchPM> getAllEndClientPM() {
        
        System.out.println("Displaying End Client PM list...");
        System.out.println("EndClientPMList.size() " + endClientSearchPMList.size());
        return endClientSearchPMList;
    }
    
    @Override
    public String execute(String requestPayload) {
        
        logger.info("Request Payload: {}", requestPayload);
        
        String response = null;
        
        //Presentation Model Builder
        try {
       
            /*ObjectMapper jsonmapper = new ObjectMapper();
            EndClientSearchPM endclientSearchPM = jsonmapper.readValue(requestPayload, EndClientSearchPM.class);
            
            System.out.println("EndClientSearch >>>>>>>>>>>>");
            System.out.println("FirstName: " + endclientSearchPM.getFirstName().getData());
            System.out.println("LastName: " + endclientSearchPM.getLastName().getData());
            System.out.println("City: " + endclientSearchPM.getCity().getData());
            
            endClientSearchPMList.add(endclientSearchPM);*/
            
            response = "Success";
            
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        
        return response;
    }

    @Override
    public EndClientSearchPM buildPresentationModel(ExternalContext executionContext) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
