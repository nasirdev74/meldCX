package com.meldcx.modules.schedules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.meldcx.modules.schedules.widgets.*

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SchedulesView(navController: NavController, vm: SchedulesVM = viewModel()) {
  val context = LocalContext.current
  val schedules = vm.schedules.collectAsState(emptyList()).value
  LaunchedEffect(key1 = "SchedulesView") {
    vm.checkPermissions(context)
  }
  Scaffold {
    Box(
      modifier = Modifier.padding(it),
      contentAlignment = Alignment.TopCenter
    ) {
      if (schedules.isEmpty()) {
        AddNewSchedule {
          vm.createSchedule(context, navController)
        }
      } else {
        BuildSchedules(
          schedules,
          onUpdate = { schedule -> vm.updateSchedule(schedule, navController) },
          onDelete = { schedule -> vm.deleteSchedule(schedule, context) }
        )
      }
      SchedulesAppBar {
        vm.createSchedule(context, navController)
      }
    }
  }
}