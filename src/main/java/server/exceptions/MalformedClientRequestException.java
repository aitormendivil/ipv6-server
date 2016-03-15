package server.exceptions;

/**
 * Created by Aitor on 15/3/16.
 */
public class MalformedClientRequestException extends Exception{

    public MalformedClientRequestException() {}

    public MalformedClientRequestException(String message){
        super(message);
    }

}
