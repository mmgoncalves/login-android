package br.com.example.projeto.login.Task;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import br.com.example.projeto.login.Login;
import br.com.example.projeto.login.Webservice.Request;

/**
 * Created by Desktop on 11/07/2015.
 */
public class Usuario_task extends AsyncTask<Void, Void, Boolean> {
    private String login;
    private String senha;
    public String msgError;

    public void addParams (String l, String s){
        login = l;
        senha = s;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean resp;
        try{
            List<NameValuePair> values = new ArrayList<NameValuePair>();

            values.add(new BasicNameValuePair("login", login));
            values.add(new BasicNameValuePair("senha", senha));
            String pag = "logar";
            Request request = new Request();
            JSONObject json = request.postMethod(pag, values);

            String status = json.getString("status");

            if(status.equalsIgnoreCase("true")){
                return true;
            }else{
                msgError = "Usuario ou senha incorretos";
                return false;
            }
        }catch (Exception e){
            msgError = "Erro: " + e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean res) {
        super.onPostExecute(res);
        Login lg = new Login();
        lg.entrar(res, login, msgError);
    }

}
