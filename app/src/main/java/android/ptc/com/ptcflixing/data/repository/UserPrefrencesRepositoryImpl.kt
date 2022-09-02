package android.ptc.com.ptcflixing.data.repository

import android.ptc.com.ptcflixing.core.Constants.Companion.HTTP_ERROR
import android.ptc.com.ptcflixing.core.Constants.Companion.INTERNET_CONNECTION_ERROR
import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.data.remote.JumiaTestApi
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig
import android.ptc.com.ptcflixing.domain.repository.UserPrefrencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPrefrencesRepositoryImpl @Inject constructor(
    private val api: JumiaTestApi
) : UserPrefrencesRepository {
    override suspend fun getCurrencyConfigurations(): Flow<NetworkLoading<CurrencyConfig>> = flow {
        emit(NetworkLoading.Loading())
        try {
            api.getConfigurations().body()?.metadata?.let { configs ->
                val currencyConfig = configs.currency.toCurrencyConfig()
                emit(NetworkLoading.Success(currencyConfig))
            }

        } catch (e: IOException) {
            e.printStackTrace()
            emit(NetworkLoading.Error(null, INTERNET_CONNECTION_ERROR))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(NetworkLoading.Error(null, HTTP_ERROR))
        }

    }

}