package dev.lstr.llevateclaro.data.datasource.pref

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.lstr.llevateclaro.data.model.PublicidadE

class CurrentPublicidad(mcontex: Context) {
    private val pmStore: CStore
    private val gson: Gson

    private val KEY_LAST_TIME_ADS = "LAST_TIME_ADS"
    private val KEY_LAST_TIME_CHOOSE_BRAND = "LAST_TIME_CHOOSE_BRAND"

    init {
        pmStore = CStore(mcontex)
        gson = GsonBuilder().create()
    }

    fun savePublicidad(publicidad: PublicidadE) {
        var  KEY_MODULO : String? = publicidad.id_modulo
        val s = gson.toJson(publicidad)
        pmStore.saveString(KEY_MODULO!!, s)
    }

    fun getPublicidad(KEY_MODULO:String): PublicidadE? {
        val s = pmStore.getString(KEY_MODULO)
        val publicidad = gson.fromJson<PublicidadE>(s, PublicidadE::class.java)
        return publicidad
    }

    fun removePublicidad(KEY_MODULO:String) {
        pmStore.saveString(KEY_MODULO, "")
    }


    fun getLastTimeAds(): Long{
        return pmStore.getLong(KEY_LAST_TIME_ADS)
    }

    fun saveLastTimeAds(time: Long){
        pmStore.saveLong(KEY_LAST_TIME_ADS, time)
    }


    fun getLastTimeChooseBrand(): Long{
        return pmStore.getLong(KEY_LAST_TIME_CHOOSE_BRAND)
    }

    fun saveLastTimeChooseBrand(time: Long){
        pmStore.saveLong(KEY_LAST_TIME_CHOOSE_BRAND, time)
    }

    companion object {

        private var instance: CurrentPublicidad? = null

        fun getInstance(context: Context): CurrentPublicidad {
            if (instance == null) {
                instance = CurrentPublicidad(context)
            }
            return instance!!
        }
    }
}