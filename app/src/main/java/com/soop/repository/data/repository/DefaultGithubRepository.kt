package com.soop.repository.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.soop.repository.data.network.GithubApiService
import com.soop.repository.data.paging.RepositoryPagingSource
import com.soop.repository.domain.model.RepositoryItem
import com.soop.repository.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultGithubRepository @Inject constructor(
    private val api: GithubApiService
) : GithubRepository {
    override fun searchRepositories(query: String): Flow<PagingData<RepositoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { RepositoryPagingSource(api, query) }
        ).flow
    }
}
