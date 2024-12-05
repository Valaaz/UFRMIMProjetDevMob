package com.valaz.ufrmim_projetdevmob.model

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val prep_time: Float,
    val cook_time: Float,
    val servings: Int,
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
)

data class Ingredient(
    val name: String,
    val quantity: String
)