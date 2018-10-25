package dev.lstr.llevateclaro.presentation.ui.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import dev.lstr.llevateclaro.R

/**
 * Created by lesternr on 5/5/18.
 */
class MessageDialog(val context: Context) {

    val builder:AlertDialog.Builder = AlertDialog.Builder(context)
    var dlg: AlertDialog? = null

    fun showMessage(message: String, callback: Callback){
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_message, null, false)

        view.findViewById<AppCompatTextView>(R.id.tv_description).setText(message)
        val btn_aceptar = view.findViewById<AppCompatButton>(R.id.btn_aceptar)
        btn_aceptar.setOnClickListener {
            callback.onOk()
            dlg!!.dismiss()
        }

        builder.setCancelable(false)
        builder.setView(view)
        dlg = builder.create()
        dlg!!.show()
    }


    interface Callback{
        fun onOk()
    }
}