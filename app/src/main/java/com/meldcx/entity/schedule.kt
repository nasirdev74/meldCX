package com.meldcx.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class Schedule(
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0,
  val packageName: String,
  val scheduledTime: Long,
  var intentRequestCode: Int = 0,
  var isExecuted: Boolean = false
)
