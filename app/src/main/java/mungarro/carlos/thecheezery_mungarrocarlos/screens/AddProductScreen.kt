package mungarro.carlos.thecheezery_mungarrocarlos.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mungarro.carlos.thecheezery_mungarrocarlos.components.ProductForm
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.AppDatabase
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.repository.CheezeryRepository

@Composable
fun AddProductScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = CheezeryRepository(AppDatabase.getInstance(context))
    val scope = rememberCoroutineScope()

    ProductForm(
        innerPadding = PaddingValues(16.dp),
        onSaveProduct = { name, price, image, description, type ->
            val newProduct = ProductEntity(
                name = name,
                price = price,
                image = if (image.isEmpty()) null else image,
                description = if (description.isEmpty()) null else description,
                type = type
            )
            scope.launch {
                repository.insertProduct(newProduct)
                navController.popBackStack()
            }
        }
    )
}