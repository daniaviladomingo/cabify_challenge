package com.cabify.challenge.data

import com.cabify.challenge.common.Mapper
import com.cabify.challenge.data.model.ProductData
import com.cabify.challenge.data.remote.IRemoteDataSource
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.repository.IRepository

class RepositoryImp(
    private val remoteDataSource: IRemoteDataSource,
    private val domainMapper: Mapper<ProductData, Product>
) : IRepository {
    override suspend fun getProducts(): List<Product> = domainMapper.map(remoteDataSource.getProducts())
}