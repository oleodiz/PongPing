package Comunicacao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

    public  <T> Document parseToXML(T dicionario)
    {
        xs.alias(dicionario.getClass().getSimpleName(), dicionario.getClass());
        return parserToXml(xs.toXML(dicionario));
    }

    private Document parserToXml(String dados)
    {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;

            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(dados)));
        } catch (Exception e) {
           e.getMessage();
        }

        return  document;
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
