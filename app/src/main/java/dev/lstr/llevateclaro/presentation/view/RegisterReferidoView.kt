package dev.lstr.llevateclaro.presentation.view

import android.content.Context

/**
 * Created by lesternr on 5/11/18.
 */
interface RegisterReferidoView {
    fun getContext(): Context
    fun goToSuccess()
    fun goToFailed()
    fun showMessage(message: String)
}