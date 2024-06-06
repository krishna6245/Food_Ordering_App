package com.example.adminpanel.dataModels

import android.os.Parcel
import android.os.Parcelable

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        userUid = parcel.readString(),
        userName = parcel.readString(),
        userAddress = parcel.readString(),
        userPhoneNumber = parcel.readString(),
        totalPrice = parcel.readInt(),
        userOrderList = parcel.createTypedArrayList(CartItemModel.CREATOR) ?: mutableListOf(),
        orderAccepted = parcel.readByte() != 0.toByte(),
        paymentReceived = parcel.readByte() != 0.toByte(),
        itemPushKey = parcel.readString(),
        currentTime = parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(userAddress)
        parcel.writeString(userPhoneNumber)
        parcel.writeInt(totalPrice)
        parcel.writeTypedList(userOrderList)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderItemModel> {
        override fun createFromParcel(parcel: Parcel): OrderItemModel {
            return OrderItemModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderItemModel?> {
            return arrayOfNulls(size)
        }
    }
}
