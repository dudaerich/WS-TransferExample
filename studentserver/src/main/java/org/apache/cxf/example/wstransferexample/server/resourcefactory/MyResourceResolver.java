/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.server.resourcefactory;

import org.apache.cxf.ws.transfer.Create;
import org.apache.cxf.ws.transfer.manager.ResourceManager;
import org.apache.cxf.ws.transfer.resourcefactory.resolver.ResourceReference;
import org.apache.cxf.ws.transfer.resourcefactory.resolver.ResourceResolver;
import org.apache.cxf.ws.transfer.shared.faults.InvalidRepresentation;
import org.w3c.dom.Element;

/**
 *
 * @author erich
 */
public class MyResourceResolver implements ResourceResolver {

    protected String studentURL;
    
    protected ResourceManager studentManager;
    
    protected String teachersURL;
    
    public MyResourceResolver(String studentURL, ResourceManager studentManager, String teachersURL) {
        this.studentURL = studentURL;
        this.studentManager = studentManager;
        this.teachersURL = teachersURL;
    }
    
    public ResourceReference resolve(Create body) {
        Element representationEl = (Element) body.getRepresentation().getAny();
        if ("student".equals(representationEl.getLocalName())) {
            return new ResourceReference(studentURL, studentManager);
        } else if ("teacher".equals(representationEl.getLocalName())) {
            return new ResourceReference(teachersURL, null);
        } else {
            throw new InvalidRepresentation();
        }
    }
    
}
