package com.valaz.ufrmim_projetdevmob.viewmodel

import com.valaz.ufrmim_projetdevmob.model.Recipe

data class RecipeState(
    val recipes: List<Recipe> = listOf(),
    val selectedRecipe: Recipe? = null,
    val isLoading: Boolean = false,
    val error: Exception? = null
)