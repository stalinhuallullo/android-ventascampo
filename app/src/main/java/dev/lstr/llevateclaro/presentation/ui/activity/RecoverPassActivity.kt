package dev.lstr.llevateclaro.presentation.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.presentation.presenter.view_model.RecoverPassViewModel
import dev.lstr.llevateclaro.presentation.ui.dialog.MessageDialog
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import kotlinx.android.synthetic.main.activity_recover_pass.*

class RecoverPassActivity : BaseActivity() {

    private lateinit var recoverPassViewModel: RecoverPassViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_pass)
        init()
        TrackerGA.getInstance(this).registerScreen("Usuario.RecoverPassword")
    }

    fun init(){
        btn_enviar.setOnClickListener {
            validateLogin()
        }

        recoverPassViewModel = ViewModelProviders.of(this).get(RecoverPassViewModel::class.java)
        recoverPassViewModel.initViewModel(this)
        recoverPassViewModel.apply {
            messageLiveData.observe(this@RecoverPassActivity, Observer { message -> showMessage(message!!) })
            resultLiveData.observe(this@RecoverPassActivity, Observer {
                result ->
                if(result!!){
                    showSuccessfulMessage()
                }
            })
        }
    }

    override fun addRootView() {
        rootView = v_root
    }

    fun validateLogin(){
        val email = txt_usuario.text.toString()
        recoverPassViewModel.sendEmail(email)
    }

    fun showSuccessfulMessage(){
        MessageDialog(this).showMessage("Su solicitud fue enviado, revise su bandeja de correo electronico!", object : MessageDialog.Callback{
            override fun onOk(){
                onBackPressed()
            }
        })
    }
}