package server;

import configuration.Configuration;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Aitor on 6/3/16.
 */
public class RequestServer implements Runnable {


    Configuration configuration;

    Socket socket;

    public RequestServer(Socket socket, Configuration configuration) {

        this.configuration = configuration;
        this.socket = socket;

    }

    public void run() {

    }

}
