package configuration;

/**
 * Created by Aitor on 28/2/16.
 */
public class ConfigurationLine {

    private int id;
    private String script;
    private String name;

    public ConfigurationLine(int id, String script, String name) {
        this.id = id;
        this.script = script;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ConfigurationLine{" +
                "id=" + id +
                ", script='" + script + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
