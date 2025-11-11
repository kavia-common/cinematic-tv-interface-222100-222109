package org.example.app.data

import org.example.app.R

/**
 * PUBLIC_INTERFACE
 * A simple in-memory repository that exposes featured categories, sections,
 * and banner background URLs for the Home screen. Fully static, no network calls.
 */
object SampleRepository {

    // Provided remote image URLs (Unsplash or similar). Kept immutable.
    private data class UrlItem(
        val id: String,
        val title: String,
        val overview: String,
        val category: String,
        val posterUrl: String,
        val backdropUrl: String = posterUrl // fallback to posterUrl if no distinct backdrop given
    )

    // Recommended: Interstellar, Inception, Gravity, Tenet, Avatar
    private val recommendedInternal: List<UrlItem> = listOf(
        UrlItem(
            id = "rec_interstellar",
            title = "Interstellar",
            overview = "A team travels through a wormhole in space in an attempt to ensure humanity's survival.",
            category = "Recommended",
            posterUrl = "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "rec_inception",
            title = "Inception",
            overview = "A thief who steals corporate secrets through dream-sharing technology.",
            category = "Recommended",
            posterUrl = "https://images.unsplash.com/photo-1529101091764-c3526daf38fe?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "rec_gravity",
            title = "Gravity",
            overview = "Two astronauts work together to survive after an accident leaves them adrift in space.",
            category = "Recommended",
            posterUrl = "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "rec_tenet",
            title = "Tenet",
            overview = "Armed with only one word, Tenet, fighting for the survival of the world.",
            category = "Recommended",
            posterUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "rec_avatar",
            title = "Avatar",
            overview = "A paraplegic Marine dispatched to Pandora on a unique mission.",
            category = "Recommended",
            posterUrl = "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?q=80&w=600&auto=format&fit=crop"
        ),
    )

    // Top Picks: Dune, The Martian, Blade Runner, Star Wars, Arrival
    private val topPicksInternal: List<UrlItem> = listOf(
        UrlItem(
            id = "top_dune",
            title = "Dune",
            overview = "A noble family becomes embroiled in a war for control of the desert planet Arrakis.",
            category = "Top Picks",
            posterUrl = "https://images.unsplash.com/photo-1519681393784-d120267933ba?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "top_martian",
            title = "The Martian",
            overview = "An astronaut becomes stranded on Mars and must rely on his ingenuity to survive.",
            category = "Top Picks",
            posterUrl = "https://images.unsplash.com/photo-1454789548928-9efd52dc4031?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "top_blade_runner",
            title = "Blade Runner",
            overview = "A blade runner must pursue and terminate four replicants.",
            category = "Top Picks",
            posterUrl = "https://images.unsplash.com/photo-1496307042754-b4aa456c4a2d?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "top_star_wars",
            title = "Star Wars",
            overview = "Epic saga set in a distant galaxy with the Force, Jedi, and Sith.",
            category = "Top Picks",
            posterUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "top_arrival",
            title = "Arrival",
            overview = "A linguist works with the military to communicate with alien lifeforms.",
            category = "Top Picks",
            posterUrl = "https://images.unsplash.com/photo-1462332420958-a05d1e002413?q=80&w=600&auto=format&fit=crop"
        ),
    )

    // Recently Added: Joker, Oppenheimer, Barbie, Fast X, Extraction 2
    private val recentlyAddedInternal: List<UrlItem> = listOf(
        UrlItem(
            id = "new_joker",
            title = "Joker",
            overview = "A failed stand-up comedian is driven to madness.",
            category = "Recently Added",
            posterUrl = "https://images.unsplash.com/photo-1541535881962-3bb380b08458?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "new_oppenheimer",
            title = "Oppenheimer",
            overview = "The story of J. Robert Oppenheimer and the atomic bomb.",
            category = "Recently Added",
            posterUrl = "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "new_barbie",
            title = "Barbie",
            overview = "Barbie sets off on an adventure in the real world.",
            category = "Recently Added",
            posterUrl = "https://images.unsplash.com/photo-1520975937866-7168d54afa8c?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "new_fastx",
            title = "Fast X",
            overview = "Dom Toretto and family face a new threat.",
            category = "Recently Added",
            posterUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?q=80&w=600&auto=format&fit=crop"
        ),
        UrlItem(
            id = "new_extraction2",
            title = "Extraction 2",
            overview = "Tyler Rake returns for another deadly mission.",
            category = "Recently Added",
            posterUrl = "https://images.unsplash.com/photo-1508739773434-c26b3d09e071?q=80&w=600&auto=format&fit=crop"
        ),
    )

    // Banner backgrounds: Sci-Fi Landscape, Neon City, Space Theme, Night Skyline, Theater Lights
    private val bannerBackgroundsInternal: List<String> = listOf(
        "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?q=80&w=1920&auto=format&fit=crop", // Sci-Fi Landscape
        "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?q=80&w=1920&auto=format&fit=crop", // Neon City
        "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?q=80&w=1920&auto=format&fit=crop", // Space Theme
        "https://images.unsplash.com/photo-1496307042754-b4aa456c4a2d?q=80&w=1920&auto=format&fit=crop", // Night Skyline
        "https://images.unsplash.com/photo-1520975937866-7168d54afa8c?q=80&w=1920&auto=format&fit=crop"  // Theater Lights
    )

    private fun UrlItem.toMediaItem(): MediaItem =
        MediaItem(
            id = id,
            title = title,
            overview = overview,
            category = category,
            // Keep placeholders for resource ids as required by existing UI; URLs will be used by UI when available.
            posterResId = R.drawable.placeholder_poster,
            backdropResId = R.drawable.placeholder_backdrop,
            year = 2024,
            rating = 4.5,
        )

    private val categoriesInternal: List<Category> by lazy {
        listOf(
            Category("Recommended", recommendedInternal.map { it.toMediaItem() }),
            Category("Top Picks", topPicksInternal.map { it.toMediaItem() }),
            Category("Recently Added", recentlyAddedInternal.map { it.toMediaItem() }),
        )
    }

    // PUBLIC_INTERFACE
    fun categories(): List<Category> = categoriesInternal

    // PUBLIC_INTERFACE
    fun recommended(): List<MediaItem> = recommendedInternal.map { it.toMediaItem() }

    // PUBLIC_INTERFACE
    fun topPicks(): List<MediaItem> = topPicksInternal.map { it.toMediaItem() }

    // PUBLIC_INTERFACE
    fun recentlyAdded(): List<MediaItem> = recentlyAddedInternal.map { it.toMediaItem() }

    // PUBLIC_INTERFACE
    fun bannerBackgrounds(): List<String> = bannerBackgroundsInternal.toList()

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
