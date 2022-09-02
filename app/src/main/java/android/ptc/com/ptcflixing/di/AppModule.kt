package android.ptc.com.ptcflixing.di

import android.app.Application
import android.ptc.com.ptcflixing.core.ConnectionLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideConnectionLiveData(context: Application): ConnectionLiveData =
        ConnectionLiveData(context)
}