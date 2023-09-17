package com.meldcx.receiver

import android.content.*
import android.util.Log
import com.meldcx.repo.*
import dagger.hilt.android.*
import kotlinx.coroutines.*
import javax.inject.*

@AndroidEntryPoint
class OnScheduleReceiver : BroadcastReceiver() {
  private val TAG = "OnScheduleReceiver"

  @Inject
  lateinit var scheduleRepository: ScheduleRepository
  override fun onReceive(context: Context?, intent: Intent?) {
    Log.i(TAG, "broadcast received: ${intent.toString()}")
    try {
      if (context != null) {
        val packageName = intent?.getStringExtra("package_name")
        val scheduleId = intent?.getLongExtra("schedule_id", 0) ?: 0
        Log.i(TAG, "package name: $packageName")
        Log.i(TAG, "schedule id: $scheduleId")
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
  private fun markExecuted(context: Context?, scheduleId: Long) {
    try {
      if (scheduleId.toInt() != 0 && context != null) {
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
