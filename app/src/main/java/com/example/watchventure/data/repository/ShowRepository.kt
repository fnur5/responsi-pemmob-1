package com.example.watchventure.data.repository

import com.example.watchventure.data.model.Show
import com.example.watchventure.data.model.toShow
import com.example.watchventure.data.remote.RetrofitInstance
import com.example.watchventure.data.remote.TvMazeApi

/**
 * Repository = satu-satunya pintu akses data untuk ViewModel.
 * ViewModel tidak tahu apakah data berasal dari Retrofit, database, dll.
 * Fungsi dibungkus Result agar error jaringan ditangani dengan rapi.
 */
class ShowRepository(
    private val api: TvMazeApi = RetrofitInstance.api
) {

    /** Jika query kosong -> tampilkan daftar default, selain itu -> cari dari API. */
    suspend fun searchShows(query: String): Result<List<Show>> = runCatching {
        if (query.isBlank()) {
            api.getShows(page = 0).map { it.toShow() }
        } else {
            api.searchShows(query.trim()).map { it.show.toShow() }
        }
    }

    suspend fun getShowDetail(id: Int): Result<Show> = runCatching {
        api.getShowDetail(id).toShow()
    }
}
