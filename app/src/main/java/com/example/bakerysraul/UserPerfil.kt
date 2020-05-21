package com.example.bakerysraul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_user_perfil.*

class UserPerfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_perfil)
    }
    fun Agregar(v: View){
        if (txtNom.text.toString().isEmpty() || txtCorr.text.toString().isEmpty() ||
            txtApellido.text.toString().isEmpty() || txtComunidad.text.toString().isEmpty() || txtCalle.text.toString().isEmpty() ||
            txtColonia.text.toString().isEmpty() || txtCP.text.toString().isEmpty() || txtRFC.text.toString().isEmpty() ||
            txtTel.text.toString().isEmpty()
            ){
            Toast.makeText(this, "Falta información por capturar",
                Toast.LENGTH_LONG).show();
            txtNom.requestFocus()
        }
        else
        {
            val nom = txtNom.text.toString()
            val ape = txtApellido.text.toString()
            val corr = txtCorr.text.toString()
            val rfc = txtRFC.text.toString()
            val com = txtComunidad.text.toString()
            val col = txtColonia.text.toString()
            val calle = txtCalle.text.toString()
            val cp = txtCP.text.toString()
            val tel = txtTel.text.toString()
            val admin = adminbd(this)
            val sentencia = "Insert into perfil(nomusr,apellidousr,correoUsr,RFC,comunidad,colonia,cp,calle,tel) values" +
                    "('$nom','$ape','$corr'.'$rfc','$com','$col',$cp,'$calle',$tel)"
            if (admin.Ejecuta(sentencia)==1){
                Toast.makeText(this, "Datos Agregados", Toast.LENGTH_SHORT).show();
                txtNom.setText("")
                txtApellido.setText("")
                txtCorr.setText("")
                txtRFC.setText("")
                txtComunidad.setText("")
                txtColonia.setText("")
                txtCP.setText("")
                txtCalle.setText("")
                txtTel.setText("")
                txtNom.requestFocus()
            }
            else{
                Toast.makeText(this, "Error al Agregar Datos", Toast.LENGTH_SHORT).show();
                txtNom.requestFocus()
            }
        }
    }
}
