package br.com.example.projeto.login.Model;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.example.projeto.login.Webservice.Request;

/**
 * Created by Desktop on 10/07/2015.
 */
public class Usuario_model {
    public String msgError = "";

    public Boolean verificaLogin(String login, String senha){
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
}
