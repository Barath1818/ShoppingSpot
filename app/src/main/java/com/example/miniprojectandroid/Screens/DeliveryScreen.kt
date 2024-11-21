package com.example.miniprojectandroid.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.miniprojectandroid.DataModel.CartItem
import com.example.miniprojectandroid.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryScreen(viewModel: ProductViewModel, navController: NavController) {
    var deliveryAddress by remember { mutableStateOf("") }
    var deliveryInstruction by remember { mutableStateOf("") }
    var promoCode by remember { mutableStateOf("") }
    var isGift by remember { mutableStateOf(false) }
    var selectedTip by remember { mutableStateOf(0) }
    val deliveryFee = 49 // Fixed delivery fee

    val cartItems by viewModel.cartItems.collectAsState()
    val grandTotal by remember {
        derivedStateOf {
            cartItems.sumOf { it.totalPrice } + selectedTip + deliveryFee
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Delivery Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Delivery Address Section
                    item {
                        Text(
                            text = "Delivery Address",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextField(
                            value = deliveryAddress,
                            onValueChange = { deliveryAddress = it },
                            label = { Text("Enter Delivery Address") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Location Icon"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Delivery Instructions Section
                    item {
                        Text(
                            text = "Delivery Instructions",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextField(
                            value = deliveryInstruction,
                            onValueChange = { deliveryInstruction = it },
                            label = { Text("Optional Instructions") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Gift Option Section
                    item {
                        Text(
                            text = "Gift Option",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Checkbox(checked = isGift, onCheckedChange = { isGift = it })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("This is a gift", style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    // Promo Code Section
                    item {
                        Text(
                            text = "Promo Code",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextField(
                            value = promoCode,
                            onValueChange = { promoCode = it },
                            label = { Text("Enter Promo Code") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Tip Section
                    item {
                        Text(
                            text = "Add a Tip",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                listOf(10, 20, 30, 40).forEach { tip ->
                                    ElevatedButton(onClick = { selectedTip = tip }) {
                                        Text("$tip USD")
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = if (selectedTip == 0) "" else selectedTip.toString(),
                                onValueChange = { selectedTip = it.toIntOrNull() ?: 0 },
                                label = { Text("Custom Tip") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    // Cart Items Section
                    item {
                        Text(
                            text = "Your Cart",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(cartItems) { cartItem ->
                        DeliveryCartItem(cartItem)
                    }

                    // Bill Details Section
                    item {
                        Text(
                            text = "Bill Details",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Cart Total: ${cartItems.sumOf { it.totalPrice }} USD",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text("Tip: $selectedTip USD", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "Delivery Fee: $deliveryFee USD",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                if (promoCode.isNotEmpty()) {
                                    Text(
                                        "Promo Code: $promoCode Applied",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                Text(
                                    "Total: $grandTotal USD",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    // Confirm Button
                    item {
                        Button(
                            onClick = {
                                viewModel.setDeliveryAddress(deliveryAddress)
                                viewModel.setDeliveryInstructions(deliveryInstruction)
                                navController.navigate("paymentScreen")
                                      },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text("Proceed to Payment")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DeliveryCartItem(cartItem: CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${cartItem.product.title} (${cartItem.quantity}x)",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${cartItem.totalPrice} USD",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
