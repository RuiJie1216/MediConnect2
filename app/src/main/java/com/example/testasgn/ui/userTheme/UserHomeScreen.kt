package com.example.testasgn.ui.userTheme

import com.example.testasgn.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testasgn.AppScreen
import com.example.testasgn.ui.theme.TestAsgnTheme

@Composable
fun UserHomeScreen(
    modifier: Modifier = Modifier,
    chooseBar: AppScreen,
    onAppointmentClick: () -> Unit,
    onMedicalReminderClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    Column(
        modifier =Modifier
            .fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .padding(16.dp)
                .height(50.dp)
                .background(Color.White, RoundedCornerShape(25.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(25.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Search...",
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(
                onClick = onAppointmentClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xBFA6E8A9)
                ),
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(8.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Appointment",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = "Appointment",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Button(
                onClick = onMedicalReminderClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xBFA6E8A9)
                ),
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Medical Reminder",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = "Medical Reminder",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ){
                Text(
                    text = "Your Appointment",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                HorizontalDivider(thickness = 2.dp, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Appointment details will be shown here",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(35.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        Spacer(modifier = Modifier.height(35.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.hospital1),
                contentDescription = "Hospital 1",
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = R.drawable.hospital2),
                contentDescription = "Hospital 2",
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        UserChooseBar(
            chooseBar = chooseBar,
            onTurnClick = {
                if (it == AppScreen.UserProfile) {
                    onProfileClick()
                }
                else {
                    onAppointmentClick()
                }
            }
        )

    }

}

@Preview(showBackground = true)
@Composable
fun UserHomeScreenPreview() {
    TestAsgnTheme {
        UserHomeScreen(
            onAppointmentClick = {},
            onMedicalReminderClick = {},
            onProfileClick = {},
            chooseBar = AppScreen.UserHome
        )
    }
}