package com.cne.rest.cxf.model;

public class EndClientSearchPM {
    
    private UIElement firstName;
    private UIElement lastName;
    private UIElement city;
    private String address;
    
    public EndClientSearchPM() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
   
}
