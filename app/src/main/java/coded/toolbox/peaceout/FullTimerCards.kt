package coded.toolbox.peaceout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coded.toolbox.peaceout.datastore.DataStoreManager
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.concurrent.TimeUnit

@Composable
fun FullTimerCards(dataStoreManager: DataStoreManager) {
    // Retrieve values from DataStore
    val selectedHour by dataStoreManager.selectedHourFlow.collectAsState(initial = 9) // Example default hour
    val selectedMinute by dataStoreManager.selectedMinuteFlow.collectAsState(initial = 15) // Example default minute
    val isFullDay by dataStoreManager.isFullDayFlow.collectAsState(initial = true)
    val fullDayHours by dataStoreManager.fullDayHoursFlow.collectAsState(initial = 8)
    val fullDayMinutes by dataStoreManager.fullDayMinutesFlow.collectAsState(initial = 45)
    val halfDayHours by dataStoreManager.halfDayHoursFlow.collectAsState(initial = 4)
    val halfDayMinutes by dataStoreManager.halfDayMinutesFlow.collectAsState(initial = 15)

    // Loading state
    var isLoading by remember { mutableStateOf(true) }

    // Variables to store the final result (hours, minutes, seconds)
    var resultHour by remember { mutableStateOf(0) }
    var resultMinute by remember { mutableStateOf(0) }
    var resultSecond by remember { mutableStateOf(0) }

    // LaunchedEffect that runs continuously, recalculating the time difference every second
    LaunchedEffect(Unit) {
        // Initial delay for loading state
        delay(1000) // Simulate loading time

        isLoading = false // Set loading to false after delay

        while (true) {
            // Get current time
            val currentCalendar = Calendar.getInstance()
            val currentTimeMillis = currentCalendar.timeInMillis

            // Create a calendar for the target time (user input time + full/half day)
            val targetCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
                set(Calendar.SECOND, 0) // Reset seconds to 0
                if (isFullDay) {
                    add(Calendar.HOUR_OF_DAY, fullDayHours)
                    add(Calendar.MINUTE, fullDayMinutes)
                } else {
                    add(Calendar.HOUR_OF_DAY, halfDayHours)
                    add(Calendar.MINUTE, halfDayMinutes)
                }
            }

            // Calculate the difference between the target time and current time in milliseconds
            val timeDifferenceMillis = targetCalendar.timeInMillis - currentTimeMillis

            if (timeDifferenceMillis > 0) {
                // Convert the time difference from milliseconds to hours, minutes, and seconds
                resultHour = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis).toInt()
                resultMinute = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis).toInt() % 60
                resultSecond = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis).toInt() % 60
            } else {
                // If the time has passed, stop the countdown
                resultHour = 0
                resultMinute = 0
                resultSecond = 0
            }

            // Delay for 1 second before updating again
            delay(1000L)
        }
    }

    // Display loading indicator or the remaining time in cards
    if (isLoading) {
        // Show a loading indicator while loading
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TimeCardWithFlip("Hours", resultHour)
            TimeCardWithFlip("Minutes", resultMinute)
            TimeCardWithFlip("Seconds", resultSecond)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview() {
    val mockDataStoreManager = DataStoreManager(context = LocalContext.current) // Mock for preview
    FullTimerCards(dataStoreManager = mockDataStoreManager)
}
