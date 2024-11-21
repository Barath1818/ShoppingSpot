package com.example.miniprojectandroid.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.miniprojectandroid.DataModel.CartItem
import com.example.miniprojectandroid.viewmodel.ProductViewModel
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(viewModel: ProductViewModel, navController: NavController) {
    val cartItems = viewModel.cartItems.collectAsState().value
    val deliveryAddress by viewModel.deliveryAddress.collectAsState()
    val deliveryInstructions by viewModel.deliveryInstructions.collectAsState()
    val grandTotal = cartItems.sumOf { it.totalPrice }
    val deliveryFee = 49.0
    val totalAmount = grandTotal + deliveryFee

    // State to control progress bar visibility and timer
    var isProcessing by remember { mutableStateOf(false) }

    LaunchedEffect(isProcessing) {
        if (isProcessing) {
            kotlinx.coroutines.delay(5000) // Wait for 5 seconds
            navController.navigate("OrderSuccessScreen")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Summary",
                    style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Order Details Header
            item {
                Text(
                    text = "Order Details",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Cart Items
            items(cartItems) { cartItem ->
                CartItemRow(cartItem)
            }

            // Divider
            item {
                Divider(color = Color.LightGray, thickness = 1.dp)
            }

            // Pricing Details
            item {
                Text(
                    text = "Price Details",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                PriceDetailRow(label = "Cart Total", value = "$grandTotal USD")
                PriceDetailRow(label = "Delivery Fee", value = "$deliveryFee USD")
                Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                PriceDetailRow(
                    label = "Total Amount",
                    value = "$totalAmount USD",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Delivery Details
            item {
                Text(
                    text = "Delivery Address",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                DeliveryDetailRow(label = "Address", value = deliveryAddress)
                if (deliveryInstructions.isNotEmpty()) {
                    DeliveryDetailRow(label = "Instructions", value = deliveryInstructions)
                }
            }

            // Proceed to Payment or Cancel Buttons
            item {
                if (!isProcessing) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { isProcessing = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor =
                            MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Place Order", color = Color.White)
                        }
                        Button(
                            onClick = { navController.navigate("PaymentScreen") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor =
                            MaterialTheme.colorScheme.error)
                        ) {
                            Text("Cancel Order", color = Color.White)
                        }
                    }
                } else {
                    // Show Progress Bar when processing
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Text(
                            text = "Processing Order...",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${cartItem.product.title} (${cartItem.quantity}x)",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${cartItem.totalPrice} USD",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PriceDetailRow(label: String, value: String, fontWeight: FontWeight? = null, color: Color? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = fontWeight ?: FontWeight.Normal),
            color = color ?: MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun DeliveryDetailRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}
