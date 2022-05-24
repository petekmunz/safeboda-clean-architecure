plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.munyao.safeboda"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {

    implementation(Dependencies.Deps.kotlinExtensions)
    implementation(Dependencies.Deps.appCompat)
    implementation(Dependencies.Deps.constraintLayout)
    implementation(Dependencies.Deps.materialDesign)
    testImplementation(Dependencies.Deps.junit)
    androidTestImplementation(Dependencies.Deps.extJunit)
    androidTestImplementation(Dependencies.Deps.espresso)
    implementation(Dependencies.Deps.paging3)
    implementation(Dependencies.Deps.hilt)
    kapt(Dependencies.Deps.hiltCompiler)

    implementation(project(":data"))
    implementation(project(":domain"))
}