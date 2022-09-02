package android.ptc.com.ptcflixing.core

sealed class NetworkLoading<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : NetworkLoading<T>(data)
    class Loading<T>(data: T? = null) : NetworkLoading<T>(data)
    class Error<T>( data:T? = null, message: String) : NetworkLoading<T>(data, message)
}
