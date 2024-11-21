package com.example.miniprojectandroid.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.example.miniprojectandroid.R
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            count = 4, // Number of pages
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
            page ->
            OnboardingPageContent(page)
        }

        PagerIndicator(pagerState = pagerState)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    if (pagerState.currentPage <
                        pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        navController.navigate("productList") // Navigate to the main screen
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape
        ) {
            Text(
                text = if (pagerState.currentPage == pagerState.pageCount - 1)
                    "Get Started"
                else
                    "Next",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun OnboardingPageContent(page: Int) {
    when (page) {
        0 -> OnboardingPage(
            imageRes = R.drawable.onboard1,
            title = "Product Delivery at door step",
            description = "Get Product at your service in within less time."
        )
        1 -> OnboardingPage(
            imageRes = R.drawable.onboard2,
            title = "Grocery & Essentials Delivery",
            description = "Get Product at your service in within less time."
        )
        2 -> OnboardingPage(
            imageRes = R.drawable.onboard3,
            title = "Get any packages delivered",
            description = "Get Product at your service in within less time."
        )
        3 -> OnboardingPage(
            imageRes = R.drawable.onboard4,
            title = "Achieve Your Goals",
            description = "Get Product at your service in within less time."
        )
    }
}

@Composable
fun OnboardingPage(imageRes: Int, title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .size(400.dp)
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}




@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicator(pagerState: PagerState) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { page ->
            val color = if (pagerState.currentPage == page) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            }

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(10.dp)
                    .background(color = color,
                        shape = CircleShape)
            )
        }
    }
}

