package com.soop.repository.data.network

import com.soop.repository.data.dto.repositorydetail.RepositoryDetailResponse
import com.soop.repository.data.dto.repositoryitems.RepositorysResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Response<RepositorysResponse>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryDetail(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepositoryDetailResponse
}
