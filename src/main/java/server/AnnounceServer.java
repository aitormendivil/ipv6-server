package server;

import configuration.Configuration;
import configuration.ConfigurationLine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Aitor on 5/3/16.
 */
public class AnnounceServer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(AnnounceServer.class.getName());

    private Configuration configuration;

    private MulticastSocket multicastSocket;
    private InetAddress MDir;
    private int port;


    public AnnounceServer(InetAddress MDir, int port, Configuration configuration) throws IOException {
        this.MDir = MDir;
        this.port = port;
        this.configuration = configuration;

        this.multicastSocket = new MulticastSocket(port);
    }


    public void run() {
        int timeToSleep = 1000;
        try {
            try {
                while (true) {
                    boolean error = sendAnnounce();
                    if(error){
                        LOGGER.severe("Error sending announce...");
                        if(timeToSleep < 60000)
                            timeToSleep = timeToSleep + 2000;
                    }
                    else{
                        timeToSleep = 1000;
                    }
                    Thread.currentThread().sleep(timeToSleep);
                }
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public boolean sendAnnounce(){

        int MaxLinesPerPacket = 10;

        List<String> messageList = new ArrayList<String>();

        String startMessage = "SSER " + this.port + "\n";
        String moreMessage = "MORE";
        String endMessage = "END";

        List<ConfigurationLine> configurationLines = configuration.getConfigurationLines();

        List<List<ConfigurationLine>> listofConfigurationLinesList = splitByGroupAsList(configurationLines, MaxLinesPerPacket);

        int listsSize = listofConfigurationLinesList.size();
        int cont = 0;
        Iterator<List<ConfigurationLine>> iterator = listofConfigurationLinesList.iterator();

        while(iterator.hasNext()){

            cont = cont + 1;
            List<ConfigurationLine> configurationLineList = iterator.next();
            Iterator<ConfigurationLine> insideiterator = configurationLineList.iterator();

            String message = startMessage;
            while(insideiterator.hasNext()){

                ConfigurationLine configurationLine = insideiterator.next();
                message = message + "PRG " + configurationLine.getId() + " " + configurationLine.getName() + "\n";
            }
            if(cont < listsSize)
                message = message + "MORE";
            else
                message = message + "END";
            messageList.add(message);

        }

        boolean error = false;
        for (String message :
                messageList) {
            error = this.send(message);
        }

        return error;
    }

    private static List<List<ConfigurationLine>> splitByGroupAsList(List<ConfigurationLine> list, int elementsInGroup) {
        List<List<ConfigurationLine>> result = new ArrayList<List<ConfigurationLine>>();

        List<ConfigurationLine> group = new ArrayList<ConfigurationLine>(elementsInGroup);
        for (ConfigurationLine s : list) {
            group.add(s);
            if (group.size() == elementsInGroup) {
                result.add(group);
                group = new ArrayList<ConfigurationLine>(elementsInGroup);
            }
        }
        if (!group.isEmpty()) {
            result.add(group);
        }
        return result;
    }


    public boolean send(String message) {

        if(this.multicastSocket != null){
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, this.MDir, this.port);
            try {
                this.multicastSocket.send(sendPacket);
                return false;
            }
            catch (Exception ex){
                LOGGER.severe("Impossible send message through socket. Exception: " + ex.getMessage());
                return true;
            }
        }
        else
            return true;

    }

}
