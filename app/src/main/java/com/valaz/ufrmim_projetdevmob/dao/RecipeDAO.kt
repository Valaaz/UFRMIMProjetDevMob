package com.valaz.ufrmim_projetdevmob.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.valaz.ufrmim_projetdevmob.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Flow<Recipe>

    @Query("SELECT * FROM recipes WHERE favorite = true")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id IN (:recipeIds)")
    fun loadAllByIds(recipeIds: IntArray): List<Recipe>

    @Query("SELECT * FROM recipes WHERE title LIKE :name LIMIT 1")
    fun findByTitle(name: String): Recipe

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Insert
    suspend fun insertAll(recipes: List<Recipe>)

    @Delete
    fun delete(recipe: Recipe)

    @Query("DELETE FROM recipes")
    suspend fun clearAll()
}