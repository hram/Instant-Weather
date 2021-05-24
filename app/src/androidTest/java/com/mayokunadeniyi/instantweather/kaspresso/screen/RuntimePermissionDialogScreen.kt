package com.mayokunadeniyi.instantweather.kaspresso.screen

import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.screen.UiScreen
import java.util.regex.Pattern

object RuntimePermissionDialogScreen : UiScreen<RuntimePermissionDialogScreen>() {

    val neverAskButton = UiButton { withText(Pattern.compile("Больше не спрашивать|Don't ask again", Pattern.CASE_INSENSITIVE)) }

    val denyButton = UiButton { withText(Pattern.compile("ОТКЛОНИТЬ|DENY", Pattern.CASE_INSENSITIVE)) }

    val allowButton = UiButton { withText(Pattern.compile("РАЗРЕШИТЬ|ALLOW", Pattern.CASE_INSENSITIVE)) }

    val whileUsingAppButton = UiButton { withText(Pattern.compile("РАЗРЕШИТЬ|While using the app", Pattern.CASE_INSENSITIVE)) }

    val onlyThisTimeButton = UiButton { withText(Pattern.compile("Разрешить только во время использования приложения|Only this time", Pattern.CASE_INSENSITIVE)) }

    val denyAndNeverAskButton = UiButton { withId("com.android.permissioncontroller", "permission_deny_and_dont_ask_again_button") }

    override val packageName: String
        get() = TODO("Not yet implemented")
}
