package com.example.ironmantrainingapp.ui.profile

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
import com.example.ironmantrainingapp.data.UserProfile
import com.example.ironmantrainingapp.ui.theme.IronmanRed

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onResetData: () -> Unit
) {
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
                text = "ATHLETE PROFILE",
                style = MaterialTheme.typography.labelMedium,
                color = IronmanRed,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = userProfile.raceName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProfileInfoCard(title = "Age", value = "${userProfile.age} years old")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileInfoCard(title = "Training Frequency", value = "${userProfile.daysPerWeek} Days per week")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileInfoCard(title = "Swim Level & Max", value = "${userProfile.swimLevel} (${userProfile.swimMaxTimeOrDist})")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileInfoCard(title = "Bike Level & Max", value = "${userProfile.bikeLevel} (${userProfile.bikeMaxTimeOrDist})")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileInfoCard(title = "Run Level & Max", value = "${userProfile.runLevel} (${userProfile.runMaxTimeOrDist})")

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onResetData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IronmanRed.copy(alpha = 0.15f))
            ) {
                Text(
                    text = "RESET DATA & RECONFIGURE",
                    color = IronmanRed,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileInfoCard(title: String, value: String) {
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
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
