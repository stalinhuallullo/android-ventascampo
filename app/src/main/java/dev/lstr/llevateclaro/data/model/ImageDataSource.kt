package dev.lstr.llevateclaro.data.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dev.lstr.llevateclaro.presentation.util.ScaleImageView
import java.lang.Exception

class ImageDataSource {
    interface ImageDatasourceCallBack {
        fun onSuccess(bitmap: Bitmap)
        fun onError(e: Exception?)
    }


    companion object {
        fun getImageFromSource(url: String, context: Context, callback: ImageDatasourceCallBack) {
            //var urlImage: String = "http://i.imgur.com/DvpvklR.png";
            if (url == "") {
                callback.onError(throw Exception("URL is empty") as Throwable)
            }
            var imgInnerDatasource = ScaleImageView(context)
            try {
                Picasso.get().load(url)
                        .networkPolicy(NetworkPolicy.OFFLINE)?.into(imgInnerDatasource, object : Callback {
                            override fun onSuccess() {
                                try {
                                    val bitmap = (imgInnerDatasource.getDrawable() as BitmapDrawable).bitmap
                                    callback.onSuccess(bitmap)
                                    Log.v("Picasso", "LOAD FROM CACHE")
                                } catch (e: Exception) {
                                    callback.onError(e)
                                    Log.v("Picasso", "ERROR LOADING FROM URL-" + e?.message.toString())
                                }
                            }

                            override fun onError(e: Exception?) {
                                Picasso.get().load(url)?.into(imgInnerDatasource, object : Callback {
                                    override fun onSuccess() {
                                        try {
                                            val bitmap = (imgInnerDatasource.getDrawable() as BitmapDrawable).bitmap
                                            callback.onSuccess(bitmap)
                                            Log.v("Picasso", "SUCCESS LOADING FROM URL")
                                        } catch (e: Exception) {
                                            callback.onError(e)
                                            Log.v("Picasso", "ERROR LOADING FROM URL-" + e?.message.toString())
                                        }
                                    }

                                    override fun onError(e: Exception?) {
                                        callback.onError(e)
                                        Log.v("Picasso", "ERROR LOADING FROM URL-" + e?.message.toString())
                                    }

                                });
                            }
                        })
            } catch (e: Exception) {
                callback.onError(e)
                Log.v("Picasso", "ERROR LOADING FROM URL-" + e?.message.toString())
            }
        }

        fun getImageFromSource(url: String, context: Context) {
            getImageFromSource(url, context, object : ImageDatasourceCallBack {
                override fun onSuccess(bitmap: Bitmap) {
                    Log.v("Picasso", "SUCCESS NO CALLBACK")
                }

                override fun onError(e: Exception?) {
                    Log.v("Picasso", "ERROR NO CALLBACK FROM URL-" + e?.message.toString())
                }


            })
        }
    }


}

