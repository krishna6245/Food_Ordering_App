package com.example.adminpanel.dataModels

data class UserModel(
    var userName : String? = null,
    var restaurantName : String? =null,
    val email : String? = null,
    var phoneNumber : String? = null,
    var location : String? = null,
)
