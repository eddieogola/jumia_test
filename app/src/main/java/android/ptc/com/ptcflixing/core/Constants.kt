package android.ptc.com.ptcflixing.core

class Constants {
    companion object{
        /**
         * Pagination
         */
        const val STARTING_PAGE_INDEX = 1
        const val ITEMS_PER_PAGE = 10

        /**
         * Errors
         */
        const val INTERNET_CONNECTION_ERROR = "Please check your internet connection"
        const val HTTP_ERROR = "Couldn't reach server"
        const val ERROR_404 = 404
        const val ERROR_200 = 200
        const val ERROR_200_MESSAGE = "Product not found!"
        const val ERROR_404_MESSAGE = "Oops! Something went wrong"

        /**
         * Search Page
         */
        const val EMPTY_SEARCH_MESSAGE = "Search something"
        const val DEFAULT_SEARCH_QUERY = "phone"
    }
}