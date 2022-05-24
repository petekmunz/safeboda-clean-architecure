plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies{
    implementation(Dependencies.Deps.pagingCommon)
    implementation(Dependencies.Deps.coroutinesCore)
    implementation(Dependencies.Deps.hilt)
    kapt(Dependencies.Deps.hiltCompiler)
}