package com.valaz.ufrmim_projetdevmob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valaz.ufrmim_projetdevmob.dao.RecipeDao
import com.valaz.ufrmim_projetdevmob.model.Ingredient
import com.valaz.ufrmim_projetdevmob.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {
    fun init() {
        val recipes = listOf(
            Recipe(
                favorite = true,
                title = "Poulet au citron et à la crème aux champignons",
                description = "Une recette savoureuse de poulet mariné au citron et aux herbes, idéale pour un dîner léger.",
                prepTime = 15f,
                cookTime = 30f,
                servings = 4,
                imageUrl = "https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                ingredients = listOf(
                    Ingredient(name = "Filets de poulet", quantity = "600g"),
                    Ingredient(name = "Citron", quantity = "2"),
                    Ingredient(name = "Ail", quantity = "2 gousses"),
                    Ingredient(name = "Huile d'olive", quantity = "3 cuillères à soupe"),
                    Ingredient(name = "Thym", quantity = "1 cuillère à café"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Presser les citrons pour obtenir le jus.",
                    "Mélanger le jus de citron, l'ail haché, le thym, l'huile d'olive, le sel et le poivre.",
                    "Mariner les filets de poulet dans cette préparation pendant 15 minutes.",
                    "Préchauffer une poêle à feu moyen et cuire les filets de poulet pendant 7 minutes de chaque côté."
                )
            ),
            Recipe(
                favorite = false,
                title = "Soupe de légumes d'hiver",
                description = "Un bol réconfortant de soupe à base de légumes frais, parfait pour les journées froides.",
                prepTime = 20f,
                cookTime = 40f,
                servings = 6,
                imageUrl = "https://example.com/images/soupe-legumes-hiver.jpg",
                ingredients = listOf(
                    Ingredient(name = "Carottes", quantity = "500g"),
                    Ingredient(name = "Pommes de terre", quantity = "300g"),
                    Ingredient(name = "Poireaux", quantity = "2"),
                    Ingredient(name = "Oignon", quantity = "1"),
                    Ingredient(name = "Bouillon de légumes", quantity = "1 litre"),
                    Ingredient(name = "Crème fraîche", quantity = "100ml"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Éplucher et couper les légumes en morceaux.",
                    "Faire revenir l'oignon haché dans une marmite avec un peu d'huile.",
                    "Ajouter les légumes, couvrir de bouillon et laisser mijoter pendant 40 minutes.",
                    "Mixer la soupe et ajouter la crème fraîche avant de servir."
                )
            ),
            Recipe(
                favorite = false,
                title = "Tarte aux pommes",
                description = "Un classique de la pâtisserie française, facile à réaliser et toujours apprécié.",
                prepTime = 20f,
                cookTime = 35f,
                servings = 6,
                imageUrl = "https://example.com/images/tarte-aux-pommes.jpg",
                ingredients = listOf(
                    Ingredient(name = "Pâte feuilletée", quantity = "1 rouleau"),
                    Ingredient(name = "Pommes", quantity = "4"),
                    Ingredient(name = "Sucre", quantity = "50g"),
                    Ingredient(name = "Beurre", quantity = "30g"),
                    Ingredient(name = "Cannelle", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Préchauffer le four à 180°C.",
                    "Étaler la pâte feuilletée dans un moule à tarte.",
                    "Éplucher, évider et trancher les pommes.",
                    "Disposer les tranches de pommes sur la pâte.",
                    "Parsemer de sucre, de cannelle et de noisettes de beurre.",
                    "Enfourner pendant 35 minutes."
                )
            ),
            Recipe(
                favorite = false,
                title = "Ratatouille",
                description = "Une spécialité provençale composée de légumes mijotés avec des herbes aromatiques.",
                prepTime = 15f,
                cookTime = 45f,
                servings = 4,
                imageUrl = "https://example.com/images/ratatouille.jpg",
                ingredients = listOf(
                    Ingredient(name = "Courgettes", quantity = "2"),
                    Ingredient(name = "Aubergines", quantity = "2"),
                    Ingredient(name = "Poivrons rouges", quantity = "2"),
                    Ingredient(name = "Tomates", quantity = "4"),
                    Ingredient(name = "Oignon", quantity = "1"),
                    Ingredient(name = "Ail", quantity = "2 gousses"),
                    Ingredient(name = "Huile d'olive", quantity = "3 cuillères à soupe"),
                    Ingredient(name = "Herbes de Provence", quantity = "1 cuillère à soupe"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Couper tous les légumes en dés.",
                    "Faire revenir l'oignon et l'ail hachés dans une cocotte avec l'huile d'olive.",
                    "Ajouter les légumes et les herbes de Provence.",
                    "Laisser mijoter à feu doux pendant 45 minutes en remuant de temps en temps."
                )
            ),
            Recipe(
                favorite = false,
                title = "Brownies au chocolat",
                description = "Des brownies moelleux et fondants, parfaits pour les amateurs de chocolat.",
                prepTime = 15f,
                cookTime = 25f,
                servings = 8,
                imageUrl = "https://example.com/images/brownies-chocolat.jpg",
                ingredients = listOf(
                    Ingredient(name = "Chocolat noir", quantity = "200g"),
                    Ingredient(name = "Beurre", quantity = "150g"),
                    Ingredient(name = "Sucre", quantity = "150g"),
                    Ingredient(name = "Farine", quantity = "80g"),
                    Ingredient(name = "Oeufs", quantity = "3")
                ),
                steps = listOf(
                    "Préchauffer le four à 180°C.",
                    "Faire fondre le chocolat et le beurre au bain-marie.",
                    "Ajouter le sucre, les oeufs battus et la farine au mélange.",
                    "Verser la préparation dans un moule.",
                    "Enfourner pendant 25 minutes."
                )
            ),
            Recipe(
                favorite = false,
                title = "Pâtes à la carbonara",
                description = "Une recette italienne classique avec des pâtes crémeuses au lardons et parmesan.",
                prepTime = 10f,
                cookTime = 15f,
                servings = 4,
                imageUrl = "https://example.com/images/pates-carbonara.jpg",
                ingredients = listOf(
                    Ingredient(name = "Spaghetti", quantity = "400g"),
                    Ingredient(name = "Lardons", quantity = "150g"),
                    Ingredient(name = "Crème fraîche", quantity = "100ml"),
                    Ingredient(name = "Parmesan râpé", quantity = "50g"),
                    Ingredient(name = "Jaunes d'œufs", quantity = "2"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Faire cuire les pâtes dans une grande casserole d'eau salée.",
                    "Dans une poêle, faire revenir les lardons à feu moyen.",
                    "Mélanger la crème, les jaunes d'œufs et le parmesan dans un bol.",
                    "Égoutter les pâtes et les mélanger avec les lardons et la sauce.",
                    "Servir chaud avec un peu de parmesan sur le dessus."
                )
            )
        )
        viewModelScope.launch {
            recipeDao.clearAll()
        }
        addAllRecipes(recipes)
    }

    fun getRecipeList(): Flow<List<Recipe>> {
        return recipeDao.getRecipes()
    }

    fun getSelectedRecipe(): Recipe {
        return TODO("Provide the return value")
    }

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeDao.insertRecipe(recipe)
        }
    }

    fun addAllRecipes(recipes: List<Recipe>) {
        for (recipe: Recipe in recipes) {
            addRecipe(recipe)
        }
    }

    fun init2() {
        var recipe = Recipe(
            favorite = true,
            title = "Poulet au citron et à la crème aux champignons",
            description = "Une recette savoureuse de poulet mariné au citron et aux herbes, idéale pour un dîner léger.",
            prepTime = 15f,
            cookTime = 30f,
            servings = 4,
            imageUrl = "https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
            ingredients = listOf(
                Ingredient(name = "Filets de poulet", quantity = "600g"),
                Ingredient(name = "Citron", quantity = "2"),
                Ingredient(name = "Ail", quantity = "2 gousses"),
                Ingredient(name = "Huile d'olive", quantity = "3 cuillères à soupe"),
                Ingredient(name = "Thym", quantity = "1 cuillère à café"),
                Ingredient(name = "Sel", quantity = "1 pincée"),
                Ingredient(name = "Poivre", quantity = "1 pincée")
            ),
            steps = listOf(
                "Presser les citrons pour obtenir le jus.",
                "Mélanger le jus de citron, l'ail haché, le thym, l'huile d'olive, le sel et le poivre.",
                "Mariner les filets de poulet dans cette préparation pendant 15 minutes.",
                "Préchauffer une poêle à feu moyen et cuire les filets de poulet pendant 7 minutes de chaque côté."
            )
        )
//        viewModelScope.launch {
//            recipeDao.clearAll()
//        }
        addRecipe(recipe)
    }

}