package uca.es.estudiantes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//Clase principal para controlar las funciones de borrado
public class BorrarActivity extends AppCompatActivity {

    //Elementos del layout que vamos a usar
    private EditText busca;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);

        //se instancian los elementos del layout que vamos a utilizar
        busca=(EditText) findViewById(R.id.busca);
        boton=(Button) findViewById(R.id.boton);

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

        //funcionalidad del boton "borrar"
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //codigo para que el teclado se cierre una vez que se introducen los apellidos
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                //llamada a la tarea asincrona que llamará al servicio web,
                // se le pasa el contexto, el texto que hemos escrito en el campo y la vista
                TareaBorrarAsincrona Tarea = new TareaBorrarAsincrona(getApplicationContext(), busca.getText().toString(),view);
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
                Intent intentInicio = new Intent(BorrarActivity.this, MainActivity.class);
                startActivity(intentInicio);
                return true;
            case R.id.Todos:
                Intent intentTodos = new Intent(BorrarActivity.this, TodosActivity.class);
                startActivity(intentTodos);
                return true;
            case R.id.Búsqueda:
                Intent intentBusqueda = new Intent(BorrarActivity.this, BusquedaActivity.class);
                startActivity(intentBusqueda);
                return true;
            case R.id.Nuevo:
                Intent intentNuevo = new Intent(BorrarActivity.this, NuevoActivity.class);
                startActivity(intentNuevo);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
