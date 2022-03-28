package com.example.shopcomposable.RoomDatabase

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int,

    @ColumnInfo(name = "item_name")
    val name:String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image_id")
    val imageId: Int,
    @ColumnInfo(name = "price")
    val price: Double
) {

}
