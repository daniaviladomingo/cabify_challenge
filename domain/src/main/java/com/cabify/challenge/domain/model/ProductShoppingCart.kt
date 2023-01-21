package com.cabify.challenge.domain.model

data class ProductShoppingCart(
    val units: Int,
    val product: Product,
){
    fun getPrice(): Float = units * product.price
}