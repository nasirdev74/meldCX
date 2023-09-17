package com.meldcx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meldcx.dao.ScheduleDao
import com.meldcx.entity.Schedule

@Database(entities = [Schedule::class], version = 1)
abstract class ScheduleDatabase : RoomDatabase() {
  abstract fun scheduleDao(): ScheduleDao
}
