/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.cxf.example.wstransferexample.client.exception.NotFoundException;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

/**
 *
 * @author Erich Duda
 */
public class ClientResourceManager {
    
    private static ClientResourceManager instance;
    
    private final List<EndpointReferenceType> resources;
    
    public static ClientResourceManager getInstance() {
        if (instance == null) {
            instance = new ClientResourceManager();
        }
        return instance;
    }
    
    public ClientResourceManager() {
        resources = new ArrayList<EndpointReferenceType>();
    }
    
    public EndpointReferenceType getResource(int i) throws NotFoundException {
        if (i >= resources.size()) {
            throw new NotFoundException();
        }
        return resources.get(i);
    }
    
    public void saveResource(EndpointReferenceType ref) {
        resources.add(ref);
    }
    
    public List<EndpointReferenceType> getAllResources() {
        return Collections.unmodifiableList(resources);
    }
}
