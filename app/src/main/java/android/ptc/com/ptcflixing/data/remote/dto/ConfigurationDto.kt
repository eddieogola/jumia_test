package android.ptc.com.ptcflixing.data.remote.dto

import android.ptc.com.ptcflixing.data.local.datastore.CurrencyConfigDataStore
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig


data class ConfigurationDto(
    val metadata: RemoteMetadataConfiguration,
    val session: RemoteSessionConfiguration,
    val success: Boolean
) {
    data class RemoteMetadataConfiguration(
        val currency: CurrencyConfigurationDto,
        val languages: List<LanguageConfigurationDto>,
        val support: RemoteSupportConfiguration
    )

    data class LanguageConfigurationDto(
        val code: String,
        val default: Boolean,
        val name: String
    )

    data class CurrencyConfigurationDto(
        val currency_symbol: String,
        val decimals: Int,
        val decimals_sep: String,
        val iso: String,
        val position: Int,
        val thousands_sep: String
    ) {
        fun toCurrencyConfigDatastore(): CurrencyConfigDataStore {
            return CurrencyConfigDataStore(
                currencySymbol = (currency_symbol.toByteArray(Charsets.UTF_8).contentToString()),
                decimals = decimals,
                decimalsSep = decimals_sep,
                iso = iso,
                position = position,
                thousandsSep = thousands_sep
            )
        }

        fun toCurrencyConfig(): CurrencyConfig {
            return CurrencyConfig(
                currencySymbol = currency_symbol,
                decimals = decimals,
                decimalsSep = decimals_sep,
                iso = iso,
                position = position,
                thousandsSep = thousands_sep
            )
        }


    }

    data class RemoteSessionConfiguration(
        val YII_CSRF_TOKEN: String,
        val expire: Any,
        val id: String
    )

    data class RemoteSupportConfiguration(
        val call_to_order_enabled: Boolean,
        val cs_email: String,
        val phone_number: String
    )
}

