package com.valaz.ufrmim_projetdevmob.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.valaz.ufrmim_projetdevmob.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes WHERE created = false")
    fun getNotCreatedRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE created = false")
    suspend fun getDiscoverRecipes(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Flow<Recipe>

    @Query("SELECT * FROM recipes WHERE favorite = true OR created = true")
    fun getFavoriteAndCreatedRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE favorite = true OR created = true")
    suspend fun getMyRecipes(): List<Recipe>

    @Query("SELECT ingredients FROM recipes")
    fun getAllIngredients(): Flow<List<String>>

    @Query("SELECT favorite FROM recipes WHERE id = :recipeId")
    fun isRecipeFavorite(recipeId: Int): Flow<Boolean>

    @Query("UPDATE recipes SET favorite = :isFavorite WHERE id = :recipeId")
    suspend fun setRecipeFavorite(recipeId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM recipes WHERE id IN (:recipeIds)")
    fun loadAllByIds(recipeIds: IntArray): List<Recipe>

    @Query("SELECT * FROM recipes WHERE title LIKE :name LIMIT 1")
    fun findByTitle(name: String): Recipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Insert
    suspend fun insertAll(recipes: List<Recipe>)

    @Query("DELETE FROM recipes WHERE id = :recipeId")
    suspend fun delete(recipeId: Int)

    @Query("DELETE FROM recipes")
    suspend fun clearAll()
}