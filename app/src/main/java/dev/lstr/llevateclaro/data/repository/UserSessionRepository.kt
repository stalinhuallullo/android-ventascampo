package dev.lstr.llevateclaro.data.repository

import dev.lstr.llevateclaro.data.datasource.UserSessionDataSource
import dev.lstr.llevateclaro.data.datasource.rest.RestUserSession

/**
 * Created by lesternr on 5/12/18.
 */
object UserSessionRepository {
    fun provideUserSession(): UserSessionDataSource {
        return RestUserSession()
    }
}