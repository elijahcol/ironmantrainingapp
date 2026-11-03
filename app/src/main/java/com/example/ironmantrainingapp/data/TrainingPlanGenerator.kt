package com.example.ironmantrainingapp.data

import java.util.Locale

object TrainingPlanGenerator {

    data class WorkoutSpec(
        val title: String,
        val description: String,
        val distance: Double,
        val duration: Int
    )

    fun generatePlan(daysPerWeek: Int): List<Workout> {
        val workouts = mutableListOf<Workout>()
        val daysList = when (daysPerWeek) {
            3 -> listOf("Tue", "Thu", "Sat")
            4 -> listOf("Tue", "Thu", "Sat", "Sun")
            5 -> listOf("Tue", "Wed", "Thu", "Sat", "Sun")
            6 -> listOf("Mon", "Tue", "Wed", "Thu", "Sat", "Sun")
            else -> listOf("Tue", "Wed", "Thu", "Sat", "Sun")
        }

        for (week in 1..16) {
            val weekPhase = when {
                week <= 4 -> "Base Building"
                week <= 8 -> "Build & Endurance"
                week <= 12 -> "Peak Intensity"
                week <= 15 -> "Race Simulation"
                else -> "Taper & Recovery"
            }

            val swimDist = minOf(1.9, 1.0 + (week * 0.06))
            val bikeDist = minOf(90.0, 30.0 + (week * 3.8))
            val runDist = minOf(21.1, 8.0 + (week * 0.85))

            daysList.forEachIndexed { index, dayStr ->
                val workoutType = when (index % 5) {
                    0 -> "SWIM"
                    1 -> "BIKE"
                    2 -> "RUN"
                    3 -> if (week % 2 == 0) "BRICK" else "BIKE"
                    else -> "STRENGTH"
                }

                val spec = when (workoutType) {
                    "SWIM" -> WorkoutSpec(
                        title = "Swim Endurance - W$week",
                        description = "Phase: $weekPhase. Focus on stroke efficiency, sighting, and bilateral breathing.",
                        distance = swimDist,
                        duration = 30 + (week * 2)
                    )
                    "BIKE" -> WorkoutSpec(
                        title = "Aerobic Cycling - W$week",
                        description = "Phase: $weekPhase. Steady cadence (85-95 RPM), Zone 2 heart rate endurance ride.",
                        distance = bikeDist,
                        duration = 60 + (week * 6)
                    )
                    "RUN" -> WorkoutSpec(
                        title = "Long Run / Tempo - W$week",
                        description = "Phase: $weekPhase. Build aerobic threshold and running form endurance.",
                        distance = runDist,
                        duration = 45 + (week * 4)
                    )
                    "BRICK" -> WorkoutSpec(
                        title = "Bike-to-Run Brick - W$week",
                        description = "Phase: $weekPhase. ${String.format(Locale.US, "%.1f", bikeDist * 0.6)}km bike followed immediately by ${String.format(Locale.US, "%.1f", runDist * 0.5)}km run off the bike.",
                        distance = bikeDist * 0.6 + runDist * 0.5,
                        duration = 90 + (week * 5)
                    )
                    else -> WorkoutSpec(
                        title = "Core & Strength Conditioning - W$week",
                        description = "Phase: $weekPhase. Functional strength, injury prevention, core stability for triathlon.",
                        distance = 5.0,
                        duration = 45
                    )
                }

                workouts.add(
                    Workout(
                        id = "w_${week}_${dayStr}_$index",
                        weekNumber = week,
                        dayOfWeek = dayStr,
                        type = workoutType,
                        title = spec.title,
                        description = spec.description,
                        targetDistanceKm = spec.distance,
                        targetDurationMinutes = spec.duration
                    )
                )
            }
        }
        return workouts
    }
}
