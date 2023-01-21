package com.cabify.challenge.ui.feature.checkout

import app.cash.turbine.test
import com.MainCoroutineRule
import com.cabify.challenge.domain.interactors.base.BaseUseCase
import com.cabify.challenge.domain.interactors.base.BaseUseCaseFlow
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.model.ProductCheckout
import com.cabify.challenge.ui.base.mvi.BasicUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class CheckoutViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var removeProductFromShoppingCartUseCase: BaseUseCase<Product, Unit>

    @MockK
    private lateinit var getCheckOutUseCase: BaseUseCaseFlow<Unit, List<ProductCheckout>>

    @MockK
    private lateinit var clearShoppingCartUseCase: BaseUseCase<Unit, Unit>

    private lateinit var checkoutViewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        checkoutViewModel = CheckoutViewModel(
            removeProductFromShoppingCartUseCase = removeProductFromShoppingCartUseCase,
            getCheckOutUseCase = getCheckOutUseCase,
            clearShoppingCartUseCase = clearShoppingCartUseCase
        )
    }

    @Test
    fun `load not empty checkout`() = runTest {
        val products = listOf(
            ProductCheckout(1, 0f, Product("MUG", "Cabify Voucher", 7.5f)),
            ProductCheckout(1, 0f, Product("TSHIRT", "Cabify T-Shirt", 7.5f)),
            ProductCheckout(1, 0f, Product("VOUCHER", "Cabify Coffee Mug", 7.5f))
        )

        coEvery { getCheckOutUseCase(Unit) } answers { flowOf(Result.success(products)) }

        checkoutViewModel.uiState.test {
            Assert.assertEquals(awaitItem(), CheckoutListContract.State(BasicUiState.Idle, true, 0f))
            Assert.assertEquals(awaitItem(), CheckoutListContract.State(BasicUiState.Data(products), false, products.map { it.finalPrice() }.sum()))
        }

        coVerify (exactly = 1){ getCheckOutUseCase(Unit) }
    }

    @Test
    fun `load empty checkout`() = runTest {
        val products = listOf<ProductCheckout>()

        coEvery { getCheckOutUseCase(Unit) } answers { flowOf(Result.success(products)) }

        checkoutViewModel.uiState.test {
            Assert.assertEquals(awaitItem(), CheckoutListContract.State(BasicUiState.Idle, true, 0f))
            Assert.assertEquals(awaitItem(), CheckoutListContract.State(BasicUiState.Data(products), true, products.map { it.finalPrice() }.sum()))
        }

        coVerify (exactly = 1){ getCheckOutUseCase(Unit) }
    }

    @Test
    fun `error to load checkout`() = runTest {
        coEvery { getCheckOutUseCase(Unit) } answers { flowOf(Result.failure(Throwable())) }

        checkoutViewModel.uiState.test {
            Assert.assertEquals(awaitItem(), CheckoutListContract.State(BasicUiState.Idle, true, 0f))
        }

        checkoutViewModel.effect.test {
            Assert.assertEquals(awaitItem(), CheckoutListContract.Effect.Error)
        }

        coVerify (exactly = 1){ getCheckOutUseCase(Unit) }
    }

    @Test
    fun `remove product`() = runTest {
        val productCheckout = ProductCheckout(1, 0f, Product("MUG", "Cabify Voucher", 7.5f))
        coEvery { removeProductFromShoppingCartUseCase(productCheckout.product) } answers { Result.success(Unit) }

        checkoutViewModel.setEvent(CheckoutListContract.Event.OnRemoveProductButtonClicked(productCheckout))
        delay(1)

        coVerify (exactly = 1){ removeProductFromShoppingCartUseCase(productCheckout.product) }
    }

    @Test
    fun `error to remove product`() = runTest {
        val productCheckout = ProductCheckout(1, 0f, Product("MUG", "Cabify Voucher", 7.5f))
        coEvery { removeProductFromShoppingCartUseCase(productCheckout.product) } answers { Result.failure(
            Throwable()) }

        checkoutViewModel.setEvent(CheckoutListContract.Event.OnRemoveProductButtonClicked(productCheckout))

        checkoutViewModel.effect.test {
            Assert.assertEquals(awaitItem(), CheckoutListContract.Effect.Error)
        }

        coVerify (exactly = 1){ removeProductFromShoppingCartUseCase(productCheckout.product) }
    }

    @Test
    fun `clear shopping cart`() = runTest {
        coEvery { clearShoppingCartUseCase(Unit) } answers { Result.success(Unit) }

        checkoutViewModel.setEvent(CheckoutListContract.Event.OnClearCheckoutButtonClicked)
        delay(1)

        coVerify (exactly = 1){ clearShoppingCartUseCase(Unit) }
    }

    @Test
    fun `error clear shopping cart`() = runTest {
        coEvery { clearShoppingCartUseCase(Unit) } answers { Result.failure(Throwable()) }

        checkoutViewModel.setEvent(CheckoutListContract.Event.OnClearCheckoutButtonClicked)

        checkoutViewModel.effect.test {
            Assert.assertEquals(awaitItem(), CheckoutListContract.Effect.Error)
        }

        coVerify (exactly = 1){ clearShoppingCartUseCase(Unit) }
    }

    @Test
    fun buy() = runTest {
        checkoutViewModel.setEvent(CheckoutListContract.Event.OnBuyButtonClicked)

        checkoutViewModel.effect.test {
            Assert.assertEquals(awaitItem(), CheckoutListContract.Effect.Buy)
        }
    }
}