package com.mayokunadeniyi.instantweather.kaspresso.search

import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.mayokunadeniyi.instantweather.kaspresso.TestCaseDsl
import com.mayokunadeniyi.instantweather.kaspresso.extensions.expected
import com.mayokunadeniyi.instantweather.kaspresso.extensions.inProcessClick
import com.mayokunadeniyi.instantweather.kaspresso.extensions.takeScreenshot
import com.mayokunadeniyi.instantweather.kaspresso.screen.HomeScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.MainScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.SearchScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.SettingsScreen

fun TestContext<TestCaseDsl>.stepStartAppAndCheckHomeScreen() {
    step("Запустить приложение") {
        takeScreenshot()
        expected("Отображается Home экран c текущей погодой") {
            MainScreen {
                homeButton { isSelected() }
                forecastButton { isNotSelected() }
                searchButton { isNotSelected() }
                settingsButton { isNotSelected() }

                HomeScreen {
                    weatherIn { hasText("Novaya Gollandiya") }
                    date { hasAnyText() } // TODO замокать время
                    icon { isDisplayed() }
                    temperature { hasText("14.25℃") }
                    main { hasText("Clouds") }
                    error { isNotDisplayed() }
                    detail { isDisplayed() }
                    humidity { hasText("95.0%") }
                    pressure { hasText("1000.0hPa") }
                    windSpeed { hasText("2.72m/s") }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepMainScreenClickSearchAndCheck() {
    step("Кликнуть в панели навигации на иконку поиска") {
        MainScreen {
            searchButton { inProcessClick() }
        }

        expected("Просходит переход на экран поиска") {
            SearchScreen {
                recycler {
                    isDisplayed()
                    hasSize(4)
                }
            }
        }
    }
}