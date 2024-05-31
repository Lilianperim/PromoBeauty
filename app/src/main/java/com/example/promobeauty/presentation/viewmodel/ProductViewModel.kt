package com.example.promobeauty.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.promobeauty.data.model.Product
import com.example.promobeauty.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val productsList = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String>()

    fun fetchProducts(category: String) {
        viewModelScope.launch {
            try {
                val result = repository.getProducts(category)
                productsList.postValue(result)
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }
}
