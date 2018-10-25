package dev.lstr.llevateclaro.presentation.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import dev.lstr.llevateclaro.BuildConfig
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.presentation.presenter.RegisterReferidoPresenter
import dev.lstr.llevateclaro.presentation.ui.dialog.MessageDialog
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import dev.lstr.llevateclaro.presentation.view.RegisterReferidoView
import kotlinx.android.synthetic.main.activity_registro_referido.*
import kotlinx.android.synthetic.main.container_registro_referido.*


class RegistroReferidoActivity : BaseActivity(), RegisterReferidoView {

    var action: String = ""

    private val REGISTRO_MOVIL = "regism"
    private val REGISTRO_FIJO = "regisf"

    val registerReferidoPresenter by lazy {
        RegisterReferidoPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_referido)
        init()

    }

    fun init() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val tipo_seleccionado = intent.getStringExtra("type_selected")

        action = REGISTRO_MOVIL
        var title = "Movil"
        if (tipo_seleccionado.equals("fijo")) {
            action = REGISTRO_FIJO
            title = "Fijo"
            iv_main_icon.setImageResource(R.drawable.icon_fijo_white)
        }

        tv_title.text = title
        home_collapsing.title = title
        home_collapsing.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
        home_collapsing.setExpandedTitleTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.transparent)))


        btn_enviar.setOnClickListener {
            sendData()
        }

        tv_fb_title.setOnClickListener {
            openFbPromo()
        }
        iv_fb_icon.setOnClickListener {
            openFbPromo()
        }

        fillSpinner()

        registerReferidoPresenter.addView(this)

        TrackerGA.getInstance(this).registerScreen("Referido.Registro.$title")
        if (action == REGISTRO_MOVIL) {
            TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Movil", "opened")
        } else {
            TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Fijo", "opened")
        }
    }


    private fun openFbPromo() {
        var link_fb = if (action == REGISTRO_MOVIL) {
            "https://www.facebook.com/llevateloenportabilidad"
        } else {
            "https://www.facebook.com/ponle-play-408146769701152"
        }

        try {
            val webpage = Uri.parse(link_fb)
            val myIntent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

    }

    fun fillSpinner() {
        val operadoresList = ArrayList<String>()
        if (action == REGISTRO_FIJO) {
            operadoresList.add("Nuevo Servicio")
            operadoresList.add("Movistar")
        } else {
            operadoresList.add("Claro")
            operadoresList.add("Movistar")
            operadoresList.add("Entel")
            operadoresList.add("Bitel")
            operadoresList.add("Linea Nueva")
        }
        val opAdapter = ArrayAdapter(this, R.layout.spinner_item_black, operadoresList)
        opAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_operador.adapter = opAdapter

        sp_operador.post {
            sp_operador.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                    try{
                        if(adapterView != null && view != null){
                            if(position >= 0){
                                val objItem = adapterView.getItemAtPosition(position)
                                if (objItem != null){
                                    val item = objItem.toString()
                                    if ("Nuevo Servicio" == item || "Linea Nueva" == item) {
                                        sp_tipo_servicio.isEnabled = false
                                        sp_tiempo_operador.isEnabled = false
                                    } else {
                                        sp_tipo_servicio.isEnabled = true
                                        sp_tiempo_operador.isEnabled = true
                                    }
                                }
                            }
                        }
                    }catch (e:Exception){
                        Crashlytics.logException(e)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }

            }
        }

        var tipoServicioList = arrayOf("Portabilidad", "Renovacion", "Migracion", "Linea Nueva")
        if (action == REGISTRO_FIJO)
            tipoServicioList = arrayOf("Portabilidad", "Nuevo Servicio")
        val tsAdapter = ArrayAdapter(this, R.layout.spinner_item_black, tipoServicioList)
        tsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_tipo_servicio.adapter = tsAdapter

        if (action == REGISTRO_MOVIL) {
            val condicionList = arrayOf("Pre Pago", "Post Pago")
            val condAdapter = ArrayAdapter(this, R.layout.spinner_item_black, condicionList)
            condAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp_condicion.adapter = condAdapter
        } else {
            ll_sp_condicion.visibility = View.GONE
        }

        if (action == REGISTRO_MOVIL) {
            txt_direccion.visibility = View.GONE
        }

        val tiempoOpList = arrayOf(" 0 - 3 meses", "3 – 6 meses", "3 – 9 meses")
        val tiempoOpAdapter = ArrayAdapter(this, R.layout.spinner_item_black, tiempoOpList)
        tiempoOpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_tiempo_operador.adapter = tiempoOpAdapter

        val tipoDocList = arrayOf("DNI", "CE", "PTP", "Pasaporte", "Otro")
        val tipoDocAdapter = ArrayAdapter(this, R.layout.spinner_item_black, tipoDocList)
        tipoDocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_tipo_documento.adapter = tipoDocAdapter
    }

    fun sendData() {
        val apaterno = txt_ap_paterno.text.toString()
        val amaterno = txt_ap_materno.text.toString()

        val nombre = txt_nombres.text.toString()
        val tipo_documento = sp_tipo_documento.selectedItem.toString().toLowerCase()
        val dni = txt_dni.text.toString()
        val direccion = txt_direccion.text.toString()

        val celular = txt_n_celular.text.toString()
        val operador = sp_operador.selectedItem.toString().toLowerCase()

        val parentesco = txt_parentesco.text.toString()
        val tipo_servicio = sp_tipo_servicio.selectedItem.toString().toLowerCase().replace(" ", "_")

        var condicion_op = ""
        if (action == REGISTRO_MOVIL) {
            condicion_op = sp_condicion.selectedItem.toString().toLowerCase()
        }


        val tiempo_op = sp_tiempo_operador.selectedItem.toString().toLowerCase()
        if (!(operador.toLowerCase().equals("Nuevo Servicio".toLowerCase()) || operador.toLowerCase().equals("Linea Nueva".toLowerCase()))) {
            if (sp_tipo_servicio.selectedItem.toString().trim().equals("") &&
                    sp_tiempo_operador.selectedItem.toString().trim().equals("")) {
                showMessage("Seleccione el tipo de servicio y el tiempo con operador.")
                return
            } else {
                if (sp_tipo_servicio.selectedItem.toString().trim().equals("")) {
                    showMessage("Seleccione el tipo de servicio.")
                    return
                }
                if (sp_tiempo_operador.selectedItem.toString().trim().equals("")) {
                    showMessage("Seleccione el tiempo con operador.")
                    return
                }
            }
        }

        if (apaterno == "" || nombre == "" || dni == "" || celular == "" || operador == "" || parentesco == "" || tipo_servicio == "") {
            showMessage("Datos a ingresar deben ser completados.")
        }else {
            registerReferidoPresenter.registerReferido(apaterno, amaterno, nombre, tipo_documento, dni, direccion, celular, operador, tipo_servicio, parentesco, condicion_op, tiempo_op, action)
        }

    }

    override fun goToSuccess() {
        val operador = sp_operador.selectedItem.toString().toLowerCase()
        val tipo_documento = sp_tipo_documento.selectedItem.toString().toLowerCase()
        val condicion_op = if (action == REGISTRO_MOVIL) sp_condicion.selectedItem.toString().toLowerCase() else ""

        if (action == REGISTRO_MOVIL) {
            TrackerGA.getInstance(this).registerScreen("Registro.Referido.Movil.Success")
            TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Movil", "success")
            TrackerGA.getInstance(this).registerEvent("ReferidoMovil", "Operador", operador)
            TrackerGA.getInstance(this).registerEvent("ReferidoMovil", "TipoDoc", tipo_documento)
            TrackerGA.getInstance(this).registerEvent("ReferidoMovil", "Condicion", condicion_op)
        } else {
            TrackerGA.getInstance(this).registerScreen("Registro.Referido.Fijo.Success")
            TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Fijo", "success")
            TrackerGA.getInstance(this).registerEvent("ReferidoFijo", "Operador", operador)
            TrackerGA.getInstance(this).registerEvent("ReferidoFijo", "TipoDoc", tipo_documento)
            TrackerGA.getInstance(this).registerEvent("ReferidoFijo", "Condicion", condicion_op)
        }

        MessageDialog(this).showMessage("Los datos se registraron exitosamente, luego de ser evaluados se actualizará su estado y podrá ver en la lista de referidos.", object : MessageDialog.Callback {
            override fun onOk() {
                goToList()
            }
        })
    }

    override fun goToFailed() {
        if (action == REGISTRO_MOVIL) {
            TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Movil", "failed")
        } else {
            TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Fijo", "failed")
        }
    }

    override fun addRootView() {
        rootView = v_root
    }

    fun goToList() {
        startActivity(Intent(this, ListaReferidoActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        when (id) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (action == REGISTRO_MOVIL) {
            TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Movil", "canceled")
        } else {
            TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Fijo", "canceled")
        }
        super.onBackPressed()
    }
}