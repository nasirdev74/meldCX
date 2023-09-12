package com.meldcx.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.*
import com.meldcx.ui.theme.*

const val defaultAppBarHeight = 60

@Composable
fun DefaultAppBar(title: String) {
  Row(
    modifier = Modifier
      .height(defaultAppBarHeight.dp)
      .fillMaxWidth()
      .background(primaryColor),
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Spacer(modifier = Modifier.width(20.dp))
    Text(
      title,
      style = TextStyle(
        fontSize = 20.sp,
        color = Color.White,
        fontFamily = fontNunito,
      )
    )
  }
}