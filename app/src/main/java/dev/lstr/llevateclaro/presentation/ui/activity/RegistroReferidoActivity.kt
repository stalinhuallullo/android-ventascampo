package dev.lstr.llevateclaro.presentation.ui.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.ipaulpro.afilechooser.utils.FileUtils
import com.squareup.picasso.Picasso
import dev.lstr.llevateclaro.BuildConfig
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.data.datasource.rest.api.RetrofitClient
import dev.lstr.llevateclaro.data.datasource.rest.api.service.BusinessSessionService
import dev.lstr.llevateclaro.presentation.presenter.RegisterReferidoPresenter
import dev.lstr.llevateclaro.presentation.ui.dialog.MessageDialog
import dev.lstr.llevateclaro.presentation.util.ProgressRequestBody
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import dev.lstr.llevateclaro.presentation.view.RegisterReferidoView
import kotlinx.android.synthetic.main.activity_registro_referido.*
import kotlinx.android.synthetic.main.container_registro_referido.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response


class RegistroReferidoActivity : BaseActivity(), RegisterReferidoView, ProgressRequestBody.UploadCallbacks {

    var action: String = ""

    private val REGISTRO_MOVIL = "regism"
    private val REGISTRO_FIJO = "regisf"
    private var action_api = "registerReferred"

    private val GALLERY = 1
    private val CAMERA = 2

    private val PERMISSION_REQUEST:Int = 1000
    private val PICK_IMAGE_REQUEST:Int = 1001

    val mArrayUri = ArrayList<Uri>()
    lateinit var dialog: ProgressDialog
    //val BASE_URL = "http://sgtel.pe/"
    // http://www.sgtel.pe/prueba_app/webservicev/web/registerReferred.php
    //val apiUpload:BusinessSessionService
        //get() = RetrofitClient.getClient(BASE_URL).create(BusinessSessionService::class.java)
    //lateinit var mService:BusinessSessionService

    val registerReferidoPresenter by lazy {
        RegisterReferidoPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.container_registro_referido)
        setContentView(R.layout.activity_registro_referido)
        init()
    }

    fun init() {
        val url:String = "http://sgtel.pe/prueba_app/llevateloadmin/public/uploads/abf681ec1e5ce1f891dbc42fe3d14dcb.jpeg"
        Picasso.get() // give it the context
                .load(url) // load the image
                .into(image_picasso) // select the ImageView to load it into



        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val tipo_seleccionado = intent.getStringExtra("type_selected")

        var title = ""

        if (tipo_seleccionado.equals("fijo")) {
            action = REGISTRO_FIJO
            title = "Fijo"
        } else {
            action = REGISTRO_MOVIL
            title = "Movil"
        }

        //tv_title.text = title
        txt_title_referred.setText("Ingresar Datos del Cliente " + title)
        home_collapsing.title = title
        home_collapsing.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
        home_collapsing.setExpandedTitleTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.transparent)))

        // Add files
        btn_addFiles.setOnClickListener {
            showAddFiles()
        }

        btn_enviar.setOnClickListener {
            sendData()
        }

        fillSpinner()

        registerReferidoPresenter.addView(this)

        TrackerGA.getInstance(this).registerScreen("Referido.Registro.$title")
        if (action == REGISTRO_MOVIL) TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Movil", "opened")
        else TrackerGA.getInstance(this).registerEvent("Referido", "Registro.Fijo", "opened")
    }

    private fun showProgress() {
        progressBar.setVisibility(View.VISIBLE)
        //btnChoose.setEnabled(false)
        //btnUpload.setEnabled(false)
    }

    /*private fun openFbPromo() {
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

    }*/

    fun fillSpinner() {
        val operadorList = ArrayList<String>()
        val operadorCedentList = ArrayList<String>()

        if (action == REGISTRO_FIJO) {
            operadorList.add("Linea Nueva")
            //operadorList.add("Otros")


            Toast.makeText(this, "Fijo", Toast.LENGTH_SHORT).show()
            ll_sp_modality.visibility = View.GONE
            ll_operatos_content.visibility = View.GONE
            ll_operator_cedent.visibility = View.GONE
        } else {
            operadorList.add("Claro")
            operadorList.add("Linea Nueva")

            operadorCedentList.add("Movistar")
            operadorCedentList.add("Entel")
            operadorCedentList.add("Bitel")


            val condicionList = arrayOf("Pre Pago", "Post Pago")
            val condAdapter = ArrayAdapter(this, R.layout.spinner_item_black, condicionList)
            condAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp_modality.adapter =  condAdapter
            ll_sp_modality.visibility = View.VISIBLE
            ll_operatos_content.visibility = View.VISIBLE
            Toast.makeText(this, "Movil", Toast.LENGTH_SHORT).show()
        }

        val opAdapter = ArrayAdapter(this, R.layout.spinner_item_black, operadorList)
        opAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_operator.adapter = opAdapter

        val opCedentAdapter = ArrayAdapter(this, R.layout.spinner_item_black, operadorCedentList)
        opCedentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_operator_cedent.adapter = opCedentAdapter

        //sp_operator.post {
            sp_operator.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                    try{
                        if(adapterView != null && view != null){
                            if(position >= 0){
                                val objItem = adapterView.getItemAtPosition(position)
                                if (objItem != null){
                                    val item = objItem.toString()
                                    Toast.makeText(this@RegistroReferidoActivity, " MENSAJE: " + item, Toast.LENGTH_SHORT).show()
                                    if ("Linea Nueva" == item) {
                                        //sp_type_sale.isEnabled = false
                                        //sp_tiempo_operador.isEnabled = false
                                        Toast.makeText(this@RegistroReferidoActivity, " MENSAJE IF: " + item, Toast.LENGTH_SHORT).show()
                                        ll_operator_cedent.visibility = View.GONE
                                    } else {
                                        Toast.makeText(this@RegistroReferidoActivity, " MENSAJE ELSE: " + item, Toast.LENGTH_SHORT).show()
                                        //sp_type_sale.isEnabled = true
                                        //sp_tiempo_operador.isEnabled = true
                                        ll_operator_cedent.visibility = View.VISIBLE
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
        //}

        var tipoServicioList = arrayOf("Portabilidad", "Renovacion", "Migracion", "Linea Nueva")
        if (action == REGISTRO_FIJO) tipoServicioList = arrayOf("Portabilidad", "Linea Nueva")
        val tsAdapter = ArrayAdapter(this, R.layout.spinner_item_black, tipoServicioList)
        tsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_type_sale.adapter = tsAdapter

        sp_type_sale.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                try{
                    if(adapterView != null && view != null){
                        if(position >= 0){
                            val objItem = adapterView.getItemAtPosition(position)
                            if (objItem != null){
                                val item = objItem.toString()
                                Toast.makeText(this@RegistroReferidoActivity, " ll_operatos_content: " + item, Toast.LENGTH_SHORT).show()
                                if ("Linea Nueva" == item) {
                                    Toast.makeText(this@RegistroReferidoActivity, "if: " + item, Toast.LENGTH_SHORT).show()
                                    ll_operatos_content.visibility = View.GONE
                                } else {
                                    Toast.makeText(this@RegistroReferidoActivity, "else: " + item, Toast.LENGTH_SHORT).show()
                                    if (action != REGISTRO_FIJO) ll_operatos_content.visibility = View.VISIBLE
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

        val tipoDocList = arrayOf("DNI", "Pasaporte", "RUC", "CE")
        val tipoDocAdapter = ArrayAdapter(this, R.layout.spinner_item_black, tipoDocList)
        tipoDocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_type_document.adapter = tipoDocAdapter
    }

    fun showAddFiles(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            var res = ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PICK_IMAGE_REQUEST)
            Log.d("RES PERMISSION", res.toString())
        } else {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }



        // Show multiple option
        // 1 => Galery
        // 2 => Camera
        /*val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Subir imagen")
        val pictureDialogItems = arrayOf("Galeria", "Camara")
        pictureDialog.setItems(pictureDialogItems) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()*/

    }


    fun choosePhotoFromGallary() {
        // Sin Utilizar
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PICK_IMAGE_REQUEST)
        } else {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
            //val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //startActivityForResult(galleryIntent, GALLERY)
        }
    }

    private fun takePhotoFromCamera() {
        // Sin Utilizar
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_IMAGE_REQUEST){
                mArrayUri.clear()
                if(data != null){
                    if (null != data.clipData) {
                        val mClipData = data.clipData
                        for (i in 0 until mClipData.itemCount)  mArrayUri.add(data.clipData.getItemAt(i).uri)
                    }
                    else {
                        mArrayUri.add(data.getData())
                        //image_view.setImageURI(data.getData())
                    }
                }
            }
        }
    }

    fun sendData() {
        var names:String? = txt_names.text.toString()
        var last_names:String? = txt_last_names.text.toString()
        var sp_modality:String? = if (action == REGISTRO_MOVIL) sp_modality.selectedItem.toString().toLowerCase() else ""
        var sp_type_sale:String? = if(sp_type_sale.selectedItem == null) {""} else {sp_type_sale.selectedItem.toString().toLowerCase()}

        var sp_type_document:String? = if(sp_type_document.selectedItem == null) {""} else {sp_type_document.selectedItem.toString().toLowerCase()}
        var number_document:String? = txt_number_document.text.toString()

        var direction:String? = txt_direction.text.toString()
        var direction_referred = txt_direction_referred.text.toString()
        var district:String? = txt_district.text.toString()

        var plan:String? = txt_plan.text.toString()

        var operator:String? = if(sp_operator.selectedItem == null) {""} else {sp_operator.selectedItem.toString().toLowerCase()}
        var operator_cedent:String? = if(sp_operator_cedent.selectedItem == null) {""} else {sp_operator_cedent.selectedItem.toString().toLowerCase()}

        var number_movil:String? = txt_number_movil.text.toString()
        var observation:String? = txt_observation.text.toString()

        var type_referrals = '1'

        /*var condition = ""
        if (action == REGISTRO_MOVIL) {
            condition = sp_type_sale
        }*/

        when (sp_modality) {
            "pre pago" -> sp_modality = "0"
            "post pago" -> sp_modality = "1"
            else -> sp_modality = ""
        }

        when (sp_type_sale) {
            "portabilidad" -> sp_type_sale = "3"
            "renovacion" -> sp_type_sale = "0"
            "migracion" -> sp_type_sale = "1"
            "linea nueva" -> sp_type_sale = "2"
            else -> sp_type_sale = ""
        }

        when (sp_type_document) {
            "dni" -> sp_type_document = "0"
            "pasaporte" -> sp_type_document = "1"
            "ruc" -> sp_type_document = "2"
            "ce" -> sp_type_document = "3"
            else -> sp_type_document = ""
        }

        when (operator) {
            "claro" -> operator = "0"
            "linea nueva" -> operator = "1"
            else -> operator = ""
        }

        when (operator_cedent) {
            "movistar" -> operator_cedent = "0"
            "entel" -> operator_cedent = "1"
            "bitel" -> operator_cedent = "2"
            else -> operator_cedent = ""
        }


        val listMultPartBody = ArrayList<MultipartBody.Part>()

        for (i in 0 until mArrayUri.size) {
            Log.d("uri "+ i.toString() +"=> ", mArrayUri.get(i).toString())
            val file = FileUtils.getFile(this, mArrayUri.get(i))
            val requestFile = ProgressRequestBody(file, this)
            val body = MultipartBody.Part.createFormData("upload_file[]", file.name, requestFile)
            listMultPartBody.add(body)
        }

        //registerReferidoPresenter.registerImage(listMultPartBody, action)
        var validate = 0
        if(action == REGISTRO_MOVIL) validate = validateMovil(names!!, last_names!!, sp_modality, sp_type_sale, sp_type_document, number_document!!, direction!!, plan!!, operator, operator_cedent, number_movil!!)
        else validate = validateFixed(names!!, last_names!!, sp_type_sale, sp_type_document, number_document!!, direction!!, plan!!, number_movil!!)

        if(validate != 0) showMessage("Campos incompletos, por favor complete los campos.")
        else {

            if(action == REGISTRO_MOVIL){
                if(sp_type_sale != "2"){
                    if(operator == "1"){
                        operator_cedent = ""
                    }
                } else {
                    operator = ""
                    operator_cedent = ""
                }
            }
            /*Log.d("names", names)
            Log.d("last_names", last_names)
            Log.d("sp_modality", sp_modality)
            Log.d("sp_type_sale", sp_type_sale)
            Log.d("sp_type_document", sp_type_document)
            Log.d("number_document", number_document)
            Log.d("direction", direction)
            Log.d("direction_referred", direction_referred)
            Log.d("district", district)
            Log.d("plan", plan)
            Log.d("operator", operator.toString())
            Log.d("operator_cedent", operator_cedent.toString())
            Log.d("number_movil", number_movil)
            Log.d("observation", observation)
            Log.d("action", action)*/
            var type_referrals = action_api
            registerReferidoPresenter.registerReferido(names, last_names, sp_modality, sp_type_sale, sp_type_document, number_document!!, direction, direction_referred, plan, operator!!, operator_cedent!!, number_movil, observation!!, listMultPartBody, type_referrals, action)
            mArrayUri.clear()
        }
    }

    fun validateMovil(names: String, last_names: String, sp_modality: String, sp_type_sale: String, sp_type_document: String, number_document: String, direction: String, plan: String, operator: String, operator_cedent: String, number_movil: String): Int{
        var error = 0

        when (true) {
            names == "" -> error++
            last_names == "" -> error++
            sp_modality == "" -> error++
            sp_type_sale == "" -> error++
            sp_type_document == "" -> error++
            number_document == "" -> error++
            direction == "" -> error++
            plan == "" -> error++
            operator == "" -> {
                if(sp_type_sale != "2"){
                    if(operator != "1") error++
                }
            }
            operator_cedent == "" -> if(sp_type_sale != "2") error++
            number_movil == "" -> error++
            else -> error = 0
        }

        return  error
    }

    fun validateFixed(names: String, last_names: String, sp_type_sale: String, sp_type_document: String, number_document: String, direction: String, plan: String, number_movil: String): Int{
        var error = 0

        when (true) {
            names == "" -> error++
            last_names == "" -> error++
            sp_type_sale == "" -> error++
            sp_type_document == "" -> error++
            number_document == "" -> error++
            direction == "" -> error++
            plan == "" -> error++
            number_movil == "" -> error++
            else -> error = 0
        }

        return  error
    }

    override fun goToSuccess() {
        val operador =""// sp_operador.selectedItem.toString().toLowerCase()
        val tipo_documento = sp_type_document.selectedItem.toString().toLowerCase()
        val condicion_op = ""//if (action == REGISTRO_MOVIL) sp_type_sale.selectedItem.toString().toLowerCase() else ""

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
