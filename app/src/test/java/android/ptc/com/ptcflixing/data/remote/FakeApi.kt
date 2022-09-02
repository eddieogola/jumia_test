package android.ptc.com.ptcflixing.data.remote

import android.ptc.com.ptcflixing.data.remote.dto.ConfigurationDto
import android.ptc.com.ptcflixing.data.remote.dto.ConfigurationDto.*
import android.ptc.com.ptcflixing.data.remote.dto.ProductDetailDto
import android.ptc.com.ptcflixing.data.remote.dto.ProductDto
import retrofit2.Response

class FakeApi : JumiaTestApi {

    /**
     * ConfigurationDTO
     */
    private val languageConfigurationDto1 = LanguageConfigurationDto(
        code = "SWA",
        default = true,
        name = "Swahili"
    )
    private val languageConfigurationDto2 = LanguageConfigurationDto(
        code = "SHE",
        default = true,
        name = "Sheng'"
    )

    private val currencyConfigurationDto = CurrencyConfigurationDto(
        currency_symbol = "Ksh",
        decimals = 3,
        decimals_sep = ".",
        iso = "KSH",
        position = 2,
        thousands_sep = "."
    )

    private val remoteSessionConfiguration = RemoteSessionConfiguration(
        YII_CSRF_TOKEN = "JGISHGO",
        expire = 3,
        id = "2"
    )

    private val remoteSupportConfiguration = RemoteSupportConfiguration(
        call_to_order_enabled = true,
        cs_email = "three@ggg",
        phone_number = "12345"
    )
    private val remoteMetadataConfiguration = RemoteMetadataConfiguration(
        currency = currencyConfigurationDto,
        languages = listOf(languageConfigurationDto1, languageConfigurationDto2),
        support = remoteSupportConfiguration
    )
    private val configurationDto =
        ConfigurationDto(
            metadata = remoteMetadataConfiguration,
            session = remoteSessionConfiguration,
            success = true
        )

    /**
     * ProductDTO
     */

    private val result1 = ProductDto.Result(
        brand = "brand1",
        image = "image",
        max_saving_percentage = 30,
        name = "name",
        price = 5,
        rating_average = 4,
        sku = "1",
        special_price = 3
    )

    private val result2 = ProductDto.Result(
        brand = "brand2",
        image = "image2",
        max_saving_percentage = 10,
        name = "name2",
        price = 5,
        rating_average = 4,
        sku = "2",
        special_price = 3
    )
    private val metadata1 = ProductDto.Metadata(
        results = listOf(result1, result2),
        sort = "sort",
        title = "title",
        total_products = 5
    )
    private val productDto = ProductDto(
        metadata = metadata1,
        success = true
    )

    /**
     * ProductDetailDTO
     */


    private val rating = ProductDetailDto.Rating(
        average = 5,
        ratings_total = 20
    )

    private val sellerEntity = ProductDetailDto.SellerEntity(
        delivery_time = "2400hrs",
        id = 9,
        name = "sdfd"
    )

    private val summary = ProductDetailDto.Summary(
        description = "ddfjpafjd",
        short_description = "dfj"
    )


    private val metadata = ProductDetailDto.Metadata(
        brand = "adf",
        image_list = listOf("image1", "image2"),
        max_saving_percentage = 15,
        name = "name",
        price = 48482,
        rating = rating,
        seller_entity = sellerEntity,
        sku = "2",
        special_price = 5157,
        summary = summary
    )

    private val productDetailDto = ProductDetailDto(
        metadata = metadata,
        success = true
    )

    override suspend fun getConfigurations(): Response<ConfigurationDto> {
        return Response.success(configurationDto)
    }


    override suspend fun getResultList(query: String?, pageNo: Int?): Response<ProductDto> {
        return Response.success(productDto)
    }

    override suspend fun getDetailItem(itemId: String): ProductDetailDto {
        return productDetailDto
    }
}