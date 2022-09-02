package android.ptc.com.ptcflixing.data.local.datastore

data class CurrencyConfigDataStore(
    val currencySymbol: String="",
    val decimals: Int = 0,
    val decimalsSep: String = "",
    val iso: String = "",
    val position: Int = 0,
    val thousandsSep: String = "",
)