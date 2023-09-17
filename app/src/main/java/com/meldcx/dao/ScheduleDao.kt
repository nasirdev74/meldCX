package com.meldcx.dao

import androidx.room.*
import com.meldcx.entity.Schedule
import kotlinx.coroutines.flow.*

@Dao
interface ScheduleDao {

  @Insert
  fun insert(schedule: Schedule)

  @Update
  fun update(schedule: Schedule)

  @Query("SELECT * FROM schedule")
  fun getAll(): List<Schedule>

  @Query("SELECT * FROM schedule ORDER BY scheduledTime ASC")
  fun getAllSchedulesFlow(): Flow<List<Schedule>>

  @Query("SELECT * FROM schedule WHERE id = :id")
  fun getById(id: Long): Schedule?

  @Query("SELECT * FROM schedule WHERE packageName = :packageName AND scheduledTime = :scheduledTime ORDER BY id ASC")
  fun getSchedulesByPackageAndTime(packageName: String, scheduledTime: Long): List<Schedule>

  @Query("DELETE FROM schedule WHERE id = :scheduleId")
  fun delete(scheduleId: Long)

  @Query("SELECT * FROM schedule WHERE isExecuted = 0 ORDER BY scheduledTime ASC")
  fun getScheduledSchedules(): List<Schedule>

  @Query("SELECT * FROM schedule WHERE isExecuted = 1 ORDER BY scheduledTime DESC")
  fun getExecutedSchedules(): List<Schedule>
}
