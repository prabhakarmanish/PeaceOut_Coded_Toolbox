package coded.toolbox.peaceout.bottomsheets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen() {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                isBottomSheetVisible = false
            },
            sheetState = bottomSheetState
        ) {
            // The content inside the bottom sheet
            BottomSheetContent(onCloseClick = {
                scope.launch {
                    bottomSheetState.hide()
                    isBottomSheetVisible = false // Hide bottom sheet on close click
                }
            })
        }
    }

    // Main content
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bottom Sheet Screen") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EA),
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    isBottomSheetVisible = true
                }
            }) {
                Text("Open Bottom Sheet")
            }
        }
    }
}

@Composable
fun BottomSheetContent(onCloseClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bottom Sheet Title",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
        )

        Text(text = "This is a simple description inside the bottom sheet.")

        Spacer(modifier = Modifier.height(20.dp))

        // Close button
        Button(onClick = onCloseClick) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Close")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomSheetScreen() {
    BottomSheetContent(
        onCloseClick = {}
    )
}
