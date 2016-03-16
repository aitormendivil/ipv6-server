package server.utils;

import configuration.Configuration;
import configuration.ConfigurationLine;
import server.exceptions.MalformedClientRequestException;
import server.exceptions.ScriptNotFoundException;

import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by Aitor on 15/3/16.
 */
public class ClientRequest {

    private static final Logger LOGGER = Logger.getLogger(ClientRequest.class.getName());

    private Socket socket;

    private String requestLine;

    private Configuration configuration;

    private int requestId;
    private int requestClientPort;
    private String requestClientAddress;
    private ConfigurationLine requestConfigurationLine;

    public ClientRequest(Socket socket, String requestLine, Configuration configuration)
            throws MalformedClientRequestException, ScriptNotFoundException {

        this.socket = socket;
        this.requestLine = requestLine;
        this.configuration = configuration;

        this.parseRequestLine(requestLine);

        if(!this.getConfigurationLine())
            throw new ScriptNotFoundException("Requested script not found");
    }

    //REQ id puertocliente [direccioncliente]
    private void parseRequestLine(String requestLine) throws MalformedClientRequestException {
        if(requestLine.startsWith("REQ ")){

            try{
                String[] requestLinePieces = requestLine.split(" ");
                this.requestId = Integer.parseInt(requestLinePieces[1]);
                this.requestClientPort = Integer.parseInt(requestLinePieces[2]);
                String requestClientAddress =  requestLinePieces.length == 4 ?
                        requestLinePieces[3] : this.socket.getRemoteSocketAddress().toString();;

            } catch (Exception ex){
                LOGGER.info("Impossible to understand the client request");
                throw new MalformedClientRequestException("Impossible to understand the client request.\n Request "
                        + "format:  REQ id port [address]");
            }
        }
        else
        {
            LOGGER.info("The client request does not start by REQ");
            throw new MalformedClientRequestException("The Client request does not start by REQ");
        }
    }

    private boolean getConfigurationLine(){

        this.requestConfigurationLine = this.configuration.getConfigurationLineById(this.requestId);

        if(this.requestConfigurationLine != null)
            return true;
        else
            return false;

    }

    public int getRequestId() {
        return requestId;
    }

    public int getRequestClientPort() {
        return requestClientPort;
    }

    public String getRequestClientAddress() {
        return requestClientAddress;
    }

    public ConfigurationLine getRequestConfigurationLine() {
        return requestConfigurationLine;
    }

}
