package com.cabify.challenge.ui.feature.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cabify.challenge.databinding.FragmentProductListBinding
import com.cabify.challenge.ui.base.BaseFragment
import com.cabify.challenge.ui.base.mvi.handle
import com.cabify.challenge.util.launch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>() {
    private val vm: ProductListViewModel by viewModels()

    override fun view(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductListAdapter { product ->
            vm.setEvent(ProductListContract.Event.OnAddProductClicked(product))
        }
        binding.recyclerProducts.adapter = adapter
        launch {
            vm.uiState.collect { uiState ->
                handleLoadingState(uiState.loading)
                uiState.productList.handle { productList ->
                    adapter.submitList(productList)
                }
            }
        }
        launch {
            vm.effect.collect { uiEffect ->
                when (uiEffect) {
                    ProductListContract.Effect.Error -> showError()
                }
            }
        }
    }
}