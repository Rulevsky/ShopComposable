package com.example.shopcomposable.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shopcomposable.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemDatavaseDao(): ItemsDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: ItemsDatabase? = null
        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): ItemsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemsDatabase::class.java,
                        "items_table"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(ItemsDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        private class ItemsDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { itemsDatabase ->
                    scope.launch {
                        populateDatabase(itemsDatabase.itemDatavaseDao())
                    }
                }
            }

            suspend fun populateDatabase(itemsDatabaseDao: ItemsDatabaseDao) {

                var item1 = Item(
                    0,
                    "Sauce Labs Backpack",
                    "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.",
                    getID(R.drawable.sauce_backpack_1200x1500_34e7aa42),
                    29.99
                )
                itemsDatabaseDao.insert(item1)

                var item2 = Item(
                    0,
                    "Sauce Labs Bike Light",
                    "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.",
                    getID(R.drawable.bike_light_1200x1500_a0c9caae),
                    9.99
                )
                itemsDatabaseDao.insert(item2)

                var item3 = Item(
                    0,
                    "Sauce Labs Bolt T-Shirt",
                    "Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.",
                    getID(R.drawable.bolt_shirt_1200x1500_c0dae290),
                    15.99
                )
                itemsDatabaseDao.insert(item3)

                var item4 = Item(
                    0,
                    "Sauce Labs Fleece Jacket",
                    "It's not every day that you come across a midweight quarter-zip fleece jacket capable of handling everything from a relaxing day outdoors to a busy day at the office.",
                    getID(R.drawable.sauce_pullover_1200x1500_439fc934),
                    49.99
                )
                itemsDatabaseDao.insert(item4)

                var item5 = Item(
                    0,
                    "Sauce Labs Onesie",
                    "Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel.",
                    getID(R.drawable.red_onesie_1200x1500_1b15e1fa),
                    7.99
                )
                itemsDatabaseDao.insert(item5)

                var item6 = Item(
                    0,
                    "Test.allTheThings() T-Shirt (Red)",
                    "This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.",
                    getID(R.drawable.red_tatt_1200x1500_e32b4ef9),
                    15.99
                )
                itemsDatabaseDao.insert(item6)

            }
        }

        fun getID(drawablePath: Int): Int {
            var imageId = drawablePath
            return imageId
        }
    }

}


