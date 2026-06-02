package com.ecohabit.ecotrack.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabit.ecotrack.data.store.CheckResult
import com.ecohabit.ecotrack.data.store.VisitingStreakProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val visitingStreakProvider: VisitingStreakProvider
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = combine(
        visitingStreakProvider.visitingStreak,
        visitingStreakProvider.canCheckToday
    ) { streak, canCheckToday ->
        DashboardUiState(visitStreak = streak, canCheckToday = canCheckToday)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardUiState())

    private val _showThankYouDialog = MutableStateFlow(false)
    val showThankYouDialog: StateFlow<Boolean> = _showThankYouDialog.asStateFlow()

    fun onCheckClick() {
        viewModelScope.launch {
            val result = visitingStreakProvider.checkVisitToday()
            if (result == CheckResult.CHECKED) {
                _showThankYouDialog.value = true
            }
        }
    }

    fun dismissThankYouDialog() {
        _showThankYouDialog.update { false }
    }
}
