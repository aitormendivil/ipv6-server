package server;

import configuration.Configuration;
import configuration.ConfigurationLine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Aitor on 5/3/16.
 */
public class Server /*implements Runnable*/ {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    Configuration configuration;

    MulticastSocket multicastSocket;
    InetAddress MDir;
    int port;

    public Server(InetAddress MDir, int port, Configuration configuration) throws IOException {
        this.MDir = MDir;
        this.port = port;
        this.configuration = configuration;

        this.multicastSocket = new MulticastSocket(port);
    }


/*    public void run() {
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket();
            try {
                while (true) {
                    byte[] sendData = new byte[256];
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, GROUP, PORT);
                    serverSocket.send(sendPacket);
                    ThreadUtilities.sleep(1000);
                }
            } catch (Exception e) {
                LOGGER.error(null, e);
            }
        } catch (Exception e) {
            LOGGER.error(null, e);
        }
    }*/

    public List<String> sendAnnounce(){

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

        return messageList;

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
                return true;
            }
            catch (Exception ex){
                LOGGER.severe("Impossible send message through socket. Exception: " + ex.getMessage());
                return false;
            }
        }
        else
            return false;

    }

}
