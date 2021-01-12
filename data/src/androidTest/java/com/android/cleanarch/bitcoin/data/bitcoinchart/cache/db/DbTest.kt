package com.android.cleanarch.bitcoin.data.bitcoinchart.cache.db

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
abstract class DbTest {

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    private lateinit var _db: BitcoinChartDb
    val db: BitcoinChartDb
        get() = _db

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed

        _db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BitcoinChartDb::class.java
        ).allowMainThreadQueries().build()

    }

    @After
    fun closeDB() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }
}
