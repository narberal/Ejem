package com.eventosfia.gerardo.UI;

/**
 * Created by gerardo on 13/05/2016.
 */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eventosfia.gerardo.ejem.R;

import POJO.Eventos;

public class EventoUI extends ActionBarActivity {
    private static final int INCLUIR = 0;
    //private static final int ALTERAR = 1;
    Eventos lEvento;
    EditText txtNombre;
    EditText txtLugar;
    EditText txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contato );



        try
        {
            final Bundle data = (Bundle) getIntent().getExtras();
            int lint = data.getInt("tipo");
            if (lint == INCLUIR)
            {
                // Cuando se agrega un contacto para crear una nueva instancia
                lEvento = new Eventos();
            }else{
                // Cuando se cambia la carga de contacto de la clase que vino por Bundle
                lEvento = (Eventos) data.getSerializable("agenda");
            }

            // Creación de los objetos Actividad
            txtNombre = (EditText)findViewById(R.id.edtNombre);
            txtLugar = (EditText)findViewById(R.id.edtLugar);
            txtFecha = (EditText)findViewById(R.id.edtFecha);

            // Carga de objetos con los datos de contacto
            // Si una inclusión que vendrá cargada con los atributos de texto
            // Establecer el archivo main.xml
            txtNombre.setText(lEvento.getNombre());
            txtLugar.setText(lEvento.getLugar());
            txtFecha.setText(lEvento.getFecha());
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void btnConfirmar_click(View view)
    {
        try
        {
            // Cuando se confirme la inclusión o alteración debe devolver
            // El registro con los datos llenó la pantalla e informar
            // El RESULT_OK y luego terminar la actividad


            Intent data = new Intent();
            lEvento.setNombre(txtNombre.getText().toString());
            lEvento.setLugar(txtLugar.getText().toString());
            lEvento.setFecha(txtFecha.getText().toString());
            data.putExtra("agenda", lEvento);
            setResult(Activity.RESULT_OK, data);
            finish();
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void btnCancelar_click(View view)
    {
        try
        {

            // Cuando acaba de cancelar la inclusión de operación
            // O modificación sólo debe informar al RESULT_CANCELED
            // Y luego terminar Actividad

            setResult(Activity.RESULT_CANCELED);
            finish();
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void toast (String msg)
    {
        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
    }

    private void trace (String msg)
    {
        toast (msg);
    }
}