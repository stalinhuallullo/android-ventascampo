package dev.lstr.llevateclaro.data.datasource.rest.api.service

import dev.lstr.llevateclaro.BuildConfig
import dev.lstr.llevateclaro.data.model.BusinessModel
import dev.lstr.llevateclaro.data.model.UserSessionModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by lesternr on 5/10/18.
 */
interface BusinessSessionService {

    //@FormUrlEncoded
    @Multipart
    @POST(BuildConfig._api+"/web/registerReferred.php")
    fun registerReferido(@Part("user_id") user_id:String, @Part("type_referrals") type_referrals:String, @Part("action") action:String, @Part("names") names:String, @Part("last_names") last_names:String, @Part("sp_modality") sp_modality:String, @Part("sp_type_sale") sp_type_sale:String, @Part("sp_type_document") sp_type_document:String, @Part("dni") dni:String, @Part("direction") direction:String, @Part("direction_referred") direction_referred:String, @Part("plan") plan:String, @Part("operator") operator:String, @Part("operator_cedent") operator_cedent:String, @Part("number_movil") number_movil:String, @Part("observation") observation: String, @Part file:ArrayList<MultipartBody.Part>): Observable<BusinessModel.RegisterReferalResult>
    //fun registerReferido(@Part file:ArrayList<MultipartBody.Part>, @Part("user_id") user_id:String, @Part("action") action:String, @Part("names") names:String, @Part("last_names") last_names:String, @Part("sp_modality") sp_modality:String, @Part("sp_type_sale") sp_type_sale:String, @Part("sp_type_document") sp_type_document:String, @Part("dni") dni:String, @Part("direction") direction:String, @Part("direction_referred") direction_referred:String, @Part("plan") plan:String, @Part("operator") operator:String, @Part("operator_cedent") operator_cedent:String, @Part("number_movil") number_movil:String, @Part("observation") observation: String): Observable<BusinessModel.RegisterReferalResult>
    //fun registerReferido(@Field("user_id") user_id:String, @Field("action") action:String, @Field("names") names:String, @Field("last_names") last_names:String, @Field("sp_modality") sp_modality:String, @Field("sp_type_sale") sp_type_sale:String, @Field("sp_type_document") sp_type_document:String, @Field("dni") dni:String, @Field("direction") direction:String, @Field("direction_referred") direction_referred:String, @Field("plan") plan:String, @Field("operator") operator:String, @Field("operator_cedent") operator_cedent:String, @Field("number_movil") number_movil:String, @Field("observation") observation: String): Observable<BusinessModel.RegisterReferalResult>


    @Multipart
    @POST(BuildConfig._api+"/web/registerReferred.php")
    fun uploadImage(@Part file:ArrayList<MultipartBody.Part>, @Part("action") action: String): Observable<BusinessModel.RegisterReferalResultImage>

    @GET(BuildConfig._api+"/web/referralsReferred.php")
    fun listaReferido(@Query("action") action:String, @Query("id") user_id:String): Observable<BusinessModel.ListReferalResult>

    @FormUrlEncoded
    //@POST(BuildConfig._api+"/web/usre.php")
    @POST(BuildConfig._api+"/web/referralsReferred.php")
    fun detalleReferido(@Query("action") action:String, @Query("id") user_id:String, @Field("id") ref_id:String): Observable<BusinessModel.DetailReferalResult>

    @FormUrlEncoded
    @POST(BuildConfig._api+"/llevateloadmin/android_backend/publicidadcontent.php")
    fun detalleReferidoObs(@Field("action") action:String, @Field("id") user_id:String): Observable<BusinessModel.DetalleReferidoObs>
}
