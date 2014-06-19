/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import java.util.logging.Logger;
import org.apache.cxf.example.wstransferexample.client.ClientResourceManager;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;
import org.apache.cxf.example.wstransferexample.client.exception.NotFoundException;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.transfer.Get;
import org.apache.cxf.ws.transfer.GetResponse;
import org.apache.cxf.ws.transfer.resource.Resource;
import org.apache.cxf.ws.transfer.shared.handlers.ReferenceParameterAddingHandler;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 *
 * @author erich
 */
public class GetResHandler implements KeywordHandler {

    private static final Logger LOGGER = Logger.getLogger(GetResHandler.class.getCanonicalName());
    
    private LSSerializer serializer;
    
    private LSOutput output;
    
    public GetResHandler() {
        try {
            DOMImplementationRegistry reg = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("ls");
            serializer = impl.createLSSerializer();
            serializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
            serializer.getDomConfig().setParameter("xml-declaration", Boolean.FALSE);
            output = impl.createLSOutput();
            output.setByteStream(System.out);
        } catch (ClassNotFoundException ex) {
            LOGGER.severe(ex.getLocalizedMessage());
        } catch (InstantiationException ex) {
            LOGGER.severe(ex.getLocalizedMessage());
        } catch (IllegalAccessException ex) {
            LOGGER.severe(ex.getLocalizedMessage());
        } catch (ClassCastException ex) {
            LOGGER.severe(ex.getLocalizedMessage());
        }
    }
    
    public void handle(List<String> parameters) throws HandlerException {
        if (parameters.size() != 1) {
            throw new HandlerException("Wrong number of arguments.");
        }
        try {
            int i = Integer.valueOf(parameters.get(0));
            EndpointReferenceType ref = ClientResourceManager.getInstance().getResource(i - 1);
            
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
            factory.setServiceClass(Resource.class);
            factory.setAddress(ref.getAddress().getValue());
            factory.getHandlers().add(new ReferenceParameterAddingHandler(ref.getReferenceParameters()));
            
            Resource client = (Resource) factory.create();
            GetResponse getResponse = client.get(new Get());
            
            serializer.write((Node) getResponse.getRepresentation().getAny(), output);
        } catch (NumberFormatException ex) {
            throw new HandlerException("Parameter must be integer.");
        } catch (NotFoundException ex) {
            throw new HandlerException("Resource is not found.");
        }
    }

    public String getHelp() {
        return "[numberOfResource] - Print resource. See createRes, lsRes.";
    }
    
}
