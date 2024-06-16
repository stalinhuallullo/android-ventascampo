package dev.lstr.llevateclaro.presentation.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MenuItem
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.presentation.ui.fragment.ListaReferidoFijoFragment
import dev.lstr.llevateclaro.presentation.ui.fragment.ListaReferidoMovilFragment
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import kotlinx.android.synthetic.main.activity_lista_referido.*

/**
 * Created by lesternr on 5/5/18.
 */
class ListaReferidoActivity : BaseActivity(){

    val listaReferidoFijoFragment by lazy {
        ListaReferidoFijoFragment()
    }
    val listaReferidoMovilFragment by lazy {
        ListaReferidoMovilFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_referido)
        init()
        TrackerGA.getInstance(this).registerScreen("Referido.Lista")
    }

    fun init(){
        toolbar.title = "Mis Referidos"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setupViewPager()
        tabs.setupWithViewPager(viewpager)
    }

    fun setupViewPager(){
        val listaReferidoAdapter = ListaReferidoAdapter(supportFragmentManager)
        listaReferidoAdapter.addFragment(listaReferidoMovilFragment, "MOVIL")
        listaReferidoAdapter.addFragment(listaReferidoFijoFragment, "FIJO")
        viewpager.adapter = listaReferidoAdapter
    }

    override fun addRootView() {
        rootView = v_root
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        Log.d("onOptionsItemSelected", id.toString())
        when(id){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class ListaReferidoAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
        val mFragmentList = ArrayList<Fragment>()
        val mTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            Log.i("getItem", position.toString())
            return mFragmentList[position]
        }
        override fun getCount(): Int {
            Log.i("getCount", mFragmentList.size.toString())
            return mFragmentList.size
        }
        fun addFragment(fragment:Fragment, title: String){
            Log.i("addGragment", title)
            mFragmentList.add(fragment)
            mTitleList.add(title)
        }
        override fun getPageTitle(position: Int):CharSequence{
            Log.i("getPageTitle", position.toString())
            return mTitleList[position]
        }
    }
}