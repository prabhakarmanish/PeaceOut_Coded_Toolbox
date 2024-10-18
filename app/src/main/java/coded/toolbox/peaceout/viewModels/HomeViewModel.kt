package coded.toolbox.peaceout.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.toolbox.peaceout.datastore.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _isFullDay = MutableStateFlow(true)
    val isFullDay: StateFlow<Boolean> = _isFullDay

    private val _selectedItem = MutableStateFlow(0)
    val selectedItem: StateFlow<Int> = _selectedItem

    init {
        // Load isFullDay preference
        viewModelScope.launch {
            dataStoreManager.isFullDayFlow.collect { value ->
                _isFullDay.value = value
            }
        }

        // Load selectedItem preference
        viewModelScope.launch {
            dataStoreManager.selectedItemFlow.collect { value ->
                _selectedItem.value = value
            }
        }
    }

    fun toggleDayMode(isFullDay: Boolean) {
        _isFullDay.value = isFullDay
        viewModelScope.launch {
            dataStoreManager.saveIsFullDay(isFullDay)
        }
    }

    fun updateSelectedItem(index: Int) {
        _selectedItem.value = index
        viewModelScope.launch {
            dataStoreManager.saveSelectedItem(index)
        }
    }
}
