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


public class MainActivity extends AppCompatActivity {

    public static String cookie;
    public static boolean logado;
    public static SharedPreferences sharedPrefs;
    public static SharedPreferences.Editor editor;
    Context context;
    Acesso acesso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        acesso = new Acesso("131.72.69.117", context);
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        logado = sharedPrefs.getBoolean("LOGADO", false);
        cookie = sharedPrefs.getString("COOKIE", "");
         editor = sharedPrefs.edit();

        acesso.validarCookie("oleodiz|1442363615|n8U8XzGgJzfGnULwNaiXPAc5HiOWhEsaj2Q03DMei0a|bd70600ab2c1a30c0bffb2d78c64509bac71e21690af98f20b06db22a7d577e3");
    }


}
