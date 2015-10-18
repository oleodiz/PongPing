package pong.ldz.com.ping;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Usuario;

public class RegistroActivity extends AppCompatActivity {

    EditText txt_senha, txt_confirmacaoSenha;
    AutoCompleteTextView txt_usuario, txt_email;
    Button btn_registrar;

    private View mProgressView;
    private View mLoginFormView;
    String usuario, senha, email;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txt_senha = (EditText) findViewById(R.id.txt_senha);
        txt_confirmacaoSenha = (EditText) findViewById(R.id.txt_confirmaSenha);
        txt_usuario = (AutoCompleteTextView) findViewById(R.id.txt_usuario);
        txt_email = (AutoCompleteTextView) findViewById(R.id.txt_email);
        btn_registrar = (Button) findViewById(R.id.btn_registrar);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        context = this;
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_usuario.getText().length()<5) {
                    txt_usuario.setError(getString(R.string.usuario_curto));
                    txt_usuario.requestFocus();
                }
                else {
                    if (txt_senha.getText().length() < 4) {
                        txt_senha.setError(getString(R.string.senha_curta));
                        txt_senha.requestFocus();
                    } else {
                        if (!txt_senha.getText().toString().equals(txt_confirmacaoSenha.getText().toString())) {
                            txt_confirmacaoSenha.setError(getString(R.string.senha_diferente));
                            txt_confirmacaoSenha.requestFocus();
                        }
                        else
                        {
                            if (txt_email.getText().length() < 4 || !emailValidator(txt_email.getText().toString()))
                            {
                                txt_email.setError(getString(R.string.email_invalido));
                                txt_email.requestFocus();
                            }
                            else {
                                usuario = txt_usuario.getText().toString();
                                senha = txt_senha.getText().toString();
                                email = txt_email.getText().toString();
                                cadastrar();
                            }
                        }
                    }
                }
            }
        });
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    void cadastrar()
    {
        showProgress(true);
        new Cadastrar().execute();
    }




    public class Cadastrar extends AsyncTask<Void, Void, Boolean> {

        String erro;

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            HttpURLConnection c = null;
            try {

                URL u = new URL("http://131.72.69.117/wordpress/api/auth/register/?username="+usuario+"&email="+email+"&user_pass="+senha+"&display_name="+usuario);
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
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
                            String statuss = new JSONObject(sb.toString()).getString("status");
                            if (statuss.equals("ok")) {
                                MainActivity.usuario = new Usuario();

                                MainActivity.usuario.id = new JSONObject(sb.toString()).getInt("user_id");
                                MainActivity.usuario.username = usuario;
                                MainActivity.usuario.avatar = "";
                                MainActivity.usuario.cookie = new JSONObject(sb.toString()).getString("cookie");

                                MainActivity.editor.putString("COOKIE", MainActivity.usuario.cookie);
                                MainActivity.editor.commit();

                                return true;
                            } else {
                                erro = new JSONObject(sb.toString()).getString("error");

                                return false;
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



            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);

            if (success) {
                Intent intent = new Intent(context, HostsActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(context, erro, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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
