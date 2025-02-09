package com.soop.repository.data.network

import com.soop.repository.data.dto.profile.GithubUserResponse
import com.soop.repository.data.dto.profile.UserRepositoryResponse
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
    ): Response<RepositoryDetailResponse>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<GithubUserResponse>

    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String
    ): Response<List<UserRepositoryResponse>>
}
