package com.meldcx.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meldcx.dao.ScheduleDao
import com.meldcx.entity.Schedule

@Database(entities = [Schedule::class], version = 1)
abstract class ScheduleDatabase : RoomDatabase() {
  abstract fun scheduleDao(): ScheduleDao

  companion object {
    @Volatile
    private var INSTANCE: ScheduleDatabase? = null

    fun getInstance(context: Context): ScheduleDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          ScheduleDatabase::class.java,
          "schedule_database"
        ).build()
        INSTANCE = instance
        instance
      }
    }
  }
}
