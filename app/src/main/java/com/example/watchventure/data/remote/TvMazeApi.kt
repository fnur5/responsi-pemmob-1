package com.example.watchventure.data.remote

import com.example.watchventure.data.model.SearchResultDto
import com.example.watchventure.data.model.ShowDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** Definisi endpoint TVmaze API (https://www.tvmaze.com/api). */
interface TvMazeApi {

    /** Pencarian berdasarkan judul: GET https://api.tvmaze.com/search/shows?q=... */
    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<SearchResultDto>

    /** Daftar show default untuk Home saat belum ada pencarian. */
    @GET("shows")
    suspend fun getShows(@Query("page") page: Int = 0): List<ShowDto>

    /** Detail satu show. */
    @GET("shows/{id}")
    suspend fun getShowDetail(@Path("id") id: Int): ShowDto
}

/** Singleton Retrofit. */
object RetrofitInstance {
    private const val BASE_URL = "https://api.tvmaze.com/"

    val api: TvMazeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvMazeApi::class.java)
    }
}
