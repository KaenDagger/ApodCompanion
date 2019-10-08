private object Versions {
    const val appCompat = "1.1.0"
    const val ktx = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val retrofit = "2.6.2"
    const val picasso = "2.71828"
    const val materialComponents = "1.1.0-alpha10"
    const val dagger = "2.24"
    const val room = "2.2.0-rc01"
    const val coroutines = "1.3.2"
    const val lifecycle = "2.2.0-alpha02"


    const val junit = "4.12"
    const val androidJunit = "1.1.0"
    const val espresso = "3.2.0"
    const val mockito = "1.10.19"
}

object Libs {
    const val kotlinStdLib =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${ProjectProperties.kotlinVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.ktx}"

    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialComponents}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    const val junit = "junit:junit:${Versions.junit}"
    const val androidTestJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx="androidx.room:room-ktx:${Versions.room}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val viewmodel="androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"

    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

}