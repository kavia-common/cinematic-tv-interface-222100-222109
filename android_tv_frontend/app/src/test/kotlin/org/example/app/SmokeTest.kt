package org.example.app

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * PUBLIC_INTERFACE
 * Minimal smoke test to ensure the Gradle test task discovers and runs at least one test.
 */
class SmokeTest {

    // PUBLIC_INTERFACE
    @Test
    fun smoke_runs() {
        /** Verifies that the JUnit Platform is configured and tests run in the app module. */
        assertTrue(true)
    }
}
