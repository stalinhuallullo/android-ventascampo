package dev.lstr.llevateclaro.data.datasource.rest

import com.crashlytics.android.Crashlytics
import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.UserSessionDataSource
import dev.lstr.llevateclaro.data.datasource.rest.api.ApiService
import dev.lstr.llevateclaro.data.datasource.rest.api.service.UserSessionService
import dev.lstr.llevateclaro.data.model.NewCompanyE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lesternr on 5/10/18.
 */

class RestUserSession: UserSessionDataSource{

    override val allCompositeDisposable: MutableList<Disposable>
        get() = arrayListOf()

    val usService by lazy {
        ApiService.init().create(UserSessionService::class.java)
    }

    override fun login(user: String, password: String, callback: DataSourceCallback){
        val action = "login"
        val disposable = usService.login(user, password, action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            if(result.data != null){
                                callback.onSuccess(result.data[0] as Object)
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

    override fun recuperarPass(email: String, callback: DataSourceCallback){
        val action = "rc"
        val disposable = usService.recuperarPass(email, action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            if(result.data != null){
                                callback.onSuccess(true as Object)
                            }else if(result.error != null){
                                callback.onSuccess(false as Object)
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
    override fun publicidad_dia(user_id: String,modulo:String,movil:String, callback: DataSourceCallback) {
        val action = "publicidad"
        val disposable = usService.publicidad_dia(action,user_id,modulo,movil)
//        val disposable = usService.publicidad_dia(user_id, action)
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

    override fun setToken(user_id:String, token:String, callback: DataSourceCallback){
        val action = "settoken"
        val disposable = usService.setToken(action, user_id, token)
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
}