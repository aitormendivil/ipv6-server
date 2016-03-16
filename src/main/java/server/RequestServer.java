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

        ClientRequest clientRequest = null;
        String line = null;

        boolean waiting = true;

        while (waiting) {
            try {
                line = this.br.readLine();

                if (line != null) {
                    waiting = false;
                    clientRequest = new ClientRequest(this.socket, line, this.configuration);
                    out.print("REQ OK");
                }
            }
            catch (MalformedClientRequestException me){
                out.print("REQ FAIL " + me.getMessage());
            }
            catch (ScriptNotFoundException se){
                out.print("REQ FAIL " + se.getMessage());
            }
            catch (java.lang.Exception e){
                LOGGER.severe("Error handling the client Request");
            }
            finally {

                try {
                    socket.close();
                } catch (IOException e) {
                    LOGGER.severe("Impossible to close socket");
                    e.printStackTrace();
                }
            }
        }

        if (!waiting) {
            try {
                StreamServer streamServer = new StreamServer(clientRequest);
                new Thread(streamServer).start();
            }
            catch (java.lang.Exception e){
                LOGGER.severe("Impossible start streaming" + e.getMessage());
            }
        }

    }

}
