package com.example.mystarwarsapplication.di

import com.example.mystarwarsapplication.network.StarWarsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeneralRepository {

    @Singleton
    @Provides
    fun api() = StarWarsApi()
}