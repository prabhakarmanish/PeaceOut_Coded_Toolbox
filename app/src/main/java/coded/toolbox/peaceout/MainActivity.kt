package coded.toolbox.peaceout

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.rememberNavController
import coded.toolbox.peaceout.datastore.DataStoreManager
import coded.toolbox.peaceout.navigation.NavGraph

val baisakhiFont = FontFamily(Font(R.font.baisakhi_font, FontWeight.Normal))

class MainActivity : ComponentActivity() {
    private val dataStoreManager by lazy { DataStoreManager(this) }  // Use lazy initialization

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController, dataStoreManager = dataStoreManager)
        }
    }
}
