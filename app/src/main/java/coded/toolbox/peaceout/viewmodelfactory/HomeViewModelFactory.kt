package coded.toolbox.peaceout.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coded.toolbox.peaceout.viewModels.HomeViewModel
import coded.toolbox.peaceout.datastore.DataStoreManager

class HomeViewModelFactory(private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
