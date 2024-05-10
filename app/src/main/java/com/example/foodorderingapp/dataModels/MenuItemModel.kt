package com.example.foodorderingapp.dataModels

data class MenuItemModel(
    val foodName : String? = null,
    val restaurantName : String? = null,
    val foodPrice : Int = 0,
    val foodImage : String? = null,
    val foodDescription : String? = null,
    val foodIngredients : MutableList<String>? = null,
)
