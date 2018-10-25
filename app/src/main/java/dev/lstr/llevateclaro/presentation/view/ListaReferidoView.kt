package dev.lstr.llevateclaro.presentation.view

import android.content.Context
import dev.lstr.llevateclaro.data.model.ReferidoE
import java.util.ArrayList

/**
 * Created by lesternr on 5/11/18.
 */
interface ListaReferidoView {
    fun getContext(): Context
    fun showData(data: ArrayList<ReferidoE>)
    fun showErrorMessage(message: String)
}