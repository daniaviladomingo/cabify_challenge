package com.cabify.challenge.ui.feature.products

import androidx.lifecycle.viewModelScope
import com.cabify.challenge.di.annotations.UseCaseAddProductToShoppingCart
import com.cabify.challenge.domain.interactors.base.BaseUseCase
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.ui.base.BaseViewModel
import com.cabify.challenge.ui.base.mvi.BasicUiState
import com.cabify.challenge.ui.base.mvi.LoadingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: BaseUseCase<Unit, List<Product>>,
    @UseCaseAddProductToShoppingCart
    private val addProductToShoppingCartUseCase: BaseUseCase<Product, Unit>,
) : BaseViewModel<ProductListContract.Event, ProductListContract.State, ProductListContract.Effect>() {

    init {
        setState { copy(loading = LoadingUiState.Loading) }
        viewModelScope.launch {
            getProductListUseCase(Unit)
                .onFailure {
                    setState { copy(loading = LoadingUiState.NotLoading) }
                    setEffect { ProductListContract.Effect.Error }
                }
                .onSuccess {
                    setState {
                        copy(
                            loading = LoadingUiState.NotLoading,
                            productList = BasicUiState.Data(it)
                        )
                    }
                }
        }
    }

    override fun createInitialState(): ProductListContract.State =
        ProductListContract.State(
            loading = LoadingUiState.NotLoading,
            productList = BasicUiState.Idle
        )

    override fun handleEvent(event: ProductListContract.Event) {
        when (event) {
            is ProductListContract.Event.OnAddProductClicked -> viewModelScope.launch {
                addProductToShoppingCartUseCase(event.product)
                    .onFailure { setEffect { ProductListContract.Effect.Error } }
            }
        }
    }
}