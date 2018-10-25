package dev.lstr.llevateclaro.data.datasource.rest.api.service

import dev.lstr.llevateclaro.BuildConfig
import dev.lstr.llevateclaro.data.model.BusinessModel
import dev.lstr.llevateclaro.data.model.UserSessionModel
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by lesternr on 5/10/18.
 */
interface BusinessSessionService {
    @FormUrlEncoded
    @POST(BuildConfig._api+"/web/usre.php")
    fun registerReferido(@Field("action") action:String, @Field("ap") apaterno:String, @Field("apm") amaterno:String, @Field("nom") nombre:String, @Field("t_doc") tipo_doc:String, @Field("dn") dn:String, @Field("dir") dir:String, @Field("te") te:String, @Field("op") op:String, @Field("ts") tipo_servicio:String, @Field("pare") parent:String, @Field("us") user_id:String, @Field("condicion_op") condicion_op: String, @Field("tiempo_op") tiempo_op: String): Observable<BusinessModel.RegisterReferalResult>

    @GET(BuildConfig._api+"/web/usre.php")
    fun listaReferido(@Query("action") action:String, @Query("id") user_id:String): Observable<BusinessModel.ListReferalResult>

    @FormUrlEncoded
    @POST(BuildConfig._api+"/web/usre.php")
    fun detalleReferido(@Query("action") action:String, @Query("id") user_id:String, @Field("id") ref_id:String): Observable<BusinessModel.DetailReferalResult>

    @FormUrlEncoded
    @POST(BuildConfig._api+"/llevateloadmin/android_backend/publicidadcontent.php")
    fun detalleReferidoObs(@Field("action") action:String, @Field("id") user_id:String): Observable<BusinessModel.DetalleReferidoObs>
}