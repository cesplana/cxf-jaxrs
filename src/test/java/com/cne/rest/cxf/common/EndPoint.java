package com.cne.rest.cxf.common;

/**
 * 
 * @author lenovo
 *
 */
public enum EndPoint {

    ENDPOINT_ADDRESS("http://localhost:8081/com.cne.rest.cxf/presentation"),
    WADL_ADDRESS("?_wadl");
    
    private String address;
    
    private EndPoint(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
   
}
