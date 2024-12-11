package com.valaz.ufrmim_projetdevmob.ui.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.valaz.ufrmim_projetdevmob.R
import com.valaz.ufrmim_projetdevmob.model.Ingredient
import com.valaz.ufrmim_projetdevmob.model.Recipe

@Composable
fun RecipeCard(recipe: Recipe?) {
    val cardPadding = 5.dp
    val textPadding = 10.dp
    val spaceBtwIconText = 5.dp

    recipe?.let { recipe ->
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AsyncImage(
                        model = recipe.imageUrl,
                        contentDescription = "Banner of ${recipe.title}",
                        modifier = Modifier.size(100.dp),
                        placeholder = painterResource(R.drawable.recette_non_disponible),
                        fallback = painterResource(R.drawable.recette_non_disponible),
                        error = painterResource(R.drawable.recette_non_disponible),
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                                .padding(top = cardPadding, end = cardPadding)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = recipe.title,
                                style = MaterialTheme.typography.titleMedium.plus(
                                    TextStyle(fontWeight = FontWeight.Bold)
                                ),
                                softWrap = true,
                                modifier = Modifier.weight(1f)
                            )
                            PersonNumber(recipe = recipe)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(spaceBtwIconText)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = Icons.Default.Timer.name,
                                modifier = Modifier.size(10.dp)
                            )
                            Text(
                                text = "Temps de préparation : ${
                                    DecimalFormat("#.##").format(
                                        recipe.prepTime
                                    )
                                } min", modifier = Modifier.padding(start = textPadding)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(spaceBtwIconText)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = Icons.Default.Timer.name,
                                modifier = Modifier.size(10.dp)
                            )
                            Text(
                                text = "Temps de cuisson : ${DecimalFormat("#.##").format(recipe.cookTime)} min",
                                modifier = Modifier.padding(start = textPadding)
                            )
                        }
                    }
                }
            }
        }
    } ?: run {
        Text("Aucune recette disponible")
    }
}

@Composable
fun PersonNumber(recipe: Recipe?) {
    Card(
        colors = CardColors(
            containerColor = Color.Cyan,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Black
        ),
        modifier = Modifier.width(35.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(all = 4.dp)
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = Icons.Default.Person.name,
                modifier = Modifier.size(15.dp),
            )
            Text(
                text = recipe!!.servings.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun RecipePreview(
    @PreviewParameter(RecipePreviewParameterProvider::class) recipe: Recipe
) {
    RecipeCard(
        recipe = recipe
    )
}

class RecipePreviewParameterProvider : PreviewParameterProvider<Recipe> {
    override val values = sequenceOf(
        Recipe(
            id = 1,
            favorite = false,
            title = "Spaghettis à la bolognaise et aux légumes",
            description = "Coucou",
            prepTime = 12,
            cookTime = 15,
            servings = 3,
            imageUrl = "https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
            ingredients = listOf(
                Ingredient("Pâtes", "200g"),
                Ingredient("Pâtes", "200g"),
                Ingredient("Pâtes", "200g"),
                Ingredient("Pâtes", "200g"),
                Ingredient("Pâtes", "200g"),
                Ingredient("Pâtes", "200g"),
                Ingredient("Pâtes", "200g")
            ),
            steps = listOf("Faire bouillir l'eau"),
        )
    )
}