package com.meldcx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.meldcx.routes.AppPages
import com.meldcx.ui.theme.MeldCXTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MeldCXTheme {
        AppPages()
      }
    }
  }
}
