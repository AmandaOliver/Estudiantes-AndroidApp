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

//Clase principal para controlar las funciones de inserción de estudiantes
public class NuevoActivity extends AppCompatActivity {
    //elementos del layout que vamos a usar
    private EditText nombre;
    private EditText apellidos1;
    private EditText apellidos2;
    private EditText tema;
    private EditText tutor1;
    private EditText tutor2;
    private EditText estado;
    private EditText fecha;
    private EditText calificacion;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        //se instancian los elementos del layout que vamos a utilizar
        nombre=(EditText) findViewById(R.id.editNombre);
        apellidos1=(EditText) findViewById(R.id.editApellido1);
        apellidos2=(EditText) findViewById(R.id.editApellido2);
        tema=(EditText) findViewById(R.id.editTema);
        tutor1=(EditText) findViewById(R.id.editTutor1);
        tutor2=(EditText) findViewById(R.id.editTutor2);
        estado=(EditText) findViewById(R.id.editEstado);
        fecha=(EditText) findViewById(R.id.editFecha);
        calificacion=(EditText) findViewById(R.id.editCalificacion);
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

        //funcionalidad del boton "crear"
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //codigo para que el teclado se cierre una vez que se introducen los apellidos
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                //llamada a la tarea asincrona que llamará al servicio web,
                //se le pasa el contexto y el texto que hemos escrito en el campo de búsqueda
                TareaCrearAsincrona Tarea = new TareaCrearAsincrona(getApplicationContext(),
                        nombre.getText().toString(),
                        apellidos1.getText().toString(),
                        apellidos2.getText().toString(),
                        tema.getText().toString(),
                        tutor1.getText().toString(),
                        tutor2.getText().toString(),
                        estado.getText().toString(),
                        fecha.getText().toString(),
                        calificacion.getText().toString());
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
                Intent intentInicio = new Intent(NuevoActivity.this, MainActivity.class);
                startActivity(intentInicio);
                return true;
            case R.id.Todos:
                Intent intentTodos = new Intent(NuevoActivity.this, TodosActivity.class);
                startActivity(intentTodos);
                return true;
            case R.id.Búsqueda:
                Intent intentBusqueda = new Intent(NuevoActivity.this, BusquedaActivity.class);
                startActivity(intentBusqueda);
                return true;
            case R.id.Borrar:
                Intent intentBorrar = new Intent(NuevoActivity.this, BorrarActivity.class);
                startActivity(intentBorrar);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
