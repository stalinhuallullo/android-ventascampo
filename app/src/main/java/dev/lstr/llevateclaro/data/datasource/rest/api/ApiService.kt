package dev.lstr.llevateclaro.data.datasource.rest.api

import dev.lstr.llevateclaro.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by lesternr on 5/10/18.
 */
object ApiService {
    fun init():Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .baseUrl(BuildConfig.base_url)
                .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
//                .addInterceptor(object :Interceptor{
//                    chain ->
//                    val original = chain.request()
//                    val request = original.newBuilder()
//                            .method(original.method(), original.body())
//                            .build()
//
//                    return chain.proceed(request)
//                })
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
    }
}