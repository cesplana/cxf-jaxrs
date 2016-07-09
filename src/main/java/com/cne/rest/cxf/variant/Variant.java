package com.cne.rest.cxf.variant;

/**
 * 
 * @author lenovo
 *
 */
public class Variant {
    
    private VariantName name;

    public void apply() {
        System.out.println("Applying Rules for : " + name.getName());
    }
    
    public VariantName getName() {
        return name;
    }

    public void setName(VariantName name) {
        this.name = name;
    }
    
}
