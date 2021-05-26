package com.mayokunadeniyi.instantweather.kaspresso.forecast

import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.mayokunadeniyi.instantweather.kaspresso.TestCaseDsl
import com.mayokunadeniyi.instantweather.kaspresso.extensions.expected
import com.mayokunadeniyi.instantweather.kaspresso.extensions.inProcessClick
import com.mayokunadeniyi.instantweather.kaspresso.extensions.takeScreenshot
import com.mayokunadeniyi.instantweather.kaspresso.screen.ForecastScreen
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
                    weatherIn { isDisplayed() }
                    date { hasAnyText() }
                    icon { isDisplayed() }
                    temperature { isDisplayed() }
                    main { isDisplayed() }
                    error { isNotDisplayed() }
                    detail { isDisplayed() }
                    humidity { isDisplayed() }
                    pressure { isDisplayed() }
                    windSpeed { isDisplayed() }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepMainScreenClickForecastAndCheckForecastScreen() {
    step("Кликнуть в панели навигации на иконку прогноза \"Forecast\"") {
        MainScreen {
            forecastButton { inProcessClick() }
        }

        expected("Просходит переход на экран прогноза погоды\n"
            + "Отображается календарь и список прогноза погоды") {
            ForecastScreen {
                calendar {
                    isDisplayed()
                }
                recycler {
                    isDisplayed()
                    hasSize(data.forecastSize)
                    data.forecast.forEach { forecast ->
                        childAt<ForecastScreen.Item>(forecast.index) {
                            icon { isDisplayed() }
                            main { hasText(forecast.main) }
                            description { hasText(forecast.description) }
                            temperature { hasText(forecast.temperature) }
                            humidity { hasText(forecast.humidity) }
                            pressure { hasText(forecast.pressure) }
                            speed { hasText(forecast.speed) }
                            date { hasText(forecast.date) }
                        }
                    }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepMainScreenClickForecastAndCheckForecastScreenWithError() {
    step("Кликнуть в панели навигации на иконку прогноза \"Forecast\"") {
        MainScreen {
            forecastButton { inProcessClick() }
        }

        expected("Просходит переход на экран прогноза погоды\n"
            + "Отображается календарь и ошибка загрузки данных") {
            ForecastScreen {
                calendar {
                    isDisplayed()
                }
                recycler {
                    isNotDisplayed()
                }
                error {
                    isDisplayed()
                    hasText("Oops! An error occurred. \n\n Please check your internet connection. \n\n Swipe down to retry.")
                }
                empty {
                    isNotDisplayed()
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepMainScreenClickForecastAndCheckForecastScreenWithEmpty() {
    step("Кликнуть в панели навигации на иконку прогноза \"Forecast\"") {
        MainScreen {
            forecastButton { inProcessClick() }
        }

        expected("Просходит переход на экран прогноза погоды\n"
            + "Отображается календарь и сообщение о том что за выбранный интервал нет данных") {
            ForecastScreen {
                calendar {
                    isDisplayed()
                }
                recycler {
                    isNotDisplayed()
                }
                empty {
                    isDisplayed()
                    hasText("Weather forecast for selected day is unavailable.")
                }
                error {
                    isNotDisplayed()
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepForecastScreenSwipeToRefreshAndCheckForecastScreen() {
    step("Обновить данные на экране с помощью жеста SwipeToRefresh") {
        ForecastScreen {
            swipeRefresh {
                swipeDown()
            }
            expected("Просходит успешный перезапрос данных\n"
                + "Отображается календарь и список прогноза погоды") {
                calendar {
                    isDisplayed()
                }
                recycler {
                    isDisplayed()
                    hasSize(data.forecastSize)
                    data.forecast.forEach { forecast ->
                        childAt<ForecastScreen.Item>(forecast.index) {
                            icon { isDisplayed() }
                            main { hasText(forecast.main) }
                            description { hasText(forecast.description) }
                            temperature { hasText(forecast.temperature) }
                            humidity { hasText(forecast.humidity) }
                            pressure { hasText(forecast.pressure) }
                            speed { hasText(forecast.speed) }
                            date { hasText(forecast.date) }
                        }
                    }
                }
            }
        }
    }
}