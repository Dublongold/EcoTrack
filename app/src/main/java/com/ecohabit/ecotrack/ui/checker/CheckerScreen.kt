package com.ecohabit.ecotrack.ui.checker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecohabit.ecotrack.data.food.HealthyFoodProvider
import com.ecohabit.ecotrack.ui.BetterPaddingValues
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckerScreen(vm: CheckerViewModel = koinViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Healthy Foods Checker")
                },
            )
        }
    ) { paddingValues ->
        var thereIsLessThan3Symbols by remember { mutableStateOf(false) }
        var oldSearchQuery by remember { mutableStateOf("") }
        val betterPaddingValues = BetterPaddingValues.fromPaddingValues(paddingValues)
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    top = betterPaddingValues.top,
                    start = betterPaddingValues.start,
                    end = betterPaddingValues.end,
                )
                .padding(horizontal = 16.dp)
        ) {
            Text("Enter query, that helps to find your food and find it healthy score!")
            OutlinedTextField(
                value = vm.searchQuery,
                onValueChange = {
                    vm.updateSearchQuery(it)
                    thereIsLessThan3Symbols = false
                },
                singleLine = true,
                label = { Text("Search query") },
                isError = thereIsLessThan3Symbols,
                trailingIcon = {
                    IconButton({
                        oldSearchQuery = vm.searchQuery
                        if (oldSearchQuery.length < 3) {
                            thereIsLessThan3Symbols = true
                        } else {
                            vm.search()
                        }
                    }) {
                        Icon(
                            Icons.Default.Search,
                            null
                        )
                    }
                },
                placeholder = {
                    Text("Name, ingredient or healthy score.")
                },
                supportingText = if (thereIsLessThan3Symbols) {
                    {
                        Text(
                            "Should be at least 3 symbols to search!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                } else null,
                modifier = Modifier.fillMaxWidth()
            )
            val bottomPadding = 16.dp + betterPaddingValues.bottom
            val bottomComposableModifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            if (vm.result.isNotEmpty()) {
                LazyColumn(
                    modifier = bottomComposableModifier,
                    contentPadding = PaddingValues(
                        bottom = bottomPadding
                    )
                ) {
                    items(vm.result) { food ->
                        HealthyFoodCard(food)
                    }
                }
            } else {
                Box(
                    bottomComposableModifier.padding(bottom = bottomPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        when {
                            vm.searchQuery == oldSearchQuery && oldSearchQuery.length >= 3 -> {
                                "There is no food that satisfy your query…"
                            }

                            else -> ""
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CheckerScreenPreview() {
    val context = LocalContext.current
    CheckerScreen(
        vm = remember {
            CheckerViewModel(
                HealthyFoodProvider(context)
            )
        }
    )
}