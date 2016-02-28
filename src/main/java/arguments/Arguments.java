package arguments;

import com.github.jankroken.commandline.annotations.*;

/**
 * Created by Aitor on 27/2/16.
 */
public class Arguments {

    private String confFilename;
    private String port;
    private String mdir;
    private String mport;
    private boolean debug = false;

    @Option
    @LongSwitch("conffile")
    @ShortSwitch("f")
    @SingleArgument
    @Required
    public void setConfFilename(String filename) {
        this.confFilename = filename;
    }

    @Option
    @LongSwitch("port")
    @ShortSwitch("p")
    @SingleArgument
    @Required
    public void setPort(String port) {
        this.port = port;
    }

    @Option
    @LongSwitch("mdir")
    @ShortSwitch("m")
    @SingleArgument
    @Required
    public void setMdir(String mdir) {
        this.mdir = mdir;
    }

    @Option
    @LongSwitch("mport")
    @ShortSwitch("o")
    @SingleArgument
    @Required
    public void setMport(String mport) {
        this.mport = mport;
    }

    @Option
    @LongSwitch("debug")
    @ShortSwitch("d")
    @Toggle(true)
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getConfFilename() {
        return confFilename;
    }

    public String getPort() {
        return port;
    }

    public String getMdir() {
        return mdir;
    }

    public String getMport() {
        return mport;
    }

    public boolean isDebug() {
        return debug;
    }

}
