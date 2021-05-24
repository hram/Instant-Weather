package com.mayokunadeniyi.instantweather.utils

import android.view.View
import android.view.ViewParent

internal fun canHandleClick(view: View?): Boolean {
    val parent: ViewParent? = view?.parent
    return when {
        view == null -> false
        view.isClickable -> true
        parent != null && parent is View -> canHandleClick(parent)
        else -> false
    }
}