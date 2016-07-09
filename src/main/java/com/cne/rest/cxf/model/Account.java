package com.cne.rest.cxf.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author cesplana
 * @since 05/09/2016
 */
@XmlRootElement(name = "Account")
public class Account {

    private long id;
    private String name;

    public Account() {
        // Default constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
