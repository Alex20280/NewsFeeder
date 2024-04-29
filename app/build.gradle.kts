plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.newsfeeder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newsfeeder"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
        )
        )
    }

    kapt {
        correctErrorTypes = true

        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {

    // Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.ui.test.manifest)

    // Test
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.truth)
    androidTestImplementation(libs.androidx.rules)
    implementation(libs.junit.platform.commons)
    testImplementation (libs.kotlinx.coroutines.test.v162)
    testImplementation (libs.androidx.core.testing)
    debugImplementation (libs.core.testing)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit.jupiter)
    testImplementation (libs.truth)
    testImplementation (libs.junit.jupiter.api)
    testImplementation (libs.hamcrest)
    testImplementation(libs.turbine)


    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.3")

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    debugImplementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.reactive)
    androidTestImplementation (libs.kotlinx.coroutines.test)

    //Lifecycle
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Navigation
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson) //Remove?
    implementation(libs.gson)
    implementation(libs.logging.interceptor)
    implementation (libs.converter.simplexml)

    // Hilt
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android.gradle.plugin)
    implementation (libs.androidx.navigation.compose)
    androidTestImplementation  (libs.hilt.android.testing)
    kaptAndroidTest (libs.hilt.compiler)
    testImplementation (libs.hilt.android.testing)
    kaptTest (libs.hilt.compiler)

    //Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    implementation (libs.androidx.room.paging)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.common)

    //Fonts
    implementation (libs.androidx.ui.text.google.fonts)

}