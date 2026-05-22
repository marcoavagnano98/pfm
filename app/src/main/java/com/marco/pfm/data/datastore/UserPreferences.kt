package com.marco.pfm.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.userPreferencesDataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class UserPreferences(
    val dataStore: androidx.datastore.core.DataStore<Preferences>,
)
