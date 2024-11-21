package com.example.miniprojectandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniprojectandroid.DataModel.CartItem
import com.example.miniprojectandroid.DataModel.ProductItem
import com.example.miniprojectandroid.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productList = MutableStateFlow<List<ProductItem>>(emptyList())
    val productList: StateFlow<List<ProductItem>> = _productList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _selectedProduct = MutableStateFlow<ProductItem?>(null)
    val selectedProduct: StateFlow<ProductItem?> = _selectedProduct


    private val _showAddToCartMessage = MutableStateFlow(false)
    val showAddToCartMessage: StateFlow<Boolean> get() = _showAddToCartMessage

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems


    private val _deliveryAddress = MutableStateFlow("")
    val deliveryAddress: StateFlow<String> = _deliveryAddress

    private val _deliveryInstructions = MutableStateFlow("")
    val deliveryInstructions: StateFlow<String> = _deliveryInstructions



    val filteredProducts = combine(_productList, _searchQuery)
    { products, query ->
        if (query.isEmpty()) products
        else products.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                val products = repository.fetchProducts()
                _productList.value = products
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load products: ${e.message}"
            }
        }
    }


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }


    fun onProductSelected(product: ProductItem) {
        _selectedProduct.value = product
    }



    fun resetAddToCartMessage() {
        _showAddToCartMessage.value = false
    }

    fun addToCart(
        product: ProductItem,
        quantity: Int,
        totalPrice: Double
    ) {
        _cartItems.update { cart ->

            val existingItemIndex = cart.indexOfFirst {
                it.product.id == product.id
            }
            if (existingItemIndex != -1) {

                cart.mapIndexed { index, cartItem ->
                    if (index == existingItemIndex) {
                        cartItem.copy(
                            quantity = cartItem.quantity + quantity,
                            totalPrice = cartItem.totalPrice + totalPrice
                        )
                    }
                    else cartItem
                }
            } else
            {

                cart + CartItem(product, quantity, totalPrice)
            }
        }

        // Show the "added to cart" message
        _showAddToCartMessage.value = true
    }

    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        val updatedItem = cartItem.copy(
            quantity = newQuantity,
            totalPrice = cartItem.product.price * newQuantity
        )

        _cartItems.update {
            cart ->
            cart.map {
                if (it == cartItem)
                    updatedItem
                else it }
        }
    }

    fun removeCartItem(cartItem: CartItem) {
        _cartItems.value = _cartItems.value.toMutableList().apply {
            remove(cartItem)
        }
    }

    // Setters to update address and instructions
    fun setDeliveryAddress(address: String) {
        _deliveryAddress.value = address
    }

    fun setDeliveryInstructions(instructions: String) {
        _deliveryInstructions.value = instructions
    }


}
