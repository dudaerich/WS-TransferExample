/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.cxf.example.wstransferexample.client.exception.NotFoundException;

/**
 *
 * @author erich
 */
public class XMLManager {
    
    private static XMLManager instance;
    
    protected List<XMLDocument> xmlDocuments;
    
    public static XMLManager getInstance() {
        if (instance == null) {
            instance = new XMLManager();
        }
        return instance;
    }
    
    public XMLManager() {
        xmlDocuments = new ArrayList<XMLDocument>();
    }
    
    public XMLDocument getDocument(int i) throws NotFoundException {
        if (i >= xmlDocuments.size()) {
            throw new NotFoundException();
        }
        return xmlDocuments.get(i);
    }
    
    public void saveDocument(XMLDocument document) {
        xmlDocuments.add(document);
    }
    
    public List<XMLDocument> getAllDocuments() {
        return Collections.unmodifiableList(xmlDocuments);
    }
}
