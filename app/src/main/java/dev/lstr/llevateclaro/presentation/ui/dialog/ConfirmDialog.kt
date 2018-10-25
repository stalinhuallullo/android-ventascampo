package dev.lstr.llevateclaro.presentation.ui.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import dev.lstr.llevateclaro.R
import kotlinx.android.synthetic.main.dialog_confirm.view.*

/**
 * Created by lesternr on 5/5/18.
 */
class ConfirmDialog(context: Context) {

    private val builder:AlertDialog.Builder = AlertDialog.Builder(context)
    private val context:Context = context
    private var dlg: AlertDialog? = null

    fun showMessage(message: String, callback: Callback){
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null, false)

        view.tv_description.text = message

        view.btn_no.setOnClickListener {
            callback.onNo()
            dlg!!.dismiss()
        }
        view.btn_si.setOnClickListener {
            callback.onYes()
            dlg!!.dismiss()
        }

        builder.setCancelable(false)
        builder.setView(view)
        dlg = builder.create()
        dlg!!.show()
    }


    interface Callback{
        fun onYes()
        fun onNo()
    }
}