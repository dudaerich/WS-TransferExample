package org.apache.cxf.example.wstransferexample.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import org.apache.cxf.example.wstransferexample.client.handlers.ExitHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.HelpHandler;

/**
 * Main Class.
 *
 */
public class App 
{
    private static final Logger LOGGER = Logger.getLogger(App.class.getCanonicalName());
    
    public static void main(String[] args){
        Controller controller = new Controller();
        registerKeywords(controller);
        
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line = reader.readLine();
                controller.processLine(line);
            } catch (IOException ex) {
                LOGGER.severe(ex.getLocalizedMessage());
            }
        }
    }
    
    private static void registerKeywords(Controller controller) {
        controller.registerKeyword("exit", new ExitHandler());
        controller.registerKeyword("help", new HelpHandler(controller));
    }
}
