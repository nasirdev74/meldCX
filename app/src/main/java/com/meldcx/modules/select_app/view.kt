package com.meldcx.modules.select_app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.meldcx.modules.select_app.widgets.BuildAddApp
import com.meldcx.ui.theme.primaryColor
import com.meldcx.widgets.*

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SelectAppView(navController: NavController, vm: SelectAppVM = viewModel()) {
  val packageManager = LocalContext.current.packageManager
  val apps = vm.apps.collectAsState().value
  Scaffold {
    Box(
      modifier = Modifier.padding(it)
    ) {
      Column(
        modifier = Modifier
          .verticalScroll(rememberScrollState())
          .padding(top = defaultAppBarHeight.dp),
        verticalArrangement = Arrangement.Top,
      ) {
        if (apps.isEmpty()) {
          Box(
            modifier = Modifier
              .height(350.dp)
              .fillMaxWidth(),
            contentAlignment = Alignment.Center,
          ) {
            CircularProgressIndicator(
              color = primaryColor, modifier = Modifier.size(40.dp)
            )
          }
        } else {
          apps.forEach {
            BuildAddApp(
              icon = packageManager.getApplicationIcon(it),
              appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(it, 0)).toString(),
            ) {
              navController.navigate("SelectDateTime/$it/0")
            }
          }
        }
      }
      DefaultAppBar(title = "Select An App")
    }
  }
}