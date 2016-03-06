import arguments.Arguments;
import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;
import com.github.jankroken.commandline.domain.UnrecognizedSwitchException;
import configuration.ConfigReader;
import configuration.Configuration;
import server.AnnounceServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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




            AnnounceServer announceServer = new AnnounceServer(InetAddress.getByName(arguments.getMdir()), Integer.parseInt(arguments.getMport()), configuration);

            new Thread(announceServer).start();



/*            for (String mensaje: listaMensajes
                 ) {
                System.out.println(mensaje);
                System.out.println("-----------");
            }*/


            // LOGGER.info("Starting announceServer... ");

/*

            InetAddress MDIR = InetAddress.getByName(MCAST_ADDR);
            Thread announceServer = announceServer();
            announceServer.start();
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
