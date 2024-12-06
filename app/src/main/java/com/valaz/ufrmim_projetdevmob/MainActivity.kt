package com.valaz.ufrmim_projetdevmob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.valaz.ufrmim_projetdevmob.ui.components.TopAppBarNavigation
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesNavigationComponent
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesScreens
import com.valaz.ufrmim_projetdevmob.ui.theme.UFRMIMProjetDevMobTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UFRMIMProjetDevMobTheme {
                val navController = rememberNavController()
                RecipesNavigationComponent(navController = navController)
            }
        }
    }
}

@Composable
fun Home(navController: NavHostController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(onClick = { navController.navigate(RecipesScreens.AddRecipeScreen.name) }) {
                    Icon(Icons.Outlined.Add, Icons.Outlined.Add.name)
                }
            }
        },
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            TopAppBarNavigation(navController = navController)
        },
    ) { scaffoldPadding ->
        Modifier.padding(scaffoldPadding)
//        RecipesNavigationComponent()
    }
}

//@Preview
//@Composable
//fun HomeActivityPreview() {
//    Home()
//}