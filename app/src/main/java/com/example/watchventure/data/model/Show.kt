package com.example.watchventure.data.model

import com.google.gson.annotations.SerializedName

/**
 * DTO (Data Transfer Object) sesuai bentuk JSON dari TVmaze API.
 * Semua field dibuat nullable karena API tidak menjamin semua data tersedia (null safety).
 */
data class ShowDto(
    val id: Int,
    val name: String?,
    val genres: List<String>?,
    val premiered: String?,
    val rating: RatingDto?,
    val summary: String?
)

data class RatingDto(
    val average: Double?
)

/** Respons dari endpoint /search/shows?q= : setiap item membungkus satu show. */
data class SearchResultDto(
    val score: Double?,
    @SerializedName("show") val show: ShowDto
)

/**
 * Model yang dipakai oleh UI. Sudah "bersih": tidak ada nullable yang membingungkan UI.
 */
data class Show(
    val id: Int,
    val title: String,
    val year: String,
    val rating: String,
    val genres: List<String>,
    val summary: String
)

/** Mapper DTO -> Model UI. */
fun ShowDto.toShow(): Show = Show(
    id = id,
    title = name ?: "Tanpa judul",
    year = premiered?.take(4)?.takeIf { it.isNotBlank() } ?: "-",
    rating = rating?.average?.toString() ?: "-",
    genres = genres.orEmpty(),
    summary = summary
        ?.replace(Regex("<[^>]*>"), "") // hapus tag HTML dari API
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?: "Ringkasan tidak tersedia."
)
