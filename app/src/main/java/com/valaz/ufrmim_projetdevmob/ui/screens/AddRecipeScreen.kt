package com.valaz.ufrmim_projetdevmob.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddRecipeScreen() {
    Scaffold { innerPadding ->
        Modifier.padding(innerPadding)
        Text(text = "Ajouter")
    }
}