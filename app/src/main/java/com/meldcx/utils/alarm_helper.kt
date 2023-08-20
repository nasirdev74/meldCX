package com.meldcx.utils

import android.app.*
import android.content.*
import android.os.*
import android.util.*
import com.meldcx.entity.*
import com.meldcx.receiver.*

class AlarmHelper {
  companion object {
    private val TAG = "AlarmHelper"
    fun cancel(schedule: Schedule, context: Context) {
      try {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, OnScheduleReceiver::class.java).apply {
          putExtra("package_name", schedule.packageName)
          putExtra("schedule_id", schedule.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
          context, schedule.intentRequestCode, intent,
          PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        Log.i(TAG, "schedule cancelled: $schedule")
      } catch (e: Exception) {
        Log.e(TAG, e.message ?: e.toString());
      }
    }

    private fun addNew(schedule: Schedule, targetTime: Long, context: Context) {
      try {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, OnScheduleReceiver::class.java).apply {
          putExtra("package_name", schedule.packageName)
          putExtra("schedule_id", schedule.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
          context, schedule.intentRequestCode, intent,
          PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(
              AlarmManager.RTC_WAKEUP,
              targetTime,
              pendingIntent
            )
          } else {
            Log.e(TAG, "can't canScheduleExactAlarms")
          }
        } else {
          alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, targetTime, pendingIntent)
        }
        Log.i(TAG, "new schedule added to alarm manager: $schedule")
      } catch (e: Exception) {
        Log.e(TAG, e.message ?: e.toString())
      }
    }

    fun update(schedule: Schedule, targetTime: Long, context: Context) {
      try {
        cancel(schedule, context)
        addNew(schedule, targetTime, context)
      } catch (e: Exception) {
        Log.e(TAG, e.message ?: e.toString())
      }
    }
  }
}