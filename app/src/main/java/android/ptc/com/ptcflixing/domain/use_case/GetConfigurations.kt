package android.ptc.com.ptcflixing.domain.use_case

import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig
import android.ptc.com.ptcflixing.domain.repository.UserPrefrencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConfigurations @Inject constructor(
    private val configRepo: UserPrefrencesRepository
) {
    suspend operator fun invoke(): Flow<NetworkLoading<CurrencyConfig>> = configRepo.getCurrencyConfigurations()
}