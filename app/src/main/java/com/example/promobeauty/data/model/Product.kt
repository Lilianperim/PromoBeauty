package com.example.promobeauty.data.model

data class Product(
    val id: String,
    val name: String,
    val regular_price: Double,
    val sale_price: Double,
    val discount: Int,
    val last_updated_at: String,
    val slug: String,
    val thumb_url: String,
    val ecommerce: String,
    val affiliate_url: String,
    val created_at: String,
    val updated_at: String
)
