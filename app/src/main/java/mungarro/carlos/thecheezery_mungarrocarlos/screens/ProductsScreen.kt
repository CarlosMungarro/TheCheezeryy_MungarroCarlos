package mungarro.carlos.thecheezery_mungarrocarlos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import mungarro.carlos.thecheezery_mungarrocarlos.viewmodel.ProductViewModel
import mungarro.carlos.thecheezery_mungarrocarlos.R
import mungarro.carlos.thecheezery_mungarrocarlos.data.DatabaseHelper
import mungarro.carlos.thecheezery_mungarrocarlos.data.ProductsDAO
import mungarro.carlos.thecheezery_mungarrocarlos.domain.Product


@Composable
fun ProductsScreen(type: String) {
    val context = LocalContext.current
    
    val viewModel: ProductViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dbHelper = DatabaseHelper(context)
                val dao = ProductsDAO(dbHelper)
                return ProductViewModel(dao) as T
            }
        }
    )
    
    LaunchedEffect(type) {
        viewModel.getProductsByType(type)
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
            items(viewModel.productsListState) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        val imageRes = if (product.image != null) {
            context.resources.getIdentifier(product.image, "drawable", context.packageName)
        } else 0
        
        Image(
            painter = painterResource(id = if (imageRes != 0) imageRes else R.drawable.muffin),
            contentDescription = product.name,
            modifier = Modifier.size(100.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.description ?: "",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "$${product.price}",
                fontSize = 18.sp,
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
