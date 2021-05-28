import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.firebase.crashlytics")
    id("marathon")
}

val API_KEY = "API_KEY"
val ALGOLIA_API_KEY = "ALGOLIA_API_KEY"
val ALGOLIA_APP_ID = "ALGOLIA_APP_ID"

fun getProperty(key: String): String {
    val items = HashMap<String, String>()

    val fl = rootProject.file("apikey.properties")

    (fl.exists())?.let {
        fl.forEachLine {
            items[it.split("=")[0]] = it.split("=")[1]
        }
    }

    return items[key] ?: ""
}


android {
    compileSdkVersion(Config.compileSdkVersion)
    buildToolsVersion(Config.buildTools)
    defaultConfig {
        applicationId(Config.applicationId)
        minSdkVersion(Config.minSdkVersion)
        targetSdkVersion(Config.targetSdkVersion)
        versionCode(Config.versionCode)
        versionName(Config.versionName)
        testInstrumentationRunner(Config.testInstrumentationRunner)
        testInstrumentationRunnerArguments(mapOf("clearPackageData" to "true"))

        buildConfigField("String", "API_KEY", getProperty(API_KEY))
        buildConfigField("String", "ALGOLIA_API_KEY", getProperty(ALGOLIA_API_KEY))
        buildConfigField("String", "ALGOLIA_APP_ID", getProperty(ALGOLIA_APP_ID))
        buildConfigField("String", "BASE_URL", "\"http://api.openweathermap.org/\"")

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }

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

    buildFeatures {
        dataBinding = true
    }

    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    compileOptions {
        sourceCompatibility(Config.javaVersion)
        targetCompatibility(Config.javaVersion)
    }

    tasks.withType().all {
        kotlinOptions {
            jvmTarget = Config.javaVersion.toString()
        }
    }

    androidExtensions {
        isExperimental = true
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }

    configurations.all {
        resolutionStrategy {
            exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-debug")
        }
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Kotlin.stdlib)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.coreKtx)
    implementation(View.constraintLayout)
    implementation(AndroidX.legacySupport)

    //Material Design
    implementation(View.material)

    //Room
    implementation(Database.roomRuntime)
    kapt(Database.roomCompiler)
    implementation(Database.roomKtx)

    //Kotlin Coroutines
    implementation(Kotlin.coroutinesAndroid)

    //Navigation Components
    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)

    //Retrofit
    implementation(Network.retrofit)
    implementation(Network.gsonConverter)
    implementation(Network.gson)

    implementation("com.squareup.okhttp3:okhttp-tls:4.9.1")

    //Preferences
    implementation(AndroidX.preferences)

    //Timber
    implementation(Utils.timber)

    //Weather Image
    implementation(Utils.weatherImage)

    //CalenderView
    implementation(Utils.calendarView)

    //Google Play Services
    implementation(Google.googlePlayGms)

    //VegaLayoutManager
    implementation(Utils.vegaLayoutManager)


    //Lifecycle KTX
    implementation(AndroidX.viewModel)
    implementation(AndroidX.liveData)
    implementation(AndroidX.lifeCycleCommon)

    //Algolia Search
    implementation(Utils.algoliaSearch)

    //Paging Library
    implementation(AndroidX.paging)

    //Elastic view
    implementation(Utils.elasticViews)

    //WorkManager
    implementation(AndroidX.workManager)

    //Dagger
    implementation(Dagger.dagger)
    kapt(Dagger.daggerCompiler)
    implementation(Dagger.daggerAndroid)
    kapt(Dagger.daggerProcessor)
    implementation(Dagger.daggerAndroidSupport)
    //Enable dagger compiler also in androidTest folder
    kaptAndroidTest(Dagger.daggerCompiler)
    kaptAndroidTest(Dagger.daggerProcessor)

    //OKHttp Logging Interceptor
    implementation(Network.okhttpInterceptor)

    //Chuck
    debugImplementation(Network.chuck)
    releaseImplementation(Network.chuckNoOp)

    //Firebase BoM, Crashlytics, Analytics
    implementation(platform(Firebase.firebaseBom))
    implementation(Firebase.crashlytics)
    implementation(Firebase.analytics)

    // AndroidX Test - JVM testing
    testImplementation(AndroidX.testExt)
    testImplementation(AndroidX.coreKtxTest)
    testImplementation(AndroidX.archCoreTesting)
    testImplementation(UnitTest.junit)
    testImplementation(UnitTest.roboelectric)
    testImplementation(UnitTest.hamcrest)
    testImplementation(Kotlin.coroutineTest)
    testImplementation(UnitTest.mockitoCore)

    // AndroidX Test - Instrumented testing
    //androidTestImplementation(UnitTest.mockitoCore)
    //androidTestImplementation(AndroidX.testExt)
    androidTestImplementation(AndroidTest.espresso)
    androidTestImplementation(AndroidTest.espressoContrib)
    androidTestImplementation(AndroidTest.espressoIntent)
    androidTestImplementation(AndroidX.archCoreTesting)
    //androidTestImplementation(AndroidX.coreKtxTest)
    androidTestImplementation(Kotlin.coroutineTest)

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.ext:truth:1.3.0")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestUtil("androidx.test:orchestrator:1.3.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.8.1")
    androidTestImplementation("com.squareup.okhttp3:okhttp-tls:4.9.1")
    androidTestImplementation("com.kaspersky.android-components:kaspresso:1.2.0")
    androidTestImplementation("com.github.infeez:kotlin-mock-server:0.7.5")
    androidTestImplementation("com.jraska:falcon:2.1.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
}

marathon {
    name = "Instant Weather"
    baseOutputDir = "build/reports/marathon"

    allureConfiguration {
        enabled = false
    }

    batchingStrategy {
        fixedSize {
            size = 1
        }
    }

    retryStrategy {
        fixedQuota {
            totalAllowedRetryQuota = 100
            retryPerTestQuota = 1
        }
    }

    testClassRegexes = listOf("^((?!Abstract).)*HomeTest\$")
    uncompletedTestRetryQuota = 100
    ignoreFailures = false
    isCodeCoverageEnabled = true
    fallbackToScreenshots = false
    testOutputTimeoutMillis = 300_000
    strictMode = false
    debug = false
    autoGrantPermission = false
    applicationPmClear = true
}
