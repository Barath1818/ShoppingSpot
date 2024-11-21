package com.example.miniprojectandroid.Screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.miniprojectandroid.R
import kotlinx.coroutines.delay

@Composable
fun OrderSuccessScreen(navController: NavController) {
    // Define order details
    val orderId = "#1033374874674"
    val otp = "7548"
    val scale = remember { Animatable(0f) }

    // Get screen height dynamically
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Launch the animation
    LaunchedEffect(Unit) {
        // Animate the scaling of the image
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        )
        delay(3000)
    }

    // Column to contain image and order details below it
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Full screen image at the top
        Spacer(modifier = Modifier.height(52.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .width(screenWidth)
                .height(screenHeight / 2) // Image height is half of the screen height
        ) {
            Image(
                painter = painterResource(id = R.drawable.order), // Replace with your drawable
                contentDescription = "Order Success",
                modifier = Modifier
                    .fillMaxSize() // Occupy full width
                    .align(Alignment.Center)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
            )
        }

        // Content below the image (order details and buttons)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Space between elements
        ) {
            Spacer(modifier = Modifier.height(32.dp)) // Space between image and content

            // Order details text fields
            Text(
                text = "Order Placed!!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Order ID: $orderId",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Accept your order delivery with OTP $otp",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Action buttons (Track Order and Close)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Track Order Button
                Button(
                    onClick = { /* Navigate to order tracking screen */ },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(MaterialTheme.colorScheme.primary), // Optional: you can customize button color
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "Track Order")
                }

                // Close Button (navigates to Product List Screen)
                OutlinedButton(
                    onClick = { navController.navigate("productList") }, // Navigate to product list screen
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White) // White background
                ) {
                    Text(text = "Close", color = MaterialTheme.colorScheme.primary) // Text color for the outlined button
                }
            }
        }
    }
}
