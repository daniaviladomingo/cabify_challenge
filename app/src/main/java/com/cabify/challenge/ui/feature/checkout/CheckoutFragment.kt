package com.cabify.challenge.ui.feature.checkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.cabify.challenge.R
import com.cabify.challenge.databinding.FragmentCheckoutBinding
import com.cabify.challenge.ui.base.BaseFragment
import com.cabify.challenge.ui.base.mvi.handle
import com.cabify.challenge.util.launch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>() {
    private val vm: CheckoutViewModel by viewModels()

    override fun view(): FragmentCheckoutBinding =
        FragmentCheckoutBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CheckoutAdapter { product ->
            vm.setEvent(CheckoutListContract.Event.OnRemoveProductButtonClicked(product))
        }
        binding.buy.setOnClickListener {
            vm.setEvent(CheckoutListContract.Event.OnBuyButtonClicked)
        }
        binding.clearCheckout.setOnClickListener {
            vm.setEvent(CheckoutListContract.Event.OnClearCheckoutButtonClicked)
        }
        binding.recyclerProducts.adapter = adapter
        launch {
            vm.uiState.collect { uiState ->
                binding.emptyCheckout.isVisible = uiState.isEmptyCheckout
                binding.clearCheckout.isVisible = !uiState.isEmptyCheckout
                binding.buy.isEnabled = !uiState.isEmptyCheckout
                binding.titleTotal.text = getString(R.string.total, uiState.totalPriceCheckout)
                uiState.checkoutList.handle { checkoutList ->
                    adapter.submitList(checkoutList)
                }
            }
        }
        launch {
            vm.effect.collect { uiEffect ->
                when (uiEffect) {
                    CheckoutListContract.Effect.Error -> showError()
                    CheckoutListContract.Effect.Buy -> Toast.makeText(requireContext(),
                        "Coming soon!!",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}