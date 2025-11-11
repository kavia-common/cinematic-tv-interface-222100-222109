package org.example.app.data

/**
 * PUBLIC_INTERFACE
 * Represents a media item (movie/show) displayed in the app.
 */
data class MediaItem(
    val id: String,
    val title: String,
    val overview: String,
    val category: String,
    val posterResId: Int,
    val backdropResId: Int,
    val year: Int = 2024,
    val rating: Double = 4.5,
)

/**
 * PUBLIC_INTERFACE
 * Represents a category of media items.
 */
data class Category(
    val name: String,
    val items: List<MediaItem>
)
