package com.meldcx.modules.select_date_time

import android.app.*
import android.widget.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import com.meldcx.modules.select_date_time.widgets.*
import com.meldcx.utils.*
import com.meldcx.widgets.*
import java.util.*

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SelectDateTimeView(
  packageName: String,
  scheduleId: Int,
  navController: NavController,
  vm: SelectDateTimeVM = viewModel()
) {
  val context = LocalContext.current
  val selectedDate = vm.selectedDate.collectAsState().value
  val selectedTime = vm.selectedTime.collectAsState().value

  LaunchedEffect(key1 = "SelectDateTimeView") {
    vm.init(packageName, scheduleId, navController)
  }

  Scaffold {
    Box(modifier = Modifier.padding(it), contentAlignment = Alignment.TopCenter) {
      Column(
        modifier = Modifier
          .padding(top = defaultAppBarHeight.dp)
          .padding(bottom = 200.dp)
          .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        BuildSelectDateTime(
          title = "Date",
          value = if (selectedDate == null) "-- --- ----" else selectedDate.format(dateFormatter)
        ) {
          vm.selectDate(context)
        }
        BuildSelectDateTime(
          title = "Time",
          value = if (selectedTime == null) "-- : --" else selectedTime.format(timeFormatter)
        ) {
          vm.selectTime(context)
        }
      }
      DefaultAppBar(title = "Select Date & Time")
      if (selectedDate != null && selectedTime != null) {
        ConfirmButton {
          vm.confirm(context)
        }
      }
    }
  }
}