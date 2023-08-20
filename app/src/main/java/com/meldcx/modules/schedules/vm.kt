package com.meldcx.modules.schedules

import android.app.*
import android.content.*
import android.net.*
import android.os.*
import android.provider.*
import android.util.*
import androidx.lifecycle.*
import androidx.navigation.*
import com.meldcx.database.*
import com.meldcx.entity.*
import com.meldcx.repo.*
import com.meldcx.routes.*
import com.meldcx.utils.*
import kotlinx.coroutines.*

class SchedulesVM(private val application: Application) : AndroidViewModel(application) {
  val TAG = "SchedulesVM"
  private val scheduleRepository = ScheduleRepository(ScheduleDatabase.getInstance(application).scheduleDao())
  val schedules = ScheduleRepository(ScheduleDatabase.getInstance(application).scheduleDao()).getAllSchedulesFlow()

  fun createSchedule(context: Context, navController: NavController) {
    if (isSystemAlertWindowGranted() && isIgnoringBatteryOptimizations()) {
      navController.navigate(AppRoutes.SelectApp.route)
    } else {
      checkPermissions(context)
    }
  }

  fun deleteSchedule(schedule: Schedule, context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Test App")
    builder.setMessage("Are you sure you want to delete?")
    builder.setPositiveButton("Delete") { dialog, _ ->
      dialog.dismiss()
      viewModelScope.launch {
        scheduleRepository.delete(schedule)
        AlarmHelper.cancel(schedule, context)
      }
    }
    builder.setNegativeButton("Cancel") { dialog, _ ->
      dialog.dismiss()
    }
    builder.show()
  }

  fun updateSchedule(schedule: Schedule, navController: NavController) {
    navController.navigate("SelectDateTime/${schedule.packageName}/${schedule.id}")
  }

  fun checkPermissions(context: Context) {
    Log.i(TAG, "checkPermissions")
    try {
      if (!isSystemAlertWindowGranted()) {
        requestSystemAlertWindowSettings(context)
      } else if (!isIgnoringBatteryOptimizations()) {
        requestBatteryOptimizationSettings(context)
      }
    } catch (e: Exception) {
      Log.e(TAG, e.message ?: e.toString())
    }
  }

  private fun requestBatteryOptimizationSettings(context: Context) {
    if (!isIgnoringBatteryOptimizations()) {
      Log.i(TAG, "not ignoring battery optimization")
      val message = "To ensure that our app functions properly and delivers timely notifications, we recommend disabling battery optimizations. Would you like to disable battery optimizations for our app?"

      // show request dialog
      val builder = AlertDialog.Builder(context)
      builder.setTitle("Test App")
      builder.setMessage(message)
      builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
        val intent = Intent(
          Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
          Uri.parse("package:" + application.packageName)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(intent)
      }
      builder.setCancelable(false)
      builder.show()
    } else {
      Log.i(TAG, "already ignoring battery optimization")
    }
  }

  private fun requestSystemAlertWindowSettings(context: Context) {
    if (!isSystemAlertWindowGranted()) {
      Log.i(TAG, "not ignoring battery optimization")
      val message = "To start an application from background, we need 'Display over other apps' permission"
      // show request dialog
      val builder = AlertDialog.Builder(context)
      builder.setTitle("Test App")
      builder.setMessage(message)
      builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
        val intent = Intent(
          Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
          Uri.parse("package:" + application.packageName)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(intent)
      }
      builder.setCancelable(false)
      builder.show()
    } else {
      Log.i(TAG, "system alert window granted")
    }
  }

  private fun isIgnoringBatteryOptimizations(): Boolean {
    val powerManager = application.getSystemService(Context.POWER_SERVICE) as PowerManager
    return powerManager.isIgnoringBatteryOptimizations(application.packageName)
  }

  private fun isSystemAlertWindowGranted(): Boolean {
    return Settings.canDrawOverlays(application.applicationContext)
  }
}