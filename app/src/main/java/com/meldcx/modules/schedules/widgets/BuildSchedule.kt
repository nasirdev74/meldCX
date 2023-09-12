package com.meldcx.modules.schedules.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.google.accompanist.drawablepainter.*
import com.meldcx.entity.*
import com.meldcx.ui.theme.*
import com.meldcx.utils.*
import java.time.*
import java.util.*

@Composable
fun BuildSchedule(
  schedule: Schedule,
  onUpdate: () -> Unit,
  onDelete: () -> Unit,
) {
  val context = LocalContext.current
  val packageManager = context.packageManager
  val icon = packageManager.getApplicationIcon(schedule.packageName)
  val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(schedule.scheduledTime), ZoneOffset.UTC)
  val appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(schedule.packageName, 0)).toString()

  Column {
    Spacer(modifier = Modifier.height(15.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
      ) {
        Spacer(modifier = Modifier.width(25.dp))
        Box(
          modifier = Modifier
            .width(24.dp)
            .height(24.dp)
            .clip(CircleShape),
          contentAlignment = Alignment.Center,
        ) {
          Image(
            contentDescription = null,
            painter = rememberDrawablePainter(drawable = icon),
            modifier = Modifier
          )
        }
        Spacer(modifier = Modifier.width(11.dp))
        Text(
          appName,
          fontSize = 15.sp,
          fontFamily = fontNunito,
          fontWeight = FontWeight.W600,
        )
      }
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 25.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        "${dateTime.format(dateFormatter)}, ${dateTime.format(timeFormatter)}",
        style = TextStyle(
          fontSize = 14.sp,
        )
      )
      BuildScheduleStatus(schedule = schedule)
    }

    BoxWithConstraints(
      modifier = Modifier.fillMaxWidth()
    ) {
      val width = maxWidth / 2
      Row(
        modifier = Modifier.fillMaxWidth()
      ) {
        BuildActionButton(
          width = width,
          label = "Update",
          textColor = Color.White,
          buttonColor = primaryColor,
        ) { onUpdate.invoke() }

        BuildActionButton(
          width = width,
          label = "Delete",
          textColor = Color.Black,
          buttonColor = Color.Red.copy(0.5f),
        ) { onDelete.invoke() }
      }
    }
    Spacer(modifier = Modifier.height(10.dp))
  }
}

@Composable
private fun BuildActionButton(
  width: Dp,
  label: String,
  textColor: Color,
  buttonColor: Color,
  onClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .height(35.dp)
      .width(width)
      .clickable { onClick.invoke() }
      .background(buttonColor),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      label, style = TextStyle(
        fontSize = 16.sp,
        color = textColor,
        fontFamily = fontNunito,
        fontWeight = FontWeight.Medium,
      )
    )
  }
}

@Composable
private fun BuildScheduleStatus(schedule: Schedule) {
  var status = "Pending"
  var textColor = primaryColor
  if (schedule.isExecuted) {
    status = "Executed"
    textColor = Color.Green
  }

  Text(
    status.uppercase(Locale.ENGLISH),
    style = TextStyle(
      color = textColor,
      fontSize = 13.sp,
      fontFamily = fontNunito,
      fontWeight = FontWeight.SemiBold,
    )
  )
}