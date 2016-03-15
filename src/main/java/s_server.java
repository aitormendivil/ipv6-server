import arguments.Arguments;
import arguments.ArgumentsValidator;
import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;
import com.github.jankroken.commandline.domain.UnrecognizedSwitchException;
import configuration.ConfigReader;
import configuration.Configuration;
import server.AnnounceServer;
import server.ClientServer;

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

    public static void main(String[] args) throws Exception {

        LOGGER.setLevel(Level.ALL);

        try {
            Arguments arguments = CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);

            LOGGER.info("Arguments: -f " + arguments.getConfFilename() + " -p " + arguments.getPort() + " -m "
                + arguments.getMdir() + " -o " + arguments.getMport());

            if(!ArgumentsValidator.validateIpv6Address(arguments.getMdir())) {
                LOGGER.severe("Mdir argument is not a valid IPv6 address");
                System.exit(1);
            }

            if(!ArgumentsValidator.validatePort(arguments.getPort())){
                LOGGER.severe("The port argument is not a valid port");
                System.exit(2);
            }

            if(!ArgumentsValidator.validatePort(arguments.getMport())){
                LOGGER.severe("The Mport argument is not a valid port");
                System.exit(3);
            }

            if(!ArgumentsValidator.validateConfFilename(arguments.getConfFilename())){
                LOGGER.severe("The Configuration argument is not a valid file path");
                System.exit(4);
            }

            LOGGER.info("Reading Configuration...");

            Configuration configuration = new Configuration();

            ConfigReader configReader = new ConfigReader();
            configReader.getConfiguration(arguments.getConfFilename(), configuration);

            AnnounceServer announceServer = new AnnounceServer(InetAddress.getByName(arguments.getMdir()), Integer.parseInt(arguments.getMport()), configuration);

            new Thread(announceServer).start();

            ClientServer clientServer = new ClientServer(Integer.parseInt(arguments.getPort()), configuration);

            new Thread(clientServer).start();



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
