import arguments.Arguments;
import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;
import com.github.jankroken.commandline.domain.UnrecognizedSwitchException;
import configuration.ConfigReader;
import configuration.Configuration;
import server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Created by Aitor on 27/2/16.
 */
public class s_server {

    private static final Logger LOGGER = Logger.getLogger(s_server.class.getName());

    public static void main(String[] args) {

        LOGGER.setLevel(Level.ALL);

        try {
            Arguments arguments = CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);

            LOGGER.info("Arguments: -f " + arguments.getConfFilename() + " -p " + arguments.getPort() + " -m "
                + arguments.getMdir() + " -o " + arguments.getMport());

            //TODO: validate arguments

            LOGGER.info("Reading Configuration...");

            Configuration configuration = new Configuration();

            ConfigReader configReader = new ConfigReader();
            configReader.getConfiguration(arguments.getConfFilename(), configuration);




            Server server = new Server(InetAddress.getByName(arguments.getMdir()), Integer.parseInt(arguments.getMport()), configuration);

            List<String> listaMensajes = server.sendAnnounce();

            for (String mensaje: listaMensajes
                 ) {
                System.out.println(mensaje);
                System.out.println("-----------");
            }


            // LOGGER.info("Starting server... ");

/*

            InetAddress MDIR = InetAddress.getByName(MCAST_ADDR);
            Thread server = server();
            server.start();
*/


        } catch (InvalidCommandLineException clException) {
            clException.printStackTrace();
        } catch (InvalidOptionConfigurationException configException) {
            configException.printStackTrace();
        } catch (UnrecognizedSwitchException unrecognizedSwitchException) {
            unrecognizedSwitchException.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
