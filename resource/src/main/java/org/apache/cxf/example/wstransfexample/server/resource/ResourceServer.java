/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransfexample.server.resource;

import javax.xml.transform.stream.StreamSource;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.transfer.manager.MemoryResourceManager;
import org.apache.cxf.ws.transfer.manager.ResourceManager;
import org.apache.cxf.ws.transfer.resource.Resource;
import org.apache.cxf.ws.transfer.resource.ResourceRemote;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactory;
import org.apache.cxf.ws.transfer.shared.TransferConstants;
import org.apache.cxf.ws.transfer.validationtransformation.XSDResourceTypeIdentifier;
import org.apache.cxf.ws.transfer.validationtransformation.XSDResourceValidator;
import org.apache.cxf.ws.transfer.validationtransformation.XSLTResourceTransformer;

/**
 *
 * @author erich
 */
public class ResourceServer {
    
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("serverUrl", true, "Url where server will listen.");
        options.addOption("help", false, "Print this message.");
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar client.jar", options);
                System.exit(0);
            }
            Config.getInstance().setServerUrl(cmd.getOptionValue("serverUrl"));
        } catch (ParseException ex) {
            System.err.println("Error: " + ex.getLocalizedMessage());
            System.exit(1);
        }
        
        ResourceManager resourceManager = new MemoryResourceManager();
        ResourceRemote resourceRemote = new ResourceRemote();
        resourceRemote.setManager(resourceManager);
        resourceRemote.getResourceTypeIdentifiers().add(new XSDResourceTypeIdentifier(
                new StreamSource(ResourceServer.class.getResourceAsStream("/xml/schema/teacher.xsd")),
                new XSLTResourceTransformer(
                        new StreamSource(ResourceServer.class.getResourceAsStream("/xml/xslt/teacherDefaultValues.xsl")),
                        new TeacherResourceValidator())));
        
        createResourceFactoryEndpoint(resourceRemote);
        createResourceEndpoint(resourceRemote);
    }
    
    public static void createResourceFactoryEndpoint(ResourceRemote resource) {
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(ResourceFactory.class);
        factory.setServiceBean(resource);
        factory.setAddress(Config.getInstance().getServerUrl() + TransferConstants.RESOURCE_REMOTE_SUFFIX);
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
        factory.setAddress(Config.getInstance().getServerUrl());
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
