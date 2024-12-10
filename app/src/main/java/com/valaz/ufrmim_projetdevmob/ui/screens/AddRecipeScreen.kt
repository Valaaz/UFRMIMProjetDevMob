package com.valaz.ufrmim_projetdevmob.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valaz.ufrmim_projetdevmob.model.Ingredient
import com.valaz.ufrmim_projetdevmob.ui.components.AddIngredientDialog
import com.valaz.ufrmim_projetdevmob.ui.components.AddStepDialog
import com.valaz.ufrmim_projetdevmob.ui.components.AddedIngredientCard
import com.valaz.ufrmim_projetdevmob.ui.components.AddedStepCard
import com.valaz.ufrmim_projetdevmob.ui.components.NumberInputField
import com.valaz.ufrmim_projetdevmob.ui.components.StepCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(backAction: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Créer une recette",
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
                InformationsSection()
                HorizontalDivider()
                IngredientsSection()
                HorizontalDivider()
                StepsSection()
                HorizontalDivider()
                PhotoSection()
            }
        }
    }
}

@Composable
fun InformationsSection() {
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
        // Temps de préparation
        Column {
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
                    text = "Temps de préparation (en min) : ",
                )
            }
            NumberInputField(defaultValue = 1, size = 40.dp)
        }

        // Temps de cuisson
        Column {
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
                    text = "Temps de cuisson (en min) : ",
                )
            }
            NumberInputField(defaultValue = 1, size = 40.dp)
        }

        // Nombre de personnes
        Column {
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
                    text = "Nombre de personnes : ",
                )
            }
            NumberInputField(defaultValue = 1, size = 40.dp)
        }
    }
}

@Composable
fun IngredientsSection() {
    var ingredients by remember { mutableStateOf(mutableListOf<Ingredient>()) }
    var openAddIngredientDialog by remember { mutableStateOf(false) }
    var selectedIngredient by remember { mutableStateOf<Ingredient?>(null) }

    Column {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            ingredients.forEach { ingredient ->
                AddedIngredientCard(
                    ingredient = ingredient,
                    deleteIngredient = { ingredientToRemove ->
                        ingredients =
                            ingredients.toMutableList().apply { remove(ingredientToRemove) }
                    },
                    updateIngredient = {
                        selectedIngredient = ingredient
                        openAddIngredientDialog = true
                    })
            }
        }
        Button(
            onClick = {
                selectedIngredient = null
                openAddIngredientDialog = true
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Ajouter un ingrédient")
        }

        if (openAddIngredientDialog) {
            AddIngredientDialog(
                ingredient = selectedIngredient,
                onDismissRequest = { openAddIngredientDialog = false },
                onConfirmation = { newIngredient ->
                    if (selectedIngredient == null) {
                        // Ajout
                        ingredients.add(newIngredient)
                    } else {
                        // Modification
                        ingredients = ingredients.toMutableList().map {
                            if (it == selectedIngredient) newIngredient else it
                        }.toMutableList()
                    }
                    openAddIngredientDialog = false
                }
            )
        }
    }
}

@Composable
fun StepsSection() {
    var steps by remember { mutableStateOf(mutableListOf<String>()) }
    var openAddStepDialog by remember { mutableStateOf(false) }
    var selectedStep by remember { mutableStateOf<String?>(null) }

    Column {
        Text(
            text = "Étapes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            steps.forEachIndexed { index, step ->
                AddedStepCard(step = step, stepNumber = index + 1, deleteStep = { stepToRemove ->
                    steps =
                        steps.toMutableList().apply { remove(stepToRemove) }
                },
                    updateStep = {
                        selectedStep = step
                        openAddStepDialog = true
                    })
            }
        }
        Button(onClick = {
            selectedStep = null
            openAddStepDialog = true
        }, modifier = Modifier.align(Alignment.End)) {
            Text("Ajouter une étape")
        }
    }

    if (openAddStepDialog) {
        AddStepDialog(
            step = selectedStep,
            stepNumber = steps.size + 1,
            onDismissRequest = { openAddStepDialog = false },
            onConfirmation = { newStep ->
                if (selectedStep == null) {
                    // Ajout
                    steps.add(newStep)
                } else {
                    // Modification
                    steps = steps.toMutableList().map {
                        if (it == selectedStep) newStep else it
                    }.toMutableList()
                }
                openAddStepDialog = false
            }
        )
    }

}

@Composable
fun PhotoSection() {
    Text(
        text = "Photo",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left,
        modifier = Modifier.fillMaxWidth()
    )
    IconButton(
        onClick = { /*TODO*/ }, modifier = Modifier
            .border(width = 1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            .size(250.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AddPhotoAlternate,
            contentDescription = Icons.Default.AddPhotoAlternate.name,
            modifier = Modifier.size(70.dp)
        )
    }
}

@Preview
@Composable
fun AddRecipeScreenPreview() {
    AddRecipeScreen(backAction = {})
}