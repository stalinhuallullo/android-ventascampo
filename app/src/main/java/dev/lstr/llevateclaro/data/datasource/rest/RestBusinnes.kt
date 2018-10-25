package dev.lstr.llevateclaro.data.datasource.rest

import com.crashlytics.android.Crashlytics
import dev.lstr.llevateclaro.data.datasource.BusinessDataSource
import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.rest.api.ApiService
import dev.lstr.llevateclaro.data.datasource.rest.api.service.BusinessSessionService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lesternr on 5/10/18.
 */

class RestBusinnes : BusinessDataSource {
    override val allCompositeDisposable: MutableList<Disposable>
        get() = arrayListOf()

    val usService by lazy {
        ApiService.init().create(BusinessSessionService::class.java)
    }

    override fun registerReferido(user_id:String, apaterno: String, amaterno: String, nombre: String, tipo_doc: String, dni: String, direccion: String, telefono: String, operador: String, tipo_servicio: String, parentesco: String, condicion_op: String, tiempo_op: String, action: String, callback: DataSourceCallback) {
        val disposable = usService.registerReferido(action, apaterno, amaterno, nombre, tipo_doc, dni, direccion, telefono, operador, tipo_servicio, parentesco, user_id, condicion_op, tiempo_op)
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

    override fun listaReferido(user_id:String, action: String, callback: DataSourceCallback) {
        val disposable = usService.listaReferido(action, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            if(result.data != null){
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