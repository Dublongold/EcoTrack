package com.ecohabit.ecotrack.ui.checker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabit.ecotrack.data.food.HealthyFood
import com.ecohabit.ecotrack.data.food.HealthyFoodProvider
import kotlinx.coroutines.launch

class CheckerViewModel(private val provider: HealthyFoodProvider) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    var result by mutableStateOf(emptyList<HealthyFood>())
        private set

    fun updateSearchQuery(value: String) {
        searchQuery = value
    }
    fun search() {
        viewModelScope.launch {
            result = provider.searchFoods(searchQuery)
        }
    }
}
