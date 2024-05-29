package com.example.foodorderingapp.dataModels

data class CartItemModel(
    val menuItem: MenuItemModel? = null,
    var quantity: Int? = null,
)
