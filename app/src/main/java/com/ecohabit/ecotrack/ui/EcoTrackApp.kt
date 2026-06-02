package com.ecohabit.ecotrack.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ecohabit.ecotrack.ui.checker.CheckerScreen
import com.ecohabit.ecotrack.ui.dashboard.DashboardScreen
import com.ecohabit.ecotrack.ui.goalsetter.GoalSetterScreen
import com.ecohabit.ecotrack.ui.habits.HabitsScreen
import com.ecohabit.ecotrack.ui.ecoactionplanner.EcoActionPlannerScreen
import com.ecohabit.ecotrack.ui.quiz.QuizScreen
import com.ecohabit.ecotrack.ui.savings.SavingsScreen
import com.ecohabit.ecotrack.ui.waste.WasteScreen

sealed class AppRoute(val route: String) {
    object DashboardRoute : AppRoute("DashboardRoute")
    object CheckerRoute : AppRoute("CheckerRoute")
    object HabitsRoute : AppRoute("HabitsRoute")
    object WasteRoute : AppRoute("WasteRoute")
    object SavingsTrackerRoute : AppRoute("SavingsTrackerRoute")
    object EcoActionPlannerRoute : AppRoute("EcoActionPlannerRoute")
    object QuizRoute : AppRoute("QuizRoute")
    object GoalSetterRoute : AppRoute("GoalSetterRoute")
}

data class MainNavigationItem(
    val route: AppRoute,
    val label: String,
    val icon: ImageVector,
    val selectedRoutes: Set<String> = setOf(route.route)
)

object MainNavigationItems {
    val dashboard = MainNavigationItem(
        route = AppRoute.DashboardRoute,
        label = "Dashboard",
        icon = Icons.Default.Home,
        selectedRoutes = setOf(
            AppRoute.DashboardRoute.route,
            AppRoute.WasteRoute.route,
            AppRoute.SavingsTrackerRoute.route,
            AppRoute.EcoActionPlannerRoute.route,
            AppRoute.QuizRoute.route,
            AppRoute.GoalSetterRoute.route
        )
    )
    val habits = MainNavigationItem(
        route = AppRoute.HabitsRoute,
        label = "Habits",
        icon = Icons.Default.Checklist
    )
    val checker = MainNavigationItem(
        route = AppRoute.CheckerRoute,
        label = "Checker",
        icon = Icons.Default.Search
    )

    val items = listOf(dashboard, habits, checker)
}

@Composable
fun EcoTrackApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            MainNavigationItems.items.forEach { item ->
                item(
                    icon = { Icon(item.icon, item.label) },
                    label = { Text(item.label) },
                    selected = currentRoute in item.selectedRoutes,
                    onClick = {
                        if (item.route == AppRoute.DashboardRoute) {
                            navController.popBackStack(AppRoute.DashboardRoute.route, false)
                        } else {
                            navController.navigate(item.route.route) {
                                popUpTo(AppRoute.DashboardRoute.route) { saveState = true }
                            }
                        }
                    }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = AppRoute.DashboardRoute.route,
        ) {
            composable(AppRoute.DashboardRoute.route) {
                DashboardScreen(
                    navController
                )
            }
            composable(AppRoute.CheckerRoute.route) { CheckerScreen() }
            composable(AppRoute.HabitsRoute.route) { HabitsScreen() }
            composable(AppRoute.WasteRoute.route) {
                WasteScreen(
                    onBackClick = navController::navigateUp
                )
            }
            composable(AppRoute.SavingsTrackerRoute.route) {
                SavingsScreen(
                    onBackClick = navController::navigateUp
                )
            }
            composable(AppRoute.EcoActionPlannerRoute.route) {
                EcoActionPlannerScreen(
                    onBackClick = navController::navigateUp
                )
            }
            composable(AppRoute.QuizRoute.route) {
                QuizScreen(
                    onBackClick = navController::navigateUp
                )
            }
            composable(AppRoute.GoalSetterRoute.route) {
                GoalSetterScreen(
                    onBackClick = navController::navigateUp
                )
            }
        }
    }
}
