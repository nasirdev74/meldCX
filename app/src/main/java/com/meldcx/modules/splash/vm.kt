package com.meldcx.modules.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.meldcx.routes.AppRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashVM(application: Application) : AndroidViewModel(application) {
  fun init(navController: NavController) {
    viewModelScope.launch {
      delay(500)
      navController.navigate(AppRoutes.Schedules.route)
    }
  }
}