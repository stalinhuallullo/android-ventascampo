package dev.lstr.llevateclaro.presentation.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import kotlinx.android.synthetic.main.activity_message_dialog.*

class MessageDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_dialog)
        init()
        TrackerGA.getInstance(this).registerScreen("Registro.Usuario.Success")
    }

    fun init(){
        tv_title.text = "Muchas gracias por registrarse"

        btn_aceptar.setOnClickListener {
            goToHome()
        }

        val message = intent.getStringExtra("message")
        tv_description.text = message
    }

    fun goToHome(){
        finish()
    }
}