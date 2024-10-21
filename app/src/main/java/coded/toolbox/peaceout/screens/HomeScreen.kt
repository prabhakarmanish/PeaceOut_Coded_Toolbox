package coded.toolbox.peaceout.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coded.toolbox.peaceout.R
import coded.toolbox.peaceout.TimePickerDialog
import coded.toolbox.peaceout.ads.BannerAd
import coded.toolbox.peaceout.baisakhiFont
import coded.toolbox.peaceout.datastore.DataStoreManager
import coded.toolbox.peaceout.viewModels.HomeViewModel
import coded.toolbox.peaceout.viewmodelfactory.HomeViewModelFactory
import com.google.android.gms.ads.AdSize
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    context: Context,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(DataStoreManager(context)))
) {
    // Collect state from the ViewModel
    val isFullDay by homeViewModel.isFullDay.collectAsState()
    val selectedItem by homeViewModel.selectedItem.collectAsState()
    var showTimePicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context2 = LocalContext.current


    LaunchedEffect(Unit) {
        // This block will run once when the HomeScreen is composed
        // Here, you could perform any actions needed when the screen starts.
        // For example, you can directly observe changes in the ViewModel.
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Filled.Share,
                      //      tint = Color.Black,
                            contentDescription = "Share"
                        )
                    },
                    label = { Text("Share") },
                    selected = selectedItem == 0,
                    onClick = {
                        homeViewModel.updateSelectedItem(0)
                        shareApp(context)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, // Set the icon color for selected state
                        unselectedIconColor = Color.Black, // Set the icon color for unselected state
                        selectedTextColor = Color.Black, // Set the text color for selected state
                        unselectedTextColor = Color.Black, // Set the text color for unselected state
                        indicatorColor = Color.Black // Set the background color when selected
                    )
                    //    alwaysShowLabel = false
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Filled.Settings,
                   //         tint = Color.Black,
                            contentDescription = "Settings"
                        )
                    },
                    label = { Text("Settings") },
                    selected = selectedItem == 1,
                    onClick = {
                        homeViewModel.updateSelectedItem(1)
                        navController.navigate("settings")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, // Set the icon color for selected state
                        unselectedIconColor = Color.Black, // Set the icon color for unselected state
                        selectedTextColor = Color.Black, // Set the text color for selected state
                        unselectedTextColor = Color.Black, // Set the text color for unselected state
                        indicatorColor = Color.Black // Set the background color when selected
                    )
                    //   alwaysShowLabel = false
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Filled.Star,
                           // tint = Color.Black,
                            contentDescription = "Rate"
                        )
                    },
                    label = { Text("Rate") },
                    selected = selectedItem == 2,
                    onClick = {
                        homeViewModel.updateSelectedItem(2)
                        rateApp(context)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, // Set the icon color for selected state
                        unselectedIconColor = Color.Black, // Set the icon color for unselected state
                        selectedTextColor = Color.Black, // Set the text color for selected state
                        unselectedTextColor = Color.Black, // Set the text color for unselected state
                        indicatorColor = Color.Black // Set the background color when selected
                    )
                    //     alwaysShowLabel = false
                )
            }
        },
        content = { paddingValues -> // Add paddingValues for content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.padding(40.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = if (isFullDay) R.drawable.hday else R.drawable.fday),
                        contentDescription = if (isFullDay) "Full Day" else "Half Day",
                        modifier = Modifier.size(90.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Switch(
                        checked = isFullDay,
                        onCheckedChange = { homeViewModel.toggleDayMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.White,
                            checkedTrackColor = Color.Black,
                            uncheckedTrackColor = Color.Black,
                            uncheckedBorderColor = Color.Black
                        )
                    )
                }

                Button(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 150.dp)
                        .widthIn(min = 300.dp, max = 500.dp),
                    onClick = {
                        showTimePicker = true
                        //    navController.navigate("timer")
                    },
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "entry-time",
                        style = TextStyle(
                            fontFamily = baisakhiFont,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))
            //    BannerAd(adUnitId = "ca-app-pub-4154300661292859/7964306492", adSize = AdSize.FULL_BANNER)
                Spacer(modifier = Modifier.height(10.dp))

                if (showTimePicker) {
                    TimePickerDialog(
                        onConfirm = { pickedHour, pickedMinute, isAM ->
                            showTimePicker = false
                            val hourIn24Format = if (isAM) pickedHour % 12 else pickedHour % 12 + 12
                            val dataStoreManager = DataStoreManager(context2)
                            scope.launch {
                                dataStoreManager.saveSelectedTime(hourIn24Format, pickedMinute)
                            }
                            navController.navigate("timeScreen")
                        },
                        onDismiss = { showTimePicker = false }
                    )
                }

            }
        }
    )
}

// Function to share the app link
private fun shareApp(context: Context) {
    val appPackageName = context.packageName
    val shareText =
        "Check out this amazing app: https://play.google.com/store/apps/details?id=$appPackageName"
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(sendIntent, null))
}

// Function to rate the app
private fun rateApp(context: Context) {
    val appPackageName = context.packageName
    val rateIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("market://details?id=$appPackageName")
    }
    try {
        context.startActivity(rateIntent)
    } catch (e: ActivityNotFoundException) {
        // Fallback if Google Play is not installed
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController(), context = LocalContext.current)

}