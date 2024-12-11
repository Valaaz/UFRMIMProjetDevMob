package com.valaz.ufrmim_projetdevmob.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valaz.ufrmim_projetdevmob.Home
import com.valaz.ufrmim_projetdevmob.ui.screens.AddRecipeScreen
import com.valaz.ufrmim_projetdevmob.ui.screens.DiscoverRecipesScreen
import com.valaz.ufrmim_projetdevmob.ui.screens.MyRecipesScreen
import com.valaz.ufrmim_projetdevmob.ui.screens.RecipeDetailScreen
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel

@Composable
fun RecipesNavigationComponent(navController: NavHostController, recipeVM: RecipeViewModel) {
    val recipeVM = recipeVM

    NavHost(navController = navController, startDestination = RecipesScreens.HomeScreen.name) {
        composable(RecipesScreens.HomeScreen.name) {
            Home(navController = navController, recipeVM = recipeVM)
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
                recipe = recipeVM.getSelectedRecipe().collectAsState(initial = null).value,
                recipeVM = recipeVM
            )
        }

        composable(
            route = RecipesScreens.AddRecipeScreen.name
        ) {
            AddRecipeScreen(backAction = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            })
        }
    }
}