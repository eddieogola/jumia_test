package android.ptc.com.ptcflixing.data.remote

import android.ptc.com.ptcflixing.data.remote.dto.ConfigurationDto
import android.ptc.com.ptcflixing.data.remote.dto.ProductDetailDto
import android.ptc.com.ptcflixing.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JumiaTestApi {
    companion object {
        const val BASE_URL = "http://nd7d1.mocklab.io/"
    }

    @GET("configurations/")
    suspend fun getConfigurations(): Response<ConfigurationDto>

    @GET("search/{phone}/page/{pageNo}/")
    suspend fun getResultList(
        @Path("phone")
        query: String?,
        @Path("pageNo")
        pageNo: Int?
    ): Response<ProductDto>

    @GET("product/{itemId}/")
    suspend fun getDetailItem(
        @Path("itemId")
        itemId: String
    ): ProductDetailDto

}