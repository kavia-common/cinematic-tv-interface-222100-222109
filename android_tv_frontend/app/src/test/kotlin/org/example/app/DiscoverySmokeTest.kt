package org.example.app

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * PUBLIC_INTERFACE
 * A minimal JUnit5 test to ensure the Gradle unit test task discovers and executes at least one test.
 *
 * This prevents builds from failing with "no tests discovered" on certain configurations.
 */
class DiscoverySmokeTest {

    // PUBLIC_INTERFACE
    @Test
    @DisplayName("Discovery smoke test should run")
    fun discovery_runs() {
        assertTrue(true, "Discovery smoke test executed")
    }
}
