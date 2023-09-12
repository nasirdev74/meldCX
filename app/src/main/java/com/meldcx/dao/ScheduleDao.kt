package com.meldcx.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meldcx.entity.Schedule
import kotlinx.coroutines.flow.*

@Dao
interface ScheduleDao {

  @Insert
  suspend fun insert(schedule: Schedule)

  @Update
  suspend fun update(schedule: Schedule)

  @Query("SELECT * FROM schedule")
  suspend fun getAll(): List<Schedule>

  @Query("SELECT * FROM schedule ORDER BY scheduledTime ASC")
  fun getAllSchedulesFlow(): Flow<List<Schedule>>

  @Query("SELECT * FROM schedule WHERE id = :id")
  suspend fun getById(id: Long): Schedule?

  @Query("SELECT * FROM schedule WHERE packageName = :packageName AND scheduledTime = :scheduledTime ORDER BY id ASC")
  fun getSchedulesByPackageAndTime(packageName: String, scheduledTime: Long): List<Schedule>

  @Query("DELETE FROM schedule WHERE id = :scheduleId")
  suspend fun delete(scheduleId: Long)

  @Query("SELECT * FROM schedule WHERE isExecuted = 0 ORDER BY scheduledTime ASC")
  suspend fun getScheduledSchedules(): List<Schedule>

  @Query("SELECT * FROM schedule WHERE isExecuted = 1 ORDER BY scheduledTime DESC")
  suspend fun getExecutedSchedules(): List<Schedule>
}
