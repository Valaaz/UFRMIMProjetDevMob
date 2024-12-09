package com.valaz.ufrmim_projetdevmob.mapper;

import com.valaz.ufrmim_projetdevmob.model.Ingredient
import com.valaz.ufrmim_projetdevmob.model.Recipe;
import com.valaz.ufrmim_projetdevmob.model.SingleIngredientDto
import com.valaz.ufrmim_projetdevmob.model.SingleRecipeDto;

class RecipeMapper {
    fun mapRecipeDtoToRecipe(recipeDto: SingleRecipeDto): Recipe {
        return with(recipeDto) {
            Recipe(
                id = id,
                favorite = favorite,
                title = title,
                description = description,
                prep_time = prep_time.toFloat(),
                cook_time = cook_time.toFloat(),
                servings = servings,
                imageUrl = image,
                ingredients = ingredients.map { it.toIngredient() },
                steps = steps
            )
        }
    }
}

fun SingleIngredientDto.toIngredient(): Ingredient {
    return Ingredient(
        name = name,
        quantity = quantity
    )
}