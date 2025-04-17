package com.example.foodmitra.Screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.foodmitra.R // Ensure that this import is correct

@Composable
fun HomePage() {
    val context = LocalContext.current // Add this line to get context

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Food Image
        Image(
            painter = painterResource(id = R.drawable.img), // Corrected resource reference
            contentDescription = "Food Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Food Description
        Text(
            text = "Delicious food prepared with fresh ingredients. Enjoy the taste of home-cooked meals.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Location and Call Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Location Icon
            IconButton(onClick = {
                val geoUri = "geo:0,0?q=Restaurant+Location".toUri() // Replace with actual location query
                val intent = Intent(Intent.ACTION_VIEW, geoUri)
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Location",
                    tint = Color(0xFF007AFF),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Call Icon
            IconButton(onClick = {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = "tel:9999999999".toUri() // Replace with actual phone number
                context.startActivity(callIntent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_call),
                    contentDescription = "Call",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
