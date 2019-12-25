package com.example.opengldemo

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_gl.*

class GlActivity : AppCompatActivity() {
    var regender: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gl)

        val activitymanger: ActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activitymanger.deviceConfigurationInfo
        val supportes2: Boolean =
            configurationInfo.reqGlEsVersion >= 0x20000 || Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                    && (Build.FINGERPRINT.startsWith("generic")) || Build.FINGERPRINT.startsWith("unknown")
                    || Build.MODEL.contains("google-sdk") || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK build for x86")
        if (supportes2) {

            mGlview.setEGLContextClientVersion(2)
            mGlview.setRenderer(GlRegender())
            mGlview.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
            regender = true
        } else {
            Toast.makeText(this, "不支持", Toast.LENGTH_LONG).show()
            return
        }


    }

    override fun onPause() {
        super.onPause()
        if (regender) {
            mGlview.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (regender) {
            mGlview.onResume()
        }
    }


}
