package com.example.testasgn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.testasgn.ui.theme.TestAsgnTheme
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            TestAsgnTheme {
               MediConnectApp(
                   modifier = Modifier
                       .background(MaterialTheme.colorScheme.background)
               )
            }
        }
    }
}

