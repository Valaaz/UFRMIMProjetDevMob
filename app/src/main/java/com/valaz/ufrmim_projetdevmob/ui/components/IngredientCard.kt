package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valaz.ufrmim_projetdevmob.model.Ingredient
import com.valaz.ufrmim_projetdevmob.preview_providers.IngredientPreviewParameterProvider

@Composable
fun IngredientCard(ingredient: Ingredient, cardWith: Dp) {
    Card(modifier = Modifier.width(cardWith).height(100.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            Text(text = ingredient.name, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text(text = ingredient.quantity, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun IngredientCardPreview(@PreviewParameter(IngredientPreviewParameterProvider::class) ingredient: Ingredient) {
    IngredientCard(ingredient = ingredient, cardWith = 50.dp)
}