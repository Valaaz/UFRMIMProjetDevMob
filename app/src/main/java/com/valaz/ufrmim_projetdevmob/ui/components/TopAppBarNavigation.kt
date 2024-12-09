package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesNavigationComponent
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesScreens
import com.valaz.ufrmim_projetdevmob.ui.screens.DiscoverRecipesScreen
import com.valaz.ufrmim_projetdevmob.ui.screens.MyRecipesScreen
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun TopAppBarNavigation(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val pagerState = rememberPagerState(pageCount = { 2 })
        val coroutineScope = rememberCoroutineScope()
        val recipeVM = RecipeViewModel()

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
//            if (page == 0)
//                navController.navigate(RecipesScreens.MyRecipesScreen.name)
//            else
//                navController.navigate(RecipesScreens.DiscoverRecipesScreen.name)
//            RecipesNavigationComponent(navController = navController)
            if (page == 0) MyRecipesScreen(
                recipeState = recipeVM.stateFlow.collectAsState().value,
                onDetails = { recipe ->
                    recipeVM.setSelectedRecipe(recipe)
                    navController.navigate(RecipesScreens.RecipeDetailScreen.name)
                }) else DiscoverRecipesScreen(
                    recipeState = recipeVM.stateFlow.collectAsState().value,
                    onDetails = { recipe ->
                        recipeVM.setSelectedRecipe(recipe)
                        navController.navigate(RecipesScreens.RecipeDetailScreen.name)
                }
            )
        }
    }
}