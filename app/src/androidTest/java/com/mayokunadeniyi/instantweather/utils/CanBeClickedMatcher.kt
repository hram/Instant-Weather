package com.mayokunadeniyi.instantweather.utils

import android.view.View
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

internal class CanBeClickedMatcher : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description) {
        description.appendText("is clickable itself or by parent")
    }

    public override fun matchesSafely(view: View): Boolean {
        return canHandleClick(view)
    }
}