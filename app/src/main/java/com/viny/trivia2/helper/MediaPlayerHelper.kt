package com.viny.trivia2.helper

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import com.viny.trivia2.R

object MediaPlayerHelper {

    fun playLoseMP3(activity: Activity) {
        var mediaPlayer: MediaPlayer? = null

        mediaPlayer = MediaPlayer.create(activity, R.raw.sonido_perder)

        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }
    }
}