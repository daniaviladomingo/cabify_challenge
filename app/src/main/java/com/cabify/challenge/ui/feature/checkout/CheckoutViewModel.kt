package com.cabify.challenge.ui.feature.checkout

import androidx.lifecycle.viewModelScope
import com.cabify.challenge.di.annotations.UseCaseRemoveProductFromShoppingCart
import com.cabify.challenge.domain.interactors.base.BaseUseCase
import com.cabify.challenge.domain.interactors.base.BaseUseCaseFlow
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.model.ProductCheckout
import com.cabify.challenge.ui.base.BaseViewModel
import com.cabify.challenge.ui.base.mvi.BasicUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    @UseCaseRemoveProductFromShoppingCart
    private val removeProductFromShoppingCartUseCase: BaseUseCase<Product, Unit>,
    private val getCheckOutUseCase: BaseUseCaseFlow<Unit, List<ProductCheckout>>,
    private val clearShoppingCartUseCase: BaseUseCase<Unit, Unit>,
) : BaseViewModel<CheckoutListContract.Event, CheckoutListContract.State, CheckoutListContract.Effect>() {

    init {
        viewModelScope.launch {
            getCheckOutUseCase(Unit)
                .collect { result ->
                    result
                        .onFailure { setEffect { CheckoutListContract.Effect.Error } }
                        .onSuccess {
                            setState {
                                copy(
                                    checkoutList = BasicUiState.Data(it),
                                    isEmptyCheckout = it.isEmpty(),
                                    totalPriceCheckout = it.map { it.finalPrice() }.sum()
                                )
                            }
                        }
                }
        }
    }

    override fun createInitialState(): CheckoutListContract.State =
        CheckoutListContract.State(
            checkoutList = BasicUiState.Idle,
            isEmptyCheckout = true,
            totalPriceCheckout = 0f
        )

    override fun handleEvent(event: CheckoutListContract.Event) {
        when (event) {
            is CheckoutListContract.Event.OnRemoveProductButtonClicked -> viewModelScope.launch {
                removeProductFromShoppingCartUseCase(event.checkout.product).onFailure {
                    setEffect { CheckoutListContract.Effect.Error }
                }
            }
            CheckoutListContract.Event.OnClearCheckoutButtonClicked -> viewModelScope.launch {
                clearShoppingCartUseCase(Unit).onFailure {
                    setEffect { CheckoutListContract.Effect.Error }
                }
            }
            CheckoutListContract.Event.OnBuyButtonClicked -> setEffect { CheckoutListContract.Effect.Buy }
        }
    }
}