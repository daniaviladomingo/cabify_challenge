package com.cabify.challenge.data_remote.model

data class ProductResponseApi(
    val products: List<ProductApi>,
)

data class ProductApi(
    val code: String,
    val name: String,
    val price: Float,
)