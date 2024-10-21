package coded.toolbox.peaceout.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coded.toolbox.peaceout.FullTimerCards
import coded.toolbox.peaceout.R
import coded.toolbox.peaceout.datastore.DataStoreManager
import kotlin.random.Random
import kotlin.system.exitProcess

@Composable
fun TimeScreen(
    navController: NavHostController,
    dataStoreManager: DataStoreManager,
    context: Context
) {

    val selectedHour by dataStoreManager.selectedHourFlow.collectAsState(initial = 9) // Example default hour
    val selectedMinute by dataStoreManager.selectedMinuteFlow.collectAsState(initial = 15) // Example default minute
    val isFullDay by dataStoreManager.isFullDayFlow.collectAsState(initial = true)
    val fullDayHours by dataStoreManager.fullDayHoursFlow.collectAsState(initial = 8)
    val fullDayMinutes by dataStoreManager.fullDayMinutesFlow.collectAsState(initial = 45)
    val halfDayHours by dataStoreManager.halfDayHoursFlow.collectAsState(initial = 4)
    val halfDayMinutes by dataStoreManager.halfDayMinutesFlow.collectAsState(initial = 15)

    var backPressedOnce by rememberSaveable { mutableStateOf(false) } // State to track back press
    var lastBackPressedTime by rememberSaveable { mutableLongStateOf(0L) } // Timestamp of last back press
    val context = LocalContext.current

    val backgroundImages = listOf(
        R.drawable.back1,
        R.drawable.back2,
        R.drawable.back3,
        R.drawable.back4
    )
    val randomIndex = rememberSaveable { Random.nextInt(backgroundImages.size) }
    val backgroundImage = backgroundImages[randomIndex]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit)
            {
                detectTapGestures(
                    onLongPress = {

                        // Get user-inputted time
                     },
                    onDoubleTap = {
                       // navController.navigate("homeScreen")
                    }
                )
            }
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        FullTimerCards(dataStoreManager = dataStoreManager)

        FloatingActionButton(
            onClick = {  navController.navigate("homeScreen") },
            containerColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        // Handle back press
        BackHandler {
            val currentTime = System.currentTimeMillis()
            if (backPressedOnce && (currentTime - lastBackPressedTime < 2000)) {
                // Close the app if pressed again within 2 seconds
                android.os.Process.killProcess(android.os.Process.myPid())
                exitProcess(0)
            } else {
                backPressedOnce = true
                lastBackPressedTime = currentTime
                Toast.makeText(context, "Press again to close the app", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    TimeScreen(
        navController = NavHostController(LocalContext.current),
        dataStoreManager = DataStoreManager(LocalContext.current),
        context = LocalContext.current
    )

}