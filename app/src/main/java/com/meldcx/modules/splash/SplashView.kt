package com.meldcx.modules.splash

import com.meldcx.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.*
import androidx.navigation.NavController
import com.meldcx.ui.theme.primaryColor

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SplashView(
  navController: NavController,
  vm: SplashVM = hiltViewModel(),
) {
  LaunchedEffect(key1 = "splash") {
    vm.init(navController)
  }
  Scaffold {
    Box(
      modifier = Modifier
        .padding(it)
        .background(primaryColor)
        .fillMaxHeight()
        .fillMaxWidth(),
      contentAlignment = Alignment.Center,
    ) {
      Image(
        contentDescription = null,
        painter = painterResource(id = R.drawable.meldcx),
        modifier = Modifier
      )
    }
  }
}