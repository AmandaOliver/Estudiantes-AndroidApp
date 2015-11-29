package uca.es.estudiantes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by usuario on 29/11/2015.
 */
public class TareaTodosEnDesarrolloAsincrona extends AsyncTask<Void , Void , String> {
    private String IP = "10.181.102.28";
    private Context context;
    private ListView lista;
    private ArrayAdapter<String> adaptador;

    TareaTodosEnDesarrolloAsincrona(Context c, ListView l,ArrayAdapter<String> a){
        context =c;
        lista =l;
        adaptador =a;
    }
    @Override
    protected String doInBackground(Void... params) {

        String text = null;
        HttpURLConnection urlConnection = null;
        try{
            URL urlToRequest = new URL("http://"+IP+":8080/WebRestServer/TFG/all");
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            InputStream in =
                    new BufferedInputStream(urlConnection.getInputStream());
            text=new Scanner(in).useDelimiter(
                    "\\A").next();
        }catch (Exception e){return e.toString();}
        finally { if (urlConnection != null) { urlConnection.disconnect(); }
        }
        return text;


    }
    protected void onPostExecute(String results) {
        adaptador.clear();
        String nombre=null;
        String tema=null;
        String apellido1=null;
        String apellido2=null;
        String cadena=null;
        JSONObject jsonObject=null;

        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                if(Objects.equals(jsonObject.getString("estado"), "ENDESARROLLO")){
                    nombre = jsonObject.getString("nombre");
                    apellido1= jsonObject.getString("apellido1");
                    apellido2=jsonObject.getString("apellido2");
                    tema= jsonObject.getString("tema");
                    cadena=nombre+" "+apellido1+" "+apellido2+" "+tema;
                    adaptador.add(cadena);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lista.setAdapter(adaptador);
    }
}
