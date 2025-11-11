package org.example.app

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * PUBLIC_INTERFACE
 * Always discovered test to ensure JUnit has at least one test method per variant.
 *
 * This avoids CI failures where Gradle variants may be configured but have no tests detected.
 */
class AlwaysDiscoveredTest {

    // PUBLIC_INTERFACE
    @Test
    @DisplayName("Always-discovered trivial test")
    fun always_discovered() {
        assertTrue(true)
    }
}
