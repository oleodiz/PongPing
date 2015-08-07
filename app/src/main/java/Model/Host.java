package Model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 06/08/2015.
 */
@XStreamAlias("Hosts")
public class Host {

    public int id_host;
    public String nome;
    public String ip;
    public boolean ativo;
    public boolean em_pe;
    public String ultima_alteracao;
}
