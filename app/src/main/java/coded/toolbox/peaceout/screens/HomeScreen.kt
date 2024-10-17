package coded.toolbox.peaceout.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coded.toolbox.peaceout.R
import coded.toolbox.peaceout.ads.BannerAd
import coded.toolbox.peaceout.baisakhiFont
import com.google.android.gms.ads.AdSize

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    var isFullDay by remember { mutableStateOf(true) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Share, contentDescription = "Share") },
                    label = { Text("Share", fontFamily = baisakhiFont) },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    alwaysShowLabel = false
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings", fontFamily = baisakhiFont) },
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 },
                    alwaysShowLabel = false
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Star, contentDescription = "Rate") },
                    label = { Text("Rate", fontFamily = baisakhiFont) },
                    selected = selectedItem == 2,
                    onClick = { selectedItem = 2 },
                    alwaysShowLabel = false
                )
            }
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Your provided layout code here
                Row(
                    modifier = Modifier
                        .padding(90.dp)
                ) {
                    Image(
                        painter = painterResource(id = if (isFullDay) R.drawable.hday else R.drawable.fday),
                        contentDescription = if (isFullDay) "Full Day" else "Half Day",
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Switch(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        checked = isFullDay,
                        onCheckedChange = { isFullDay = it },
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
                        .padding(top=120.dp)
                        .widthIn(min = 300.dp, max = 500.dp),
                    onClick = { showTimePicker = true },
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

                Spacer(modifier = Modifier.size(120.dp))

//                BannerAd(
//                    adUnitId = "ca-app-pub-3940256099942544/6300978111",
//                    adSize = AdSize.MEDIUM_RECTANGLE)
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview()
{
    HomeScreen()
}