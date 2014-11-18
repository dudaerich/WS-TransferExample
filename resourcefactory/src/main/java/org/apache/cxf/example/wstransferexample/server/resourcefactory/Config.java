/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.cxf.example.wstransferexample.server.resourcefactory;

/**
 * Helper class for holding configuration.
 * @author Erich Duda
 */
public class Config {
    
    private static Config instance;
    
    private String resourceFactoryUrl;
    
    private String resourceUrl;
    
    private String resourceTeachersUrl;
    
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public String getResourceFactoryUrl() {
        if (resourceFactoryUrl == null) {
            return "http://localhost:8080/ResourceFactory";
        } else {
            return resourceFactoryUrl;
        }
    }

    public void setResourceFactoryUrl(String resourceFactoryUrl) {
        this.resourceFactoryUrl = resourceFactoryUrl;
    }

    public String getResourceUrl() {
        if (resourceUrl == null) {
            return "http://localhost:8080/ResourceStudents";
        } else {
            return resourceUrl;
        }
    }

    public void setResourceUrl(String resourceStudentsUrl) {
        this.resourceUrl = resourceStudentsUrl;
    }

    public String getResourceTeachersUrl() {
        if (resourceTeachersUrl == null) {
            return "http://localhost:8081/ResourceTeachers";
        } else {
            return resourceTeachersUrl;
        }
    }

    public void setResourceTeachersUrl(String resourceTeachersUrl) {
        this.resourceTeachersUrl = resourceTeachersUrl;
    }
    
    
    
}
