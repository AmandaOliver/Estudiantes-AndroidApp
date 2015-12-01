package uca.es.estudiantes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

//Clase que modela la tarea asíncrona de crear un estudiante
public class TareaCrearAsincrona extends AsyncTask<Void , Void , String> {
    private String IP = "10.181.102.28";//cambiar esta ip por la del servidor
    private Context context;
    private int code;//código para guardar la respuesta del servidor
    private String nombre;
    private String apellidos1;
    private String apellidos2;
    private String tema;
    private String tutor1;
    private String tutor2;
    private String estado;
    private String fecha;
    private String calificacion;
    private static final int NOTIF_ALERTA_ID = 1;

    TareaCrearAsincrona(Context cn, String n, String a1, String a2, String t, String t1, String t2, String e, String f, String c){
        context=cn;
        nombre=n;
        apellidos1=a1;
        apellidos2=a2;
        tema=t;
        tutor1=t1;
        tutor2=t2;
        estado=e;
        fecha=f;
        calificacion=c;
    }
    @Override
    protected String doInBackground(Void... params) {
        URL url = null;
        try {

            //Se indica la url de nuestro servicio
            url = new URL("http://"+IP+":8080/WebRestServer/TFG/addEstudiante/");
        } catch (Exception exception ) {
            exception.printStackTrace();
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.connect();

            //Crear objeto JSON
            JSONObject json=new JSONObject();
            json.put("nombre",nombre);
            json.put("apellido1",apellidos1);
            json.put("apellido2",apellidos2);
            json.put("tema",tema);
            json.put("tutor1",tutor1);
            json.put("tutor2",tutor2);
            json.put("estado",estado);
            json.put("fechaPresentacion",fecha);
            json.put("calificacion", calificacion);

            //relacionamos el Json con el flujo de datos
            String message = json.toString();
            OutputStream os = new BufferedOutputStream(httpURLConnection.getOutputStream());
            os.write(message.getBytes());
            //limpiar el flujo
            os.flush();
            //Se guarda la respuesta del servidor
            code=httpURLConnection.getResponseCode();

        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    protected void onPostExecute(String results) {

        //si el codigo es 200 significa que la operación se ha realizado con éxito
        if (code!=204) {
            Toast.makeText(context, "¡Error!",
                    Toast.LENGTH_SHORT).show();

        } else {

            //Notificacion
            NotificationCompat.Builder notificacion =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_stat_name2))
                            .setContentTitle("Creado")
                            .setContentText("Estudiante correctamente creado")
                            .setContentInfo("4")
                            .setTicker("Alerta!");

            //sonido de la notificacion
            notificacion.setSound(RingtoneManager.getDefaultUri
                    (RingtoneManager.TYPE_NOTIFICATION));

            //cerrar la notificación al pulsarla
            notificacion.setAutoCancel (true);

            //Al pulsarla nos vamos a la actividad Todos
            Intent notIntent = new Intent(context, TodosActivity.class);
            PendingIntent contIntent =
                    PendingIntent.getActivity(context,0, notIntent,
                            0);
            notificacion.setContentIntent(contIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIF_ALERTA_ID, notificacion.build());

        }

    }
}