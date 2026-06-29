plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.rickandmortyquest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rickandmortyquest"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "RICK_AND_MORTY_BASE_URL",
                "\"https://rickandmortyapi.com/api/\""
            )

            buildConfigField(
                "String",
                "FAKE_POST_BASE_URL",
                "\"https://jsonplaceholder.typicode.com/\""
            )

            buildConfigField(
                "String",
                "LOCAL_BACKEND_BASE_URL",
                "\"http://10.0.2.2:8080/\""
            )
        }

        release {
            isMinifyEnabled = false
            isShrinkResources = false

            buildConfigField(
                "String",
                "RICK_AND_MORTY_BASE_URL",
                "\"https://rickandmortyapi.com/api/\""
            )

            buildConfigField(
                "String",
                "FAKE_POST_BASE_URL",
                "\"https://jsonplaceholder.typicode.com/\""
            )

            buildConfigField(
                "String",
                "LOCAL_BACKEND_BASE_URL",
                "\"http://10.0.2.2:8080/\""
            )

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.runtime)

    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    implementation(libs.gson)

    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockito)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}