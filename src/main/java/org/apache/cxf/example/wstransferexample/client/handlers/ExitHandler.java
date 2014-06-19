/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client.handlers;

import java.util.List;
import org.apache.cxf.example.wstransferexample.client.KeywordHandler;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;

/**
 * Exit program.
 * @author Erich Duda
 */
public class ExitHandler implements KeywordHandler {

    public void handle(List<String> parameters) throws HandlerException {
        if (!parameters.isEmpty()) {
            throw new HandlerException("Wrong number of arguments.");
        }
        System.exit(0);
    }

    public String getHelp() {
        return "Exit program.";
    }
    
}
