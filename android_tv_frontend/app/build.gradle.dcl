androidApplication {
    namespace = "org.example.app"

    // Compose configuration block for Declarative Gradle DSL
    compose {
        enabled = true
    }

    dependencies {
        // Compose BOM to align versions
        implementation(platform("androidx.compose:compose-bom:2024.10.01"))

        // Core Compose UI
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-tooling-preview")

        // Material 3 for Compose
        implementation("androidx.compose.material3:material3")

        // Navigation for Compose
        implementation("androidx.navigation:navigation-compose:2.8.3")

        // Activity Compose integration
        implementation("androidx.activity:activity-compose:1.9.3")

        // Android TV Compose libraries
        implementation("androidx.tv:tv-foundation:1.0.0-alpha10")
        implementation("androidx.tv:tv-material:1.0.0-alpha10")

        // Existing dependencies
        implementation("org.apache.commons:commons-text:1.11.0")
        implementation(project(":utilities"))
    }
}
