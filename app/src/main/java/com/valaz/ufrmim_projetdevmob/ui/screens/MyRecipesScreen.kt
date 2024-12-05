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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valaz.ufrmim_projetdevmob.model.Recipe
import com.valaz.ufrmim_projetdevmob.ui.components.RecipeCard
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeState
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRecipesScreen(recipeState: RecipeState) {
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
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = Icons.Default.FilterList.name
                )
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
        if (recipeState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val recipes = recipeState.recipes
            // val filteredList: List<Recipe> = recipes.filter { it.title.lowercase().contains(searchValue.text.lowercase()) }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(recipes) { recipe ->
                    RecipeCard(recipe)
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