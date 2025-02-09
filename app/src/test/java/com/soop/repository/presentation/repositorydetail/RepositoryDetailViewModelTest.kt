package com.soop.repository.presentation.repositorydetail

import com.soop.repository.TEST_ERROR_MESSAGE
import com.soop.repository.domain.repository.GithubRepository
import com.soop.repository.presentation.repositorydetail.model.RepositoryDetailUiState
import com.soop.repository.repository.ErrorGithubRepository
import com.soop.repository.repository.FakeGithubRepository
import com.soop.repository.repositoryDetail1
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
class RepositoryDetailViewModelTest {
    private lateinit var viewModel: RepositoryDetailViewModel
    private lateinit var fakeRepository: GithubRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeGithubRepository()
        viewModel = RepositoryDetailViewModel(fakeRepository)
    }

    @Test
    fun `초기 상태는 Loading이어야 한다`() = runTest {
        val initialState = viewModel.uiState.first()
        assertThat(initialState).isInstanceOf(RepositoryDetailUiState.Loading::class.java)
    }

    @Test
    fun `fetchRepositoryDetail 정상 호출 시 Success 상태로 변경되어야 한다`() = runTest {
        // When
        viewModel.fetchRepositoryDetail(owner = "chaehyun", repo = "soop-repo")
        advanceUntilIdle()

        // Then
        val result = viewModel.uiState.first()
        assertThat(result).isInstanceOf(RepositoryDetailUiState.Success::class.java)
    }

    @Test
    fun `fetchRepositoryDetail 정상 호출 시 올바른 데이터를 불러온다`() = runTest {
        // When
        viewModel.fetchRepositoryDetail(
            owner = repositoryDetail1.ownerLoginName,
            repo = repositoryDetail1.repositoryName
        )
        advanceUntilIdle()

        // Then
        val result = viewModel.uiState.first()
        val data = (result as? RepositoryDetailUiState.Success)?.data
        assertThat(data?.ownerLoginName).isEqualTo(repositoryDetail1.ownerLoginName)
        assertThat(data?.repositoryName).isEqualTo(repositoryDetail1.repositoryName)
    }

    @Test
    fun `API 호출 실패 시 Error 상태로 변경되어야 한다`() = runTest {
        // Given
        val errorRepository = ErrorGithubRepository(errorMessage = TEST_ERROR_MESSAGE)
        viewModel = RepositoryDetailViewModel(errorRepository)

        // When
        viewModel.fetchRepositoryDetail(owner = "chaehyun", repo = "soop-repo")
        advanceUntilIdle()

        // Then
        val result = viewModel.uiState.first()
        assertThat(result).isInstanceOf(RepositoryDetailUiState.Error::class.java)

        val errorMessage = (result as? RepositoryDetailUiState.Error)?.message ?: "Unknown error"
        assertThat(errorMessage).isEqualTo(TEST_ERROR_MESSAGE)
    }
}
