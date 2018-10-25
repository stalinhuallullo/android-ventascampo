package dev.lstr.llevateclaro.data.model

import java.util.ArrayList

/**
 * Created by lesternr on 5/10/18.
 */
object UserSessionModel {
    data class LoginResult(val data: ArrayList<UserE>, val error: String)
    data class RegisterResult(val mensaje: String, val error: String)
    data class RecoverResult(val data: String, val error: String)
    data class Publicidad(val data: ArrayList<PublicidadE>, val error: String)
    data class TokenResult(val data: String, val error: String)
}