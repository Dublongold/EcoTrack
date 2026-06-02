package com.ecohabit.ecotrack.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp

data class BetterPaddingValues(
    val top: Dp = Dp.Unspecified,
    val bottom: Dp = Dp.Unspecified,
    val start: Dp = Dp.Unspecified,
    val end: Dp = Dp.Unspecified,
) {

    companion object {
        @Composable
        fun fromPaddingValues(paddingValues: PaddingValues) = BetterPaddingValues(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding(),
            start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
            end = paddingValues.calculateEndPadding(LocalLayoutDirection.current)
        )
    }
}