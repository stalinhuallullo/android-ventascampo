package dev.lstr.llevateclaro.presentation.presenter

import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.repository.BusinessRepository
import dev.lstr.llevateclaro.presentation.view.RegisterReferidoView
import org.jetbrains.anko.indeterminateProgressDialog

/**
 * Created by lesternr on 5/11/18.
 */
class RegisterReferidoPresenter {
    private var view: RegisterReferidoView? = null

    private val userSessionDataSource by lazy{
        BusinessRepository.provideBusiness()
    }

    fun addView(view: RegisterReferidoView){
        this.view = view
    }

    fun registerReferido(apaterno: String, amaterno: String, nombre: String, tipo_doc: String, dni: String, direccion: String, telefono: String, operador: String, tipo_servicio: String, parentesco: String, condicion_op: String, tiempo_op: String, action: String){
        val user_id = CurrentUser.getInstance(view!!.getContext()).getUser()!!.id

        val loadingView = view!!.getContext().indeterminateProgressDialog("Registrando referido...")
        loadingView.show()

        userSessionDataSource.registerReferido(user_id, apaterno, amaterno, nombre, tipo_doc, dni, direccion, telefono, operador, tipo_servicio, parentesco, condicion_op, tiempo_op, action, object: DataSourceCallback {
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