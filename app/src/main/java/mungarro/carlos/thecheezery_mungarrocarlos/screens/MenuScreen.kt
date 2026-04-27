package mungarro.carlos.thecheezery_mungarrocarlos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mungarro.carlos.thecheezery_mungarrocarlos.R
import mungarro.carlos.thecheezery_mungarrocarlos.ui.theme.*

@Composable
fun MenuScreen(navController: NavController) {
    val firstGradient = Brush.verticalGradient(listOf(Brighter_Pink, Pinky))
    val secondGradient = Brush.verticalGradient(listOf(Pinky, Less_Purple))
    val thirdGradient = Brush.verticalGradient(listOf(Less_Purple, Very_purple))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.grupo_2),
            contentDescription = "Logo",
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MenuOption(text = "Hot drinks", gradient = firstGradient) {
                    navController.navigate("products/Hot drinks")
                }
                MenuOption(text = "Salties", gradient = secondGradient) {
                    navController.navigate("products/Salties")
                }
                MenuOption(text = "Combos", gradient = thirdGradient) {
                    navController.navigate("combos_list") // Navegar a la pantalla de combos
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MenuOption(text = "Cold drinks", gradient = firstGradient) {
                    navController.navigate("products/Cold drinks")
                }
                MenuOption(text = "Sweets", gradient = secondGradient) {
                    navController.navigate("products/Sweets")
                }
                MenuOption(text = "Add new product", gradient = thirdGradient) {
                    navController.navigate("add_product")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        MenuOption(text = "Add new Combo", gradient = thirdGradient) {
            navController.navigate("add_combo")
        }
    }
}

@Composable
fun MenuOption(text: String, gradient: Brush, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(gradient)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MenuScreenPreview() {
    MenuScreen(rememberNavController())
}
