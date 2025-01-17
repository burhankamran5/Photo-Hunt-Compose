package com.example.photohuntcompose.ui.screens.huntview

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.photohuntcompose.navigation.HandleBackPressToHome
import com.example.photohuntcompose.navigation.Screen
import com.example.photohuntcompose.viewmodel.HuntViewModel

@Composable
fun HuntLoadingScreen(navController: NavHostController, huntViewModel: HuntViewModel) {

	HandleBackPressToHome(navController, huntViewModel)

	val items = huntViewModel.currentItems.collectAsState()

	LaunchedEffect(items.value) {
		if (items.value.isNotEmpty()) {
			Log.d("HuntLoadingScreen", "Items generated, navigating to Loaded screen")
			navController.navigate(Screen.Loaded.route)
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
		Column(
			modifier = Modifier
				.align(Alignment.Center),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Text(text = "🤖", style = MaterialTheme.typography.displayLarge.copy(
				color = MaterialTheme.colorScheme.onPrimary))

			Spacer(modifier = Modifier.height(16.dp))

			Text(
				text = "AI is preparing your hunt...",
				style = MaterialTheme.typography.titleLarge)

			Spacer(modifier = Modifier.height(50.dp))
		}

		CircularProgressIndicator(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.padding(bottom = 35.dp)
		)
	}
}

@Preview(showBackground = true)
@Composable
fun HuntLoadingScreenPreview() {
	val navController = rememberNavController()
	val huntViewModel = HuntViewModel()
	HuntLoadingScreen(navController, huntViewModel)
}

