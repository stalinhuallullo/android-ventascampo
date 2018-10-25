package dev.lstr.llevateclaro.presentation.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.data.model.DetalleReferidoE
import dev.lstr.llevateclaro.data.model.DetalleReferidoObsE
import dev.lstr.llevateclaro.presentation.presenter.view_model.DetalleReferidoViewModel
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import kotlinx.android.synthetic.main.activity_detalle_referido.*
import kotlinx.android.synthetic.main.container_detalle_referido.*

class DetalleReferidoActivity : BaseActivity() {

    private lateinit var detalleReferidoViewModel: DetalleReferidoViewModel

    companion object {
        val action_detalle_referido_movil = "cdmv"
        val action_detalle_referido_fijo = "cdvf"
    }

    var action_type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_referido)
        init()
        TrackerGA.getInstance(this).registerScreen("Referido.Detalle")
    }

    fun init() {
        val ref_id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        action_type = intent.getStringExtra("action")

        toolbar.title = name
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        ll_acumulado.visibility = View.INVISIBLE

        detalleReferidoViewModel = ViewModelProviders.of(this).get(DetalleReferidoViewModel::class.java)
        detalleReferidoViewModel.initViewModel(this)
        detalleReferidoViewModel.apply {
            messageLiveData.observe(this@DetalleReferidoActivity, Observer { message -> showMessage(message!!) })
            detalleReferidoLiveData.observe(this@DetalleReferidoActivity, Observer { data -> showData(data!!) })
            detalleReferidoObsLiveData.observe(this@DetalleReferidoActivity, Observer { data -> showDataObs(data!!) })
        }
        detalleReferidoViewModel.getDetalleReferido(ref_id, action_type)
        detalleReferidoViewModel.getDetalleReferidoObs(ref_id, action_type)
    }

    fun showData(data: DetalleReferidoE) {
        var default_message = ""
        var is_completed = false

        if (data.datosen == "A") {
            v_datos_enviados.text = "✓"
           // v_datos_enviados.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            v_datos_enviados.setBackgroundResource(R.drawable.borderdetallegreen)
            default_message = "Estas en el proceso de evaluación."
        } else if (data.datosen == "R") {
            v_datos_enviadosx.text = "X"
            //v_datos_enviadosx.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            v_datos_enviadosx.setBackgroundResource(R.drawable.borderdetallered)
            default_message = "Estas en el proceso de evaluación."
        }
        if (data.califica == "A") {
            v_calificacion.text = "✓"
//            v_calificacion.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            v_calificacion.setBackgroundResource(R.drawable.borderdetallegreen)
            default_message = "Estas en el proceso de venta."
        } else if (data.califica == "R") {
            v_calificacionx.text = "X"
//            v_calificacionx.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            v_calificacionx.setBackgroundResource(R.drawable.borderdetallered)
            default_message = "Lo sentimos, su referido no califica."
        }
        if (data.venta == "A") {
            v_venta_exitosa.text = "✓"
//            v_venta_exitosa.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            v_venta_exitosa.setBackgroundResource(R.drawable.borderdetallegreen)
            default_message = "Estas en el proceso de entrega."
        } else if (data.venta == "R") {
            v_venta_exitosax.text = "X"
            //v_venta_exitosax.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            v_venta_exitosax.setBackgroundResource(R.drawable.borderdetallered)
            default_message = "Venta no concretada."
        }


        if (action_type == action_detalle_referido_movil) {
            tv_ultimo_paso.text = "Entregado: "
            if (data.entregado == "A") {
                v_entregado.text = "✓"
                //v_entregado.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                v_entregado.setBackgroundResource(R.drawable.borderdetallegreen)
                tv_acumulado.text = ""
                tv_mensaje.visibility = View.INVISIBLE
                ll_acumulado.visibility = View.VISIBLE
                tv_acumulado.text = "Acumulaste S/${data.acomulado}"
                default_message = ""
                is_completed = true
            } else if (data.entregado == "R") {
                v_entregadox.text = "X"
                //v_entregadox.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                v_entregadox.setBackgroundResource(R.drawable.borderdetallered)
                default_message = "No entregado."
            }
        } else if (action_type == action_detalle_referido_fijo) {
            tv_ultimo_paso.text = "Instalado: "
            if (data.instalado == "A") {
                v_entregado.text = "✓"
                //v_entregado.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                v_entregado.setBackgroundResource(R.drawable.borderdetallegreen)
                tv_acumulado.text = ""
                tv_mensaje.visibility = View.INVISIBLE
                ll_acumulado.visibility = View.VISIBLE
                tv_acumulado.text = "Acumulaste S/${data.acomulado}"
                default_message = ""
                is_completed = true
            } else if (data.instalado == "R") {
                v_entregadox.text = "X"
               // v_entregadox.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                v_entregadox.setBackgroundResource(R.drawable.borderdetallered)
                default_message = "No instalado."
            }
        }

        if (data.mensaje_extra != null && data.mensaje_extra != "") {
            tv_mensaje.text = data.mensaje_extra
        } else {
            tv_mensaje.text = default_message
        }

        if (is_completed) {
            cv_mensaje.visibility = View.GONE
        } else {
            cv_mensaje.visibility = View.VISIBLE
            cv_mensaje.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
        }
    }

    fun showDataObs(data: DetalleReferidoObsE) {

        if (data.datosen_obs != null && !data.datosen_obs.trim().equals("")) {
            v_datos_enviados_text.text = data.datosen_obs
            rl_v_datos_enviados_text.visibility = View.VISIBLE
        } else {
            rl_v_datos_enviados_text.visibility = View.GONE
        }
        if (data.califica_obs != null && !data.califica_obs.trim().equals("")) {
            v_calificacion_text.text = data.califica_obs
            rl_v_calificacion_text.visibility = View.VISIBLE
        } else {
            rl_v_calificacion_text.visibility = View.GONE
        }
        if (data.ven_obs != null && !data.ven_obs.trim().equals("")) {
            v_venta_exitosa_text.text = data.ven_obs
            rl_v_venta_exitosa_text.visibility = View.VISIBLE
        } else {
            rl_v_venta_exitosa_text.visibility = View.GONE
        }
        if (data.entre_obs != null && !data.entre_obs.trim().equals("")) {
            v_entregado_text.text = data.entre_obs
            rl_v_entregado_text.visibility = View.VISIBLE
        } else {
            rl_v_entregado_text.visibility = View.GONE
        }
    }

    override fun addRootView() {
        rootView = v_root
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        when (id) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}