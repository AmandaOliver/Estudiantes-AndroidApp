package uca.es.estudiantes;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
//Clase principal para mostrar los estudiantes
public class TodosActivity extends AppCompatActivity {
    //Elementos del layout que vamos a usar
    private ListView lista;
    private ArrayAdapter<String> adaptador;// Adaptador para mostrar los elementos de la lista
    private Button botonPresentados;
    private Button botonEnDesarrollo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        //se instancian los elementos del layout que vamos a utilizar
        lista = (ListView) findViewById(R.id.listaEstudiantes);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        botonPresentados = (Button) findViewById(R.id.botonPresentados);
        botonEnDesarrollo=(Button)findViewById(R.id.botonEnDesarrollo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       //funcionalidad del boton que muestra los alumnos que ya han presentado el TFG
        botonPresentados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //llamada a la tarea asincrona que mostrará los alumnos, se le pasa la lista y su adaptador
                TareaTodosPresentadosAsincrona Tarea=  new TareaTodosPresentadosAsincrona(getApplicationContext(), lista, adaptador);
                Tarea.execute();
            }
        });

        //funcionalidad del boton que muestra los alumnos que aun están desarrollando el TFG
        botonEnDesarrollo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //llamada a la tarea asincrona que mostrará los alumnos, se le pasa la lista y su adaptador
                TareaTodosEnDesarrolloAsincrona Tarea = new TareaTodosEnDesarrolloAsincrona(getApplicationContext(), lista, adaptador);
                Tarea.execute();
            }
        });
    }
}





