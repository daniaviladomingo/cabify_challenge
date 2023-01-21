package com.cabify.challenge.shopping_cart

import com.cabify.challenge.domain.model.Product
import org.junit.Assert
import org.junit.Test

internal class CheckoutImpTest {
    private val checkout = CheckoutImp()

    @Test
    fun `check that TSHIRT has discount if units greater or equal 3`() {
        Assert.assertEquals(
            checkout.calculateDiscount(3, Product("TSHIRT", "Cabify T-Shirt", 20f)) , 3f
        )
    }

    @Test
    fun `check that TSHIRT has not discount if units smaller 3`() {
        Assert.assertEquals(
            checkout.calculateDiscount(2, Product("TSHIRT", "Cabify T-Shirt", 20f)) , 0f
        )
    }

    @Test
    fun `check that VOUCHER has discount if units greater or equal 2`() {
        Assert.assertEquals(
            checkout.calculateDiscount(2, Product("VOUCHER", "Cabify Voucher", 5f)) , 5f
        )
    }

    @Test
    fun `check that VOUCHER has not discount if units smaller 2`() {
        Assert.assertEquals(
            checkout.calculateDiscount(1, Product("VOUCHER", "Cabify Voucher", 5f)) , 0f
        )
    }

    @Test
    fun `check that MUG has not discount`() {
        Assert.assertEquals(
            checkout.calculateDiscount(10, Product("MUG", "Cabify Coffee Mug", 75f)) , 0f
        )
    }
}