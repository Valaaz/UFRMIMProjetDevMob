package com.valaz.ufrmim_projetdevmob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valaz.ufrmim_projetdevmob.dao.RecipeDao
import com.valaz.ufrmim_projetdevmob.db.Converters
import com.valaz.ufrmim_projetdevmob.model.Ingredient
import com.valaz.ufrmim_projetdevmob.model.Recipe
import com.valaz.ufrmim_projetdevmob.ui.navigation.RecipesScreens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {
    var selectedRecipeId: Int = -1
    lateinit var currentScreen: RecipesScreens

    private val _discoverRecipesList = MutableStateFlow<List<Recipe>>(emptyList())
    private val _myRecipesList = MutableStateFlow<List<Recipe>>(emptyList())

    private val _filteredDiscoveredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    private val _filtersDiscoverApplied = MutableStateFlow(false)
    val filtersDiscoverApplied: StateFlow<Boolean> = _filtersDiscoverApplied

    private val _filteredMyRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    private val _filtersMyRecipesApplied = MutableStateFlow(false)
    val filtersMyRecipesApplied: StateFlow<Boolean> = _filtersMyRecipesApplied

    init {
        viewModelScope.launch {
            val discoverRecipeList = recipeDao.getDiscoverRecipes()
            _discoverRecipesList.value = discoverRecipeList
        }
    }

    fun getDiscoverRecipesList(): Flow<List<Recipe>> {
        return recipeDao.getNotCreatedRecipes()
    }

    fun getMyRecipesList(): Flow<List<Recipe>> {
        return recipeDao.getFavoriteAndCreatedRecipes()
    }

    fun getAllIngredientsName(): Flow<List<String>> {
        return recipeDao.getAllIngredients().map { recipeIngredientsList ->
            recipeIngredientsList.flatMap { ingredientsJson ->
                Converters().toIngredientList(ingredientsJson)
            }.map { ingredient ->
                ingredient.name
            }.distinct()
        }
    }

    fun getRecipeById(recipeId: Int): Flow<Recipe> {
        return recipeDao.getRecipeById(recipeId)
    }

    fun getSelectedRecipe(): Flow<Recipe> {
        return getRecipeById(selectedRecipeId)
    }

    fun setSelectedRecipe(recipeId: Int) {
        selectedRecipeId = recipeId
    }

    fun isRecipeFavorite(recipeId: Int): Flow<Boolean> {
        return recipeDao.isRecipeFavorite(recipeId)
    }

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            val isCurrentlyFavorite = recipeDao.isRecipeFavorite(recipeId).first()
            recipeDao.setRecipeFavorite(recipeId, !isCurrentlyFavorite)
        }
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

    fun deleteRecipe(recipeId: Int) {
        viewModelScope.launch {
            recipeDao.delete(recipeId)
        }
    }

    fun applyDiscoverFilters(
        prepTime: IntRange,
        cookTime: IntRange,
        servings: IntRange,
        selectedIngredients: List<String>
    ) {
        _filteredDiscoveredRecipes.value = _discoverRecipesList.value.filter { recipe ->
            recipe.prepTime in prepTime
                    &&
                    recipe.cookTime in cookTime
                    &&
                    recipe.servings in servings
            //&&
//                    selectedIngredients.all { ingredient ->
//                        recipe.ingredients.any { it.name == ingredient }
//                    }
        }
        _filtersDiscoverApplied.value = true
    }

    fun applyMyRecipesFilters(
        prepTime: IntRange,
        cookTime: IntRange,
        servings: IntRange,
        selectedIngredients: List<String>
    ) {
        viewModelScope.launch {
            val myRecipeList = recipeDao.getMyRecipes()
            _myRecipesList.value = myRecipeList
        }

        _filteredMyRecipes.value = _myRecipesList.value.filter { recipe ->
            recipe.prepTime in prepTime
                    &&
                    recipe.cookTime in cookTime
                    &&
                    recipe.servings in servings
            //&&
//                    selectedIngredients.all { ingredient ->
//                        recipe.ingredients.any { it.name == ingredient }
//                    }
        }
        _filtersMyRecipesApplied.value = true
    }

    fun resetDiscoveredFiltersApplied() {
        _filtersDiscoverApplied.value = false
    }

    fun resetMyRecipesFiltersApplied() {
        _filtersMyRecipesApplied.value = false
    }

    fun getDiscoveredFilteredRecipes(): Flow<List<Recipe>> = _filteredDiscoveredRecipes.asStateFlow()

    fun getMyRecipesFilteredRecipes(): Flow<List<Recipe>> = _filteredMyRecipes.asStateFlow()

    fun init() {
        val recipes = listOf(
            Recipe(
                favorite = true,
                title = "Poulet au citron et à la crème aux champignons",
                description = "Une recette savoureuse de poulet mariné au citron et aux herbes, idéale pour un dîner léger.",
                prepTime = 15,
                cookTime = 30,
                servings = 4,
                imageUrl = "https://images.radio-canada.ca/v1/alimentation/recette/1x1/poulet-olives-citrons.jpg",
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
                prepTime = 20,
                cookTime = 40,
                servings = 6,
                imageUrl = "https://img.cuisineaz.com/660x660/2018/03/19/i137990-soupe-aux-legumes-d-hiver.jpeg",
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
                prepTime = 20,
                cookTime = 35,
                servings = 6,
                imageUrl = "https://assets.afcdn.com/recipe/20220128/128250_w1024h1024c1cx1294cy688cxt0cyt0cxb2037cyb1472.webp",
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
                prepTime = 15,
                cookTime = 45,
                servings = 4,
                imageUrl = "https://www.julieandrieu.com/media/cache/web_recipe_detail/uploads/recettes/vegetarien/ratatouille-comme-a-nice.jpg",
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
                prepTime = 15,
                cookTime = 25,
                servings = 8,
                imageUrl = "https://assets.afcdn.com/recipe/20161114/26634_w1024h768c1cx2736cy1824.webp",
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
                prepTime = 10,
                cookTime = 15,
                servings = 4,
                imageUrl = "https://assets.afcdn.com/recipe/20211214/125831_w1024h1024c1cx866cy866cxt0cyt292cxb1732cyb1732.jpg",
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
            ),
            Recipe(
                favorite = false,
                title = "Quiche Lorraine",
                description = "Une tarte salée classique française garnie de lardons, de crème et de fromage.",
                prepTime = 20,
                cookTime = 35,
                servings = 6,
                imageUrl = "https://assets.afcdn.com/recipe/20221010/135915_w1024h1024c1cx999cy749cxt0cyt0cxb1999cyb1499.jpg",
                ingredients = listOf(
                    Ingredient(name = "Pâte brisée", quantity = "1 rouleau"),
                    Ingredient(name = "Lardons", quantity = "200 g"),
                    Ingredient(name = "Crème fraîche", quantity = "200 ml"),
                    Ingredient(name = "Œufs", quantity = "3"),
                    Ingredient(name = "Fromage râpé", quantity = "100 g"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée"),
                    Ingredient(name = "Muscade", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Préchauffer le four à 180°C.",
                    "Étaler la pâte dans un moule à tarte et piquer le fond avec une fourchette.",
                    "Faire revenir les lardons à la poêle.",
                    "Mélanger les œufs, la crème, le sel, le poivre et la muscade.",
                    "Répartir les lardons sur la pâte, verser le mélange par-dessus et saupoudrer de fromage.",
                    "Cuire au four pendant 35 minutes."
                )
            ),
            Recipe(
                favorite = false,
                title = "Poulet Basquaise",
                description = "Un plat traditionnel du Pays Basque composé de poulet mijoté avec des poivrons et des tomates.",
                prepTime = 15,
                cookTime = 40,
                servings = 4,
                imageUrl = "https://jemangefrancais.com/img/cms/image%20dillustration%20poulet%20basquaise.jpg",
                ingredients = listOf(
                    Ingredient(name = "Cuisses de poulet", quantity = "4"),
                    Ingredient(name = "Poivrons rouges", quantity = "2"),
                    Ingredient(name = "Poivrons verts", quantity = "2"),
                    Ingredient(name = "Tomates", quantity = "4"),
                    Ingredient(name = "Oignon", quantity = "1"),
                    Ingredient(name = "Ail", quantity = "2 gousses"),
                    Ingredient(name = "Huile d'olive", quantity = "3 cuillères à soupe"),
                    Ingredient(name = "Vin blanc", quantity = "100 ml"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Faire dorer les cuisses de poulet dans une cocotte avec l'huile d'olive.",
                    "Ajouter l'oignon émincé, l'ail haché et les poivrons en lamelles.",
                    "Incorporer les tomates coupées en dés et déglacer avec le vin blanc.",
                    "Saler, poivrer et laisser mijoter à feu doux pendant 40 minutes."
                )
            ),
            Recipe(
                favorite = false,
                title = "Gratin Dauphinois",
                description = "Un gratin crémeux à base de pommes de terre et de crème, un classique français.",
                prepTime = 15,
                cookTime = 50,
                servings = 6,
                imageUrl = "https://assets.afcdn.com/recipe/20201217/116563_w1024h768c1cx1116cy671cxt0cyt0cxb2232cyb1342.webp",
                ingredients = listOf(
                    Ingredient(name = "Pommes de terre", quantity = "1 kg"),
                    Ingredient(name = "Crème fraîche", quantity = "400 ml"),
                    Ingredient(name = "Lait", quantity = "200 ml"),
                    Ingredient(name = "Ail", quantity = "1 gousse"),
                    Ingredient(name = "Fromage râpé", quantity = "100 g"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée"),
                    Ingredient(name = "Muscade", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Préchauffer le four à 180°C.",
                    "Éplucher et couper les pommes de terre en fines rondelles.",
                    "Frotter un plat à gratin avec la gousse d'ail.",
                    "Mélanger la crème, le lait, le sel, le poivre et la muscade.",
                    "Disposer les pommes de terre en couches dans le plat, en les recouvrant du mélange à chaque couche.",
                    "Parsemer de fromage râpé et cuire au four pendant 50 minutes."
                )
            ),
            Recipe(
                favorite = false,
                title = "Soupe à l'Oignon",
                description = "Une soupe réconfortante composée d'oignons caramélisés et gratinée au fromage.",
                prepTime = 10,
                cookTime = 40,
                servings = 4,
                imageUrl = "https://media.istockphoto.com/id/601123554/fr/photo/gratin-de-soupe-%C3%A0-loignon-%C3%A0-la-fran%C3%A7aise.jpg?s=612x612&w=0&k=20&c=AaTmz2BlUBEGzn9nEugt8-e6M3Q7N5CrmB_Z91v3B_k=",
                ingredients = listOf(
                    Ingredient(name = "Oignons", quantity = "6"),
                    Ingredient(name = "Beurre", quantity = "50 g"),
                    Ingredient(name = "Farine", quantity = "2 cuillères à soupe"),
                    Ingredient(name = "Bouillon de bœuf", quantity = "1 litre"),
                    Ingredient(name = "Vin blanc", quantity = "100 ml"),
                    Ingredient(name = "Pain", quantity = "4 tranches"),
                    Ingredient(name = "Fromage râpé", quantity = "100 g"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Émincer les oignons et les faire caraméliser dans une casserole avec le beurre.",
                    "Ajouter la farine et bien mélanger.",
                    "Incorporer le bouillon et le vin blanc, puis laisser mijoter 30 minutes.",
                    "Verser la soupe dans des bols, déposer une tranche de pain, parsemer de fromage et gratiner au four."
                )
            ),
            Recipe(
                favorite = false,
                title = "Bœuf Bourguignon",
                description = "Un ragoût de bœuf mijoté avec du vin rouge et des légumes, une spécialité bourguignonne.",
                prepTime = 20,
                cookTime = 150,
                servings = 6,
                imageUrl = "https://img.cuisineaz.com/660x660/2013/12/20/i73725-boeuf-bourguignon.jpg",
                ingredients = listOf(
                    Ingredient(name = "Viande de bœuf", quantity = "1 kg"),
                    Ingredient(name = "Vin rouge", quantity = "750 ml"),
                    Ingredient(name = "Carottes", quantity = "3"),
                    Ingredient(name = "Oignons", quantity = "2"),
                    Ingredient(name = "Lardons", quantity = "200 g"),
                    Ingredient(name = "Champignons", quantity = "200 g"),
                    Ingredient(name = "Farine", quantity = "2 cuillères à soupe"),
                    Ingredient(name = "Bouquet garni", quantity = "1"),
                    Ingredient(name = "Huile", quantity = "2 cuillères à soupe"),
                    Ingredient(name = "Sel", quantity = "1 pincée"),
                    Ingredient(name = "Poivre", quantity = "1 pincée")
                ),
                steps = listOf(
                    "Faire revenir la viande coupée en morceaux dans une cocotte avec de l'huile.",
                    "Ajouter les oignons et les lardons, puis saupoudrer de farine.",
                    "Incorporer les carottes en rondelles, les champignons, le vin rouge et le bouquet garni.",
                    "Saler, poivrer et laisser mijoter à feu doux pendant 2h30."
                )
            )
        )
        viewModelScope.launch {
            recipeDao.clearAll()
        }
        addAllRecipes(recipes)
    }
}