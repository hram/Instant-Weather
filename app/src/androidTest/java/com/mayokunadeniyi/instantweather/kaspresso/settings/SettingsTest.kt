package com.mayokunadeniyi.instantweather.kaspresso.settings

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mayokunadeniyi.instantweather.kaspresso.BaseAndroidTest
import com.mayokunadeniyi.instantweather.kaspresso.mocks.Mocks
import com.mayokunadeniyi.instantweather.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsTest : BaseAndroidTest() {

    private val sectionId = 234567890L // ID раздела для выгрузки в TestRail

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val precondition = "Приложение запускалось ранее и были выданы разрешения к данным о местоположении устройства."

    @Test
    fun openSettingsAndChangeCacheTest() = createTest(sectionId,
        "Изменение времени жизни кеша",
        "Переход в настройки, запуск редактирования времени жизни кеша и проверка что значение изменилось",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
        },
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckHomeScreen()
        stepMainScreenClickSettingsAndCheck()
        stepSettingsScreenClickCacheAndCheckDialog()
        stepEditValueScreenEnterValueAndCheck()
        stepEditValueScreenPressOkAndCheckSettingsScreen()
    }

    @Test
    fun openSettingsAndChangeThemeTest() = createTest(sectionId,
        "Изменение настройки темы",
        "Переход в настройки, запуск редактирования темы",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
        },
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckHomeScreen()
        stepMainScreenClickSettingsAndCheck()
        stepSettingsScreenClickThemeAndCheckDialog()
        stepChooseThemeScreenChooseDarkAndCheckSettingsScreen()
    }

    @Test
    fun openSettingsAndChangeTemperatureUnitTest() = createTest(sectionId,
        "Изменение настройки единыцы измерения температуры",
        "Переход в настройки, запуск редактирования единицы измерения температуры, проверка отображения",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
        },
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckHomeScreen()
        stepMainScreenClickSettingsAndCheck()
        stepSettingsScreenClickTemperatureUnitAndCheckDialog()
        stepChooseTemperatureUnitScreenChooseFahrenheitAndCheckSettingsScreen()
        stepMainScreenClickHomeAndCheckFahrenheit()
    }
}