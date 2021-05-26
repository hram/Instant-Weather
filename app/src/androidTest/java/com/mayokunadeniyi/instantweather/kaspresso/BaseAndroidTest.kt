package com.mayokunadeniyi.instantweather.kaspresso

import android.Manifest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.infeez.mock.ScenarioBuilder
import com.infeez.mock.mockScenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.mayokunadeniyi.instantweather.InstantWeatherApplication
import com.mayokunadeniyi.instantweather.data.source.repository.WeatherRepository
import com.mayokunadeniyi.instantweather.kaspresso.di.DaggerTestAppComponent
import com.mayokunadeniyi.instantweather.kaspresso.rule.DisableAnimationsRule
import com.mayokunadeniyi.instantweather.kaspresso.rule.DisableVirtualKeyboardRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule

open class BaseAndroidTest : BaseParametrizedTest() {

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    @get:Rule
    val disableKeyboardRule = DisableVirtualKeyboardRule()

    @get:Rule
    open val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

    private lateinit var server: MockWebServer

    lateinit var weatherRepository: WeatherRepository

    @Before
    open fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()

        val app = instrumentation.targetContext.applicationContext as InstantWeatherApplication

        val appInjector = DaggerTestAppComponent.builder()
            .application(app)
            .build()
        appInjector.inject(app)

        server = appInjector.getMockWebServer()
        weatherRepository = appInjector.getWeatherRepository()
    }

    fun createTest(
        sectionId: Long,
        title: String? = null,
        description: String? = null,
        preconditions: String? = null,
        mockScenario: (ScenarioBuilder.() -> Unit)? = null,
        before: ((ScenarioBuilder) -> Unit)? = null,
        init: (suspend TestCaseDsl.() -> Unit)? = null,
        after: (TestCaseDsl.() -> Unit)? = null,
        run: TestContext<TestCaseDsl>.(ScenarioBuilder) -> Unit
    ) {
        var data: TestCaseDsl? = null
        var scenarioBuilder: ScenarioBuilder? = null
        before {
            if (sectionId != -1L) {
                testLogger.i("$SECTION_ID $sectionId")
            }
            title?.apply {
                testLogger.i("$TITLE_SECTION $title")
            }
            description?.apply {
                lines().forEach { line ->
                    testLogger.i("$DESCRIPTION_SECTION $line")
                }
            }
            preconditions?.apply {
                lines().forEach { line ->
                    testLogger.i("$PRECONDITIONS_SECTION $line")
                }
            }
            scenarioBuilder = server.mockScenario(mockScenario ?: {})
            before?.invoke(scenarioBuilder!!)
        }.after {
            after?.invoke(data!!)
        }.init {
            runBlocking { init?.invoke(this@init) }
            data = this
        }.run {
            run(scenarioBuilder!!)
        }
    }
}