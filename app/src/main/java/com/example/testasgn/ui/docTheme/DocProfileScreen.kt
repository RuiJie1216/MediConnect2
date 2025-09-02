package com.example.testasgn.ui.docTheme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testasgn.ui.data.model.Doctor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    doctor: Doctor,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = onLogoutClick) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Doctor Avatar",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = doctor.docName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileDetailItem(label = "Degrees", value = doctor.docDegree)
            ProfileDetailItem(label = "Specialty", value = doctor.docSpecialty)
            ProfileDetailItem(label = "Years of Practice", value = "${doctor.yearOfPractice} years")
            ProfileDetailItem(label = "Languages Spoken", value = doctor.language)
            ProfileDetailItem(label = "Day-Off", value = doctor.dayOff)

            Spacer(Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = doctor.quote,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}


@Composable
fun ProfileDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:")
        Text(text = value, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {

    val fakeDoctor = Doctor(
        doctorId = "D001",
        loginId = "doc123",
        pwd = "password",
        docName = "Dr. Alice Wong",
        docDegree = "MBBS, MD",
        docSpecialty = "Cardiology",
        yearOfPractice = 12,
        language = "English, Mandarin, Malay",
        dayOff = "Sunday",
        quote = "Caring for patients is my lifelong mission."
    )

    // 直接塞给 ProfileScreen
    ProfileScreen(
        doctor = fakeDoctor,
        onBackClick = {},
        onEditClick = {},
        onLogoutClick = {}
    )
}