package uca.es.estudiantes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Clase que modela la tarea asíncrona de buscar un estudiante
public class TareaBusquedaAsincrona extends AsyncTask<Void , Void , String> {
    private String ip = IP.getIP();
    private TextView texto;
    private Context context;
    private String apellidos;
    private int code;

    TareaBusquedaAsincrona(Context c, TextView t, String a){
        context=c;
        texto=t;
        //Al path hay que pasarle los apellidos juntos,
        //pero en la búsqueda pueden estar separados
        apellidos=a.replace(" ","");
    }
    @Override
    protected String doInBackground(Void... params) {
        String text = null;
        HttpURLConnection urlConnection = null;
        try{
            //es necesario codificar los apellidos en utf-8 para que la url funcione correctamente
            String query = URLEncoder.encode(apellidos, "utf-8");

            URL urlToRequest = new URL("http://"+ip+":8080/WebRestServer/TFG/estudiante/"+query);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            InputStream in =
                    new BufferedInputStream(urlConnection.getInputStream());
            text=new Scanner(in).useDelimiter(
                    "\\A").next();

            code=urlConnection.getResponseCode();
        }catch (Exception e){return e.toString();}
        finally { if (urlConnection != null) { urlConnection.disconnect(); }
        }

        return text;
    }
    protected void onPostExecute(String results) {
        //si el codigo es 200 significa que la operación se ha realizado con éxito
        if (code!=200) {
            Toast.makeText(context, "¡Estudiante no encontrado!",
                    Toast.LENGTH_SHORT).show();

        } else {
            texto.setText(results);
        }


    }
}
