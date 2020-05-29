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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val IP = "http://192.168.1.77" // Dirección IP del servidor web que almacena los servicios web
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    //checar este codigo

    fun empleados(v:View){
        val wsURL = IP + "/WSBakery/bdLogin.php"
        val admin = adminbd(this)
        admin.Ejecuta("DELETE FROM usuario")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,wsURL,null,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                val sensadoJson = response.getJSONArray("empleados")
                for (i in 0 until sensadoJson.length()){
                    // Los nombres del getString son como los arroja el servicio web
                    val id = sensadoJson.getJSONObject(i).getString("idEmp")
                    val contra = sensadoJson.getJSONObject(i).getString("contrasena")
                    val sentencia = "INSERT INTO usuario(idEmp,contrasena) values(${id},'${contra})"
                    val res = admin.Ejecuta(sentencia)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error Empleados: " + error.message.toString() , Toast.LENGTH_LONG).show();
                Log.d("GonzalezSandra",error.message.toString() )
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun btnLogin(view: View){
        if (txtidEmp.text.isEmpty() || txtContra.text.isEmpty()){
            txtidEmp.setError("Error, faltan datos de entrada")
            txtidEmp.requestFocus()
        } else{
            var id = txtidEmp.text.toString()
            var contra = txtContra.text.toString()
            var admin = adminbd(this)
            var query = "Select * from usuario where idEmp = '$id' and contrasena='$contra'"
            val resul = admin.Consulta(query)
            if (resul!!.moveToFirst()){
                id = resul.getString(1)
                contra = resul.getString(3)
                val acti = Intent(this, PrincipalMenu::class.java)
                acti.putExtra(PrincipalMenu.EXTRA_ID,id)
                acti.putExtra(PrincipalMenu.EXTRA_CONTRA,contra)
                startActivity(acti)
            } else{
                Toast.makeText(this, "ID o contraseña invalido", Toast.LENGTH_LONG).show();
                txtidEmp.requestFocus()
            }
        }
    }

    fun btnRegistrar(v: View){
        val acti : Intent = Intent(this,MainRegistro::class.java)
        startActivity(acti)
    }


}
