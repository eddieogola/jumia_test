package android.ptc.com.ptcflixing.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

const val PREFERENCE_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferenceKeys {
        val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
        val DECIMALS = intPreferencesKey("decimals")
        val DECIMALS_SEP = stringPreferencesKey("decimals_sep")
        val ISO = stringPreferencesKey("iso")
        val POSITION = intPreferencesKey("position")
        val THOUSANDS_SEP = stringPreferencesKey("thousands_sep")
    }

    suspend fun currencySymbol(currencySymbol: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.CURRENCY_SYMBOL] = currencySymbol
        }
    }

    suspend fun currencyDecimals(decimals: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.DECIMALS] = decimals
        }
    }

    suspend fun currencyDecimalsSep(decimalsSep: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.DECIMALS_SEP] = decimalsSep
        }
    }

    suspend fun currencyISO(iso: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.ISO] = iso
        }
    }

    suspend fun currencyPosition(position: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.POSITION] = position
        }
    }

    suspend fun currrencyThousandsSep(thousandsSep: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.THOUSANDS_SEP] = thousandsSep
        }
    }


    val readCurrencyConfigDataStore = context.dataStore.data
        .catch { exception ->
            if (exception is IOException)
                exception.printStackTrace()
            emit(emptyPreferences())
        }
        .map { preferences ->
            val currencySymbol = preferences[PreferenceKeys.CURRENCY_SYMBOL] ?: ""
            val decimals = preferences[PreferenceKeys.DECIMALS] ?: 0
            val decimalsSep = preferences[PreferenceKeys.DECIMALS_SEP] ?: ""
            val iso = preferences[PreferenceKeys.ISO] ?: ""
            val position = preferences[PreferenceKeys.POSITION] ?: 0
            val thousandsSep = preferences[PreferenceKeys.THOUSANDS_SEP] ?: ""

            CurrencyConfigDataStore(
                currencySymbol = currencySymbol,
                decimals = decimals,
                decimalsSep = decimalsSep,
                iso = iso,
                position = position,
                thousandsSep = thousandsSep
            )
        }

}