package com.example.miniprojectandroid.MVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniprojectandroid.viewmodel.ProductViewModel
import com.example.miniprojectandroid.repository.ProductRepository

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
