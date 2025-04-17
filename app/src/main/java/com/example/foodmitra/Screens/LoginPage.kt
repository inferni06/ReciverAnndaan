package com.example.foodmitra.Screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodmitra.R
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    val context = LocalContext.current

    var ngoName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var upiId by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            bitmap = BitmapFactory.decodeStream(inputStream)
            Toast.makeText(context, "Image Selected!", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Register Your NGO",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF388E3C)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Image Picker
        Box(
            modifier = Modifier
                .size(150.dp)
                .clickable { imagePickerLauncher.launch("image/*") }
                .background(Color.LightGray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Selected NGO Image",
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.ngo),
                contentDescription = "Default NGO Image",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (bitmap == null) {
            Text(text = "Click Image to Upload your NGO Image", color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // NGO Name
        OutlinedTextField(
            value = ngoName,
            onValueChange = { ngoName = it },
            label = { Text("NGO Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Location
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // UPI ID
        OutlinedTextField(
            value = upiId,
            onValueChange = { upiId = it },
            label = { Text("UPI ID") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = {
                val trimmedNgoName = ngoName.trim()
                val trimmedLocation = location.trim()
                val trimmedUpiId = upiId.trim()

                if (
                    trimmedNgoName.isNotEmpty() &&
                    trimmedLocation.isNotEmpty() &&
                    trimmedUpiId.contains("@")
                ) {
                    Toast.makeText(context, "NGO Registered!", Toast.LENGTH_SHORT).show()
                    navController.navigate("bottom_nav") {
                        popUpTo("ngo_register") { inclusive = true }
                    }

                    // Optional: Reset fields after successful submission
//                    ngoName = ""
//                    location = ""
//                    upiId = ""
//                    bitmap = null
//                    selectedImageUri = null
                } else {
                    Toast.makeText(
                        context,
                        "Please complete all fields correctly",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Submit", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(36.dp))
    }
}
