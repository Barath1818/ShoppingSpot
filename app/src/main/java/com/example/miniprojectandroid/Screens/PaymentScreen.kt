package com.example.miniprojectandroid.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.miniprojectandroid.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(viewModel: ProductViewModel, navController: NavController) {
    var selectedPaymentMethod by remember { mutableStateOf("Credit/Debit Card") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var holderName by remember { mutableStateOf("") }
    var netBankingOption by remember { mutableStateOf("") }
    var upiId by remember { mutableStateOf("") }
    var walletOption by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Payment Options",
                    style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Payment Method Selection
                item {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (selectedPaymentMethod == "Credit/Debit Card")
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.White)
                                .padding(8.dp)
                                .clickable { selectedPaymentMethod = "Credit/Debit Card" },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedPaymentMethod == "Credit/Debit Card",
                                onClick = { selectedPaymentMethod = "Credit/Debit Card" })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Credit/Debit Card",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selectedPaymentMethod == "Credit/Debit Card")
                                    MaterialTheme.colorScheme.primary
                                else Color.Black
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (selectedPaymentMethod == "Net Banking")
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.White)
                                .padding(8.dp)
                                .clickable { selectedPaymentMethod = "Net Banking" },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedPaymentMethod == "Net Banking",
                                onClick = { selectedPaymentMethod = "Net Banking" })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Net Banking",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selectedPaymentMethod == "Net Banking")
                                    MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (selectedPaymentMethod == "UPI")
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.White)
                                .padding(8.dp)
                                .clickable { selectedPaymentMethod = "UPI" },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedPaymentMethod == "UPI", onClick = { selectedPaymentMethod = "UPI" })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "UPI",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selectedPaymentMethod == "UPI")
                                    MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (selectedPaymentMethod == "Wallet")
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.White)
                                .padding(8.dp)
                                .clickable { selectedPaymentMethod = "Wallet" },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedPaymentMethod == "Wallet",
                                onClick = { selectedPaymentMethod = "Wallet" })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Wallet",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selectedPaymentMethod == "Wallet")
                                    MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (selectedPaymentMethod == "Cash on Delivery")
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.White)
                                .padding(8.dp)
                                .clickable { selectedPaymentMethod = "Cash on Delivery" },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedPaymentMethod == "Cash on Delivery",
                                onClick = { selectedPaymentMethod = "Cash on Delivery" })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Cash on Delivery",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selectedPaymentMethod == "Cash on Delivery")
                                    MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }
                    }
                }

                // Payment Details Section
                item {
                    when (selectedPaymentMethod) {
                        "Credit/Debit Card" -> {
                            Text(
                                text = "Credit/Debit Card",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            CreditCardForm(
                                cardNumber = cardNumber,
                                expiryDate = expiryDate,
                                cvv = cvv,
                                holderName = holderName,
                                onCardNumberChange = { cardNumber = it },
                                onExpiryDateChange = { expiryDate = it },
                                onCvvChange = { cvv = it },
                                onHolderNameChange = { holderName = it }
                            )
                        }
                        "Net Banking" -> {
                            Text(
                                text = "Net Banking",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            NetBankingForm(
                                selectedOption = netBankingOption,
                                onOptionChange = { netBankingOption = it }
                            )
                        }
                        "UPI" -> {
                            Text(
                                text = "UPI",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            UpiForm(
                                upiId = upiId,
                                onUpiIdChange = { upiId = it }
                            )
                        }
                        "Wallet" -> {
                            Text(
                                text = "Wallet",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            WalletForm(
                                selectedOption = walletOption,
                                onOptionChange = { walletOption = it }
                            )
                        }
                        "Cash on Delivery" -> {
                            Text(
                                text = "No additional details required for Cash on Delivery.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }

                // Place Order Button
                item {
                    Button(
                        onClick = { navController.navigate("PlacingOrderScreen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text(
                            text = "Place Order",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun CreditCardForm(
    cardNumber: String,
    expiryDate: String,
    cvv: String,
    holderName: String,
    onCardNumberChange: (String) -> Unit,
    onExpiryDateChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onHolderNameChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            value = cardNumber,
            onValueChange = onCardNumberChange,
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = expiryDate,
            onValueChange = onExpiryDateChange,
            label = { Text("Expiry Date (MM/YY)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = cvv,
            onValueChange = onCvvChange,
            label = { Text("CVV") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = holderName,
            onValueChange = onHolderNameChange,
            label = { Text("Cardholder Name") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun NetBankingForm(selectedOption: String, onOptionChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("HDFC Bank", "ICICI Bank", "SBI Bank", "Axis Bank").forEach { bank ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionChange(bank) }
                    .background(if (selectedOption == bank) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.White)
                    .padding(8.dp)
            ) {
                RadioButton(selected = selectedOption == bank, onClick = { onOptionChange(bank) })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = bank, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun UpiForm(upiId: String, onUpiIdChange: (String) -> Unit) {
    TextField(
        value = upiId,
        onValueChange = onUpiIdChange,
        label = { Text("Enter UPI ID") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun WalletForm(selectedOption: String, onOptionChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Paytm", "Google Pay", "PhonePe", "Amazon Pay").forEach { wallet ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionChange(wallet) }
                    .background(if (selectedOption == wallet) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.White)
                    .padding(8.dp)
            ) {
                RadioButton(selected = selectedOption == wallet, onClick = { onOptionChange(wallet) })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = wallet, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
