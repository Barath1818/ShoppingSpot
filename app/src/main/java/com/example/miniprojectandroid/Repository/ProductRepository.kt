// ProductRepository.kt
package com.example.miniprojectandroid.repository

import com.example.miniprojectandroid.DataModel.ProductItem
import com.example.miniprojectandroid.Retrofit.RetrofitInstance

class ProductRepository {
    suspend fun fetchProducts(): List<ProductItem> {
        return RetrofitInstance.api.getProducts()
    }
}