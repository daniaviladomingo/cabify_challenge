package com.cabify.challenge.ui.feature.products

import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.ui.base.mvi.BasicUiState
import com.cabify.challenge.ui.base.mvi.LoadingUiState
import com.cabify.challenge.ui.base.mvi.UiEffect
import com.cabify.challenge.ui.base.mvi.UiEvent
import com.cabify.challenge.ui.base.mvi.UiState

interface ProductListContract {
    sealed interface Event : UiEvent {
        data class OnAddProductClicked(val product: Product) : Event
    }

    data class State(
        val loading: LoadingUiState,
        val productList: BasicUiState<List<Product>>,
    ) : UiState

    sealed interface Effect : UiEffect {
        object Error : Effect
    }
}