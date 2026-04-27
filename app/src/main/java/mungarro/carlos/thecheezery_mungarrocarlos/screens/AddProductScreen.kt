package mungarro.carlos.thecheezery_mungarrocarlos.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mungarro.carlos.thecheezery_mungarrocarlos.components.ProductForm
import mungarro.carlos.thecheezery_mungarrocarlos.viewmodel.ProductViewModel

import mungarro.carlos.thecheezery_mungarrocarlos.data.DatabaseHelper
import mungarro.carlos.thecheezery_mungarrocarlos.data.ProductsDAO
import mungarro.carlos.thecheezery_mungarrocarlos.domain.Product

import kotlin.text.isEmpty

@Composable
fun AddProductScreen(navController: NavController) {
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


    ProductForm(
        innerPadding = PaddingValues(16.dp),
        onSaveProduct = { name, price, image, description, type ->
            val newProduct = Product(
                name = name,
                price = price,
                image = if (image.isEmpty()) null else image,
                description = if (description.isEmpty()) null else description,
                type = type
            )
            viewModel.saveProduct(newProduct)
            navController.popBackStack()
        }
    )
}
