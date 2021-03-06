package uca.es.estudiantes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            case R.id.Todos:
                Intent intentTodos = new Intent(MainActivity.this, TodosActivity.class);
                startActivity(intentTodos);
                return true;
            case R.id.Búsqueda:
                Intent intentBusqueda = new Intent(MainActivity.this, BusquedaActivity.class);
                startActivity(intentBusqueda);
                return true;
            case R.id.Nuevo:
                Intent intentNuevo = new Intent(MainActivity.this, NuevoActivity.class);
                startActivity(intentNuevo);
                return true;
            case R.id.Borrar:
                Intent intentBorrar = new Intent(MainActivity.this, BorrarActivity.class);
                startActivity(intentBorrar);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
