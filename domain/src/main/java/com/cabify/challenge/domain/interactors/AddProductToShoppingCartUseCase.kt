package com.cabify.challenge.domain.interactors

import com.cabify.challenge.domain.interactors.base.BaseUseCase
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.repository.IShoppingCart
import kotlinx.coroutines.CoroutineDispatcher

class AddProductToShoppingCartUseCase(
    private val shoppingCart: IShoppingCart,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Product, Unit>(dispatcher) {
    override suspend fun block(param: Product) {
        shoppingCart.addProduct(param)
    }
}