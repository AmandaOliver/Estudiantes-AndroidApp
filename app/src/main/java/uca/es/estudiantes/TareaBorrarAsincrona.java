package uca.es.estudiantes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by usuario on 30/11/2015.
 */
public class TareaBorrarAsincrona extends AsyncTask<Void , Void , String> {
    private String IP = "10.181.102.28";
    private Context context;
    private String apellidos;
    private int code;
    private View view;


    TareaBorrarAsincrona(Context c,  String a, View v){
        context=c;
        apellidos=a.replace(" ","");
        view=v;

    }
    @Override
    protected String doInBackground(Void... params) {

        URL url = null;
        try {
            String query = URLEncoder.encode(apellidos, "utf-8");
            url = new URL("http://"+IP+":8080/WebRestServer/TFG/delete/"+query);
        } catch (Exception exception ) {
            exception.printStackTrace();
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("DELETE");
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
       if(code==204){
           Snackbar.make(view, "¡Estudiante borrado!", Snackbar.LENGTH_LONG).show();

       }
        else Toast.makeText(context, "¡Estudiante no encontrado!",
               Toast.LENGTH_SHORT).show();


    }
}
