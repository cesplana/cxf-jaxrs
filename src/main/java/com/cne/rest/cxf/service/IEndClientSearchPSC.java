package com.cne.rest.cxf.service;

import java.util.List;

import com.cne.rest.cxf.model.EndClientSearchPM;

/**
 * 
 * @author lenovo
 *
 */
public interface IEndClientSearchPSC<T_CONTEXT, T_MODEL> {
    
    /**
     * 
     * @param requestPayload
     * @return
     */
    public abstract String execute(String requestPayload);
    
    /**
     * 
     * @param executionContext
     * @return
     */
    public abstract T_MODEL buildPresentationModel(T_CONTEXT executionContext);
    
    /**
     * 
     * @return
     */
    public abstract List<EndClientSearchPM> getAllEndClientPM();
}
