package com.example.bakerysraul

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun btnLogin(view: View){
        if (txtCorr.text.isEmpty() || txtContra.text.isEmpty()){
            txtCorr.setError("Error, faltan datos de entrada")
            txtCorr.requestFocus()
        } else{
            var corr = txtCorr.text.toString()
            var contra = txtContra.text.toString()
            var admin = adminbd(this)
            var query = "Select * from usuarios where correoUsr = '$corr' and contrasena='$contra'"
            val resul = admin.Consulta(query)
            if (resul!!.moveToFirst()){
                corr = resul.getString(0)
                contra = resul.getString(2)
                val acti = Intent(this, PrincipalMenu::class.java)
                acti.putExtra(PrincipalMenu.EXTRA_CORR,corr)
                acti.putExtra(PrincipalMenu.EXTRA_CONTRA,contra)
                startActivity(acti)
            } else{
                Toast.makeText(this, "Correo o contraseña invalido", Toast.LENGTH_LONG).show();
                txtCorr.requestFocus()
            }
        }
    }

    fun btnRegistrar(v: View){
        val acti : Intent = Intent(this,MainRegistro::class.java)
        startActivity(acti)
    }
}
