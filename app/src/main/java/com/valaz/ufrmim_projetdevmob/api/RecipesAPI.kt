package com.valaz.ufrmim_projetdevmob.api

import com.valaz.ufrmim_projetdevmob.mapper.RecipeMapper
import com.valaz.ufrmim_projetdevmob.model.Recipe
import com.valaz.ufrmim_projetdevmob.model.SingleRecipeDto
import io.ktor.http.HttpMethod

import com.valaz.ufrmim_projetdevmob.network.KtorClient
import kotlin.jvm.Throws

object RecipesAPI {
    private lateinit var recipes: List<Recipe>

    private const val RECIPE_ENDPOINT = "recipes"
    private val recipeMapper = RecipeMapper()

    @Throws(Exception::class)
    suspend fun getRecipes(): List<Recipe> {
        if (!this::recipes.isInitialized) {
            val recipesData: List<SingleRecipeDto> =
                KtorClient.httpCall(HttpMethod.Get, RECIPE_ENDPOINT)
            recipes = recipesData.map {
                recipeMapper.mapRecipeDtoToRecipe(it)
            }
        }

        return recipes
    }
}