package com.example.ironmantrainingapp.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ironmantrainingapp.data.UserProfile
import com.example.ironmantrainingapp.ui.theme.IronmanRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onCompleteSetup: (UserProfile) -> Unit
) {
    var age by remember { mutableStateOf("30") }
    var swimLevel by remember { mutableStateOf("Intermediate") }
    var swimTime by remember { mutableStateOf("400m in 8:00") }
    var bikeLevel by remember { mutableStateOf("Intermediate") }
    var bikeTime by remember { mutableStateOf("20km in 40:00") }
    var runLevel by remember { mutableStateOf("Intermediate") }
    var runTime by remember { mutableStateOf("5km in 25:00") }
    var daysPerWeek by remember { mutableStateOf(5) }
    var raceName by remember { mutableStateOf("Ironman 70.3 Championship") }

    val levels = listOf("Beginner", "Intermediate", "Advanced")
    val daysOptions = listOf(3, 4, 5, 6)
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Logo Header
            Text(
                text = "IRONMAN",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = IronmanRed,
                letterSpacing = 4.sp
            )
            Text(
                text = "70.3 TRAINING PROGRAM",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Configure your profile and current fitness maxes to generate your personalized 16-week training schedule.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Age & Race Name
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Your Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = IronmanRed,
                    focusedLabelColor = IronmanRed
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = raceName,
                onValueChange = { raceName = it },
                label = { Text("Target Race / Goal") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = IronmanRed,
                    focusedLabelColor = IronmanRed
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Swim Section
            DisciplineSection(
                title = "🏊 SWIM (Target 1.9 km)",
                level = swimLevel,
                onLevelChange = { swimLevel = it },
                levels = levels,
                timeOrDist = swimTime,
                onTimeOrDistChange = { swimTime = it },
                labelTime = "400m Time / Max Distance"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Bike Section
            DisciplineSection(
                title = "🚴 BIKE (Target 90 km)",
                level = bikeLevel,
                onLevelChange = { bikeLevel = it },
                levels = levels,
                timeOrDist = bikeTime,
                onTimeOrDistChange = { bikeTime = it },
                labelTime = "20km Time / Max Speed"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Run Section
            DisciplineSection(
                title = "🏃 RUN (Target 21.1 km)",
                level = runLevel,
                onLevelChange = { runLevel = it },
                levels = levels,
                timeOrDist = runTime,
                onTimeOrDistChange = { runTime = it },
                labelTime = "5km Time / Max Distance"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Days Trained Per Week
            Text(
                text = "Training Days Per Week",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                daysOptions.forEach { days ->
                    val isSelected = daysPerWeek == days
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) IronmanRed else MaterialTheme.colorScheme.surfaceVariant)
                            .border(
                                width = if (isSelected) 0.dp else 1.dp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { daysPerWeek = days },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$days Days",
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Generate Button
            Button(
                onClick = {
                    val userAge = age.toIntOrNull() ?: 30
                    val profile = UserProfile(
                        age = userAge,
                        swimLevel = swimLevel,
                        swimMaxTimeOrDist = swimTime,
                        bikeLevel = bikeLevel,
                        bikeMaxTimeOrDist = bikeTime,
                        runLevel = runLevel,
                        runMaxTimeOrDist = runTime,
                        daysPerWeek = daysPerWeek,
                        isSetupComplete = true,
                        raceName = raceName
                    )
                    onCompleteSetup(profile)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IronmanRed)
            ) {
                Text(
                    text = "GENERATE IRONMAN 70.3 PLAN",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.5.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun DisciplineSection(
    title: String,
    level: String,
    onLevelChange: (String) -> Unit,
    levels: List<String>,
    timeOrDist: String,
    onTimeOrDistChange: (String) -> Unit,
    labelTime: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Level selection pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                levels.forEach { lvl ->
                    val isSelected = level == lvl
                    OutlinedButton(
                        onClick = { onLevelChange(lvl) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) IronmanRed.copy(alpha = 0.15f) else Color.Transparent,
                            contentColor = if (isSelected) IronmanRed else MaterialTheme.colorScheme.onSurface
                        ),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                            brush = androidx.compose.ui.graphics.SolidColor(if (isSelected) IronmanRed else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                        )
                    ) {
                        Text(text = lvl, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = timeOrDist,
                onValueChange = { onTimeOrDistChange(it) },
                label = { Text(labelTime) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = IronmanRed,
                    focusedLabelColor = IronmanRed
                )
            )
        }
    }
}
