package pong.ldz.com.ping;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import Comunicacao.Conversao;
import Comunicacao.XMLParser;
import Model.Host;
import Model.Hosts;

public class MainActivity extends AppCompatActivity {

    Hosts hosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hosts = new Hosts();


        new CarregaXML().executeOnExecutor(Executors.newFixedThreadPool(4), "http://131.72.69.117/dados.xml");
    }


    private class CarregaXML extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... url) {

            XMLParser parser = new XMLParser(); // the parser create as seen in the Gist from GitHub
            String xml = parser.getXmlFromUrl(url[0]); // getting XML from URL

            hosts = new Conversao().XMLtoClass(hosts, xml);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
