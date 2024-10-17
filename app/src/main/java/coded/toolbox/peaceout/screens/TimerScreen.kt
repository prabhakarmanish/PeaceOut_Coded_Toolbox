package coded.toolbox.peaceout.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coded.toolbox.peaceout.R
import kotlin.random.Random

@Composable
fun TimeScreen() {

    var backPressedOnce by rememberSaveable { mutableStateOf(false) } // State to track back press
    var lastBackPressedTime by rememberSaveable { mutableStateOf(0L) } // Timestamp of last back press
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
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        //TimerScreen()

        FloatingActionButton(
            onClick = { }, // Navigate back to HomeScreen
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
                System.exit(0)
            } else {
                backPressedOnce = true
                lastBackPressedTime = currentTime
                Toast.makeText(context, "Press again to close the app", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview
@Composable
fun PreTime() {
    TimeScreen()
}