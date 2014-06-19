/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
 * @author Erich Duda
 */
public class LoadDirHandler implements KeywordHandler {

    private static final Logger LOGGER = Logger.getLogger(LoadDirHandler.class.getCanonicalName());
    
    private DocumentBuilder documentBuilder;
    
    public LoadDirHandler() {
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
        File dir = new File(parameters.get(0));
        if (!dir.exists()) {
            throw new HandlerException("Directory does not exist.");
        }
        if (!dir.isDirectory()) {
            throw new HandlerException("Paramater must be directory.");
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                try {
                    Document doc = documentBuilder.parse(file);
                    XMLManager.getInstance().saveDocument(new XMLDocument(file.getPath(), doc));
                } catch (SAXException ex) {
                    LOGGER.severe("Error at parsing XML file.");
                } catch (IOException ex) {
                    LOGGER.severe("File not found.");
                }
            }
        }
    }

    public String getHelp() {
        return "[path] - Load all files from the directory.";
    }
    
}
