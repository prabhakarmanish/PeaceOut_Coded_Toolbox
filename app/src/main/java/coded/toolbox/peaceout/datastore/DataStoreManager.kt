package coded.toolbox.peaceout.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore instance
val Context.dataStore by preferencesDataStore("user_preferences")

class DataStoreManager(private val context: Context) {

    // Define keys for each preference
    companion object {
        val IS_FULL_DAY_KEY = booleanPreferencesKey("is_full_day")
        val SELECTED_ITEM_KEY = intPreferencesKey("selected_item")
        val FULL_DAY_HOURS_KEY = intPreferencesKey("full_day_hours")
        val FULL_DAY_MINUTES_KEY = intPreferencesKey("full_day_minutes")
        val HALF_DAY_HOURS_KEY = intPreferencesKey("half_day_hours")
        val HALF_DAY_MINUTES_KEY = intPreferencesKey("half_day_minutes")
        val REMINDER_ENABLED_KEY = booleanPreferencesKey("reminder_enabled")
        val REMINDER_TIME_KEY = intPreferencesKey("reminder_time")
        // New keys for selected hour and minute
        val SELECTED_HOUR_KEY = intPreferencesKey("selected_hour")
        val SELECTED_MINUTE_KEY = intPreferencesKey("selected_minute")
    }

    // Function to save full day mode
    suspend fun saveIsFullDay(isFullDay: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_FULL_DAY_KEY] = isFullDay
        }
    }

    // Function to save selected item
    suspend fun saveSelectedItem(selectedItem: Int) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_ITEM_KEY] = selectedItem
        }
    }

    // Function to save full day settings
    suspend fun saveFullDaySettings(hours: Int, minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[FULL_DAY_HOURS_KEY] = hours
            preferences[FULL_DAY_MINUTES_KEY] = minutes
        }
    }

    // Function to save half day settings
    suspend fun saveHalfDaySettings(hours: Int, minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[HALF_DAY_HOURS_KEY] = hours
            preferences[HALF_DAY_MINUTES_KEY] = minutes
        }
    }

    // Function to save reminder settings
    suspend fun saveReminderSettings(enabled: Boolean, time: Int) {
        context.dataStore.edit { preferences ->
            preferences[REMINDER_ENABLED_KEY] = enabled
            preferences[REMINDER_TIME_KEY] = time
        }
    }

    // Function to save selected time (hour and minute)
    suspend fun saveSelectedTime(hour: Int, minute: Int) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_HOUR_KEY] = hour
            preferences[SELECTED_MINUTE_KEY] = minute
        }
    }

    // Flow to get full day mode
    val isFullDayFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_FULL_DAY_KEY] ?: true
    }

    // Flow to get selected item
    val selectedItemFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_ITEM_KEY] ?: 0
    }

    // Flow to get full day hours and minutes
    val fullDayHoursFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[FULL_DAY_HOURS_KEY] ?: 8 // Default 8 hours
    }

    val fullDayMinutesFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[FULL_DAY_MINUTES_KEY] ?: 45 // Default 45 minutes
    }

    // Flow to get half day hours and minutes
    val halfDayHoursFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[HALF_DAY_HOURS_KEY] ?: 4 // Default 4 hours
    }

    val halfDayMinutesFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[HALF_DAY_MINUTES_KEY] ?: 15 // Default 15 minutes
    }

    // Flow to get reminder settings
    val reminderEnabledFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[REMINDER_ENABLED_KEY] ?: true // Default reminder enabled
    }

    val reminderTimeFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[REMINDER_TIME_KEY] ?: 10 // Default 10 minutes
    }

    // Flow to get selected hour and minute
    val selectedHourFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_HOUR_KEY] ?: 0 // Default hour
    }

    val selectedMinuteFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_MINUTE_KEY] ?: 0 // Default minute
    }
}
