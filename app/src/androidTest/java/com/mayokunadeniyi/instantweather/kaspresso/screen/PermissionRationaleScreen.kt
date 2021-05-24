package com.mayokunadeniyi.instantweather.kaspresso.screen

import android.app.AlertDialog
import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.components.kautomator.screen.UiScreen

object PermissionRationaleScreen : UiScreen<PermissionRationaleScreen>() {

    val title = UiTextView { withText("Location Permission") }

    val message = UiTextView { withResourceName("android:id/message") }

    val positiveButton = UiButton { withResourceName("android:id/button1") }

    val negativeButton = UiButton { withResourceName("android:id/button2") }

    val neutralButton = UiButton { withResourceName("android:id/button3") }

    override val packageName: String
        get() = TODO("Not yet implemented")
}