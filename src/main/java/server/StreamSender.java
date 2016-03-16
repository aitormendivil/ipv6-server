package server;

import configuration.ConfigurationLine;
import server.exceptions.ScriptVideoFileNotFoundException;
import server.exceptions.StreamingScriptNotFoundException;
import server.utils.ClientRequest;

import java.io.File;

/**
 * Created by Aitor on 15/3/16.
 */
public class StreamSender implements Runnable {


    private ClientRequest clientRequest;
    private ConfigurationLine configurationLine;

    private Process runningScript;


    public StreamSender(ClientRequest clientRequest) {

        this.clientRequest = clientRequest;
        this.configurationLine = clientRequest.getRequestConfigurationLine();

    }

    public void run() {

        try {
            this.runningScript = Runtime.getRuntime().exec(generateCommmand(this.configurationLine));
            this.runningScript.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String generateCommmand(ConfigurationLine configurationLine) throws ScriptVideoFileNotFoundException, StreamingScriptNotFoundException {

        try {
            File f = new File(configurationLine.getScript());

            if (!f.exists()) {
                throw new ScriptVideoFileNotFoundException();
            }
        } catch (Exception e) {
            throw new ScriptVideoFileNotFoundException();
        }

        try {
            File f = new File("/scripts/stream_send.bash");

            if (!f.exists()) {
                throw new ScriptVideoFileNotFoundException();
            }
        } catch (Exception e) {
            throw new StreamingScriptNotFoundException();
        }

        String pattern = System.getProperty("os.name").toLowerCase().contains("win") ? "%s %s %s %s" : "bash %s %s %s %s";
        File stremScriptFile = new File("/scripts/stream_send.bash");

        return String.format(
                pattern,
                stremScriptFile.getAbsolutePath(),
                clientRequest.getRequestClientAddress(),
                clientRequest.getRequestClientPort(),
                this.configurationLine.getScript()
        );
    }

}
