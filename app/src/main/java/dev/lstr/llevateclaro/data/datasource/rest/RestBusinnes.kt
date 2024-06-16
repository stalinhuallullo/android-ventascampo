package dev.lstr.llevateclaro.data.datasource.rest

import android.util.Log
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import dev.lstr.llevateclaro.data.datasource.BusinessDataSource
import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.rest.api.ApiService
import dev.lstr.llevateclaro.data.datasource.rest.api.service.BusinessSessionService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

/**
 * Created by lesternr on 5/10/18.
 */

class RestBusinnes : BusinessDataSource {
    override val allCompositeDisposable: MutableList<Disposable>
        get() = arrayListOf()

    val usService by lazy {
        ApiService.init().create(BusinessSessionService::class.java)
    }

    //override fun registerReferido(user_id:String, apaterno: String, amaterno: String, nombre: String, tipo_doc: String, dni: String, direccion: String, telefono: String, operador: String, tipo_servicio: String, parentesco: String, condicion_op: String, tiempo_op: String, action: String, callback: DataSourceCallback) {
    //override fun registerReferido(listMultPartBody: ArrayList<MultipartBody.Part>, user_id:String, names: String, last_names: String, sp_modality: String, sp_type_sale: String, sp_type_document: String, dni: String, direction: String, direction_referred: String, plan: String, operator: String, operator_cedent: String, number_movil: String, observation: String, action: String, callback: DataSourceCallback) {
    override fun registerReferido(user_id:String, names: String, last_names: String, sp_modality: String, sp_type_sale: String, sp_type_document: String, dni: String, direction: String, direction_referred: String, plan: String, operator: String, operator_cedent: String, number_movil: String, observation: String, listMultPartBody: ArrayList<MultipartBody.Part>, type_referrals: String, action: String, callback: DataSourceCallback) {

        //val disposable = usService.registerReferido(listMultPartBody, user_id, action, names, last_names, sp_modality, sp_type_sale, sp_type_document, dni, direction, direction_referred, plan, operator, operator_cedent, number_movil, observation)
        val disposable = usService.registerReferido(user_id, type_referrals, action, names, last_names, sp_modality, sp_type_sale, sp_type_document, dni, direction, direction_referred, plan, operator, operator_cedent, number_movil, observation, listMultPartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            if(result.mensaje != null){
                                callback.onSuccess(result.mensaje as Object)
                            }else if(result.error != null){
                                callback.onError(result.error)
                            }else{
                                callback.onError("Al parecer hubo un error, intente mas tarde.")
                            }
                        },
                        {
                            error ->
                            callback.onError("Al parecer hubo un error, intente despues.")
                            Crashlytics.logException(error)
                        }
                )
        allCompositeDisposable.add(disposable)
    }

    override fun registerReferidoImages(listMultPartBody: ArrayList<MultipartBody.Part>, action: String, callback: DataSourceCallback) {
        Log.d("", "===================================== registerReferido API ==========================================")
        Log.d("action", action)
        Log.d("listMultPartBody", listMultPartBody.toString())
        val disposable = usService.uploadImage(listMultPartBody, action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            Log.d("result", result.toString())
                            if(result.mensaje != null){
                                Log.d("RES_SUCCESS", result.mensaje.toString())
                                callback.onSuccess(result.mensaje.toString() as Object)
                            }else if(result.error != null){
                                Log.d("RES_ERROR", result.error)
                                callback.onError(result.error)
                            }else{
                                Log.d("WITHOUT_RES", "No se sabe lo que paso")
                                callback.onError("Al parecer hubo un error, intente mas tarde.")
                            }
                        },
                        {
                            error ->
                            callback.onError("Al parecer hubo un error, intente despues.")
                            Crashlytics.logException(error)
                            Log.d("WITHOUT_RES_DEFAULT", "Error for default => " + error.toString())
                        }
                )

        Log.d("SEND", "Se envian los datos")
        //Log.d("disposable" , disposable.toString())
        //allCompositeDisposable.add(disposable)
        Log.d("SEND FINAL", "Se finalizo de enviar los datos")
    }

    override fun listaReferido(user_id:String, action: String, callback: DataSourceCallback) {
        Log.d("LISTAREFERIDO", "user_id => " + user_id + "  |  action => " + action)

        val disposable = usService.listaReferido(action, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            if(result.data != null){
                                Log.d("DATA result.data", result.data.toString())
                                callback.onSuccess(result.data as Object)
                            }else if(result.error != null){
                                callback.onError(result.error)
                            }else{
                                callback.onError("Al parecer hubo un error, intente mas tarde.")
                            }
                        },
                        {
                            error ->
                            callback.onError("Al parecer hubo un error, intente despues.")
                            Crashlytics.logException(error)
                        }
                )
        allCompositeDisposable.add(disposable)
    }

    override fun detalleReferido(user_id:String, ref_id:String, action: String, callback: DataSourceCallback) {
        val disposable = usService.detalleReferido(action, user_id, ref_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            if(result.data != null){
                                callback.onSuccess(result.data.get(0) as Object)
                            }else if(result.error != null){
                                callback.onError(result.error)
                            }else{
                                callback.onError("Al parecer hubo un error, intente mas tarde.")
                            }
                        },
                        {
                            error ->
                            callback.onError("Al parecer hubo un error, intente despues.")
                            Crashlytics.logException(error)
                        }
                )
        allCompositeDisposable.add(disposable)
    }

    override fun detalleReferidoObs(id_mo:String, action: String, callback: DataSourceCallback) {
        val disposable = usService.detalleReferidoObs(action, id_mo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            if(result.data != null){
                                if(result.data.isNotEmpty())
                                    callback.onSuccess(result.data.get(0) as Object)
                                else callback.onError("Lo sentimos, hubo un error con el servicio. Intente despues.")
                            }else if(result.error != null){
                                callback.onError(result.error)
                            }else{
                                callback.onError("Al parecer hubo un error, intente mas tarde.")
                            }
                        },
                        {
                            error ->
                            callback.onError("Al parecer hubo un error, intente despues.")
                            Crashlytics.logException(error)
                        }
                )
        allCompositeDisposable.add(disposable)
    }


}