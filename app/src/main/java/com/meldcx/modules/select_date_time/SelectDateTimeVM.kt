package com.meldcx.modules.select_date_time

import android.app.*
import android.content.*
import android.util.*
import androidx.lifecycle.*
import androidx.navigation.*
import com.meldcx.database.*
import com.meldcx.entity.*
import com.meldcx.repo.*
import com.meldcx.routes.*
import com.meldcx.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.*
import java.time.format.*
import java.util.*

class SelectDateTimeVM(application: Application) : AndroidViewModel(application) {
  private val TAG = "SelectDateTimeVM"
  private var scheduleId = 0
  private lateinit var packageName: String
  private lateinit var navController: NavController
  private val scheduleRepository =
    ScheduleRepository(ScheduleDatabase.getInstance(application).scheduleDao())

  val selectedDate = MutableStateFlow<LocalDate?>(null)
  val selectedTime = MutableStateFlow<LocalTime?>(null)

  fun selectDate(context: Context) {
    val now = LocalDateTime.now()
    val year = now.year
    val month = now.monthValue - 1
    val day = now.dayOfMonth
    val datePicker = DatePickerDialog(
      context,
      { _, y, m, d -> selectedDate.value = LocalDate.of(y, m + 1, d) },
      year,
      month,
      day,
    )
    datePicker.datePicker.minDate = System.currentTimeMillis()
    datePicker.show()
  }

  fun selectTime(context: Context) {
    val timerPicker = TimePickerDialog(
      context,
      { _, h, m -> selectedTime.value = LocalTime.of(h, m) },
      LocalTime.now().hour,
      LocalTime.now().minute,
      false
    )
    timerPicker.show()
  }

  fun confirm(context: Context) {
    if (isSelectedDateTimeValid(context)) {
      val schedule = Schedule(
        id = scheduleId.toLong(),
        packageName = packageName,
        scheduledTime = getSelectedDateTime()?.toEpochSecond(ZoneOffset.UTC) ?: 0
      )
      viewModelScope.launch(Dispatchers.IO) {
        val result = scheduleRepository.save(schedule)
        Log.i(TAG, "save result: $result")
        if (result != null) {
          viewModelScope.launch(Dispatchers.Main) {
            AlarmHelper.update(result, getSelectedDateTimeInMillis(), context)
            showSucceedDialog(context)
          }
        }
      }
    }
  }

  fun init(pk: String, scheduleId: Int, navController: NavController) {
    packageName = pk
    this.scheduleId = scheduleId
    this.navController = navController
    checkScheduleId()
  }

  private fun checkScheduleId() {
    try {
      if (scheduleId != 0) {
        viewModelScope.launch {
          val existingSchedule = scheduleRepository.getById(scheduleId.toLong())
          if (existingSchedule != null) {
            val dateTime = LocalDateTime.ofInstant(
              Instant.ofEpochSecond(existingSchedule.scheduledTime),
              ZoneOffset.UTC
            )
            selectedDate.value = dateTime.toLocalDate()
            selectedTime.value = dateTime.toLocalTime()
          }
        }
      }
    } catch (e: Exception) {
      Log.e(TAG, e.message ?: e.toString())
    }
  }

  private fun showSucceedDialog(context: Context) {
    val packageManager = context.packageManager
    val appName = packageManager.getApplicationLabel(
      packageManager.getApplicationInfo(packageName, 0)
    )
    val builder = AlertDialog.Builder(context)
    builder.setTitle(if (scheduleId == 0) "New Schedule Created" else "Schedule Updated")
    builder.setMessage(
      "$appName will launch at\n${
        getSelectedDateTime()?.format(
          DateTimeFormatter.ofPattern(
            "hh:mm a - dd MMM yyyy"
          )
        )
      }"
    )
    builder.setPositiveButton("Got IT") { dialog, _ ->
      dialog.dismiss()
      navController.popBackStack(AppRoutes.Schedules.route, false)
    }
    builder.setCancelable(false)
    builder.show()
  }

  private fun isSelectedDateTimeValid(context: Context): Boolean {
    var isValid = false
    val selectedDateTime = getSelectedDateTime()
    if (selectedDateTime != null) {
      val currentDateTime = LocalDateTime.now()
      if (selectedDateTime.isAfter(currentDateTime)) {
        isValid = true
      }
    }
    /// show error
    if (!isValid) {
      val builder = AlertDialog.Builder(context)
      builder.setTitle("Invalid DateTime")
      builder.setMessage("Please select a valid date-time")
      builder.setPositiveButton("Try Again") { dialog, _ ->
        dialog.dismiss()
      }
      builder.show()
    }
    return isValid
  }

  private fun getSelectedDateTime(): LocalDateTime? {
    if (selectedDate.value != null && selectedTime.value != null) {
      val y = selectedDate.value?.year
      val m = selectedDate.value?.month
      val d = selectedDate.value?.dayOfMonth
      val hh = selectedTime.value?.hour
      val mm = selectedTime.value?.minute
      if (y != null && m != null && d != null && hh != null && mm != null) {
        return LocalDateTime.of(y, m, d, hh, mm)
      }
    }
    return null
  }

  private fun getSelectedDateTimeInMillis(): Long {
    val calendar = Calendar.getInstance()
    val y = selectedDate.value?.year ?: 0
    val m = selectedDate.value?.month?.value ?: 0
    val d = selectedDate.value?.dayOfMonth ?: 0
    val hh = selectedTime.value?.hour ?: 0
    val mm = selectedTime.value?.minute ?: 0
    calendar.set(y, m - 1, d, hh, mm)
    return calendar.timeInMillis
  }
}