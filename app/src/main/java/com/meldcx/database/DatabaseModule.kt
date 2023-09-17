package com.meldcx.database

import android.app.*
import androidx.room.*
import com.meldcx.dao.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideScheduleDatabase(application: Application): ScheduleDatabase {
    return Room.databaseBuilder(application, ScheduleDatabase::class.java, "schedule_database")
      .build()
  }

  @Provides
  @Singleton
  fun provideScheduleDao(scheduleDatabase: ScheduleDatabase): ScheduleDao {
    return scheduleDatabase.scheduleDao()
  }
}
