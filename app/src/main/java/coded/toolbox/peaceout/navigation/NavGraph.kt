package coded.toolbox.peaceout.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coded.toolbox.peaceout.datastore.DataStoreManager
import coded.toolbox.peaceout.screens.HomeScreen
import coded.toolbox.peaceout.screens.SettingsScreen
import coded.toolbox.peaceout.screens.TimeScreen
import androidx.compose.ui.platform.LocalContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController, dataStoreManager: DataStoreManager) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "timeScreen") {
        composable("timeScreen") {
            TimeScreen(navController,dataStoreManager, context)
        }
        composable("homeScreen") {
            HomeScreen(navController, context)
        }
        composable("settings") {
            SettingsScreen(navController, dataStoreManager, context)
        }
    }
}