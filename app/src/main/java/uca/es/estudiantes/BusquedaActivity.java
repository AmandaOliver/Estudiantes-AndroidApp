package uca.es.estudiantes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//clase principal para realizar las funciones de búsqueda
public class BusquedaActivity extends AppCompatActivity {

    //elementos del layout que vamos a usar
    private EditText busca;
    private Button boton;
    private TextView texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        //se instancian los elementos del layout que vamos a utilizar
        busca=(EditText) findViewById(R.id.busca);
        boton=(Button) findViewById(R.id.boton);
        texto=(TextView) findViewById(R.id.texto);

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

        //funcionalidad del boton "buscar"
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //llamada a la tarea asincrona que llamará al servicio web,
                //se le pasa el contexto y el texto que hemos escrito en el campo de búsqueda
                TareaBusquedaAsincrona Tarea = new TareaBusquedaAsincrona(getApplicationContext(), texto, busca.getText().toString());
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
                Intent intentInicio = new Intent(BusquedaActivity.this, MainActivity.class);
                startActivity(intentInicio);
                return true;
            case R.id.Todos:
                Intent intentTodos = new Intent(BusquedaActivity.this, TodosActivity.class);
                startActivity(intentTodos);
                return true;
            case R.id.Nuevo:
                Intent intentNuevo = new Intent(BusquedaActivity.this, NuevoActivity.class);
                startActivity(intentNuevo);
                return true;
            case R.id.Borrar:
                Intent intentBorrar = new Intent(BusquedaActivity.this, BorrarActivity.class);
                startActivity(intentBorrar);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
