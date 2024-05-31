package com.example.promobeauty.presentation.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.promobeauty.data.model.Product
import com.example.promobeauty.databinding.ProductsListItemBinding
import java.text.NumberFormat
import java.util.Locale
import android.content.Context

class ProductAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateProducts(newProducts: List<Product>) {
        productList = newProducts
        notifyDataSetChanged()
    }

    class ProductViewHolder(private val binding: ProductsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding){
            titleProduct.text = product.name
            val regularPrice = currencyFormat().format(product.regular_price)
            val salePrice = currencyFormat().format(product.sale_price)
            initialPrice.text = "De: $regularPrice"
            priceProduct.text = "Por: $salePrice"

            Glide.with(productImage.context)
                .load(product.thumb_url)
                .into(productImage)

            buttonGetPromotion.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(product.affiliate_url)
                }
                itemView.context.startActivity(intent)
            }
        }

        private fun currencyFormat(): NumberFormat =
            NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    }
}

