package android.ptc.com.ptcflixing.domain.repository

import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.data.local.datastore.CurrencyConfigDataStore
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig
import kotlinx.coroutines.flow.Flow

interface UserPrefrencesRepository {
    suspend fun getCurrencyConfigurations(): Flow<NetworkLoading<CurrencyConfig>>
}