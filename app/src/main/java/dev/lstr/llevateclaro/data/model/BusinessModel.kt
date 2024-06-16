package dev.lstr.llevateclaro.data.model

import java.util.ArrayList

/**
 * Created by lesternr on 5/10/18.
 */
object BusinessModel {
    data class RegisterReferalResult(val mensaje: String, val error: String)
    data class RegisterReferalResultImage(val mensaje: String, val error: String)
    data class ListReferalResult(val data: ArrayList<ReferidoE>, val error: String)
    data class DetailReferalResult(val data: ArrayList<DetalleReferidoE>, val error: String)
    data class ResumenAcumuladoResult(val data: AcumuladoE, val error: String)
    data class SolicitarCobroResult(val mensaje: String, val error: String)
    data class DetalleReferidoObs(val data: ArrayList<DetalleReferidoObsE>, val error: String)
}