package com.example.flowstimerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    private val _countdownFlow = MutableStateFlow(0)
    val countdownFlow = _countdownFlow.asStateFlow()

    private val _isCountdownComplete = MutableStateFlow(false)
    val isCountdownComplete = _isCountdownComplete.asStateFlow()

    private var isCountingDown = false


    fun startCountdown(timeInSeconds: Int) {
        if (isCountingDown) return  // Prevent multiple countdowns

        viewModelScope.launch {
            isCountingDown = true
            _isCountdownComplete.value = false
            _countdownFlow.value = timeInSeconds

            while (_countdownFlow.value > 0) {
                delay(1000L)  // Wait for 1 second
                _countdownFlow.value -= 1
            }

            isCountingDown = false
            _isCountdownComplete.value = true
        }
    }
}