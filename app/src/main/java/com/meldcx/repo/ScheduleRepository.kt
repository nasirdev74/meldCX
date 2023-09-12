package com.meldcx.repo

import com.meldcx.dao.ScheduleDao
import com.meldcx.entity.Schedule
import kotlinx.coroutines.flow.*
import java.time.*

class ScheduleRepository(private val scheduleDao: ScheduleDao) {
  suspend fun insert(schedule: Schedule) {
    scheduleDao.insert(schedule)
  }

  suspend fun update(schedule: Schedule) {
    scheduleDao.update(schedule)
  }

  suspend fun save(schedule: Schedule): Schedule? {
    val existingSchedule = getById(schedule.id)
    if (existingSchedule == null) {
      val requestCode = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt()
      schedule.intentRequestCode = requestCode
      insert(schedule)
    } else {
      val newSchedule = Schedule(
        id = existingSchedule.id,
        packageName = schedule.packageName,
        scheduledTime = schedule.scheduledTime,
        intentRequestCode = existingSchedule.intentRequestCode,
        isExecuted = schedule.isExecuted,
      )
      update(newSchedule)
    }
    return getSchedulesByPackageAndTime(schedule.packageName, schedule.scheduledTime).lastOrNull()
  }

  suspend fun markExecuted(id: Long): Schedule? {
    val schedule = getById(id)
    if (schedule != null) {
      schedule.isExecuted = true
      return save(schedule)
    }
    return null
  }

  suspend fun delete(schedule: Schedule) {
    scheduleDao.delete(schedule.id)
  }

  suspend fun getAll(): List<Schedule> {
    return scheduleDao.getAll()
  }

  fun getAllSchedulesFlow(): Flow<List<Schedule>> {
    return scheduleDao.getAllSchedulesFlow()
  }

  suspend fun getById(id: Long): Schedule? {
    return scheduleDao.getById(id)
  }

  private fun getSchedulesByPackageAndTime(
    packageName: String,
    scheduledTime: Long
  ): List<Schedule> {
    return scheduleDao.getSchedulesByPackageAndTime(packageName, scheduledTime)
  }

  suspend fun getScheduledSchedules(): List<Schedule> {
    return scheduleDao.getScheduledSchedules()
  }

  suspend fun getExecutedSchedules(): List<Schedule> {
    return scheduleDao.getExecutedSchedules()
  }
}
