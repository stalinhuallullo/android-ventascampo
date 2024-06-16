package dev.lstr.llevateclaro.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.model.S
import dev.lstr.llevateclaro.presentation.ui.fragment.HomeFragment
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.container_home.*


class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener{

    var toggleHamburguer:ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initSettings()
        init()
        openHomeFragment()
        Toast.makeText(this, "openHomeFragment", Toast.LENGTH_SHORT).show()

        TrackerGA.getInstance(this).registerScreen("Inicio")
    }

    fun initSettings(){
        val user = CurrentUser.getInstance(this).getUser()
        if (user != null){
            TrackerGA.getInstance(this@HomeActivity).setUserInfo(user)
        }

        val iv_logo = nav_view.getHeaderView(0).findViewById<ImageView>(R.id.iv_logo)

        /*when(CurrentUser.getInstance(this).getBusiness()){
            S.key_claro_peru -> iv_logo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_empresa_claro))
        }*/
    }

    fun init(){
        toolbar.title = "Inicio"
        setSupportActionBar(toolbar)

        toggleHamburguer = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggleHamburguer!!)
        toggleHamburguer!!.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun addRootView() {}

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.nav_home -> openHomeFragment()
            R.id.nav_perfil -> openPerfil()
            R.id.nav_referidos -> openReferidos()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }

    fun openHomeFragment(){
        TrackerGA.getInstance(this).registerEvent("HomeMenu", "Home", "tap")

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
    }

    fun openPerfil(){
        TrackerGA.getInstance(this).registerEvent("HomeMenu", "Profile", "tap")

        val it = Intent(this, ProfileActivity::class.java)
        startActivity(it)
    }

    fun openReferidos(){
        TrackerGA.getInstance(this).registerEvent("HomeMenu", "ListaReferidos", "tap")

        val it = Intent(this, ListaReferidoActivity::class.java)
        startActivity(it)
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}