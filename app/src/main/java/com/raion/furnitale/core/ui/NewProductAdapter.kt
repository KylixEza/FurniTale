package com.raion.furnitale.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.raion.furnitale.core.domain.model.Product
import com.raion.furnitale.databinding.ItemSelectionListBinding
import com.raion.furnitale.ui.home.HomeFragmentDirections

class NewProductAdapter: RecyclerView.Adapter<NewProductAdapter.NewProductViewHolder>() {

    private var newProductList = ArrayList<Product>()

    fun setList(data: List<Product>) {
        newProductList.clear()
        newProductList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProductViewHolder {
        val view = ItemSelectionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewProductViewHolder, position: Int) {
        holder.bind(product = newProductList[position], position)
    }

    override fun getItemCount(): Int = newProductList.size


    inner class NewProductViewHolder(private val view: ItemSelectionListBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(product: Product, position: Int) {
            view.data = product
            itemView.setOnClickListener {
                it.findNavController().navigate(HomeFragmentDirections.actionHomeDestinationToDetailActivity(newProductList[position].id!!))
            }
        }
    }
}