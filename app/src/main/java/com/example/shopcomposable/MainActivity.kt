package com.example.shopcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopcomposable.RoomDatabase.Item
import com.example.shopcomposable.RoomDatabase.ItemsDatabase
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
            ShopComposableTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ContentScaffold()
                }
            }
        }
    }

    @Composable
    fun ContentScaffold() {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        Scaffold(
            bottomBar = {
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
                            onClick = { /*TODO*/ },
                            modifier = Modifier.padding(end = 30.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)
                        }
                    }
                }
            }
        ) {
            MyModalDrawer(drawerState = drawerState)
        }
    }

    @Composable
    fun ItemsCardList(itemsFromDb: List<Item>) {
        LazyColumn {
            items(items = itemsFromDb) { itemFromDb ->
                ItemCard(item = itemFromDb)
            }
        }
    }

    @Composable
    fun ItemCard(item: Item) {
        val expanded = remember { mutableStateOf(false) }
        Column {
            Row(modifier = Modifier.padding(top = 10.dp, end = 6.dp)) {
                Image(
                    painter = painterResource(id = item.imageId),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
                Column {
                    Text(
                        text = item.name,
                        fontSize = 20.sp,
                        color = Color.Red
                    )
                    Text(
                        text = item.description,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(CenterVertically)
                ) {
                    Text(
                        text = item.price.toString() + "$",
                        color = Color.Red,
                        textAlign = TextAlign.Right,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 20.dp),
                    )
                }
                Button(
                    onClick = { /*TODO*/
                        expanded.value = !expanded.value
                    },
                    modifier = Modifier
                        .padding(end = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Red
                    ),
                    border = BorderStroke(1.dp, Color.Red)
                ) {
                    Text(if (expanded.value) "Remove" else "Add to Cart")
                }
            }
        }
    }

    //
    // Need fix with modal drawer width(to high)
    //
    @Composable
    fun MyModalDrawer(drawerState: DrawerState) {
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerContent()
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ItemsCardList(itemsFromDb = itemsFromDb)
                }
            }
        )
    }

    @Composable
    fun ModalDrawerContent() {
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
}






