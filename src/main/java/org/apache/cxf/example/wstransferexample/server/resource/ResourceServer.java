/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.server.resource;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.transfer.manager.MemoryResourceManager;
import org.apache.cxf.ws.transfer.manager.ResourceManager;
import org.apache.cxf.ws.transfer.resource.Resource;
import org.apache.cxf.ws.transfer.resource.ResourceRemote;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactory;
import org.apache.cxf.ws.transfer.shared.TransferConstants;

/**
 *
 * @author erich
 */
public class ResourceServer {
    
    public static final String RESOURCE_TEACHERS_URL = "http://localhost:8081/ResourceTeachers";
    
    public static void main(String[] args) {
        ResourceManager resourceManager = new MemoryResourceManager();
        ResourceRemote resourceRemote = new ResourceRemote();
        resourceRemote.setManager(resourceManager);
        
        createResourceFactoryEndpoint(resourceRemote);
        createResourceEndpoint(resourceRemote);
    }
    
    public static void createResourceFactoryEndpoint(ResourceRemote resource) {
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(ResourceFactory.class);
        factory.setServiceBean(resource);
        factory.setAddress(RESOURCE_TEACHERS_URL + TransferConstants.RESOURCE_REMOTE_SUFFIX);
        // Logging Interceptors
        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
        loggingInInterceptor.setPrettyLogging(true);
        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
        loggingOutInterceptor.setPrettyLogging(true);
        factory.getInInterceptors().add(loggingInInterceptor);
        factory.getOutInterceptors().add(loggingOutInterceptor);
        factory.create();
    }
    
    public static void createResourceEndpoint(ResourceRemote resource) {
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(Resource.class);
        factory.setServiceBean(resource);
        factory.setAddress(RESOURCE_TEACHERS_URL);
        // Logging Interceptors
        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
        loggingInInterceptor.setPrettyLogging(true);
        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
        loggingOutInterceptor.setPrettyLogging(true);
        factory.getInInterceptors().add(loggingInInterceptor);
        factory.getOutInterceptors().add(loggingOutInterceptor);
        factory.create();
    }
    
}
