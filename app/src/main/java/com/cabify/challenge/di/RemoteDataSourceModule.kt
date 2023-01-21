package com.cabify.challenge.di

import com.cabify.challenge.BuildConfig
import com.cabify.challenge.common.Mapper
import com.cabify.challenge.data.model.ProductData
import com.cabify.challenge.data.remote.IRemoteDataSource
import com.cabify.challenge.data_remote.ApiRetrofit
import com.cabify.challenge.data_remote.RemoteDataSourceImp
import com.cabify.challenge.data_remote.model.ProductApi
import com.cabify.challenge.data_remote.model.mapper.DataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    @Singleton
    fun remoteDataSourceProvider(
        apiRetrofit : ApiRetrofit,
        dataMapper: Mapper<ProductApi, ProductData>
    ): IRemoteDataSource = RemoteDataSourceImp(
        apiRetrofit = apiRetrofit,
        dataMapper = dataMapper
    )

    @Provides
    @Singleton
    fun apiRetrofitProvider(okHttpClient: OkHttpClient, endPoint: String): ApiRetrofit =
        Retrofit.Builder()
            .baseUrl(endPoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiRetrofit::class.java)

    @Provides
    @Singleton
    fun okHttpClientProvider(
        timeout: Long,
        timeUnit: TimeUnit,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(timeout, timeUnit)
        .readTimeout(timeout, timeUnit)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun loggingInterceptorProvider(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    )

    @Provides
    @Singleton
    fun timeOutProvider(): Long = 30

    @Provides
    @Singleton
    fun timeUnits(): TimeUnit = TimeUnit.SECONDS

    @Provides
    @Singleton
    fun endPointProvider(): String = "https://gist.githubusercontent.com/"

    @Provides
    @Singleton
    fun dataMapperProvider(): Mapper<ProductApi, ProductData> = DataMapper()
}