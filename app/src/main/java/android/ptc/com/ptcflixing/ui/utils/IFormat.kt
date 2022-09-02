package android.ptc.com.ptcflixing.ui.utils

import android.ptc.com.ptcflixing.domain.model.CurrencyConfig

interface IFormat {

    fun intToStringPercentage(int: Int): String

    fun currencyFormat(int: Int, config: CurrencyConfig): String
}