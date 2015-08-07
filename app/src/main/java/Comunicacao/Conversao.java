package Comunicacao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

import Model.Host;

/**
 * Created by Leonardo on 18/03/14.
 */
public class Conversao {
    private XStream xs;
    public Conversao()
    {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("ddd", "_");
        xs = new XStream(new DomDriver("UTF-8", replacer));
    }

    public <T> T XMLtoClass(T dicionario, String xml)
    {
        xs.alias(dicionario.getClass().getSimpleName(), dicionario.getClass());
        if(xml != null && xml != "") {
            try {
                dicionario = (T) xs.fromXML(xml);
            } catch (RuntimeException e) {
                e.printStackTrace();
                return null;
            }
        }
        else
            return null;

        return dicionario;
    }

}
