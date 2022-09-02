package android.ptc.com.ptcflixing.di

import android.ptc.com.ptcflixing.domain.repository.UserPrefrencesRepository
import android.ptc.com.ptcflixing.domain.repository.ProductsRepository
import android.ptc.com.ptcflixing.domain.use_case.GetConfigurations
import android.ptc.com.ptcflixing.domain.use_case.GetProductDetail
import android.ptc.com.ptcflixing.domain.use_case.GetProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetProducts(
        productsRepositoryRepo: ProductsRepository
    ): GetProducts = GetProducts(productsRepositoryRepo)

    @Provides
    @Singleton
    fun provideGetProductDetao(
        productsRepositoryRepo: ProductsRepository
    ): GetProductDetail = GetProductDetail(productsRepositoryRepo)


    @Provides
    @Singleton
    fun provideGetConfigurations(
        configRepo: UserPrefrencesRepository
    ): GetConfigurations = GetConfigurations(configRepo)
}