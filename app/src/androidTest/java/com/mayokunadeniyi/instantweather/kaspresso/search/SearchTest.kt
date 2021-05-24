package com.mayokunadeniyi.instantweather.kaspresso.search

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mayokunadeniyi.instantweather.kaspresso.BaseAndroidTest
import com.mayokunadeniyi.instantweather.kaspresso.mocks.Mocks
import com.mayokunadeniyi.instantweather.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTest : BaseAndroidTest() {

    private val sectionId = 234567890L // ID раздела для выгрузки в TestRail

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val precondition = "Приложение запускалось ранее и были выданы разрешения к данным о местоположении устройства."

    @Test
    fun searchSuccessLoadDataTest() = createTest(sectionId,
        "Переход на экран поиска, успешная загрузка подсказок",
        "Проверка перехода на экран поиска и работа с Algolia Instant Search",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
            add(Mocks.indexesQueries)
        },
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckHomeScreen()
        stepMainScreenClickSearchAndCheck()
    }
}