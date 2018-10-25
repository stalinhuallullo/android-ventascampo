package dev.lstr.llevateclaro.presentation.view

import android.content.Context
import dev.lstr.llevateclaro.data.model.DetalleReferidoE

/**
 * Created by lesternr on 5/11/18.
 */
interface DetalleReferidoView {
    fun getContext(): Context
    fun showData(data: DetalleReferidoE)
    fun showMessage(message: String)
}