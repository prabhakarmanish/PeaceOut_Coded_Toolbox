package coded.toolbox.peaceout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onConfirm: (Int, Int, Boolean) -> Unit, // Pass selected hour, minute, and AM/PM
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    // Time picker state initialized with current hour/minute, using 12-hour format
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false, // 12-hour format picker
    )

    // Track AM/PM selection based on the selected hour
    val isAM = remember { mutableStateOf(timePickerState.hour < 12) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Select Time",
                fontFamily = baisakhiFont, // Use custom font for title
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                TimeInput(state = timePickerState)
                // Automatically adjust AM/PM based on hour selection
                isAM.value = timePickerState.hour < 12
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onDismiss) {
                    Text("Dismiss", fontFamily = baisakhiFont)
                }
                Button(onClick = {
                    onConfirm(timePickerState.hour, timePickerState.minute, isAM.value)
                }) {
                    Text("Confirm", fontFamily = baisakhiFont)
                }
            }
        },
    )
}
