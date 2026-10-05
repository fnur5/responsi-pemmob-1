package com.example.watchventure.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchventure.data.model.Show
import com.example.watchventure.data.repository.ShowRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Seluruh state Home dalam satu objek: query, hasil, loading, error. */
data class HomeUiState(
    val query: String = "",
    val shows: List<Show> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel : ViewModel() {

    private val repository = ShowRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        search(delayMillis = 0) // muat daftar awal
    }

    /** Dipanggil setiap kali teks di search bar berubah. */
    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
        search(delayMillis = 500) // debounce 500 ms agar tidak spam API
    }

    fun retry() = search(delayMillis = 0)

    private fun search(delayMillis: Long) {
        searchJob?.cancel() // batalkan pencarian sebelumnya
        searchJob = viewModelScope.launch {
            delay(delayMillis)
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.searchShows(_uiState.value.query)
                .onSuccess { result ->
                    _uiState.update { it.copy(shows = result, isLoading = false) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            shows = emptyList(),
                            isLoading = false,
                            error = e.message ?: "Terjadi kesalahan jaringan"
                        )
                    }
                }
        }
    }
}
