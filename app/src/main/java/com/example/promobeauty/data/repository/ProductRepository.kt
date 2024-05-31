package com.example.promobeauty.data.repository

import com.example.promobeauty.data.client.RetrofitInstance
import com.example.promobeauty.data.model.Product

private const val API_KEY = ""

class ProductRepository {
    private val api = RetrofitInstance.api

    suspend fun getProducts(category: String): List<Product> {
        return api.getProducts(category, API_KEY)
    }
}
