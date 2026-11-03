package com.example.ironmantrainingapp.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ironmantrainingapp.data.UserProfile
import com.example.ironmantrainingapp.data.Workout
import com.example.ironmantrainingapp.ui.theme.IronmanRed
import com.example.ironmantrainingapp.ui.theme.IronmanSuccess
import java.util.Locale

@Composable
fun DashboardScreen(
    userProfile: UserProfile,
    workouts: List<Workout>,
    onSelectWorkout: (Workout) -> Unit
) {
    val scrollState = rememberScrollState()

    val completedCount = workouts.count { it.isCompleted }
    val totalCount = workouts.size.coerceAtLeast(1)
    val progressPercent = (completedCount.toFloat() / totalCount.toFloat()) * 100f

    val currentWorkout = workouts.firstOrNull { !it.isCompleted } ?: workouts.firstOrNull()

    val totalSwimKm = workouts.filter { it.type == "SWIM" && it.isCompleted }.sumOf { it.loggedDistanceKm ?: it.targetDistanceKm }
    val totalBikeKm = workouts.filter { (it.type == "BIKE" || it.type == "BRICK") && it.isCompleted }.sumOf { it.loggedDistanceKm ?: it.targetDistanceKm }
    val totalRunKm = workouts.filter { (it.type == "RUN" || it.type == "BRICK") && it.isCompleted }.sumOf { it.loggedDistanceKm ?: it.targetDistanceKm }

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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "IRONMAN 70.3",
                        style = MaterialTheme.typography.labelMedium,
                        color = IronmanRed,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = userProfile.raceName,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = IronmanRed.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Age: ${userProfile.age}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = IronmanRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Progress Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Training Progress",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "$completedCount / ${workouts.size} Workouts",
                            style = MaterialTheme.typography.bodyMedium,
                            color = IronmanRed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = IronmanRed,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = String.format(Locale.US, "Overall Completion: %.1f%%", progressPercent),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Today's / Next Workout
            Text(
                text = "UPCOMING / TODAY'S WORKOUT",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (currentWorkout != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectWorkout(currentWorkout) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (currentWorkout.isCompleted) IronmanSuccess.copy(alpha = 0.15f) else IronmanRed.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = if (currentWorkout.isCompleted) "COMPLETED" else currentWorkout.type,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    color = if (currentWorkout.isCompleted) IronmanSuccess else IronmanRed,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = "Week ${currentWorkout.weekNumber} • ${currentWorkout.dayOfWeek}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentWorkout.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentWorkout.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Target: ${currentWorkout.targetDistanceKm} km",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Duration: ${currentWorkout.targetDurationMinutes} min",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Total Logged Stats
            Text(
                text = "CUMULATIVE LOGGED DISTANCE",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(modifier = Modifier.weight(1f), title = "Swim", value = String.format(Locale.US, "%.1f km", totalSwimKm))
                StatCard(modifier = Modifier.weight(1f), title = "Bike", value = String.format(Locale.US, "%.1f km", totalBikeKm))
                StatCard(modifier = Modifier.weight(1f), title = "Run", value = String.format(Locale.US, "%.1f km", totalRunKm))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = IronmanRed
            )
        }
    }
}
