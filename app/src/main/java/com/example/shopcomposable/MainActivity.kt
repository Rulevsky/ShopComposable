package com.example.shopcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopcomposable.RoomDatabase.Item
import com.example.shopcomposable.RoomDatabase.ItemsDatabase
import com.example.shopcomposable.ui.Screens.AppScreen
import com.example.shopcomposable.ui.components.ShopAppBar
import com.example.shopcomposable.ui.components.ShopModalDrawer
import com.example.shopcomposable.ui.theme.ShopComposableTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private lateinit var itemsDatabase: ItemsDatabase
    private lateinit var itemsFromDb: List<Item>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemsDatabase = ItemsDatabase.getInstance(applicationContext, applicationScope)
        CoroutineScope(applicationScope.launch(Dispatchers.IO) {
            itemsFromDb = itemsDatabase.itemDatavaseDao().getAll()
        })
        setContent {
            ShopComposableApp()
        }
    }

    @Composable
    fun ShopComposableApp() {
        ShopComposableTheme {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val allScreens = AppScreen.values().toList()
            val navController = rememberNavController()
            val backStackEntry = navController.currentBackStackEntryAsState()
//            val currentScreen = AppScreen.fromRoute(
//                backStackEntry.value?.destination?.route
//            )

            Scaffold(
                bottomBar = {
                    ShopAppBar(
                        drawerState = drawerState,
                        scope = scope,
                        allScreens = allScreens,
                        navController = navController,
                        onScreenSelected = { screen -> navController.navigate(screen.name)},
                        //currentScreen = currentScreen
                        )
                }
            ) {
                ShopModalDrawer(drawerState = drawerState, itemsFromDb = itemsFromDb, navController)
            }

        }
    }
}






