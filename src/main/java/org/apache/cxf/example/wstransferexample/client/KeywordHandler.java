/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client;

import java.util.List;

/**
 * Interface for handling keyword.
 * @author Erich Duda
 */
public interface KeywordHandler {
    
    public void handle(List<String> parameters);
    
    public String getHelp();
    
}
