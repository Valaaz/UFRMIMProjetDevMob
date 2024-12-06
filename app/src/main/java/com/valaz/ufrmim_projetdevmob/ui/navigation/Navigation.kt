package com.valaz.ufrmim_projetdevmob.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valaz.ufrmim_projetdevmob.ui.screens.MyRecipesScreen
import com.valaz.ufrmim_projetdevmob.ui.screens.RecipeDetailScreen
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel

@Composable
fun RecipesNavigationComponent() {
    val navController = rememberNavController()
    val recipeVM = RecipeViewModel()

    NavHost(navController = navController, startDestination = RecipesScreens.MyRecipesScreen.name) {
        composable(RecipesScreens.MyRecipesScreen.name) {
            MyRecipesScreen(
                recipeState = recipeVM.stateFlow.collectAsState().value,
                onDetails = { recipe ->
                    recipeVM.setSelectedRecipe(recipe)
                    navController.navigate(RecipesScreens.RecipeDetailScreen.name)
                })
        }

        composable(
            route = RecipesScreens.RecipeDetailScreen.name
        ) {
            RecipeDetailScreen(
                backAction = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                },
                recipe = recipeVM.stateFlow.collectAsState().value.selectedRecipe
            )
        }
    }
}