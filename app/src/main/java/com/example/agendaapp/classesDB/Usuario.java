package com.example.agendaapp.classesDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class Usuario {
    //creamos el objeto
    SQLiteDatabase db=null;
    Context context1=null;
    String tablename="usuario";

    public Usuario(Context context1, SQLiteDatabase db){
        //inicializar los objetos locales con los objetos que recibiremos como parametros
        this.context1=context1;
        this.db=db;
    }

    //metodos para agregar y consultar

    public boolean Nuevo(String nombre, String direccion, int telefono){
        //preparamos el contentvalues
        ContentValues valores=new ContentValues();
        valores.put("nombre",nombre);
        valores.put("telefono",telefono);
        valores.put("direccion",direccion);

        //try-catch
        try{
            db.insert(tablename,null,valores);
            return true;
        }catch (Exception e){
            Toast.makeText(context1, "Algo ha fallado, Intenta de Nuevo", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public Cursor Consulta(){
        //un cursor es una tabla temporal que se crea en tiempo de ejecucion, de los datos que se estan consultando
        //siempre una consulta nos debe devolver un cursor
        Cursor cursor1=db.query(tablename,null,null,null,null,null,null);
        //retornar el cursor
        return cursor1;

    }


}
