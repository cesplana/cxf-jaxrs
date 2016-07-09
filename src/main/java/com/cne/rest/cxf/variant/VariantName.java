package com.cne.rest.cxf.variant;

/**
 * 
 * @author lenovo
 *
 */
public class VariantName {
    private String name;

    public VariantName() {
        // Default Constructor
    }
    
    public VariantName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
