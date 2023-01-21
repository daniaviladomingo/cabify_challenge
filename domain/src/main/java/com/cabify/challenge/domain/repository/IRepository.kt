package com.cabify.challenge.domain.repository

import com.cabify.challenge.domain.model.Product

interface IRepository {
    suspend fun getProducts(): List<Product>
}