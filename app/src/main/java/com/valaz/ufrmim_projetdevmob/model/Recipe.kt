package com.valaz.ufrmim_projetdevmob.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "favorite") val favorite: Boolean,
    @ColumnInfo(name = "prep_time") val prepTime: Float,
    @ColumnInfo(name = "cook_time") val cookTime: Float,
    @ColumnInfo(name = "servings") val servings: Int,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "ingredients") val ingredients: List<Ingredient>,
    @ColumnInfo(name = "steps") val steps: List<String>,
)

@Entity(tableName = "ingredients")
data class Ingredient(
    val name: String,
    val quantity: String
)