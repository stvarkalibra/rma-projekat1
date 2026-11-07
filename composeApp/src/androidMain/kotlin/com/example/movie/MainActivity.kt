package com.example.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.movie.core.navigation.AppNavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        renderApplication()
    }

    private fun renderApplication() {
        setContent {
            val rootNavController = rememberNavController()

            _root_ide_package_.com.example.movie.core.navigation.AppNavGraph(
                navController = rootNavController
            )
        }
    }
}