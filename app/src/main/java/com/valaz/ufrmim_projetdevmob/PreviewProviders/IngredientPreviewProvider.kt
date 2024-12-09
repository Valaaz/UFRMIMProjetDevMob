package com.valaz.ufrmim_projetdevmob.PreviewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.valaz.ufrmim_projetdevmob.model.Ingredient

class IngredientPreviewParameterProvider : PreviewParameterProvider<Ingredient> {
    override val values = sequenceOf(
        Ingredient(
            name = "Pâtes",
            quantity = "200g"
        )
    )
}