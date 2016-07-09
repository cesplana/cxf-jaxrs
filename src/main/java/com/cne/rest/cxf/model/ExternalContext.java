package com.cne.rest.cxf.model;

/**
 * 
 * @author lenovo
 *
 */
public class ExternalContext {
    private UIElement firstName;
    private UIElement lastName;
    private UIElement city;
    
    public ExternalContext() {
        // TODO Auto-generated constructor stub
    }

    public UIElement getFirstName() {
        return firstName;
    }

    public void setFirstName(UIElement firstName) {
        this.firstName = firstName;
    }

    public UIElement getLastName() {
        return lastName;
    }

    public void setLastName(UIElement lastName) {
        this.lastName = lastName;
    }

    public UIElement getCity() {
        return city;
    }

    public void setCity(UIElement city) {
        this.city = city;
    }
    
}
