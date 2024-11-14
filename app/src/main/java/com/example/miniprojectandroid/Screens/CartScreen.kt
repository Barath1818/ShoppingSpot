package com.example.miniprojectandroid.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.miniprojectandroid.DataModel.CartItem
import com.example.miniprojectandroid.R
import com.example.miniprojectandroid.viewmodel.ProductViewModel

@Composable
fun CartScreen(viewModel: ProductViewModel, navController: NavController) {
    val cartItems by viewModel.cartItems.collectAsState()
    var grandTotal by remember { mutableStateOf(0.0) }

    // Calculate the grand total whenever cartItems change
    LaunchedEffect(cartItems) {
        grandTotal = cartItems.sumOf { it.totalPrice }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Your Cart", style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                items(cartItems) { cartItem ->
                    CartItemRow(
                        cartItem = cartItem,
                        onQuantityChange = { newQuantity ->
                            viewModel.updateCartItemQuantity(cartItem, newQuantity
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Display the grand total
            Text(
                text = "Total: $grandTotal USD",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buy Now Button
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Buy Now")
            }
        }
        else
        {
            Text("Your cart is empty", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, onQuantityChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(cartItem.product.image),
            contentDescription = cartItem.product.title,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = cartItem.product.title,
                style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "${cartItem.product.price} USD",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Decrease quantity button
                IconButton(
                    onClick = {
                        if (cartItem.quantity > 1)
                            onQuantityChange(cartItem.quantity - 1)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_circle_down_24),
                        contentDescription = "Decrease quantity"
                    )
                }
                // Display quantity
                Text(text = "Quantity: ${cartItem.quantity}", style = MaterialTheme.typography.bodyMedium)
                // Increase quantity button
                IconButton(
                    onClick = { onQuantityChange(cartItem.quantity + 1) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_circle_up_24),
                        contentDescription = "Increase quantity"
                    )
                }
            }
            Text(
                text = "Total: ${cartItem.totalPrice} USD",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}
