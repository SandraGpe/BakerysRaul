package com.example.bakerysraul

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class adminbd(context: Context): SQLiteOpenHelper(context,DataBase,null,1) {
    companion object{
        val DataBase = "bakery"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //crear tablas
        db?.execSQL("create table usuario (correoUsr text primary key,nomusr text,contrasena text)")
        //crear otra tabla, lo mismo de arriba db?
        db?.execSQL("create table pan(codigo INTEGER PRIMARY KEY AUTOINCREMENT, nomPan text,precio double,cantidad double)")
        //BD ventas tabla emepleado, ejemplo para graficar
        db?.execSQL("Create Table EmpleadoCalif(_id int primary key, NomEmp text, Calif float)")
        //Tabla para el Perfil del Usuario/Cliente
        db?.execSQL("Create Table perfil(_idCliente int primary key, nomCliente text, apellidoCliente text,RFC text, comunidad text,colonia text,calle text,cp int, tel int)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    //Función para mandar ejecutar un Insert,update o delete
    fun Ejecuta(sentencia: String) : Int{
        try {
            val db = this.writableDatabase
            db.execSQL(sentencia)
            return 1
        }
        catch (ex: Exception){
            return 0 //Terminación no exitosa
        }
    }

    //funcion para mandar ejecutar una consulta SQL (Select)
    fun Consulta(query: String): Cursor?{
        try {
            val db = this.readableDatabase
            return db.rawQuery(query,null)

        }
        catch (ex:java.lang.Exception) {
            return null
        }
    }
}