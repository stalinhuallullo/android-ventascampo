package dev.lstr.llevateclaro.data.datasource

/**
 * Created by lesternr on 5/10/18.
 */
interface DataSourceCallback {
    fun onSuccess(data: Object)
    fun onError(error:String)
}