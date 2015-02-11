/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.server.resourcefactory;

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
import org.apache.cxf.ws.transfer.resource.ResourceLocal;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactory;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactoryImpl;
import org.apache.cxf.ws.transfer.validationtransformation.XSDResourceTypeIdentifier;
import org.apache.cxf.ws.transfer.validationtransformation.XSDResourceValidator;
import org.apache.cxf.ws.transfer.validationtransformation.XSLTResourceTransformer;

/**
 * ResourceFactoryServer main class.
 * @author Erich Duda
 */
public class ResourceFactoryServer {
    
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("resourceFactoryUrl", true, "Url where the ResourceFactory will listen.");
        options.addOption("resourceUrl", true, "Url where the Resource will listen.");
        options.addOption("resourceTeachersUrl", true, "Url of the ResourceFactory for teachers.");
        options.addOption("help", false, "Print this message.");
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar client.jar", options);
                System.exit(0);
            }
            Config.getInstance().setResourceFactoryUrl(cmd.getOptionValue("resourceFactoryUrl"));
            Config.getInstance().setResourceUrl(cmd.getOptionValue("resourceUrl"));
            Config.getInstance().setResourceTeachersUrl(cmd.getOptionValue("resourceTeachersUrl"));
        } catch (ParseException ex) {
            System.err.println("Error: " + ex.getLocalizedMessage());
            System.exit(1);
        }
        
        ResourceManager resourceManager = new MemoryResourceManager();
        createResource(resourceManager);
        createResourceFactory(resourceManager);
    }
    
    private static void createResource(ResourceManager resourceManager) {
        ResourceLocal resourceLocal = new ResourceLocal();
        resourceLocal.setManager(resourceManager);
        resourceLocal.getResourceTypeIdentifiers().add(
                new XSDResourceTypeIdentifier(
                        new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/schema/studentPut.xsd")),
                        new XSLTResourceTransformer(
                                new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/xslt/studentPut.xsl")),
                                new StudentPutResourceValidator())));
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(Resource.class);
        factory.setServiceBean(resourceLocal);
        factory.setAddress(Config.getInstance().getResourceUrl());
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
                new MyResourceResolver(
                        Config.getInstance().getResourceUrl(),
                        resourceManager,
                        Config.getInstance().getResourceTeachersUrl()));
        resourceFactory.getResourceTypeIdentifiers().add(
                new XSDResourceTypeIdentifier(
                        new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/schema/studentCreate.xsd")),
                        new XSLTResourceTransformer(
                                new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/xslt/studentCreate.xsl")))));
        resourceFactory.getResourceTypeIdentifiers().add(
                new XSDResourceTypeIdentifier(
                        new StreamSource(ResourceFactoryServer.class.getResourceAsStream("/xml/schema/teacherCreateBasic.xsd")),
                        new XSLTResourceTransformer(
                                new StreamSource(
                                        ResourceFactoryServer.class.getResourceAsStream("/xml/xslt/teacherCreateBasic.xsl")))));
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(ResourceFactory.class);
        factory.setServiceBean(resourceFactory);
        factory.setAddress(Config.getInstance().getResourceFactoryUrl());
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
