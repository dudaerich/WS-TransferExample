/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.cxf.example.wstransferexample.client;

/**
 * Helper class for holding configuration.
 * @author Erich Duda
 */
public class Config {
    
    private static Config instance;
    
    private String serverUrl;
    
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public String getServerUrl() {
        if (serverUrl == null) {
            return "http://localhost:8080/ResourceFactory";
        } else {
            return serverUrl;
        }
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    
}
