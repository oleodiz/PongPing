package Model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;

/**
 * Created by Leonardo on 06/08/2015.
 */

public class Hosts {
    public ArrayList<Host> hosts;
    public Hosts()
    {
        hosts = new ArrayList<Host>();
    }
}
