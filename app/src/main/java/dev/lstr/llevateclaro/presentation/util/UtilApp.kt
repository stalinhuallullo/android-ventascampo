package dev.lstr.llevateclaro.presentation.util

import android.content.Context
import android.text.TextUtils
import java.util.*


/**
 * Created by lesternr on 5/6/18.
 */
class UtilApp(context: Context) {

    companion object {
        private var instance: UtilApp? = null

        fun getInstance(context: Context): UtilApp{
            if(instance == null){
                instance = UtilApp(context)
            }
            return instance!!
        }
    }

    fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isDocumentValid(doc: String): Boolean {
        return doc.length > 6
    }
    fun isPasswordValid(pass: String): Boolean {
        return pass.length > 1
    }

    fun getDiffYears(first: Calendar,last: Calendar): Int {
        var diff = last.get(Calendar.YEAR) - first.get(Calendar.YEAR)
        if (first.get(Calendar.MONTH) > last.get(Calendar.MONTH) ||
            (first.get(Calendar.MONTH) == last.get(Calendar.MONTH) && first.get(Calendar.DATE) > last.get(Calendar.DATE))) {
            diff--
        }
        return diff
    }
}