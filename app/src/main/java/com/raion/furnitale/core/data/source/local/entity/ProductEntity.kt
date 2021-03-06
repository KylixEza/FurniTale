package com.raion.furnitale.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductEntity(

    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,

    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "user_email")
    val userEmail: String? = "",

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "sub_title")
    val subTitle: String? = "",

    @ColumnInfo(name = "image_product")
    val imageProduct: String? = "",

    @ColumnInfo(name = "image_seller")
    val imageSeller: String? = "",

    @ColumnInfo(name = "category")
    val category: String? = "",

    @ColumnInfo(name = "price")
    val price: String? = "",

    @ColumnInfo(name = "real_price")
    val realPrice: Int? = 0,

    @ColumnInfo(name = "store_name")
    val storeName: String? = "",

    @ColumnInfo(name = "city")
    val city: String? = "",

    @ColumnInfo(name = "description")
    val description: String? = "",

    @ColumnInfo(name = "total")
    val totalStuffs: Int? = 0
)
