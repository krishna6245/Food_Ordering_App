package com.example.adminpanel.dataModels
import android.os.Parcel
import android.os.Parcelable

data class CartItemModel(
    val menuItem: MenuItemModel? = null,
    var quantity: Int? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(MenuItemModel::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(menuItem, flags)
        parcel.writeValue(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItemModel> {
        override fun createFromParcel(parcel: Parcel): CartItemModel {
            return CartItemModel(parcel)
        }

        override fun newArray(size: Int): Array<CartItemModel?> {
            return arrayOfNulls(size)
        }
    }
}
