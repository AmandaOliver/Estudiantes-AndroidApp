package uca.es.estudiantes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

        TareaTodosAsincrona Tarea = new TareaTodosAsincrona(getApplicationContext(), lista, adaptador);
        Tarea.execute();

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Según el botón del toolbar que pulsemos, iremos a una actividad u otra
        switch (item.getItemId()) {
            case R.id.Inicio:
                Intent intentInicio = new Intent(TodosActivity.this, MainActivity.class);
                startActivity(intentInicio);
                return true;
            case R.id.Búsqueda:
                Intent intentBusqueda = new Intent(TodosActivity.this, BusquedaActivity.class);
                startActivity(intentBusqueda);
                return true;
            case R.id.Nuevo:
                Intent intentNuevo = new Intent(TodosActivity.this, NuevoActivity.class);
                startActivity(intentNuevo);
                return true;
            case R.id.Borrar:
                Intent intentBorrar = new Intent(TodosActivity.this, BorrarActivity.class);
                startActivity(intentBorrar);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}





