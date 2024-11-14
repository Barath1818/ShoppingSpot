// ProductApiService.kt
package com.example.miniprojectandroid.Retrofit

import com.example.miniprojectandroid.DataModel.Product


import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): Product
}