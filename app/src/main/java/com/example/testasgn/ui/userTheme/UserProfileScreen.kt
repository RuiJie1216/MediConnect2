package com.example.testasgn.ui.userTheme

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testasgn.AppScreen
import com.example.testasgn.R
import com.example.testasgn.ui.data.dataTable.Users
import java.util.Locale


@Composable
fun InfoColumn(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .background(Color(0x4017E12B), RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SettingBar(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .height(45.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Icon Picture",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp),
                tint = Color.Black
            )
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Arrow",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(20.dp),
            tint = Color.Black
        )
    }
}

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    currentUser: Users?,
    currentScreen: AppScreen,
    onHomeClick: () -> Unit,
    onAppointmentClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onPersonalInfoClick: () -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0x591BC2B1),
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                )
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_photo),
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = currentUser?.name ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "IC: ${currentUser?.ic ?: ""}",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(top = 2.dp)
                    )


                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "   Info",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //infoItem
            InfoColumn(
                label = "Age",
                value = if (currentUser?.age == 0 || currentUser?.age == null) "-" else currentUser?.age.toString(),
                modifier = Modifier
                    .weight(1f)
            )
            InfoColumn(
                label = "Weight",
                value = currentUser?.weight?.takeIf { it != 0.0 }?.let { String.format(Locale.US, "%.1f kg", it) } ?: "-",
                modifier = Modifier
                    .weight(1f)
            )
            InfoColumn(
                label = "Height",
                value = currentUser?.height?.takeIf { it != 0.0 }?.let { String.format(Locale.US, "%.1f cm", it) } ?: "-",
                modifier = Modifier
                    .weight(1f)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Personal info
            SettingBar(
                icon = Icons.Default.Person,
                text = "Personal info",
                onClick = onPersonalInfoClick
            )

            // History
            SettingBar(
                icon = Icons.Default.DateRange,
                text = "History",
                onClick = {}
            )

            // Bill
            SettingBar(
                icon = Icons.Default.CheckCircle,
                text = "Bill",
                onClick = {}
            )

            // Setting
            SettingBar(
                icon = Icons.Default.Settings,
                text = "Setting",
                onClick = {}
            )


            // Logout
            SettingBar(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                text = "Logout",
                onClick = onLogoutClick
            )


            Spacer(modifier = Modifier.weight(1f))
            UserChooseBar(
                chooseBar = currentScreen,
                onTurnClick = {
                    if (it == AppScreen.UserHome) {
                        onHomeClick()
                    }
                    else {
                        onAppointmentClick()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    UserProfileScreen(
        currentUser = Users(
            ic = "0123",
            name = "test",
            age = 20,
            weight = 50.0,
            height = 160.0,
            email = "",
            phone = ""
        ),
        currentScreen = AppScreen.UserProfile,
        onHomeClick = {},
        onAppointmentClick = {},
        onLogoutClick = {},
        onPersonalInfoClick = {}
    )
}