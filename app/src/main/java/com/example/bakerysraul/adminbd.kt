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
        db?.execSQL("create table usuario (correoUsr text primary key, idEmp int, nomusr text,contrasena text)")
        //crear otra tabla, lo mismo de arriba db?
        db?.execSQL("create table pan(codigo INTEGER PRIMARY KEY AUTOINCREMENT, nomPan text,precio double,cantidad double)")
        //BD ventas tabla emepleado, ejemplo para graficar
        db?.execSQL("Create Table EmpleadoCalif(_id int primary key, NomEmp text, Calif float)")
        //Tabla para el Perfil del Usuario/Cliente
        db?.execSQL("Create Table perfil(idCliente int primary key autoincrement, nomCliente text, apellidoCliente text,RFC text, comunidad text,colonia text,calle text,cp int, tel int)")
    }

    // Permite ejecutar Insert, Update o Delete
    fun Ejecuta(sentencia: String): Boolean{
        try {
            val bd = this.writableDatabase
            bd.execSQL(sentencia)
            bd.close()
            return true
        }
        catch (ex:Exception){
            return false
        }
    }

    // Permir ejecutar una consulta
    fun Consulta(query: String): Cursor?{
        try {
            val bd = this.readableDatabase
            return bd.rawQuery(query,null)
        }
        catch (ex:Exception){
            return null
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}