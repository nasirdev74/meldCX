package com.meldcx.modules.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.meldcx.routes.AppRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.*

@HiltViewModel
class SplashVM @Inject constructor() : ViewModel() {
  fun init(navController: NavController) {
    viewModelScope.launch {
      delay(500)
      navController.navigate(AppRoutes.Schedules.route)
    }
  }
}