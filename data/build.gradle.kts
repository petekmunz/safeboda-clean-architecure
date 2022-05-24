plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release"){
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

    implementation(Dependencies.Deps.coroutinesAndroid)
    implementation(Dependencies.Deps.kotlinExtensions)
    implementation(Dependencies.Deps.appCompat)
    implementation(Dependencies.Deps.materialDesign)
    testImplementation(Dependencies.Deps.junit)
    androidTestImplementation(Dependencies.Deps.extJunit)
    androidTestImplementation(Dependencies.Deps.espresso)

    implementation(Dependencies.Deps.retrofit)
    implementation(Dependencies.Deps.gson)
    implementation(Dependencies.Deps.retrofitGsonConverter)
    implementation(Dependencies.Deps.httpLoggingInterceptor)
    implementation(Dependencies.Deps.room)
    implementation(Dependencies.Deps.roomKtx)
    annotationProcessor(Dependencies.Deps.roomCompiler)
    implementation(Dependencies.Deps.roomPaging)
    implementation(Dependencies.Deps.hilt)
    kapt(Dependencies.Deps.hiltCompiler)
    implementation(Dependencies.Deps.paging3)

    implementation(project(":domain"))

}