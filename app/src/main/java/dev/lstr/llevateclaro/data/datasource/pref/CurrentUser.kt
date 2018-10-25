package dev.lstr.llevateclaro.data.datasource.pref

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.lstr.llevateclaro.data.model.UserE

/**
 * Created by lesternr on 5/16/18.
 */

class CurrentUser(context: Context) {
    private val pmStore: CStore
    private val gson: Gson
    private val KEY_USER = "PREF_KEY_USER"
    private val KEY_USER_BUSINESS_SELECTED = "PREF_KEY_USER_BUSINESS_SELECTED"
    private val KEY_TOKEN = "PREF_KEY_TOKEN"

    init {
        pmStore = CStore(context)
        gson = GsonBuilder().create()
    }

    fun saveUser(user: UserE) {
        val s = gson.toJson(user)
        pmStore.saveString(KEY_USER, s)
    }

    fun getUser(): UserE? {
        val s = pmStore.getString(KEY_USER)
        val user = gson.fromJson<UserE>(s, UserE::class.java)
        return user
    }

    fun saveToken(token: String) {
        pmStore.saveString(KEY_TOKEN, token)
    }

    fun getToken(): String? {
        val tkn = pmStore.getString(KEY_TOKEN)
        return tkn
    }

    fun removeUser() {
        pmStore.saveString(KEY_USER, "")
    }

    fun saveBusiness(value: String) {
        pmStore.saveString(KEY_USER_BUSINESS_SELECTED, value)
    }

    fun getBusiness(): String{
        val business = pmStore.getString(KEY_USER_BUSINESS_SELECTED)
        return business
    }

    companion object {

        private var instance: CurrentUser? = null

        fun getInstance(context: Context): CurrentUser {
            if (instance == null) {
                instance = CurrentUser(context)
            }
            return instance!!
        }
    }

}