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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testasgn.ui.data.dataTable.Doctors
import com.example.testasgn.ui.theme.TestAsgnTheme

@Composable
fun DocProfileScreen(
    modifier: Modifier = Modifier,
    doctor: Doctors?
) {
    Column(
        modifier = Modifier
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
            text = doctor?.name ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProfileDetailItem(label = "Degrees", value = doctor?.degree ?: "")
        ProfileDetailItem(label = "Specialty", value = doctor?.specialty ?: "")
        ProfileDetailItem(
            label = "Years of Practice",
            value = "${doctor?.year ?: 0} years"
        )
        ProfileDetailItem(label = "Languages Spoken", value = doctor?.language ?: "")
        ProfileDetailItem(label = "Day-Off", value = doctor?.dayOff ?: "")

        Spacer(Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            if (doctor?.quote != "") {
                doctor?.quote
            } else {
                "No quote has written..."
            }?.let {
                Text(
                    text = it,
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
fun DocProfileScreenPreview() {
    TestAsgnTheme {
        DocProfileScreen(
            doctor = Doctors(
                id = "1",
                name = "test1",
                email = "abc123",
                phone = "0129",
                degree = "testDegree",
                specialty = "testSpecialty",
                year = 0,
                currentHospital = "hih",
                quote = "",
                language = "chinese, english",
                dayOff = "Monday, Tuesday"
            )
        )

    }
}