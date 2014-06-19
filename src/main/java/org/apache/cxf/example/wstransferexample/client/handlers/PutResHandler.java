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
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.transfer.Put;
import org.apache.cxf.ws.transfer.Representation;
import org.apache.cxf.ws.transfer.resource.Resource;
import org.apache.cxf.ws.transfer.shared.handlers.ReferenceParameterAddingHandler;
import org.w3c.dom.Document;

/**
 *
 * @author Erich Duda
 */
public class PutResHandler implements KeywordHandler {

    public void handle(List<String> parameters) throws HandlerException {
        if (parameters.size() != 2) {
            throw new HandlerException("Wrong number of arguments.");
        }
        try {
            int resourceNumber = Integer.valueOf(parameters.get(0));
            int xmlNumber = Integer.valueOf(parameters.get(1));
            EndpointReferenceType ref = ClientResourceManager.getInstance().getResource(resourceNumber - 1);
            Document doc = XMLManager.getInstance().getDocument(xmlNumber - 1).getDocument();
            
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
            factory.setServiceClass(Resource.class);
            factory.setAddress(ref.getAddress().getValue());
            factory.getHandlers().add(new ReferenceParameterAddingHandler(ref.getReferenceParameters()));

            Resource client = (Resource) factory.create();
            Representation representation = new Representation();
            representation.setAny(doc.getDocumentElement());
            Put putRequest = new Put();
            putRequest.setRepresentation(representation);
            client.put(putRequest);
        } catch (NumberFormatException ex) {
            throw new HandlerException("Parameter must be integer.");
        } catch (NotFoundException ex) {
            throw new HandlerException("Resource is not found.");
        } catch (SOAPFaultException ex) {
            throw new HandlerException(ex.getLocalizedMessage());
        }
    }

    public String getHelp() {
        return "[numberOfResource] [numberOfXML] - Update resource by XML.";
    }
    
}
