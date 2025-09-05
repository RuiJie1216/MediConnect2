package com.example.testasgn.ui.docTheme

import androidx.compose.ui.Modifier
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testasgn.ui.theme.TestAsgnTheme
import com.example.testasgn.ui.viewModel.AccViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocHomeScreen(
    modifier: Modifier = Modifier,
    onAppointmentClick: () -> Unit,
    onProfileClick: () -> Unit,
    onPatientClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row {
            Button(
                onClick = onAppointmentClick,
                modifier = Modifier
                    .padding(8.dp)
                    .height(125.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCBFFFC),
                    contentColor = Color.Black
                )
            ) {
                Text("Appointment List")
            }

            Column {
                Button(
                    onClick = onProfileClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC7FFEC),
                        contentColor = Color.Black
                    )

                ) {
                    Text("My Profile")
                }

                Button(
                    onClick = onPatientClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5EFFC9),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Patients Information")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Current Appointment",
            style = MaterialTheme.typography.titleMedium,
            fontStyle = FontStyle.Italic,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(8.dp))

        Column(modifier = modifier
            .align(Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95F)

                    .height(150.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )

                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📅 Date: 17/08/2025",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Lew Yao Bing - 8:00AM",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Tan Chin Hua - 8:30AM",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Lee Kuan Ying - 9:00AM",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DocHomePreview() {
    TestAsgnTheme {
        DocHomeScreen(
            onPatientClick = {},
            onProfileClick = {},
            onAppointmentClick = {}
        )
    }
}
