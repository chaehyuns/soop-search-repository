package com.soop.repository.data.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soop.repository.data.dto.repositoryitems.RepositorysResponse
import com.soop.repository.data.mapper.toDomain
import com.soop.repository.data.network.GithubApiService
import com.soop.repository.domain.model.RepositoryItem

class RepositoryPagingSource(
    private val api: GithubApiService,
    private val query: String
) : PagingSource<Int, RepositoryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
        val page = params.key ?: DEFAULT_PAGE
        return try {
            val response =
                api.searchRepositories(query = query, page = page, perPage = params.loadSize)
            if (response.isSuccessful) {
                val repoResponse = response.body() ?: EMPTY_RESPONSE
                val items = repoResponse.items.map { it.toDomain() }
                val nextKey = parseNextPage(response.headers()[LINK_HEADER])
                LoadResult.Page(
                    data = items,
                    prevKey = if (page == DEFAULT_PAGE) null else page - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("HTTP error ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun parseNextPage(linkHeader: String?): Int? {
        if (linkHeader == null) return null
        val parts = linkHeader.split(LINK_DELIMITER)
        parts.forEach { part ->
            if (part.contains(LINK_REL_NEXT)) {
                val regex = "<([^>]+)>".toRegex()
                regex.find(part)?.groups?.get(1)?.value?.let { url ->
                    val uri = Uri.parse(url)
                    val nextPageStr = uri.getQueryParameter(PAGE_QUERY_PARAM)
                    return nextPageStr?.toIntOrNull()
                }
            }
        }
        return null
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = 1
        private const val LINK_HEADER = "link"
        private const val LINK_REL_NEXT = "rel=\"next\""
        private const val PAGE_QUERY_PARAM = "page"
        private const val LINK_DELIMITER = ","
        private val EMPTY_RESPONSE = RepositorysResponse(
            totalCount = 0,
            incompleteResults = false,
            items = emptyList()
        )
    }
}
