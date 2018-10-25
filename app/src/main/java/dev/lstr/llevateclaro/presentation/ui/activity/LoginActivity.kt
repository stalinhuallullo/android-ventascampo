package dev.lstr.llevateclaro.presentation.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.presentation.presenter.view_model.LoginViewModel
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import io.fabric.sdk.android.services.common.CommonUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(){
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        TrackerGA.getInstance(this).registerScreen("Usuario.Login")
    }

    fun init(){
        btn_ingresar.setOnClickListener {
            validateLogin()
        }

        tv_olvide_pass.setOnClickListener {
            goToOlvidePass()
        }

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.initViewModel(this)
        loginViewModel.apply {
            messageLiveData.observe(this@LoginActivity, Observer { message -> showMessage(message!!) })
            resultLiveData.observe(this@LoginActivity, Observer {
                r ->
                if (r!!.success){
                    TrackerGA.getInstance(this@LoginActivity).registerEvent("Login", "Success", "")
                    goToHome()
                }else{
                    showMessage(r.data as String)
                    TrackerGA.getInstance(this@LoginActivity).registerEvent("Login", "Failed","")
                }
            })
        }
    }

    override fun addRootView() {
        rootView = v_root
    }

    fun validateLogin(){
        val user = txt_usuario.text.toString()
        val pass = txt_password.text.toString()
        CommonUtils.hideKeyboard(this, txt_usuario)
        CommonUtils.hideKeyboard(this, txt_password)

        loginViewModel.login(user, pass)
    }

    fun goToHome(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    fun goToOlvidePass(){
        startActivity(Intent(this, RecoverPassActivity::class.java))
    }
}