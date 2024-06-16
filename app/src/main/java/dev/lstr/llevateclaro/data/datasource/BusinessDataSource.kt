package dev.lstr.llevateclaro.data.datasource

import io.reactivex.disposables.Disposable
import okhttp3.MultipartBody

/**
 * Created by lesternr on 5/10/18.
 */
interface BusinessDataSource {
    val allCompositeDisposable: MutableList<Disposable>

    //fun registerReferido(user_id:String, apaterno: String, amaterno: String, nombre: String, tipo_doc: String, dni: String, direccion: String, telefono: String, operador: String, tipo_servicio: String, parentesco: String, condicion_op: String, tiempo_op: String, action: String, callback: DataSourceCallback)
    //fun registerReferido(listMultPartBody: ArrayList<MultipartBody.Part>, user_id:String, names: String, last_names: String, sp_modality: String, sp_type_sale: String, sp_type_document: String, dni: String, direction: String, direction_referred: String, plan: String, operator: String, operator_cedent: String, number_movil: String, observation: String, action: String, callback: DataSourceCallback)
    fun registerReferido(user_id: String, names: String, last_names: String, sp_modality: String, sp_type_sale: String, sp_type_document: String, dni: String, direction: String, direction_referred: String, plan: String, operator: String, operator_cedent: String, number_movil: String, observation: String, listMultPartBody: ArrayList<MultipartBody.Part>, type_referrals: String, action: String, callback: DataSourceCallback)

    fun registerReferidoImages(listMultPartBody: ArrayList<MultipartBody.Part>, action: String, callback: DataSourceCallback)

    fun listaReferido(user_id:String, action: String, callback: DataSourceCallback)

    fun detalleReferido(user_id:String, ref_id:String, action: String, callback: DataSourceCallback)

    fun detalleReferidoObs(id_mo:String, action: String, callback: DataSourceCallback)
}