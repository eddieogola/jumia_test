package android.ptc.com.ptcflixing.di

import android.app.Application
import android.ptc.com.ptcflixing.data.local.datastore.PreferencesManager
import android.ptc.com.ptcflixing.data.local.room.JumiaTestDatabase
import android.ptc.com.ptcflixing.data.remote.JumiaTestApi
import android.ptc.com.ptcflixing.data.repository.UserPrefrencesRepositoryImpl
import android.ptc.com.ptcflixing.domain.repository.UserPrefrencesRepository
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        context: Application
    ) = Room.databaseBuilder(context, JumiaTestDatabase::class.java, "jumia_test_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providePreferenceManager(
        context: Application
    ): PreferencesManager = PreferencesManager(context)

    @Provides
    @Singleton
    fun provideConfiguration(
        api: JumiaTestApi,
        pref: PreferencesManager
    ): UserPrefrencesRepository = UserPrefrencesRepositoryImpl(api)

}