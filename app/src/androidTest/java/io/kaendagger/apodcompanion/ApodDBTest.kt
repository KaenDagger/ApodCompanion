package io.kaendagger.apodcompanion

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.InstrumentationRegistry
import io.kaendagger.apodcompanion.data.database.APODDao
import io.kaendagger.apodcompanion.data.database.APODDatabase
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.coroutines.CoroutineContext

@RunWith(AndroidJUnit4::class)
class ApodDBTest:CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    private lateinit var apodDao: APODDao

    @Before
    fun setup() {
        val apodDb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            APODDatabase::class.java
        ).build()
        apodDao = apodDb.apodDao()
    }

    @After
    fun tearDown(){
        coroutineContext.cancelChildren()
    }

    @Test
    fun insertApodTest(){
        runBlocking{
            launch(Dispatchers.IO) {
                val apod = DummyApodFactory.makeDummyApodOffline()
                apodDao.insertAPODOffline(apod)
                val list = apodDao.getPastAPODs()
                assert(list.isNotEmpty())
            }
        }
    }

}