package com.meldcx.modules.select_app.widgets

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.meldcx.ui.theme.fontNunito

@Composable
fun BuildAddApp(
  appName: String,
  icon: Drawable,
  onAction: () -> Unit,
) {
  Column {
    Row(
      modifier = Modifier
        .clickable { onAction.invoke() }
        .padding(top = 14.dp, bottom = 14.dp)
        .fillMaxWidth(),
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
          )
        }
        Spacer(modifier = Modifier.width(11.dp))
        Text(
          appName,
          fontSize = 15.sp,
          fontFamily = fontNunito,
          fontWeight = FontWeight.W500,
        )
      }
    }
    Divider()
  }
}
