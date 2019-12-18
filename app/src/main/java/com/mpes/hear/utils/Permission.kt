package com.mpes.hear.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat

class Permission(activity: Activity) {

    private val activity = activity

    private val permissions = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    fun requestPermissions() {

        val request = permissions.filter { ActivityCompat.checkSelfPermission(activity.baseContext, it) != PackageManager.PERMISSION_GRANTED }

        if (request.isNotEmpty())
            ActivityCompat.requestPermissions(activity, request.toTypedArray(), 9999)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            9999 -> {
                if ((grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED })) {
                    Log.d("Jholl", "permissao ok.")
                } else {
                    Log.d("Jholl", "permissao negadizzz.")

                    AlertDialog.Builder(activity)
                        .setMessage("Este app precisa dessas permissões para funcionar corretamente.")
                        .setTitle("Atenção")
                        .setPositiveButton("OK") { _, _ -> requestPermissions() }
                        .setNegativeButton("Cancelar") { _, _ -> activity.finish() }
                        .create()
                        .show()
                }
                return
            }
            else -> {
            }
        }
    }
}