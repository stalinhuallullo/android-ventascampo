package dev.lstr.llevateclaro.presentation.view

import android.content.Context

/**
 * Created by lesternr on 5/11/18.
 */
interface LoginView {
    fun getContext(): Context
    fun goToHome()
    fun showMessage(message: String)
}