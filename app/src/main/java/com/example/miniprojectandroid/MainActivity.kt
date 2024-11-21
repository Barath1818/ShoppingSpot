package com.example.miniprojectandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miniprojectandroid.Screens.ProductDetailScreen
import com.example.miniprojectandroid.ui.ProductListScreen
import com.example.miniprojectandroid.repository.ProductRepository
import com.example.miniprojectandroid.viewmodel.ProductViewModel
import com.example.miniprojectandroid.MVVM.ProductViewModelFactory
import com.example.miniprojectandroid.Screens.CartScreen
import com.example.miniprojectandroid.Screens.DeliveryScreen
import com.example.miniprojectandroid.Screens.OnboardingScreen
import com.example.miniprojectandroid.Screens.OrderScreen
import com.example.miniprojectandroid.Screens.OrderSuccessScreen
import com.example.miniprojectandroid.Screens.PaymentScreen
import com.example.miniprojectandroid.Screens.SplashScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val repository = ProductRepository()  // Initialize repository
    val viewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(repository))

    NavHost(navController, startDestination = "SplashScreen") {
        composable("SplashScreen") {
            SplashScreen(navController = navController)
        }
        composable("OnboardScreen") {
            OnboardingScreen(navController = navController)
        }
        composable("productList") {
            ProductListScreen(viewModel = viewModel, navController = navController)
        }
        composable("productDetail") {
            ProductDetailScreen(viewModel = viewModel, navController = navController) {
                // Implement additional actions if needed
            }
        }
        // Move CartScreen composable outside of productDetail
        composable("CartScreen") {
            CartScreen(viewModel = viewModel, navController = navController)
        }
        composable("deliveryScreen") {
            DeliveryScreen(viewModel = viewModel, navController = navController)
        }
        composable("paymentScreen") {
            PaymentScreen(viewModel = viewModel, navController = navController)
        }
        composable("PlacingOrderScreen") {
            OrderScreen(viewModel = viewModel,navController = navController)
        }
        composable("OrderSuccessScreen") {
            OrderSuccessScreen(navController = navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}
