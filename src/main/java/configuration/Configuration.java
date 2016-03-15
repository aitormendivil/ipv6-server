package configuration;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Aitor on 28/2/16.
 */
public class Configuration {


    private ArrayList<ConfigurationLine> configurationLines = null;


    public void addConfigurationLine(ConfigurationLine configurationLine){

        if(configurationLines == null)
            configurationLines = new ArrayList<ConfigurationLine>();

        configurationLines.add(configurationLine);

    }

    public void addConfigurationLineAtIndex(int index, ConfigurationLine configurationLine){

        if(configurationLines == null)
            configurationLines = new ArrayList<ConfigurationLine>();

        configurationLines.add(index, configurationLine);

    }


    public ArrayList<ConfigurationLine> getConfigurationLines() {
        return configurationLines;
    }

    public ConfigurationLine getConfigurationLineAtIndex(int index){

        if(configurationLines != null)
            return configurationLines.get(index);
        else
            return null;
    }

    public ConfigurationLine getConfigurationLineById(int id){

        if(configurationLines != null){
            Iterator<ConfigurationLine> configurationLineIterator = configurationLines.iterator();
            while (configurationLineIterator.hasNext()){
                ConfigurationLine configurationLine = configurationLineIterator.next();
                if(configurationLine.getId() == id)
                    return configurationLine;
            }
            return null;
        }
        else
            return null;

    }

}
