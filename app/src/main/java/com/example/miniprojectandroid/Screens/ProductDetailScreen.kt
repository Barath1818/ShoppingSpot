package com.example.miniprojectandroid.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.miniprojectandroid.DataModel.ProductItem
import com.example.miniprojectandroid.R
import com.example.miniprojectandroid.viewmodel.ProductViewModel



@Composable
fun ProductDetailScreen(
    viewModel: ProductViewModel,
    navController: NavController,
    onBuyClicked: () -> Unit
    )
{
    val product = viewModel.selectedProduct.collectAsState().value
    val context = LocalContext.current
    val showAddToCartMessage by viewModel.showAddToCartMessage.collectAsState()

    // Show toast when item is added to cart
    LaunchedEffect(showAddToCartMessage) {
        if (showAddToCartMessage) {
            Toast.makeText(context,
                "Item added to cart successfully",
                Toast.LENGTH_SHORT).show()
            viewModel.resetAddToCartMessage() // Reset the message state
        }
    }

    var quantity by remember { mutableStateOf(1) }
    val totalPrice = product?.price?.times(quantity) ?: 0.0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button and Title
        item {
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
                Text(text = "Product Details",
                    style = MaterialTheme.typography.headlineMedium)
            }
                Spacer(modifier = Modifier.height(16.dp))
          }

        // Product Image, Title, and Price
        product?.let {
            item {
                // Centering the image using Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(it.image),
                        contentDescription = it.title,
                        modifier = Modifier.size(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${it.price} USD",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Quantity Selector
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                        if (quantity > 1)
                            quantity -= 1
                    }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_circle_down_24),
                            contentDescription = "Decrease Quantity"
                        )
                    }
                    Text(text = "Quantity: $quantity",
                        style = MaterialTheme.typography.bodyLarge)
                          IconButton(
                              onClick = {
                                  quantity += 1
                              })
                          {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_circle_up_24),
                            contentDescription = "Increase Quantity"
                        )
                    }
                }

            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Centering the total price
                    Text(
                        text = "Total: $totalPrice USD",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(26.dp))
                }
            }
            // Add to Cart Button (Centered)
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Button(onClick = {
                        viewModel.addToCart(product, quantity, totalPrice)
                        onBuyClicked()
                    }) {
                        Text("Add to cart")
                    }
                }
            }
        }
    }
}

