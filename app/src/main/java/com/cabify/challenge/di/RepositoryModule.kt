package com.cabify.challenge.di

import com.cabify.challenge.common.Mapper
import com.cabify.challenge.data.RepositoryImp
import com.cabify.challenge.data.model.ProductData
import com.cabify.challenge.data.model.mapper.DomainMapper
import com.cabify.challenge.data.remote.IRemoteDataSource
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.repository.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun repositoryProvider(
        remoteDataSource: IRemoteDataSource,
        domainMapper: Mapper<ProductData, Product>,
    ): IRepository =
        RepositoryImp(
            remoteDataSource = remoteDataSource,
            domainMapper = domainMapper
        )

    @Provides
    @Singleton
    fun domainMapperProvider(): Mapper<ProductData, Product> = DomainMapper()
}