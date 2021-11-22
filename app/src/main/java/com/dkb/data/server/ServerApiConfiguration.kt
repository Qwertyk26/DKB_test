package com.dkb.data.server

import android.content.Context
import android.util.Log
import com.dkb.R
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.TimeUnit

class ServerApiConfiguration {

    private val gson by lazy {
        GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create()
    }

    fun configureServerApi(context: Context): ServerApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor(Logger())
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.BASE_URL))
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ServerApi::class.java)
    }

    private inner class Logger : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            try {
                if (message.isEmpty()) return
                val prettyPrintJson = gson.toJson(JsonParser.parseString(message))

                Log.d("JSON", prettyPrintJson)
            } catch (m: JsonSyntaxException) {
                Log.d("Message", message)
            }
        }
    }
}