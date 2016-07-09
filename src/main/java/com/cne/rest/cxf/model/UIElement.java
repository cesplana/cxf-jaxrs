package com.cne.rest.cxf.model;

/**
 * 
 * @author lenovo
 * @since 05/15/2016
 * 
 */
public class UIElement {
    
    private String data;
    private boolean isDisabled;
    private boolean isHidden;
    private String label;
    
    public UIElement() {
        // Default Contructor
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
