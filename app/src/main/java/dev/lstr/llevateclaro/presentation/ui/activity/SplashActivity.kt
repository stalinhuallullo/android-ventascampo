package dev.lstr.llevateclaro.presentation.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import dev.lstr.llevateclaro.BuildConfig
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            validateAppStatus()
        }, 1500)
    }

    override fun addRootView() {
        rootView = v_root
    }

    private fun validateAppStatus(){
        if (BuildConfig.BUILD_TYPE == "release" && Build.MODEL == "Simulator"){
            showMessage("App is only available for real devices, thank you!")
            TrackerGA.getInstance(this).registerEvent("AppAccess", "rejected", "simulator")
            return
        }
        validateSession()
    }

    private fun validateSession(){
        val user = CurrentUser.getInstance(this).getUser()
        if(user == null) {
            validateLogin()
        }else{
            goToHome()
        }
    }

    fun validateLogin(){
        TrackerGA.getInstance(this).registerScreen("Splash.ToLogin")

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun goToHome(){
        TrackerGA.getInstance(this).registerScreen("Splash.ToHome")

        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}