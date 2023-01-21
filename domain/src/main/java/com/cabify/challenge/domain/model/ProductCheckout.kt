package com.cabify.challenge.domain.model

import kotlin.math.min

data class ProductCheckout(
    val units: Int,
    val priceWithDiscount: Float,
    val product: Product,
) {
    fun priceWithoutDiscount(): Float = units * product.price
    fun finalPrice(): Float = min(priceWithoutDiscount(), priceWithDiscount)
    fun hasDiscount(): Boolean = priceWithDiscount < priceWithoutDiscount()
}