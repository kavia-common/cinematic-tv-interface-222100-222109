package org.example.app.data

import org.example.app.R

/**
 * PUBLIC_INTERFACE
 * A simple in-memory repository that exposes featured categories and items for search/details.
 * This avoids any network calls and uses local placeholder resources.
 */
object SampleRepository {

    private val categoriesInternal: List<Category> by lazy {
        val action = listOf(
            MediaItem(
                id = "a1",
                title = "Eclipse Protocol",
                overview = "A covert agent must stop a rogue satellite before it blinds the world.",
                category = "Action",
                posterResId = R.drawable.placeholder_poster,
                backdropResId = R.drawable.placeholder_backdrop,
                year = 2023,
                rating = 4.2
            ),
            MediaItem(
                id = "a2",
                title = "Crimson Horizon",
                overview = "A rescue team ventures beyond the crimson clouds to save a lost pilot.",
                category = "Action",
                posterResId = R.drawable.placeholder_poster,
                backdropResId = R.drawable.placeholder_backdrop,
                year = 2022,
                rating = 4.0
            ),
        )

        val drama = listOf(
            MediaItem(
                id = "d1",
                title = "Quiet Roads",
                overview = "A heartfelt journey through grief, hope, and new beginnings.",
                category = "Drama",
                posterResId = R.drawable.placeholder_poster,
                backdropResId = R.drawable.placeholder_backdrop,
                year = 2024,
                rating = 4.7
            ),
            MediaItem(
                id = "d2",
                title = "Letters to the Sea",
                overview = "A coastal town reconnects through messages found in drifting bottles.",
                category = "Drama",
                posterResId = R.drawable.placeholder_poster,
                backdropResId = R.drawable.placeholder_backdrop,
                year = 2021,
                rating = 4.3
            ),
        )

        val scifi = listOf(
            MediaItem(
                id = "s1",
                title = "Starlit Echoes",
                overview = "Two explorers chase a repeating signal from the edge of known space.",
                category = "Sci‑Fi",
                posterResId = R.drawable.placeholder_poster,
                backdropResId = R.drawable.placeholder_backdrop,
                year = 2020,
                rating = 4.1
            ),
            MediaItem(
                id = "s2",
                title = "Arcadia-9",
                overview = "On a terraformed world, a mystery threatens the colony’s fragile peace.",
                category = "Sci‑Fi",
                posterResId = R.drawable.placeholder_poster,
                backdropResId = R.drawable.placeholder_backdrop,
                year = 2024,
                rating = 4.6
            ),
        )

        listOf(
            Category("Action", action),
            Category("Drama", drama),
            Category("Sci‑Fi", scifi),
        )
    }

    /**
     * PUBLIC_INTERFACE
     * Returns all categories with their items.
     */
    fun categories(): List<Category> = categoriesInternal

    /**
     * PUBLIC_INTERFACE
     * Find an item by id.
     */
    fun byId(id: String): MediaItem? = categoriesInternal.flatMap { it.items }.firstOrNull { it.id == id }

    /**
     * PUBLIC_INTERFACE
     * Filter items by query across title and overview (case-insensitive).
     */
    fun search(query: String): List<MediaItem> {
        if (query.isBlank()) return emptyList()
        val q = query.trim().lowercase()
        return categoriesInternal.flatMap { it.items }
            .filter { it.title.lowercase().contains(q) || it.overview.lowercase().contains(q) }
    }
}
