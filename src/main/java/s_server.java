import arguments.Arguments;
import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;
import com.github.jankroken.commandline.domain.UnrecognizedSwitchException;
import configuration.ConfigReader;
import configuration.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Aitor on 27/2/16.
 */
public class s_server {

    private static final Logger LOGGER = Logger.getLogger( s_server.class.getName() );

    public static void main(String[] args) {

        LOGGER.setLevel(Level.ALL);

        try {
            Arguments arguments = CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);

            LOGGER.info("Arguments: -f " + arguments.getConfFilename() + " -p " + arguments.getPort() + " -m "
                + arguments.getMdir() + " -o " + arguments.getMport());

            LOGGER.info("Reading Configuration...");

            Configuration configuration = new Configuration();

            ConfigReader configReader = new ConfigReader();
            configReader.getConfiguration(arguments.getConfFilename(), configuration);

            // LOGGER.info("Starting server... ");

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
        }

    }

}
