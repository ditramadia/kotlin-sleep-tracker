package com.example.sleeptracker

/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sleeptracker.database.SleepDatabase
import com.example.sleeptracker.database.SleepDatabaseDao
import com.example.sleeptracker.database.SleepNight
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTonight() {
        // Insert new night
        val night = SleepNight()
        sleepDao.insertOne(night)

        // Get tonight
        val tonight = sleepDao.getTonight()

        // Assertion
        assertEquals(-1, tonight?.sleepQuality)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAll() {
        // Insert new nights
        val night = SleepNight()
        val night2 = SleepNight()
        val night3 = SleepNight()
        val night4 = SleepNight()
        sleepDao.insertOne(night)
        sleepDao.insertOne(night2)
        sleepDao.insertOne(night3)
        sleepDao.insertOne(night4)

        // Get all
        val allNights = sleepDao.getAll()
        val allNightsId : MutableList<Long> = mutableListOf(
            allNights[3].nightId,
            allNights[2].nightId,
            allNights[1].nightId,
            allNights[0].nightId
        )

        // Assertion
        val expectedAllNightsId : MutableList<Long> = mutableListOf(
            1L,
            2L,
            3L,
            4L
        )
        assertEquals(expectedAllNightsId, allNightsId)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndClear() {
        // Insert new nights
        val night = SleepNight()
        val night2 = SleepNight()
        val night3 = SleepNight()
        val night4 = SleepNight()
        sleepDao.insertOne(night)
        sleepDao.insertOne(night2)
        sleepDao.insertOne(night3)
        sleepDao.insertOne(night4)

        // Clear
        sleepDao.clear()

        // Assertion
        val numberOfNights = sleepDao.getAll().size
        assertEquals(0, numberOfNights)
    }

    @Test
    @Throws(Exception::class)
    fun updateAndGetOne() {
        // Insert new night
        val night = SleepNight()
        night.nightId = sleepDao.insertOne(night)

        // Get inserted night
        val insertedNight = sleepDao.getOne(night.nightId)

        // Update modified night
        insertedNight.sleepQuality = 1
        sleepDao.updateOne(insertedNight)

        // Get modified night
        val updatedNight = sleepDao.getOne(night.nightId)

        // Assertion
        assertEquals(1, updatedNight.sleepQuality)
    }

}

