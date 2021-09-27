package com.example.agendaapp.classesDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(Context context1, String nameDB, SQLiteDatabase.CursorFactory factory1, int version){
        super(context1, nameDB, factory1,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL, ejecuta cualquier comando sql, dml o ddl
        db.execSQL("CREATE TABLE usuario(idU INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, telefono INTEGER, direccion TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE usuario");
        onCreate(db);
    }
}
