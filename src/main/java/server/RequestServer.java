package server;

import configuration.Configuration;
import server.exceptions.MalformedClientRequestException;
import server.exceptions.ScriptNotFoundException;
import server.utils.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by Aitor on 6/3/16.
 */
public class RequestServer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(RequestServer.class.getName());

    private Configuration configuration;

    private Socket socket;

    private BufferedReader br;

    private PrintWriter out;

    public RequestServer(Socket socket, Configuration configuration) throws IOException {

        this.configuration = configuration;
        this.socket = socket;

        br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        out = new PrintWriter(this.socket.getOutputStream(), true);

    }

    public void run() {

        LOGGER.info("Starting request server...");

        ClientRequest clientRequest = null;
        String line = null;

        boolean waiting = true;

        while (waiting) {
            try {
                line = this.br.readLine();
                if (line != null) {
                    LOGGER.info("Received message from client: " + line);
                    waiting = false;
                    clientRequest = new ClientRequest(this.socket, line, this.configuration);
                    out.println("REQ OK");
                }
                else{
                    LOGGER.info("Connection closed from client");
                    waiting = false;
                    socket.close();
                }
            }
            catch (MalformedClientRequestException me){
                waiting = true;
                out.println("REQ FAIL " + me.getMessage());
            }
            catch (ScriptNotFoundException se){
                waiting = true;
                out.println("REQ FAIL " + se.getMessage());
            }
            catch (Exception e){
                waiting = true;
                LOGGER.severe("Error handling the client Request");
            }
        }

        if (clientRequest != null) {
            try {
                StreamSender streamServer = new StreamSender(clientRequest);
                new Thread(streamServer).start();
            }
            catch (java.lang.Exception e){
                LOGGER.severe("Impossible start streaming" + e.getMessage());
            }
        }

    }

}
