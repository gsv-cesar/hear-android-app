package com.mpes.hear.services

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import android.util.Log

class VoiceService : Service() {

    private var media: MediaRecorder? = null

    override fun onCreate() {
        Log.d("Jholl", "Create voice service.")

        media = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            //setOutputFile("${externalCacheDir.absolutePath}/audiorecordtest.3gp")
            setOutputFile("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/audiorecordtest.3gp")
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            prepare()
            start()
        }

        Log.d("Jholl", "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/audiorecordtest.3gp")


    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("Jholl", "Bind voice service.")
        return null
    }

    override fun onDestroy() {
        Log.d("Jholl", "Destroy voice service.")

        media?.apply {
            stop()
            release()
        }

        media = null
    }
}