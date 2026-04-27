package mungarro.carlos.thecheezery_mungarrocarlos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mungarro.carlos.thecheezery_mungarrocarlos.R
import mungarro.carlos.thecheezery_mungarrocarlos.ui.theme.Brighter_Pink
import mungarro.carlos.thecheezery_mungarrocarlos.ui.theme.Purple_grey

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(R.drawable.the_cheezery),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple_grey)
                .padding(horizontal = 30.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to The Cheezery",
                color = Color.White,
                fontSize = 24.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Home of the most wonderful desserts ever seen (and tasted) by the human being.",
                color = Color.White,
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            Button(
                onClick = { navController.navigate("menu") },
                colors = ButtonDefaults.buttonColors(containerColor = Brighter_Pink),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.height(45.dp).padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Get started!",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeScreenPreview() {
    WelcomeScreen(rememberNavController())
}
