package dev.lstr.llevateclaro.presentation.presenter

import android.util.Log
import android.widget.Toast
import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.repository.BusinessRepository
import dev.lstr.llevateclaro.presentation.view.RegisterReferidoView
import okhttp3.MultipartBody
import org.jetbrains.anko.indeterminateProgressDialog

/**
 * Created by lesternr on 5/11/18.
 */
class RegisterReferidoPresenter {
    private var view: RegisterReferidoView? = null

    private val userSessionDataSource by lazy{
        BusinessRepository.provideBusiness()
    }

    private val userSessionDataSourceImages by lazy{
        BusinessRepository.provideImages()
    }

    fun addView(view: RegisterReferidoView){
        this.view = view
    }

    //fun registerReferido(apaterno: String, amaterno: String, nombre: String, tipo_doc: String, dni: String, direccion: String, telefono: String, operador: String, tipo_servicio: String, parentesco: String, condicion_op: String, tiempo_op: String, action: String){
    fun registerReferido(names: String, last_names: String, sp_modality: String, sp_type_sale: String, sp_type_document: String, dni: String, direction: String, direction_referred: String, plan: String, operator: String, operator_cedent: String, number_movil: String, observation: String, listMultPartBody: ArrayList<MultipartBody.Part>, type_referrals: String, action: String) {
    //fun registerReferido(names: String, last_names: String, sp_modality: String, sp_type_sale: String, sp_type_document: String, dni: String, direction: String, direction_referred: String, plan: String, operator: String, operator_cedent: String, number_movil: String, observation: String, action: String) {
        Log.d("", "===================================== REGISTER ==========================================")
        Log.d("names", names)
        Log.d("last_names", last_names)
        Log.d("sp_modality ===> ", sp_modality)
        Log.d("sp_type_sale ===> ", sp_type_sale)
        Log.d("sp_type_document ===> ", sp_type_document)
        Log.d("dni", dni)
        Log.d("direction", direction)
        Log.d("direction_referred", direction_referred)
        Log.d("plan", plan)
        Log.d("operator ===> ", operator)
        Log.d("operator_cedent ===> ", operator_cedent)
        Log.d("number_movil", number_movil)
        Log.d("observation", observation)
        Log.d("listMultPartBody ===> ", listMultPartBody.toString())
        Log.d("action", action)

        val user_id = CurrentUser.getInstance(view!!.getContext()).getUser()!!.id

        val loadingView = view!!.getContext().indeterminateProgressDialog("Registrando referido...")
        loadingView.show()

        //userSessionDataSource.registerReferido(user_id, apaterno, amaterno, nombre, tipo_doc, dni, direccion, telefono, operador, tipo_servicio, parentesco, condicion_op, tiempo_op, action, object: DataSourceCallback {
        //userSessionDataSource.registerReferido(listMultPartBody, user_id, names, last_names,  sp_modality, sp_type_sale, sp_type_document, dni, direction, direction_referred, plan, operator, operator_cedent, number_movil, observation, action, object: DataSourceCallback {
        userSessionDataSource.registerReferido(user_id, names, last_names,  sp_modality, sp_type_sale, sp_type_document, dni, direction, direction_referred, plan, operator, operator_cedent, number_movil, observation, listMultPartBody, type_referrals, action, object: DataSourceCallback {
            override fun onSuccess(data: Object) {
                loadingView.dismiss()
                view!!.goToSuccess()
            }
            override fun onError(error: String) {
                loadingView.dismiss()
                view!!.showMessage(error)
                view!!.goToFailed()
            }
        })
    }

    fun registerImage(listMultPartBody: ArrayList<MultipartBody.Part>, action: String){
        val loadingView = view!!.getContext().indeterminateProgressDialog("Registrando referido...")
        loadingView.show()

        userSessionDataSourceImages.registerReferidoImages(listMultPartBody, action, object: DataSourceCallback {
            override fun onSuccess(data: Object) {
                loadingView.dismiss()
                view!!.goToSuccess()
            }
            override fun onError(error: String) {
                loadingView.dismiss()
                view!!.showMessage(error)
                view!!.goToFailed()
            }
        })
    }
}