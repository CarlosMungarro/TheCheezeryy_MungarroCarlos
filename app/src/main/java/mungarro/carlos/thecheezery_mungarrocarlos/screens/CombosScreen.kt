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
import mungarro.carlos.thecheezery_mungarrocarlos.data.CombosDAO
import mungarro.carlos.thecheezery_mungarrocarlos.data.DatabaseHelper
import mungarro.carlos.thecheezery_mungarrocarlos.domain.Combo
import mungarro.carlos.thecheezery_mungarrocarlos.ui.theme.Brighter_Pink
import kotlin.collections.forEach

@Composable
fun CombosScreen() {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    val combosDAO = remember { CombosDAO(dbHelper) }
    var combosList by remember { mutableStateOf<List<Combo>>(emptyList()) }

    LaunchedEffect(Unit) {
        combosList = combosDAO.getAllCombos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Combos",
            color = Brighter_Pink,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (combosList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No combos available", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(combosList) { combo ->
                    ComboItem(combo)
                }
            }
        }
    }
}

@Composable
fun ComboItem(combo: Combo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = combo.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "$${combo.price}", fontSize = 18.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Includes:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            combo.products.forEach { product ->
                Text(text = "• ${product.name}", fontSize = 14.sp, color = Color.DarkGray)
            }
        }
    }
}
