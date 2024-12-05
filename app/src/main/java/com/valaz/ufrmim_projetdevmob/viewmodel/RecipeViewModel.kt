package com.valaz.ufrmim_projetdevmob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valaz.ufrmim_projetdevmob.api.RecipesAPI
import com.valaz.ufrmim_projetdevmob.model.Recipe
import com.valaz.ufrmim_projetdevmob.network.StateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val recipesMutableStateFlow = MutableStateFlow(RecipeState())

    val stateFlow: StateFlow<RecipeState>
        get() = recipesMutableStateFlow.asStateFlow()

    private var recipeState: RecipeState
        get() = recipesMutableStateFlow.value
        set(value) {
            recipesMutableStateFlow.value = value
        }

    init {
        viewModelScope.launch {
            getMovieList()
        }
    }

    fun getMovieList() {
        recipeState = recipeState.copy(isLoading = true)
        StateManager.launchCoroutine {
            recipeState = try {
                recipeState.copy(
                    recipes = RecipesAPI.getRecipes(),
                    isLoading = false
                )
            } catch (error: Exception) {
                recipeState.copy(
                    error = error,
                    isLoading = false
                )
            }
        }
    }

    fun setSelectedMovie(recipe: Recipe) {
        recipeState = recipeState.copy(
            selectedRecipe = recipe
        )
    }
}