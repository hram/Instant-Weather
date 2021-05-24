package com.mayokunadeniyi.instantweather.kaspresso

import android.os.Build
import com.jraska.falcon.Falcon
import com.kaspersky.kaspresso.device.activities.Activities
import com.kaspersky.kaspresso.device.screenshots.Screenshots
import com.kaspersky.kaspresso.device.screenshots.screenshotfiles.ScreenshotsDirectoryStorage
import java.io.File

class ScreenshotsFalcon(private val activities: Activities, private val screenshotDir: File = File("screenshots")) : Screenshots {
    private val TEST_CASE_CLASS_JUNIT_3 = "android.test.InstrumentationTestCase"
    private val TEST_CASE_METHOD_JUNIT_3 = "runMethod"
    private val TEST_CASE_CLASS_JUNIT_4 = "org.junit.runners.model.FrameworkMethod$1"
    private val TEST_CASE_METHOD_JUNIT_4 = "runReflectiveCall"
    private val TEST_CASE_CLASS_CUCUMBER_JVM = "cucumber.runtime.model.CucumberFeature"
    private val TEST_CASE_METHOD_CUCUMBER_JVM = "run"
    private val NAME_SEPARATOR = "_"
    private val EXTENSION = ".png"

    private val directoryStorage = ScreenshotsDirectoryStorage()

    override fun take(tag: String) {
        val resumedActivity = activities.getResumed()

        if (resumedActivity != null) {
            runCatching {
                val screenshotFile = getScreenshotFile(tag)
                Falcon.takeScreenshot(resumedActivity, screenshotFile)
            }.onSuccess {
                return
            }
        }
    }

    fun getScreenshotFile(tag: String): File {
        val screenshotRootDirectory = directoryStorage.getRootScreenshotDirectory(screenshotDir)

        val screenshotTestDirectory = directoryStorage.obtainDirectory(getDirectoryForTest(screenshotRootDirectory))
        if (!screenshotTestDirectory.exists()) {
            screenshotTestDirectory.mkdirs()
        }
        val screenshotName = tag + EXTENSION
        return screenshotTestDirectory.resolve(screenshotName)
    }

    private fun getDirectoryForTest(screenshotRootDirectory: File): File {
        val testClass = Thread.currentThread().stackTrace.findTestClassTraceElement()
        val className = testClass.className.replace("[^A-Za-z0-9._-]".toRegex(), NAME_SEPARATOR)
        val methodName = testClass.methodName

        return screenshotRootDirectory.resolve(className).resolve(methodName)
    }

    internal fun Array<StackTraceElement>.findTestClassTraceElement(): StackTraceElement {
        return this.withIndex().reversed()
            .find { (_, element) -> element.isJunit3() || element.isJunit4() || element.isCucumber() }
            ?.let { (i, _) -> extractStackElement(i) }
            ?: throw IllegalArgumentException("Could not find test class! Trace: ${this.map { it.toString() }}")
    }

    private fun StackTraceElement.isJunit3(): Boolean {
        return TEST_CASE_CLASS_JUNIT_3 == className && TEST_CASE_METHOD_JUNIT_3 == methodName
    }

    private fun StackTraceElement.isJunit4(): Boolean {
        return TEST_CASE_CLASS_JUNIT_4 == className && TEST_CASE_METHOD_JUNIT_4 == methodName
    }

    private fun StackTraceElement.isCucumber(): Boolean {
        return TEST_CASE_CLASS_CUCUMBER_JVM == className && TEST_CASE_METHOD_CUCUMBER_JVM == methodName
    }

    @Suppress("MagicNumber")
    private fun Array<StackTraceElement>.extractStackElement(i: Int): StackTraceElement {
        // Stacktrace length changed in M
        val testClassTraceIndex = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) i - 2 else i - 3
        return this[testClassTraceIndex]
    }
}