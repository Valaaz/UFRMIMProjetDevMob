package com.valaz.ufrmim_projetdevmob.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import coil3.compose.rememberAsyncImagePainter
import com.valaz.ufrmim_projetdevmob.db.AppDatabase
import com.valaz.ufrmim_projetdevmob.model.Ingredient
import com.valaz.ufrmim_projetdevmob.model.Recipe
import com.valaz.ufrmim_projetdevmob.ui.components.AddIngredientDialog
import com.valaz.ufrmim_projetdevmob.ui.components.AddStepDialog
import com.valaz.ufrmim_projetdevmob.ui.components.AddedIngredientCard
import com.valaz.ufrmim_projetdevmob.ui.components.AddedStepCard
import com.valaz.ufrmim_projetdevmob.ui.components.NumberInputField
import com.valaz.ufrmim_projetdevmob.ui.components.RawButton
import com.valaz.ufrmim_projetdevmob.viewmodel.RecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(backAction: () -> Unit, recipeVM: RecipeViewModel) {
    var title by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf(0) }
    var cookTime by remember { mutableStateOf(0) }
    var servings by remember { mutableStateOf(0) }
    var ingredients by remember { mutableStateOf(listOf<Ingredient>()) }
    var steps by remember { mutableStateOf(listOf<String>()) }
    var imageUri by remember { mutableStateOf<String>("") }

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
                InformationsSection(
                    title = title,
                    onTitleChange = { title = it },
                    prepTime = prepTime,
                    onPrepTimeChange = { prepTime = it },
                    cookTime = cookTime,
                    onCookTimeChange = { cookTime = it },
                    servings = servings,
                    onServingsChange = { servings = it }
                )
                HorizontalDivider()
                IngredientsSection(
                    ingredients = ingredients,
                    onIngredientsChange = { ingredients = it },
                )
                HorizontalDivider()
                StepsSection(
                    steps = steps,
                    onStepsChange = { steps = it },
                )
                HorizontalDivider()
                PhotoSection()
                HorizontalDivider()
                Button(onClick = {
                    recipeVM.addRecipe(
                        Recipe(
                            title = title,
                            isCreated = true,
                            description = "",
                            prepTime = prepTime,
                            cookTime = cookTime,
                            servings = servings,
                            ingredients = ingredients,
                            steps = steps,
                            imageUrl = ""
                        )
                    )
                    backAction()
                }) {
                    Text("Valider")
                }
            }
        }
    }
}

@Composable
fun InformationsSection(
    title: String,
    onTitleChange: (String) -> Unit,
    prepTime: Int,
    onPrepTimeChange: (Int) -> Unit,
    cookTime: Int,
    onCookTimeChange: (Int) -> Unit,
    servings: Int,
    onServingsChange: (Int) -> Unit
) {
    val spaceBtwIconText = 5.dp

    Text(
        text = "Informations",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left,
        modifier = Modifier.fillMaxWidth()
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth().padding(bottom = 10.dp)
    ) {
        // Temps de préparation
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text("Titre de la recette", style = MaterialTheme.typography.titleMedium) }, modifier = Modifier.fillMaxWidth()
            )
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
                    style = MaterialTheme.typography.titleMedium
                )
            }
            NumberInputField(value = prepTime, onValueChange = onPrepTimeChange, size = 40.dp)
        }

        // Temps de cuisson
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
                    style = MaterialTheme.typography.titleMedium
                )
            }
            NumberInputField(value = cookTime, onValueChange = onCookTimeChange, size = 40.dp)
        }

        // Nombre de personnes
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
                    style = MaterialTheme.typography.titleMedium
                )
            }
            NumberInputField(value = servings, onValueChange = onServingsChange, size = 40.dp)
        }
    }
}

@Composable
fun IngredientsSection(
    ingredients: List<Ingredient>,
    onIngredientsChange: (List<Ingredient>) -> Unit
) {
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
        ) {
            ingredients.forEach { ingredient ->
                AddedIngredientCard(
                    ingredient = ingredient,
                    deleteIngredient = { ingredientToRemove ->
                        onIngredientsChange(ingredients - ingredientToRemove)
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
                        onIngredientsChange(ingredients + newIngredient)
                    } else {
                        // Modification
                        onIngredientsChange(
                            ingredients.map {
                                if (it == selectedIngredient) newIngredient else it
                            }
                        )
                    }
                    openAddIngredientDialog = false
                }
            )
        }
    }
}

@Composable
fun StepsSection(steps: List<String>, onStepsChange: (List<String>) -> Unit) {
    var openAddStepDialog by remember { mutableStateOf(false) }
    var selectedStep by remember { mutableStateOf<String?>(null) }

    Column {
        Text(
            text = "Étapes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            steps.forEachIndexed { index, step ->
                AddedStepCard(step = step, stepNumber = index + 1, deleteStep = { stepToRemove ->
                    onStepsChange(steps - stepToRemove)
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
                    onStepsChange(steps + newStep)
                } else {
                    // Modification
                    onStepsChange(
                        steps.map {
                            if (it == selectedStep) newStep else it
                        }
                    )
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
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    RawButton(
        onClick = {
            launcher.launch("image/*")
        },
        modifier = Modifier
            .border(width = 1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            .size(250.dp)
    ) {
        if (imageUri != null) {
            val painter: Painter =
                rememberAsyncImagePainter(model = imageUri)
            Image(
                painter = painter,
                contentDescription = "Image sélectionnée",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(10.dp))
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Default.AddPhotoAlternate,
                    contentDescription = Icons.Default.AddPhotoAlternate.name,
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun AddRecipeScreenPreview() {
    AddRecipeScreen(backAction = {}, recipeVM = RecipeViewModel(
        Room.databaseBuilder(
            LocalContext.current,
        AppDatabase::class.java, "cook-database"
    ).fallbackToDestructiveMigration().build().recipeDao()))
}