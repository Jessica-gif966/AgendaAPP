package com.example.agendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendaapp.classesDB.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//declaracion de objetos locales para enlazar con los controladores
    private EditText etNombre, etTelefono, etDireccion;
    private Button btnGuardar, btnConsultar;
    private  ListView myListView;
    private List<String> users=new ArrayList<>();
    private ArrayAdapter<String> myAdapter;
    //crear dos objetos de la base de datos
    SQLiteDatabase db=null;
    SQLiteHelper sqlh=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //enlaces
        etNombre=(EditText) findViewById(R.id.etNombre);
        etTelefono=(EditText) findViewById(R.id.etTelefono);
        etDireccion=(EditText) findViewById(R.id.etDireccion);
        btnGuardar=(Button) findViewById(R.id.btnGuardar);
        btnConsultar=(Button) findViewById(R.id.btnConsultar);
        myListView=(ListView) findViewById(R.id.myListView);
        //oyentes de los botones
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarUsuario();
            }
        });
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsultarUsuarios();
            }
        });
        //creacion o uso de la bd
        sqlh=new SQLiteHelper(getApplicationContext(),"agenda1.db",null,1);
    }
    public void GuardarUsuario() {
        //obtener los valores de las cajas de texto
        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();
        String direccion = etDireccion.getText().toString();

        //validacion de campos vacios
        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Llena todos los campos", Toast.LENGTH_SHORT).show();
        } else{

        //crear el objeto db
        //permisos de escritura y consulta en la base de datos
        db=sqlh.getWritableDatabase();
        //creamos una instancia a la clase usuario para hacer operaciones dml
        Usuario u=new Usuario(getApplicationContext(),db);
        //llamar el metodo
        boolean res=u.Nuevo(nombre,direccion,Integer.parseInt(telefono));
        if(res==true){
            Toast.makeText(getApplicationContext(), "Usuario guardado", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
            db.close();
        }}
    }
    public void ConsultarUsuarios(){
        db=sqlh.getReadableDatabase();
        //instancia de la clase usuario
        Usuario u=new Usuario(getApplicationContext(),db);
        Cursor cursor1= u.Consulta();
        //declaramos variables temporales
        //para que cada recorrido tengamos los valores de mi tabla temporal en esas variables y podamos imprimir
        String nombre, direccion;
        int idU,telefono;
        //recorremos cursor
        int filas=cursor1.getCount();
        cursor1.moveToFirst();
        users.clear();
        for(int i=0; i<filas;i++){
            idU=cursor1.getInt(0);
            nombre=cursor1.getString(1);
            telefono=cursor1.getInt(2);
            direccion=cursor1.getString(3);
            users.add(idU+","+nombre+","+telefono+","+direccion+"");
            cursor1.moveToNext();

        }

        db.close();
        myAdapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
        myListView.setAdapter(myAdapter);
    }
}