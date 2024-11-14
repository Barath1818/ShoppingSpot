package com.example.miniprojectandroid.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HeadingText(
    text: String,
    modifier: Modifier = Modifier,
//    backgroundColor: Color = Color(0xFF6200EE), // Purple shade for a unique background
    textColor: Color = Color.Black
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
//            .background(backgroundColor)
            .padding(10.dp), // Padding around the text for a heading feel
            contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.displaySmall, // Using custom heading style
            textAlign = TextAlign.Center
        )
    }
}
