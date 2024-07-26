package com.example.photohuntcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.photohuntcompose.navigation.NavGraph
import com.example.photohuntcompose.ui.theme.PhotoHuntComposeTheme
import com.example.photohuntcompose.viewmodel.HuntViewModel
import com.example.photohuntcompose.viewmodel.PredictionViewModel

class MainActivity : ComponentActivity() {
    private val huntViewModel: HuntViewModel by viewModels()
    private val predictionViewModel: PredictionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoHuntApp(huntViewModel = huntViewModel, predictionViewModel = predictionViewModel)
        }
    }
}

@Composable
fun PhotoHuntApp(huntViewModel: HuntViewModel, predictionViewModel: PredictionViewModel) {
    PhotoHuntComposeTheme{
        NavGraph(huntViewModel = huntViewModel, predictionViewModel = predictionViewModel)
    }
}
