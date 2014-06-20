/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.server.resourcefactory;

import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.example.wstransferexample.server.resource.ResourceServer;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.transfer.manager.MemoryResourceManager;
import org.apache.cxf.ws.transfer.manager.ResourceManager;
import org.apache.cxf.ws.transfer.resource.Resource;
import org.apache.cxf.ws.transfer.resource.ResourceLocal;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactory;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactoryImpl;
import org.apache.cxf.ws.transfer.validationtransformation.XSDResourceValidator;
import org.apache.cxf.ws.transfer.validationtransformation.XSLTResourceTransformer;

/**
 * ResourceFactoryServer main class.
 * @author Erich Duda
 */
public class ResourceFactoryServer {
    
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
        resourceLocal.getValidators().add(
                new XSDResourceValidator(new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/schema/studentPut.xsd")),
                        new StudentPutResourceTransformer()));
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
        resourceFactory.setResourceResolver(
                new MyResourceResolver(RESOURCE_STUDENTS_URL, resourceManager, ResourceServer.RESOURCE_TEACHERS_URL));
        resourceFactory.getValidators().add(
                new XSDResourceValidator(new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/schema/studentCreate.xsd")),
                        new XSLTResourceTransformer(new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/xslt/studentCreate.xsl")))));
        resourceFactory.getValidators().add(new XSDResourceValidator(
                new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/schema/teacherCreateBasic.xsd"))));
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
