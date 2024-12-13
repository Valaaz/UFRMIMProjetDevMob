package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.material3.RangeSlider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun IntegerRangeSlider(
    range: IntRange,
    onRangeChange: (IntRange) -> Unit,
    valueRange: IntRange,
    steps: Int = 0,
    modifier: Modifier
) {
    RangeSlider(
        value = range.start.toFloat()..range.endInclusive.toFloat(),
        onValueChange = { newRange ->
            onRangeChange(
                newRange.start.toInt()..newRange.endInclusive.toInt()
            )
        },
        valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
        steps = steps,
        modifier = modifier
    )
}
