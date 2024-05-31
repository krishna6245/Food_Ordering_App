package com.example.foodorderingapp.dataModels

import android.os.Parcel
import android.os.Parcelable

data class MenuItemModel(
    val foodName: String? = null,
    val restaurantName: String? = null,
    val foodPrice: Int = 0,
    val foodImage: String? = null,
    val foodDescription: String? = null,
    val foodIngredients: MutableList<String>? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(foodName)
        parcel.writeString(restaurantName)
        parcel.writeInt(foodPrice)
        parcel.writeString(foodImage)
        parcel.writeString(foodDescription)
        parcel.writeStringList(foodIngredients)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuItemModel> {
        override fun createFromParcel(parcel: Parcel): MenuItemModel {
            return MenuItemModel(parcel)
        }

        override fun newArray(size: Int): Array<MenuItemModel?> {
            return arrayOfNulls(size)
        }
    }
}
