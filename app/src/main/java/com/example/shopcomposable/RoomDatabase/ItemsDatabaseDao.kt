package com.example.shopcomposable.RoomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemsDatabaseDao {
    @Insert
    fun insert(item: Item)
    @Query("SELECT * from items_table WHERE itemId = :key")
    fun get(key: Int): Item?
    @Query("SELECT * FROM items_table")
    fun getAll(): List<Item>

}