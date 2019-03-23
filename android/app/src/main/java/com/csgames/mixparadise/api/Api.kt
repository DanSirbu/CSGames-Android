package com.csgames.mixparadise.api

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.MessageDigest

object Api {
    private lateinit var retrofit: Retrofit

    //From https://rosettacode.org/wiki/SHA-1#Kotlin
    fun sha1(input: String) : String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-1")
        val digest = md.digest(bytes)

        var returnString = ""
        for (byte in digest) {
            returnString += "%02x".format(byte)
        }

        return returnString
    }

    fun init(context: Context) {
        val okHttpClient = OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024))
            .addInterceptor(
                object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        var key = "asdasdsdsfds";
                        var authorization_raw = "csgames19-" + (System.currentTimeMillis() / 60).toString() + key
                        var authorizationCode = sha1(authorization_raw)

                        val request = chain.request().newBuilder()
                            .addHeader("Team", "Citation Needed")
                            .addHeader("Authorization", authorizationCode)
                            .build()

                        return chain.proceed(request)
                    }
                }
            )
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://mirego-csgames19.herokuapp.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val drinkService: DrinkService by lazy { retrofit.create(DrinkService::class.java) }
}
