package com.cabify.challenge.ui.feature.checkout

import com.cabify.challenge.domain.model.ProductCheckout
import com.cabify.challenge.ui.base.mvi.BasicUiState
import com.cabify.challenge.ui.base.mvi.UiEffect
import com.cabify.challenge.ui.base.mvi.UiEvent
import com.cabify.challenge.ui.base.mvi.UiState

interface CheckoutListContract {
    sealed interface Event : UiEvent {
        data class OnRemoveProductButtonClicked(val checkout: ProductCheckout) : Event
        object OnBuyButtonClicked : Event
        object OnClearCheckoutButtonClicked: Event
    }

    data class State(
        val checkoutList: BasicUiState<List<ProductCheckout>>,
        val isEmptyCheckout: Boolean,
        val totalPriceCheckout: Float,
    ) : UiState

    sealed interface Effect : UiEffect {
        object Error : Effect
        object Buy: Effect
    }
}