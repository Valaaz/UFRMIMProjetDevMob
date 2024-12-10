package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.valaz.ufrmim_projetdevmob.preview_providers.IngredientPreviewParameterProvider
import com.valaz.ufrmim_projetdevmob.R
import com.valaz.ufrmim_projetdevmob.model.Ingredient

@Composable
fun IngredientCard(ingredient: Ingredient) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Card(modifier = Modifier.size(70.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.padding(5.dp)) {
                AsyncImage(
                    model = R.drawable.pates,
                    contentDescription = "Banner of ${ingredient.name}",
                    placeholder = painterResource(R.drawable.recette_non_disponible),
                    fallback = painterResource(R.drawable.recette_non_disponible),
                    error = painterResource(R.drawable.recette_non_disponible),
                )
                Text(text= ingredient.name)
            }
        }
        Text(text = ingredient.quantity)
    }
}

@Preview
@Composable
fun IngredientCardPreview(@PreviewParameter(IngredientPreviewParameterProvider::class) ingredient: Ingredient) {
    IngredientCard(ingredient = ingredient)
}