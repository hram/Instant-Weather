package com.mayokunadeniyi.instantweather.kaspresso.screen

import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.components.kautomator.screen.UiScreen

object ChooseThemeScreen : UiScreen<ChooseThemeScreen>() {

    val light = UiTextView { withText("Light theme") }

    val dark = UiTextView { withText("Dark theme") }

    val auto = UiTextView { withText("Auto battery") }

    val system = UiTextView { withText("Follow system") }

    val negativeButton = UiButton { withResourceName("android:id/button2") }

    override val packageName: String
        get() = "com.mayokunadeniyi.instantweather"
}