package Comunicacao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
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

import pong.ldz.com.ping.HostsActivity;
import pong.ldz.com.ping.LoginActivity;
import pong.ldz.com.ping.MainActivity;

/**
 * Created by Leonardo on 12/09/2015.
 */
public class Acesso {

    String enderecoSite;
    Context context;
    public Acesso(String enderecoSite, Context context)
    {
        this.enderecoSite = enderecoSite;
        this.context = context;
    }

    public void validarCookie(String cookie)
    {
        new validarCookie().execute(cookie);
    }

    private class validarCookie extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(String... url) {

            HttpURLConnection c = null;
            try {
                URL u = new URL("http://"+enderecoSite+"/wordpress/api/auth/validate_auth_cookie/?cookie="+url[0]);
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
                            boolean valid = new JSONObject(sb.toString()).getBoolean("valid");
                            if (valid) {
                                MainActivity.editor.putBoolean("LOGADO", true);
                                MainActivity.editor.commit();
                                publishProgress(true);
                                return true;
                            } else {
                                MainActivity.editor.putBoolean("LOGADO", false);
                                MainActivity.editor.commit();
                                publishProgress(false);

                            }
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
            publishProgress(false);
            return false;
        }

        @Override
        protected void onProgressUpdate(Boolean... result) {
            if (result[0])
            {
                Toast.makeText(context, "Válido", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, HostsActivity.class);
                context.startActivity(intent);
            }
            else
            {
                Toast.makeText(context, "Inválido", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
            ((Activity) context).finish();
        }
    }






}
