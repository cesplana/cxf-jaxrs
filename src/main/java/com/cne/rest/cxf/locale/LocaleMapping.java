package com.cne.rest.cxf.locale;

/**
 * 
 * @author lenovo
 *
 */
public class LocaleMapping {
   
    private String attribute;
    private String value;
    
    public LocaleMapping() {
        // Default Constructor
    }
    
    /**
     * 
     * @param attribute
     * @param value
     */
    public LocaleMapping(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }


    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
