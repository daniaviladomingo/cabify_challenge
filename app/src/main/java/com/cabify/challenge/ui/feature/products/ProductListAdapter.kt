package com.cabify.challenge.ui.feature.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cabify.challenge.R
import com.cabify.challenge.databinding.ItemProductBinding
import com.cabify.challenge.domain.model.Product

class ProductListAdapter(
    private val onAddProductToCart: (Product) -> Unit,
) : ListAdapter<Product, ProductListAdapter.ViewHolder>(ProductDiffCallback) {
    inner class ViewHolder(
        private val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.name.text = product.name
            binding.price.text = binding.root.context.getString(R.string.price, product.price)
            binding.addToCart.setOnClickListener {
                onAddProductToCart(product)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}

private object ProductDiffCallback :
    DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product,
    ): Boolean = oldItem.code == newItem.code

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product,
    ): Boolean = oldItem == newItem
}
