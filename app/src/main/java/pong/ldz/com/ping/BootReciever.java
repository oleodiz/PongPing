package pong.ldz.com.ping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import Comunicacao.Acesso;
import Model.Usuario;

/**
 * Created by Leonardo on 08/11/2015.
 */
public class BootReciever extends BroadcastReceiver
{
    public static String cookie;
    public static boolean logado;
    public static String endereco;
    public static SharedPreferences sharedPrefs;
    public static SharedPreferences.Editor editor;
    public static Usuario usuario;
    Acesso acesso;
    @Override
    public void onReceive(Context context, Intent intent) {
        endereco = "131.72.69.117";

        acesso = new Acesso(endereco, context);
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        logado = sharedPrefs.getBoolean("LOGADO", false);
        cookie = sharedPrefs.getString("COOKIE", "");

        editor = sharedPrefs.edit();

        acesso.validarCookie(cookie);
    }

}
