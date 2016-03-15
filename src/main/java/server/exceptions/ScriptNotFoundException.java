package server.exceptions;

/**
 * Created by Aitor on 15/3/16.
 */
public class ScriptNotFoundException extends Exception{

    public ScriptNotFoundException() {}

    public ScriptNotFoundException(String message){
        super(message);
    }

}

