package com.valaz.ufrmim_projetdevmob.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valaz.ufrmim_projetdevmob.model.Recipe
import com.valaz.ufrmim_projetdevmob.ui.components.RawButton
import com.valaz.ufrmim_projetdevmob.ui.components.RecipeCard
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesScreens
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRecipesScreen(
    recipeVM: RecipeViewModel,
    onDetails: (Recipe) -> Unit,
    filterScreen: (RecipesScreens) -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    val recipes = recipeVM.getMyRecipesList().collectAsState(initial = null)
    val filtersApplied by recipeVM.filtersMyRecipesApplied.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    // Filtrer les recettes en fonction de la requête de recherche
    val filteredRecipes = recipes.value?.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    } ?: emptyList()

    LaunchedEffect(recipes.value) {
        isLoading = recipes.value == null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            windowInsets = WindowInsets(top = 0.dp),
            query = searchQuery,
            onQueryChange = { query ->
                searchQuery = query
            },
            onSearch = {},
            active = false,
            onActiveChange = {},
            leadingIcon = {
                IconButton(onClick = { filterScreen(RecipesScreens.MyRecipesScreen) }) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = Icons.Default.FilterList.name
                    )
                }
            },
            placeholder = { Text("Rechercher") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = Icons.Default.Search.name
                )
            },
        ) {
        }

        if (filtersApplied) {
            Button(onClick = { recipeVM.resetMyRecipesFiltersApplied() }) {
                Text("Réinitaliser les filtres")
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (filtersApplied) {
                val filteredByFilters = recipeVM.getMyRecipesFilteredRecipes().collectAsState(initial = emptyList())

                if (filteredByFilters.value.isNullOrEmpty()) {
                    Text("Aucune recette ne correspond à vos filtres")
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(filteredByFilters.value.filter {
                            it.title.contains(searchQuery, ignoreCase = true)
                        }) { recipe ->
                            RawButton(onClick = { onDetails(recipe) }) {
                                RecipeCard(recipe)
                            }
                        }
                    }
                }
            } else {
                if (filteredRecipes.isEmpty()) {
                    Text("Aucune recette ne correspond à votre recherche")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(filteredRecipes) { recipe ->
                            RawButton(onClick = { onDetails(recipe) }) {
                                RecipeCard(recipe)
                            }
                        }
                    }
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun MyRecipesActivityPreview() {
//    MyRecipesScreen(recipeState = RecipeViewModel().stateFlow.collectAsState().value)
//}