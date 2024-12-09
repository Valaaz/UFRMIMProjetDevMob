package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.valaz.ufrmim_projetdevmob.PreviewProviders.IngredientPreviewParameterProvider
import com.valaz.ufrmim_projetdevmob.model.Ingredient

@Composable
fun AddedIngredientCard(ingredient: Ingredient) {
    RawButton(
        onClick = {}, modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NameAndQuantity(ingredient = ingredient, modifier = Modifier.weight(1f))
            DeleteIcon()
        }
    }
}

@Composable
fun NameAndQuantity(ingredient: Ingredient, modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(ingredient.name)
        Icon(imageVector = Icons.Default.Close, contentDescription = Icons.Default.Close.name)
        Text(ingredient.quantity)
    }
}

@Composable
fun DeleteIcon() {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.padding(end = 5.dp)) {
        VerticalDivider()
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.DeleteForever,
                contentDescription = Icons.Outlined.DeleteForever.name
            )
        }
    }
}

@Preview
@Composable
fun AddedIngredientCardPreview(@PreviewParameter(IngredientPreviewParameterProvider::class) ingredient: Ingredient) {
    AddedIngredientCard(ingredient = ingredient)
}