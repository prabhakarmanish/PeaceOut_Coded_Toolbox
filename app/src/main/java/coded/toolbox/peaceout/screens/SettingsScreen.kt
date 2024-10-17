package coded.toolbox.peaceout.screens

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coded.toolbox.peaceout.baisakhiFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen()
{
    var fullDayButtonScale by remember { mutableStateOf(1f) }
    var halfDayButtonScale by remember { mutableStateOf(1f) }
    var reminderButtonScale by remember { mutableStateOf(1f) }
    var cardButtonScale by remember { mutableStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()

    val scale by animateFloatAsState(
        targetValue = fullDayButtonScale,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val scale2 by animateFloatAsState(
        targetValue = halfDayButtonScale,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val scale3 by animateFloatAsState(
        targetValue = reminderButtonScale,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )


    val scale4 by animateFloatAsState(
        targetValue = cardButtonScale,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )


    val context = LocalContext.current
    var fullDayHours by remember { mutableStateOf("8") }
    var fullDayMinutes by remember { mutableStateOf("45") }
    var halfDayHours by remember { mutableStateOf("4") }
    var halfDayMinutes by remember { mutableStateOf("15") }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var reminderTime by remember { mutableStateOf("10") }
    var isReminderEnabled by remember { mutableStateOf(true) }
    var resetPressCount by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    Scaffold(modifier = Modifier.background(Color.White), topBar = {
        TopAppBar(
            title = {
                Text(
                    "Settings",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
        )
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
                .verticalScroll(scrollState)
        ) {
            // Full Day Working Hours
            Text(
                "Full Day Working Hours",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = fullDayHours, onValueChange = { newValue ->
                            fullDayHours = when {
                                newValue.isEmpty() -> "0"
                                newValue.all { char -> char.isDigit() } -> newValue
                                else -> fullDayHours
                            }
                        }, modifier = Modifier
                            .width(70.dp)
                            .height(56.dp), label = {
                            Text("Hours")
                        }, singleLine = true, textStyle = TextStyle(
                            fontFamily = baisakhiFont, fontSize = 20.sp
                        ), keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ), visualTransformation = VisualTransformation.None
                    )


                    OutlinedTextField(
                        value = fullDayMinutes, onValueChange = { newValue ->
                            fullDayMinutes = when {
                                newValue.isEmpty() -> "0"
                                newValue.all { char -> char.isDigit() } -> newValue
                                else -> fullDayMinutes
                            }
                        }, modifier = Modifier
                            .width(80.dp)
                            .height(56.dp), label = {
                            Text("Minutes")
                        }, singleLine = true, textStyle = TextStyle(
                            fontFamily = baisakhiFont, fontSize = 20.sp
                        ), keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ), visualTransformation = VisualTransformation.None
                    )
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            fullDayButtonScale = 0.90f
                            delay(100)
                            fullDayButtonScale = 1f
                        }
                        Toast.makeText(
                            context,
                            "Full Day Hours saved: ${if (fullDayHours.isNotBlank()) "$fullDayHours hours" else ""} ${if (fullDayMinutes.isNotBlank()) "$fullDayMinutes minutes" else ""}".trim(),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.scale(scale),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x000000)),
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(
                        "Save", color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            // Half Day Working Hours
            Text(
                "Half Day Working Hours",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = halfDayHours, onValueChange = { newValue ->
                            halfDayHours = when {
                                newValue.isEmpty() -> "0"
                                newValue.all { char -> char.isDigit() } -> newValue
                                else -> halfDayHours
                            }
                        }, modifier = Modifier
                            .width(70.dp)
                            .height(56.dp), label = {
                            Text("Hours")
                        }, singleLine = true, textStyle = TextStyle(
                            fontFamily = baisakhiFont, fontSize = 20.sp
                        ), keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ), visualTransformation = VisualTransformation.None
                    )
                    OutlinedTextField(
                        value = halfDayMinutes, onValueChange = { newValue ->
                            halfDayMinutes = when {
                                newValue.isEmpty() -> "0"
                                newValue.all { char -> char.isDigit() } -> newValue
                                else -> halfDayMinutes
                            }
                        }, modifier = Modifier
                            .width(80.dp)
                            .height(56.dp), label = {
                            Text("Minutes")
                        }, singleLine = true, textStyle = TextStyle(
                            fontFamily = baisakhiFont, fontSize = 20.sp
                        ), keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ), visualTransformation = VisualTransformation.None
                    )
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            halfDayButtonScale = 0.90f
                            delay(100)
                            halfDayButtonScale = 1f
                        }
                        Toast.makeText(
                            context,
                            "Half Day Hours saved: ${if (halfDayHours.isNotBlank()) "$halfDayHours hours" else ""} ${if (halfDayMinutes.isNotBlank()) "$halfDayMinutes minutes" else ""}".trim(),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.scale(scale2),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x000000)), // White background
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Text(
                        "Save", color = Color.Black, fontWeight = FontWeight.Bold
                    )
                }
            }

            // Reminder Settings
            Text(
                "Reminder Settings",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Add the toggle switch
            SwitchSetting(label = "Enable Reminders",
                checked = isReminderEnabled,
                onCheckedChange = { isReminderEnabled = it })

            if (isReminderEnabled) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Remind me before ")
                    OutlinedTextField(
                        value = reminderTime, onValueChange = { newValue ->
                            reminderTime = when {
                                newValue.isEmpty() -> "0"
                                newValue.all { char -> char.isDigit() } -> newValue
                                else -> reminderTime
                            }
                        }, modifier = Modifier
                            .width(80.dp)
                            .height(56.dp), label = {
                            Text("Minutes")
                        }, singleLine = true, textStyle = TextStyle(
                            fontFamily = baisakhiFont, fontSize = 20.sp
                        ), keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ), visualTransformation = VisualTransformation.None
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                reminderButtonScale = 0.90f
                                delay(100)
                                reminderButtonScale = 1f
                            }
                            Toast.makeText(
                                context,
                                "Reminder saved: ${if (reminderTime.isNotBlank()) "$reminderTime minutes before" else ""}".trim(),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier.scale(scale3),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0x000000)),
                        shape = RoundedCornerShape(50.dp),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(
                            "Save", color = Color.Black, fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy Policy Button
            IconTextButton(text = "Privacy Policy",
                icon = Icons.Default.Info,
                onClick = { /* Navigate to Privacy Policy */ })

            // Terms of Service Button
            IconTextButton(text = "Terms of Service",
                icon = Icons.Default.CheckCircle,
                onClick = { /* Navigate to Terms of Service */ })

            Spacer(modifier = Modifier.height(16.dp))
            Text("App Version: 1.0.0", modifier = Modifier.padding(vertical = 8.dp))
        }
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    resetPressCount++
                    if (resetPressCount == 1) {
                        Toast.makeText(
                            context, "Press again to reset all settings", Toast.LENGTH_SHORT
                        ).show()
                    } else if (resetPressCount == 2) {
                        fullDayHours = "8"
                        fullDayMinutes = "45"
                        halfDayHours = "4"
                        halfDayMinutes = "15"
                        notificationsEnabled = true
                        reminderTime = "10"
                        resetPressCount = 0
                        Toast.makeText(context, "All settings have been reset", Toast.LENGTH_SHORT)
                            .show()
                    }
                }, modifier = Modifier.padding(16.dp), containerColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset",
                    tint = Color.Black,
                    modifier = Modifier.graphicsLayer(scaleX = -1f)
                )
            }
        })
}

@Composable
fun SwitchSetting(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Switch(
            checked = checked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(
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


@Composable
fun IconTextButton(text: String, icon: ImageVector, onClick: () -> Unit) {

    var buttonScale by remember { mutableStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()

    val scale by animateFloatAsState(
        targetValue = buttonScale,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Button(
        onClick = {
            coroutineScope.launch {
                buttonScale = 0.95f
                delay(100)
                buttonScale = 1f
            }
            onClick() // Call the original onClick lambda
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale), // Apply the scale modifier
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text, color = Color.Black, fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}
