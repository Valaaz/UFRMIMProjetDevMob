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
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverRecipesScreen(
    recipeVM: RecipeViewModel,
    onDetails: (Recipe) -> Unit,
    filterScreen: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    val recipes = recipeVM.getRecipesList().collectAsState(initial = null)
    val filtersApplied by recipeVM.filtersApplied.collectAsState()

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
            query = "",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {},
            leadingIcon = {
                IconButton(onClick = filterScreen) {
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
            Button(onClick = { recipeVM.resetFiltersApplied() }) {
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
                val filteredRecipes = recipeVM.getFilteredRecipes().collectAsState(initial = emptyList())

                if (filteredRecipes.value.isNullOrEmpty()) {
                    Text("Aucune recette ne correspond à vos filtres")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(filteredRecipes.value) { recipe ->
                            RawButton(onClick = { onDetails(recipe) }) {
                                RecipeCard(recipe)
                            }
                        }
                    }
                }
            } else {
                if (recipes.value.isNullOrEmpty()) {
                    Text("Vous n'avez aucune recette créée ou favorite")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(recipes.value!!) { recipe ->
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