package android.ptc.com.ptcflixing.di

import android.ptc.com.ptcflixing.data.local.room.JumiaTestDatabase
import android.ptc.com.ptcflixing.data.remote.JumiaTestApi
import android.ptc.com.ptcflixing.data.remote.JumiaTestApi.Companion.BASE_URL
import android.ptc.com.ptcflixing.data.repository.ProductsRepositoryImpl
import android.ptc.com.ptcflixing.domain.repository.ProductsRepository
import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideJumiaTestApi(): JumiaTestApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(JumiaTestApi::class.java)
    }

    @Provides
    @Singleton
    fun providePagingRepo(
        api: JumiaTestApi,
        db: JumiaTestDatabase
    ): ProductsRepository = ProductsRepositoryImpl(api, db)

}