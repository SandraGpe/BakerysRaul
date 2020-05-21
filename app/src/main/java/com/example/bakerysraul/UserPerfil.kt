package com.example.bakerysraul

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
    val idcliente = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_perfil)
    }

    //SI FUNCIONA
    fun Agregar(v: View){
        if (txtCliente.text.toString().isEmpty() ||
            txtApellido.text.toString().isEmpty() ||
            txtComunidad.text.toString().isEmpty() ||
            txtCalle.text.toString().isEmpty() ||
            txtColonia.text.toString().isEmpty() ||
            txtRFC.text.toString().isEmpty() ||
            txtTel.text.toString().isEmpty()){
            txtIdU.setError("Falta información de Ingresar")
            txtIdU.requestFocus()
        } else {
            val nom = txtCliente.text.toString()
            val ape = txtApellido.text.toString()
            val rfc = txtRFC.text.toString()
            val com = txtComunidad.text.toString()
            val col = txtColonia.text.toString()
            val calle = txtCalle.text.toString()
            val cp = txtCP.text.toString()
            val tel = txtTel.text.toString()
            val admin = adminbd(this)
            var jsonEntrada = JSONObject()
            jsonEntrada.put("nomCliente", txtCliente.text.toString())
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

    fun btnBuscar(view: View){
        if (txtIdU.text.toString().isEmpty()){
            txtIdU.setError("Falta Clave del Cliente")
            Toast.makeText(this, "Falta Informacion del ID", Toast.LENGTH_LONG).show();
            txtIdU.requestFocus()
        }
        else{
            val wsURL = IP + "/WSBakery/getCliente.php"
            val idcliente = txtIdU.text.toString()
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idCliente",idcliente)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, wsURL,jsonEntrada,
                Response.Listener { response ->
                    val succ = response["success"]
                    val msg = response["message"]
                    if (succ == 200){
                        val sensadoJson = response.getJSONArray("perfil")
                        // Los nombres del getString son como los arroja el servicio web
                        val idc = sensadoJson.getJSONObject(0).getString("idCliente")
                        val nom = sensadoJson.getJSONObject(0).getString("nomCliente")
                        val ape = sensadoJson.getJSONObject(0).getString("apellidocliente")
                        val rfc = sensadoJson.getJSONObject(0).getString("RFC")
                        val com = sensadoJson.getJSONObject(0).getString("comunidad")
                        val col = sensadoJson.getJSONObject(0).getString("colonia")
                        val calle = sensadoJson.getJSONObject(0).getString("calle")
                        val cp = sensadoJson.getJSONObject(0).getString("cp")
                        val tel = sensadoJson.getJSONObject(0).getString("tel")

                        txtIdU.setText(idc)
                        txtCliente.setText(nom)
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

    // Esta consulta se hace en la base de datos local, partiendo de que la información ya se cargo
    // ejecutando el boton de Carga Productos del servidor web
    fun Consultar(view: View) {
         if (txtIdU.text.toString().isEmpty()){
             txtIdU.setError("Falta ingresar clave del producto")
             Toast.makeText(this, "Falta información del id", Toast.LENGTH_SHORT).show();
             txtIdU.requestFocus()
         }
         else{
             val admin = adminbd(this)
             val id:String = txtIdU.text.toString()
             //                                                    0         1         2           3       4       5      6    7  8
             val cur = admin.Consulta("SELECT idCliente,nomCliente,apellidoCliente,RFC,comunidad,colonia,calle,cp,tel FROM perfil WHERE idCliente=$idcliente")
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
     }

    fun Borrar(view: View) {
        if (txtIdU.text.toString().isEmpty()){
            Toast.makeText(this, "Falta Clave del Cliente",
                Toast.LENGTH_LONG).show();
            txtIdU.requestFocus()
        } else {
            val idcliente = txtIdU.text.toString()
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idCliente", txtIdU.text.toString())
            txtIdU.setText("")
            txtCliente.setText("")
            txtApellido.setText("")
            txtRFC.setText("")
            txtComunidad.setText("")
            txtColonia.setText("")
            txtCalle.setText("")
            txtCP.setText("")
            txtTel.setText("")
            txtIdU.requestFocus()
            sendRequest(IP + "/WSAndroid/deleteProducto.php",jsonEntrada)
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
                    txtCliente.setText("")
                    txtApellido.setText("")
                    txtRFC.setText("")
                    txtComunidad.setText("")
                    txtColonia.setText("")
                    txtCP.setText("")
                    txtCalle.setText("")
                    txtTel.setText("")
                    txtCliente.requestFocus()
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

}
