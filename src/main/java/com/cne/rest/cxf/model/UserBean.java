package com.cne.rest.cxf.model;

/**
 * 
 * @author lenovo
 *
 */
public class UserBean {
    
    private String username;
    private String password;
    private String mandator;
    
    public UserBean() {
        // TODO Auto-generated constructor stub
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMandator() {
        return mandator;
    }

    public void setMandator(String mandator) {
        this.mandator = mandator;
    }
}
