package pong.ldz.com.ping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import Comunicacao.Acesso;
import Model.Usuario;


public class MainActivity extends AppCompatActivity {
    Context context;
    Acesso acesso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        BootReciever.endereco = "131.72.69.117";

        acesso = new Acesso(BootReciever.endereco, context);
        BootReciever.sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        BootReciever.logado = BootReciever.sharedPrefs.getBoolean("LOGADO", false);
        BootReciever.cookie = BootReciever.sharedPrefs.getString("COOKIE", "");
       // endereco = sharedPrefs.getString("ENDERECO", "");

        if (BootReciever.logado)
        {
            Intent intent = new Intent(context, HostsActivity.class);
            context.startActivity(intent);
            finish();
        }
        else
        {
            BootReciever.editor = BootReciever.sharedPrefs.edit();
            acesso.validarCookie(BootReciever.cookie);
        }
    }
}
