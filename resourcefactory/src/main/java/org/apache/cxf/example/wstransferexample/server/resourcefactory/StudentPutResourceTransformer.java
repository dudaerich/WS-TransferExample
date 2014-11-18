/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.server.resourcefactory;

import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.ws.transfer.Representation;
import org.apache.cxf.ws.transfer.shared.faults.PutDenied;
import org.apache.cxf.ws.transfer.validationtransformation.ResourceTransformer;
import org.apache.cxf.ws.transfer.validationtransformation.XSLTResourceTransformer;
import org.w3c.dom.Element;

/**
 *
 * @author erich
 */
public class StudentPutResourceTransformer implements ResourceTransformer {

    public static final String UID_NAMESPACE = "http://university.edu/student";
    
    public static final String UID_NAME = "uid";
    
    public void transform(Representation newRepresentation, Representation oldRepresentation) {
        ResourceTransformer transformer = new XSLTResourceTransformer(new StreamSource(
                getClass().getResourceAsStream("/xml/xslt/studentPut.xsl")));
        transformer.transform(newRepresentation, null);
        
        Element newRepresentationEl = (Element) newRepresentation.getAny();
        Element oldRepresentationEl = (Element) oldRepresentation.getAny();
        
        String newUid = newRepresentationEl.getElementsByTagNameNS(UID_NAMESPACE, UID_NAME).item(0).getTextContent();
        String oldUid = oldRepresentationEl.getElementsByTagNameNS(UID_NAMESPACE, UID_NAME).item(0).getTextContent();
        
        if (!newUid.equals(oldUid)) {
            throw new PutDenied();
        }
    } 
    
}
