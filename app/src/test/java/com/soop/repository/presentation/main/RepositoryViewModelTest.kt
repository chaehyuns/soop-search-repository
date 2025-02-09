package com.soop.repository.presentation.main

import com.soop.repository.domain.repository.GithubRepository
import com.soop.repository.presentation.main.model.RepositoryUiState
import com.soop.repository.repository.FakeGithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoryViewModelTest {
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var fakeRepository: GithubRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeGithubRepository()
        viewModel = RepositoryViewModel(fakeRepository)
    }

    @Test
    fun `updateQuery가 정상적으로 query 값을 변경한다`() = runTest {
        viewModel.updateQuery("Kotlin")
        assertThat(viewModel.query.first()).isEqualTo("Kotlin")
    }

    @Test
    fun `초기 상태는 Initial이다`() = runTest {
        val initialState = viewModel.uiState.first()
        assertThat(initialState).isInstanceOf(RepositoryUiState.Initial::class.java)
    }

    @Test
    fun `검색어가 공백이면 Initial 상태로 유지되어야 한다`() = runTest {
        viewModel.searchRepositories("")
        val result = viewModel.uiState.first()
        assertThat(result).isInstanceOf(RepositoryUiState.Initial::class.java)
    }

    @Test
    fun `정상적으로 검색 시 uiState가 Loading으로 변경된 후 Success로 변경되어야 한다`() = runTest {
        viewModel.searchRepositories("Android")

        assertThat(viewModel.uiState.first()).isInstanceOf(RepositoryUiState.Loading::class.java)

        advanceUntilIdle()

        val result = viewModel.uiState.first()
        assertThat(result).isInstanceOf(RepositoryUiState.Success::class.java)
    }
}
