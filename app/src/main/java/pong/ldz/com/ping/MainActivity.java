package pong.ldz.com.ping;

import android.content.Context;
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

    public static String cookie;
    public static boolean logado;
    public static String endereco;
    public static SharedPreferences sharedPrefs;
    public static SharedPreferences.Editor editor;
    public static Usuario usuario;
    Context context;
    Acesso acesso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        endereco = "131.72.69.117";

        acesso = new Acesso(endereco, context);
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        logado = sharedPrefs.getBoolean("LOGADO", false);
        cookie = sharedPrefs.getString("COOKIE", "");
       // endereco = sharedPrefs.getString("ENDERECO", "");

        editor = sharedPrefs.edit();

        acesso.validarCookie(cookie);
    }


}
