package com.cabify.challenge.ui.feature.products

import app.cash.turbine.test
import com.MainCoroutineRule
import com.cabify.challenge.domain.interactors.base.BaseUseCase
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.ui.base.mvi.BasicUiState
import com.cabify.challenge.ui.base.mvi.LoadingUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ProductListViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getProductListUseCase: BaseUseCase<Unit, List<Product>>

    @MockK
    private lateinit var addProductToShoppingCartUseCase: BaseUseCase<Product, Unit>

    private lateinit var productListViewModel: ProductListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        productListViewModel = ProductListViewModel(
            getProductListUseCase = getProductListUseCase,
            addProductToShoppingCartUseCase = addProductToShoppingCartUseCase
        )
    }

    @Test
    fun `load product list success`() = runTest {
        val products = listOf(
            Product("MUG", "Cabify Voucher", 7.5f),
            Product("TSHIRT", "Cabify T-Shirt", 7.5f),
            Product("VOUCHER", "Cabify Coffee Mug", 7.5f)
        )

        coEvery { getProductListUseCase(Unit) } answers { Result.success(products) }

        productListViewModel.uiState.test {
            Assert.assertEquals(awaitItem(), ProductListContract.State(LoadingUiState.Loading, BasicUiState.Idle))
            Assert.assertEquals(awaitItem(), ProductListContract.State(LoadingUiState.NotLoading, BasicUiState.Data(products)))
        }

        coVerify(exactly = 1) { getProductListUseCase(Unit) }
    }

    @Test
    fun `load product list error`() = runTest {
        coEvery { getProductListUseCase(Unit) } answers { Result.failure(Throwable()) }

        productListViewModel.uiState.test {
            Assert.assertEquals(awaitItem(), ProductListContract.State(LoadingUiState.Loading, BasicUiState.Idle))
            Assert.assertEquals(awaitItem(), ProductListContract.State(LoadingUiState.NotLoading, BasicUiState.Idle))
        }

        productListViewModel.effect.test {
            Assert.assertEquals(awaitItem(), ProductListContract.Effect.Error)
        }

        coVerify(exactly = 1) { getProductListUseCase(Unit) }
    }

    @Test
    fun `add product to cart success`() = runTest {
        val product = Product("MUG", "Cabify Voucher", 7.5f)
        coEvery { addProductToShoppingCartUseCase(product) } answers { Result.success(Unit) }

        productListViewModel.setEvent(ProductListContract.Event.OnAddProductClicked(product))
        advanceUntilIdle()

        coVerify(exactly = 1) { addProductToShoppingCartUseCase(product) }
    }

    @Test
    fun `add product to cart error`() = runTest {
        val product = Product("MUG", "Cabify Voucher", 7.5f)
        coEvery { addProductToShoppingCartUseCase(product) } answers { Result.failure(Throwable()) }

        productListViewModel.setEvent(ProductListContract.Event.OnAddProductClicked(product))

        productListViewModel.effect.test {
            Assert.assertEquals(awaitItem(), ProductListContract.Effect.Error)
        }

        coVerify(exactly = 1) { addProductToShoppingCartUseCase(product) }
    }
}