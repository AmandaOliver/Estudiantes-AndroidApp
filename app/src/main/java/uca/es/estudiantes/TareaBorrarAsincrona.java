package uca.es.estudiantes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
    private TextView texto;
    private Context context;
    private String apellidos;

    TareaBorrarAsincrona(Context c, String a){
        context=c;
        apellidos=a.replace(" ","");
    }
    @Override
    protected String doInBackground(Void... params) {
        String text = null;
        HttpURLConnection urlConnection = null;
        try{
            String query = URLEncoder.encode(apellidos, "utf-8");
            URL urlToRequest = new URL("http://"+IP+":8080/WebRestServer/TFG/delete/"+query);
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




    }
}
