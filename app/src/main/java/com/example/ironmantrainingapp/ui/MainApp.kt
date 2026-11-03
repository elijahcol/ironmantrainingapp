package com.example.ironmantrainingapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ironmantrainingapp.data.TrainingPlanGenerator
import com.example.ironmantrainingapp.data.TrainingRepository
import com.example.ironmantrainingapp.data.UserProfile
import com.example.ironmantrainingapp.data.Workout
import com.example.ironmantrainingapp.ui.aicoach.AiCoachScreen
import com.example.ironmantrainingapp.ui.dashboard.DashboardScreen
import com.example.ironmantrainingapp.ui.profile.ProfileScreen
import com.example.ironmantrainingapp.ui.schedule.ScheduleScreen
import com.example.ironmantrainingapp.ui.onboarding.OnboardingScreen
import com.example.ironmantrainingapp.ui.theme.IronmanRed
import com.example.ironmantrainingapp.ui.workout.WorkoutDetailDialog

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Today", Icons.Default.Home)
    object Schedule : Screen("schedule", "Schedule", Icons.Default.DateRange)
    object AiCoach : Screen("aicoach", "AI Coach", Icons.AutoMirrored.Filled.Chat)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@Composable
fun MainApp(repository: TrainingRepository) {
    var userProfile by remember { mutableStateOf(repository.getUserProfile()) }
    var workouts by remember { mutableStateOf(repository.getWorkouts()) }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Dashboard) }
    var selectedWorkoutForDetail by remember { mutableStateOf<Workout?>(null) }

    LaunchedEffect(userProfile.isSetupComplete) {
        if (userProfile.isSetupComplete && workouts.isEmpty()) {
            val generated = TrainingPlanGenerator.generatePlan(userProfile.daysPerWeek)
            repository.saveWorkouts(generated)
            workouts = generated
        }
    }

    if (!userProfile.isSetupComplete) {
        OnboardingScreen(
            onCompleteSetup = { profile ->
                repository.saveUserProfile(profile)
                val generated = TrainingPlanGenerator.generatePlan(profile.daysPerWeek)
                repository.saveWorkouts(generated)
                userProfile = profile
                workouts = generated
            }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    val items = listOf(Screen.Dashboard, Screen.Schedule, Screen.AiCoach, Screen.Profile)
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentScreen == screen,
                            onClick = { currentScreen = screen },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = IronmanRed,
                                selectedTextColor = IronmanRed,
                                indicatorColor = IronmanRed.copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (currentScreen) {
                    Screen.Dashboard -> DashboardScreen(
                        userProfile = userProfile,
                        workouts = workouts,
                        onSelectWorkout = { workout -> selectedWorkoutForDetail = workout }
                    )
                    Screen.Schedule -> ScheduleScreen(
                        workouts = workouts,
                        onSelectWorkout = { workout -> selectedWorkoutForDetail = workout }
                    )
                    Screen.AiCoach -> AiCoachScreen(userProfile = userProfile)
                    Screen.Profile -> ProfileScreen(
                        userProfile = userProfile,
                        onResetData = {
                            repository.resetData()
                            userProfile = UserProfile(isSetupComplete = false)
                            workouts = emptyList()
                        }
                    )
                }
            }

            selectedWorkoutForDetail?.let { workout ->
                WorkoutDetailDialog(
                    workout = workout,
                    onDismiss = { selectedWorkoutForDetail = null },
                    onSaveWorkout = { updated ->
                        repository.updateWorkout(updated)
                        workouts = repository.getWorkouts()
                        selectedWorkoutForDetail = null
                    }
                )
            }
        }
    }
}
