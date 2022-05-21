package com.example.shopcomposable.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopcomposable.RoomDatabase.Item
import com.example.shopcomposable.ui.Screens.ItemsScreen
import com.example.shopcomposable.ui.Screens.AppScreen
import com.example.shopcomposable.ui.Screens.ShopingCartScreen


//
// Need adjust modal drawer width(too high)
//
@Composable
fun ShopModalDrawer(
    drawerState: DrawerState,
    itemsFromDb : List<Item>,
    navController: NavHostController

) {

    val backStackEntry = navController.currentBackStackEntry
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerContent(navController)
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                ItemsScreen(itemsFromDb = itemsFromDb)
                ShopNavHost(navController = navController, itemsFromDb = itemsFromDb)
            }
        }
    )
}

@Composable
fun ModalDrawerContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp),
    ) {
        Text(
            "All items",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "About",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Log out",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ShopNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    itemsFromDb: List<Item>
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.ItemsScreen.name,
        modifier = Modifier
    ){
        composable(AppScreen.ItemsScreen.name) {
            ItemsScreen(itemsFromDb = itemsFromDb)
        }
        composable(AppScreen.ShopingCartScreen.name) {
            ShopingCartScreen(navController)
        }
    }
}