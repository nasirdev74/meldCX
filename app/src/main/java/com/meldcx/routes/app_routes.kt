package com.meldcx.routes

sealed class AppRoutes(val route: String) {
  data object Splash : AppRoutes("splash")
  data object Schedules : AppRoutes("Schedules")
  data object SelectApp : AppRoutes("SelectApp")
  data object SelectDateTime : AppRoutes("SelectDateTime/{packageName}/{scheduleId}")
}
