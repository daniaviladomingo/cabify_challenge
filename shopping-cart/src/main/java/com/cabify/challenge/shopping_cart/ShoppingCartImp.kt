package com.cabify.challenge.shopping_cart

import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.model.ProductShoppingCart
import com.cabify.challenge.domain.repository.IShoppingCart
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ShoppingCartImp : IShoppingCart {

    // Product & Units
    private val items = mutableMapOf<Product, Int>()

    private var emitProducts: (suspend () -> Unit)? = null

    override suspend fun addProduct(product: Product) {
        items[product] = items[product]?.plus(1) ?: 1
        emitProducts?.invoke()
    }

    override suspend fun removeProduct(product: Product) {
        items.remove(product)
        emitProducts?.invoke()
    }

    override suspend fun clear() {
        items.clear()
        emitProducts?.invoke()
    }

    override fun getProducts(): Flow<List<ProductShoppingCart>> = callbackFlow {
        if (emitProducts == null) {
            trySend(mapperToProductShoppingCartList())
        }
        emitProducts = {
            trySend(mapperToProductShoppingCartList())
        }
        awaitClose { cancel() }
    }

    private fun mapperToProductShoppingCartList(): List<ProductShoppingCart> =
        items.map { ProductShoppingCart(it.value, it.key) }
}