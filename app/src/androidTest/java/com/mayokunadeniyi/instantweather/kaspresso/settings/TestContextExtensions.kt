package com.mayokunadeniyi.instantweather.kaspresso.settings

import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.mayokunadeniyi.instantweather.kaspresso.TestCaseDsl
import com.mayokunadeniyi.instantweather.kaspresso.extensions.expected
import com.mayokunadeniyi.instantweather.kaspresso.extensions.inProcessClick
import com.mayokunadeniyi.instantweather.kaspresso.extensions.takeScreenshot
import com.mayokunadeniyi.instantweather.kaspresso.screen.ChooseTemperatureUnitScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.ChooseThemeScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.EditValueScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.HomeScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.MainScreen
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

fun TestContext<TestCaseDsl>.stepMainScreenClickSettingsAndCheck() {
    step("Кликнуть в панели навигации на иконку настроек") {
        MainScreen {
            settingsButton { inProcessClick() }
        }

        expected("Просходит переход на экран настроек, все настройки выставлены по умолчанию") {
            SettingsScreen {
                recycler {
                    isDisplayed()
                    hasSize(6)
                    childAt<SettingsScreen.Item>(0) {
                        title { hasText("Cache") }
                        summary { isNotDisplayed() }
                    }
                    childAt<SettingsScreen.Item>(1) {
                        title { hasText("Cache duration (seconds)") }
                        summary { hasText("900") }
                    }
                    childAt<SettingsScreen.Item>(2) {
                        title { hasText("Theme") }
                        summary { isNotDisplayed() }
                    }
                    childAt<SettingsScreen.Item>(3) {
                        title { hasEmptyText() }
                        summary { hasText("System") }
                    }
                    childAt<SettingsScreen.Item>(4) {
                        title { hasText("Temperature Unit") }
                        summary { isNotDisplayed() }
                    }
                    childAt<SettingsScreen.Item>(5) {
                        title { hasEmptyText() }
                        summary { hasText("Celsius/°C") }
                    }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepSettingsScreenClickCacheAndCheckDialog() {
    step("Кликнуть по настройке \"Cache\"") {
        SettingsScreen {
            recycler {
                childAt<SettingsScreen.Item>(1) {
                    inProcessClick()
                }
            }

            expected("Открываеся диалог редактирования значения") {
                EditValueScreen {
                    title { hasText("Cache duration (seconds)") }
                    edit { hasText("900") }
                    positiveButton { isDisplayed() }
                    negativeButton { isDisplayed() }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepSettingsScreenClickThemeAndCheckDialog() {
    step("Кликнуть по настройке \"Theme\"") {
        SettingsScreen {
            recycler {
                childAt<SettingsScreen.Item>(3) {
                    inProcessClick()
                }
            }

            expected("Открываеся диалог выбора одного значения из списка") {
                ChooseThemeScreen {
                    light { isDisplayed() }
                    dark { isDisplayed() }
                    auto { isDisplayed() }
                    system { isDisplayed() }
                    negativeButton { isDisplayed() }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepSettingsScreenClickTemperatureUnitAndCheckDialog() {
    step("Кликнуть по настройке \"Temperature Unit\"") {
        SettingsScreen {
            recycler {
                childAt<SettingsScreen.Item>(5) {
                    inProcessClick()
                }
            }

            expected("Открываеся диалог выбора одного значения из двух Celsius/°C и Fahrenheit/°F") {
                ChooseTemperatureUnitScreen {
                    celsius { isDisplayed() }
                    fahrenheit { isDisplayed() }
                    negativeButton { isDisplayed() }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepChooseThemeScreenChooseDarkAndCheckSettingsScreen() {
    step("Выбрать тему \"Dark theme\"") {
        ChooseThemeScreen {
            dark { click() }
        }
        expected("Диалог закрывается, происходит смена тебы, в настройках отображается выбранная тема") {
            SettingsScreen {
                recycler {
                    childAt<SettingsScreen.Item>(3) {
                        summary { hasText("Dark") }
                    }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepChooseTemperatureUnitScreenChooseFahrenheitAndCheckSettingsScreen() {
    step("Выбрать \"Fahrenheit/°F\"") {
        ChooseTemperatureUnitScreen {
            fahrenheit { click() }
        }
        expected("Диалог закрывается, происходит смена тебы, в настройках отображается выбранная тема") {
            SettingsScreen {
                recycler {
                    childAt<SettingsScreen.Item>(5) {
                        summary { hasText("Fahrenheit/°F") }
                    }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepEditValueScreenEnterValueAndCheck() {
    step("Ввести новое значение настройки") {
        EditValueScreen {
            edit { replaceText("300") }

            expected("В диалоге отображается новое значение") {
                edit { hasText("300") }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepEditValueScreenPressOkAndCheckSettingsScreen() {
    step("Нажать кнопку \"ОК\"") {
        EditValueScreen {
            positiveButton { click() }

            expected("Введенное в диалоге значение отображается на экране настроек") {
                SettingsScreen {
                    recycler {
                        childAt<SettingsScreen.Item>(1) {
                            title { hasText("Cache duration (seconds)") }
                            summary { hasText("300") }
                        }
                    }
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepMainScreenClickHomeAndCheckFahrenheit() {
    step("Кликнуть в панели навигации на иконку \"Home\"") {
        MainScreen {
            homeButton { inProcessClick() }
        }

        expected("Просходит переход на экран текущей погоды, темпратура отображается в фаренгейтах") {
            HomeScreen {
                temperature { hasText("57.65℉") }
            }
        }
    }
}
