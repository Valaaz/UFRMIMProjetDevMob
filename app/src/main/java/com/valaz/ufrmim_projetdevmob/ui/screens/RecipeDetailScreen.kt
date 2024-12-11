package com.valaz.ufrmim_projetdevmob.ui.screens

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.valaz.ufrmim_projetdevmob.R
import com.valaz.ufrmim_projetdevmob.model.Ingredient
import com.valaz.ufrmim_projetdevmob.model.Recipe
import com.valaz.ufrmim_projetdevmob.ui.components.IngredientCard
import com.valaz.ufrmim_projetdevmob.ui.components.RecipePreviewParameterProvider
import com.valaz.ufrmim_projetdevmob.ui.components.StepCard
import com.russhwolf.settings.Settings
import com.valaz.ufrmim_projetdevmob.dao.RecipeDao
import com.valaz.ufrmim_projetdevmob.db.AppDatabase
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(backAction: () -> Unit, recipe: Recipe?, recipeVM: RecipeViewModel) {
    val isFavorite = recipe?.id?.let { id ->
        recipeVM.isRecipeFavorite(id).collectAsState(initial = false).value
    } ?: false
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Détail recette",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineMedium
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
                actions = {
                    IconButton(onClick = {
                        recipe?.id?.let { recipeId ->
                            recipeVM.toggleFavorite(recipeId)
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) Icons.Default.Favorite.name else Icons.Default.FavoriteBorder.name,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),

                )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            recipe?.let { recipe ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = recipe.imageUrl,
                        contentDescription = "Banner of ${recipe.title}",
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .fillMaxWidth(),
                        placeholder = painterResource(R.drawable.recette_non_disponible),
                        fallback = painterResource(R.drawable.recette_non_disponible),
                        error = painterResource(R.drawable.recette_non_disponible),
                    )
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    HorizontalDivider()
                    InformationsSection(recipe = recipe)
                    HorizontalDivider()
                    IngredientsSection(ingredients = recipe.ingredients)
                    HorizontalDivider()
                    StepsSection(steps = recipe.steps)
                }
            } ?: run {
                Text(
                    "Aucune recette à afficher", modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}

@Composable
fun InformationsSection(recipe: Recipe) {
    val spaceBtwIconText = 5.dp

    Text(
        text = "Informations",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left,
        modifier = Modifier.fillMaxWidth()
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spaceBtwIconText)
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = Icons.Default.Timer.name,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Temps de préparation : ${DecimalFormat("#.##").format(recipe.prepTime)} min",
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spaceBtwIconText)
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = Icons.Default.Timer.name,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Temps de cuisson : ${DecimalFormat("#.##").format(recipe.cookTime)} min",
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spaceBtwIconText)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = Icons.Default.Person.name,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Nombre de personnes : ${recipe.servings}",
            )
        }
    }
}

@Composable
fun IngredientsSection(ingredients: List<Ingredient>) {
    Text(
        text = "Ingredients",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left,
        modifier = Modifier.fillMaxWidth()
    )
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.height(400.dp)) {
        items(ingredients) { ingredient ->
            IngredientCard(ingredient = ingredient)
        }
    }
}

@Composable
fun StepsSection(steps: List<String>) {
    Text(
        text = "Étapes",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left,
        modifier = Modifier.fillMaxWidth()
    )
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        steps.forEachIndexed { index, step ->
            StepCard(step = step, stepNumber = index)
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun RecipeDetailScreenPreview(@PreviewParameter(RecipePreviewParameterProvider::class) recipe: Recipe) {
//    RecipeDetailScreen(backAction = {}, recipe = recipe)
//}