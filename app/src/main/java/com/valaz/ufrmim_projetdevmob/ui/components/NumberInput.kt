package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NumberInputField(defaultValue: Int, size: Dp) {
    var value by remember {
        mutableStateOf(TextFieldValue(defaultValue.toString()))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(size)
            .border(
                border = BorderStroke(width = 1.dp, Color.Black),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        IconButton(
            onClick = {
                val newValue = (value.text.toIntOrNull() ?: defaultValue) - 1
                value = TextFieldValue((if (newValue < 1) 1 else newValue).toString())
            },
            modifier = Modifier.size(size)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrement",
                tint = Color.Black
            )
        }
        VerticalDivider(Modifier.border(width = 1.dp, color = Color.Black))
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.text.all { it.isDigit() } || newValue.text.isEmpty()) {
                    val numericValue = newValue.text.toIntOrNull()
                    if (numericValue == null || numericValue in 1..999) {
                        value = if (newValue.text.isEmpty()) {
                            TextFieldValue("1", TextRange(0, 1))
                        } else {
                            newValue
                        }
                    }
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            textStyle = TextStyle(textAlign = TextAlign.Center),
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
        VerticalDivider(Modifier.border(width = 1.dp, color = Color.Black))
        IconButton(
            onClick = {
                val newValue = (value.text.toIntOrNull() ?: defaultValue) + 1
                value = TextFieldValue((if (newValue > 999) 999 else newValue).toString())
            },
            modifier = Modifier.size(size)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increment",
                tint = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun NumberInputPreview() {
    NumberInputField(defaultValue = 1, size = 50.dp)
}