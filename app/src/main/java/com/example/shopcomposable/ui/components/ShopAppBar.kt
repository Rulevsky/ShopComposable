package com.example.shopcomposable.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shopcomposable.ui.Screens.AppScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/////////// drawerState
/////////
@Composable
fun ShopAppBar(
    drawerState: DrawerState,
    scope: CoroutineScope,
    allScreens: List<AppScreen>,
    navController: NavController,
    onScreenSelected: (AppScreen) -> Unit,
    //currentScreen: AppScreen,

) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                },
                modifier = Modifier.padding(start = 30.dp)
            ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }

            Button(
                onClick = {
                    navController.navigate(AppScreen.ItemsScreen.name);
                    scope.launch {
                        drawerState.apply {
                            if (isOpen) close()
                        }
                    }
                },

                ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = null)
            }


            Button(
                onClick = {
                    navController.navigate(AppScreen.ShopingCartScreen.name);
                    scope.launch {
                        drawerState.apply {
                            if (isOpen) close()
                        }
                    }
                },
                modifier = Modifier.padding(end = 30.dp)
            ) {
                Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)
            }
        }
    }
}

