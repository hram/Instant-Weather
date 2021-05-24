package com.mayokunadeniyi.instantweather.utils

import androidx.test.espresso.EspressoException
import java.util.concurrent.TimeUnit

object UITestConfig {

    /**
     * Works only for [ClickType.InProcessClick]
     */
    var visualizeClicks: Boolean = true

    val defaultClicksType: ClickType = ClickType.InProcessClick

    var clicksType: ClickType = defaultClicksType

    /**
     * Changing this value will affect all subsequent actions/checks wait frequency
     */
    var waiterFrequencyMs: Long = 50L

    /**
     * Changing this value will affect all subsequent actions/checks wait timeout
     */
    var waiterTimeoutMs: Long = TimeUnit.SECONDS.toMillis(2)

    /**
     * Exceptions to be waited for; any unregistered exceptions will be propagated
     */
    var waiterAllowedExceptions = setOf(
        EspressoException::class.java,
        AssertionError::class.java
    )

    /**
     * To react on every exception during waiter loop
     */
    var onWaiterRetry: (e: Throwable) -> Unit = {}

    sealed class ClickType {
        /**
         * Use default espresso clicks and long clicks
         */
        class EspressoClick(val rollbackPolicy: ClickRollbackPolicy) : ClickType() {
            /**
             * Because of clicks implementation inside Espresso sometimes clicks can be interpreted
             * as long clicks. Here we have several options to handle it.
             *
             * https://stackoverflow.com/questions/32330671/android-espresso-performs-longclick-instead-of-click
             */
            sealed class ClickRollbackPolicy {
                object DoNothing : ClickRollbackPolicy()
                object TryOneMoreClick : ClickRollbackPolicy()
                object Fail : ClickRollbackPolicy()
            }
        }

        /**
         * [Documentation](https://avito-tech.github.io/avito-android/docs/test_framework/internals/)
         */
        object InProcessClick : ClickType()
    }
}