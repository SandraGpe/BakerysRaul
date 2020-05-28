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
import kotlinx.android.synthetic.main.activity_calif_empresa.*
import org.json.JSONObject

class CalifEmpresa : AppCompatActivity() {
    val IP = "http://192.168.0.7"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calif_empresa)
    }
    fun graficaClic(v:View){
        val intGraf = Intent(this, CalifGrafica::class.java)
        startActivity(intGraf)
    }

    fun guardarClic(v: View){
        if (etIDC.text.isEmpty() || etIDE.text.isEmpty() || etCalif.text.isEmpty()){
            etIDE.setError("Falta información de ingresar")
            etIDE.requestFocus()
        }
        else
        {
            val admin = adminbd(this)
            val cli = etIDC.text.toString()
            val emp = etIDE.text.toString()
            val cal = etCalif.text.toString().toFloat()
            val sentencia = "Insert into calificar(idCli,idEmpleado,calif) values " +
                    "(${cli},${emp},${cal})"
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idCliente", etIDC.text.toString())
            jsonEntrada.put("idEmpleado", etIDE.text.toString())
            jsonEntrada.put("calif", etCalif.text.toString().toFloat())
            sendRequest(IP + "/WSBakery/saveCalif.php",jsonEntrada)
            if (admin.Ejecuta(sentencia)==1){
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();

                etCalif.setText("0")
                etIDE.setText("")
                etIDC.setText("")
                etIDE.requestFocus()
            }
            else
            {
                Toast.makeText(this, "Error de capa 8 No Guardo", Toast.LENGTH_SHORT).show();
            }

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
                    etIDC.setText("")
                    etIDE.setText("")
                    etCalif.setText("")
                    etIDE.requestFocus()
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
