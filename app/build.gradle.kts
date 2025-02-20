plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(ProjectProperties.compileSdk)
    buildToolsVersion(ProjectProperties.buildToolsSdkVersion)
    defaultConfig {
        applicationId = ProjectProperties.applicationId
        minSdkVersion(ProjectProperties.minSdk)
        targetSdkVersion(ProjectProperties.targetSdk)
        versionCode = ProjectProperties.versionCode
        versionName = ProjectProperties.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    val APOD_API_KEY: String by project
    buildTypes.forEach {
        it.buildConfigField("String", "APOD_API_KEY", APOD_API_KEY)
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))
    implementation(Libs.kotlinStdLib)
    implementation(Libs.appCompat)
    implementation(Libs.coreKtx)
    implementation(Libs.constraintLayout)
    implementation(Libs.retrofit)
    implementation(Libs.retrofit_converter)
    implementation(Libs.picasso)
    implementation(Libs.materialDesign)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
    implementation(Libs.room_runtime)
    kapt(Libs.room_compiler)
    implementation(Libs.room_ktx)
    implementation(Libs.coroutines)
    implementation(Libs.viewmodel)
    implementation(Libs.lifecycle_extensions)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.coroutines_test)
    androidTestImplementation(Libs.androidTestJunit)
    androidTestImplementation(Libs.espresso)
}
