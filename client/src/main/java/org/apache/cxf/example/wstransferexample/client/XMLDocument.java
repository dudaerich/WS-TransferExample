/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client;

import org.w3c.dom.Document;

/**
 *
 * @author Erich Duda
 */
public class XMLDocument {
    
    private String name;
    
    private Document document;
    
    public XMLDocument() {
        
    }
    
    public XMLDocument(String name, Document document) {
        this.name = name;
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    
}
