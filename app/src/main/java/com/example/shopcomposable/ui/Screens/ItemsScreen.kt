package com.example.shopcomposable.ui.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopcomposable.RoomDatabase.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


@Composable
fun ItemsScreen(
    itemsFromDb: List<Item>,
) {

    LazyColumn {
        items(items = itemsFromDb) { itemFromDb ->
            ItemCard(item = itemFromDb)
        }
    }

}

@Composable
fun ItemCard(item: Item) {
    val applicationScope = CoroutineScope(SupervisorJob())
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
                    .align(Alignment.CenterVertically)
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
//                    CoroutineScope(applicationScope.launch(Dispatchers.IO) {
//                        cartDatabase.cartDatabaseDao().insert(CartItem(itemId = 0 ,name = item.name, price = item.price, amount = 1.0))
//                    })
//                    // попробовать пихать в базу с itemId Item чтоб можно было удалять
//                    // может перед добавлением проверять по ID if .equals
//                    expanded.value = !expanded.value
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
