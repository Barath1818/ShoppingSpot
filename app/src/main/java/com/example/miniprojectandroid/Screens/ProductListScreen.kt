package com.example.miniprojectandroid.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.miniprojectandroid.DataModel.ProductItem
import com.example.miniprojectandroid.ui.theme.HeadingText
import com.example.miniprojectandroid.viewmodel.ProductViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.miniprojectandroid.R
import androidx.compose.material3.*
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp

@Composable
fun ProductListScreen(viewModel: ProductViewModel, navController: NavController) {
    val products by viewModel.filteredProducts.collectAsState(initial = emptyList())
    val error by viewModel.errorMessage.collectAsState()
    var searchQuery by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Row with Title, Search Bar, and Cart Icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Title or heading for the screen
            Text(text = "Shopping Spot", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.weight(1f)) // Pushes the cart icon to the right side

            // Cart Icon Button
            IconButton(
                onClick = {
                    navController.navigate("CartScreen") // Navigate to CartScreen
                },
                modifier = Modifier
                    .padding(end = 8.dp) // Adjust padding as needed
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                    contentDescription = "Cart",
                    tint = Color.Black
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.onSearchQueryChanged(it)
            },
            label = { Text("Search products") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal Scrolling Product Images
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductImage(
                    product = product,
                    onClick = {
                        viewModel.onProductSelected(product)
                        navController.navigate("productDetail")
                    }
                )
            }
        }

        // Heading text for the list
        Text(
            text = "List of All Products",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        // Product List with Vertical Scrolling
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onClick = {
                        viewModel.onProductSelected(product)
                        navController.navigate("productDetail")
                    }
                )
            }
        }

        // Error message display
        error.takeIf { it.isNotEmpty() }?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

    }
    }

// Product card for vertical scrolling product list
@Composable
fun ProductCard(product: ProductItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.title,
                modifier = Modifier.size(100.dp).weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(2f)) {
                Text(text = product.title,
                    style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "${product.price} USD", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

// Circular Product Image with Black Border
@Composable
fun ProductImage(product: ProductItem , onClick: () -> Unit) {
    Image(
        painter = rememberAsyncImagePainter(product.image),
        contentDescription = product.title,
        modifier = Modifier
            .clickable { onClick() }
            .size(80.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape) // Black border around the circular image
            .padding(4.dp) // Adds padding between images in the LazyRow
    )
}

