package uca.es.estudiantes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//Clase que modela la tarea asíncrona de borrar un estudiante
public class TareaBorrarAsincrona extends AsyncTask<Void , Void , String> {

    private String ip = IP.getIP();
    private Context context;
    private String apellidos;
    private int code;//código para guardar la respuesta del servidor
    private View view;

    //constructor
    TareaBorrarAsincrona(Context c,  String a, View v){
        context=c;
        //Al path hay que pasarle los apellidos juntos,
        // pero en la búsqueda pueden estar separados
        apellidos=a.replace(" ","");
        view=v;

    }
    @Override
    protected String doInBackground(Void... params) {

        URL url = null;
        try {
            //es necesario codificar los apellidos en utf-8 para que la url funcione correctamente
            String query = URLEncoder.encode(apellidos, "utf-8");
            //Se indica la url de nuestro servicio
            url = new URL("http://"+ip+":8080/WebRestServer/TFG/delete/"+query);
        } catch (Exception exception ) {
            exception.printStackTrace();
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("DELETE");
            //Se guarda la respuesta del servidor
            code=httpURLConnection.getResponseCode();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    protected void onPostExecute(String results) {
        //si el codigo es 204 significa que la operación se ha realizado con éxito
       if(code==204){
           Snackbar.make(view, "¡Estudiante borrado!", Snackbar.LENGTH_LONG).show();

       }
        else Toast.makeText(context, "¡Estudiante no encontrado!",
               Toast.LENGTH_SHORT).show();


    }
}
