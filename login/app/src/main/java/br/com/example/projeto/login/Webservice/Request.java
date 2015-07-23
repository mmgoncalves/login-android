package br.com.example.projeto.login.Webservice;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by Desktop on 10/07/2015.
 */
public class Request {

    private String getUrl(String pag){
        String url = "http://192.168.1.107:8888/";
        switch (pag){
            case "logar": url += "logar.php";
            break;
        }

        return url;
    }

    public JSONObject getMethod(String pag) throws Exception{
        String url = getUrl(pag);
        //classe de conexao HTTP
        HttpClient httpClient = new DefaultHttpClient();
        //iniciar uma conexao com uma URL por metodo POST
        HttpGet httpGet = new HttpGet(url);
        //executar a URL e pegar o resultado
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String resultado = EntityUtils.toString(httpResponse.getEntity());

        JSONObject json = new JSONObject(resultado);
        return json;
    }

    public JSONObject postMethod(String pag, List<NameValuePair> params) throws Exception{
        String url = getUrl(pag);
        Log.i("Script", "url: " + url);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse resp = httpClient.execute(post);

        String resultado = EntityUtils.toString(resp.getEntity());

        JSONObject json = new JSONObject(resultado);
        return json;
    }


}
