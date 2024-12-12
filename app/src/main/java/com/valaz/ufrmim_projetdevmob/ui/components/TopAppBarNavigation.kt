package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesScreens
import com.valaz.ufrmim_projetdevmob.ui.screens.DiscoverRecipesScreen
import com.valaz.ufrmim_projetdevmob.ui.screens.MyRecipesScreen
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun TopAppBarNavigation(navController: NavHostController, recipeVM: RecipeViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val pagerState = rememberPagerState(pageCount = { 2 }, initialPage = 1)
        val coroutineScope = rememberCoroutineScope()
        val recipeVM = recipeVM

        TabRow(selectedTabIndex = pagerState.currentPage) {
            Tab(
                selected = pagerState.currentPage == 0,
                text = { Text(text = "Mes Recettes") },
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
            )
            Tab(
                selected = pagerState.currentPage == 1,
                text = { Text(text = "Découvrir") },
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } },
            )
        }
        HorizontalPager(state = pagerState) { page ->
            if (page == 0) MyRecipesScreen(
                recipeVM = recipeVM,
                onDetails = { recipe ->
                    recipeVM.setSelectedRecipe(recipe.id ?: -1)
                    navController.navigate(RecipesScreens.RecipeDetailScreen.name)
                }) else DiscoverRecipesScreen(
                recipeVM = recipeVM,
                onDetails = { recipe ->
                    recipeVM.setSelectedRecipe(recipe.id ?: -1)
                    navController.navigate(RecipesScreens.RecipeDetailScreen.name)
                },
                filterScreen = {
                    navController.navigate(RecipesScreens.FilterScreen.name)
                }
            )
        }
    }
}