package pong.ldz.com.ping;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Adapters.HostAdapter;
import Model.Host;

public class HostsActivity extends AppCompatActivity {

    public static ArrayList<Host> hosts;
    Context context;
    ListView ltv_hosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosts);

        context = this;
        ltv_hosts = (ListView) findViewById(R.id.ltv_hosts);

        new obeterHosts().execute(BootReciever.cookie);
    }

    private class obeterHosts extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(String... url) {

            HttpURLConnection c = null;
            try {
                URL u = new URL("http://"+BootReciever.endereco+"/wordpress/api/auth/getHosts/?cookie="+url[0]);
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(20000);
                c.setReadTimeout(20000);
                c.connect();
                int status = c.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        try {
                            hosts = new ArrayList<Host>();
                            JSONArray array = new JSONArray(sb.toString());
                            for (int i =0;i< array.length(); i++)
                            {
                                JSONObject hostJson = (JSONObject) array.get(i);
                                Host host = new Host();
                                host.id_host = hostJson.getInt("ID");
                                host.nome = hostJson.getString("NOME");
                                host.ip = hostJson.getString("IP");
                                host.ativo = hostJson.getInt("ATIVO") == 1;
                                host.em_pe = hostJson.getInt("EM_PE") == 1;
                                host.ultima_alteracao = hostJson.getString("ULTIMA_ALTERACAO");
                                host.latitude = hostJson.getString("LATITUDE");
                                host.longitude = hostJson.getString("LONGITUDE");

                                hosts.add(host);
                            }
                            publishProgress();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return false;
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Boolean... result) {
            HostAdapter adapter = new HostAdapter(hosts, context);
            ltv_hosts.setAdapter(adapter);

            ltv_hosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  //  Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                   // startActivity(intent);
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), ConfiguracoesActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_mapa) {
            Intent intent = new Intent(getApplicationContext(), MapaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
