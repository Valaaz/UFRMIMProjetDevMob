package com.valaz.ufrmim_projetdevmob.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.valaz.ufrmim_projetdevmob.model.Ingredient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(
    ingredient: Ingredient? = null,
    onDismissRequest: () -> Unit,
    onConfirmation: (Ingredient) -> Unit
) {
    var ingredientName by remember {
        mutableStateOf(ingredient?.name ?: "")
    }
    var ingredientQuantity by remember {
        mutableStateOf(ingredient?.quantity ?: "")
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(15.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Ajouter un ingrédient",
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                )
                OutlinedTextField(
                    value = ingredientName,
                    onValueChange = { ingredientName = it },
                    label = {
                        Text(
                            text = "Nom"
                        )
                    })
                OutlinedTextField(
                    value = ingredientQuantity,
                    onValueChange = { ingredientQuantity = it },
                    label = {
                        Text(
                            text = "Quantité"
                        )
                    },
                    )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Button(onClick = onDismissRequest) {
                        Text("Annuler")
                    }
                    Button(onClick = {
                        if (ingredientName.isNotBlank() && ingredientQuantity.isNotBlank()) {
                            val newIngredient = Ingredient(
                                name = ingredientName,
                                quantity = ingredientQuantity
                            )
                            onConfirmation(newIngredient)
                        }
                    }) {
                        Text("Valider")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddIngredientDialogPreview() {
    AddIngredientDialog(onDismissRequest = {}, onConfirmation = {})
}