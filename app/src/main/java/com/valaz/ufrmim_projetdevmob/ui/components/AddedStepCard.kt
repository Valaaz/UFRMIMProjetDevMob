package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valaz.ufrmim_projetdevmob.model.Ingredient

@Composable
fun AddedStepCard(
    step: String, stepNumber: Int, deleteStep: (String) -> Unit, updateStep: (String) -> Unit
) {
    RawButton(onClick = {
        updateStep(step)
    }) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Etape ${stepNumber}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text = step, modifier = Modifier.padding(bottom = 5.dp))
                }
                IconButton(onClick = { deleteStep(step) }) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteForever,
                        contentDescription = Icons.Outlined.DeleteForever.name
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AddedStepCardPreview() {
    AddedStepCard(
        step = "Préchauffer une poêle à feu moyen et cuire les filets de poulet pendant 7 minutes de chaque côté.",
        stepNumber = 1,
        deleteStep = {},
        updateStep = {}
    )
}