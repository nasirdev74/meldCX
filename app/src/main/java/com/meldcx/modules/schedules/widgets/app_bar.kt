package com.meldcx.modules.schedules.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.meldcx.ui.theme.fontNunito
import com.meldcx.ui.theme.primaryColor

@Composable
fun SchedulesAppBar(onAddClick: () -> Unit) {
  Column(
    modifier = Modifier
      .height(50.dp)
      .fillMaxWidth()
      .background(primaryColor),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.End
  ) {
    Box(
      modifier = Modifier
        .clip(
          RoundedCornerShape(
            topStart = 5.dp, bottomStart = 5.dp
          )
        )
        .clickable { onAddClick.invoke() }
        .fillMaxHeight(),
      contentAlignment = Alignment.CenterEnd,
    ) {
      Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Spacer(modifier = Modifier.width(20.dp))
        Text(
          "New Schedule", style = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = fontNunito,
            fontWeight = FontWeight.Medium,
          )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
          imageVector = Icons.Rounded.Add,
          contentDescription = "Add Circle",
          tint = Color.White,
          modifier = Modifier
            .height(20.dp)
            .width(20.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
      }
    }
  }
}
