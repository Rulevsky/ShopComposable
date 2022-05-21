package com.example.shopcomposable.ui.Screens

enum class AppScreen() {
    ItemsScreen(),
    ShopingCartScreen();
//    companion object {
//        fun fromRoute(route: String?): AppScreen =
//
//            when (route?.substringBefore("/")) {
//                ItemsScreen.name -> AppScreen.ItemsScreen
//                ShopingCartScreen.name -> AppScreen.ShopingCartScreen
//                null -> ItemsScreen
//                else -> throw IllegalArgumentException("Route $route is not recognized.")
//        }
//    }
}

