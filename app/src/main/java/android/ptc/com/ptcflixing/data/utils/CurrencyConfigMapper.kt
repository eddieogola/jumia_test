package android.ptc.com.ptcflixing.data.utils

import android.ptc.com.ptcflixing.core.EntityMapper
import android.ptc.com.ptcflixing.data.local.datastore.CurrencyConfigDataStore
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig

class CurrencyConfigMapper {
    companion object : EntityMapper<CurrencyConfigDataStore, CurrencyConfig> {
        override fun CurrencyConfigDataStore.mapToDomainModel(): CurrencyConfig {
            return CurrencyConfig(
                currencySymbol = currencySymbol,
                decimals = decimals,
                decimalsSep = decimalsSep,
                iso = iso,
                position = position,
                thousandsSep = thousandsSep
            )
        }

        override fun CurrencyConfig.mapToEntity(): CurrencyConfigDataStore {
            return CurrencyConfigDataStore(
                currencySymbol = currencySymbol.toByteArray(Charsets.UTF_8).contentToString(),
                decimals = decimals,
                decimalsSep = decimalsSep,
                iso = iso,
                position = position,
                thousandsSep = thousandsSep
            )
        }
    }
}