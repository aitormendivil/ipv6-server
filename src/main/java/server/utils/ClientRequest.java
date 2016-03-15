package server.utils;

import configuration.Configuration;
import server.exceptions.MalformedClientRequestException;

import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by Aitor on 15/3/16.
 */
public class ClientRequest {


    private static final Logger LOGGER = Logger.getLogger(ClientRequest.class.getName());

    Socket socket;

    String requestLine;

    Configuration configuration;


    public ClientRequest(Socket socket, String requestLine, Configuration configuration) {
        this.socket = socket;
        this.requestLine = requestLine;
        this.configuration = configuration;


    }

    //REQ id puertocliente [direccioncliente]

    private void parseRequestLine(String requestLine) throws MalformedClientRequestException {

        if(requestLine.startsWith("REQ ")){

            try{


            }catch (Exception ex){

            }

        }
        else
        {
            LOGGER.severe("");
            throw new MalformedClientRequestException("The Client request does not start by REQ");
        }
    }


}
