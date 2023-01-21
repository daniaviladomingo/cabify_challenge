package com.cabify.challenge.ui.feature.checkout

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cabify.challenge.R
import com.cabify.challenge.databinding.ItemCheckoutBinding
import com.cabify.challenge.domain.model.ProductCheckout

class CheckoutAdapter(
    private val onRemoveProductFromCart: (ProductCheckout) -> Unit,
) : ListAdapter<ProductCheckout, CheckoutAdapter.ViewHolder>(ProductDiffCallback) {
    inner class ViewHolder(
        private val binding: ItemCheckoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productCheckout: ProductCheckout) {
            binding.name.text = productCheckout.product.name
            binding.price.text = binding.root.context.getString(R.string.price, productCheckout.priceWithoutDiscount())
            binding.priceDiscount.isVisible = productCheckout.hasDiscount()
            if(productCheckout.hasDiscount()){
                binding.price.paintFlags = binding.price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.priceDiscount.text = binding.root.context.getString(R.string.price, productCheckout.priceWithDiscount)
            } else {
                binding.price.paintFlags = binding.price.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.priceDiscount.text = ""
            }
            binding.units.text =
                binding.root.context.getString(R.string.units, productCheckout.units)

            binding.removeFromCart.setOnClickListener {
                onRemoveProductFromCart(productCheckout)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}

private object ProductDiffCallback :
    DiffUtil.ItemCallback<ProductCheckout>() {

    override fun areItemsTheSame(
        oldItem: ProductCheckout,
        newItem: ProductCheckout,
    ): Boolean = oldItem.product.code == newItem.product.code

    override fun areContentsTheSame(
        oldItem: ProductCheckout,
        newItem: ProductCheckout,
    ): Boolean = oldItem == newItem
}
