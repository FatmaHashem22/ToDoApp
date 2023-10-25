package com.example.todoapp.ui.theme.fragments

import java.util.Calendar

fun Calendar.clearTime() {
    this.clear(Calendar.HOUR_OF_DAY)
    this.clear(Calendar.HOUR)
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.SECOND)
    this.clear(Calendar.MILLISECOND)
}