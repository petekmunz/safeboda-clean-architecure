class Dependencies {
    object BuildPlugins {
        val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
        val hiltPlugin by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
        val navigationSafeArgs by lazy{"androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"}
    }

    object Deps {
        val legacySupport by lazy { "androidx.legacy:legacy-support-v4:${Versions.legacySupport}" }
        val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
        val kotlinExtensions by lazy { "androidx.core:core-ktx:${Versions.kotlinExtensions}" }
        val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
        val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
        val junit by lazy { "junit:junit:${Versions.jUnit}" }
        val hilt by lazy{"com.google.dagger:hilt-android:${Versions.hilt}"}
        val hiltCompiler by lazy{"com.google.dagger:hilt-compiler:${Versions.hilt}"}
        val room by lazy{"androidx.room:room-runtime:${Versions.room}"}
        val roomKtx by lazy{"androidx.room:room-ktx:${Versions.room}"}
        val roomCompiler by lazy{"androidx.room:room-compiler:${Versions.room}"}
        val roomPaging by lazy{"androidx.room:room-paging:2.5.0-alpha01"}
        val retrofit by lazy{"com.squareup.retrofit2:retrofit:${Versions.retrofit}"}
        val retrofitGsonConverter by lazy{"com.squareup.retrofit2:converter-gson:${Versions.retrofit}"}
        val gson by lazy{"com.google.code.gson:gson:${Versions.gson}"}
        val coroutinesAndroid by lazy{"org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"}
        val coroutinesCore by lazy{"org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"}
        val espresso by lazy{"androidx.test.espresso:espresso-core:${Versions.espresso}"}
        val extJunit by lazy{"androidx.test.ext:junit:${Versions.extJunit}"}
        val paging3 by lazy{"androidx.paging:paging-runtime:${Versions.paging3}"}
        val pagingCommon by lazy{"androidx.paging:paging-common:${Versions.paging3}"}
        val httpLoggingInterceptor by lazy{"com.squareup.okhttp3:logging-interceptor:${Versions.httpLogInterceptor}"}
        val navigationFragment by lazy{"androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"}
        val navigationUiKtx by lazy{"androidx.navigation:navigation-ui-ktx:${Versions.navigation}"}
        val javaxInject by lazy{"javax.inject:javax.inject:${Versions.javaxInject}"}
    }
}