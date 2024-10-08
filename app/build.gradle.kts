import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinAndroidKsp)
    //alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}


android {
    namespace = "com.example.movieTracker"
    compileSdk = 34

    val localProperties = Properties()
    val propertiesFile = rootProject.file("local.properties")



    localProperties.load(propertiesFile.inputStream())


    val movieApiKey = localProperties.getProperty("MOVIE_API_KEY") ?: ""

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "MOVIE_API_KEY", movieApiKey)
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

dependencies {

    // Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-android-compiler:2.49")
    ksp("com.google.dagger:hilt-compiler:2.49")
    implementation("androidx.hilt:hilt-work:1.2.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    //ksp ("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation(libs.navigation.compose)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Coil Compose
    implementation("io.coil-kt:coil-compose:2.1.0")

    // Material Extended Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.8")

    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.0")

    implementation("androidx.compose.material:material:1.6.8")

    // data store
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // kotlin serializer
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    // accompaniest
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.27.0")


    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.navigation.compose)
    
    //implementation("androidx.compose.animation:animation:1.6.8")
    //implementation("androidx.compose.foundation:foundation:1.6.8")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}