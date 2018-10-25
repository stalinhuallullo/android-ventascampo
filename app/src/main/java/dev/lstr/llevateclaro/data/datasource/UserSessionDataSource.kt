package dev.lstr.llevateclaro.data.datasource

import io.reactivex.disposables.Disposable

/**
 * Created by lesternr on 5/10/18.
 */
interface UserSessionDataSource {
    val allCompositeDisposable: MutableList<Disposable>

    fun login(user:String, password:String, callback: DataSourceCallback)
    fun recuperarPass(email:String, callback: DataSourceCallback)
    fun publicidad_dia(user_id:String,modulo:String,movil:String, callback: DataSourceCallback)
    fun setToken(user_id:String, token:String, callback: DataSourceCallback)

}