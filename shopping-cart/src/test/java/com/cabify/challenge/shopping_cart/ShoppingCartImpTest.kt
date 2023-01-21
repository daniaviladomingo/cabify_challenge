package com.cabify.challenge.shopping_cart

import app.cash.turbine.test
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.model.ProductShoppingCart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ShoppingCartImpTest {
    private val shoppingCart = ShoppingCartImp()

    private val productFlow = shoppingCart.getProducts()

    @Test
    fun `add product to cart`() = runTest {
        val productCode = "MUG"
        val productToAdd = Product(productCode, "Cabify Coffee Mug", 7.5f)

        shoppingCart.addProduct(productToAdd)

        productFlow.test {
            val shoppingCartList = awaitItem()
            val shoppingCartProduct = getShoppingCartProduct(productCode, shoppingCartList)
            Assert.assertEquals(shoppingCartProduct?.product, productToAdd)
        }
    }

    @Test
    fun `increment units product when add to cart`() = runTest {
        val productCode = "MUG"
        val productToAdd = Product(productCode, "Cabify Coffee Mug", 7.5f)

        shoppingCart.addProduct(productToAdd)

        productFlow.test {
            var shoppingCartList = awaitItem()
            var shoppingCartProduct = getShoppingCartProduct(productCode, shoppingCartList)
            Assert.assertEquals(shoppingCartProduct?.units, 1)

            shoppingCart.addProduct(productToAdd)
            shoppingCartList = awaitItem()
            shoppingCartProduct = getShoppingCartProduct(productCode, shoppingCartList)
            Assert.assertEquals(shoppingCartProduct?.units, 2)
        }
    }

    @Test
    fun `remove product from cart`() = runTest {
        val productCode = "MUG"
        val productToAdd = Product(productCode, "Cabify Coffee Mug", 7.5f)
        shoppingCart.addProduct(productToAdd)

        productFlow.test {
            var shoppingCartList = awaitItem()
            var shoppingCartProduct = getShoppingCartProduct(productCode, shoppingCartList)
            Assert.assertEquals(shoppingCartProduct?.product, productToAdd)

            shoppingCart.removeProduct(productToAdd)

            shoppingCartList = awaitItem()
            shoppingCartProduct = getShoppingCartProduct(productCode, shoppingCartList)
            Assert.assertEquals(shoppingCartProduct?.product, null)
        }
    }

    @Test
    fun `clear cart`() = runTest {
        val productToAdd = Product("MUG", "Cabify Coffee Mug", 7.5f)
        shoppingCart.addProduct(productToAdd)

        productFlow.test {
            Assert.assertTrue(awaitItem().isNotEmpty())

            shoppingCart.clear()
            Assert.assertTrue(awaitItem().isEmpty())
        }
    }

    private fun getShoppingCartProduct(
        code: String,
        shoppingCartList: List<ProductShoppingCart>,
    ): ProductShoppingCart? =
        shoppingCartList.find { it.product.code == code }
}