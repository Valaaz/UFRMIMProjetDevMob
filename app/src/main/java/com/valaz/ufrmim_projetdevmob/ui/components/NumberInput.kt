package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NumberInputField(value: Int, onValueChange: (Int) -> Unit, size: Dp) {
    val lineColor = MaterialTheme.colorScheme.onSecondaryContainer

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(size)
            .border(
                width = 1.dp,
                color = lineColor,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        IconButton(
            onClick = {
                val newValue = (value - 1).coerceAtLeast(1)
                onValueChange(newValue)
            },
            modifier = Modifier.size(size)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrement",
                tint = lineColor
            )
        }
        VerticalDivider(Modifier.border(width = 1.dp, color = lineColor))
        BasicTextField(
            value = value.toString(),
            onValueChange = { newValue ->
                val numericValue = newValue.toIntOrNull()
                if (numericValue != null && numericValue in 1..999) {
                    onValueChange(numericValue)
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            textStyle = TextStyle(textAlign = TextAlign.Center, color = lineColor),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    innerTextField()
                }
            }
        )
        VerticalDivider(Modifier.border(width = 1.dp, color = lineColor))
        IconButton(
            onClick = {
                val newValue = (value + 1).coerceAtMost(999)
                onValueChange(newValue)
            },
            modifier = Modifier.size(size)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increment",
                tint = lineColor
            )
        }
    }
}

@Preview
@Composable
fun NumberInputPreview() {
    NumberInputField(value = 1, onValueChange = {}, size = 50.dp)
}