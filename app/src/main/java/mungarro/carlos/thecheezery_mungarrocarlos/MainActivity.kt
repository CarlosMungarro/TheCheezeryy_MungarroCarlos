package mungarro.carlos.thecheezery_mungarrocarlos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mungarro.carlos.thecheezery_mungarrocarlos.screens.AddComboScreen
import mungarro.carlos.thecheezery_mungarrocarlos.screens.AddProductScreen
import mungarro.carlos.thecheezery_mungarrocarlos.screens.CombosScreen
import mungarro.carlos.thecheezery_mungarrocarlos.screens.MenuScreen
import mungarro.carlos.thecheezery_mungarrocarlos.screens.ProductsScreen
import mungarro.carlos.thecheezery_mungarrocarlos.screens.WelcomeScreen
import mungarro.carlos.thecheezery_mungarrocarlos.ui.theme.TheCheezery_MungarroCarlosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCheezery_MungarroCarlosTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "welcome",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("welcome") { WelcomeScreen(navController) }
                        composable("menu") { MenuScreen(navController) }
                        composable("add_product") { AddProductScreen(navController) }
                        composable("add_combo") { AddComboScreen(navController) }
                        composable("combos_list") { CombosScreen() }
                        composable(
                            route = "products/{type}",
                            arguments = listOf(navArgument("type") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val type = backStackEntry.arguments?.getString("type") ?: ""
                            ProductsScreen(type)
                        }
                    }
                }
            }
        }
    }
}