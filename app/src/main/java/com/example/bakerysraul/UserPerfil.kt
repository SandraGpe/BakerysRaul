package com.example.bakerysraul

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_user_perfil.*
import org.json.JSONObject

class UserPerfil : AppCompatActivity() {
    val IP = "http://192.168.1.77" // Dirección IP del servidor web que almacena los servicios web
    val idCliente = 0
    var bandera : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_perfil)
    }

    /*
    BOTONES EN ORDEN
    1.BuscarXid ♥
    2.getallClientes ♥
    3.Consultar
    4.Actualizar ♥
    5.Limpiar ♥
    6.Agregar ♥
    7.Borrar ♥
    */

    //SI FUNCIONA
    fun Buscar(view: View){
        if (txtIdU.text.toString().isEmpty()){
            txtIdU.setError("Falta Clave del Cliente")
            Toast.makeText(this, "Falta Informacion del ID", Toast.LENGTH_LONG).show();
            txtIdU.requestFocus()
        }
        else{
            val wsURL = IP + "/WSBakery/getCliente.php"
            val id = txtIdU.text.toString()
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idCliente",id)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, wsURL,jsonEntrada,
                Response.Listener { response ->
                    val succ = response["success"]
                    val msg = response["message"]
                    if (succ == 200){
                        val sensadoJson = response.getJSONArray("perfil")
                        // Los nombres del getString son como los arroja el servicio web
                        val id = sensadoJson.getJSONObject(0).getString("idCliente")
                        val nom = sensadoJson.getJSONObject(0).getString("nomCliente")
                        val ape = sensadoJson.getJSONObject(0).getString("apellidoCliente")
                        val rfc = sensadoJson.getJSONObject(0).getString("RFC")
                        val com = sensadoJson.getJSONObject(0).getString("comunidad")
                        val col = sensadoJson.getJSONObject(0).getString("colonia")
                        val calle = sensadoJson.getJSONObject(0).getString("calle")
                        val cp = sensadoJson.getJSONObject(0).getString("cp")
                        val tel = sensadoJson.getJSONObject(0).getString("tel")

                        txtIdU.setText(id)
                        idPedido.setText(nom)
                        txtApellido.setText(ape)
                        txtRFC.setText(rfc)
                        txtComunidad.setText(com)
                        txtColonia.setText(col)
                        txtCalle.setText(calle)
                        txtCP.setText(cp)
                        txtTel.setText(tel)

                    }
                    Toast.makeText(this, "Success:${succ}  Message:${msg} Servidor Web Modificado",
                        Toast.LENGTH_SHORT).show();
                },
                Response.ErrorListener{error ->
                    Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show();
                    Log.d("ERROR","${error.message}");
                    Toast.makeText(this, "Error de capa 8 checa URL", Toast.LENGTH_SHORT).show();
                }
            )
            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }
    }

    //SI FUNCIONA
    // Este boton elimina los productos de la base de datos local y ejecuta el servicio web getProductos.php
    //Que consulta todos los producto del servidor web, para insertarlos en la base de datos local
    fun getAllClientes(view: View) {
        val wsURL = IP + "/WSBakery/getClientes.php"
        val admin = adminbd(this)
        admin.Ejecuta("DELETE FROM perfil")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,wsURL,null,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                val sensadoJson = response.getJSONArray("clientes")
                for (i in 0 until sensadoJson.length()){
                    // Los nombres del getString son como los arroja el servicio web
                    val idcliente = sensadoJson.getJSONObject(i).getString("idCliente")
                    val nomcliente = sensadoJson.getJSONObject(i).getString("nomCliente")
                    val apellidoCliente = sensadoJson.getJSONObject(i).getString("apellidoCliente")
                    val rfc = sensadoJson.getJSONObject(i).getString("RFC")
                    val comunidad = sensadoJson.getJSONObject(i).getString("comunidad")
                    val colonia= sensadoJson.getJSONObject(i).getString("colonia")
                    val calle = sensadoJson.getJSONObject(i).getString("calle")
                    val cp= sensadoJson.getJSONObject(i).getString("cp")
                    val tel = sensadoJson.getJSONObject(i).getString("tel")
                    val sentencia = "Insert into perfil(idCliente,nomCliente,apellidoCliente,RFC,comunidad,colonia,calle,cp,tel) values(${idcliente},'${nomcliente}','${apellidoCliente}','${rfc}','${comunidad}','${colonia}','${calle}',${cp},${tel})"
                    val res = admin.Ejecuta(sentencia)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error getAllClientes: " + error.message.toString() , Toast.LENGTH_LONG).show();
                Log.d("GonzalezSandra",error.message.toString() )
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    // Esta consulta se hace en la base de datos local, partiendo de que la información ya se cargo
    // ejecutando el boton de Cargar Clientes del servidor web
    /*fun Consultar(view: View) {
        if (txtIdU.text.toString().isEmpty()){
            txtIdU.setError("Falta ingresar clave del producto")
            Toast.makeText(this, "Falta información del ID", Toast.LENGTH_SHORT).show();
            txtIdU.requestFocus()
        }
        else{
            val admin = adminbd(this)
            val id :String = txtIdU.text.toString()
            //                                                    0         1         2           3       4       5      6    7  8
            val cur = admin.Consulta("select idCliente,nomCliente,apellidoCliente,RFC,comunidad,colonia,calle,cp,tel from perfil where idCliente=$id")
            if (cur!!.moveToFirst()){
                txtCliente.setText(cur.getString(1))
                txtApellido.setText(cur.getString(2))
                txtRFC.setText(cur.getString(3))
                txtComunidad.setText(cur.getString(4))
                txtColonia.setText(cur.getString(5))
                txtCalle.setText(cur.getString(6))
                txtCP.setText(cur.getString(7))
                txtTel.setText(cur.getString(8))
            }
            else
            {
                Toast.makeText(this, "No existe ID del Cliente", Toast.LENGTH_LONG).show();
                txtIdU.requestFocus()
            }
        }
    }*/

    //SI FUNCIONA
    fun Actualizar(view: View) {
        if (txtIdU.text.toString().isEmpty() ||
            idPedido.text.toString().isEmpty() ||
            txtApellido.text.toString().isEmpty() ||
            txtComunidad.text.toString().isEmpty() ||
            txtCalle.text.toString().isEmpty() ||
            txtColonia.text.toString().isEmpty() ||
            txtCP.text.toString().isEmpty() ||
            txtRFC.text.toString().isEmpty() ||
            txtTel.text.toString().isEmpty()){
            txtIdU.setError("Falta información de Ingresar")
            txtIdU.requestFocus()
        }
        else
        {
            val id = txtIdU.text.toString()
            val nom = idPedido.text.toString()
            val ape = txtApellido.text.toString()
            val rfc = txtRFC.text.toString()
            val com = txtComunidad.text.toString()
            val col = txtColonia.text.toString()
            val calle = txtCalle.text.toString()
            val cp = txtCP.text.toString()
            val tel = txtTel.text.toString()
            val admin = adminbd(this)
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idCliente", txtIdU.text.toString())
            jsonEntrada.put("nomCliente", idPedido.text.toString())
            jsonEntrada.put("apellidoCliente", txtApellido.text.toString())
            jsonEntrada.put("RFC",txtRFC.text.toString())
            jsonEntrada.put("comunidad",txtComunidad.text.toString())
            jsonEntrada.put("colonia",txtColonia.text.toString())
            jsonEntrada.put("calle",txtCalle.text.toString())
            jsonEntrada.put("cp",txtCP.text.toString())
            jsonEntrada.put("tel",txtTel.text.toString())
            txtIdU.setText("")
            idPedido.setText("")
            txtApellido.setText("")
            txtRFC.setText("")
            txtComunidad.setText("")
            txtColonia.setText("")
            txtCalle.setText("")
            txtCP.setText("")
            txtTel.setText("")
            txtIdU.requestFocus()
            sendRequest(IP + "/WSBakery/updateCliente.php",jsonEntrada)

        }
    }

    //SI FUNCIONA
    fun Limpiar(v:View){
        txtIdU.setText("")
        idPedido.setText("")
        txtApellido.setText("")
        txtRFC.setText("")
        txtComunidad.setText("")
        txtColonia.setText("")
        txtCalle.setText("")
        txtCP.setText("")
        txtTel.setText("")
    }

    //SI FUNCIONA
    fun Agregar(v: View){
        if (idPedido.text.toString().isEmpty() ||
            txtApellido.text.toString().isEmpty() ||
            txtComunidad.text.toString().isEmpty() ||
            txtCalle.text.toString().isEmpty() ||
            txtColonia.text.toString().isEmpty() ||
            txtRFC.text.toString().isEmpty() ||
            txtTel.text.toString().isEmpty()){
            txtIdU.setError("Falta información de Ingresar")
            txtIdU.requestFocus()
        } else {
            val nom = idPedido.text.toString()
            val ape = txtApellido.text.toString()
            val rfc = txtRFC.text.toString()
            val com = txtComunidad.text.toString()
            val col = txtColonia.text.toString()
            val calle = txtCalle.text.toString()
            val cp = txtCP.text.toString()
            val tel = txtTel.text.toString()
            val admin = adminbd(this)
            var jsonEntrada = JSONObject()
            jsonEntrada.put("nomCliente", idPedido.text.toString())
            jsonEntrada.put("apellidoCliente", txtApellido.text.toString())
            jsonEntrada.put("RFC",txtRFC.text.toString())
            jsonEntrada.put("comunidad",txtComunidad.text.toString())
            jsonEntrada.put("colonia",txtColonia.text.toString())
            jsonEntrada.put("calle",txtCalle.text.toString())
            jsonEntrada.put("cp",txtCP.text.toString())
            jsonEntrada.put("tel",txtTel.text.toString())
            sendRequest(IP + "/WSBakery/agregarperfil.php",jsonEntrada)
        }
    }

    //SI FUNCIONA
    fun Borrar(view: View) {
        if (txtIdU.text.toString().isEmpty()){
            Toast.makeText(this, "Falta Clave del Cliente",
                Toast.LENGTH_LONG).show();
            txtIdU.requestFocus()
        } else {
            val idCliente = txtIdU.text.toString()
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idCliente", txtIdU.text.toString())
            txtIdU.setText("")
            idPedido.setText("")
            txtApellido.setText("")
            txtRFC.setText("")
            txtComunidad.setText("")
            txtColonia.setText("")
            txtCalle.setText("")
            txtCP.setText("")
            txtTel.setText("")
            txtIdU.requestFocus()
            sendRequest(IP + "/WSBakery/deleteCliente.php",jsonEntrada)
        }
    }
    //Rutina para mandar ejecutar un web service de tipo Insert, Update o Delete
    fun sendRequest( wsURL: String, jsonEnt: JSONObject){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL,jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200){
                    idPedido.setText("")
                    txtApellido.setText("")
                    txtRFC.setText("")
                    txtComunidad.setText("")
                    txtColonia.setText("")
                    txtCP.setText("")
                    txtCalle.setText("")
                    txtTel.setText("")
                    idPedido.requestFocus()
                    Toast.makeText(this, "Success:${succ}  Message:${msg} Servidor Web Modificado", Toast.LENGTH_SHORT).show();
                }
            },
            Response.ErrorListener{ error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show();
                Log.d("ERROR","${error.message}");
                Toast.makeText(this, "Error de capa 8 checa URL", Toast.LENGTH_SHORT).show();
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun lista(v:View){
        val lista = Intent(this, RecyclerView::class.java)
        startActivity(lista)
    }

}
