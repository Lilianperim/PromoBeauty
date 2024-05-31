package com.example.promobeauty.data.api

import com.example.promobeauty.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("products/all")
    suspend fun getProducts(
        @Query("category") category: String,
        @Header("x-api-key") apiKey: String
    ): List<Product>
}