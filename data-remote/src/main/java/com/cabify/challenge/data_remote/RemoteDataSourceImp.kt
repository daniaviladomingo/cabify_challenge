package com.cabify.challenge.data_remote

import com.cabify.challenge.common.Mapper
import com.cabify.challenge.data.model.ProductData
import com.cabify.challenge.data.remote.IRemoteDataSource
import com.cabify.challenge.data_remote.model.ProductApi

class RemoteDataSourceImp(
    private val apiRetrofit: ApiRetrofit,
    private val dataMapper: Mapper<ProductApi, ProductData>,
) : IRemoteDataSource {
    override suspend fun getProducts(): List<ProductData> = dataMapper.map(apiRetrofit.getProducts().products)
}