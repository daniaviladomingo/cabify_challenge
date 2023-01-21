package com.cabify.challenge.data_remote

import com.cabify.challenge.data_remote.model.ProductResponseApi
import retrofit2.http.GET

interface ApiRetrofit {
    @GET("palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json")
    suspend fun getProducts(): ProductResponseApi
}