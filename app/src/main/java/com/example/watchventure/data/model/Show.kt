package com.example.watchventure.data.model

import com.google.gson.annotations.SerializedName

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

data class SearchResultDto(
    val score: Double?,
    @SerializedName("show") val show: ShowDto
)

data class Show(
    val id: Int,
    val title: String,
    val year: String,
    val rating: String,
    val genres: List<String>,
    val summary: String
)

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
