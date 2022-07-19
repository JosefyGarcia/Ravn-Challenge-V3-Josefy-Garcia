package com.example.mystarwarsapplication.di

import com.example.mystarwarsapplication.data.repository.PeopleRepository
import com.example.mystarwarsapplication.data.repository.PeopleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class GeneralViewModel {
    @Binds
    @ViewModelScoped
    abstract fun bindingRepository(repository: PeopleRepositoryImpl): PeopleRepository
}