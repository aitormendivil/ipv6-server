package server.exceptions;

/**
 * Created by Aitor on 15/3/16.
 */
public class StreamingScriptNotFoundException extends Exception{

    public StreamingScriptNotFoundException() {}

    public StreamingScriptNotFoundException(String message){
        super(message);
    }

}
