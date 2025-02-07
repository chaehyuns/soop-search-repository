package com.soop.repository.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.soop.repository.domain.repository.GithubRepository
import com.soop.repository.presentation.main.model.RepositoryItemUiModel.Companion.toUiModel
import com.soop.repository.presentation.main.model.RepositoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RepositoryUiState>(RepositoryUiState.Initial)
    val uiState: StateFlow<RepositoryUiState> = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow() // 검색어 상태 유지

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun searchRepositories(query: String) {
        if (query.isBlank()) {
            _uiState.value = RepositoryUiState.Initial
            return
        }

        _uiState.value = RepositoryUiState.Loading

        viewModelScope.launch {
            repository.searchRepositories(query)
                .map { pagingData -> pagingData.map { it.toUiModel() } }
                .cachedIn(viewModelScope)
                .catch { exception ->
                    _uiState.value = RepositoryUiState.Error(exception.localizedMessage)
                }
                .collect { data ->
                    _uiState.value = RepositoryUiState.Success(flowOf(data))
                }
        }
    }
}
