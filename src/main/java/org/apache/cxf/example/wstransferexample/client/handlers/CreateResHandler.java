/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.example.wstransferexample.client.ClientResourceManager;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.XMLManager;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;
import org.apache.cxf.example.wstransferexample.client.exception.NotFoundException;
import org.apache.cxf.example.wstransferexample.server.resourcefactory.ResourceFactoryServer;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.transfer.Create;
import org.apache.cxf.ws.transfer.CreateResponse;
import org.apache.cxf.ws.transfer.Representation;
import org.apache.cxf.ws.transfer.resourcefactory.ResourceFactory;
import org.w3c.dom.Document;

/**
 *
 * @author erich
 */
public class CreateResHandler implements KeywordHandler {

    private final ResourceFactory client;
    
    public CreateResHandler() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(ResourceFactory.class);
        factory.setAddress(ResourceFactoryServer.RESOURCE_FACTORY_URL);
        client = (ResourceFactory) factory.create();
    }
    
    public void handle(List<String> parameters) throws HandlerException {
        if (parameters.size() != 1) {
            throw new HandlerException("Wrong number of arguments.");
        }
        try {
            int i = Integer.valueOf(parameters.get(0));
            Document doc = XMLManager.getInstance().getDocument(i - 1).getDocument();
            Representation representation = new Representation();
            representation.setAny(doc.getDocumentElement());
            Create createRequest = new Create();
            createRequest.setRepresentation(representation);
            CreateResponse createResponse = client.create(createRequest);
            ClientResourceManager.getInstance().saveResource(createResponse.getResourceCreated());
        } catch (NumberFormatException ex) {
            throw new HandlerException("Parameter must be integer.");
        } catch (NotFoundException ex) {
            throw new HandlerException("XML is not found.");
        } catch (SOAPFaultException ex) {
            throw new HandlerException(ex.getLocalizedMessage());
        }
    }

    public String getHelp() {
        return "[numberOfXML] - Creates XMLResource and saves its reference.";
    }
    
}
