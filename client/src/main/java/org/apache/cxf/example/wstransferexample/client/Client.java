package org.apache.cxf.example.wstransferexample.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.cxf.example.wstransferexample.client.handlers.CreateResHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.DeleteResHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.ExitHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.GetResHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.HelpHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.LoadDirHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.LoadXMLHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.LsResHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.LsXMLHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.PutResHandler;
import org.apache.cxf.example.wstransferexample.client.handlers.ShowXMLHandler;

/**
 * Main Class.
 *
 */
public class Client
{
    private static final Logger LOGGER = Logger.getLogger(Client.class.getCanonicalName());
    
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("serverUrl", true, "Url of the WS-Transfer Server.");
        options.addOption("help", false, "Print this message.");
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar client.jar", options);
                System.exit(0);
            }
            Config.getInstance().setServerUrl(cmd.getOptionValue("serverUrl"));
        } catch (ParseException ex) {
            System.err.println("Error: " + ex.getLocalizedMessage());
            System.exit(1);
        }
        
        Controller controller = new Controller();
        registerKeywords(controller);
        
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                controller.processLine(line);
            } catch (IOException ex) {
                LOGGER.severe(ex.getLocalizedMessage());
            }
        }
    }
    
    private static void registerKeywords(Controller controller) {
        controller.registerKeyword("exit", new ExitHandler());
        controller.registerKeyword("help", new HelpHandler(controller));
        controller.registerKeyword("loadXML", new LoadXMLHandler());
        controller.registerKeyword("loadDir", new LoadDirHandler());
        controller.registerKeyword("lsXML", new LsXMLHandler());
        controller.registerKeyword("showXML", new ShowXMLHandler());
        controller.registerKeyword("createRes", new CreateResHandler());
        controller.registerKeyword("lsRes", new LsResHandler());
        controller.registerKeyword("getRes", new GetResHandler());
        controller.registerKeyword("deleteRes", new DeleteResHandler());
        controller.registerKeyword("putRes", new PutResHandler());
    }
}
