package com.meldcx.modules.select_app

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class SelectAppVM(application: Application) : AndroidViewModel(application) {
  private val TAG = "SelectAppVM"
  val apps = MutableStateFlow(emptyList<String>())

  init {
    try {
      viewModelScope.launch(Dispatchers.IO) {
        val ls = mutableListOf<String>()
        val packageManager = application.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        packages.forEach {
          if (packageManager.getLaunchIntentForPackage(it.packageName) != null && it.packageName != application.packageName) {
            ls.add(it.packageName)
          }
        }
        delay(500)
        apps.value = ls
        Log.d(TAG, "application query size: ${apps.value.size}")
      }
    } catch (e: Exception) {
      Log.e(TAG, e.message ?: e.toString())
    }
  }
}