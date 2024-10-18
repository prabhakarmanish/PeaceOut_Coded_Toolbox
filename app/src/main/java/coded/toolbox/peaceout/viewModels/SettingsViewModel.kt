package coded.toolbox.peaceout.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.toolbox.peaceout.datastore.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    // Default state flows
    private val _fullDayHours = MutableStateFlow(8) // Default 8 hours
    val fullDayHours = _fullDayHours.asStateFlow()

    private val _fullDayMinutes = MutableStateFlow(45) // Default 45 minutes
    val fullDayMinutes = _fullDayMinutes.asStateFlow()

    private val _halfDayHours = MutableStateFlow(4) // Default 4 hours
    val halfDayHours = _halfDayHours.asStateFlow()

    private val _halfDayMinutes = MutableStateFlow(15) // Default 15 minutes
    val halfDayMinutes = _halfDayMinutes.asStateFlow()

    private val _isReminderEnabled = MutableStateFlow(true) // Default reminder enabled
    val isReminderEnabled = _isReminderEnabled.asStateFlow()

    private val _reminderTime = MutableStateFlow(10) // Default 10 minutes
    val reminderTime = _reminderTime.asStateFlow()

    init {
        // Collect saved data from DataStore on ViewModel initialization
        viewModelScope.launch {
            dataStoreManager.fullDayHoursFlow.collectLatest {
                _fullDayHours.value = it
            }
        }

        viewModelScope.launch {
            dataStoreManager.fullDayMinutesFlow.collectLatest {
                _fullDayMinutes.value = it
            }
        }

        viewModelScope.launch {
            dataStoreManager.halfDayHoursFlow.collectLatest {
                _halfDayHours.value = it
            }
        }

        viewModelScope.launch {
            dataStoreManager.halfDayMinutesFlow.collectLatest {
                _halfDayMinutes.value = it
            }
        }

        viewModelScope.launch {
            dataStoreManager.reminderEnabledFlow.collectLatest {
                _isReminderEnabled.value = it
            }
        }

        viewModelScope.launch {
            dataStoreManager.reminderTimeFlow.collectLatest {
                _reminderTime.value = it
            }
        }
    }

    // Functions to save data
    fun saveFullDaySettings(hours: Int, minutes: Int) {
        viewModelScope.launch {
            _fullDayHours.value = hours
            _fullDayMinutes.value = minutes
            dataStoreManager.saveFullDaySettings(hours, minutes)
        }
    }

    fun saveHalfDaySettings(hours: Int, minutes: Int) {
        viewModelScope.launch {
            _halfDayHours.value = hours
            _halfDayMinutes.value = minutes
            dataStoreManager.saveHalfDaySettings(hours, minutes)
        }
    }

    fun saveReminderSettings(enabled: Boolean, time: Int) {
        viewModelScope.launch {
            _isReminderEnabled.value = enabled
            _reminderTime.value = time
            dataStoreManager.saveReminderSettings(enabled, time)
        }
    }

    // Update methods for UI interaction
    fun updateFullDayHours(hours: Int) {
        _fullDayHours.value = hours
    }

    fun updateFullDayMinutes(minutes: Int) {
        _fullDayMinutes.value = minutes
    }

    fun updateHalfDayHours(hours: Int) {
        _halfDayHours.value = hours
    }

    fun updateHalfDayMinutes(minutes: Int) {
        _halfDayMinutes.value = minutes
    }

    fun setReminderEnabled(enabled: Boolean) {
        _isReminderEnabled.value = enabled
    }

    fun updateReminderTime(time: Int) {
        _reminderTime.value = time
    }
}
