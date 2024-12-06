package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RawButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.clickable {
        if (enabled) {
            onClick()
        }
    }) {
        content()
    }
}