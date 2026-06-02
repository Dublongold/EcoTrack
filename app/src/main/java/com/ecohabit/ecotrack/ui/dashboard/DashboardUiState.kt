package com.ecohabit.ecotrack.ui.dashboard

data class DashboardUiState(
    val visitStreak: Int = 0,
    val canCheckToday: Boolean = true
)