package com.meldcx.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.meldcx.database.*
import com.meldcx.repo.*
import kotlinx.coroutines.*

class OnScheduleReceiver : BroadcastReceiver() {
  private val TAG = "OnScheduleReceiver"
  override fun onReceive(context: Context?, intent: Intent?) {
    Log.i(TAG, "broadcast received: ${intent.toString()}")
    try {
      if (context != null) {
        val packageName = intent?.getStringExtra("package_name")
        val scheduleId = intent?.getLongExtra("schedule_id", 0) ?: 0
        Log.i(TAG, "package name found: $packageName")
        Log.i(TAG, "schedule id found: $scheduleId")
        if (!packageName.isNullOrEmpty()) {
          val pm = context.packageManager
          val launchIntent = pm?.getLaunchIntentForPackage(packageName)
          launchIntent?.let {
            context.startActivity(it)
            Log.i(TAG, "application started: $packageName")
            markExecuted(context, scheduleId)
          }
        }
      }
    } catch (e: Exception) {
      Log.e(TAG, e.message ?: e.toString())
    }
  }

  @OptIn(DelicateCoroutinesApi::class)
  fun markExecuted(context: Context?, scheduleId: Long) {
    try {
      if (scheduleId.toInt() != 0 && context != null) {
        val scheduleRepository = ScheduleRepository(ScheduleDatabase.getInstance(context.applicationContext).scheduleDao())
        GlobalScope.launch {
          val result = scheduleRepository.markExecuted(scheduleId)
          Log.i(TAG, "scheduleRepository.markExecuted: $result")
        }
      }
    } catch (e: Exception) {
      Log.e(TAG, e.message ?: e.toString())
    }
  }
}
