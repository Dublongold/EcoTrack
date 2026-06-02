package com.ecohabit.ecotrack.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ecohabit.ecotrack.ui.AppRoute
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    vm: DashboardViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val showThankYouDialog by vm.showThankYouDialog.collectAsState()

    val actions = listOf(
        "Waste Counter" to AppRoute.WasteRoute.route,
        "Savings Tracker" to AppRoute.SavingsTrackerRoute.route,
        "Eco Action Planner" to AppRoute.EcoActionPlannerRoute.route,
        "Education Quiz" to AppRoute.QuizRoute.route,
        "Prioritize Goals" to AppRoute.GoalSetterRoute.route
    )
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dashboard") })
        }
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Visit streak", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${uiState.visitStreak} day${if (uiState.visitStreak == 1) "" else "s"}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = vm::onCheckClick,
                        enabled = uiState.canCheckToday,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (uiState.canCheckToday) "Check" else "Checked today")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Quick Actions", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            actions.forEach { (title, route) ->
                Button(
                    onClick = { navController.navigate(route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(title)
                }
            }
        }
    }
    if (showThankYouDialog) {
        AlertDialog(
            onDismissRequest = vm::dismissThankYouDialog,
            title = { Text("Thank you!") },
            text = { Text("Thanks for saving the planet today.") },
            confirmButton = {
                TextButton(onClick = vm::dismissThankYouDialog) {
                    Text("OK")
                }
            }
        )
    }
}
