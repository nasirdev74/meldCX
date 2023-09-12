package com.meldcx.modules.schedules.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.meldcx.entity.*

@Composable
fun BuildSchedules(
  schedules: List<Schedule>,
  onUpdate: (schedule: Schedule) -> Unit,
  onDelete: (schedule: Schedule) -> Unit,
) {
  Column(
    modifier = Modifier
      .padding(top = 50.dp)
      .verticalScroll(rememberScrollState())
  ) {
    schedules.forEach {
      BuildSchedule(
        schedule = it,
        onUpdate = { onUpdate.invoke(it) },
        onDelete = { onDelete.invoke(it) },
      )
    }
    Spacer(modifier = Modifier.height(50.dp))
  }
}