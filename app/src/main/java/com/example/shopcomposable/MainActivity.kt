package com.example.shopcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var itemsDatabase: ItemsDatabase
    lateinit var itemsFromDb: List<Item>
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
                    ItemsCardList(itemsFromDb = itemsFromDb)
                }
            }
        }
    }


    @Composable
    private fun ItemsCardList(itemsFromDb: List<Item>) {

        Scaffold(bottomBar = {
            BottomAppBar {
                AppBarContent()
            }
        }) {
            // Screen content
            LazyColumn() {
                items(items = itemsFromDb) { itemFromDb ->
                    ItemCard(item = itemFromDb)
                }
            }
        }
    }

    @Composable
    fun ItemCard(item: Item) {
        val expanded = remember { mutableStateOf(false) }
        Column {

            Row(modifier = Modifier.padding(2.dp, top = 10.dp)) {
                Image(
                    painter = painterResource(id = item.imageId),
                    contentDescription = "bike light",
                    modifier = Modifier
                        .size(160.dp)
                )
                Column {
                    Text(
                        text = item.name,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = item.description,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }

            Row() {
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
                        .padding(end = 10.dp),

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

    @Composable
    fun AppBarContent() {
        Button(onClick = { /*TODO*/ }) {
            Text("test")
        }
    }
}


