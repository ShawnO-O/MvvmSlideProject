plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)  version "1.9.0"
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.shawn.mvvmslideproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.shawn.mvvmslideproject"
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation (libs.androidx.navigation.compose)
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation (libs.ui)
    implementation (libs.androidx.material)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.fragment.ktx)
    //fragment
    implementation(libs.androidx.fragment.ktx) // 或最新版本
    //activity
    implementation(libs.androidx.activity.ktx) // 或最新版本
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    //Coil-loading image
    implementation("io.coil-kt:coil-compose:2.7.0")
    //hilt 這要對應kotlin版本 所以一定要版本一致性
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation(libs.androidx.appcompat)
    kapt("com.google.dagger:hilt-android-compiler") { version { strictly("2.48") } }
    implementation("com.google.dagger:hilt-android") { version { strictly("2.48") } }
    kapt("com.google.dagger:hilt-compiler") { version { strictly("2.48") } }
    implementation("com.google.dagger:hilt-android") { version { strictly("2.48") } }
    kapt("com.google.dagger:hilt-android-compiler") { version { strictly("2.48") } }

    // room
    implementation(libs.androidx.room.ktx)
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //import mockito
    testImplementation ("io.mockk:mockk:1.13.12")
    androidTestImplementation ("io.mockk:mockk-android:1.13.12")
    //test for coroutines
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0-RC")
    //For InstantTaskExecutorRule
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    //test for flow
    testImplementation("app.cash.turbine:turbine:0.12.1")
}