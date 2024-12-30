package com.example.madcamp_week1_moonwon


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*


@Composable
fun InfoScreen(name: String, phone: String) {
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Name: $name", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Phone: $phone", style = MaterialTheme.typography.bodyLarge)
    }
}
