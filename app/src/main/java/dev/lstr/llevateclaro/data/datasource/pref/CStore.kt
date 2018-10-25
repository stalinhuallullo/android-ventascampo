package dev.lstr.llevateclaro.data.datasource.pref

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by lesternr on 5/16/18.
 */
class CStore(ctx: Context) {
    val DB: SharedPreferences

    init{
        DB = ctx.getSharedPreferences("sp_claro_llevate", Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String) {
        println("Guardando PMSTORE:$key=$value")
        val editor = DB.edit()
        editor.putString(key, value)

        editor.apply()
    }

    fun getString(key: String): String {
        return if (DB.contains(key)) {
            DB.getString(key, "")
        } else ""
    }

    fun saveLong(key: String, value: Long) {
        val editor = DB.edit()
        editor.putLong(key, value)

        editor.apply()
    }

    fun getLong(key: String): Long {
        return if (DB.contains(key)) {
            DB.getLong(key, 0)
        } else 0
    }
}