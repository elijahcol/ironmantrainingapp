package com.example.ironmantrainingapp.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TrainingRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ironman_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_USER_PROFILE = "key_user_profile"
        private const val KEY_WORKOUTS = "key_workouts"
    }

    fun getUserProfile(): UserProfile {
        val json = prefs.getString(KEY_USER_PROFILE, null) ?: return UserProfile()
        return try {
            gson.fromJson(json, UserProfile::class.java) ?: UserProfile()
        } catch (e: Exception) {
            UserProfile()
        }
    }

    fun saveUserProfile(profile: UserProfile) {
        val json = gson.toJson(profile)
        prefs.edit().putString(KEY_USER_PROFILE, json).apply()
    }

    fun getWorkouts(): List<Workout> {
        val json = prefs.getString(KEY_WORKOUTS, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<Workout>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveWorkouts(workouts: List<Workout>) {
        val json = gson.toJson(workouts)
        prefs.edit().putString(KEY_WORKOUTS, json).apply()
    }

    fun updateWorkout(updatedWorkout: Workout) {
        val workouts = getWorkouts().toMutableList()
        val index = workouts.indexOfFirst { it.id == updatedWorkout.id }
        if (index != -1) {
            workouts[index] = updatedWorkout
            saveWorkouts(workouts)
        }
    }

    fun resetData() {
        prefs.edit().clear().apply()
    }
}
