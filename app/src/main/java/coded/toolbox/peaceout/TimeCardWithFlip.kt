package coded.toolbox.peaceout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun TimeCardWithFlip(label: String, value: Int) {
    var oldValue by remember { mutableStateOf(value) }
    var rotationY by remember { mutableStateOf(0f) }

    // Trigger flip animation when value changes
    if (oldValue != value) {
        oldValue = value
        rotationY += 360f // Flip the card by 360 degrees
    }

    // Animate the rotation along the Y-axis for flip effect
    val animatedRotationY by animateFloatAsState(
        targetValue = rotationY,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 150) // Control flip duration
    )

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
            .graphicsLayer {
                rotationY = animatedRotationY // Apply flip on Y-axis
                cameraDistance = 12f * density // Adjust camera distance for 3D effect
            }
            .background(Color.White)
            .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(8.dp)), // Add border here
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(text = label,
                style = MaterialTheme.typography.labelMedium,
                fontFamily = baisakhiFont,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = String.format(Locale.getDefault(),"%02d", value),
                style = MaterialTheme.typography.displayMedium,
                fontFamily = baisakhiFont
            )
        }
    }
}
