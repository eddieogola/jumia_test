package android.ptc.com.ptcflixing.domain.repository

interface ItemDetail {

    suspend fun getItemDetail(itemId: Int)
}