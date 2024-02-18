package com.example.sleeptracker.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDatabaseDao {
    @Insert
    fun insertOne(night: SleepNight) : Long

    @Update
    fun updateOne(night: SleepNight)

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")
    fun getOne(key: Long) : SleepNight

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")
    fun getOneById(key: Long) : LiveData<SleepNight>

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAll(): LiveData<List<SleepNight>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?
}