package com.soop.repository.presentation.repositorydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soop.repository.domain.repository.GithubRepository
import com.soop.repository.presentation.repositorydetail.model.RepositoryDetailUiModel.Companion.toUiModel
import com.soop.repository.presentation.repositorydetail.model.RepositoryDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RepositoryDetailUiState>(RepositoryDetailUiState.Loading)
    val uiState: StateFlow<RepositoryDetailUiState> = _uiState

    fun fetchRepositoryDetail(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                val response = repository.getRepositoryDetail(owner, repo)
                _uiState.value = RepositoryDetailUiState.Success(response.toUiModel())
            } catch (e: Exception) {
                _uiState.value = RepositoryDetailUiState.Error(e.message)
            }
        }
    }
}
