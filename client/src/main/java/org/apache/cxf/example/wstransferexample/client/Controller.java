/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.cxf.example.wstransferexample.client.exception.HandlerException;

/**
 * Class accepts requests and then calls appropriate methods.
 * @author Erich Duda
 */
public class Controller {
    
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getCanonicalName());
    
    protected Map<String, KeywordHandler> keywords;
    
    protected Pattern splitPattern;
    
    public Controller() {
        keywords = new HashMap<String, KeywordHandler>();
        splitPattern = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");
    }
    
    public void registerKeyword(String keyword, KeywordHandler handler) {
        if (keyword == null || handler == null) {
            throw new NullPointerException();
        }
        if (keywords.containsKey(keyword)) {
            LOGGER.warning(String.format("Keyword \"%s\" is already registered.", keyword));
        }
        keywords.put(keyword, handler);
    }
    
    public void processLine(String line) {
        List<String> commands = new ArrayList<String>();
        Matcher m = splitPattern.matcher(line);
        while (m.find()) {
            commands.add(m.group().replace("\"", "").trim());
        }
        if (!commands.isEmpty()) {
            String command = commands.get(0);
            if (keywords.containsKey(command)) {
                try {
                    keywords.get(command).handle(commands.subList(1, commands.size()));
                } catch (HandlerException ex) {
                    System.out.println(ex.getLocalizedMessage());
                    System.out.println("Try using help command.");
                }
            } else {
                System.out.println(String.format("Keyword \"%s\" is not defined.", command));
                printHelp();
            }
        }
    }
    
    public void printHelp() {
        System.out.println("Keywords:");
        for (String keyword : keywords.keySet()) {
            System.out.println(String.format("  %s: %s", keyword, keywords.get(keyword).getHelp()));
        }
    }
}
