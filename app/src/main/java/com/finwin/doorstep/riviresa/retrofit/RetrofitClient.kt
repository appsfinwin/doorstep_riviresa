package com.finwin.doorstep.riviresa.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private var instance: Retrofit? = null
    private var retrofitPostOfiice: Retrofit? = null
    private  var apiInterface: ApiInterface ? = null

    public fun RetrofitClientPostOfiice(): Retrofit? {
        if (retrofitPostOfiice == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            retrofitPostOfiice = Retrofit.Builder()
                .baseUrl("https://globalvalues.digicob.in/api/postoffice/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
            return  retrofitPostOfiice
    }

    public fun getApi(): ApiInterface? {
        if (apiInterface == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            apiInterface = Retrofit.Builder()
                //.baseUrl("http://192.168.0.221:169/")//local
                //.baseUrl("http://doorsteplocal.digicob.in/")
                .baseUrl("http://doorstep.riviresa.digicob.in:2524/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiInterface::class.java)
        }
        return apiInterface
    }

}