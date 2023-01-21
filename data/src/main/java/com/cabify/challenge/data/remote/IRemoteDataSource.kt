package com.cabify.challenge.data.remote

import com.cabify.challenge.data.model.ProductData

interface IRemoteDataSource {
    suspend fun getProducts(): List<ProductData>
}