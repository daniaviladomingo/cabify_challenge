package com.cabify.challenge.shopping_cart

import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.repository.ICheckout

class CheckoutImp : ICheckout {
    override fun calculateDiscount(units: Int, product: Product): Float =
        when (product.code) {
            "VOUCHER" -> discountVoucher(units, product.price)
            "TSHIRT" -> discountTShirt(units)
            else -> 0f
        }

    private fun discountVoucher(units: Int, price: Float): Float {
        var discount = 0f
        (1..units / 2).onEach { discount += price }
        return discount
    }

    private fun discountTShirt(units: Int): Float =
        if (units >= 3) {
            units.toFloat()
        } else
            0f

}