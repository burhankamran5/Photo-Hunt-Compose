package com.example.photohuntcompose.ui.screens.huntview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun PhotoHuntLoadedScreen(navController: NavHostController, huntViewModel: HuntViewModel) {

	HandleBackPressToHome(navController, huntViewModel)

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
				text = "Your hunt is ready!",
				style = MaterialTheme.typography.titleLarge)

			Spacer(modifier = Modifier.height(50.dp))
		}

		Button(
			onClick = { navController.navigate(Screen.Pending.route) },
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.padding(bottom = 35.dp)
		) {
			Text(text = "Bring it on!")
		}
	}
}

@Preview(showBackground = true)
@Composable
fun PhotoHuntLoadedScreenPreview() {
	val navController = rememberNavController()
	val huntViewModel = HuntViewModel()
	PhotoHuntLoadedScreen(navController, huntViewModel)
}
