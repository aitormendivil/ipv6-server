package server;

import configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by Aitor on 6/3/16.
 */
public class ClientServer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ClientServer.class.getName());

    Configuration configuration;

    int port;

    public ClientServer(int port, Configuration configuration) {
        this.port = port;
        this.configuration = configuration;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            while (true) {
                try {
                    Socket s = serverSocket.accept();
                    RequestServer requestServer = new RequestServer(s, this.configuration);
                    new Thread(requestServer).start();
                }
                catch (Exception e){
                    serverSocket.close();
                    LOGGER.severe(e.getMessage());
                }
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
}