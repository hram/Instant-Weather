package com.mayokunadeniyi.instantweather.kaspresso.runtimepermission

import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.mayokunadeniyi.instantweather.kaspresso.TestCaseDsl
import com.mayokunadeniyi.instantweather.kaspresso.extensions.expected
import com.mayokunadeniyi.instantweather.kaspresso.extensions.takeScreenshot
import com.mayokunadeniyi.instantweather.kaspresso.screen.HomeScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.MainScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.PermissionRationaleScreen
import com.mayokunadeniyi.instantweather.kaspresso.screen.RuntimePermissionDialogScreen

fun TestContext<TestCaseDsl>.stepStartAppAndCheckRuntimePermissionDialogScreen() {
    step("Запустить приложение") {
        takeScreenshot()
        expected("Отображается системный диалог с запросом доступа к данным о местоположении устройства\n"
            + "Отображается текст о необходимости дать разрешение и две кнопки:\n"
            + "- Разрешить только во время использования приложения\n"
            + "- Отклонить") {
            device.permissions.isDialogVisible()
            RuntimePermissionDialogScreen {
                onlyThisTimeButton {
                    isDisplayed()
                    isEnabled()
                }
                denyButton {
                    isDisplayed()
                    isEnabled()
                }
                denyAndNeverAskButton {
                    isNotDisplayed()
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepRuntimePermissionDialogScreenAllowAndCheckMainScreen() {
    step("Нажать на кнопку \"Разрешить только во время использования приложения\"") {
        RuntimePermissionDialogScreen {
            onlyThisTimeButton {
                click()
            }
        }
        expected("Происходит переход на главный экран приложения") {
            MainScreen {
                homeButton { isDisplayed() }
                forecastButton { isDisplayed() }
                searchButton { isDisplayed() }
                settingsButton { isDisplayed() }

                HomeScreen {

                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepRuntimePermissionDialogScreenDenyAndNeverAskAndCheckMainScreen() {
    step("Нажать на кнопку \"Запретить и больше не спрашивать\"") {
        RuntimePermissionDialogScreen {
            denyAndNeverAskButton {
                click()
            }
        }
        expected("Происходит переход на главный экран приложения") {
            MainScreen {
                homeButton { isDisplayed() }
                forecastButton { isDisplayed() }
                searchButton { isDisplayed() }
                settingsButton { isDisplayed() }

                HomeScreen {

                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepRuntimePermissionDialogScreenDenyAndCheckPermissionRationaleScreen() {
    step("Нажать на кнопку \"Разрешить только во время использования приложения\"") {
        RuntimePermissionDialogScreen {
            denyButton {
                click()
            }
        }
        expected("Отображается кастомный диалога об необходимости дать разрешения") {
            PermissionRationaleScreen {
                title { isDisplayed() }
                message { hasText("This application requires access to your location to function!") }
                positiveButton {
                    isDisplayed()
                    hasText("ASK ME")
                }
                negativeButton {
                    isDisplayed()
                    hasText("NO")
                }
                neutralButton { isNotDisplayed() }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepRationaleScreenClickAskMeAndCheckRuntimePermissionDialogScreen() {
    step("Нажать кнопку \"ASK ME\"") {
        PermissionRationaleScreen {
            positiveButton { click() }
        }

        expected("Отображается системный диалог с повторным запросом доступа к данным о местоположении устройства\n"
            + "Отображается текст о необходимости дать разрешение и три кнопки:\n"
            + "- Разрешить только во время использования приложения\n"
            + "- Отклонить\n"
            + "- Запретить и больше не спрашивать") {
            device.permissions.isDialogVisible()
            RuntimePermissionDialogScreen {
                onlyThisTimeButton {
                    isDisplayed()
                    isEnabled()
                }
                denyButton {
                    isDisplayed()
                    isEnabled()
                }
                denyAndNeverAskButton {
                    isDisplayed()
                    isEnabled()
                }
            }
        }
    }
}

fun TestContext<TestCaseDsl>.stepRationaleScreenClickNoAndExit() {
    step("Нажать кнопку \"NO\"") {
        PermissionRationaleScreen {
            negativeButton { click() }
            expected("Приложение завершает работу") {

            }
        }
    }
}