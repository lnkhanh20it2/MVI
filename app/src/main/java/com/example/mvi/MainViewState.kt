package com.example.mvi

sealed class MainViewState {
    //Idle
    object Idle : MainViewState()

    //number
    data class Number(val number: Int) : MainViewState()

    //error
    data class Error(val error: String) : MainViewState()
}