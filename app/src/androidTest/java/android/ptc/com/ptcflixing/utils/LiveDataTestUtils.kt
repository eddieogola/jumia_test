package android.ptc.com.ptcflixing.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class LiveDataTestUtils {
    companion object{
        /**
         * Test Live Data
         */
        fun<T> LiveData<T>.getOrAwaitValue(): T{
            var data: T? = null
            val latch = CountDownLatch(1)

            val observer = object : Observer<T>{
                override fun onChanged(t: T) {
                    data = t
                    this@getOrAwaitValue.removeObserver(this)
                    latch.countDown()
                }
            }

            this.observeForever(observer)

            try {
                if(!latch.await(2, TimeUnit.SECONDS)){
                    throw TimeoutException("Live data never gets its value")
                }
            }finally {
                this.removeObserver(observer)
            }

            return data as T
        }
    }
}