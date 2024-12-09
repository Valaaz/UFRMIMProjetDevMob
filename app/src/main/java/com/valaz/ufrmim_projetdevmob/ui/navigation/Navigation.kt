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
fun RecipesNavigationComponent(navController: NavHostController) {
//    val navController = navController
//    val navController = rememberNavController()
    val recipeVM = RecipeViewModel()

    NavHost(navController = navController, startDestination = RecipesScreens.HomeScreen.name) {
        composable(RecipesScreens.HomeScreen.name) {
            Home(navController = navController)
        }

        composable(RecipesScreens.MyRecipesScreen.name) {
            MyRecipesScreen(
                recipeState = recipeVM.stateFlow.collectAsState().value,
                onDetails = { recipe ->
                    recipeVM.setSelectedRecipe(recipe)
                    navController.navigate(RecipesScreens.RecipeDetailScreen.name)
                })
        }

        composable(RecipesScreens.DiscoverRecipesScreen.name) {
            DiscoverRecipesScreen(
                recipeState = recipeVM.stateFlow.collectAsState().value,
                onDetails = { recipe ->
                    recipeVM.setSelectedRecipe(recipe)
                    navController.navigate(RecipesScreens.RecipeDetailScreen.name)
                }
            )
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

        composable(
            route = RecipesScreens.AddRecipeScreen.name
        ) {
            AddRecipeScreen()
        }
    }
}