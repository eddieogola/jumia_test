package android.ptc.com.ptcflixing.data.local.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider

class RoomUITest {

    companion object {
        private val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(context, JumiaTestDatabase::class.java)
            .allowMainThreadQueries().build()
    }
}