package com.meldcx.modules.select_date_time.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.meldcx.ui.theme.*

@Composable
fun ConfirmButton(onClick: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxHeight(),
    verticalArrangement = Arrangement.Bottom
  ) {
    Box(
      modifier = Modifier
        .height(60.dp)
        .fillMaxWidth()
        .background(primaryColor)
        .clickable { onClick.invoke() },
      contentAlignment = Alignment.Center,
    ) {
      Text(
        "Confirm",
        style = TextStyle(
          fontSize = 20.sp,
          color = Color.White,
          fontFamily = fontNunito,
          fontWeight = FontWeight.SemiBold,
        )
      )
    }
  }
}