package com.cabify.challenge.domain.interactors

import com.cabify.challenge.domain.interactors.base.BaseUseCaseFlow
import com.cabify.challenge.domain.model.ProductCheckout
import com.cabify.challenge.domain.repository.ICheckout
import com.cabify.challenge.domain.repository.IShoppingCart
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCheckOutUseCase(
    private val shoppingCart: IShoppingCart,
    private val checkout: ICheckout,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, List<ProductCheckout>>(dispatcher) {
    override fun build(param: Unit): Flow<Result<List<ProductCheckout>>> =
        shoppingCart.getProducts().map { shoppingCart ->
            Result.success(shoppingCart.map { productShoppingCart ->
                ProductCheckout(
                    units = productShoppingCart.units,
                    priceWithDiscount = productShoppingCart.getPrice() - checkout.calculateDiscount(
                        units = productShoppingCart.units,
                        product = productShoppingCart.product
                    ),
                    product = productShoppingCart.product
                )
            })
        }
}