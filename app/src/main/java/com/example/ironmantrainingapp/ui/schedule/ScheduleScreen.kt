package com.example.ironmantrainingapp.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ironmantrainingapp.data.Workout
import com.example.ironmantrainingapp.ui.theme.IronmanRed
import com.example.ironmantrainingapp.ui.theme.IronmanSuccess

@Composable
fun ScheduleScreen(
    workouts: List<Workout>,
    onSelectWorkout: (Workout) -> Unit
) {
    var selectedWeek by remember { mutableStateOf(1) }
    val scrollState = rememberScrollState()

    val weekWorkouts = workouts.filter { it.weekNumber == selectedWeek }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "TRAINING SCHEDULE",
                style = MaterialTheme.typography.labelMedium,
                color = IronmanRed,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "16-Week 70.3 Roadmap",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Week Selector Horizontal LazyRow
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(16) { index ->
                    val weekNum = index + 1
                    val isSelected = selectedWeek == weekNum
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(45.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) IronmanRed else MaterialTheme.colorScheme.surface)
                            .clickable { selectedWeek = weekNum },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "W$weekNum",
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Phase indicator
            val phaseTitle = when {
                selectedWeek <= 4 -> "Phase: Base Building & Aerobic Foundation"
                selectedWeek <= 8 -> "Phase: Build & Endurance Capacity"
                selectedWeek <= 12 -> "Phase: Peak Intensity & Threshold"
                selectedWeek <= 15 -> "Phase: Race Simulation & Brick Mastery"
                else -> "Phase: Taper & Race Readiness"
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = phaseTitle,
                    modifier = Modifier.padding(14.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Workouts List for Selected Week
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (weekWorkouts.isEmpty()) {
                    Text(
                        text = "No workouts scheduled for this week with your current days-per-week configuration.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                } else {
                    weekWorkouts.forEach { workout ->
                        WorkoutCard(workout = workout, onClick = { onSelectWorkout(workout) })
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun WorkoutCard(workout: Workout, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (workout.isCompleted) IronmanSuccess.copy(alpha = 0.15f) else IronmanRed.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = if (workout.isCompleted) "DONE" else workout.type,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            color = if (workout.isCompleted) IronmanSuccess else IronmanRed,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                    Text(
                        text = workout.dayOfWeek,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "${workout.targetDistanceKm} km / ${workout.targetDurationMinutes} min",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = workout.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = workout.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}
