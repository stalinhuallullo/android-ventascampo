package dev.lstr.llevateclaro.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import dev.lstr.llevateclaro.BuildConfig
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.model.UserE
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.container_perfil.*

class ProfileActivity : BaseActivity(){

    var currUser:CurrentUser? = null
    var user:UserE? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        init()
        TrackerGA.getInstance(this).registerScreen("Usuario.Perfil.Show")
    }

    fun init(){
        toolbar.title = "Mi Perfil"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        currUser = CurrentUser.getInstance(this)
        user = currUser!!.getUser()
        if(currUser == null) {
            doLogout()
        }

        fillData()

        btn_logout.setOnClickListener {
            startLogout()
        }
    }

    fun fillData(){
        val username = "${user!!.nombres} ${user!!.apellido_paterno} ${user!!.apellido_materno}"
        txt_user_name.text = username
        txt_user_email.text = "EMAIL: ${user!!.email}"
        txt_user_dni.text = "DNI: ${user!!.dni}"
        txt_user_operadora.text = "OPERADORA: ${user!!.operadora}"
        txt_user_phone.text = "TELEFONO: " + user!!.telefono

        txt_app_version.text = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }

    override fun addRootView() {}

    fun startLogout(){
        currUser!!.removeUser()
        doLogout()
    }
    fun doLogout(){
        TrackerGA.getInstance(this).registerScreen("Usuario.Perfil.Logout")
        TrackerGA.getInstance(this).registerEvent("MiPerfil", "Logout", "tap")

        val it = Intent(this, LoginActivity::class.java)
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        when(id){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}