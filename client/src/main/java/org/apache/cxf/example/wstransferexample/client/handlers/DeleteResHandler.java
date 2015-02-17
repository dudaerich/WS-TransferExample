/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.example.wstransferexample.client.ClientResourceManager;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.ProxyManager;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;
import org.apache.cxf.example.wstransferexample.client.exception.NotFoundException;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.transfer.Delete;
import org.apache.cxf.ws.transfer.resource.Resource;

/**
 *
 * @author Erich Duda
 */
public class DeleteResHandler implements KeywordHandler {

    public void handle(List<String> parameters) throws HandlerException {
        if (parameters.size() != 1) {
            throw new HandlerException("Wrong number of arguments.");
        }
        try {
            int i = Integer.valueOf(parameters.get(0));
            EndpointReferenceType ref = ClientResourceManager.getInstance().getResource(i - 1);
            Resource client = ProxyManager.getInstance().getResource(ref);
            
            client.delete(new Delete());
        } catch (NumberFormatException ex) {
            throw new HandlerException("Parameter must be integer.");
        } catch (NotFoundException ex) {
            Logger.getLogger(DeleteResHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SOAPFaultException ex) {
            throw new HandlerException(ex.getLocalizedMessage());
        }
    }

    public String getHelp() {
        return "[numberOfResource] - Delete resource.";
    }
    
}
