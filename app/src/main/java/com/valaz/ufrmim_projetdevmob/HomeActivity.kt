package com.valaz.ufrmim_projetdevmob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesNavigationComponent
import com.valaz.ufrmim_projetdevmob.ui.screens.DiscoverScreen
import com.valaz.ufrmim_projetdevmob.ui.screens.MyRecipesScreen
import com.valaz.ufrmim_projetdevmob.ui.theme.UFRMIMProjetDevMobTheme
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UFRMIMProjetDevMobTheme {
                Home()
            }
        }
    }
}

@Composable
fun Home() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(onClick = { println("action") }) {
                    Icon(Icons.Outlined.Add, Icons.Outlined.Add.name)
                }
            }
        },

        topBar = {
            TopAppBarNavigation()
        },
    ) { scaffoldPadding ->
        Modifier.padding(scaffoldPadding)
    }
}

@Composable
fun TopAppBarNavigation() {
    var tabIndex by remember { mutableStateOf(0) }
    val recipeVM = RecipeViewModel()
    val recipeState = recipeVM.stateFlow.collectAsState().value

    val tabs = listOf("Mes recettes", "Découvrir")

    Column(modifier = Modifier.fillMaxWidth()) {
        val pagerState = rememberPagerState(pageCount = { 2 })
        val coroutineScope = rememberCoroutineScope()
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
            RecipesNavigationComponent()
//            if (page == 0) MyRecipesScreen(recipeState = recipeState) else DiscoverScreen()
        }
    }
}

@Preview
@Composable
fun HomeActivityPreview() {
    Home()
}