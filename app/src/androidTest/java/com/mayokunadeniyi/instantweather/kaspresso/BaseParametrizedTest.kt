package com.mayokunadeniyi.instantweather.kaspresso

import com.kaspersky.kaspresso.interceptors.watcher.testcase.impl.logging.LoggingStepWatcherInterceptor
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.logger.UiTestLoggerImpl
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.BaseTestCase
import java.util.concurrent.TimeUnit

open class BaseParametrizedTest(
    kaspressoBuilder: Kaspresso.Builder = Kaspresso.Builder.advanced {
        flakySafetyParams = FlakySafetyParams.default().apply {
            intervalMs = TimeUnit.MILLISECONDS.toMillis(100)
            timeoutMs = TimeUnit.SECONDS.toMillis(20)
        }
        testLogger = UiTestLoggerImpl("KASPRESSO")
    }.apply {
        stepWatcherInterceptors = mutableListOf(LoggingStepWatcherInterceptor(libLogger))
        screenshots = ScreenshotsFalcon(activities)
    }
) : BaseTestCase<TestCaseDsl, TestCaseDsl>(
    kaspressoBuilder = kaspressoBuilder,
    dataProducer = provideMainDataProducer()
) {
    companion object {
        private fun provideMainDataProducer(): ((TestCaseDsl.() -> Unit)?) -> TestCaseDsl {
            return { action -> TestCaseDataProducer().initData(action) }
        }
    }
}

@DslMarker
annotation class TestCaseDslMarker

@TestCaseDslMarker
class TestCaseDsl {
    lateinit var weatherIn: String
    lateinit var temperature: String
    lateinit var main: String
    lateinit var humidity: String
    lateinit var pressure: String
    lateinit var windSpeed: String
}