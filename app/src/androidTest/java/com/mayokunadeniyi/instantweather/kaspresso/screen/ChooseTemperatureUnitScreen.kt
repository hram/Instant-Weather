package com.mayokunadeniyi.instantweather.kaspresso.screen

import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.components.kautomator.screen.UiScreen

object ChooseTemperatureUnitScreen : UiScreen<ChooseTemperatureUnitScreen>() {

    val celsius = UiTextView { withText("Celsius/°C") }

    val fahrenheit = UiTextView { withText("Fahrenheit/°F") }

    val negativeButton = UiButton { withResourceName("android:id/button2") }

    override val packageName: String
        get() = "com.mayokunadeniyi.instantweather"
}