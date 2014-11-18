/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import org.apache.cxf.example.wstransferexample.client.ClientResourceManager;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.transfer.manager.MemoryResourceManager;
import org.w3c.dom.Element;

/**
 *
 * @author erich
 */
public class LsResHandler implements KeywordHandler {

    public void handle(List<String> parameters) throws HandlerException {
        if (!parameters.isEmpty()) {
            throw new HandlerException("Wrong number of arguments.");
        }
        int i = 1;
        for (EndpointReferenceType ref : ClientResourceManager.getInstance().getAllResources()) {
            // Getting uuid.
            List<Object> refEl = ref.getReferenceParameters().getAny();
            String uuid = "";
            for (Object o : refEl) {
                Element el = (Element) o;
                if (MemoryResourceManager.REF_NAMESPACE.equals(el.getNamespaceURI())
                        && MemoryResourceManager.REF_LOCAL_NAME.equals(el.getLocalName())) {
                    uuid = el.getTextContent();
                }
            }
            System.out.println(String.format("%d: %s, %s", i++, ref.getAddress().getValue(), uuid));
        }
    }

    public String getHelp() {
        return "Print list of created resources.";
    }
    
}
