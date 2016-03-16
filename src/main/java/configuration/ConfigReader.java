package configuration;

import java.io.*;

/**
 * Created by Aitor on 28/2/16.
 */
public class ConfigReader {

    public Configuration getConfiguration(String file, Configuration configuration){

        try {
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String line;

            while ((line = br.readLine()) != null) {
                ConfigurationLine configurationLine = parseLine(line);
                configuration.addConfigurationLine(configurationLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configuration;
    }

    private ConfigurationLine parseLine(String line) {

        String[] linePieces =  line.split(" ");

        String stringId = linePieces[0];
        int id = Integer.parseInt(stringId);
        String script = linePieces[1];

        String name = "";
        for (int i = 2; i < linePieces.length; i++ ){
            name = (name + " " + linePieces[i]).trim();
        }

        return new ConfigurationLine(id, script, name);

    }


}
