package coded.toolbox.peaceout.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coded.toolbox.peaceout.ads.BannerAd
import coded.toolbox.peaceout.baisakhiFont
import coded.toolbox.peaceout.datastore.DataStoreManager
import coded.toolbox.peaceout.viewModels.SettingsViewModel
import coded.toolbox.peaceout.viewmodelfactory.SettingsViewModelFactory
import com.google.android.gms.ads.AdSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, dataStoreManager: DataStoreManager, context: Context)
{
    // Create the ViewModel using the factory
    val viewModel: SettingsViewModel = remember {
        SettingsViewModelFactory(dataStoreManager).create(SettingsViewModel::class.java)
    }
    // Fetching values from the ViewModel
    val fullDayHours by viewModel.fullDayHours.collectAsState()
    val fullDayMinutes by viewModel.fullDayMinutes.collectAsState()
    val halfDayHours by viewModel.halfDayHours.collectAsState()
    val halfDayMinutes by viewModel.halfDayMinutes.collectAsState()
    val isReminderEnabled by viewModel.isReminderEnabled.collectAsState()
    val reminderTime by viewModel.reminderTime.collectAsState()

    // For the animation scale of buttons
    var fullDayButtonScale by remember { mutableFloatStateOf(1f) }
    var halfDayButtonScale by remember { mutableFloatStateOf(1f) }
    var reminderButtonScale by remember { mutableFloatStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()

    // Animations for the buttons
    val scaleFullDay by animateFloatAsState(
        targetValue = fullDayButtonScale,
        label = "Button Full Day Animation",
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val scaleHalfDay by animateFloatAsState(
        targetValue = halfDayButtonScale,
        label = "Button Half Day Animation",
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val scaleReminder by animateFloatAsState(
        targetValue = reminderButtonScale,
        label = "Button Reminder Animation",
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        bottomBar = {
            BannerAd(adUnitId = "ca-app-pub-4154300661292859/7964306492", adSize = AdSize.BANNER)
            Spacer(modifier = Modifier.height(10.dp))
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp)
                    .verticalScroll(scrollState)
                    .padding(WindowInsets.ime.asPaddingValues())
            ) {
                // Full Day Working Hours
                Text(
                    "Full Day Working Hours",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = fullDayHours.toString(),
                        onValueChange = { viewModel.updateFullDayHours(it.filter { it.isDigit() }.toIntOrNull() ?: fullDayHours) },
                        label = { Text("Hours") },
                        modifier = Modifier.width(70.dp),
                        textStyle = TextStyle(fontFamily = baisakhiFont, fontSize = 20.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = fullDayMinutes.toString(),
                        onValueChange = { viewModel.updateFullDayMinutes(it.filter { it.isDigit() }.toIntOrNull() ?: fullDayMinutes) },
                        label = { Text("Minutes") },
                        modifier = Modifier.width(80.dp),
                        textStyle = TextStyle(fontFamily = baisakhiFont, fontSize = 20.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            fullDayButtonScale = 0.90f
                            delay(100)
                            fullDayButtonScale = 1f
                            Toast.makeText(context, "Full Day Hours saved!", Toast.LENGTH_SHORT).show()
                            viewModel.saveFullDaySettings(fullDayHours, fullDayMinutes)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(scaleFullDay)
                        .padding(vertical = 8.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(50.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x000000))
                ) {
                    Text("Save", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                // Half Day Working Hours
                Text(
                    "Half Day Working Hours",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = halfDayHours.toString(),
                        onValueChange = { viewModel.updateHalfDayHours(it.filter { it.isDigit() }.toIntOrNull() ?: halfDayHours) },
                        label = { Text("Hours") },
                        modifier = Modifier.width(70.dp),
                        textStyle = TextStyle(fontFamily = baisakhiFont, fontSize = 20.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = halfDayMinutes.toString(),
                        onValueChange = { viewModel.updateHalfDayMinutes(it.filter { it.isDigit() }.toIntOrNull() ?: halfDayMinutes) },
                        label = { Text("Minutes") },
                        modifier = Modifier.width(80.dp),
                        textStyle = TextStyle(fontFamily = baisakhiFont, fontSize = 20.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            halfDayButtonScale = 0.90f
                            delay(100)
                            halfDayButtonScale = 1f
                            Toast.makeText(context, "Half Day Hours saved!", Toast.LENGTH_SHORT).show()
                            viewModel.saveHalfDaySettings(halfDayHours, halfDayMinutes)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(scaleHalfDay)
                        .padding(vertical = 8.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(50.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x000000))
                ) {
                    Text("Save", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                // Reminder Settings
                Text(
                    "Reminder Settings",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                SwitchSetting(label = "Enable Reminders", checked = isReminderEnabled) {
                    viewModel.setReminderEnabled(it)
                }

                if (isReminderEnabled) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Remind me before      ")

                        OutlinedTextField(
                            value = reminderTime.toString(),
                            onValueChange = { viewModel.updateReminderTime(it.filter { it.isDigit() }.toIntOrNull() ?: reminderTime) },
                            label = { Text("Minutes") },
                            modifier = Modifier.width(80.dp),
                            textStyle = TextStyle(fontFamily = baisakhiFont, fontSize = 20.sp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                reminderButtonScale = 0.90f
                                delay(100)
                                reminderButtonScale = 1f
                                Toast.makeText(context, "Reminder saved!", Toast.LENGTH_SHORT).show()
                                viewModel.saveReminderSettings(isReminderEnabled,reminderTime)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(scaleReminder)
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(50.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0x000000))
                    ) {
                        Text("Save", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Add additional settings or components here if needed
            }
        }
    )
}

@Composable
fun SwitchSetting(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = checked,
            onCheckedChange, colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.Black,
                checkedTrackColor = Color.Black,
                uncheckedTrackColor = Color.White,
                checkedBorderColor = Color.Black,
                uncheckedBorderColor = Color.Black
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewSettings() {
    SettingsScreen(rememberNavController(), DataStoreManager(LocalContext.current), LocalContext.current)

}