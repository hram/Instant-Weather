package com.mayokunadeniyi.instantweather.kaspresso.extensions

import androidx.test.espresso.action.CoordinatesProvider
import com.agoda.kakao.common.actions.BaseActions
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.mayokunadeniyi.instantweather.kaspresso.EXPECTED
import com.mayokunadeniyi.instantweather.kaspresso.SCREENSHOT_CONTENT
import com.mayokunadeniyi.instantweather.kaspresso.SCREENSHOT_EXPECTED
import com.mayokunadeniyi.instantweather.kaspresso.ScreenshotsFalcon
import com.mayokunadeniyi.instantweather.utils.EspressoActions
import com.mayokunadeniyi.instantweather.utils.VisibleCenterGlobalCoordinatesProvider
import java.lang.IllegalArgumentException

fun TestContext<*>.expected(description: String, actions: (() -> Unit)? = null) {
    if (description.isBlank()) {
        throw IllegalArgumentException("description не должны быть пустым")
    }
    description.lines().forEach { line ->
        testLogger.i("$EXPECTED $line")
    }
    actions?.apply {
        try {
            invoke()
        } finally {
            takeScreenshot(SCREENSHOT_EXPECTED)
        }
    }
}

private fun TestContext<*>.takeScreenshot(name: String) {
    val tag = System.currentTimeMillis().toString()
    testLogger.i("$name $tag")
    val file = (device.screenshots as ScreenshotsFalcon).getScreenshotFile(tag)
    device.uiDevice.takeScreenshot(file)
}

fun TestContext<*>.takeScreenshot() {
    takeScreenshot(SCREENSHOT_CONTENT)
}

fun BaseActions.inProcessClick(coordinatesProvider: CoordinatesProvider = VisibleCenterGlobalCoordinatesProvider()) {
    act { EspressoActions.click(coordinatesProvider = coordinatesProvider) }
}