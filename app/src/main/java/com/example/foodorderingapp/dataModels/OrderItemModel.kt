package com.example.foodorderingapp.dataModels
data class OrderItemModel(
    var userUid: String? = null,
    var userName: String? = null,
    var userAddress: String? = null,
    var userPhoneNumber: String? = null,
    var totalPrice: Int = 0,
    var userOrderList: MutableList<CartItemModel> = mutableListOf(),
    var orderAccepted: Boolean = false,
    var paymentReceived: Boolean = false,
    var itemPushKey: String? = null,
    var currentTime: Long = 0,
)