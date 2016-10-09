package com.cne.rest.cxf.variant;

import java.util.List;

import org.apache.commons.jxpath.JXPathContext;

/**
 * 
 * @author lenovo
 *
 */
public class VariantManager {
    
    private Object variant;

    /**
     * 
     * @param variants
     * @throws Exception
     */
    public void applyAll(List<VariantName> variants) throws Exception {
        
        StringBuilder variantExp = new StringBuilder();
        
        for(VariantName name: variants) {
            if (name != null && name.getName() != null) {
                variantExp.append("/");
                variantExp.append(name.getName());
            }
        }
        
        JXPathContext jxpath = JXPathContext.newContext(variant);
        
        System.out.println("Variant JxPath Express: " + variantExp.toString());
        
        Variant variant = (Variant) jxpath.getValue(variantExp.toString());
        
        if (variant != null) {
            System.out.println("VARIANT NAME: " + variant.getName().getName());
        } else {
           throw new Exception("Could not find variant!");
        }
    }

    public void setVariant(Object variant) {
        this.variant = variant;
    }
    
}
