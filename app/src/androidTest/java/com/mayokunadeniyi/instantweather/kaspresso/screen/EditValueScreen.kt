package com.mayokunadeniyi.instantweather.kaspresso.screen

import com.kaspersky.components.kautomator.component.edit.UiEditText
import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.components.kautomator.screen.UiScreen

object EditValueScreen : UiScreen<EditValueScreen>() {

    val title = UiTextView { withId(this@EditValueScreen.packageName, "alertTitle") }

    val message = UiTextView { withResourceName("android:id/message") }

    val edit = UiEditText { withResourceName("android:id/edit") }

    val positiveButton = UiButton { withResourceName("android:id/button1") }

    val negativeButton = UiButton { withResourceName("android:id/button2") }

    override val packageName: String
        get() = "com.mayokunadeniyi.instantweather"
}