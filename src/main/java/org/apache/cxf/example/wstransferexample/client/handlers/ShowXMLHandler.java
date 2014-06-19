/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.XMLManager;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;
import org.apache.cxf.example.wstransferexample.client.exception.NotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Erich Duda
 */
public class ShowXMLHandler implements KeywordHandler {

    private static final Logger LOGGER = Logger.getLogger(ShowXMLHandler.class.getCanonicalName());
    
    private Transformer transformer;
    
    public ShowXMLHandler() {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        } catch (TransformerConfigurationException ex) {
            LOGGER.severe("Error occured during creating of Transformer instance.");
        }
    }
    
    public void handle(List<String> parameters) throws HandlerException {
        if (parameters.size() != 1) {
            throw new HandlerException("Wrong number of arguments.");
        }
        try {
            int i = Integer.valueOf(parameters.get(0));
            Document doc = XMLManager.getInstance().getDocument(i-1).getDocument();
            transformer.transform(new DOMSource((Node) doc), new StreamResult(System.out));
            System.out.println();
        } catch (NumberFormatException ex) {
            throw new HandlerException("Parameter must be integer.");
        } catch (NotFoundException ex) {
            throw new HandlerException("XML is not found.");
        } catch (TransformerException ex) {
            throw new HandlerException("Error occured during transformation the XML.");
        }
    }

    public String getHelp() {
        return "[numberOfXML] - Print loaded XML. See loadXML, lsXML.";
    }
    
}
