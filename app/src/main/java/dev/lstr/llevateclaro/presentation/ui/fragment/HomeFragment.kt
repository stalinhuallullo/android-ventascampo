package dev.lstr.llevateclaro.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.model.S
import dev.lstr.llevateclaro.presentation.ui.activity.RegistroReferidoActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by lesternr on 5/5/18.
 */
class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSettings()
        init()
    }

    private fun initSettings() {
        when(CurrentUser.getInstance(context).getBusiness()){
            S.key_claro_peru -> iv_logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_empresa_claro))
        }
    }

    fun init() {

        ll_menu_fijo.setOnClickListener {
            startFijo()
        }

        ll_menu_movil.setOnClickListener {
            startMovil()
        }

    }

    fun startFijo() {
        val it = Intent(context, RegistroReferidoActivity::class.java)
        it.putExtra("type_selected", "fijo")
        startActivity(it)
    }

    fun startMovil() {
        val it = Intent(context, RegistroReferidoActivity::class.java)
        it.putExtra("type_selected", "movil")
        startActivity(it)
    }

}