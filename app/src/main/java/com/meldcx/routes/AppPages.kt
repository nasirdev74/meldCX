package com.meldcx.routes

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.meldcx.modules.schedules.SchedulesView
import com.meldcx.modules.select_app.SelectAppView
import com.meldcx.modules.select_date_time.*
import com.meldcx.modules.splash.SplashView

@Composable
fun AppPages() {
  val navController = rememberNavController()
  val systemUiController = rememberSystemUiController()
  systemUiController.setNavigationBarColor(color = Color.Black)
  systemUiController.setStatusBarColor(color = Color.Transparent)

  NavHost(
    navController = navController,
    startDestination = AppRoutes.Splash.route,
    modifier = Modifier
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    composable(AppRoutes.Splash.route) {
      SplashView(navController = navController)
    }
    composable(AppRoutes.Schedules.route) {
      SchedulesView(navController = navController)
    }
    composable(AppRoutes.SelectApp.route) {
      SelectAppView(navController = navController)
    }
    composable(AppRoutes.SelectDateTime.route) { backStackEntry ->
      val packageName = backStackEntry.arguments?.getString("packageName") ?: "no package name found"
      val scheduleId = backStackEntry.arguments?.getString("scheduleId")?.toIntOrNull()
      SelectDateTimeView(
        navController = navController,
        scheduleId = scheduleId ?: 0,
        packageName = packageName,
      )
    }
  }
}
