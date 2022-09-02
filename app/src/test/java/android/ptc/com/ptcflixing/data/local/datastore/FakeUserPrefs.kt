package android.ptc.com.ptcflixing.data.local.datastore

class FakeUserPrefs {
    companion object{
         val fakeCurrencyConfigDataStore = CurrencyConfigDataStore(
            currencySymbol = "KE",
            decimals =  1,
            decimalsSep = ".",
            iso = "Ksh",
            position = 1,
            thousandsSep = "-"
        )

    }
}