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

//Clase que modela la tarea asíncrona de mostrar los alumnos que están desarrollando el TFG
public class TareaTodosEnDesarrolloAsincrona extends AsyncTask<Void , Void , String> {
    private String ip = IP.getIP();
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
            URL urlToRequest = new URL("http://"+ip+":8080/WebRestServer/TFG/all");
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
        //borramos la lista que se estaba mostrando antes
        adaptador.clear();

        String nombre=null;
        String tema=null;
        String apellido1=null;
        String apellido2=null;
        String cadena=null;
        JSONObject jsonObject=null;

        try {
            //El servicio devuelve un Array de JSON
            JSONArray jsonArray = new JSONArray(results);

            //Recorremos el Array para obtener el JSON correspondiente a cada estudiante
            for (int i = 0; i < jsonArray.length(); i++) {

                jsonObject = jsonArray.getJSONObject(i);

                //Solo vamos a mostrar los que están desarrollando el TFG
                if(Objects.equals(jsonObject.getString("estado"), "ENDESARROLLO")){
                    nombre = jsonObject.getString("nombre");
                    apellido1= jsonObject.getString("apellido1");
                    apellido2=jsonObject.getString("apellido2");
                    tema= jsonObject.getString("tema");
                    cadena=nombre+" "+apellido1+" "+apellido2+" "+tema;
                    //Añadimos la cadena a la lista
                    adaptador.add(cadena);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //relacionamos el adaptador con la lista
        lista.setAdapter(adaptador);
    }
}
