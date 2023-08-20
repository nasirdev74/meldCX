package com.meldcx.modules.select_date_time.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.meldcx.ui.theme.*

@Composable
fun BuildSelectDateTime(
  title: String,
  value: String,
  onClick: () -> Unit,
) {
  Column {
    Text(
      title,
      style = TextStyle(
        fontSize = 24.sp,
        fontFamily = fontNunito,
        fontWeight = FontWeight.Bold
      )
    )
    Spacer(modifier = Modifier.height(20.dp))
    Box(
      modifier = Modifier
        .height(60.dp)
        .width(200.dp)
        .border(3.dp, color = primaryColor, shape = RoundedCornerShape(10.dp))
        .clip(RoundedCornerShape(10.dp))
        .clickable { onClick.invoke() },
      contentAlignment = Alignment.Center,
    ) {
      Text(value)
    }
  }
}