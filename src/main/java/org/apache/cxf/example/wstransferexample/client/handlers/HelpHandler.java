/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import org.apache.cxf.example.wstransferexample.client.Controller;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;

/**
 * Print help.
 * @author Erich Duda
 */
public class HelpHandler implements KeywordHandler {

    private final Controller controller;
    
    public HelpHandler(Controller controller) {
        this.controller = controller;
    }
    
    public void handle(List<String> parameters) {
        controller.printHelp();
    }

    public String getHelp() {
        return "Print this help.";
    }
    
}
