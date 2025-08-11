package com.viny.trivia2.helper

object StringHelper {

    fun hhmmFormat(minutes: Int, seconds: Int): String {
        return String.format("%02d:%02d", minutes, seconds)
    }
}