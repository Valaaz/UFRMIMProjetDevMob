package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StepCard(step: String, stepNumber: Int) {
    Card(modifier = Modifier.padding(5.dp)) {
        Column {
            Text(
                text = "Etape ${stepNumber}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = step)
        }
    }
}

@Preview
@Composable
fun StepCardPreview() {
    StepCard(
        step = "Préchauffer une poêle à feu moyen et cuire les filets de poulet pendant 7 minutes de chaque côté.",
        stepNumber = 1
    )
}