package com.mayokunadeniyi.instantweather.kaspresso.search

import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.mayokunadeniyi.instantweather.kaspresso.TestCaseDsl
import com.mayokunadeniyi.instantweather.kaspresso.extensions.expected
import com.mayokunadeniyi.instantweather.kaspresso.extensions.inProcessClick
import com.mayokunadeniyi.instantweather.kaspresso.extensions.takeScreenshot
import com.mayokunadeniyi.instantweather.kaspresso.screen.HomeScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.MainScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.SearchScreen

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
                    weatherIn { hasText(data.weatherIn) }
                    date { hasAnyText() } // TODO замокать время
                    icon { isDisplayed() }
                    temperature { hasText(data.temperature) }
                    main { hasText(data.main) }
                    error { isNotDisplayed() }
                    detail { isDisplayed() }
                    humidity { hasText(data.humidity) }
                    pressure { hasText(data.pressure) }
                    windSpeed { hasText(data.windSpeed) }
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