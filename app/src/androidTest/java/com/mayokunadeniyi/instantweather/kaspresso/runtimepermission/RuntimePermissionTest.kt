package com.mayokunadeniyi.instantweather.kaspresso.runtimepermission

import android.Manifest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.mayokunadeniyi.instantweather.kaspresso.BaseAndroidTest
import com.mayokunadeniyi.instantweather.ui.MainActivity
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RuntimePermissionTest : BaseAndroidTest() {

    private val sectionId = 123456789L // ID раздела для выгрузки в TestRail

    // переопределение пермишенов так как в BaseAndroidTest по умолчанию выдаются все пермишены
    @get:Rule
    override val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val precondition = "Приложение запущено впервые. Разрешение на доступ к данным о местоположении устройства - отключено."

    @Test
    fun runtimePermissionEnabledTest() = createTest(sectionId,
        "Запуск приложения. Разрешение на доступа к данным о местоположении устройства",
        "Основной позитивный сценарий на разрешение доступа к данным о местоположении устройства.",
        precondition,
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckRuntimePermissionDialogScreen()
        stepRuntimePermissionDialogScreenAllowAndCheckMainScreen()
    }

    @Test
    fun runtimePermissionDenyAndNoTest() = createTest(sectionId,
        "Запрет на доступ к данным о местоположении устройства на Rationale экране",
        "При запуске приложения пользователь отказался дать разрешения...",
        precondition,
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckRuntimePermissionDialogScreen()
        stepRuntimePermissionDialogScreenDenyAndCheckPermissionRationaleScreen()
        stepRationaleScreenClickNoAndExit()
    }

    @Test
    fun runtimePermissionDenyAckAgainAllowTest() = createTest(sectionId,
        "Запуск приложения. Запрет на доступа к данным о местоположении устройства",
        "При запуске приложения пользователь сначала отказался дать разрешения, но потом разрешил",
        precondition,
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckRuntimePermissionDialogScreen()
        stepRuntimePermissionDialogScreenDenyAndCheckPermissionRationaleScreen()
        stepRationaleScreenClickAskMeAndCheckRuntimePermissionDialogScreen()
        stepRuntimePermissionDialogScreenAllowAndCheckMainScreen()
    }

    @Test
    fun runtimePermissionDenyAckAgainDenyTest() = createTest(sectionId,
        "Запрет на доступа к данным о местоположении устройства",
        "При запуске приложения пользователь отказался 2 раза дать разрешения",
        precondition,
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckRuntimePermissionDialogScreen()
        stepRuntimePermissionDialogScreenDenyAndCheckPermissionRationaleScreen()
        stepRationaleScreenClickAskMeAndCheckRuntimePermissionDialogScreen()
        stepRuntimePermissionDialogScreenDenyAndCheckPermissionRationaleScreen()
        stepRationaleScreenClickNoAndExit()
    }

    @Ignore("Этот сценанарий никак не обрабатывапется приложением и оно зависает. NoActivityResumedException: No activities in stage RESUMED. Did you forget to launch the activity")
    @Test
    fun runtimePermissionDenyAckAgainNewerAskTest() = createTest(sectionId,
        "Запрет на доступа к данным о местоположении устройства с запретом выводить запрос",
        "При запуске приложения пользователь отказался дать разрешения и запретил запрашивать их в дальнейшем",
        precondition,
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckRuntimePermissionDialogScreen()
        stepRuntimePermissionDialogScreenDenyAndCheckPermissionRationaleScreen()
        stepRationaleScreenClickAskMeAndCheckRuntimePermissionDialogScreen()
        stepRuntimePermissionDialogScreenDenyAndNeverAskAndCheckMainScreen()
    }
}