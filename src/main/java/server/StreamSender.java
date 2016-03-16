package server;

import configuration.ConfigurationLine;
import server.exceptions.ScriptVideoFileNotFoundException;
import server.exceptions.StreamingScriptNotFoundException;
import server.utils.ClientRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Aitor on 15/3/16.
 */
public class StreamSender implements Runnable {


    private ClientRequest clientRequest;
    private ConfigurationLine configurationLine;
    private String command;

    private Process runningScript;


    public StreamSender(ClientRequest clientRequest)
            throws StreamingScriptNotFoundException, IOException, ScriptVideoFileNotFoundException {

        this.clientRequest = clientRequest;
        this.configurationLine = clientRequest.getRequestConfigurationLine();
        this.command = this.generateCommand(this.configurationLine);

    }

    public void run() {

        try {
            this.runningScript = Runtime.getRuntime().exec(this.command);
            this.runningScript.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String generateCommand(ConfigurationLine configurationLine)
            throws ScriptVideoFileNotFoundException, StreamingScriptNotFoundException, IOException {

        try {
            File f = new File(configurationLine.getScript());

            if (!f.exists()) {
                throw new ScriptVideoFileNotFoundException("Script video not found exception");
            }
        } catch (Exception e) {
            throw new ScriptVideoFileNotFoundException("Script video not found exception");
        }

        InputStream in = getClass().getResourceAsStream("/scripts/stream_send.bash");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        File temp = File.createTempFile("stream_send", ".bash");
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

        String line = "";
        while((line = reader.readLine()) != null){
          bw.write(line);
        }

        bw.close();

        String command = "bash %s %s %s %s";
        File streamScriptFile = new File(temp.getAbsolutePath());

        return String.format(command, streamScriptFile.getAbsolutePath(), clientRequest.getRequestClientAddress(),
                clientRequest.getRequestClientPort(), this.configurationLine.getScript()
        );
    }

}
