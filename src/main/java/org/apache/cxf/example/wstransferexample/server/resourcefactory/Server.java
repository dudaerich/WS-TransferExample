/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.server.resourcefactory;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.transfer.manager.MemoryResourceManager;
import org.apache.cxf.ws.transfer.manager.ResourceManager;
import org.apache.cxf.ws.transfer.resource.Resource;
import org.apache.cxf.ws.transfer.resource.ResourceLocal;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactory;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactoryImpl;
import org.apache.cxf.ws.transfer.resourcefactory.resolver.SimpleResourceResolver;

/**
 * Server main class.
 * @author Erich Duda
 */
public class Server {
    
    public static final String RESOURCE_STUDENTS_URL = "http://localhost:8080/ResourceStudents";
    
    public static final String RESOURCE_FACTORY_URL = "http://localhost:8080/ResourceFactory";
    
    public static void main(String[] args) {
        ResourceManager resourceManager = new MemoryResourceManager();
        createResource(resourceManager);
        createResourceFactory(resourceManager);
    }
    
    private static void createResource(ResourceManager resourceManager) {
        ResourceLocal resourceLocal = new ResourceLocal();
        resourceLocal.setManager(resourceManager);
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(Resource.class);
        factory.setServiceBean(resourceLocal);
        factory.setAddress(RESOURCE_STUDENTS_URL);
        // Logging Interceptors
        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
        loggingInInterceptor.setPrettyLogging(true);
        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
        loggingOutInterceptor.setPrettyLogging(true);
        factory.getInInterceptors().add(loggingInInterceptor);
        factory.getOutInterceptors().add(loggingOutInterceptor);
        factory.create();
    }
    
    private static void createResourceFactory(ResourceManager resourceManager) {
        ResourceFactoryImpl resourceFactory = new ResourceFactoryImpl();
        resourceFactory.setResourceResolver(new SimpleResourceResolver(RESOURCE_STUDENTS_URL, resourceManager));
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(ResourceFactory.class);
        factory.setServiceBean(resourceFactory);
        factory.setAddress(RESOURCE_FACTORY_URL);
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
