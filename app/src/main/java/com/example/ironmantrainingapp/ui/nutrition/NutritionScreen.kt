package com.example.ironmantrainingapp.ui.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ironmantrainingapp.ui.theme.IronmanRed

@Composable
fun NutritionScreen() {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            Text(
                text = "RACE & TRAINING FUEL",
                style = MaterialTheme.typography.labelMedium,
                color = IronmanRed,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ironman 70.3 Nutrition Guide",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(20.dp))

            NutritionCard(
                title = "🏊 Swim Fueling Strategy",
                content = "Pre-race meal 2-3 hours before start (e.g., oatmeal, banana, toast with honey). Sip water and sports drink up to 30 mins before the swim start. Take an energy gel 15 mins before entering the water."
            )

            Spacer(modifier = Modifier.height(16.dp))

            NutritionCard(
                title = "🚴 Bike Fueling (The Critical Phase)",
                content = "Aim for 60-90g of carbohydrates per hour through energy gels, sports drinks, and energy chews. Drink 500-750ml of fluids per hour containing electrolytes (sodium) to prevent cramping. Start fueling within the first 15 minutes on the bike."
            )

            Spacer(modifier = Modifier.height(16.dp))

            NutritionCard(
                title = "🏃 Run Fueling (Half Marathon)",
                content = "Take an energy gel every 30-45 minutes with water at aid stations. Avoid new foods on race day. Practice your exact race nutrition plan during long brick workouts in Weeks 10-14."
            )

            Spacer(modifier = Modifier.height(16.dp))

            NutritionCard(
                title = "💧 Hydration & Electrolytes",
                content = "Electrolytes (sodium, potassium, magnesium) are essential to prevent cramping during the 90km bike and 21.1km run. Adjust fluid intake based on ambient temperature and sweat rate."
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun NutritionCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = IronmanRed,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
