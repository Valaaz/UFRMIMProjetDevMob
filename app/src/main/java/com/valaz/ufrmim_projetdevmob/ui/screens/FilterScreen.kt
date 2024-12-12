package com.valaz.ufrmim_projetdevmob.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.valaz.ufrmim_projetdevmob.db.AppDatabase
import com.valaz.ufrmim_projetdevmob.ui.components.IntegerRangeSlider
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesScreens
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(backAction: () -> Unit, recipeVM: RecipeViewModel, screen: RecipesScreens) {
    val allIngredientsName = recipeVM.getAllIngredientsName().collectAsState(initial = emptyList())

    var prepTime by remember {
        mutableStateOf(0..999)
    }

    var cookTime by remember {
        mutableStateOf(0..999)
    }

    var servings by remember {
        mutableStateOf(0..100)
    }

    var selectedIngredients by remember {
        mutableStateOf(listOf<String>())
    }

    LaunchedEffect(allIngredientsName.value) {
        selectedIngredients = allIngredientsName.value
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Filtres",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = backAction) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = Icons.Default.ArrowBackIosNew.name,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Temps de préparation
                Text(
                    "Temps de préparation",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )
                IntegerRangeSlider(
                    range = prepTime,
                    onRangeChange = { prepTime = it },
                    valueRange = 0..999
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${prepTime.start} min")
                    Text("${prepTime.endInclusive} min")
                }

                // Temps de cuisson
                Text(
                    "Temps de cuisson",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )
                IntegerRangeSlider(
                    range = cookTime,
                    onRangeChange = { cookTime = it },
                    valueRange = 0..999
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${cookTime.start} min")
                    Text("${cookTime.endInclusive} min")
                }

                // Nombre de personne
                Text(
                    "Nombre de personne",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )
                IntegerRangeSlider(
                    range = servings,
                    onRangeChange = { servings = it },
                    valueRange = 0..100
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${servings.start}")
                    Text("${servings.endInclusive}")
                }

                // Ingrédients
                Text(
                    "Ingredients",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    allIngredientsName.value.chunked(2).forEach { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowItems.forEach { ingredientName ->
                                val isSelected = selectedIngredients.contains(ingredientName)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        selectedIngredients = if (isSelected) {
                                            selectedIngredients - ingredientName
                                        } else {
                                            selectedIngredients + ingredientName
                                        }
                                    },
                                    label = { Text(ingredientName) },
                                    trailingIcon = {
                                        if (isSelected) Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = Icons.Default.Check.name
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        if (screen.equals(RecipesScreens.DiscoverRecipesScreen)) {
                            recipeVM.applyDiscoverFilters(
                                prepTime = prepTime,
                                cookTime = cookTime,
                                servings = servings,
                                selectedIngredients = selectedIngredients
                            )
                        } else if (screen.equals(RecipesScreens.MyRecipesScreen)) {
                            recipeVM.applyMyRecipesFilters(
                                prepTime = prepTime,
                                cookTime = cookTime,
                                servings = servings,
                                selectedIngredients = selectedIngredients
                            )
                        }
                        backAction()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Valider")
                }

            }
        }
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    FilterScreen(
        backAction = {}, recipeVM = RecipeViewModel(
            Room.databaseBuilder(
                context = LocalContext.current,
                AppDatabase::class.java, "cook-database"
            ).fallbackToDestructiveMigration().build().recipeDao()
        ),
        screen = RecipesScreens.DiscoverRecipesScreen
    )
}