package com.example.flowstimerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flowstimerapp.ui.theme.FlowsTimerAPpTheme
import com.example.flowstimerapp.utils.VibrationHelper
import com.example.flowstimerapp.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val vibrationHelper = VibrationHelper(this)

        setContent {
            FlowsTimerAPpTheme {
                var userValue by remember { mutableStateOf("") }
                val viewModel = viewModel<MainViewModel>()
                val time = viewModel.countdownFlow.collectAsState(initial = 0)
                val isTimerComplete = viewModel.isCountdownComplete.collectAsState()
                val snackBarHostState = remember { SnackbarHostState() }

                LaunchedEffect(isTimerComplete.value) {
                    if (isTimerComplete.value) {
                        vibrationHelper.vibrateOnComplete()

                        snackBarHostState.showSnackbar(
                            message = "Timer Completed",
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                //Animation State
                val rotation = rememberInfiniteTransition()
                val angle by rotation.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFF1A1A1A), // Dark background
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackBarHostState,
                            snackbar = { snackBarData ->
                                Snackbar(
                                    snackbarData = snackBarData,
                                    containerColor = Color(0xFF83DEE8), // Background color
                                    contentColor = Color.Black,  // Text color
                                    actionColor = Color.White,   // Action button color
                                    dismissActionContentColor = Color.White, // Dismiss action color
                                    modifier = Modifier.padding(16.dp)
                                )
                            })
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Animated timer text
                            Box(
                                modifier = Modifier.size(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.size(200.dp)) {
                                    drawArc(
                                        color = Color(0xFF2D2D2D),
                                        startAngle = 0f,
                                        sweepAngle = 360f,
                                        useCenter = false,
                                        style = Stroke(width = 20f, cap = StrokeCap.Round)
                                    )

                                    if (time.value > 0) {
                                        drawArc(
                                            color = Color(0xFF00BCD4),
                                            startAngle = -90f,
                                            sweepAngle = angle,
                                            useCenter = false,
                                            style = Stroke(width = 20f, cap = StrokeCap.Round)
                                        )
                                    }

                                }
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        time.value.toString(),
                                        fontSize = 60.sp,
                                        modifier = Modifier

                                            .padding(20.dp),
                                        color = Color(0xFF00BCD4) // Cyan color
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(30.dp))

                            TextField(
                                value = userValue,
                                onValueChange = { newValue ->
                                    if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                        userValue = newValue
                                    }
                                },
                                modifier = Modifier
                                    .padding(20.dp)
                                    .size(width = 200.dp, height = 60.dp),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color(0xFF2D2D2D),
                                    focusedContainerColor = Color(0xFF2D2D2D),
                                    unfocusedTextColor = Color.White,
                                    focusedTextColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent  // Removes the disabled line
                                ),
                                label = { Text("Enter seconds", color = Color.Gray) },
                                singleLine = true,
                                shape = RoundedCornerShape(20.dp)
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            Button(
                                onClick = {
                                    userValue.toIntOrNull()?.let { seconds ->
                                        viewModel.startCountdown(seconds)
                                    }
                                },
                                modifier = Modifier.size(width = 200.dp, height = 50.dp)
                            ) {
                                Text("Start", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
