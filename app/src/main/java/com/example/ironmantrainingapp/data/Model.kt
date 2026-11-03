package com.example.ironmantrainingapp.data

data class UserProfile(
    val age: Int = 30,
    val swimLevel: String = "Intermediate",
    val swimMaxTimeOrDist: String = "400m in 8:00",
    val bikeLevel: String = "Intermediate",
    val bikeMaxTimeOrDist: String = "20km in 40:00",
    val runLevel: String = "Intermediate",
    val runMaxTimeOrDist: String = "5km in 25:00",
    val daysPerWeek: Int = 5,
    val isSetupComplete: Boolean = false,
    val raceName: String = "Ironman 70.3 Championship"
)

data class Workout(
    val id: String,
    val weekNumber: Int,
    val dayOfWeek: String, // "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    val type: String, // "SWIM", "BIKE", "RUN", "BRICK", "STRENGTH", "REST"
    val title: String,
    val description: String,
    val targetDistanceKm: Double,
    val targetDurationMinutes: Int,
    val isCompleted: Boolean = false,
    val loggedDurationMinutes: Int? = null,
    val loggedDistanceKm: Double? = null,
    val notes: String = "",
    val rpe: Int? = null // 1 to 10
)
