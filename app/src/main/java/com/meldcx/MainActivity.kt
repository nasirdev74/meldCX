package com.meldcx

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.meldcx.routes.AppPages
import com.meldcx.ui.theme.MeldCXTheme

class MainActivity : ComponentActivity() {
  private val TAG = "MainActivity"
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MeldCXTheme {
        AppPages()
      }
    }
    Log.i(TAG, "app launched")
  }
}
