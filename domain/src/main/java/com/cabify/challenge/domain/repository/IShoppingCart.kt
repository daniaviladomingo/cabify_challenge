package com.cabify.challenge.domain.repository

import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.model.ProductShoppingCart
import kotlinx.coroutines.flow.Flow

interface IShoppingCart {
    suspend fun addProduct(product: Product)
    suspend fun removeProduct(product: Product)
    suspend fun clear()
    fun getProducts(): Flow<List<ProductShoppingCart>>
}