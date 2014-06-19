/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import java.util.logging.Logger;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.XMLManager;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;
import org.apache.cxf.example.wstransferexample.client.exception.NotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 *
 * @author Erich Duda
 */
public class ShowXMLHandler implements KeywordHandler {

    private static final Logger LOGGER = Logger.getLogger(ShowXMLHandler.class.getCanonicalName());
    
    private LSSerializer serializer;
    
    private LSOutput output;
    
    public ShowXMLHandler() {
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
            Document doc = XMLManager.getInstance().getDocument(i-1).getDocument();
            serializer.write((Node) doc, output);
        } catch (NumberFormatException ex) {
            throw new HandlerException("Parameter must be integer.");
        } catch (NotFoundException ex) {
            throw new HandlerException("XML is not found.");
        }
    }

    public String getHelp() {
        return "[numberOfXML] - Print loaded XML. See loadXML, lsXML.";
    }
    
}
