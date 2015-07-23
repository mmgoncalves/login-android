package br.com.example.projeto.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.example.projeto.login.Model.Usuario_model;
import br.com.example.projeto.login.Task.Usuario_task;
import br.com.example.projeto.login.Webservice.Request;


public class Login extends ActionBarActivity {
    public String login;
    public String senha;
    private String msgError;

    ProgressBar progressBar;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) findViewById(R.id.telalogin);

        SharedPreferences pref = getSharedPreferences("logado", MODE_PRIVATE);
        String login = pref.getString("login", null);
        if(login != null && login != ""){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void logar(View v){
        // recuperar o editText
        EditText edtLogin = (EditText) findViewById(R.id.txtlogin);
        EditText edtSenha = (EditText) findViewById(R.id.txtsenha);

        // recuperar os valore digitados
        setLogin(edtLogin.getText().toString());
        setSenha(edtSenha.getText().toString());

        /* - METODO 1 - Usando uma AsyncTask especifica*
        // chama a classe que envia os dados ao webservice
        Usuario_task sTask = new Usuario_task();
        // passa os parametros
        sTask.addParams(login, senha);
        // executa
        sTask.execute();
        */

        /* METODO 2 - Usando o model*
        Usuario_model usuarioModel = new Usuario_model();
        Boolean resp = usuarioModel.verificaLogin(login, senha);

        if(resp){
            entrar(resp, login, usuarioModel.msgError);
        }else{
            Toast.makeText(getApplicationContext(), usuarioModel.msgError, Toast.LENGTH_SHORT).show();
        }
        */

        /* METODO 3 - Inner class*/
        LogarTask logarTask = new LogarTask();
        logarTask.execute();
    }

    public void entrar(Boolean resultTask, String login, String msgError){
        // verifica a resposta
        if(resultTask){
            // caso seja TRUE, grava no sharedPreferences o login do usuario
            SharedPreferences pref = getSharedPreferences("logado", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("login", login);
            editor.putString("data", new Date().toString());
            editor.commit();

            // finaliza a activity
            finish();

            // redireciona para a ativity princippal
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else{
            Toast.makeText(getApplicationContext(), msgError, Toast.LENGTH_SHORT).show();
        }
    }

    public void exibirProgress(Boolean exibir){
        progressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
        linearLayout.setVisibility( !exibir ? View.VISIBLE : View.GONE);
    }

    private class LogarTask extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String resp = "";
            try {
                String login = getLogin();
                String senha = getSenha();
                Log.i("Script", "login1: " + login);

                List<NameValuePair> values = new ArrayList<NameValuePair>();
                values.add(new BasicNameValuePair("login", login));
                values.add(new BasicNameValuePair("senha", senha));

                String pag = "logar";
                Request request = new Request();
                Log.i("Script", "login2: " + login);

                JSONObject json = request.postMethod(pag, values);

                String status = json.getString("status");


                if(status.equalsIgnoreCase("true")){
                    return true;
                }else{
                    setMsgError("Usuario ou senha incorretos");
                    return false;
                }
            }catch (Exception e){
                setMsgError("Erro no sistema");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            exibirProgress(false);
            entrar(s, getLogin(), getMsgError());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }
}
