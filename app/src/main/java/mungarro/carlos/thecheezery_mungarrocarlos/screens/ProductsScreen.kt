package mungarro.carlos.thecheezery_mungarrocarlos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mungarro.carlos.thecheezery_mungarrocarlos.R
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.AppDatabase
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.repository.CheezeryRepository

@Composable
fun ProductsScreen(type: String) {
    val context = LocalContext.current
    val repository = remember { CheezeryRepository(AppDatabase.getInstance(context)) }
    var products by remember { mutableStateOf<List<ProductEntity>>(emptyList()) }

    LaunchedEffect(type) {
        repository.getProductsByType(type).collect { products = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = type,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE91E63),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductEntity) {
    val context = LocalContext.current
    val imageRes = if (product.image != null) {
        context.resources.getIdentifier(product.image, "drawable", context.packageName)
    } else 0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = if (imageRes != 0) imageRes else R.drawable.muffin),
            contentDescription = product.name,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = product.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = product.description ?: "", fontSize = 14.sp, color = Color.Gray)
            Text(text = "$${product.price}", fontSize = 18.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Medium)
        }
    }
}