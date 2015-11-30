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

/**
 * Created by usuario on 30/11/2015.
 */
public class TareaBusquedaAsincrona extends AsyncTask<Void , Void , String> {
    private String IP = "10.181.102.28";
    private TextView texto;
    private Context context;
    private String apellidos;

    TareaBusquedaAsincrona(Context c, TextView t, String a){
        context=c;
        texto=t;
        apellidos=a.replace(" ","");
    }
    @Override
    protected String doInBackground(Void... params) {
        String text = null;
        HttpURLConnection urlConnection = null;
        try{
            String query = URLEncoder.encode(apellidos, "utf-8");
            URL urlToRequest = new URL("http://"+IP+":8080/WebRestServer/TFG/estudiante/"+query);
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

        Pattern pat = Pattern.compile("^java.*");
        Matcher mat = pat.matcher(results);
        if (mat.matches()) {
            Toast.makeText(context, "¡Estudiante no encontrado!",
                    Toast.LENGTH_SHORT).show();
        } else {
            texto.setText(results);
        }


    }
}
