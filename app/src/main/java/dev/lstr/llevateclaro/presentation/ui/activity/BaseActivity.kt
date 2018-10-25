package dev.lstr.llevateclaro.presentation.ui.activity

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

abstract class BaseActivity : AppCompatActivity() {

    var rootView: View? = null

    override fun onStart() {
        super.onStart()
        addRootView()
    }

    fun showMessage(message: String){
        println("Will show:"+rootView)
        rootView?.let {
            view ->
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

    fun getContext(): Context{
        return this
    }

    abstract fun addRootView()
}