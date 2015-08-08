package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Host;
import pong.ldz.com.ping.R;

/**
 * Created by Leonardo on 07/08/2015.
 */
public class HostAdapter extends BaseAdapter {

    ArrayList<Host> hosts;
    Context context;
    LayoutInflater inflater = null;
    public HostAdapter(ArrayList<Host> hosts, Context context)
    {
        this.context = context;
        this.hosts = hosts;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return hosts.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView txt_nome, txt_ip, txt_data, txt_hora;
        ImageView img_okHost;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_host, null);

        holder.txt_nome = (TextView) rowView.findViewById(R.id.txt_nome);
        holder.txt_ip = (TextView) rowView.findViewById(R.id.txt_ip);
        holder.txt_data = (TextView) rowView.findViewById(R.id.txt_data);
        holder.txt_hora = (TextView) rowView.findViewById(R.id.txt_hora);
        holder.img_okHost = (ImageView) rowView.findViewById(R.id.img_okHost);

        holder.txt_nome.setText(hosts.get(position).nome);
        holder.txt_ip.setText(hosts.get(position).ip);
        holder.txt_data.setText(hosts.get(position).ultima_alteracao.split(" ")[0]);
        holder.txt_hora.setText(hosts.get(position).ultima_alteracao.split(" ")[1]);

        if (hosts.get(position).em_pe)
            holder.img_okHost.setImageResource(R.drawable.serveron);
        else
            holder.img_okHost.setImageResource(R.drawable.serveroff);

        return rowView;
    }
}
