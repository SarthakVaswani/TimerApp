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

    private val _countDownFlow = MutableStateFlow(0)
    val countDownFlow = _countDownFlow.asStateFlow()

    private var isCountingDown = false


    fun startCountdown(timeInSeconds: Int) {
        if (isCountingDown) return  // Prevent multiple countdowns

        viewModelScope.launch {
            isCountingDown = true
            _countDownFlow.value = timeInSeconds

            while (_countDownFlow.value > 0) {
                delay(1000L)  // Wait for 1 second
                _countDownFlow.value -= 1
            }

            isCountingDown = false
        }
    }
}