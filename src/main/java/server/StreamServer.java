package server;

import server.utils.ClientRequest;

/**
 * Created by Aitor on 15/3/16.
 */
public class StreamServer implements Runnable {


    private ClientRequest clientRequest;

    public StreamServer(ClientRequest clientRequest) {

        this.clientRequest = clientRequest;

    }

    public void run() {

    }

}
