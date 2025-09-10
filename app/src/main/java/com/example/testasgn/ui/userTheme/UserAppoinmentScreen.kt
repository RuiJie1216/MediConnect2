package com.example.testasgn.ui.userTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testasgn.R

@Composable
fun AppointmentScreen(
    onSpecChosenClick: () -> Unit = {},
    onDocChosenClick: () -> Unit = {},
    onSurgeryClick: () -> Unit = {},
    onInternalMedicineClick: () -> Unit = {},
    onNeurologyClick: () -> Unit = {},
    onRadiologyClick: () -> Unit = {},
    onPediatricsClick: () -> Unit = {},
    onOrthopaedicClick: () -> Unit = {},
    onUrologyClick: () -> Unit = {},
    onDermatologyClick: () -> Unit = {},
    onHematologyClick: () -> Unit = {},
    onEndocrinologyClick: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Search bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by specialty, doctor") },
            shape = RoundedCornerShape(30.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Toggle buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onSpecChosenClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A2BE2)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text(
                    text ="Specialty",
                    color = Color.White,
                    fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(
                onClick = onDocChosenClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A2BE2)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text(
                    text = "Doctor",
                    color = Color.White,
                    fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid of specialties
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item { SpecialtyButton("Surgery", onSurgeryClick, R.drawable.ic_surgery) }
            item { SpecialtyButton("Internal Medicine", onInternalMedicineClick, R.drawable.ic_internal_medicine) }
            item { SpecialtyButton("Neurology", onNeurologyClick, R.drawable.ic_neurology) }
            item { SpecialtyButton("Radiology", onRadiologyClick, R.drawable.ic_radiology) }
            item { SpecialtyButton("Pediatrics", onPediatricsClick, R.drawable.ic_pediatrics) }
            item { SpecialtyButton("Orthopaedic", onOrthopaedicClick, R.drawable.ic_orthopaedic) }
            item { SpecialtyButton("Urology", onUrologyClick, R.drawable.ic_urology) }
            item { SpecialtyButton("Dermatology", onDermatologyClick, R.drawable.ic_dermatology) }
            item { SpecialtyButton("Hematology", onHematologyClick, R.drawable.ic_hematology) }
            item { SpecialtyButton("Endocrinology", onEndocrinologyClick, R.drawable.ic_endocrinology) }
        }
    }
}

@Composable
fun SpecialtyButton(
    name: String,
    onClick: () -> Unit,
    iconRes: Int
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF1EF578),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "$name icon",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppointmentPreview() {
    AppointmentScreen(
        onSurgeryClick = {},
        onInternalMedicineClick = {},
        onNeurologyClick = {},
        onRadiologyClick = {},
        onPediatricsClick = {},
        onOrthopaedicClick = {},
        onUrologyClick = {},
        onDermatologyClick = {},
        onHematologyClick = {},
        onEndocrinologyClick = {}
    )
}
//Topbar
/*
            TopAppBar(
                title = { Text(text = "Appointment",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF29E6D2),
                    titleContentColor = Color.White
                )
            )

 */