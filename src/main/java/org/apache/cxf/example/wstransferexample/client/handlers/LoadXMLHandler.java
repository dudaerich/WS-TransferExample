/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.XMLDocument;
import org.apache.cxf.example.wstransferexample.client.XMLManager;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author erich
 */
public class LoadXMLHandler implements KeywordHandler {

    private static final Logger LOGGER = Logger.getLogger(LoadXMLHandler.class.getCanonicalName());
    
    private DocumentBuilder documentBuilder;
    
    public LoadXMLHandler() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            documentBuilder = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            LOGGER.severe(ex.getLocalizedMessage());
        }
    }
    
    public void handle(List<String> parameters) throws HandlerException {
        if (parameters.size() != 1) {
            throw new HandlerException("Wrong number of arguments.");
        }
        try {
            Document document = documentBuilder.parse(new File(parameters.get(0)));
            XMLDocument xmlDocument = new XMLDocument(parameters.get(0), document);
            XMLManager.getInstance().saveDocument(xmlDocument);
        } catch (SAXException ex) {
            throw new HandlerException("Error at parsing XML file.");
        } catch (IOException ex) {
            throw new HandlerException("File not found.");
        }
    }

    public String getHelp() {
        return "[xmlPath] - Load XML from the path. See lsXML, showXML.";
    }
    
}
