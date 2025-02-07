package com.soop.repository.di.module

import com.soop.repository.data.repository.DefaultGithubRepository
import com.soop.repository.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGithubRepository(
        imageRepositoryImpl: DefaultGithubRepository
    ): GithubRepository
}
