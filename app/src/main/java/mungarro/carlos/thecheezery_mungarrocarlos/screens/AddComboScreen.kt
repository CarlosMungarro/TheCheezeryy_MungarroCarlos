package mungarro.carlos.thecheezery_mungarrocarlos.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.AppDatabase
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ComboEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.repository.CheezeryRepository
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductEntity
import mungarro.carlos.thecheezery_mungarrocarlos.ui.theme.Brighter_Pink

@Composable
fun AddComboScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { CheezeryRepository(AppDatabase.getInstance(context)) }
    val scope = rememberCoroutineScope()

    var comboName by remember { mutableStateOf("") }
    var comboPrice by remember { mutableStateOf("") }
    var allProducts by remember { mutableStateOf<List<ProductEntity>>(emptyList()) }
    val selectedProductIds = remember { mutableStateListOf<Int>() }

    LaunchedEffect(Unit) {
        repository.getAllProducts().collect { allProducts = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add new combo",
            color = Brighter_Pink,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = comboName,
            onValueChange = { comboName = it },
            label = { Text("Combo Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = comboPrice,
            onValueChange = { comboPrice = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Select Products",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(allProducts) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedProductIds.contains(product.id),
                        onCheckedChange = { isChecked ->
                            if (isChecked) selectedProductIds.add(product.id)
                            else selectedProductIds.remove(product.id)
                        }
                    )
                    Text(text = product.name, fontSize = 18.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "$${product.price}", color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val price = comboPrice.toFloatOrNull() ?: 0f
                if (comboName.isNotBlank() && price > 0 && selectedProductIds.isNotEmpty()) {
                    scope.launch {
                        repository.insertCombo(
                            ComboEntity(name = comboName, price = price),
                            selectedProductIds.toList()
                        )
                        navController.popBackStack()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Brighter_Pink),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Save combo", color = Color.White, fontSize = 18.sp)
        }
    }
}