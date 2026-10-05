package com.example.watchventure.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchventure.data.model.Show
import com.example.watchventure.data.repository.ShowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** State Detail: data detail, loading, error. */
data class DetailUiState(
    val show: Show? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class DetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val repository = ShowRepository()

    // "showId" otomatis terisi dari argumen route navigasi
    private val showId: Int = checkNotNull(savedStateHandle["showId"])

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getShowDetail(showId)
                .onSuccess { show ->
                    _uiState.update { it.copy(show = show, isLoading = false) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(isLoading = false, error = e.message ?: "Gagal memuat detail")
                    }
                }
        }
    }
}
