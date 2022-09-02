package android.ptc.com.ptcflixing.data.paging

import androidx.paging.DifferCallback
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher


class FakePaging {
    @ExperimentalCoroutinesApi
    companion object {
        suspend fun <T : Any> PagingData<T>.collectDataForTest(): List<T> {
            val differCallback = object : DifferCallback {
                override fun onChanged(position: Int, count: Int) {}

                override fun onInserted(position: Int, count: Int) {}

                override fun onRemoved(position: Int, count: Int) {}
            }

            val items = mutableListOf<T>()
            val dif = object : PagingDataDiffer<T>(differCallback, StandardTestDispatcher()) {
                override suspend fun presentNewList(
                    previousList: NullPaddedList<T>,
                    newList: NullPaddedList<T>,
                    lastAccessedIndex: Int,
                    onListPresentable: () -> Unit
                ): Int? {
                    for (index in 0 until newList.size)
                        items.add(newList.getFromStorage(index))
                    onListPresentable()
                    return null
                }
            }
            dif.collectFrom(this)
            return items
        }

    }

}