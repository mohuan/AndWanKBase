package com.weidingqiang.wanbasek.di.module

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.weidingqiang.rxqweklibrary.api.Api
import com.weidingqiang.rxqweklibrary.utils.HttpsUtils
import com.weidingqiang.wanbasek.BuildConfig
import com.weidingqiang.wanbasek.app.AppContext
import com.weidingqiang.wanbasek.di.qualifier.QBaseUrl
import com.weidingqiang.wanbasek.model.http.api.ApiConstants
import com.weidingqiang.wanbasek.model.http.api.QBaseApis
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 作者：weidingqiang on 2018/3/28 14:08
 * 邮箱：weidingqiang@163.com
 */
@Module
class HttpModule {

    @Singleton
    @Provides
    internal fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()


    @Singleton
    @Provides
    internal fun provideOkHttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Singleton
    @Provides
    internal fun provideClient(builder: OkHttpClient.Builder): OkHttpClient {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(loggingInterceptor)
        }
        val cacheFile = File(AppContext.instance.cacheDir, "cache")
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
        //增加头部信息
        val headerInterceptor = Interceptor { chain ->
            val build = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
            chain.proceed(build)
        }

        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext.instance.applicationContext))
        builder.cookieJar(cookieJar)


        // 设置https
        val sslParams = HttpsUtils.getSslSocketFactory(null, null, null)
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)

        builder.hostnameVerifier { s, sslSession -> true }

        //        builder.authenticator();
        builder.interceptors().add(Api.TokenInterceptor);

        builder.proxy(Proxy.NO_PROXY)

        //设置缓存
        builder.addInterceptor(Api.mRewriteCacheControlInterceptor)
        builder.addNetworkInterceptor(Api.mRewriteCacheControlInterceptor)
        builder.cache(cache)
        //设置超时
        builder.connectTimeout(Api.CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(Api.READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        return builder.build()
    }

    private fun createRetrofit(builder: Retrofit.Builder, client: OkHttpClient, url: String): Retrofit =
            builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(FastJsonConverterFactory.create())
                .build()

    @Singleton
    @Provides
    @QBaseUrl
    internal fun provideTestRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit = createRetrofit(builder, client, ApiConstants.HOST)

    @Singleton
    @Provides
    internal fun provideTestService(@QBaseUrl retrofit: Retrofit): QBaseApis = retrofit.create(QBaseApis::class.java!!)

}