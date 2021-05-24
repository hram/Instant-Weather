package com.mayokunadeniyi.instantweather.utils

import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import org.hamcrest.Matcher
import org.hamcrest.core.IsAnything

object EspressoActions {
    fun click(
        type: UITestConfig.ClickType = UITestConfig.clicksType,
        coordinatesProvider: CoordinatesProvider = VisibleCenterGlobalCoordinatesProvider(),
        visualize: Boolean = UITestConfig.visualizeClicks
    ): ViewAction {

        fun safeAction(action: ViewAction) = ActionOnEnabledElement(
            ActionOnClickableElement(action)
        )

        val clickAction = when (type) {
            is UITestConfig.ClickType.EspressoClick -> when (type.rollbackPolicy) {

                is UITestConfig.ClickType.EspressoClick.ClickRollbackPolicy.DoNothing -> defaultEspressoClickAction(
                    coordinatesProvider
                )

                is UITestConfig.ClickType.EspressoClick.ClickRollbackPolicy.TryOneMoreClick -> {
                    val rollbackAction = safeAction(defaultEspressoClickAction(coordinatesProvider))
                    return defaultEspressoClickAction(coordinatesProvider, rollbackAction)
                }

                is UITestConfig.ClickType.EspressoClick.ClickRollbackPolicy.Fail -> defaultEspressoClickAction(
                    coordinatesProvider, object : ViewAction {
                    override fun getDescription(): String =
                        "fake fail action after click interpreted as long click"

                    override fun getConstraints(): Matcher<View> = IsAnything()

                    override fun perform(uiController: UiController?, view: View?) {
                        throw PerformException.Builder()
                            .withActionDescription("click interpreted as long click")
                            .withViewDescription("view")
                            .build()
                    }
                }
                )
            }

            is UITestConfig.ClickType.InProcessClick -> inProcessClickAction(
                coordinatesProvider = coordinatesProvider,
                visualizeClicks = visualize
            )
        }
        return safeAction(clickAction)
    }

    /**
     * Same as [ViewActions.click] but with usage of given coordinates provider
     */
    private fun defaultEspressoClickAction(
        coordinatesProvider: CoordinatesProvider,
        rollbackAction: ViewAction? = null
    ): ViewAction =
        actionWithAssertions(
            GeneralClickAction(
                Tap.SINGLE,
                coordinatesProvider,
                Press.FINGER,
                InputDevice.SOURCE_UNKNOWN,
                MotionEvent.BUTTON_PRIMARY,
                rollbackAction
            )
        )
}