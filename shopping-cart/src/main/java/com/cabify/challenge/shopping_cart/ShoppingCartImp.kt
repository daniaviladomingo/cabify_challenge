package com.cabify.challenge.shopping_cart

import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.model.ProductShoppingCart
import com.cabify.challenge.domain.repository.IShoppingCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ShoppingCartImp : IShoppingCart {

    // Product & Units
    private val items = MutableStateFlow(emptyMap<Product, Int>())

    override suspend fun addProduct(product: Product) {
        val currentItems = items.value.toMutableMap()
        currentItems[product] = currentItems[product]?.plus(1) ?: 1
        items.value = currentItems
    }

    override suspend fun removeProduct(product: Product) {
        val currentItems = items.value.toMutableMap()
        items.value = currentItems.apply { remove(product) }
    }

    override suspend fun clear() {
        items.value = emptyMap()
    }

    override fun getProducts(): Flow<List<ProductShoppingCart>> =
        items.map { items.value.map { ProductShoppingCart(it.value, it.key) } }
}