package com.example.ironmantrainingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ironmantrainingapp.data.TrainingRepository
import com.example.ironmantrainingapp.ui.MainApp
import com.example.ironmantrainingapp.ui.theme.IronmanTrainingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = TrainingRepository(applicationContext)
        setContent {
            IronmanTrainingAppTheme {
                MainApp(repository = repository)
            }
        }
    }
}
