package com.cabify.challenge.domain.interactors

import com.cabify.challenge.domain.interactors.base.BaseUseCase
import com.cabify.challenge.domain.repository.IShoppingCart
import kotlinx.coroutines.CoroutineDispatcher

class ClearShoppingCartUseCase(
    private val shoppingCart: IShoppingCart,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, Unit>(dispatcher) {
    override suspend fun block(param: Unit) {
        shoppingCart.clear()
    }
}