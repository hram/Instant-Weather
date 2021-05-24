package com.mayokunadeniyi.instantweather.kaspresso.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mayokunadeniyi.instantweather.kaspresso.BaseAndroidTest
import com.mayokunadeniyi.instantweather.kaspresso.mocks.Mocks
import com.mayokunadeniyi.instantweather.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeTest : BaseAndroidTest() {

    private val sectionId = 234567890L // ID раздела для выгрузки в TestRail

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val precondition = "Приложение запускалось ранее и были выданы разрешения к данным о местоположении устройства."

    @Test
    fun homeSuccessLoadDataTest() = createTest(sectionId,
        "Успешная загрузка погоды на основе местовоположения",
        "Основной позитивный сценарий запуска приложения и отображения текущей погоды",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
        }, init = {
            weatherIn = "Novaya Gollandiya"
            temperature = "14.25℃"
            main = "Clouds"
            humidity = "95.0%"
            pressure = "1000.0hPa"
            windSpeed = "2.72m/s"
        },
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckHomeScreen()
    }

    @Test
    fun homeErrorLoadDataAndRefreshSuccessTest() = createTest(sectionId,
        "Ошибка загрузки погоды",
        "Ошибка загрузки погоды и успешное обновление",
        precondition, {
            add(Mocks.weather500)
        }, init = {
            weatherIn = "Novaya Gollandiya"
            temperature = "14.25℃"
            main = "Clouds"
            humidity = "95.0%"
            pressure = "1000.0hPa"
            windSpeed = "2.72m/s"
        },
        before = {
            activityTestRule.launchActivity(null)
        }) { server ->
        stepStartAppAndCheckHomeScreenWithError()
        server.replace(Mocks.weather500, Mocks.weatherNovayaGollandiyaSuccess)
        stepHomeScreenSwipeToRefreshAndCheckHomeScreen()
    }
}