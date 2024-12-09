package com.valaz.ufrmim_projetdevmob.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val recipes: List<SingleRecipeDto>
)

@Serializable
data class SingleRecipeDto(
    val id: Int,
    val favorite: Boolean,
    val title: String,
    val description: String,
    val prep_time: Float,
    val cook_time: Float,
    val servings: Int,
    val image: String,
    val ingredients: List<SingleIngredientDto>,
    val steps: List<String>,
)

@Serializable
data class SingleIngredientDto(
    val name: String,
    val quantity: String
)