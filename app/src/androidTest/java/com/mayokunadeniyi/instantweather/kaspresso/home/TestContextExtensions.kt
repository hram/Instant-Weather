package com.mayokunadeniyi.instantweather.kaspresso.home

import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.mayokunadeniyi.instantweather.kaspresso.TestCaseDsl
import com.mayokunadeniyi.instantweather.kaspresso.extensions.expected
import com.mayokunadeniyi.instantweather.kaspresso.extensions.takeScreenshot
import com.mayokunadeniyi.instantweather.kaspresso.screen.HomeScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.MainScreen

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

fun TestContext<TestCaseDsl>.stepStartAppAndCheckHomeScreenWithError() {
    step("Запустить приложение") {
        takeScreenshot()
        expected("Отображается ошибка загрузки данных") {
            HomeScreen {
                error { hasText("Oops! An error occurred. \n\n Please check your internet connection. \n\n Swipe down to retry.") }
                weatherIn { isNotDisplayed() }
                date { isNotDisplayed() }
                icon { isNotDisplayed() }
                temperature { isNotDisplayed() }
                main { isNotDisplayed() }
                detail { isNotDisplayed() }
                humidity { isNotDisplayed() }
                pressure { isNotDisplayed() }
                windSpeed { isNotDisplayed() }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepHomeScreenSwipeToRefreshAndCheckHomeScreen() {
    step("Обновить данные на экране с помощью жеста SwipeToRefresh") {
        HomeScreen {
            swipeRefresh {
                swipeDown()
            }
            expected("Отображается Home экран c текущей погодой") {
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