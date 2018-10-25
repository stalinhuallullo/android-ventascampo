package dev.lstr.llevateclaro.data.datasource

import io.reactivex.disposables.Disposable

/**
 * Created by lesternr on 5/10/18.
 */
interface BusinessDataSource {
    val allCompositeDisposable: MutableList<Disposable>

    fun registerReferido(user_id:String, apaterno: String, amaterno: String, nombre: String, tipo_doc: String, dni: String, direccion: String, telefono: String, operador: String, tipo_servicio: String, parentesco: String, condicion_op: String, tiempo_op: String, action: String, callback: DataSourceCallback)

    fun listaReferido(user_id:String, action: String, callback: DataSourceCallback)

    fun detalleReferido(user_id:String, ref_id:String, action: String, callback: DataSourceCallback)

    fun detalleReferidoObs(id_mo:String, action: String, callback: DataSourceCallback)
}