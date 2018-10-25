package dev.lstr.llevateclaro.data.datasource.rest.api.service

import dev.lstr.llevateclaro.BuildConfig
import dev.lstr.llevateclaro.data.model.UserSessionModel
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by lesternr on 5/10/18.
 */
interface UserSessionService {

    @FormUrlEncoded
    @POST(BuildConfig._api+"/web/usweb.php")
    fun login(@Field("usu") user:String, @Field("pas") password:String, @Field("action") action:String): Observable<UserSessionModel.LoginResult>

    @GET(BuildConfig._api+"/web/usweb.php")
    fun recuperarPass(@Query("id") user:String, @Query("action") action:String): Observable<UserSessionModel.RecoverResult>

    @FormUrlEncoded
    @POST(BuildConfig._api+"/llevateloadmin/android_backend/publicidadcontent.php")
    fun publicidad_dia(@Field("action") action:String, @Field("id") user_id:String,@Field("modulo") modulo:String,@Field("movil") movil:String): Observable<UserSessionModel.Publicidad>


    @GET(BuildConfig._api+"/llevateloadmin/android_backend/android_services2.php")
    fun setToken(@Query("action") action:String, @Query("us") user:String, @Query("token") token:String): Observable<UserSessionModel.TokenResult>
}