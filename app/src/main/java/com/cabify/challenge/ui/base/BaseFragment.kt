package com.cabify.challenge.ui.base

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.cabify.challenge.R
import com.cabify.challenge.ui.base.mvi.LoadingUiState

abstract class BaseFragment<T : ViewBinding> : Fragment(R.layout.view_base) {

    protected lateinit var binding: T

    private lateinit var containerView: FrameLayout
    private lateinit var viewProgress: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        containerView = view.findViewById(R.id.view)
        viewProgress = view.findViewById(R.id.view_progress)

        binding = view()

        containerView.addView(
            binding.root,
            LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        )
    }

    protected abstract fun view(): T

    protected fun handleLoadingState(loadingUiState: LoadingUiState) {
        when (loadingUiState) {
            LoadingUiState.Loading -> viewProgress.visibility = VISIBLE
            LoadingUiState.NotLoading -> viewProgress.visibility = GONE
        }
    }

    protected fun showError(msgError: String = getString(R.string.error_msg)) {
        AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setMessage(msgError)
            .setPositiveButton(R.string.accept) { _, _ -> }
            .show()
    }
}
