package com.cabify.challenge.domain.repository

import com.cabify.challenge.domain.model.Product

interface ICheckout {
    fun calculateDiscount(units: Int, product: Product): Float
}