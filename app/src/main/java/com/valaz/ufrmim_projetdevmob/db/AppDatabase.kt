package com.valaz.ufrmim_projetdevmob.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.valaz.ufrmim_projetdevmob.dao.RecipeDao
import com.valaz.ufrmim_projetdevmob.model.Recipe

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}