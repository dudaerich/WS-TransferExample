/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.XMLDocument;
import org.apache.cxf.example.wstransferexample.client.XMLManager;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;

/**
 *
 * @author Erich Duda
 */
public class LsXMLHandler implements KeywordHandler {

    public void handle(List<String> parameters) throws HandlerException {
        if (!parameters.isEmpty()) {
            throw new HandlerException("Wrong number of arguments.");
        }
        int i = 1;
        for (XMLDocument xmlDocument : XMLManager.getInstance().getAllDocuments()) {
            System.out.println(String.format("%d: %s", i++, xmlDocument.getName()));
        }
    }

    public String getHelp() {
        return "Print list of all loaded XML.";
    }
    
}
