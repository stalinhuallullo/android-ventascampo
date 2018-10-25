package dev.lstr.llevateclaro.presentation.ui.fragment

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by lesternr on 5/5/18.
 */
abstract class BaseFragment : Fragment() {

    fun showMessage(view: View, message: String){
        view?.let {
            view ->
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }
}