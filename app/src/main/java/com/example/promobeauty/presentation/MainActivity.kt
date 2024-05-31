package com.example.promobeauty.presentation

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.promobeauty.R
import com.example.promobeauty.data.di.ProductViewModelFactory
import com.example.promobeauty.data.model.Product
import com.example.promobeauty.data.repository.ProductRepository
import com.example.promobeauty.databinding.MainActivityBinding
import com.example.promobeauty.presentation.adapter.ProductAdapter
import com.example.promobeauty.presentation.viewmodel.ProductViewModel
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val viewModel: ProductViewModel by viewModels {
        ProductViewModelFactory(ProductRepository())
    }
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categories: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        categories = resources.getStringArray(R.array.category)
        setupRecyclerView()
        viewModel.fetchProducts(categories.first())
        setupObservers()
        setupCategorySpinner()
    }

    private fun setupObservers() {
        viewModel.productsList.observe(this) { products ->
            showProductFilter(products)
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, "Por favor, colocar a API_KEY enviada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProductFilter(products: List<Product>) {
        productAdapter.updateProducts(products)
        binding.productsRv.scrollToPosition(0)
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList())
        binding.productsRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setupCategorySpinner() {
        val displayCategories = categories.map {
            it.replaceFirstChar { char -> char.uppercase(Locale.getDefault()) }
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, displayCategories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySp.adapter = adapter

        binding.categorySp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categories[position]
                viewModel.fetchProducts(selectedCategory)
                showLoadingSpinner(true)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                showLoadingSpinner(false)
            }
        }
    }

    private fun showLoadingSpinner(show: Boolean) {
        binding.loadingSpinner.visibility = if (show) View.VISIBLE else View.GONE
    }
}
