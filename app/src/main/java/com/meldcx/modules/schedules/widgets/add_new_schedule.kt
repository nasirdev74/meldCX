package com.meldcx.modules.schedules.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meldcx.ui.theme.fontNunito
import com.meldcx.ui.theme.primaryColor

@Composable
fun AddNewSchedule(onClick: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)
        .border(width = 3.dp, color = primaryColor, shape = RoundedCornerShape(10.dp))
        .clip(RoundedCornerShape(10.dp))
        .clickable { onClick.invoke() }
        .height(90.dp),
      contentAlignment = Alignment.Center,
    ) {
      Text(
        "Create New Schedule",
        style = TextStyle(
          fontFamily = fontNunito,
          fontSize = 20.sp,
        )
      )
    }
  }
}
